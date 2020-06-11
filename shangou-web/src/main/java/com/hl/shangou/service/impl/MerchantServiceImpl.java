package com.hl.shangou.service.impl;

import com.hl.shangou.dao.GoodsDao;
import com.hl.shangou.dao.GoodsTypeDao;
import com.hl.shangou.dao.MerchantDao;
import com.hl.shangou.enums.ApprovalEnum;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;
import com.hl.shangou.pojo.vo.GoodsVO;
import com.hl.shangou.pojo.vo.MerchantVO;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    MerchantDao merchantDao;

    @Resource
    ImgCacheService imgCacheService;

    @Resource
    GoodsDao goodsDao;


    @Resource
    GoodsTypeDao goodsTypeDao;


    @Override
    public ResponseDTO ajaxAddMerchant(MerchantVO merchantVO) {


        if (merchantVO.getPcd() != null) {
            //获取省份
            String pcd = merchantVO.getPcd();
            String[] split = pcd.split("-");
            merchantVO.setProvince(split[0]);

            //设置必要默认参数
            merchantVO.setMerchantId(System.currentTimeMillis());
            merchantVO.setCreateTime(new Date());
            merchantVO.setUpdateTime(new Date());
            merchantVO.setMaxDeliveryArea(3000.0);
            //设置的当前入驻审批状态
            merchantVO.setApprovalStatus(ApprovalEnum.审核中.toString());

            int i = merchantDao.ajaxAddMerchant(merchantVO);

            //成功添加之后需要清除缓存图片
            if (i==1) {
                //清除图片信息
                imgCacheService.deleteImgCache(merchantVO);

            }

            return ResponseDTO.ok("申请成功");

        }
        return ResponseDTO.fail("申请失败");
    }

    /**
     * 判断是否有商户
     * @param UserId
     * @return
     */
    @Override
    public boolean selectByMerchantUserId(Long UserId) {
        return merchantDao.selectByMerchantUserId(UserId)!=null;
    }


    /**
     * 返回商户详细信息
     * @param UserId
     * @return
     */
    @Override
    public Merchant selectMerchantByUserId(Long UserId) {
        return merchantDao.selectByMerchantUserId(UserId);
    }

    @Override
    public Long selectMerchantIdByUserId(Long userId) {
        Merchant merchant = merchantDao.selectByMerchantUserId(userId);
        return merchant.getMerchantId();
    }



    /**
     * 附近的商家
     * @param query
     * @return
     */
    @Override
    public PageDTO getNearbyMerchantsGoods(MerchantQuery query) {

        //查到附近的商家
        List<MerchantVO> merchants = merchantDao.ajaxListMerchantVO(query);//从集合里边剔除元素，必须使用iterator

        // 查询出来之后，就查询商户卖得最好的商品，就是销量最多的商品
        if (!CollectionUtils.isEmpty(merchants)) {// 如果是空集，要报错，所以必须判断不是空集才能mybatis的 for循环

            //最好的商品也可能是个集合（销售数量最大且相同）
            List<GoodsVO> goodsVOS = goodsDao.getMerchantBestGoods(merchants);

            Map<Long, List<GoodsVO>> collect = goodsVOS.stream().collect(Collectors.groupingBy(GoodsVO::getMerchantId));


            Iterator<MerchantVO> iterator = merchants.iterator();// 迭代
            while (iterator.hasNext()) {// 还有下一个元素
                MerchantVO next = iterator.next();// 取出来商户
                List<GoodsVO> goodsVOS1 = collect.get(next.getMerchantId());

                if (CollectionUtils.isEmpty(goodsVOS1)) {// 这个商家压根没有最好的商品，直接放弃这个商家
                    iterator.remove();// 从集合里边移除这个商家
                } else {
                    next.setBestGoods(goodsVOS1.get(0));// 不管有多少个卖得相同数量最好的，都只取一个
                }
            }
        }
        Integer count = merchantDao.ajaxListCount(query);
        return PageDTO.setPageData(count, merchants);
    }


    /**
     * 还需要带上商户的所有商品类型
     * 索性把商品类型查出来之后，把商品也查出来，这样一口气查出来传到前端，免费前端再次发ajax来服务器请求数据，一个店铺的全部商品数据也不会太多
     * @param merchantId
     * @return
     */
    @Override
    public MerchantVO selectMerchantById(Long merchantId) {
        // 1查询商户基本字段
        MerchantVO merchantVO = merchantDao.selectByPK(merchantId);
        // 根据商户id查询商户的所有 商品类型
        List<GoodsTypeVO> goodsTypeVOS = goodsTypeDao.selectByMerchantId(merchantId);



        if (!CollectionUtils.isEmpty(goodsTypeVOS)) {
            // 3、根据商品类型的集合批量查询商品
            List<GoodsVO> g = goodsDao.selectGoodsByTypes(goodsTypeVOS);
            // 按照商品类型分组
            Map<Long, List<GoodsVO>> collect = g.stream().collect(Collectors.groupingBy(GoodsVO::getGoodsTypeId));

            for (GoodsTypeVO t : goodsTypeVOS) {
                t.setGoodsVOS(collect.get(t.getGoodsTypeId()));// 设置这种商品类型的商品
            }
            merchantVO.setGoodsTypeList(goodsTypeVOS);// 可以！java堆内存 改变
        }
        return merchantVO;
    }


    @Override
    public PageDTO ajaxList(MerchantQuery query) {
        List<Merchant> merchantList=merchantDao.ajaxList(query);
        int count=merchantDao.ajaxSelectCount(query);

        return PageDTO.setPageData(count,merchantList);
    }

    @Override
    public ResponseDTO ajaxCheckState(Merchant merchant) {
        int i = merchantDao.updateByPrimaryKeySelective(merchant);
        if (i == 1) {
            return ResponseDTO.ok("成功");
        }
        return ResponseDTO.fail("失败");
    }
}
