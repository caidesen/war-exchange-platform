package xyz.warspear.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class CorsPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //预检请求不进入过滤器
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.debug("*****************PostFilter run start*****************");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        HttpServletRequest request = currentContext.getRequest();
        //设置源站
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        //允许游览器访问几个头
        response.setHeader("Access-Control-Expose-Headers","X-forwared-port, X-forwarded-host, token");
        //如果服务端指定了具体的域名而非“*”，那么响应首部中的 Vary 字段的值必须包含 Origin
        response.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        currentContext.setSendZuulResponse(true);
        log.debug("*****************PostFilter run end*****************");
        return null;
    }
}
