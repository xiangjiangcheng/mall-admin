package com.river.malladmin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.river.malladmin.common.base.Option;
import com.river.malladmin.shared.codegen.model.entity.GenConfig;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.model.form.MenuForm;
import com.river.malladmin.system.model.query.MenuPageQuery;
import com.river.malladmin.system.model.vo.MenuVO;
import com.river.malladmin.system.model.vo.RouteVO;

import java.util.List;
import java.util.Set;

/**
 * @author xiang
 * @createDate 2025-03-25 22:30:11
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusByIds(Set<Long> menuIds);

    List<Option<Long>> getMenuTree();

    List<MenuVO> getMenus(MenuPageQuery query);

    MenuForm getMenuForm(Long id);

    boolean saveMenu(MenuForm menuForm);

    boolean deleteMenu(Long id);

    boolean updateMenuVisible(Long menuId, Integer visible);

    List<RouteVO> getRoutes();

    /**
     * 代码生成时添加菜单
     *
     * @param parentMenuId 父菜单ID
     * @param genConfig   实体名
     */
    void addMenuForCodegen(Long parentMenuId, GenConfig genConfig);

    List<Option<Long>> listMenuOptions(boolean onlyParent);
}
