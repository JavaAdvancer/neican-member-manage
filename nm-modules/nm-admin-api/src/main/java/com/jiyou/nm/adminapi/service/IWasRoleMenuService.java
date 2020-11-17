package com.jiyou.nm.adminapi.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyou.nm.model.entity.WasMenu;
import com.jiyou.nm.model.entity.WasRoleMenu;
import com.jiyou.nm.model.vo.UpdateRoleMenuReq;

import java.util.List;

/**
 * <p>
 * 老酒后台-角色对应的菜单 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface IWasRoleMenuService extends IService<WasRoleMenu> {

    /**
     * 获取角色菜单树
     *
     * @param roleId
     * @return
     */
    JSONObject selectRoleMenuTree(Integer roleId);



    /**
     * 更新角色菜单设置
     */
    boolean updateRoleMenu(UpdateRoleMenuReq req);


    List<WasMenu> selectRoleOwnedList(Integer roleId);

}
