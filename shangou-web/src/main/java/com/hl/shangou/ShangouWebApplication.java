package com.hl.shangou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan(basePackages = {"com.hl.shangou.dao"}) // dao扫描包路径
@EnableScheduling  // 开启定时任务
@EnableTransactionManagement  // 开启事务控制
public class ShangouWebApplication {

    Logger logger= LoggerFactory.getLogger(ShangouWebApplication.class);

    @PostConstruct
    void setTimezone() {
        logger.warn("服务器Timezone默认时区是：" + TimeZone.getDefault().getID());
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ShangouWebApplication.class, args);
    }

}
