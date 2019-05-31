package xyz.warspear.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.warspear.utils.JWTUtils;
import xyz.warspear.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutFilter extends ZuulFilter {
    @Autowired
    RedisUtils redisUtils;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        return requestURI.equals("/api/auth/logout");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        //通知其他过滤器已经进行处理
        currentContext.set("processed");
        //不进行路由
        currentContext.setSendZuulResponse(false);
        String token = request.getHeader("token");
        //没有携带token就抛异常
        if (token == null||token.equals(""))
            throw new ZuulException("", 401, "未登录！");
        String username = JWTUtils.getUsername(token);
        //缓存中没有
        if(redisUtils.hasKey(username))
            throw new ZuulException("", 200, "登录已经失效");
        //删除缓存
        redisUtils.del(username);
        currentContext.setResponseStatusCode(200);
        return null;
    }
}
