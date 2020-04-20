package com.baizhi.gr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserCountVo {

    private String month;  //月份
    private String count; //数量
}
