package com.baizhi.gr.repository;

import com.baizhi.gr.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//泛型:实体类,ID的类型
public interface VideoRepository extends ElasticsearchRepository<Video,String> {
}
