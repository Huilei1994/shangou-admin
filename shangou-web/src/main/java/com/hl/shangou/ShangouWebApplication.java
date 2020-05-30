package com.hl.shangou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.hl.shangou.dao"}) // dao扫描包路径
@EnableScheduling  // 开启定时任务
@EnableTransactionManagement  // 开启事务控制
public class ShangouWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShangouWebApplication.class, args);
    }

}
