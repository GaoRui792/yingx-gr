package com.baizhi.gr.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.gr.annotation.CUD;
import com.baizhi.gr.annotation.Select;
import com.baizhi.gr.dao.UserDAO;
import com.baizhi.gr.entity.User;
import com.baizhi.gr.service.UserService;
import com.baizhi.gr.util.AliyunUploadUtil;
import com.baizhi.gr.vo.CategoryVo;
import com.baizhi.gr.vo.UserCountVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceimpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Select
    public Map<String,Object> showUserByPage(int rows, int page) {
        log.info("userServiceImpl.showUserByPage");
        HashMap<String,Object> map = new HashMap<>();
        //rows 为分页条数, page为当前页数
        //查询所有的数据
        List<User> users = userDAO.selectAll();
        int size = users.size();
        //添加总条数和总页数的数据
        map.put("total",size%rows == 0 ? size/rows : size/rows+1);
        map.put("records",size);
        //查询分页数据
        List<User> pageUsers = userDAO.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));
        //添加当前页数和数据
        map.put("page",page);
        map.put("rows",pageUsers);
        return map;
    }

    @Override
    @CUD
    public String CUDUser(User user,String oper) {
        log.info("CUDUser.user ==> {}",user);
        String uid = null;
        if("add".equals(oper)){
            //添加操作
            uid = UUID.randomUUID().toString();
            user.setId(uid);
            user.setCreateDate(new Date());
            user.setStatus("正常");
            //添加用户
            userDAO.insert(user);
        }else if ("edit".equals(oper)){
            //修改操作
            userDAO.updateByPrimaryKeySelective(user);
        }else if ("del".equals(oper)){
            //删除操作
            userDAO.deleteByPrimaryKey(user);
        }
        return uid;
    }

    @Override
    public void uplocdImage(MultipartFile headImg, String id, HttpServletRequest request) {
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String savaPath = realPath + "/upload";
        File file = new File(savaPath);
        //判断文件夹是否存在
        if (!file.exists()){
            //如果不存在创建文件夹
            file.mkdirs();
        }
        //获取文件名
        String filename = new Date().getTime()+"-"+headImg.getOriginalFilename();
        //文件上传
        try {
            headImg.transferTo(new File(savaPath + "/" + filename));
            //修改图片的路径
            User user = new User();
            user.setId(id);
            user.setHeadImg(filename);
            userDAO.updateByPrimaryKeySelective(user);
        } catch (IOException e) {

        }
    }

    @Override
    public void AliyunUplocdImage(MultipartFile headImg, String id) {
        //上传到阿里云之后文件的名称
        String fileName = "image/"+new Date().getTime()+headImg.getOriginalFilename();
        //将文件上传至阿里云
        AliyunUploadUtil.AliyunUploadByBytes("yingx-gr",fileName,headImg);
        //修改数据库中图片的路径
        User user = new User();
        user.setId(id);
        user.setHeadImg("https://yingx-gr.oss-cn-beijing.aliyuncs.com/"+fileName);
        userDAO.updateByPrimaryKeySelective(user);
    }

    @Override
    public String userPoi() {

        List<User> users = userDAO.selectAll();
        String message = null;

        //文件的导出
        //导出的参数exportParams中的参数: title为标题,sheetName为工作表名
        ExportParams exportParams = new ExportParams("应学App用户信息", "用户");
        //配置工作表的参数 参数:导出的参数的对象, 导出的对象 , 需要导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        //导出
        try {
            workbook.write(new FileOutputStream("E://186-poi.xls"));
            //释放资源
            workbook.close();
            message = "导出成功";
        } catch (IOException e) {
            e.printStackTrace();
            message = "导出失败";
        }

        return message;
    }

    @Override
    public Map<String, Object> queryCount() {
        //查询出男生和女生每个月的注册人数
        List<UserCountVo> boys = userDAO.selectUserByDate("男");
        List<UserCountVo> girls = userDAO.selectUserByDate("女");
        //创建需要返回给前端的数据
        HashMap<String, Object> map = new HashMap<>();
        LinkedHashSet<String> months = new LinkedHashSet<>();
        //将男生女生所有有过注册月份都放入其中
        for (UserCountVo boy : boys) {
            months.add(boy.getMonth());
        }
        for (UserCountVo girl : girls) {
            months.add(girl.getMonth());
        }
        return null;
    }
}
