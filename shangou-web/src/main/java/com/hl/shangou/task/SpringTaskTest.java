package com.hl.shangou.task;

import com.hl.shangou.config.webmvc.WebMvcConfig;
import com.hl.shangou.dao.ImgCacheDao;
import com.hl.shangou.pojo.entity.ImgCache;
import com.hl.shangou.service.ImgCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * creator：杜夫人
 * date: 2020/5/26
 */
//@Component
public class SpringTaskTest {
    Logger logger = LoggerFactory.getLogger(SpringTaskTest.class);


    @Resource
    ImgCacheService imgCacheService;


    @Scheduled(fixedDelay = 60*60*1000)// 每过1H但是是以方法运行完之后开始算
    public void clearImgCache() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,-1); //拿到一小时之前的时间点
        Date time = instance.getTime(); //转换为时间对象
        List<ImgCache> allImgCache = imgCacheService.getAllImgCache(time);

        //循环删除获取得到图片
        for (ImgCache cache : allImgCache) {
            //删除之前打印删除的日志
            boolean b =WebMvcConfig.deleteFile(cache.getImgUrl());
            logger.info("正在定时删除缓存图片"+cache.getImgUrl()+"结果是"+b);
            if (b) {
                imgCacheService.deleteByPrimaryKey(cache.getImgUrl());
            }

        }

    }

/*
    @Scheduled(cron = "3/4 * * * * ?")
    public void addUser() {// 单线程

        try {
            System.err.println("定时添加用户了哦" + System.currentTimeMillis() / 1000);
            logger.trace("trace级别");
            logger.debug("debug级别");
            logger.info("info级别");
            logger.warn("警告级别");
            logger.error("错误级别");
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 用在微信公众号 dev 刷新js的key 默认存活时间是两小时。
    @Scheduled(fixedRate = 5000)// 每过5000毫秒就执行这个方法，不管你方法执行了多久
    public void task2() {
        // 如果这个方法开始的时候是0秒开始的
        // 执行这个方法竟然花了三秒，请问，下次执行这个方法，时间是多少
        // 是5秒
        System.err.println("fixedRate" + System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 5000)// 每过5000毫秒就执行这个方法，但是是以方法运行完之后开始算
    public void task3() {
        // 如果这个方法开始的时候是0秒开始的
        // 执行这个方法竟然花了三秒，请问，下次执行这个方法，时间是多少
        // 是8秒
        System.err.println("fixedDelay" + System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
