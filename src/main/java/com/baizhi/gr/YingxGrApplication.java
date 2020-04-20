package com.baizhi.gr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.baizhi.gr.dao")
@SpringBootApplication
public class YingxGrApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxGrApplication.class, args);
    }

}
