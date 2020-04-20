package com.baizhi.gr.dao;

import com.baizhi.gr.entity.User;
import com.baizhi.gr.vo.UserCountVo;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface UserDAO extends Mapper<User> {
    List<UserCountVo> selectUserByDate(String sex);
}
