package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.river.malladmin.system.model.entity.RoleMenu;
import com.river.malladmin.system.model.vo.RolePermVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xiang
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
 * @createDate 2025-03-25 22:31:19
 * @Entity com.river.malladmin.system.model.entity.RoleMenu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色编码查询角色权限(button type)
     *
     * @param roleCode 角色编码
     * @return 角色权限集合
     */
    List<RolePermVO> selectRolePerms(@Param("roleCode") String roleCode);

    /**
     * 根据角色ID查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return
     */
    @Select("SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId}")
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    void deleteMenusByRoleId(Long roleId);
}




