package com.baizhi.gr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class VideoCateUserVo {
    private String id;  //视频id
    private String videoTitle;  //视频标题
    private String cover;  //视频封面
    private String path;  //视频路径
    private String uploadTime;  //视频上传时间
    private String description;  //视频描述
    private String likeCount;  //视频点赞数
    private String cateName;  //所属类别名
    private String categoryId;  //所属类别id
    private String userId;  //所属用户id
    private String userName;  //所属用户名
}
