<%--
  Created by IntelliJ IDEA.
  User: 程文辉
  Date: 2020/9/20
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <c:forEach items="${fatherTMenu}" var="Tmenu">
                <c:if test="${Tmenu.childTMenu.size()==0}">
                    <li class="list-group-item tree-closed" >
                        <a href="${PATH}/${Tmenu.url}"><i class="glyphicon glyphicon-dashboard"></i> ${Tmenu.name}</a>
                    </li>
                </c:if>

                <c:if test="${Tmenu.childTMenu.size()!=0}">
                    <li class="list-group-item tree-closed">
                        <span><i class="glyphicon glyphicon glyphicon-tasks"></i> ${Tmenu.name} <span class="badge" style="float:right">${Tmenu.childTMenu.size()}</span></span>
                        <ul style="margin-top:10px;display:none;">
                            <c:forEach items="${Tmenu.childTMenu}" var="child">
                                <li style="height:30px;">
                                    <a href="${PATH}/${child.url}"><i class="glyphicon glyphicon-user"></i> ${child.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>