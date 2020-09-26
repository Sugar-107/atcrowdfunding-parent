<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 程文辉
  Date: 2020/9/19
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="${PATH}/static/jquery/jquery-2.1.1.min.js"></script>
    <script src="${PATH}/static/layer/layer.js"></script>
    <%@ include file="/WEB-INF/common/css.jsp" %>

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

<%@include file="/WEB-INF/common/nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/common/side.jsp"%>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form id="condForm" action="${PATH}/admin/index" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyWord" value="${param.keyWord}" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" onclick="$('#condForm').submit()" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px; " id="onDel"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${PATH}/toadd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="fatherCheck" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pageInfo.list}" var="tadmin" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td><input class="childCheck" id="${tadmin.id}" type="checkbox"></td>
                                    <td>${tadmin.loginacct}</td>
                                    <td>${tadmin.username}</td>
                                    <td>${tadmin.email}</td>
                                    <td>
                                        <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                        <button type="button" onclick="window.location.href = '${PATH}/update?id=${tadmin.id}&pageNum=${pageInfo.pageNum}'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                        <button type="button" onclick="window.location.href = '${PATH}/delete?id=${tadmin.id}&pageNum=${pageInfo.pageNum}'"  class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <c:if test="${!pageInfo.isFirstPage}">
                                        <li><a href="${PATH}/admin/index?pageNum=${pageInfo.pageNum-1}&keyWord=${param.keyWord}">上一页</a></li>
                                        </c:if>
                                        <c:forEach items="${pageInfo.navigatepageNums}" var="i">
                                            <c:if test="${i==pageInfo.pageNum}">
                                                <li class="active"><a href="${PATH}/admin/index?pageNum=${i}&keyWord=${param.keyWord}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <c:if test="${i!=pageInfo.pageNum}">
                                                <li><a href="${PATH}/admin/index?pageNum=${i}&keyWord=${param.keyWord}">${i}</a></li>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${pageInfo.hasNextPage}">
                                            <li><a href="${PATH}/admin/index?pageNum=${pageInfo.pageNum+1}&keyWord=${param.keyWord}">下一页</a></li>
                                        </c:if>
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

        $("#fatherCheck").click(function () {
            let checked = $("#fatherCheck").prop("checked");
            $(".childCheck").prop("checked",checked);
        });

        $("#onDel").click(function () {

            layer.confirm('确认删除吗？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                layer.msg('好的', {icon: 1});
                let ids = new Array();
                var checks = $(".childCheck:checked");
                console.log(checks);
                for (var i = 0 ; i <checks.length ; i++){
                    let id = checks[i].id;
                    ids.push(id);
                }
                location.href = "${PATH}/admin/delAll?ids="+ids;
            }, function(){
                layer.msg('取消成功', {
                    time: 20000, //20s后自动关闭
                    btn: ['关闭']
                });
            });


        });


    });
</script>
</body>
</html>

