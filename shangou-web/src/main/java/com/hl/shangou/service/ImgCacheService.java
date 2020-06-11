package com.hl.shangou.service;

import com.hl.shangou.pojo.entity.ImgCache;
import com.hl.shangou.pojo.vo.MerchantVO;

import java.util.Date;
import java.util.List;

public interface ImgCacheService extends BaseService{

    List<ImgCache> getAllImgCache(Date time);

    int deleteByPrimaryKey(String imgUrl);

    int insert(ImgCache record);

    int insertSelective(ImgCache record);

    ImgCache selectByPrimaryKey(String imgUrl);

    int updateByPrimaryKeySelective(ImgCache record);

    int updateByPrimaryKey(ImgCache record);

    boolean deleteImgCache(Object object);
}
