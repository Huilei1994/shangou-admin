package com.hl.shangou.service.impl;

import com.hl.shangou.dao.BusinessTypeDao;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.vo.BusinessTypeVO;
import com.hl.shangou.service.BusinessTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Resource
    BusinessTypeDao businessTypeDao;


    @Override
    public List<BusinessTypeVO> selectAllBusinessType() {


        List<BusinessTypeVO> businessTypeVO = businessTypeDao.selectAllBusinessType();

        List<BusinessTypeVO> newBusinessTypeVOS = businessTypeVO.stream().filter(new Predicate<BusinessTypeVO>() {
            @Override
            public boolean test(BusinessTypeVO businessTypeVO) {
                return businessTypeVO.getParentId() == 0;
            }
        }).collect(toList());

        System.err.println(newBusinessTypeVOS);



        //设置值
        if (newBusinessTypeVOS != null) {
            for (BusinessTypeVO typeVO : newBusinessTypeVOS) {
                ArrayList<BusinessTypeVO> businessTypeVOS = new ArrayList<>();
                for (BusinessTypeVO vo : businessTypeVO) {
                    if (typeVO.getTypeId()== vo.getParentId()) {
                        businessTypeVOS.add(vo);
                    }
                }
                typeVO.setBusinessTypeVOList(businessTypeVOS);
            }
        }


        /*if (businessTypeVO != null) {
            for (BusinessTypeVO typeVO : businessTypeVO) {
                //当前typeId 查询子类并设置在集合中
                Integer getParentId = typeVO.getParentId();
                if (getParentId != null) {
                    if (getParentId == 0) {
                        newBusinessTypeVOS.add(typeVO);
                    }else {
                        for (BusinessTypeVO vo : newBusinessTypeVOS) {
                            //子集合
                            ArrayList<BusinessTypeVO> newBusinessTypeVOS1 = new ArrayList<>();
                            if (vo.getParentId()!=null) {
                                if (vo.getParentId()==getParentId) {
                                    newBusinessTypeVOS1.add(vo);
                                }
                                typeVO.setBusinessTypeVOList(newBusinessTypeVOS1);
                            }

                        }
                    }
                }


            }
        }else {
            ResponseDTO.fail("查询失败");
        }*/

        return newBusinessTypeVOS;
    }
}
