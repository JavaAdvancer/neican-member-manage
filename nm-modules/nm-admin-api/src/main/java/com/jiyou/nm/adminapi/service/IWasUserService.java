package com.jiyou.nm.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.common.utils.tree.Tree;
import com.jiyou.nm.model.entity.WasMenu;
import com.jiyou.nm.model.entity.WasUser;
import com.jiyou.nm.model.vo.PageWasUserRes;
import com.jiyou.nm.model.vo.UpdateWasUserReq;

import java.util.List;

/**
 * <p>
 * 管理后台用户表 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface IWasUserService extends IService<WasUser> {

    WasUser findUserByUsername(String usname);

    boolean  saveOrUpdateUser(UpdateWasUserReq req);

    PageInfo<PageWasUserRes> pageQuery(String keyword, PageReq pageReq);

    boolean updateState(Integer id,Integer state);

    boolean delete(Integer id);

    List<Tree<WasMenu>> selectOwnedMenuTree(Integer id);

}
