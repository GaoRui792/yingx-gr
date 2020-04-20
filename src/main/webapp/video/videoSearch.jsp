<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script src="${path}/bootstrap/js/jquery.min.js"></script>

<script>
    $(function(){

        //点击搜索按钮
        $("#btnId").click(function(){

            //清空表单
            $("#queryTable").empty();

            $("#queryTable").append("<tr>" +
                "<td>ID</td>" +
                "<td>标题</td>" +
                "<td>描述</td>" +
                "<td>封面</td>" +
                "<td>上传时间</td>" +
                "</tr>");

            //获取内容
            var content =$("#contentId").val();

            //向后台发请求查询数据
            $.ajax({
                url:"${path}/video/querySearch",
                type:"post",
                dataType:"JSON",
                data:{"content":content},
                success:function (data) {

                    //遍历取出数据
                    $.each(data,function(index,video){
                        //获取数据渲染页面
                        $("#queryTable").append("<tr>" +
                            "<td>"+video.id+"</td>" +
                            "<td>"+video.title+"</td>" +
                            "<td>"+video.brief+"</td>" +
                            "<td><img src='"+video.cover+"' style='width: 200px;height: 100px' /></td>" +
                            "<td>"+video.publishDate+"</td>" +
                            "</tr>");
                    })
                }
            })
        });

    });
</script>


<div align="center">
    <%--搜索框--%>
    <div class="input-group" style="width: 300px">
        <input id="contentId" type="text" class="form-control" placeholder="查询视频标题或简介" aria-describedby="basic-addon2">
        <span class="input-group-btn" id="basic-addon2">
            <button class="btn btn-warning" id="btnId">点我搜索</button>
        </span>
    </div>
</div>

<hr>

<%--展示搜索内容--%>
<div class="panel panel-default">
    <!-- 面板标题 -->
    <div class="panel-heading">检索结果</div>

    <!-- 搜索内容 -->
    <table class="table" id="queryTable">

    </table>
</div>