package com.baizhi.gr.service;

import com.baizhi.gr.entity.Common;
import com.baizhi.gr.entity.Video;
import com.baizhi.gr.vo.VideoCateNameVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    Map<String,Object> pageVideo(Integer rows,Integer page);
    //视频的增删改方法
    String CUDVideo(Video video,String oper);
    //视频的阿里云上传
    void uploadVideo(MultipartFile file,String id);
    //前端视频的查询方法
    Common selectVideoAndCateName();
    //前端模糊查询视频的方法
    Common selectVideoByTitle(String content);
    //视频检索,查询ES
    List<Video> querySreach(String content);
}
