package com.hl.shangou.service.impl;

import com.hl.shangou.dao.GoodsDao;
import com.hl.shangou.dao.ImgCacheDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.pojo.vo.GoodsVO;
import com.hl.shangou.service.GoodsService;
import com.hl.shangou.service.ImgCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsDao goodsDao;

    @Resource
    ImgCacheService imgCacheService;

    @Override
    public int deleteByPrimaryKey(Long goodsId) {
        return goodsDao.deleteByPrimaryKey(goodsId);
    }

    @Override
    public int insert(Goods record) {
        return goodsDao.insert(record);
    }

    @Override
    public int insertSelective(Goods record) {
        return goodsDao.insertSelective(record);
    }

    @Override
    public Goods selectByPrimaryKey(Long goodsId) {
        return goodsDao.selectByPrimaryKey(goodsId);
    }

    @Override
    public int updateByPrimaryKeySelective(Goods record) {
        return goodsDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Goods record) {
        return goodsDao.updateByPrimaryKey(record);
    }

    @Override
    public PageDTO ajaxList(GoodsQuery query) {
        List<GoodsVO> goodsVOS = goodsDao.ajaxList(query);
        Integer count = goodsDao.ajaxListCount(query);

        return PageDTO.setPageData(count,goodsVOS);
    }

    @Override
    public ResponseDTO ajaxDeleteGoods(Long goodsId) {
        int i = goodsDao.deleteByPrimaryKey(goodsId);
        if (i == 1) {
            return ResponseDTO.ok("删除成功");
        }
        return ResponseDTO.fail("删除失败");
    }


    @Override
    public ResponseDTO ajaxAdd(Goods goods) {

        //删除缓存的图片信息
        imgCacheService.deleteImgCache(goods);
        //删除详细信息里面的图片缓存信息
        String detailStr = goods.getDetail();
        if ( detailStr != null && detailStr != "") {
            String[] split = detailStr.split("<img src=\"");
            for (String s : split) {
                int index=s.indexOf("\"");
                if (index > 0) {
                    String uploadString = s.substring(0, s.indexOf("\""));
                    if (uploadString != null) {
                        if (uploadString.startsWith("/upload/")) {
                            imgCacheService.deleteByPrimaryKey(uploadString);
                        }
                    }
                }
            }
        }
        int i = goodsDao.insertSelective(goods);
        if (i == 1) {
            return ResponseDTO.ok("添加成功");
        }
        return ResponseDTO.fail("添加失败");
    }

    @Override
    public ResponseDTO ajaxUpdateGoods(Goods goods) {
        int i = goodsDao.updateByPrimaryKeySelective(goods);
        if (i == 1) {
            return ResponseDTO.ok("修改成功");
        }
        return ResponseDTO.fail("修改失败");
    }
}
