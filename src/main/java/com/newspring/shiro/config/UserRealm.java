package com.newspring.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class UserRealm extends AuthorizingRealm {

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //给资源添加授权字符串
        info.addStringPermission("user:toadd");

        // 到数据库查询当前登录用户的授权字符串
        //  获取当前的登录对象
        //  Subject subject = SecurityUtils.getSubject();
        // User user=(User)subject.getPrincipal();
        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //模拟数据库查询用户名
        String name = "zhangsan";
        String password = "123";
        UsernamePasswordToken usrToken = (UsernamePasswordToken) token;
        if (!usrToken.getUsername().equals(name)) {
            System.out.println("realm用户名不存在");
            return null;
        } else {
            //判断密码， 第一个是返回的参数 从数据库查询出来的结果，user用户、
            // 第三个是shiro的名称
            return new SimpleAuthenticationInfo("", password, "");
        }

    }
}
