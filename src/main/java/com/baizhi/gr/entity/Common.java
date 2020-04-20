package com.baizhi.gr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Common {
    private Object data;
    private String message;
    private Object status;
}
