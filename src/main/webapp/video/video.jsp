<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#videoTable").jqGrid({
            /* 初始化参数 */
            url:"${path}/video/pageVideo", //请求地址,访问后台的url
            editurl:"${path}/video/cud",
            autowidth:true,
            styleUI:"Bootstrap",
            datatype:"json",	 //返回数据的类型
            mtype:"post",    /* 提交方式 默认为get */
            pager:"#videoPage",
            rowNum:"4",
            rowList:[4,5,6],
            //multiselect:true,   //多选
            viewrecords:true,   //显示总条数
            colNames:["id","标题","简介","视频","类别ID","分组ID","用户ID","发布时间"],  //表头
            colModel:[{name:"id",align:"center"},
                {name:"title",align:"center",editable:true},   //数据
                {name:"brief",align:"center",editable:true},
                {name:"path",align:"center",editable:true,width:340,edittype:"file",
                    //参数含义:cellvalue 列的值, options 操作,rowObject 行对象
                    formatter:function (cellvalue,options,rowObject) {
                        return "<video src = '"+cellvalue+"' width='160px' height='120px' controls poster='"+rowObject.cover+"'/>"
                    }
                },
                {name:"categoryId",align:"center"},
                {name:"groupId",align:"center"},
                {name:"userId",align:"center"},
                {name:"publishDate",align:"center",edittype:"date",datefmt:"yyyy-MM-dd"}]
        }).jqGrid("navGrid","#videoPage",{search:false,edit:false,addtext:"添加",deltext:"删除"}
            ,{closeAfterEdit:true}
            ,{closeAfterAdd:true,
                //提交之后
                afterSubmit:function (response) {
                    //文件上传,修改图片的路径
                    $.ajaxFileUpload({
                        url:"${path}/video/uploadVideo",
                        type:"post",
                        fileElementId:"path",
                        data:{id:response.responseText},
                        success:function () {
                            $("#videoTable").trigger("reloadGrid");
                        }
                    })
                    return "shut";
                }
            }
            ,{});
    });
</script>

<style>
    th {
        text-align: center;
    }
</style>

<div class="panel-warning panel">
    <%-- 面板头 --%>
    <div class="panel-heading panel">
        <h4>视频信息</h4>
    </div>
    <%-- 标签页 --%>
    <div class="nav nav-tabs">
        <li class="active"><a href="#">视频信息</a></li>
        <%--初始化表单--%>
        <table id="videoTable"></table>
        <%--分页工具栏--%>
        <div id="videoPage"></div>
    </div>
</div>