package com.hl.shangou.controller;

import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
public class LoginController extends BaseController{


    @RequestMapping("/login")
    @ResponseBody
    ResponseDTO login(UserVO userVO) {// 这个方法是执行登录操作的


        UsernamePasswordToken usernamePasswordToken;
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

            //UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("18223170162", "123456");

        }


        return ResponseDTO.ok("登录成功");
    }

    // 这个方法是跳转到登录页面用的
    @RequestMapping("/loginPage")
    String loginPage() {
        return "loginPage";
    }



    // 这个方法是跳转到登录成功页面用的
    @RequestMapping("/loginSuccess")
    String loginSuccess() {

        return "pages/back/home";
    }



}
