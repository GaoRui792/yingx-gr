package com.baizhi.gr.serviceimpl;

import com.baizhi.gr.annotation.CUD;
import com.baizhi.gr.annotation.Select;
import com.baizhi.gr.dao.VideoDAO;
import com.baizhi.gr.entity.Common;
import com.baizhi.gr.entity.Video;
import com.baizhi.gr.repository.VideoRepository;
import com.baizhi.gr.service.VideoService;
import com.baizhi.gr.util.AliyunSendPhoneUtil;
import com.baizhi.gr.util.AliyunUploadUtil;
import com.baizhi.gr.vo.VideoCateNameVo;
import com.baizhi.gr.vo.VideoCateUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
@Transactional
@Slf4j
public class VideoServiceimpl implements VideoService {

    @Autowired
    private VideoDAO videoDAO;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @Select
    public Map<String, Object> pageVideo(Integer rows, Integer page) {
        log.info("VideoService.pageVideo");
        HashMap<String,Object> map = new HashMap<>();
        //rows 为分页条数, page为当前页数
        //查询所有的数据
        Video video = new Video();
        int size = videoDAO.selectCount(video);
        //添加总条数和总页数的数据
        map.put("total",size%rows == 0 ? size/rows : size/rows+1);
        map.put("records",size);
        //查询分页数据
        List<Video> videos = videoDAO.selectByRowBounds(video, new RowBounds((page - 1) * rows, rows));
        //添加当前页数和数据
        map.put("page",page);
        map.put("rows",videos);
        return map;
    }

    @Override
    @CUD(message = "视频增删改")
    public String CUDVideo(Video video, String oper) {
        log.info("CUDVideo.oper ==> {}",oper);
        String uid = null;
        if("add".equals(oper)){
            //添加操作
            uid = UUID.randomUUID().toString();
            video.setId(uid);
            video.setGroupId("1");
            video.setCategoryId("1");
            video.setUserId("1");
            video.setPublishDate(new Date());
            videoDAO.insert(video);
            //添加视频
        }else if ("del".equals(oper)){
            //删除操作
            //查询视频完整信息
            video = videoDAO.selectByPrimaryKey(video);

            //删除阿里云中的视频及封面
            //字符串拆分,获取在阿里云中的路径
            String[] pathSplit = video.getPath().split("/");
            String[] coverSplit = video.getCover().split("/");

            String videoName=pathSplit[pathSplit.length-2]+"/"+pathSplit[pathSplit.length-1];
            String coverName=coverSplit[coverSplit.length-2]+"/"+coverSplit[coverSplit.length-1];

            log.info("videoName ==> {},coverName ==> {}",videoName,coverName);
            AliyunUploadUtil.delete("yingx-gr",videoName);
            AliyunUploadUtil.delete("yingx-gr",coverName);

            //删除数据库信息
            videoDAO.deleteByPrimaryKey(video);

            //删除该视频在es中的索引信息
            videoRepository.delete(video);
        }
        return uid;
    }

    @Override
    public void uploadVideo(MultipartFile file, String id) {
        String bucketName = "yingx-gr";

        //获取文件名
        String fileName = new Date().getTime()+file.getOriginalFilename();
        //将视频上传至阿里云
        AliyunUploadUtil.AliyunUploadByBytes(bucketName,"video/"+fileName,file);

        //去掉视频后缀,保留视频名
        String[] names = fileName.split("\\.");
        String name=names[0];
        //拼接截图名
        String coverName=name+".jpg";

        //截取视频第一帧做封面并上传至阿里云
        AliyunUploadUtil.videoCoverIntercept(bucketName,"video/"+fileName,"image/"+coverName);

        //修改视频路径
        Video video = new Video();
        //根据id查询出该视频的所有信息
        video.setId(id);
        video = videoDAO.selectByPrimaryKey(video);
        video.setCover("https://yingx-gr.oss-cn-beijing.aliyuncs.com/image/"+coverName);
        video.setPath("https://yingx-gr.oss-cn-beijing.aliyuncs.com/video/"+fileName);
        videoDAO.updateByPrimaryKeySelective(video);
        //将该视频消息存储在es中,方便进行视频的索引
        videoRepository.save(video);
    }

    @Override
    public Common selectVideoAndCateName() {
        /*
        *  查询出前端展示视频所需要的数据
        *  因为未写点赞数,所以将点赞数设置为随机数
        * */
        Common common = new Common();
        try {
            List<VideoCateNameVo> videoCateNameVos = videoDAO.selectVideoAndCateName();
            for (VideoCateNameVo videoCateNameVo : videoCateNameVos) {
                videoCateNameVo.setLikeCount(AliyunSendPhoneUtil.randomCode(4));
            }
            common.setData(videoCateNameVos);
            common.setMessage("查询成功");
            common.setStatus("100");
        }catch (Exception e){
            common.setMessage("查询失败");
            common.setStatus("104");
        }

        return common;
    }

    @Override
    public Common selectVideoByTitle(String content) {

        Common common = new Common();
        try {
            List<VideoCateUserVo> videoCateUserVos = videoDAO.selectVideoByTitle(content);
            for (VideoCateUserVo videoCateUserVo : videoCateUserVos) {
                videoCateUserVo.setLikeCount(AliyunSendPhoneUtil.randomCode(4));
            }
            common.setData(videoCateUserVos);
            common.setMessage("查询成功");
            common.setStatus("100");
        }catch (Exception e){
            common.setMessage("查询失败");
            common.setStatus("104");
        }
        return common;
    }

    @Override
    public List<Video> querySreach(String content) {
        //处理高亮的操作 为查询到的结果添加标记,产生高亮效果
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀

        //查询的条件 查询出基础的数据
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")  //索引名
                .withTypes("video")   //索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))  //搜索的条件
                .withHighlightFields(field)  //处理高亮 添加上面设置的高亮效果
                .build();

        //高亮查询  参数: 上面查询到的基础的数据, 索引的实体类  以匿名内部类的方式实现方法,返回高亮的数据
        AggregatedPage<Video> videoList = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> videos = new ArrayList<>();

                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //原始数据
                    Map<String, Object> map = hit.getSourceAsMap();

                    //处理普通数据
                    //判断索引中的数据是否为空,不为空即添加该数据
                    String id = map.get("id")!=null? map.get("id").toString() : null;
                    String title = map.get("title")!=null? map.get("title").toString() : null;
                    String brief = map.get("brief")!=null? map.get("brief").toString() : null;
                    String path = map.get("path")!=null? map.get("path").toString() : null;
                    String cover = map.get("cover")!=null? map.get("cover").toString() : null;
                    String categoryId = map.get("categoryId")!=null? map.get("categoryId").toString() : null;
                    String groupId = map.get("groupId")!=null? map.get("groupId").toString() : null;
                    String userId = map.get("userId")!=null? map.get("userId").toString() : null;

                    //处理日期操作
                    Date date = null;

                    if(map.get("publishDate")!=null){
                        String publishDateStr = map.get("publishDate").toString();
                        //处理日期转换
                        Long aLong = Long.valueOf(publishDateStr);
                        date = new Date(aLong);
                    }

                    //封装video对象
                    Video video = new Video(id, title, brief, path, cover, date, categoryId, groupId, userId);

                    //处理高亮数据
                    Map<String, HighlightField> highlightMap = hit.getHighlightFields();

                    if (highlightMap.get("title") != null) {
                        String titles = highlightMap.get("title").fragments()[0].toString();
                        video.setTitle(titles);
                    }

                    if (highlightMap.get("brief") != null) {
                        String briefs = highlightMap.get("brief").fragments()[0].toString();
                        video.setBrief(briefs);
                    }

                    //将对象放入集合
                    videos.add(video);

                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });

        return videoList.getContent();
    }
}
