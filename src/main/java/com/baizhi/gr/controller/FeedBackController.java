package com.baizhi.gr.controller;

import com.baizhi.gr.service.FeedBackService;
import com.baizhi.gr.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @RequestMapping("pagefeedback")
    @ResponseBody
    public Map<String,Object> pageLog(Integer rows,Integer page){
        Map<String, Object> map = feedBackService.showFeedBackByPage(rows, page);
        return map;
    }
}
