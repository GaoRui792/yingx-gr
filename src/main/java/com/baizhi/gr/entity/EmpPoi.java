package com.baizhi.gr.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmpPoi {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private String age;
    @Excel(name = "生日",format = "yyyy-MM-dd",width = 30)
    private Date bir;
}
