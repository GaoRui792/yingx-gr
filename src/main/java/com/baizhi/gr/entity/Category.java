package com.baizhi.gr.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_category")
public class Category implements Serializable {
    @Id
    @Excel(name = "ID")
    private String id;
    @Column(name = "cate_name")
    @Excel(name = "类别名")
    private String cateName;
    @Excel(name = "级别")
    private String levels;
    @Column(name = "parent_id")
    @Excel(name = "父级ID")
    private String parentId;
}
