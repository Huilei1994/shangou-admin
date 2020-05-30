package com.hl.shangou.config.webmvc;

import com.hl.shangou.config.interceptor.ShangouInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * creator：Administrator
 * date:2019/10/17
 */

/**
 * webMVC的配置类
 * 这个就是MVC核心接口：能够配置拦截器，能够配置跨域访问，还能配置静态资源，还能配置很多东西
 */
@SpringBootConfiguration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    ShangouInterceptor shangouInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(shangouInterceptor).addPathPatterns("/pages/back/**");
    }

    /**
     * 配置跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }


    /**
     * 配置项目虚拟路径映射到磁盘真正的路径
     * 也就是图片或者文件会被映射到磁盘路径。
     *
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
        // 你要代理的静态资源路径（虚拟路径），也就是说项目中如果出现此（虚拟路径）路径，那么它会映射指向到真正的磁盘路径
//        /upload/** 这个虚拟路径将会被映射到，
//        默认 肯定是linux磁盘路径
        String docPath = getConfigUploadPath();// 磁盘路径,映射磁盘路径
        logger.info("图片上传路径为：" + docPath);
        // 凡是以/upload/开头的静态资源，将都会被映射到磁盘路径里边去
        registry.addResourceHandler("/upload/**").addResourceLocations(docPath);
    }

    public static final String WINDOW_UPLOAD_PATH = "N:\\";
    public static final String LINUX_UPLOAD_PATH = "/";
    public static final String WINDOW_CONFIG_UPLOAD_PATH = "file:N:\\upload\\";
    public static final String LINUX_CONFIG_UPLOAD_PATH = "file:/upload/";


    /**
     * 获取上传文件路径配置的方法
     *
     * @return
     */
    public static String getConfigUploadPath() {
        String realPath = LINUX_CONFIG_UPLOAD_PATH;// 写死。也就是规定好，这些文件就放到哪里去.默认情况是Linux系统
        String systemType = System.getProperty("os.name");// 获取系统的类别, Window
        if (systemType.toLowerCase().contains("windows")) {// 此时是windows系统
            realPath = WINDOW_CONFIG_UPLOAD_PATH;
        }
        return realPath;
    }

    /**
     * 获取上传文件路径的方法:根据操作系统获取
     * 保存磁盘路径
     *
     * @return
     */
    public static String getUploadPath() {
        String realPath = LINUX_UPLOAD_PATH;// 写死。也就是规定好，这些文件就放到哪里去
        String systemType = System.getProperty("os.name");// 获取系统的类别, Window 10
        if (systemType.toLowerCase().contains("windows")) {// 此时是windows系统
            realPath = WINDOW_UPLOAD_PATH;
        }
        return realPath;
    }

}
