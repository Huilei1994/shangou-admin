package com.hl.shangou.service.impl;

import com.hl.shangou.dao.MerchantDao;
import com.hl.shangou.enums.ApprovalEnum;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.MerchantVO;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    MerchantDao merchantDao;

    @Resource
    ImgCacheService imgCacheService;


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
