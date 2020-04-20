<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#logTable").jqGrid({
            /* 初始化参数 */
            url:"${path}/log/pageLog", //请求地址,访问后台的url
            autowidth:true,
            styleUI:"Bootstrap",
            datatype:"json",	 //返回数据的类型
            mtype:"post",    /* 提交方式 默认为get */
            pager:"#logPage",
            rowNum:"4",
            rowList:[4,5,6],
            viewrecords:true,   //显示总条数
            colNames:["管理员","操作","时间","状态"],  //表头
            colModel:[{name:"admin",align:"center"},
                {name:"operation",align:"center"},   //数据
                {name:"Date",align:"center"},
                {name:"status",align:"center"}]
        });
    });

</script>

<style>
    th {
        text-align: center;
    }
</style>

<div class="panel-info panel">
    <%-- 面板头 --%>
    <div class="panel-heading panel">
        <h4>日志信息</h4>
    </div>
    <%-- 标签页 --%>
    <div class="nav nav-tabs">
        <li class="active"><a href="#">日志信息</a></li>
        <%--初始化表单--%>
        <table id="logTable"></table>
        <%--分页工具栏--%>
        <div id="logPage"></div>
    </div>
</div>