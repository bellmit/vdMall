package com.vedeng.mjx.web.security;

import com.github.fartherp.shiro.RedisCacheManager;
import com.github.fartherp.shiro.RedisSessionDAO;
import com.github.fartherp.shiro.RedisSessionListener;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @author: Wyatt
 * @Date: 2019-06-03 15:38
 */
@Component
public class ShiroConfig {

    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

    @Value("${session.expire.seconds:1800}")
    private long ttl;

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager sessionManager, RedisCacheManager shiroRedisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(shiroRedisCacheManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/login.html", "anon");

        //健康检查
        chainDefinition.addPathDefinition("/checkpreload.html", "anon");

        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs/**", "anon");
        chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon");
        //立即申请
      //chainDefinition.addPathDefinition("/apply/vedengJoin", "authc");
        chainDefinition.addPathDefinition("/doLogin.html", "anon");

        chainDefinition.addPathDefinition("/**", "anon"); // need to accept POSTs from the login form

//        chainDefinition.addPathDefinition("/**", "authc"); // need to accept POSTs from the login form

        chainDefinition.addPathDefinition("/logout.html", "logout");

        return chainDefinition;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(SessionDAO redisSessionDAO, ObjectProvider<List<SessionListener>> sessionListenersProvider, CacheManager shiroRedisCacheManager) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setSessionListeners(sessionListenersProvider.getIfAvailable());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(ttl);
        return sessionManager;
    }

    /**
     * 内置session监听器，保证删除session/cache冗余的数据信息
     */
    @Bean
    public List<SessionListener> sessionListener(SessionDAO redisSessionDAO, CustomRealm customRealm) {
        return Collections.singletonList(new RedisSessionListener(redisSessionDAO, Arrays.asList(customRealm)));
    }

    @Bean(name = "shiroRedisCacheManager")
    public RedisCacheManager cacheManager(RedissonClient redissonClient) {
        RedisCacheManager manager= new RedisCacheManager(redissonClient);
        return manager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisCacheManager shiroRedisCacheManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO(shiroRedisCacheManager);
        redisSessionDAO.setSessionInMemoryEnabled(false);
        return redisSessionDAO;
    }
}

