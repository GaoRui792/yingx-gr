package com.baizhi.gr.controller;

import com.baizhi.gr.entity.Common;
import com.baizhi.gr.service.CategoryService;
import com.baizhi.gr.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("queryByReleaseTime")
    public Common showVideos(String phone){
        /*
        *  前台展示视频数据的查询方法
        * */
        System.out.println("前台视频展示");
        Common common = videoService.selectVideoAndCateName();
        return common;
    }

    @RequestMapping("queryByLikeVideoName")
    public Common selectVideo(String content){
        /*
        *  模糊查询视频的方法
        *  content为标题模糊查询的条件
        * */
        Common common = videoService.selectVideoByTitle(content);
        return common;
    }

    @RequestMapping("queryAllCategory")
    public Common queryAllCategory(){
        System.out.println("查询所有分类信息");
        Common common = categoryService.selectAllCategory();
        return common;
    }

    @RequestMapping("queryByUserMoving")
    public void queryByUser(String userId){
        /*
        *  查询该用户所关注的人发布的视频
        * */
        System.out.println("userId = " + userId);
    }

}
