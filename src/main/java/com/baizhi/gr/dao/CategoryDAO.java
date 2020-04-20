package com.baizhi.gr.dao;

import com.baizhi.gr.entity.Category;
import com.baizhi.gr.vo.CategoryVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryDAO extends Mapper<Category> {

    //前台查询所有的分类查询
    List<CategoryVo> selectAllCategory();

}
