package com.baizhi.gr.dao;

import com.baizhi.gr.entity.Video;
import com.baizhi.gr.vo.VideoCateNameVo;
import com.baizhi.gr.vo.VideoCateUserVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoDAO extends Mapper<Video> {

    //前端展示视频所需要的数据
    List<VideoCateNameVo> selectVideoAndCateName();

    //前台模糊查询视频信息
    List<VideoCateUserVo> selectVideoByTitle(String title);

}
