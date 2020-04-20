<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
</head>
<body>
    <!--顶部导航-->
    <div class="container-fluid">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <%-- 导航条的头,下面放导航条里的内容 --%>
                <div class="navbar-header">
                    <%-- 按钮,表示后台管理系统是一个按钮样式 --%>
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="">应学视频App后台管理系统</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <%-- navbar-nav 样式 --%>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#" class="navbar-link" id="font">欢迎：<strong>${Admin.username}</strong></a></li>
                        <li><a href="${path}/admin/exit" class="navbar-link">退出登录<span class="glyphicon glyphicon-log-out"></span></a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row">
            <!--左边手风琴部分-->
            <div class="col-sm-2">
                <%-- 面板组 --%>
                <div class="panel-group" id="operation" role="tablist">
                    <%-- 用户 --%>
                    <div class="panel panel-danger">
                        <%-- 头部 --%>
                        <div class="panel-heading text-center" id="userHeading" role="tab">
                            <h3 class="panel-title">
                                <%-- data-toggle = "collapse" 折叠开关
                                 data-parent="#operation" 共享父容器 开一个关一个 --%>
                                <a role="button" data-toggle="collapse" data-parent="#operation" href="#userBody">
                                    用户管理
                                </a>
                            </h3>
                        </div>
                        <%-- 身体
                            class="panel-collapse collapse in"  折叠的样式 表示是可被折叠的 in为默认展开
                            role="tabpanel"  角色  表示充当着标签页的角色
                        --%>
                        <div id="userBody" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body text-center">
                                <button class="btn btn-danger">
                                    <a role="button" class="btn-danger" href="javascript:$('#MainCol').load('${path}/user/user.jsp')">用户展示</a>
                                </button><br/>
                                <button class="btn btn-danger">
                                    <a role="button" class="btn-danger" href="javascript:$('#MainCol').load('${path}/user/userCount.jsp')">用户统计</a>
                                </button><br/>
                                <button class="btn btn-danger">
                                    <a role="button" class="btn-danger" href="javascript:$('#MainCol').load('${path}/user/userCity.jsp')">用户分布</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <%-- 分类 --%>
                    <div class="panel panel-success">
                        <%-- 头部 --%>
                        <div class="panel-heading text-center" id="categoryHeading" role="tab">
                            <h3 class="panel-title">
                                <%-- data-toggle = "collapse" 折叠开关
                                 data-parent="#operation" 共享父容器 开一个关一个 --%>
                                <a role="button" data-toggle="collapse" data-parent="#operation" href="#categoryBody">
                                    分类管理
                                </a>
                            </h3>
                        </div>
                        <%-- 身体
                            class="panel-collapse collapse in"  折叠的样式 表示是可被折叠的 in为默认展开
                            role="tabpanel"  角色  表示充当着标签页的角色
                        --%>
                        <div id="categoryBody" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body text-center">
                                <button class="btn btn-success">
                                    <a role="button" class="btn-success" href="javascript:$('#MainCol').load('${path}/category/category.jsp')">分类展示</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <%-- 视频 --%>
                    <div class="panel panel-warning">
                        <%-- 头部 --%>
                        <div class="panel-heading text-center" id="videoHeading" role="tab">
                            <h3 class="panel-title">
                                <%-- data-toggle = "collapse" 折叠开关
                                 data-parent="#operation" 共享父容器 开一个关一个 --%>
                                <a role="button" data-toggle="collapse" data-parent="#operation" href="#videoBody">
                                    视频管理
                                </a>
                            </h3>
                        </div>
                        <%-- 身体
                            class="panel-collapse collapse in"  折叠的样式 表示是可被折叠的 in为默认展开
                            role="tabpanel"  角色  表示充当着标签页的角色
                        --%>
                        <div id="videoBody" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body text-center">
                                <button class="btn btn-warning">
                                    <a role="button" class="btn-warning" href="javascript:$('#MainCol').load('${path}/video/video.jsp')">视频展示</a>
                                </button><br/>
                                <button class="btn btn-warning">
                                    <a role="button" class="btn-warning" href="javascript:$('#MainCol').load('${path}/video/videoSearch.jsp')">视频搜索</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <%-- 日志 --%>
                    <div class="panel panel-info">
                        <%-- 头部 --%>
                        <div class="panel-heading text-center" id="logHeading" role="tab">
                            <h3 class="panel-title">
                                <%-- data-toggle = "collapse" 折叠开关
                                 data-parent="#operation" 共享父容器 开一个关一个 --%>
                                <a role="button" data-toggle="collapse" data-parent="#operation" href="#logBody">
                                    日志管理
                                </a>
                            </h3>
                        </div>
                        <%-- 身体
                            class="panel-collapse collapse in"  折叠的样式 表示是可被折叠的 in为默认展开
                            role="tabpanel"  角色  表示充当着标签页的角色
                        --%>
                        <div id="logBody" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body text-center">
                                <button class="btn btn-info">
                                    <a role="button" class="btn-info" href="javascript:$('#MainCol').load('${path}/log/log.jsp')">日志展示</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <%-- 反馈 --%>
                    <div class="panel panel-primary">
                        <%-- 头部 --%>
                        <div class="panel-heading text-center" id="FeedBackHeading" role="tab">
                            <h3 class="panel-title">
                                <%-- data-toggle = "collapse" 折叠开关
                                 data-parent="#operation" 共享父容器 开一个关一个 --%>
                                <a role="button" data-toggle="collapse" data-parent="#operation" href="#FeedBackBody">
                                    反馈管理
                                </a>
                            </h3>
                        </div>
                        <%-- 身体
                            class="panel-collapse collapse in"  折叠的样式 表示是可被折叠的 in为默认展开
                            role="tabpanel"  角色  表示充当着标签页的角色
                        --%>
                        <div id="FeedBackBody" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body text-center">
                                <button class="btn btn-primary">
                                    <a role="button" class="btn-primary" href="javascript:$('#MainCol').load('${path}/feedback/feedback.jsp')">反馈展示</a>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--右边开始--%>
            <div class="col-sm-10" id="MainCol">
                <!--巨幕开始-->
                <div class="jumbotron">
                    <h2>欢迎来到应学视频App后台管理系统</h2>
                </div>
                <!--右边轮播图部分-->
                <div id="slideshow" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#slideshow" data-slide-to="0" class="active"></li>
                        <li data-target="#slideshow" data-slide-to="1"></li>
                        <li data-target="#slideshow" data-slide-to="2"></li>
                        <li data-target="#slideshow" data-slide-to="3"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="https://yingx-gr.oss-cn-beijing.aliyuncs.com/image/100.jpg">
                            <div class="carousel-caption"></div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/79.jpg">
                            <div class="carousel-caption"></div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/104.jpg">
                            <div class="carousel-caption"></div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/girl.jpg">
                            <div class="carousel-caption"></div>
                        </div>
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#slideshow" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#slideshow" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <!--页脚-->
    <div class="panel-footer panel text-center">
        @百知教育 zhangcn@zparkhr.com
    </div>
    <!--栅格系统-->

</body>
</html>
