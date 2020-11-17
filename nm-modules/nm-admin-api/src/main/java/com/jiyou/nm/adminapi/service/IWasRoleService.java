package com.jiyou.nm.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.model.entity.WasRole;
import com.jiyou.nm.model.vo.PageWasRoleRes;
import com.jiyou.nm.model.vo.UpdateWasRoleReq;

/**
 * <p>
 * 老酒后台-角色 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface IWasRoleService extends IService<WasRole> {

    boolean saveOrUpdateUser(UpdateWasRoleReq req);

    PageInfo<PageWasRoleRes> pageQuery(String keyword, PageReq pageReq);

    boolean updateState(Integer id,Integer state);

    boolean delete(Integer id);

}
