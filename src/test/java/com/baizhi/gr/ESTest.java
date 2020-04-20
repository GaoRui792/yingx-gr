package com.baizhi.gr;

import com.baizhi.gr.dao.VideoDAO;
import com.baizhi.gr.entity.Video;
import com.baizhi.gr.repository.VideoRepository;
import com.baizhi.gr.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoDAO videoDAO;

    @Test
    public void insert(){
        List<Video> videos = videoDAO.selectAll();
        for (Video video : videos) {
            videoRepository.save(video);
        }

    }

    @Test
    public void findAll(){
        Iterable<Video> all = videoRepository.findAll();
        for (Video video : all) {
            System.out.println("video = " + video);
        }
    }

}
