package com.jiyou.nm.common.base;


import com.jiyou.nm.common.entity.Response;
import com.jiyou.nm.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: mhj
 * @Desc: Test父类
 * @Date: 2018/4/11 17:29
 */
public abstract class BaseTest implements BaseTestInterFaces{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void printResponse(Response response) {
        logger.info(JsonUtil.toJson(response));
    }


}
