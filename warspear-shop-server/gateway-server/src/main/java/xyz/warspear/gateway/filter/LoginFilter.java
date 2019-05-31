package xyz.warspear.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.warspear.entity.po.User;
import xyz.warspear.gateway.domain.UserInRds;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.utils.JWTUtils;
import xyz.warspear.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 * 在这里拦截登录请求
 */
public class LoginFilter extends ZuulFilter {
    @Autowired
    UserRepository userRepository;
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
    /**
     * 匹配登录url
     */
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        return requestURI.equals("/api/auth/login");
    }

    @Override
    @Transactional
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        //通知其他过滤器已经进行处理
        currentContext.set("processed");
        //不进行路由
        currentContext.setSendZuulResponse(false);
        //若不是post提交的返回错误信息
        if (!request.getMethod().equals("POST")) {
            throw new ZuulException("", 405, "请求类型错误");
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //参数为空抛异常
        if (username == null || password == null || username.equals("") || password.equals(""))
            throw new ZuulException("", 401, "没有获取到登录信息");
        //从数据库拿user信息，没有就抛异常
        User user = userRepository.findByUsername(username);
        if (user == null) {
            //有可能是邮箱
            user = userRepository.findByEmail(username);
            if (user == null)
                throw new ZuulException("", 401, "用户不存在");
        }
        //前台传过来的可能是邮箱，所以要用数据库中的username签发token
        String usernameInDB = user.getUsername();
        String encodePassword = DigestUtils.md5Hex(usernameInDB + password);
        if (!encodePassword.equals(user.getPassword())) {
            throw new ZuulException("", 401, "密码错误");
        }
        //签发token
        String token = JWTUtils.creat(usernameInDB, password);
        currentContext.setResponseStatusCode(200);
        response.addHeader("token", token);
        //加入缓存
        UserInRds userInRds = new UserInRds(user, password);
        redisUtils.set(usernameInDB, userInRds, 60 * 30);
        return null;
    }
}
