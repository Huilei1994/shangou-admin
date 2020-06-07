package com.hl.shangou.controller;

import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
public class LoginController extends BaseController{



    // 退出App跳转到登录主界面
    @RequestMapping("/logoutApp")
    String logoutApp() {
        //退出
        SecurityUtils.getSubject().logout();
        return "loginPage";
    }

    //06-03添加了是否是前后台登录
    @RequestMapping("/login")
    @ResponseBody
    ResponseDTO login(UserVO userVO,boolean isBack) {// 这个方法是执行登录操作的
        UsernamePasswordToken usernamePasswordToken;
        if (!isBack) {// 就应该设置一个标记
            if (!StringUtils.isEmpty(userVO.getCode())) {// 就代表是验证码登录或者注册
                getSession().setAttribute("isBack", false);// 不是后台登录
            }
        }
        // 获取subject
        if (userVO != null) {
            String phone = userVO.getPhone();
            String password = userVO.getPassword();
            String code = userVO.getCode();
            Subject subject = SecurityUtils.getSubject();
            if (code == null ||code=="") {
                 usernamePasswordToken = new UsernamePasswordToken(phone, password);
            }else {
                //将前端传来的验证码存到session中
                getSession().setAttribute("code",code);
                 usernamePasswordToken = new UsernamePasswordToken(phone, code);
            }
            try {
                subject.login(usernamePasswordToken);// 只要 执行login方法，那么它就会跑到userRealm里边的认证方法（doGetAuthenticationInfo）
            } catch (AuthenticationException a) {
                return ResponseDTO.fail("登录失败");
            }
        }
        return ResponseDTO.ok("登录成功");
    }

    // 这个方法是跳转到登录页面用的 判断是否是后台登录
    @RequestMapping("/loginPage")
    String loginPage(HttpServletRequest request, boolean isBack) {
        // 就应该判断以下 是来自于哪个访问路径的
        if (isBack) {
            return "loginPage";
        }

        // 不是后台登录，就是前端登录
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);//shiro保存拦截之前的请求对象
        if (savedRequest != null) {
        //String queryString = savedRequest.getQueryString();// 获取参数字符
            if ("/pages/back/merchant/addPre".equals(savedRequest.getRequestURI())) {// 证明此时shiro拦截的是客户端
                return "client-loginPage";
            }
        }

        return "client-loginPage";
    }


    //新增前台用户登录注册
    // 这个方法是跳转到登录成功页面用的
    @RequestMapping("/loginSuccess")
    String loginSuccess(boolean isBack, HttpServletRequest request) {

        //后台直接返回后台成功主界面
        if (isBack) return "pages/back/home";

        //判断之前是否有被拦截的url
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);

        if (savedRequest != null) {
            //说明是在前台摸个界面要求登录
            // 获取参数字符串,有被拦截的路径，就跳转回拦截之前的那个路径
            String queryString = savedRequest.getQueryString();
            if (queryString == null) {
                return "redirect:" + savedRequest.getRequestUrl();
            }
            //直接跳回被拦截的路径+参数
            return "redirect:"+savedRequest.getRequestUrl()+"?"+queryString;
        }



        //登录就跳转到我的界面
        return "pages/back/client/my-info";// 应该跳转到客户端我的界面
    }



    // 登录后显示的主界面
    @RequestMapping("/pages/back/dashBoard")
    String dashBoard() {
        return "pages/back/dashboard/dashboard";//应该跳转到客户端我的界面
    }




}
