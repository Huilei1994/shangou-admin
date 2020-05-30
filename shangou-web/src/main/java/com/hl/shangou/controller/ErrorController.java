package com.hl.shangou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
public class ErrorController {
    @RequestMapping("/showErrorPage")
    String showErrorPage(Model model, HttpServletRequest request) {
        model.addAttribute("msg", request.getAttribute("msg"));
        return "errorPage";
    }
}
