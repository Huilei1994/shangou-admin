package com.hl.shangou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
public class IndexController {
    @RequestMapping("index")
    String index() {
        return "index";
    }
}
