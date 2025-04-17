package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.common.contant.SystemConstants;
import com.river.malladmin.common.enums.MenuTypeEnum;
import com.river.malladmin.common.enums.StatusEnum;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.security.utils.SecurityUtils;
import com.river.malladmin.shared.codegen.model.entity.GenConfig;
import com.river.malladmin.system.mapper.MenuMapper;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.form.MenuForm;
import com.river.malladmin.system.model.query.MenuPageQuery;
import com.river.malladmin.system.model.vo.MenuVO;
import com.river.malladmin.system.model.vo.RouteVO;
import com.river.malladmin.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiang
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuServiceImpl roleMenuService;

    @Override
    public List<Menu> getMenusByIds(Set<Long> menuIds) {
        if (menuIds.isEmpty()) return Collections.emptyList();
        return this.lambdaQuery().in(Menu::getId, menuIds).list();
    }

    @Override
    public List<Option<Long>> getMenuTree() {
        List<Menu> masterMenus = this.list();
        // 构建菜单树
        return buildMenuTree(masterMenus, 0L);
    }

    /**
     * 递归构建菜单树
     *
     * @param masterMenus 所有菜单
     * @param parentId    父菜单ID
     * @return 菜单树
     */
    private List<Option<Long>> buildMenuTree(List<Menu> masterMenus, Long parentId) {
        // 找出当前父菜单下所有子菜单
        List<Option<Long>> menuOptions = masterMenus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new Option<>(menu.getId(), menu.getName())).toList();

        if (CollUtil.isNotEmpty(menuOptions)) {
            // 递归构建子菜单
            for (Option<Long> menu : menuOptions) {
                List<Option<Long>> subMenuOptions = buildMenuTree(masterMenus, menu.getValue());
                if (CollUtil.isNotEmpty(subMenuOptions)) {
                    menu.setChildren(subMenuOptions);
                }
            }
        }
        return menuOptions;
    }

    @Override
    public List<MenuVO> getMenus(MenuPageQuery query) {
        List<Menu> menus = this.lambdaQuery()
                .like(StrUtil.isNotBlank(query.getKeywords()), Menu::getName, query.getKeywords())
                .orderByAsc(Menu::getSort)
                .list();

        // 获取所有菜单ID
        Set<Long> menuIds = menus.stream()
                .map(Menu::getId)
                .collect(Collectors.toSet());

        // 获取所有父级ID
        Set<Long> parentIds = menus.stream()
                .map(Menu::getParentId)
                .collect(Collectors.toSet());

        // 获取根节点ID（递归的起点），即父节点ID中不包含在部门ID中的节点，注意这里不能拿顶级菜单 O 作为根节点，因为菜单筛选的时候 O 会被过滤掉
        List<Long> rootIds = parentIds.stream()
                .filter(id -> !menuIds.contains(id))
                .toList();

        // 使用递归函数来构建菜单树
        return rootIds.stream()
                .flatMap(rootId -> buildMenuTree(rootId, menus).stream())
                .collect(Collectors.toList());
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuVO> buildMenuTree(Long parentId, List<Menu> menuList) {
        return CollectionUtil.emptyIfNull(menuList)
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = MenuVO.from(entity);
                    List<MenuVO> children = buildMenuTree(entity.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).toList();
    }

    @Override
    public MenuForm getMenuForm(Long id) {
        Menu menu = this.getById(id);
        if (Objects.isNull(menu)) throw new BusinessException(ResultCode.MENU_NOT_EXIST);
        MenuForm form = new MenuForm();
        BeanUtils.copyProperties(menu, form);
        return form;
    }

    @Override
    public boolean saveMenu(MenuForm menuForm) {
        MenuTypeEnum menuType = menuForm.getType();
        if (menuType == MenuTypeEnum.CATALOG) {  // 如果是目录
            String path = menuForm.getRoutePath();
            if (menuForm.getParentId() == 0 && !path.startsWith("/")) {
                menuForm.setRoutePath("/" + path); // 一级目录需以 / 开头
            }
            menuForm.setComponent("Layout");
        } else if (menuType == MenuTypeEnum.EXT_LINK) {   // 如果是外链

            menuForm.setComponent(null);
        }
        if (Objects.equals(menuForm.getParentId(), menuForm.getId())) {
            throw new RuntimeException("父级菜单不能为当前菜单");
        }

        Menu entity = menuForm.toEntity();
        String treePath = generateMenuTreePath(menuForm.getParentId());
        entity.setTreePath(treePath);

        // 新增类型为菜单时候 路由名称唯一
        if (MenuTypeEnum.MENU.equals(menuType)) {
            Assert.isFalse(this.exists(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getRouteName, entity.getRouteName())
                    .ne(menuForm.getId() != null, Menu::getId, menuForm.getId())
            ), "路由名称已存在");
        } else {
            // 其他类型时 给路由名称赋值为空
            entity.setRouteName(null);
        }

        boolean result = this.saveOrUpdate(entity);
        if (result) {
            // 编辑刷新角色权限缓存
            if (menuForm.getId() != null) {
                roleMenuService.refreshRolePermsCache();
            }
        }
        // 修改菜单如果有子菜单，则更新子菜单的树路径
        updateChildrenTreePath(entity.getId(), treePath);
        return this.saveOrUpdate(entity);
    }

    /**
     * 更新子菜单树路径
     *
     * @param id       当前菜单ID
     * @param treePath 当前菜单树路径
     */
    private void updateChildrenTreePath(Long id, String treePath) {
        List<Menu> children = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id));
        if (CollectionUtil.isNotEmpty(children)) {
            // 子菜单的树路径等于父菜单的树路径加上父菜单ID
            String childTreePath = treePath + "," + id;
            this.update(new LambdaUpdateWrapper<Menu>()
                    .eq(Menu::getParentId, id)
                    .set(Menu::getTreePath, childTreePath)
            );
            for (Menu child : children) {
                // 递归更新子菜单
                updateChildrenTreePath(child.getId(), childTreePath);
            }
        }
    }

    @Override
    public boolean deleteMenu(Long id) {
        return this.lambdaUpdate().eq(Menu::getId, id).remove();
    }

    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        return this.lambdaUpdate()
                .eq(Menu::getId, menuId)
                .set(Menu::getVisible, visible).update();
    }

    @Override
    public List<RouteVO> getRoutes() {
        List<Menu> visibleMenus;
        if (SecurityUtils.isRoot()) {
            // 如果是超级管理员，返回所有路由
            visibleMenus = this.lambdaQuery()
                    .eq(Menu::getVisible, 1)
                    .in(Menu::getType, CollUtil.newArrayList(MenuTypeEnum.MENU, MenuTypeEnum.CATALOG))
                    .list();
        } else {
            Set<String> roleCodes = SecurityUtils.getRoles();
            visibleMenus = this.getBaseMapper().getMenusByRoleCodes(roleCodes);
        }
        return buildRoutes(SystemConstants.ROOT_NODE_ID, visibleMenus);
    }

    private List<RouteVO> buildRoutes(Long rootId, List<Menu> visibleMenus) {
        List<RouteVO> routeVOS = CollUtil.newArrayList();

        for (Menu menu : visibleMenus) {
            if (menu.getParentId().equals(rootId)) {
                RouteVO routeVO = toRouteVo(menu);
                routeVOS.add(routeVO);
                // 递归构建子路由
                List<RouteVO> children = buildRoutes(menu.getId(), visibleMenus);
                routeVO.setChildren(children);
            }
        }
        return routeVOS;
    }

    /**
     * 根据RouteBO创建RouteVO
     */
    private RouteVO toRouteVo(Menu menu) {
        RouteVO routeVO = new RouteVO();
        // 获取路由名称
        String routeName = menu.getRouteName();
        if (StrUtil.isBlank(routeName)) {
            // 路由 name 需要驼峰，首字母大写
            routeName = StringUtils.capitalize(StrUtil.toCamelCase(menu.getRoutePath(), '-'));
        }
        // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setName(routeName);

        // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setPath(menu.getRoutePath());
        routeVO.setRedirect(menu.getRedirect());
        routeVO.setComponent(menu.getComponent());

        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setHidden(StatusEnum.DISABLE.getValue().equals(menu.getVisible()));
        // 【菜单】是否开启页面缓存
        if (MenuTypeEnum.MENU.equals(menu.getType())
                && ObjectUtil.equals(menu.getKeepAlive(), 1)) {
            meta.setKeepAlive(true);
        }
        meta.setAlwaysShow(ObjectUtil.equals(menu.getAlwaysShow(), 1));

        String paramsJson = menu.getParams();
        // 将 JSON 字符串转换为 Map<String, String>
        if (StrUtil.isNotBlank(paramsJson)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> paramMap = objectMapper.readValue(paramsJson, new TypeReference<>() {
                });
                meta.setParams(paramMap);
            } catch (Exception e) {
                throw new RuntimeException("解析参数失败", e);
            }
        }
        routeVO.setMeta(meta);
        return routeVO;
    }

    /**
     * 代码生成时添加菜单
     *
     * @param parentMenuId 父菜单ID
     * @param genConfig    实体名称
     */
    @Override
    public void addMenuForCodegen(Long parentMenuId, GenConfig genConfig) {
        Menu parentMenu = this.getById(parentMenuId);
        Assert.notNull(parentMenu, "上级菜单不存在");

        String entityName = genConfig.getEntityName();

        long count = this.count(new LambdaQueryWrapper<Menu>().eq(Menu::getRouteName, entityName));
        if (count > 0) {
            return;
        }

        // 获取父级菜单子菜单最带的排序
        Menu maxSortMenu = this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, parentMenuId)
                .orderByDesc(Menu::getSort)
                .last("limit 1")
        );
        int sort = 1;
        if (maxSortMenu != null) {
            sort = maxSortMenu.getSort() + 1;
        }

        Menu menu = new Menu();
        menu.setParentId(parentMenuId);
        menu.setName(genConfig.getBusinessName());

        menu.setRouteName(entityName);
        menu.setRoutePath(StrUtil.toSymbolCase(entityName, '-'));
        menu.setComponent(genConfig.getModuleName() + "/" + StrUtil.toSymbolCase(entityName, '-') + "/index");
        menu.setType(MenuTypeEnum.MENU);
        menu.setSort(sort);
        menu.setVisible(1);
        boolean result = this.save(menu);

        if (result) {
            // 生成treePath
            String treePath = generateMenuTreePath(parentMenuId);
            menu.setTreePath(treePath);
            this.updateById(menu);

            // 生成CURD按钮权限
            String permPrefix = genConfig.getModuleName() + ":" + StrUtil.lowerFirst(entityName) + ":";
            String[] actions = {"查询", "新增", "编辑", "删除"};
            String[] perms = {"query", "add", "edit", "delete"};

            for (int i = 0; i < actions.length; i++) {
                Menu button = new Menu();
                button.setParentId(menu.getId());
                button.setType(MenuTypeEnum.BUTTON);
                button.setName(actions[i]);
                button.setPerm(permPrefix + perms[i]);
                button.setSort(i + 1);
                this.save(button);

                // 生成 treepath
                button.setTreePath(treePath + "," + button.getId());
                this.updateById(button);
            }
        }
    }

    /**
     * 部门路径生成
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String generateMenuTreePath(Long parentId) {
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            return String.valueOf(parentId);
        } else {
            Menu parent = this.getById(parentId);
            return parent != null ? parent.getTreePath() + "," + parent.getId() : null;
        }
    }

    @Override
    public List<Option<Long>> listMenuOptions(boolean onlyParent) {
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>()
                .in(onlyParent, Menu::getType, MenuTypeEnum.CATALOG.getValue(), MenuTypeEnum.MENU.getValue())
                .orderByAsc(Menu::getSort)
        );
        return buildMenuOptions(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 菜单下拉列表
     */
    private List<Option<Long>> buildMenuOptions(Long parentId, List<Menu> menuList) {
        List<Option<Long>> menuOptions = new ArrayList<>();

        for (Menu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                Option<Long> option = new Option<>(menu.getId(), menu.getName());
                List<Option<Long>> subMenuOptions = buildMenuOptions(menu.getId(), menuList);
                if (!subMenuOptions.isEmpty()) {
                    option.setChildren(subMenuOptions);
                }
                menuOptions.add(option);
            }
        }

        return menuOptions;
    }
}




