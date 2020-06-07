package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.vo.BusinessTypeVO;

import java.util.List;

public interface BusinessTypeService {


    List<BusinessTypeVO> selectAllBusinessType();

}
