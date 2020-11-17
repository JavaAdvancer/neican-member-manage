package com.jiyou.nm.adminapi.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyou.nm.adminapi.service.IWasMenuService;
import com.jiyou.nm.adminapi.service.IWasRoleMenuService;
import com.jiyou.nm.common.utils.tree.BuildTree;
import com.jiyou.nm.common.utils.tree.Tree;
import com.jiyou.nm.mapper.WasRoleMenuMapper;
import com.jiyou.nm.model.entity.WasMenu;
import com.jiyou.nm.model.entity.WasRoleMenu;
import com.jiyou.nm.model.vo.UpdateRoleMenuReq;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 老酒后台-角色对应的菜单 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@Service
public class WasRoleMenuServiceImpl extends ServiceImpl<WasRoleMenuMapper, WasRoleMenu> implements IWasRoleMenuService {

    private IWasMenuService wasMenuService;

    public WasRoleMenuServiceImpl(IWasMenuService wasMenuService) {
        this.wasMenuService = wasMenuService;
    }

    @Override
    public JSONObject selectRoleMenuTree(Integer roleId) {
        //select all menu list
        List<WasMenu> allMenuList = wasMenuService.list(
                Wrappers.<WasMenu>lambdaQuery().orderByAsc(WasMenu::getOrderNum));
        //select current role  already owned
        List<WasRoleMenu> ownedMenuList = this.list(
                Wrappers.<WasRoleMenu>lambdaQuery().eq(WasRoleMenu::getRoleId, roleId));
        List<Integer> ownedMenuIdList = ListUtils.emptyIfNull(ownedMenuList)
                .stream().map(WasRoleMenu::getMenuId).collect(Collectors.toList());
        List<Tree<WasMenu>> trees = new ArrayList<>();
        for (WasMenu menu : allMenuList) {
            Tree<WasMenu> tree = new Tree<>();
            tree.setId(Integer.toString(menu.getId()));
            tree.setParentId(Integer.toString(menu.getParentId()));
            tree.setText(menu.getName());
            Map<String, Object> state = new HashMap<>(16);
            if (ownedMenuIdList.contains(menu.getId())) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<WasMenu> build = BuildTree.build(trees);
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("tree", build);
        jsonObject.put("perms", ownedMenuIdList);
        return jsonObject;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(UpdateRoleMenuReq req) {
        //1.先删除
        LambdaQueryWrapper<WasRoleMenu> deleteWrapper = Wrappers.<WasRoleMenu>lambdaQuery()
                .eq(WasRoleMenu::getRoleId, req.getRoleId());
        this.remove(deleteWrapper);
        //2.重新保存
        if (CollUtil.isNotEmpty(req.getMenuIdList())) {
            List<WasRoleMenu> insertList = req.getMenuIdList().stream().map(a -> {
                WasRoleMenu roleMenu = new WasRoleMenu();
                roleMenu.setRoleId(req.getRoleId());
                roleMenu.setMenuId(a);
                return roleMenu;
            }).collect(Collectors.toList());
            return this.saveBatch(insertList);
        }
        return true;
    }

    @Override
    public List<WasMenu> selectRoleOwnedList(Integer roleId) {
        return this.baseMapper.selectRoleOwnedList(roleId);
    }

}
