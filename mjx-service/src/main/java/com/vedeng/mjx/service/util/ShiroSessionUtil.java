package com.vedeng.mjx.service.util;

import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.domain.TopAccountJx;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: shiro session的工具类
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/7/3
 */
public class ShiroSessionUtil {

    /**
     * 设置session 用户信息
     * @auther franlin.wu
     * @param accountJx 用户信息
     * @return
     */
    public static boolean setSession(TopAccountJx accountJx) {
        if(null == accountJx) {
            return false;
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        session.setAttribute(CommonConstant.SESSION_KEY, accountJx);
        UsernamePasswordToken ut = new UsernamePasswordToken(accountJx.getMobile(), "");
        subject.login(ut);

        return true;
    }
    public static boolean setSessionWxCode() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        session.setAttribute(CommonConstant.SESSION_WXCODE_KEY, "1");
        return true;
    }
    public static boolean hasSessionWxCode() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        return session.getAttribute(CommonConstant.SESSION_WXCODE_KEY)!=null;
    }

    public static boolean setSessionLogoutCode() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        session.setAttribute(CommonConstant.SESSION_WXCODE_KEY, "1");
        return true;
    }
    public static boolean hasSessionLogoutCode() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        return session.getAttribute(CommonConstant.SESSION_WXCODE_KEY)!=null;
    }

    /**
     * 获取用户信息
     * @auther franlin.wu
     * @return
     */
    public static TopAccountJx getSessionTopAccountJx() {
        Subject currentUser = SecurityUtils.getSubject();

        if(null == currentUser || null == currentUser.getSession()) {
            return null;
        }

        return (TopAccountJx)currentUser.getSession().getAttribute(CommonConstant.SESSION_KEY);
    }

    /**
     * 清空用户信息
     * @auther franlin.wu
     * @return
     */
    public static void clearSessionTopAccountJx(HttpServletResponse responce) {
        Subject currentUser = SecurityUtils.getSubject();

        if(null != currentUser && null != currentUser.getSession()) {
            currentUser.getSession().setAttribute(CommonConstant.SESSION_KEY,null);
        }

        Cookie c1 = new Cookie(CommonConstant.MJX_WECHAT_OPENID, "");
        c1.setPath("/");
        c1.setMaxAge(0);
        responce.addCookie(c1);

    }
}
