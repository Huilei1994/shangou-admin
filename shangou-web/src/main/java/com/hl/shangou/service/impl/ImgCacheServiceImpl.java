package com.hl.shangou.service.impl;

import com.hl.shangou.dao.ImgCacheDao;
import com.hl.shangou.pojo.entity.ImgCache;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.util.Common.StringUtil;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
        Class cls = object.getClass();
        //获得声明的属性
        Field[] declaredFields = cls.getDeclaredFields();

        for (Field field : declaredFields) {
            //类型是string
            if (field.getType().isAssignableFrom(String.class)) {
                field.setAccessible(true); //解封
                String value = null;

                try {
                    value=(String)field.get(object);//获取传来的值,并转换为字符串

                    if (!StringUtils.isEmpty(value)) {
                        if (value.startsWith("/upload/")) {
                            imgCacheDao.deleteByPrimaryKey(value); //删除图片缓存信息
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }
}
