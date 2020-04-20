package com.baizhi.gr.service;

import com.baizhi.gr.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    HashMap<String,String> login(Admin admin, String enCode);
}
