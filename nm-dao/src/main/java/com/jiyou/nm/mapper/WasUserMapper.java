package com.jiyou.nm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiyou.nm.model.entity.WasUser;
import com.jiyou.nm.model.vo.PageWasUserRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理后台用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
public interface WasUserMapper extends BaseMapper<WasUser> {

    List<PageWasUserRes> select4Page(@Param("keyword") String keyword);

}
