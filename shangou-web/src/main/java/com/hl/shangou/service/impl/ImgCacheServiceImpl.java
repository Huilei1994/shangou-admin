package com.hl.shangou.service.impl;

import com.hl.shangou.dao.ImgCacheDao;
import com.hl.shangou.pojo.entity.ImgCache;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.util.Common.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;


@Service
public class ImgCacheServiceImpl implements ImgCacheService {


    @Resource
    ImgCacheDao imgCacheDao;


    //获取一个小时所有图片
    @Override
    public List<ImgCache> getAllImgCache(Date time) {
        List<ImgCache> allImgCache = imgCacheDao.getAllImgCache(time);
        return allImgCache;
    }

    /**
     * 根据url路径来删除数据
     * @param imgUrl
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String imgUrl) {
        return imgCacheDao.deleteByPrimaryKey(imgUrl);
    }

    @Override
    public int insert(ImgCache record) {
        return imgCacheDao.insert(record);
    }

    @Override
    public int insertSelective(ImgCache record) {
        return imgCacheDao.insertSelective(record);
    }

    @Override
    public ImgCache selectByPrimaryKey(String imgUrl) {
        return imgCacheDao.selectByPrimaryKey(imgUrl);
    }

    @Override
    public int updateByPrimaryKeySelective(ImgCache record) {
        return imgCacheDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ImgCache record) {
        return imgCacheDao.updateByPrimaryKey(record);
    }


    /**
     * 反射获取得到对象属性
     * 并根据属性值获取图片路径
     * 然后清除缓存信息
     * @param object
     * @return
     */
    @Override
    public boolean deleteImgCache(Object object)  {

        Class cls = object.getClass();// 获取反射类对象
        Field[] declaredFields = cls.getDeclaredFields();// 拿到本类 的所有成员
        for (Field f : declaredFields) {
            if (f.getType().isAssignableFrom(String.class)) {// 如果当前成员是String这个类，那么我就把值取出来
                f.setAccessible(true);// 暴力解封
                String value = null;// 可以强制转换不会错
                if (!StringUtils.isEmpty(value)) {// 不是空，就判断是否是/upload/开头的，如果是，就认为是图片。就去缓存里边删缓存
                    if (value.contains("<img")) {// 这样判断这个是富文本
                        List<String> imgStrToList = getImgStrToList(value);// 从文本提取图片地址
                        for (String s : imgStrToList) {
                            if (s.startsWith("/upload/")) {// 就是图片
                                imgCacheDao.deleteByPrimaryKey(s);
                            }
                        }
                    }
                    if (value.startsWith("/upload/")) {// 就是图片
                        String[] split = value.split(",");
                        for (String s : split) {
                            imgCacheDao.deleteByPrimaryKey(s);
                        }
                    }
                }
            }
        }
        return true;
    }
}
