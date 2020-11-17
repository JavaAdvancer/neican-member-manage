package com.jiyou.nm.adminapi.controller;


import com.jiyou.nm.common.exception.BusinessException;
import com.jiyou.nm.common.exception.RestResponse;
import com.jiyou.nm.model.entity.WasUser;
import com.jiyou.nm.model.vo.CacheUser;
import com.jiyou.nm.model.vo.WasUserLoginReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理后台用户登陆相关接口
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */

@Slf4j
@RestController
public class WasUserLoginController {

    @PostMapping("login")
    public RestResponse login(@RequestBody @Validated WasUserLoginReq loginReg) {
        String userName = loginReg.getUsername();
        String password = loginReg.getPassword();
        try {
            // 获取Subject实例对象，用户实例
            Subject currentUser = SecurityUtils.getSubject();
            // 将用户名和密码封装到UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            CacheUser cacheUser;
            //认证-传到 LoginShiroRealm 类中的方法进行认证
            currentUser.login(token);
            // 构建缓存用户信息返回给前端
            WasUser user = (WasUser) currentUser.getPrincipals().getPrimaryPrincipal();
            if(loginReg.isRememberMe()){
                DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
                DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
                RedisSessionDAO redisSessionDAO = (RedisSessionDAO )sessionManager.getSessionDAO();
                Session session = sessionManager.getSessionDAO().readSession(currentUser.getSession().getId());
                //更新session过期使用为30天
                redisSessionDAO.setExpire(60*60*24*30);
                redisSessionDAO.update(session);
            }
            cacheUser = CacheUser.builder()
                    .token(currentUser.getSession().getId().toString())
                    .build();
            BeanUtils.copyProperties(user, cacheUser);
            log.warn("CacheUser is {}", cacheUser.toString());
            return RestResponse.success(cacheUser);
        } catch (UnknownAccountException e) {
            log.error("账户不存在异常：", e);
            throw BusinessException.createArgs("账号不存在!");
        } catch (DisabledAccountException e) {
            log.error("账户已禁用异常：", e);
            throw BusinessException.createArgs("账户已禁用!");
        } catch (IncorrectCredentialsException e) {
            log.error("凭据错误（密码错误）异常：", e);
            throw BusinessException.createArgs("用户名或密码错误!");
        } catch (AuthenticationException e) {
            log.error("身份验证异常:", e);
            throw BusinessException.createArgs("用户名或密码错误!");
        }
    }

    /**
     * 登出
     * 需要传sessionId,可通过cooki或请求头传递
     *
     * @return
     */
    @GetMapping("logout")
    public RestResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RestResponse.success("登出成功");
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     *
     * @return
     */
    @RequestMapping("/un_auth")
    public RestResponse unAuth() {
        return RestResponse.error(Integer.toString(HttpStatus.UNAUTHORIZED.value()), "用户未登录！");
    }

    /**
     * 未授权，无权限，此处返回未授权状态信息由前端控制跳转页面
     *
     * @return
     */
    @RequestMapping("/unauthorized")
    public RestResponse unauthorized() {
        return RestResponse.error(Integer.toString(HttpStatus.FORBIDDEN.value()), "用户无权限！");
    }

}
