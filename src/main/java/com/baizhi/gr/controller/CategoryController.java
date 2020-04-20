package com.baizhi.gr.controller;

import com.baizhi.gr.entity.Category;
import com.baizhi.gr.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Controller
@RequestMapping("category")
@Slf4j
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("page")
    public Map<String,Object> pageCategory(Integer rows,Integer page,String pageLevel,String parentId){
        log.info("一级类别表的分页操作");
        log.info("pageLevel ==> {}",pageLevel);
        log.info("ParentId ==> {}",parentId);
        //一级类别表的分页操作
        Map<String, Object> map = categoryService.pageCategory(rows, page,pageLevel,parentId);
        return map;
    }

    @RequestMapping("cud")
    public Map<String,String> cudOne(String oper, Category category){
        log.info("类别的增删改==category ==> {}",category);
        //一级类别的增删改
        Map<String, String> map = categoryService.cudCategory(oper, category);
        return map;
    }
}
