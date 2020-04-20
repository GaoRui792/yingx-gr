<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#feedbackTable").jqGrid({
            /* 初始化参数 */
            url:"${path}/feedback/pagefeedback", //请求地址,访问后台的url
            autowidth:true,
            styleUI:"Bootstrap",
            datatype:"json",	 //返回数据的类型
            mtype:"post",    /* 提交方式 默认为get */
            pager:"#feedbackPage",
            rowNum:"4",
            rowList:[4,5,6],
            viewrecords:true,   //显示总条数
            colNames:["id","标题","描述","用户ID","反馈时间"],  //表头
            colModel:[{name:"id",align:"center"},
                {name:"title",align:"center"},
                {name:"content",align:"center"},
                {name:"userID",align:"center"},
                {name:"saveDate",align:"center",datefmt:"yyyy-MM-dd"}]
        });
    });

</script>

<style>
    th {
        text-align: center;
    }
</style>

<div class="panel-primary panel">
    <%-- 面板头 --%>
    <div class="panel-heading panel">
        <h4>反馈信息</h4>
    </div>
    <%-- 标签页 --%>
    <div class="nav nav-tabs">
        <li class="active"><a href="#">反馈信息</a></li>
        <%--初始化表单--%>
        <table id="feedbackTable"></table>
        <%--分页工具栏--%>
        <div id="feedbackPage"></div>
    </div>
</div>