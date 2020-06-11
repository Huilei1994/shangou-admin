package com.hl.shangou.service.impl;

import com.hl.shangou.config.webmvc.WebMvcConfig;
import com.hl.shangou.dao.GoodsDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.pojo.vo.GoodsVO;
import com.hl.shangou.service.GoodsService;
import com.hl.shangou.service.ImgCacheService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (goods.getIsCoupon() == null) {
            goods.setIsCoupon(false);
        }
        if (goods.getOnSale() == null) {
            goods.setOnSale(false);
        }
        if (goods.getTakeaway()==null){
            goods.setTakeaway(true);
        }


        //删除缓存的图片信息(自己写的)
        /*imgCacheService.deleteImgCache(goods);
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
        }*/

        // 删除缓存图片
        deleteImgCache(goods);

        int i = goodsDao.insertSelective(goods);
        if (i == 1) {
            return ResponseDTO.ok("添加成功");
        }
        return ResponseDTO.fail("添加失败");
    }

    //也要图片清除缓存
    @Override
    public ResponseDTO ajaxUpdateGoods(Goods goods) {

        Goods oldGoods = goodsDao.selectByPrimaryKey(goods.getGoodsId());

        //主图只有一张，就直接删除
        if (oldGoods.getPic().equals(goods.getPic())) {
            WebMvcConfig.deleteFile(oldGoods.getPic());
        }

        //轮播图
        if (!(oldGoods.getImgs().equals(goods.getImgs()))) {
            /*String[] oldGoodsImgs = oldGoods.getImgs().split(",");
            String[] goodsImgs = goods.getImgs().split(",");*/
            //StringUtils.collectionToCommaDelimitedString();

            List<String> oldGoodsImgs = stringToList(oldGoods.getImgs());
            List<String> oldGoodsImgs1=stringToList(oldGoods.getImgs());
            List<String> newGoodsImgs = stringToList(goods.getImgs());

            //取并集
            oldGoodsImgs.retainAll(newGoodsImgs);

            if (oldGoodsImgs.size()>0) {
                //删除并集
                oldGoodsImgs1.removeAll(oldGoodsImgs);
                newGoodsImgs.removeAll(oldGoodsImgs);
                //删除剩下的图片
                if (oldGoodsImgs1.size()>0) {
                    for (String s : oldGoodsImgs1) {
                        WebMvcConfig.deleteFile(s);
                    }
                }
                //删除新增加的图片缓存
                if (newGoodsImgs.size()>0) {
                    for (String newGoodsImg : newGoodsImgs) {
                        imgCacheService.deleteByPrimaryKey(newGoodsImg);
                    }
                }
            }else {
                for (String s : oldGoodsImgs) {
                    WebMvcConfig.deleteFile(s);
                }
                for (String s : newGoodsImgs) {
                    imgCacheService.deleteByPrimaryKey(s);
                }
            }

        }

        if (!(oldGoods.getDetail().equals(goods.getDetail()))) {

            List<String> oldGoodsDetailStr = imgCacheService.getImgStrToList(oldGoods.getDetail());
            List<String> oldGoodsDetailStr1 = imgCacheService.getImgStrToList(oldGoods.getDetail());
            List<String> newGoodsDetailStr = imgCacheService.getImgStrToList(goods.getDetail());

            //取并集
            oldGoodsDetailStr.retainAll(newGoodsDetailStr);
            if (oldGoodsDetailStr.size()>0) {
                //删除并集
                oldGoodsDetailStr1.removeAll(oldGoodsDetailStr);
                newGoodsDetailStr.removeAll(oldGoodsDetailStr);

                //删除剩下的图片
                if (oldGoodsDetailStr1.size()>0) {
                    for (String s : oldGoodsDetailStr1) {
                        WebMvcConfig.deleteFile(s);
                    }
                }
                //删除新增加的图片缓存
                if (newGoodsDetailStr.size()>0) {
                    for (String s : newGoodsDetailStr) {
                        imgCacheService.deleteByPrimaryKey(s);
                    }
                }
            }else {
                for (String s : oldGoodsDetailStr) {
                    WebMvcConfig.deleteFile(s);
                }
                for (String s : newGoodsDetailStr) {
                    imgCacheService.deleteByPrimaryKey(s);
                }
            }
        }
        int i = goodsDao.updateByPrimaryKeySelective(goods);
        if (i == 1) {
            return ResponseDTO.ok("修改成功");
        }
        return ResponseDTO.fail("修改失败");
    }


    //查看商户自己的商品
    @Override
    public PageDTO ajaxMerchantList(GoodsQuery query) {
        List<GoodsVO> goodsVOS = goodsDao.ajaxMerchantList(query);
        Integer count = goodsDao.ajaxMerchantCount(query);

        return PageDTO.setPageData(count,goodsVOS);


    }
}
