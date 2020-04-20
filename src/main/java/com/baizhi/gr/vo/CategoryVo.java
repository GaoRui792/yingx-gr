package com.baizhi.gr.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baizhi.gr.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    /*
    * @Excel为EasyPoi注解 用于导出文件时的注解 needMerge为合并单元格
    * @ExcelCollection为集合导出在需要导出的数据中存在集合对象时添加该注解
    * 集合中的泛型所在的实体类中也需要添加@Excel注解标注
    * @ExcelIgnore表示该属性不在表中展示
    * */
    @ExcelIgnore
    private String id;
    @Excel(name = "类别名称",needMerge = true)
    private String cateName;
    @Excel(name = "级别",needMerge = true)
    private String levels;
    @Excel(name = "父级类别ID",needMerge = true)
    private String parentId;
    @ExcelCollection(name = "二级类别")
    private List<Category> categoryList;
}
