package com.hl.shangou.controller;

import com.hl.shangou.config.webmvc.WebMvcConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * creator：杜夫人
 * date: 2020/5/20
 */

public class BaseController {
    protected String saveFile(MultipartFile f, String uploadPath) {// 很多控制器里边都可能会有保存文件的操作
        if (f != null && !f.isEmpty()) {
            if (f.getSize() > 0) {
                String uid = UUID.randomUUID().toString();// 文件名称不会重复
                String originalFilename = f.getOriginalFilename();// 获取原始文件的名称
                String s = originalFilename.substring(originalFilename.lastIndexOf("."));// 获取文件后缀名称
                String fileName = uid + s;// 文件的名称（包含文件后缀）
                String realPath = getRequest().getServletContext().getRealPath("/");// 这个才是项目发布的磁盘目录

                String s1 = WebMvcConfig.getUploadPath();// 磁盘任意路径

                File file = new File(s1 + uploadPath + fileName);// 需要指定上文的保存目录路径
                if (!file.getParentFile().exists()) {// 如果父目录不存在
                    file.getParentFile().mkdirs();// 就多级创建父目录
                }
                try {
                    f.transferTo(file);
                    return uploadPath + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
