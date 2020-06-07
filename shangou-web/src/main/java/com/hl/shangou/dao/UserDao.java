package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.User;
import com.hl.shangou.pojo.query.UserQuery;
import com.hl.shangou.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserVO selectUserByPhoneAndPassword(UserQuery query);

    UserVO selectUserByPhone(String phone);

    List<User> ajaxUserList(UserQuery userQuery);

    int ajaxGetCount();

    int deleteByPrimaryKeys(@Param("userIds") List<Long> userIds);
}
