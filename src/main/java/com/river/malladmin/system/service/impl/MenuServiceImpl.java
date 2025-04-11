package com.river.malladmin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.common.enums.MenuTypeEnum;
import com.river.malladmin.common.exception.BusinessException;
import com.river.malladmin.common.result.ResultCode;
import com.river.malladmin.system.mapper.MenuMapper;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.form.MenuForm;
import com.river.malladmin.system.model.query.MenuPageQuery;
import com.river.malladmin.system.model.vo.MenuVO;
import com.river.malladmin.system.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiang
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

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

        Menu menu = menuForm.toEntity();
        return this.saveOrUpdate(menu);
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
}




