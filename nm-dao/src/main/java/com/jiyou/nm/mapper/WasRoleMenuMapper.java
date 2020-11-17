package com.jiyou.nm.mapper;

import com.jiyou.nm.model.entity.WasMenu;
import com.jiyou.nm.model.entity.WasRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 老酒后台-角色对应的菜单 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface WasRoleMenuMapper extends BaseMapper<WasRoleMenu> {

    List<WasMenu> selectRoleOwnedList(@Param("roleId") Integer roleId);

}
