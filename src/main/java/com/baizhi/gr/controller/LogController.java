package com.baizhi.gr.controller;

import com.baizhi.gr.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("pageLog")
    @ResponseBody
    public Map<String,String> pageLog(){
        Map<String, String> map = logService.pageLog();
        return map;
    }
}
