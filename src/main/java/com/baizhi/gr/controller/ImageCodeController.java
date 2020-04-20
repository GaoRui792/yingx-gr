package com.baizhi.gr.controller;

import com.baizhi.gr.util.ImageCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("imageCode")
@Slf4j
public class ImageCodeController {
    @RequestMapping("getImageCode")
    private void getImageCode(HttpServletRequest request, HttpServletResponse response){
        //获取随机字符
        String securityCode = ImageCodeUtil.getSecurityCode();
        log.info("imagecode ==> {}",securityCode);
        //存储随机字符
        request.getSession().setAttribute("loginCode",securityCode);
        //生成图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //响应图片
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
