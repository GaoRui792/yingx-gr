package com.baizhi.gr.controller;

import com.baizhi.gr.entity.Video;
import com.baizhi.gr.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("video")
@Slf4j
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("pageVideo")
    public Map<String,Object> pageVideo(Integer rows,Integer page){
        log.info("视频分页");
        Map<String, Object> map = videoService.pageVideo(rows, page);
        return map;
    }

    @RequestMapping("cud")
    public String cudVideo(Video video,String oper){
        log.info("cud.video ==> {}",video);
        String uid = videoService.CUDVideo(video, oper);
        return uid;
    }

    @RequestMapping("uploadVideo")
    public void uploadVideo(MultipartFile path, String id){
        /*
         * 文件上传
         * */
        videoService.uploadVideo(path,id);
    }

    @RequestMapping("querySearch")
    public List<Video> querySearch(String content){
        log.info("视频检索,es   content ==> {}",content);
        List<Video> videos = videoService.querySreach(content);
        return videos;
    }
}
