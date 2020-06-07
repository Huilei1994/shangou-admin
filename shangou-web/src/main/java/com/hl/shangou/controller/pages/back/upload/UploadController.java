package com.hl.shangou.controller.pages.back.upload;


import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.dto.WangEditDTO;
import com.hl.shangou.pojo.entity.ImgCache;
import com.hl.shangou.service.ImgCacheService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;


@RestController
@RequestMapping("/pages/back/upload")
public class UploadController extends BaseController {
//    img1,img2
//    img1

    @Resource
    ImgCacheService imgCacheService;

    /**
     * 上传图片文件
     * @param request
     * @return
     */
    @RequestMapping("uploadFiles")
    ResponseDTO add(MultipartHttpServletRequest request) {
        Collection<MultipartFile> values = request.getFileMap().values();
        StringBuffer buffer = new StringBuffer();

        //默认路径在/upload/下的某个文件
        String uploadPath = "/upload/";
        String dir = request.getParameter("dir");

        //给文件路径附上正确的结尾
        if (!StringUtils.isEmpty(dir)) {
            if (dir.endsWith("/")) {// dir必须按照/结尾
                uploadPath += dir;
            } else {
                uploadPath += dir + "/";//没有结尾我帮你结尾
            }
        }

        //多个图片文件就循环的保存图片并保存到数据库，以便清除其中的缓存
        for (MultipartFile f : values) {
            String s = saveFile(f, uploadPath);
            //判断保存后 返回路径是否为空（空就是没有保存成功，有就是保存成功）
            if (!StringUtils.isEmpty(s)) {
                //保存成功后就要报路径存到数据库
                ImgCache imgCache = new ImgCache();
                imgCache.setCreateTime(new Date());
                imgCache.setImgUrl(s);
                //插入图片信息到数据库
                int insert = imgCacheService.insert(imgCache);
                //拼接每一个传来路径已逗号分隔
                buffer.append(s).append(",");
            }
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        return ResponseDTO.ok("上传成功", buffer.toString());
    }



    @RequestMapping("editUploadFiles")
    WangEditDTO editUploadFiles(MultipartHttpServletRequest request) {
        Collection<MultipartFile> values = request.getFileMap().values();
        StringBuffer buffer = new StringBuffer();

        //默认路径在/upload/下的某个文件
        String uploadPath = "/upload/";
        String dir = request.getParameter("dir");

        //给文件路径附上正确的结尾
        if (!StringUtils.isEmpty(dir)) {
            if (dir.endsWith("/")) {// dir必须按照/结尾
                uploadPath += dir;
            } else {
                uploadPath += dir + "/";//没有结尾我帮你结尾
            }
        }
        //多个图片文件就循环的保存图片并保存到数据库，以便清除其中的缓存
        for (MultipartFile f : values) {
            String s = saveFile(f, uploadPath);
            //判断保存后 返回路径是否为空（空就是没有保存成功，有就是保存成功）
            if (!StringUtils.isEmpty(s)) {
                //保存成功后就要报路径存到数据库
                ImgCache imgCache = new ImgCache();
                imgCache.setCreateTime(new Date());
                imgCache.setImgUrl(s);
                //插入图片信息到数据库
                int insert = imgCacheService.insert(imgCache);
                //拼接每一个传来路径已逗号分隔
                buffer.append(s).append(",");
            }
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        String[] urls = buffer.toString().split(",");
        System.err.println(urls);
        return WangEditDTO.ok(urls);
    }


}
