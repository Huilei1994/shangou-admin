package com.hl.shangou.util.param;


import cn.hutool.core.util.ReUtil;
import com.hl.shangou.util.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * creator：杜夫人
 * date: 2020/5/19
 */
@Component
public class ValidateParamUtil {// 在父容器里边的

    /**
     * 这个结果 res表示验证的结果，msg表示消息，url表示跳转的路径
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public Boolean getBoolean(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            String clsName = h.getBeanType().getSimpleName();// 简单类名
            String mName = h.getMethod().getName();// 方法名称
            String key = "validate." + clsName + "." + mName;
            String message = getMsg(key);
            Map<String, Object> map = new HashMap<>();
            boolean res = true;// 默认是全部验证是通过的
            if (StringUtils.isEmpty(message)) {
                return true;// 直接方向，根本不需要拦截
            } else {// 需要拦截
                String[] split = message.split("\\|"); // [oldPrice:MONEY,nowPrice:MONEY,phone:MOBILE]
                for (String s : split) {
                    String[] split1 = s.split(":");
                    String paramName = split1[0];// 参数名称
                    String rule = split1[1];// 参数的规则
                    Pattern pattern = getPatternByName(rule);

                    String paramValue = request.getParameter(paramName);// 根据参数名称获取参数值
                    // false,单独的一个小项的验证结果
                    boolean matchRegex = isMatch(pattern, paramValue);// 这个就是验证参数值是否符合正则表达式

                    if (!matchRegex) {// 只要有一个匹配失败的时候
                        res = false;// 就改变flag的值，也就是说整个验证不能通过
                        map.put("msg", "参数" + paramName + "的值是" + paramValue + "不符合正则验证，规则是" + rule);
                        break;// 跳出循环
                    }
                }
                map.put("res", res);
            }
            boolean r = (boolean) map.get("res");
            if (r) {// 验证通过
                return true;
            } else {
                Object msg = map.get("msg");// 把错误消息取出来
                request.setAttribute("msg", msg);
                // 可以写死，也可以进行配置

                // 先去获取这个方法对应的错误页面路径
                String url = getMsg(key + ".errorUrl");
                if (StringUtils.isEmpty(url)) {
                    url = getMsg("error.url");
                }
                request.getRequestDispatcher(url).forward(request, response);
                return false;
            }
            // 现在有了key，就需要一个资源读取类
        }
        return null;
    }

    private Pattern getPatternByName(String patterName) {
        if (!StringUtils.isEmpty(patterName)) {
            switch (patterName) {// 这里会空指针
                case "MOBILE":
                    return Pattern.compile("(?:0|86|\\+86)?1[3456789]\\d{9}");

                case "GENERAL":
                    return Pattern.compile("^\\w+$");
                case "WORD":
                    return Pattern.compile("[a-zA-Z]+");

                case "CHINESE":
                    return Pattern.compile(ReUtil.RE_CHINESE);
                /// ....此处省略其他的验证，自己写上去

                default:
                    return Pattern.compile(patterName);// 如果一个都没有匹配成功，说明，这个正则可能是你自己写的，所以我们如果要做得更好
            }
        }
        return null;
    }

    public boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

    private String getMsg(String key) {
        String message = null;
        try {
            MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
            message = messageSource.getMessage(key, null, Locale.getDefault());
        } catch (NoSuchMessageException e) {
//            e.printStackTrace();
            return null;
        }
        return message;
    }
}
