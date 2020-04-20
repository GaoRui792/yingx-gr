<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#userTable").jqGrid({
            /* 初始化参数 */
            url:"${path}/user/pageUser", //请求地址,访问后台的url
            editurl:"${path}/user/cud",
            autowidth:true,
            styleUI:"Bootstrap",
            datatype:"json",	 //返回数据的类型
            mtype:"post",    /* 提交方式 默认为get */
            pager:"#userPage",
            rowNum:"4",
            rowList:[4,5,6],
            //multiselect:true,   //多选
            viewrecords:true,   //显示总条数
            colNames:["姓名","手机号","头像","签名","微信","状态","注册时间","性别","所在城市"],  //表头
            colModel:[
                {name:"username",align:"center",editable:true,width:90},   //数据
                {name:"phone",align:"center",editable:true,width:180},
                {name:"headImg",align:"center",editable:true,edittype:"file",
                    //参数含义:cellvalue 列的值, options 操作,rowObject 行对象
                    formatter:function (cellvalue) {
                        //return "<img src = '${path}/upload/"+cellvalue+"' width='70px'/>"
                        return "<img src = '"+cellvalue+"' width='70px'/>"
                    }
                },
                {name:"sign",align:"center",editable:true,width:120},
                {name:"weChat",align:"center",editable:true,width:180},
                {name:"status",align:"center",width:110,
                    //edittype:"select",editoptions:{value:{'正常':'正常','冻结':'冻结'}},
                    formatter:function (cellvalue,option,rowObject) {
                        if (cellvalue == "正常"){
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn-success btn'>正常</button>"
                        }else {
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn-danger btn'>冻结</button>"
                        }
                    }
                },
                {name:"createDate",align:"center",edittype:"date",datefmt:"yyyy-MM-dd"},
                {name:"sex",align:"center",editable:true},
                {name:"city",align:"center",editable:true}]
        }).jqGrid("navGrid","#userPage",{search:false,edit:false,addtext:"添加",deltext:"删除"}
            ,{closeAfterEdit:true}
            ,{closeAfterAdd:true,
                //提交之后
                afterSubmit:function (response) {
                    //文件上传,修改图片的路径
                    $.ajaxFileUpload({
                        url:"${path}/user/upload",
                        type:"post",
                        fileElementId:"headImg",
                        data:{id:response.responseText},
                        success:function () {
                            $("#userTable").trigger("reloadGrid");
                        }
                    })
                    return "shutdown";
                }
            }
            ,{});

        $("#sendPhone").click(function () {
            var phone = $("#phone").val();
            $.ajax({
                url:"${path}/user/sendPhone",
                type:"post",
                data:{phone:phone},
                success:function(data){
                    $("#userNav").prepend(
                        "<div class='alert alert-warning alert-dismissible' role='alert'>"+
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                        "<span aria-hidden='true'>&times;</span></button>"+
                        "<strong id='showMsg'>"+data+"</strong></div>"
                    )
                }
            })
        });

        $("#userPoi").click(function () {
            $.ajax({
                url:"${path}/user/userPoi",
                type:"post",
                data:{},
                success:function(data){
                    $("#userNav").prepend(
                        "<div class='alert alert-warning alert-dismissible' role='alert'>"+
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                        "<span aria-hidden='true'>&times;</span></button>"+
                        "<strong id='showMsg'>"+data+"</strong></div>"
                    )
                }
            })
        });
    });

    function updateStatus(id,status){
        if(status == "正常"){
            $.ajax({
                url:"${path}/user/cud",
                type:"post",
                data:{id:id,status:"冻结",oper:"edit"},
                success:function(data){
                    $("#userTable").trigger("reloadGrid");
                }
            })
        }else{
            $.ajax({
                url:"${path}/user/cud",
                type:"post",
                data:{id:id,status:"正常",oper:"edit"},
                success:function(data){
                    $("#userTable").trigger("reloadGrid");
                }
            })
        }
    }
</script>

<style>
    th {
        text-align: center;
    }
</style>

<div class="panel-danger panel">
    <%-- 面板头 --%>
    <div class="panel-heading panel">
        <h4>用户信息</h4>
    </div>
    <div class="container-fluid">
        <div class="col-sm-2">
            <button class="btn-warning btn" id="userPoi">导出用户信息</button>
        </div>
        <div class="col-sm-6 col-sm-offset-4">
            <%-- 发送验证码 --%>
            <div class="input-group">
                <input type="text" id="phone" class="form-control" placeholder="请输入手机号:" aria-describedby="basic-addon2">
                <span class="input-group-addon" id="sendPhone">发送验证码</span>
            </div>
        </div>
    </div>
    <%-- 标签页 --%>
    <div id="userNav" class="nav nav-tabs">
        <li class="active"><a href="#">用户信息</a></li>
        <%--初始化表单--%>
        <table id="userTable"></table>
        <%--分页工具栏--%>
        <div id="userPage"></div>
    </div>
</div>