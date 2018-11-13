package acme.me.common.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:38
 * @Description: microservice-view
 */
public abstract class BaseAction {

    public final String getCookeValue(Cookie[] cookies, String cookieKey) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieKey)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public final String getRequestParameter(HttpServletRequest request, String paramKey) {
        return request.getParameter(paramKey);
    }

    public final Cookie addOrUpdateCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
        return cookie;
    }

}
