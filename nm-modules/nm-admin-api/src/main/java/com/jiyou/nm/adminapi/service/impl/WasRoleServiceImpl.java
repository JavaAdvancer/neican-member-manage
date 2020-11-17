package com.jiyou.nm.adminapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyou.nm.adminapi.service.IWasRoleService;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.common.exception.BusinessException;
import com.jiyou.nm.mapper.WasRoleMapper;
import com.jiyou.nm.model.entity.WasRole;
import com.jiyou.nm.model.vo.PageWasRoleRes;
import com.jiyou.nm.model.vo.UpdateWasRoleReq;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 老酒后台-角色 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@Service
public class WasRoleServiceImpl extends ServiceImpl<WasRoleMapper, WasRole> implements IWasRoleService {

    @Override
    public boolean saveOrUpdateUser(UpdateWasRoleReq req) {
        WasRole role = new WasRole();
        LambdaQueryWrapper<WasRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WasRole::getIsDel, 0)
                .eq(WasRole::getRoleName, req.getRoleName());
        if (req.getId() != null) {
            wrapper.ne(WasRole::getId, req.getId());
        }
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw BusinessException.createArgs("角色名称已被占用,请重新输入");
        }
        BeanUtil.copyProperties(req, role);
        return this.saveOrUpdate(role);
    }


    @Override
    public PageInfo<PageWasRoleRes> pageQuery(String keyword, PageReq pageReq) {
        PageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize(), "create_time desc");
        List<PageWasRoleRes> list = this.baseMapper.select4Page(keyword);
        return new PageInfo<>(ListUtils.emptyIfNull(list));
    }

    @Override
    public boolean updateState(Integer id, Integer state) {
        WasRole role = this.getById(id);
        if (role == null) {
            throw BusinessException.createArgs("角色无效");
        }
        LambdaUpdateWrapper<WasRole> updateWrapper = Wrappers.<WasRole>lambdaUpdate().set(WasRole::getState, state)
                .eq(WasRole::getId, id).eq(WasRole::getIsDel, 0);
        return this.update(updateWrapper);
    }

    @Override
    public boolean delete(Integer id) {
        WasRole role = this.getById(id);
        if (role == null) {
            throw BusinessException.createArgs("角色无效");
        }
        LambdaUpdateWrapper<WasRole> updateWrapper = Wrappers.<WasRole>lambdaUpdate().set(WasRole::getIsDel, 1)
                .eq(WasRole::getId, id).eq(WasRole::getIsDel, 0);
        return this.update(updateWrapper);
    }


}
