package com.baizhi.gr.serviceimpl;

import com.baizhi.gr.annotation.Select;
import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.dao.FeedBackDAO;
import com.baizhi.gr.entity.Admin;
import com.baizhi.gr.entity.FeedBack;
import com.baizhi.gr.entity.User;
import com.baizhi.gr.service.AdminService;
import com.baizhi.gr.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@Slf4j
public class FeedBackServiceimpl implements FeedBackService {

    @Autowired
    private FeedBackDAO feedBackDAO;

    @Override
    @Select
    public Map<String, Object> showFeedBackByPage(int rows, int page) {
        HashMap<String,Object> map = new HashMap<>();
        //rows 为分页条数, page为当前页数
        //查询所有的数据
        List<FeedBack> feedBacks = feedBackDAO.selectAll();
        int size = feedBacks.size();
        //添加总条数和总页数的数据
        map.put("total",size%rows == 0 ? size/rows : size/rows+1);
        map.put("records",size);
        //查询分页数据
        List<FeedBack> feedBack = feedBackDAO.selectByRowBounds(new FeedBack(), new RowBounds((page - 1) * rows, rows));
        //添加当前页数和数据
        map.put("page",page);
        map.put("rows",feedBack);
        return map;
    }
}
