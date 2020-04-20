package com.baizhi.gr.service;

import com.baizhi.gr.entity.Category;
import com.baizhi.gr.entity.Common;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //类别的分页展示
    Map<String,Object> pageCategory(int rows,int page,String pageLevel,String parentId);
    //类别的增删改操作
    Map<String,String> cudCategory(String oper, Category category);
    //前台查询所有类别信息
    Common selectAllCategory();
}
