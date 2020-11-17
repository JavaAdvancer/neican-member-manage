package com.jiyou.nm.adminapi.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.jiyou.nm.common.exception.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 屏蔽OPTIONS请求
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean accessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (!accessAllowed) {
            // 判断请求是否是options请求
            String method = WebUtils.toHttp(request).getMethod();
            if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
                return true;
            }
        }
        return accessAllowed;
    }

    /**
     * 解决未登录302问题
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            // 返回固定的JSON串
            this.unAuthened(request, response);
            return false;
        }

    }

    public void unAuthened(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        servletResponse.setCharacterEncoding("utf8");
        RestResponse errorRes = RestResponse.error(HttpStatus.SC_UNAUTHORIZED + "", "用户未登录");
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(JSONObject.toJSONString(errorRes));
        servletResponse.flushBuffer();
    }
}