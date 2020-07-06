package com.newspring.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//Shiro的配置类
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean()
     * 创建过滤拦截
     */

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro内置的过滤器
        /**
         * shiro内置过滤器，可以实现权限相关的拦截器
         *      anon:无需认证（登录）可以访问
         *      authc：必须认证才可以访问
         *      user:如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源 权限才可以访问
         *      role:该资源必须得到校色权限才可以访问
         */
        //创建拦截Map结合
        Map<String, String> filterMap = new LinkedHashMap<>();

        //放行登录页面
        filterMap.put("/tologin", "anon");
        filterMap.put("/findfile", "anon");
        filterMap.put("/toadd", "anon");
        //放行校验页面
        filterMap.put("/user/xiaoyan", "anon");
        // 添加shiro授权访问资源
        //filterMap.put("/toadd","perms[user:toadd]");
        filterMap.put("/toupdate", "perms[user:toupdate]");
        // shiro放行swagger2
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterMap.put("/configuration/security", "anon");
        filterMap.put("/configuration/ui", "anon");

        //必须加/不然仍然可以访问的到资源 拦截所有一定要方到最后。不然会拦截
        filterMap.put("/*", "authc");
        //修改调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //未收全的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/toUnauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /*
    创建DefaultWebSecurityManager
    关联realm
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /*
    创建一个Realm对象, shiro 连接数据库的桥梁
     */
    @Bean("userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }


    /**
     * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
     */
    @Bean("shiroDialect")
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
