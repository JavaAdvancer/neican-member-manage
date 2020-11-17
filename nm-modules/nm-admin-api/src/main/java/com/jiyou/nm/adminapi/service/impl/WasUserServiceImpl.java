package com.jiyou.nm.adminapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jiyou.nm.adminapi.service.IWasMenuService;
import com.jiyou.nm.adminapi.service.IWasRoleMenuService;
import com.jiyou.nm.adminapi.service.IWasUserService;
import com.jiyou.nm.adminapi.util.ShiroUtil;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.common.exception.BusinessException;
import com.jiyou.nm.common.utils.AirUtils;
import com.jiyou.nm.common.utils.tree.BuildTree;
import com.jiyou.nm.common.utils.tree.Tree;
import com.jiyou.nm.mapper.WasUserMapper;
import com.jiyou.nm.model.entity.WasMenu;
import com.jiyou.nm.model.entity.WasUser;
import com.jiyou.nm.model.vo.PageWasUserRes;
import com.jiyou.nm.model.vo.UpdateWasUserReq;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理后台用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@Service
public class WasUserServiceImpl extends ServiceImpl<WasUserMapper, WasUser> implements IWasUserService {

    private IWasMenuService wasMenuService;

    private IWasRoleMenuService wasRoleMenuService;


    public WasUserServiceImpl(IWasMenuService wasMenuService, IWasRoleMenuService wasRoleMenuService) {
        this.wasMenuService = wasMenuService;
        this.wasRoleMenuService = wasRoleMenuService;
    }

    @Override
    public WasUser findUserByUsername(String username) {
        LambdaQueryWrapper<WasUser> wrapper = Wrappers.<WasUser>lambdaQuery().eq(WasUser::getUsername, username)
                .eq(WasUser::getIsDel, 0);
        return this.getOne(wrapper);
    }

    @Override
    public boolean saveOrUpdateUser(UpdateWasUserReq req) {
        if (req.getId() == null) {
            //新增用户
            if (StrUtil.isBlank(req.getPassword())) {
                throw BusinessException.createArgs("登录密码不能为空");
            }
            if (StrUtil.isBlank(req.getPasswordRep())) {
                throw BusinessException.createArgs("确认密码不能为空");
            }
            if (!req.getPassword().equals(req.getPasswordRep())) {
                throw BusinessException.createArgs("两次输入的密码不一致，请确认");
            }
        } else {
            if (StrUtil.isNotBlank(req.getPasswordRep()) && StrUtil.isBlank(req.getPassword())) {
                throw BusinessException.createArgs("登录密码不能为空");
            }
            if (StrUtil.isNotBlank(req.getPassword()) && StrUtil.isBlank(req.getPasswordRep())) {
                throw BusinessException.createArgs("确认密码不能为空");
            }
            if (StrUtil.isNotBlank(req.getPassword()) && StrUtil.isNotBlank(req.getPasswordRep())) {
                if (!req.getPassword().equals(req.getPasswordRep())) {
                    throw BusinessException.createArgs("两次输入的密码不一致，请确认");
                }
            }
        }
        WasUser user = new WasUser();
        LambdaQueryWrapper<WasUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WasUser::getIsDel, 0)
                .and(a -> a.eq(WasUser::getUsername, req.getUsername()).or().eq(WasUser::getMobile, req.getMobile()));
        if (req.getId() != null) {
            wrapper.ne(WasUser::getId, req.getId());
        }
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw BusinessException.createArgs("用户名或手机号已被占用,请重新输入");
        }
        BeanUtil.copyProperties(req, user);
        if (StrUtil.isNotBlank(req.getPassword())) {
            //密码加密
            user.setPassword(ShiroUtil.buildPassHash(req.getUsername(), req.getPassword()));
        }
        return this.saveOrUpdate(user);
    }

    @Override
    public PageInfo<PageWasUserRes> pageQuery(String keyword, PageReq pageReq) {
        PageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize(), "a.create_time desc");
        List<PageWasUserRes> list = this.baseMapper.select4Page(keyword);
        return new PageInfo<>(ListUtils.emptyIfNull(list));
    }

    @Override
    public boolean updateState(Integer id, Integer state) {
        WasUser user = this.getById(id);
        if (user == null) {
            throw BusinessException.createArgs("用户无效");
        }
        LambdaUpdateWrapper<WasUser> updateWrapper = Wrappers.<WasUser>lambdaUpdate().set(WasUser::getState, state)
                .eq(WasUser::getId, id).eq(WasUser::getIsDel, 0);
        return this.update(updateWrapper);
    }

    @Override
    public boolean delete(Integer id) {
        WasUser user = this.getById(id);
        if (user == null) {
            throw BusinessException.createArgs("用户无效");
        }
        LambdaUpdateWrapper<WasUser> updateWrapper = Wrappers.<WasUser>lambdaUpdate().set(WasUser::getIsDel, 1)
                .eq(WasUser::getId, id).eq(WasUser::getIsDel, 0);
        return this.update(updateWrapper);
    }

    @Override
    public List<Tree<WasMenu>> selectOwnedMenuTree(Integer id) {
        //load user
        WasUser user = this.getById(id);
        if (user == null) {
            throw BusinessException.createArgs("用户无效");
        }
        List<WasMenu> ownedMenuList = new ArrayList<>();
        if (user.getSuperAdmin() == 1) {
            //超级管理员默认获取所有的菜单
            List<WasMenu> list = wasMenuService.list(
                    Wrappers.<WasMenu>lambdaQuery().orderByAsc(WasMenu::getOrderNum));
            if (CollUtil.isNotEmpty(list)) {
                ownedMenuList.addAll(list);
            }
        } else {
            //获取对应角色拥有的菜单
            List<WasMenu> list = wasRoleMenuService.selectRoleOwnedList(user.getRoleId());
            this.getMenuList(ownedMenuList, list);
        }
        //构造tree
        List<Tree<WasMenu>> trees = new ArrayList<>();
        for (WasMenu menu : ownedMenuList) {
            Tree<WasMenu> tree = new Tree<>();
            tree.setId(Integer.toString(menu.getId()));
            tree.setParentId(Integer.toString(menu.getParentId()));
            tree.setText(menu.getName());
            Map<String, Object> attrMap = new HashMap<>();
            attrMap.put("route", menu.getUrl());
            attrMap.put("perms", menu.getPerms());
            attrMap.put("type", menu.getType());
            attrMap.put("icon",menu.getIcon());
            tree.setAttributes(attrMap);
            trees.add(tree);
        }
        return BuildTree.buildList(trees, "0");
    }

    /**
     * 获取父级菜单
     * @param ownedMenuList
     * @param list
     */
    private void getMenuList(List<WasMenu> ownedMenuList, List<WasMenu> list) {
        if (CollUtil.isNotEmpty(list)) {
            ownedMenuList.addAll(list);

            List<Integer> menuIds = Lists.newArrayList();
            ownedMenuList.stream().forEach(om -> {
                menuIds.add(om.getId());
            });

            list.stream().forEach(menu -> {
                this.getParentMenu(ownedMenuList, menuIds, menu);
            });
        }
    }

    private void getParentMenu(List<WasMenu> ownedMenuList, List<Integer> menuIds, WasMenu menu) {
        WasMenu wasMenu = wasMenuService.getById(menu.getParentId());
        if (AirUtils.hv(wasMenu) && !menuIds.contains(wasMenu.getId())) {
            ownedMenuList.add(wasMenu);
            menuIds.add(wasMenu.getId());

            this.getParentMenu(ownedMenuList, menuIds, wasMenu);
        }
    }

}
