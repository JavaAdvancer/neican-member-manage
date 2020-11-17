package com.jiyou.nm.adminapi.config.shiro;

import com.jiyou.nm.adminapi.service.IWasUserService;
import com.jiyou.nm.model.entity.WasUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * 登陆校验realm
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private IWasUserService wasUserService;

    /**
     * 只支持用户名/密码token
     *
     * @return
     */
    @Override
    public Class getAuthenticationTokenClass() {
        //支持的Token类的class
        return UsernamePasswordToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 权限分配的相关知识在此不做介绍，校验权限时才会调用该方法
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登陆验证
        UsernamePasswordToken usernamePasswordToken  = (UsernamePasswordToken)token;
        String userName = usernamePasswordToken.getPrincipal().toString();
        WasUser user = wasUserService.findUserByUsername(userName);
        /*
        DisabledAccountException （禁用的帐号）
        LockedAccountException （锁定的帐号）
        UnknownAccountException（错误的帐号）
        ExcessiveAttemptsException（登录失败次数过多）
        IncorrectCredentialsException （错误的凭证）
        ExpiredCredentialsException （过期的凭证）
        */
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getState() == 0) {
            throw new DisabledAccountException();
        }
        String salt = user.getUsername();
        // 认证信息token里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名
        // 盐也放进去
        // 这样通过配置中的 HashedCredentialsMatcher 进行自动校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(salt),
                getName());
        return authenticationInfo;
    }
}