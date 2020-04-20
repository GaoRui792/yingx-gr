package com.baizhi.gr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class VideoCateNameVo {
    private String id;              //id
    private String videoTitle;      //视频标题
    private String cover;           //封面截图路径
    private String path;            //视频路径
    private Date uploadTime;        //上传时间
    private String description;     //描述
    private String likeCount;       //点赞数
    private String cateName;        //所属类别名称
}
