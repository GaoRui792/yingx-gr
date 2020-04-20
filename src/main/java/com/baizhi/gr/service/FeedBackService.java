package com.baizhi.gr.service;

import com.baizhi.gr.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FeedBackService {
    //分页展示的反馈信息
    Map<String,Object> showFeedBackByPage(int rows, int page);
}
