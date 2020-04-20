package com.baizhi.gr;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.dao.CategoryDAO;
import com.baizhi.gr.dao.UserDAO;
import com.baizhi.gr.entity.Admin;
import com.baizhi.gr.entity.Category;
import com.baizhi.gr.entity.EmpPoi;
import com.baizhi.gr.entity.User;
import com.baizhi.gr.vo.CategoryVo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EasyPoiTest {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void EasyPoi(){
        //需要导出的数据
        ArrayList<EmpPoi> empPois = new ArrayList<>();
        empPois.add(new EmpPoi("1","小花","13",new Date()));
        empPois.add(new EmpPoi("2","小清","32",new Date()));
        empPois.add(new EmpPoi("3","小呈","45",new Date()));
        empPois.add(new EmpPoi("4","小晃","21",new Date()));
        empPois.add(new EmpPoi("5","小吕","27",new Date()));

        //文件的导出
        //导出的参数exportParams中的参数: title为标题,sheetName为工作表名
        ExportParams exportParams = new ExportParams("计算机一班学生", "学生");
        //配置工作表的参数 参数:导出的参数的对象, 导出的对象 , 需要导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,EmpPoi.class, empPois);
        //导出
        try {
            workbook.write(new FileOutputStream("E://186-poi.xls"));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EasyPoiVoOut(){

        /*List<CategoryVo> categoryVos = categoryDAO.selectAllCategory();

        for (CategoryVo categoryVo : categoryVos) {
            System.out.println("categoryVo = " + categoryVo);
        }*/

        List<User> users = userDAO.selectAll();

        //文件的导出
        //导出的参数exportParams中的参数: title为标题,sheetName为工作表名
        ExportParams exportParams = new ExportParams("应学App类别信息", "类别");
        //配置工作表的参数 参数:导出的参数的对象, 导出的对象 , 需要导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,User.class, users);
        //导出
        try {
            workbook.write(new FileOutputStream("E://186-poi.xls"));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EasyPoiIn(){
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        long start = new Date().getTime();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("E://186-poi.xls"));
            List<CategoryVo> list = ExcelImportUtil.importExcel(fileInputStream, CategoryVo.class, params);
            for (CategoryVo categoryVo : list) {
                System.out.println("categoryVo = " + categoryVo);
                for (Category category : categoryVo.getCategoryList()) {
                    System.out.println("category = " + category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
