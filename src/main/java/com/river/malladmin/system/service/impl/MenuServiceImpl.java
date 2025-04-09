package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.mapper.MenuMapper;
import com.river.malladmin.system.model.entity.Menu;
import com.river.malladmin.system.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xiang
 * @description 针对表【sys_menu(菜单管理)】的数据库操作Service实现
 * @createDate 2025-03-25 22:30:11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getMenusByIds(Set<Long> menuIds) {
        if (menuIds.isEmpty()) return Collections.emptyList();
        return this.lambdaQuery().in(Menu::getId, menuIds).list();
    }
}




