<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/bootstrap/js/echarts.min.js"></script>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript">

    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        $.get("${path/user/userCountByDate}",function (data) {
            var option = {
                title: {
                    text: '用户每月注册人数'
                },
                tooltip: {},
                legend: {
                    data:['男生','女生']
                },
                xAxis: {
                    data: data.month
                },
                yAxis: {},
                series: [{
                    name: '男生',
                    type: 'bar',
                    data: data.boys
                },{
                    name: '女生',
                    type: 'bar',
                    data: data.girls
                }]
            };
        },"json");
        // 使用刚指定的配置项和数据显示图表。
        // 指定图表的配置项和数据

        myChart.setOption(option);
    });
</script>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div align="center">
    <div id="main" style="width: 600px;height:400px;">
    </div>
</div>