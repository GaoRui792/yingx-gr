<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        jQuery("#categoryTable").jqGrid(
            {
                url : "${path}/category/page?pageLevel=1",
                editurl:"${path}/category/cud?levels=1",
                autowidth:true,
                styleUI:"Bootstrap",
                datatype:"json",	 //返回数据的类型
                mtype:"post",    /* 提交方式 默认为get */
                pager:"#categoryPage",
                rowNum:"4",
                rowList:[4,5,6],
                //multiselect:true,   //多选
                viewrecords:true,   //显示总条数
                colNames : [ 'ID', '类别名', '级别'],
                colModel : [
                    {name : 'id',align:"center"},
                    {name : 'cateName',align:"center",editable:true},
                    {name : 'levels',hidden:true},
                ],
                subGrid : true, //是否开启子表格
                subGridRowExpanded : function(CategoryTwo, ParentId) {
                    //设置子表格的相关属性
                    /*
                    * subgrid_id  当点击一行时,会在父表格中创建一个容纳子表格的div 为div的id
                    * row_id  点击行的id
                    * */

                    //创建容纳子表格的table和分页所需要的div
                    var CategoryTwoTable = CategoryTwo + "_t";
                    var categoryTwoPage = "p_" + ParentId;
                    $("#" + CategoryTwo).html(
                        "<table id='"+CategoryTwoTable+"' class='scroll'></table>" +
                        "<div id='"+categoryTwoPage+"' class='scroll'></div>");

                    $("#" + CategoryTwoTable).jqGrid({
                        url : "${path}/category/page?pageLevel=2&parentId="+ParentId,
                        editurl:"${path}/category/cud?levels=2&parentId="+ParentId,
                        autowidth:true,
                        styleUI:"Bootstrap",
                        datatype:"json",	 //返回数据的类型
                        mtype:"post",    /* 提交方式 默认为get */
                        pager : "#"+categoryTwoPage,
                        rowNum:"4",
                        rowList:[4,5,6],
                        viewrecords:true,   //显示总条数
                        colNames : [ 'ID', '类别名', '级别','父级ID' ],
                        colModel : [
                            {name : 'id',align:"center"},
                            {name : 'cateName',align:"center",editable:true},
                            {name : 'levels',hidden:true},
                            {name : 'parentId',align:"center"}
                        ],
                    });

                    $("#" + CategoryTwoTable).jqGrid('navGrid',
                        "#" + categoryTwoPage, {search:false}
                        ,{closeAfterEdit:true}
                        ,{closeAfterAdd:true}
                        ,{});
                }
            });
        jQuery("#categoryTable").jqGrid('navGrid', '#categoryPage', {search:false,addtext:"添加",edittext:"修改",deltext:"删除"}
        ,{closeAfterEdit:true}
        ,{closeAfterAdd:true}
        ,
            {
                //删除方法
                afterSubmit:function (response) {
                    console.log(response)
                    if (response.responseJSON.status == "400") {
                        $("#categoryNav").prepend(
                            "<div class='alert alert-danger alert-dismissible' role='alert'>"+
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                            "<span aria-hidden='true'>&times;</span></button>"+
                            "<strong id='showMsg'>"+response.responseJSON.message+"</strong></div>"
                        )
                    }
                    return "message";
                }
            }
        );
    });
</script>
<style>
    th {
        text-align: center;
    }
</style>

<div id="categoryPanel" class="panel-success panel">
    <%-- 面板头 --%>
    <div class="panel-heading panel">
        <h4>类别信息</h4>
    </div>
    <%-- 标签页 --%>
    <div id="categoryNav" class="nav nav-tabs">
        <li class="active"><a href="#">类别信息</a></li>
        <%--初始化表单--%>
        <table id="categoryTable"></table>
        <%--分页工具栏--%>
        <div id="categoryPage"></div>
    </div>

</div>