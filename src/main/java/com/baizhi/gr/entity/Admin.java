package com.baizhi.gr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_admin")
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
}
