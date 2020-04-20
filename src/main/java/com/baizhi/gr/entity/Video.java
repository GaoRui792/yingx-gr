package com.baizhi.gr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_video")
@Document(indexName = "yingxv",type = "video")
public class Video implements Serializable {
    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;  //标题

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String brief;  //简介

    @Field(type = FieldType.Keyword)
    private String path;   //视频地址

    @Field(type = FieldType.Keyword)
    private String cover;  //封面地址

    @Column(name = "publish_date")
    @Field(type = FieldType.Date)
    private Date publishDate; //发布时间

    @Column(name = "category_id")
    @Field(type = FieldType.Keyword)
    private String categoryId; //类别ID

    @Column(name = "group_id")
    @Field(type = FieldType.Keyword)
    private String groupId;    //分组ID

    @Column(name = "user_id")
    @Field(type = FieldType.Keyword)
    private String userId;     //用户ID


}
