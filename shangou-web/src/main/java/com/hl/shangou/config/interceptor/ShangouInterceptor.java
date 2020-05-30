package com.hl.shangou.config.interceptor;

import com.hl.shangou.util.param.ValidateParamUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * creator：杜夫人
 * date: 2020/5/19
 */
@Component
public class ShangouInterceptor implements HandlerInterceptor {

    @Resource
    ValidateParamUtil validateParamUtil;

    // 在进入控制器之前进行拦截：前
    // handler里边包含的信息有：你即将要去访问的控制器（controller类），以及你即将要访问的方法（指顶的requestMapping）
    // 既然有了类，有了方法，那么我们就可以利用反射，对这个方法所需要的合格的参数进行验证
    // 思路：首先，定义一个专门的属性（验证）文件：在这个验证文件里边 定义 类型为： 某个类.某个方法需要什么验证=属性:正则表达式|属性2:正则表达式2....
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        ValidateParamUtil validateParamUtil = SpringUtil.getBean(ValidateParamUtil.class);
        Boolean x = validateParamUtil.getBoolean(request, response, handler);
        if (x != null) return x;
        return true;// 如果这里返回false,表示拦截，只有return true才会放行
    }

    // 请求之后进行拦截：中
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.err.println("请求正在执行");

        // Access-Control-Allow-Origin, 这是拦截器实现跨域请求的配置方式
//        String origin = request.getHeader("Origin");
//        System.out.println("origin.........."+origin);
//
//        response.setHeader("Access-Control-Allow-Origin", origin);
//        response.setHeader("Vary", "Origin");
//
//        // Access-Control-Max-Age
//        response.setHeader("Access-Control-Max-Age", "3600");
//
//        // Access-Control-Allow-Credentials
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Access-Control-Allow-Methods
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//
//        // Access-Control-Allow-Headers
//        response.setHeader("Access-Control-Allow-Headers",
//                "Origin, X-Requested-With, Content-Type,Accept,X-Custom-Header,Set-Cookie");

    }

    // 控制器执行完成之后，再次拦截。后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.err.println("请求执行完毕");
    }

}
