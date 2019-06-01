package xyz.warspear.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.warspear.gateway.domain.UserInRds;
import xyz.warspear.utils.JWTUtils;
import xyz.warspear.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthorizationFilter extends ZuulFilter {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.matches("/api/auth/images/captcha/[0-9]*"))
            return false;
        if (requestURI.matches("/api/auth/user") || request.getMethod() == "POST")
            return false;
        if (requestURI.matches("/api/auth/verify"))
            return false;
        if (requestURI.matches("/api/auth/checkUsername"))
            return false;
        if (requestURI.matches("/api/item/items/page/[0-9]*/size/[0-9]*"))
            return false;
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        //如果已经有其他过滤器介入处理了，直接结束方法
        if (currentContext.get("processed") != null)
            return null;
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        String token = request.getHeader("token");
        //未携带token
        if (token == null || token.equals(""))
            throw new ZuulException("", 402, "请求头中没有token");
        String username = JWTUtils.getUsername(token);
        //token不合法
        if (username == null)
            throw new ZuulException("", 402, "不合法的token");
        UserInRds user = (UserInRds) redisUtils.get(username);
        if (user == null)
            throw new ZuulException("", 402, "登录已过期或已退出登录");
        //token过期
        if (JWTUtils.verify(token, username, user.getPassword())){
            token = JWTUtils.creat(username, user.getPassword());
            response.addHeader("token", token);
        }
        //匹配uri
        String requestURI = request.getRequestURI();
        boolean flag = false;
        List<String> permissions = user.getPermissions();
        for (String permission : permissions) {
            if (requestURI.matches(permission)){
                flag = true;
            }
        }
        if(flag)
            currentContext.setSendZuulResponse(true);
        else{
            currentContext.setSendZuulResponse(false);
            throw new ZuulException("", 401, "没有权限");
        }
        //刷新缓存
        redisUtils.expire(username,60 * 30);
        return null;
    }
}
