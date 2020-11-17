package com.jiyou.nm.mapper;

import com.jiyou.nm.model.entity.WasRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiyou.nm.model.vo.PageWasRoleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 老酒后台-角色 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface WasRoleMapper extends BaseMapper<WasRole> {

    List<PageWasRoleRes> select4Page(@Param("keyword") String keyword);

}
