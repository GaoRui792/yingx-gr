package com.baizhi.gr.serviceimpl;

import com.baizhi.gr.annotation.CUD;
import com.baizhi.gr.annotation.Select;
import com.baizhi.gr.dao.CategoryDAO;
import com.baizhi.gr.entity.Category;
import com.baizhi.gr.entity.Common;
import com.baizhi.gr.service.CategoryService;
import com.baizhi.gr.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    @Select
    public Map<String, Object> pageCategory(int rows, int page,String pageLevel,String parentId) {
        HashMap<String, Object> map = new HashMap<>();
        //一级类别的总个数
        Category category = new Category();
        if ("1".equals(pageLevel)){
            category.setLevels("1");
        }else if ("2".equals(pageLevel)){
            category.setLevels("2");
            category.setParentId(parentId);
        }else{
            return null;
        }
        int count = categoryDAO.selectCount(category);
        //添加总条数和总页数
        map.put("records",count);
        map.put("total",count % rows == 0 ? count/rows : count/rows+1);
        //查询并添加当前页所展示的信息
        List<Category> categories = categoryDAO.selectByRowBounds(category, new RowBounds((page - 1) * rows, rows));
        map.put("rows",categories);
        //添加当前页数
        map.put("page",page);
        return map;
    }

    @Override
    @CUD(message = "类别的增删改方法")
    public Map<String,String> cudCategory(String oper, Category category) {
        HashMap<String,String> map = null;
        if ("add".equals(oper)){
            //添加类别
            category.setId(UUID.randomUUID().toString());
            categoryDAO.insert(category);
        }else if ("edit".equals(oper)){
            //修改类别信息
            categoryDAO.updateByPrimaryKey(category);
        }else if ("del".equals(oper)){
            if (category.getLevels().equals("1")){
                //判断下面是否有二级类别,存在二级类别则不能删除
                Category categoryTwo = new Category();
                categoryTwo.setParentId(category.getId());
                int count = categoryDAO.selectCount(categoryTwo);
                log.info("count == {}",count);
                map = new HashMap<String,String>();
                if (count == 0){
                    categoryDAO.deleteByPrimaryKey(category);
                }else{
                    map.put("status","400");
                    map.put("message","删除失败,该类别下存在子类别");
                }
            }else{
                //删除二级类别
                categoryDAO.deleteByPrimaryKey(category);
            }
        }
        return map;
    }

    @Override
    public Common selectAllCategory() {
        Common common = new Common();
        try{
            List<CategoryVo> categoryVos = categoryDAO.selectAllCategory();
            common.setData(categoryVos);
            common.setMessage("查询成功");
            common.setStatus("100");
        }catch (Exception e){
            common.setMessage("查询失败");
            common.setStatus("104");
        }
        return common;
    }

}
