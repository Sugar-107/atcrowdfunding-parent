<%--
  Created by IntelliJ IDEA.
  User: 程文辉
  Date: 2020/9/22
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <%@ include file="/WEB-INF/common/css.jsp"%>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

    <%@ include file="/WEB-INF/common/nav.jsp"%>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/common/side.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="condInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" id="conBtn" ><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" id="deleteBathBtn" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="addBtn" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="fatherCheck" ></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTable">

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">添加角色信息</h4>
                </div>
                <div class="modal-body">
                    <form role="form" method="post" id="roleSave" >
                        <input type="hidden" name="id" />
                        <div class="form-group">
                            <label for="exampleInputPassword1">角色名称</label>
                            <input type="text" name="roleName" class="form-control" id="exampleInputPassword1"
                                   placeholder="请输入用户名称" >
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="btnSave">保存</button>
                </div>
            </div>
        </div>
    </div>

<%@include file="/WEB-INF/common/js.jsp"%>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        loadData(1);//页面静态元素加载完毕之后立马异步获取数据
    });
    var keyword;

    $("#conBtn").click(function () {
        keyword = $("#condInput").val();
        loadData(1);
    });


    function loadData(pageNum) {
        $.getJSON(
            "${applicationScope.PATH}/role/loadData",
            {"pageNum":pageNum,"pageSize":2,"keyWord":keyword},
            function (pageInfo) {
                loadRole(pageInfo.list);
                loadPage(pageInfo);
            });
    }

    function loadRole(list) {
        var  context = "";
        for (var  i = 0 ; i < list.length ; i++){
            context+='<tr>'
            context+='	<td>'+(i+1)+'</td>'
            context+='	<td><input class="childCheck" type="checkbox" name="'+list[i].id+'" ></td>'
            context+='	<td>'+list[i].name+'</td>'
            context+='	<td>'
            context+='	  <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>'
            context+='	  <button type="button" onclick="updateRole('+list[i].id+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>'
            context+='	  <button type="button" onclick="deleteOne('+list[i].id+')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>'
            context+='	</td>'
            context+='</tr>'
        }

        $("#roleTable").html(context);

    }

    function loadPage(pageInfo) {
        var  context = "";

        if (pageInfo.isFirstPage) {
            context += '<li class="disabled"><a href="#">上一页</a></li>';
        } else {
            context += '<li ><a  onclick="loadData(' + (pageInfo.pageNum - 1) + ')">上一页</a></li>';
        }
        for (var  i = 0 ; i < pageInfo.navigatepageNums.length;i++){
             if (pageInfo.pageNum==pageInfo.navigatepageNums[i]){
                 context+='<li class="active"><a onclick="loadData('+pageInfo.navigatepageNums[i]+')">'+pageInfo.navigatepageNums[i]+' <span class="sr-only">(current)</span></a></li>'
            }else {
                 context+='<li><a onclick="loadData('+pageInfo.navigatepageNums[i]+')">'+pageInfo.navigatepageNums[i]+'</a></li>'
            }
        }
        if (pageInfo.isLastPage) {
            context += '<li class="disabled"><a href="#">下一页</a></li>';
        } else {
            context += '<li ><a  onclick="loadData(' + (pageInfo.pageNum + 1) + ')">下一页</a></li>';
        }

        $(".pagination").html(context);

    }
    //添加按钮
    $("#addBtn").click(function () {
        //模态框的行为
        $('#myModal').modal({
            show: true,
            backdrop: false,//'static'
            keyboard: false
        });
    });




    $("#btnSave").click(function () {
        let id = $("input[name='id']").val();
        if (id==null||id==""){
            $.post("${applicationScope.PATH}/role/saveRole",{"id":id,"name":$("input[name='roleName']").val()},
                function (res) {
                    if (res=="true"){
                        layer.alert("添加成功");
                    }else {
                        layer.alert("添加失败")
                    }
                    $("#myModal").modal("hide");
                    loadData(100*1000);

                });
        }else {
            $.post("${applicationScope.PATH}/role/saveRole",{"id":id,"name":$("input[name='roleName']").val()},
                function (res) {
                    if (res=="true"){
                        layer.alert("保存成功");
                    }else {
                        layer.alert("保存失败")
                    }
                    $("#myModal").modal("hide");
                    loadData(100*1000);
                });
        }

    });

    $("#fatherCheck").click(function () {
        let checked = $("#fatherCheck").prop("checked");
        $(".childCheck").prop("checked",checked);
    });


    $("#deleteBathBtn").click(function () {
        var checks = $(".childCheck:checked");
        var ids = new Array();
        console.log(checks);
        for (var i = 0 ; i <checks.length ; i++){
            let id = checks[i].name;
            ids.push(id);
        }
        $.post("${applicationScope.PATH}/role/deleteAll",{"ids":ids.toString()},function (res) {
            if (res=="true"){
                layer.alert("删除成功");
            }else {
                layer.alert("删除失败");
            }
        });

    });

    function updateRole(id) {
        $.getJSON("${applicationScope.PATH}/role/getOne?id="+id,null,function (res) {
            $("input[name='id']").val(res.id);
            $("input[name='roleName']").val(res.name);
            $('#myModal').modal({
                show: true,
                backdrop: false,//'static'
                keyboard: false
            });
        });
    }

    function deleteOne(id) {
        $.getJSON("${applicationScope.PATH}/role/deleteOne?id="+id, null, function (res) {
            if (res==true){
                layer.alert("删除成功")
            }else {
                layer.alert("删除失败")
            }
        });
    }




</script>
</body>
</html>

