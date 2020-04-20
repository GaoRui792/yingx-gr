package com.baizhi.gr;

import com.baizhi.gr.dao.CategoryDAO;
import com.baizhi.gr.dao.UserDAO;
import com.baizhi.gr.entity.Category;
import com.baizhi.gr.entity.User;
import com.baizhi.gr.vo.CategoryVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void pageUser(){
        Category category = new Category();
        category.setLevels("1");
        int count = categoryDAO.selectCount(category);
        List<Category> select = categoryDAO.select(category);
        System.out.println(count);
        for (Category category1 : select) {
            System.out.println("category1 = " + category1);
        }
    }

    @Test
    public void selectAll(){
        List<CategoryVo> categoryVos = categoryDAO.selectAllCategory();
        for (CategoryVo categoryVo : categoryVos) {
            System.out.println("categoryVo = " + categoryVo);
        }
    }
}
