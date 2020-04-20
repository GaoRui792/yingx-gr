package com.baizhi.gr.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_user")
public class User implements Serializable {
    @Id
    @Excel(name = "用户ID",width = 35)
    private String id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "手机号",width = 20)
    private String phone;

    @Excel(name = "头像")
    @Column(name = "head_img")
    private String headImg;

    @Excel(name = "签名")
    private String sign;    //签名

    @Column(name = "wechat")
    @Excel(name = "微信",width = 25)
    private String weChat;  //微信

    @Excel(name = "状态")
    private String status;

    @Column(name = "create_date")
    @Excel(name = "注册时间",format = "yyyy-MM-ss")
    private Date createDate;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "所在城市")
    private String city;

    @ExcelIgnore
    @Transient
    private Integer video_num;    //视频图片数
    @ExcelIgnore
    @Transient
    private Integer fans_num;     //粉丝数
    @ExcelIgnore
    @Transient
    private String score;         //学分
}