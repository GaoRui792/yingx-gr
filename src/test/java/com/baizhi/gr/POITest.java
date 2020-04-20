package com.baizhi.gr;

import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.entity.Admin;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class POITest {

    @Autowired
    private AdminDAO adminDAO;

    @Test
    public void poiTest(){
        //1.创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //2.创建一个工作表, 参数为:工作表的名字 不写的话默认为sheet1,sheet2...命名
        Sheet sheet = workbook.createSheet("学生信息1");
        //3.创建一行 参数为: 行下标 从0开始
        Row row = sheet.createRow(0);
        //4.创建一个单元格
        Cell cell = row.createCell(0);
        //5.给单元格添加内容
        cell.setCellValue("这是第一行第一个单元格");
        //导出表格
        try {
            workbook.write(new FileOutputStream("E://186-poi.xls"));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void poiByListTest(){
        //数据查询,查询出需要导出的数据
        List<Admin> admins = adminDAO.selectAll();
        //1.创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //2.创建一个工作表, 参数为:工作表的名字 不写的话默认为sheet1,sheet2...命名
        Sheet sheet = workbook.createSheet("学生信息1");
        //3.创建目录行
        String[] title = {"id","username","password"};
        Row row = sheet.createRow(0);
        //4.给目录行的单元格中填写内容
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //添加需要导出的数据
        for (int i = 0; i < admins.size(); i++) {
            Row rowAdmin = sheet.createRow(i+1);
            //创建单元格并赋值
            rowAdmin.createCell(0).setCellValue(admins.get(i).getId());
            rowAdmin.createCell(1).setCellValue(admins.get(i).getUsername());
            rowAdmin.createCell(2).setCellValue(admins.get(i).getPassword());
        }
        //导出 如果导出的文件路径中存在该文件那么将会覆盖掉
        try {
            workbook.write(new FileOutputStream("E://186-poi.xls"));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tt(){
        //获取需要导入的文件
        try {
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("E://186-poi.xls")));
            //根据文件获取工作表
            Sheet sheet = workbook.getSheet("学生信息1");
            //sheet.getLastRowNum() 获取最后一行的下标
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                 //获取行
                Row row = sheet.getRow(i);
                String id = row.getCell(0).getStringCellValue();
                String username = row.getCell(1).getStringCellValue();
                String password = row.getCell(2).getStringCellValue();
                Admin admin = new Admin(id, username, password);
                System.out.println("admin = " + admin);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
