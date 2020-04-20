package com.baizhi.gr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_feedback")
public class FeedBack implements Serializable {
    @Id
    private String id;
    private String title;
    private String content;
    @Column(name = "user_id")
    private String userID;
    @Column(name = "save_date")
    private Date saveDate;
}
