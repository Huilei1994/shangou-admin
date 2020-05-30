package com.hl.shangou.config.exception;
/**
 * creator：杜夫人
 * date: 2020/5/28
 */

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AuthorizationException.class)
    String handUnAuthException(Exception e, Model model) {
        model.addAttribute("errorMsg", e.getMessage());
        return "unAuthPage";
    }
}
