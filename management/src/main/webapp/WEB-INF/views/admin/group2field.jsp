<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String servletPath = (String)request.getAttribute("javax.servlet.forward.servlet_path");
String[] list = servletPath.split("\\/");
request.setAttribute("role",list[1]);
request.setAttribute("topMenuId",list[2]);
request.setAttribute("leftMenuId",list[3]);
%>

<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>用户组与题库关联管理 </title>
        <meta name="keywords" content="">
        <link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
        <link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
        <link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="resources/css/style.css" rel="stylesheet">

        <style type="text/css">
            .disable-btn, .enable-btn {
                text-decoration: underline;
            }
            .disable-btn, .enable-btn {
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <header>
            <span style="display:none;" id="rule-role-val"><%=list[1]%></span>
            <div class="container">
                <div class="row">
                    <div class="col-xs-5">
                        <div class="logo">
                            <h1><a href="#">网站管理系统</a></h1>
                            <div class="hmeta">
                                专注互联网在线考试解决方案
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-7" id="login-info">
                        <c:choose>
                            <c:when test="${not empty sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
                                <div id="login-info-user">

                                    <a href="user-detail/${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}" id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
                                    <span>|</span>
                                    <a href="j_spring_security_logout"><i class="fa fa-sign-out"></i> 退出</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-primary" href="user-register">用户注册</a>
                                <a class="btn btn-success" href="user-login-page">登录</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </header>
        <!-- Navigation bar starts -->

        <div class="navbar bs-docs-nav" role="banner">
            <div class="container">
                <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <c:import url="/common-page/top-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />
                </nav>
            </div>
        </div>

        <!-- Navigation bar ends -->

        <!-- Slider starts -->

        <div>
            <!-- Slider (Flex Slider) -->

            <div class="container" style="min-height:500px;">

                <div class="row">
                    <div class="col-xs-2" id="left-menu">
                        <c:import url="/common-page/left-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />
                    </div>
                    <div class="col-xs-10" id="right-content">
                        <div class="page-header">
                            <h1><i class="fa fa-list-ul"></i>用户组与题库关联管理 </h1>
                        </div>
                        <div class="page-content row">
                            <div class="col-xs-2" id="left-menu">
                                <div class="list-group user-group-nav" style="margin-top: 0px;">
                                    <div class="list-group-item group-nav-item active" data-id="0">
                                        全部分组
                                    </div>
                                    <c:forEach items="${groupList }" var="item">
                                        <div class="list-group-item group-nav-item" data-id="${item.groupId }">
                                            ${item.groupName } <span class="action-span"> <i class="fa fa-pencil action-btn edit-group-btn"></i><i class="fa fa-trash-o action-btn delete-group-btn"></i> </span>
                                        </div>
                                    </c:forEach>

                                </div>
                                <div class="list-group">
                                    <a id="add-group" href="javascript:void(0);" class="list-group-item" data-id="13" style="background-color: #47a447;color:#FFF;"><i class="fa fa-plus-square"></i> 添加分组</a>
                                </div>
                            </div>

                            <div class="col-xs-10">
                                <iframe width="100%" id="group2field-iframe" style="height: 800px;" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
                            </div>

                            <div class="modal fade" id="add-group-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h6 class="modal-title" id="myModalLabel">创建分组</h6>
                                        </div>
                                        <div class="modal-body">
                                            <form id="question-edit-form">
                                                <div class="form-line form-group-name" style="display: block;">
                                                    <span class="form-label"><span class="warning-label"></span>分组名：</span>
                                                    <input type="text" class="df-input-narrow" id="group-name">
                                                    <span class="form-message"></span>
                                                    <br>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                                关闭窗口
                                            </button>
                                            <button id="add-group-btn" type="button" class="btn btn-primary df-submit">
                                                确定添加
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="edit-group-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h6 class="modal-title" id="myModalLabel">修改分组</h6>
                                        </div>
                                        <div class="modal-body">
                                            <form id="group-edit-form">

                                                <div class="form-line form-group-name" style="display: block;">
                                                    <span class="form-label"><span class="warning-label"></span>分组名：</span>
                                                    <input type="text" class="df-input-narrow" id="group-name-edit">
                                                    <span class="form-message"></span>
                                                    <br>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                                关闭窗口
                                            </button>
                                            <button id="update-group-btn" type="button" class="btn btn-primary df-submit">
                                                确定修改
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="add-field2group-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h6 class="modal-title" id="myModalLabel">添加题库权限到用户组</h6>
                                        </div>
                                        <div class="modal-body">
                                            <form id="user-add-form" style="margin-top:40px;"  action="secure/add-user">
                                                <div class="form-line form-group-id" style="display: none;">
                                                    <span class="form-label"><span class="warning-label">*</span>id：</span>
                                                    <input type="text" class="df-input-narrow" id="group-add-id" value="" disabled="disabled">
                                                    <span class="form-message"></span>
                                                    <br>
                                                </div>
                                                <div class="form-line form-group" style="display: block;">
                                                    <span class="form-label"><span class="warning-label">*</span>添加题库权限到：</span>
                                                    <input type="text" class="df-input-narrow" id="group-add" value="默认分组" disabled="disabled">
                                                    <span class="form-message"></span>
                                                    <br>
                                                </div>
                                                <div class="form-line form-username" style="display: block;">
                                                    <span class="form-label"><span class="warning-label">*</span>题库：</span>
                                                    <input type="text" class="df-input-narrow" id="name-add" maxlength="20">
                                                    <span class="form-message"></span>
                                                    <br>
                                                </div>

                                            </form>

                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                                关闭窗口
                                            </button>
                                            <button id="add-field-btn" data-action="secure/add-group2filed" data-url="secure/user/group2filed" type="button" class="btn btn-primary">
                                                确定添加
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="copy">
                            <p>
                                51jobpass.com Copyright © <a href="http://www.51jobpass.com/" target="_blank">51jobpass</a> - <a href="." target="_blank">主页</a> | <a href="http://www.51jobpass.com/" target="_blank">关于我们</a> | <a href="http://www.51jobpass.com/" target="_blank">FAQ</a> | <a href="http://www.51jobpass.com/" target="_blank">联系我们</a>
                            </p>
                        </div>
                    </div>
                </div>

            </div>

        </footer>

        <!-- Slider Ends -->

        <!-- Javascript files -->
        <!-- jQuery -->
        <script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
        <script type="text/javascript" src="resources/js/all.js"></script>

        <!-- Bootstrap JS -->
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="resources/js/group-manage.js"></script>
        <script type="text/javascript" src="resources/js/add-user.js"></script>
        <script type="text/javascript" src="resources/js/update-user.js"></script>
        <script>
            $(function() {
                /* $(".left-menu-item-name").hide(); */

                $("#add-group").click(function() {
                    $("#add-group-modal").modal({
                        backdrop : true,
                        keyboard : true
                    });

                });
                $(".edit-group-btn").click(function(e) {
                    var selectGroupName = $(this).parent().parent().text().trim();
                    var selectGroupId = $(this).parent().parent().data("id");

                    $("#group-name-edit").val(selectGroupName);
                    $("#group-name-edit").data("id", selectGroupId);
                    $("#edit-group-modal").modal({
                        backdrop : true,
                        keyboard : true
                    });
                    e.preventDefault();
                    return false;
                });

                $(".user-group-nav .group-nav-item").click(function() {
                    var id = $(this).data("id");

                    document.getElementById('group2field-iframe').src = "<%=list[1]%>/user/inner/user-list/" + id;
                    $(".group-nav-item").removeClass("active");
                    $(this).addClass("active");

                    return false;
                });

                var items = $(".group-nav-item");
                $(items[0]).addClass("active");
                document.getElementById('group2field-iframe').src = "<%=list[1]%>/user/inner/user-list/" + $(items[0]).data("id");

                $("#link-user-btn-r").click(function(){
                    var groupId = $("#group-add-link-r").val();
                    var userId = $("#name-add-link-r").val();
                    $.ajax({
                        headers : {
                            'Accept' : 'application/json',
                            'Content-Type' : 'application/json'
                        },
                        type : "GET",
                        url : "secure/add-user-group-" + userId + "-" + groupId,
                        success : function(message, tst, jqXHR) {
                            if (!util.checkSessionOut(jqXHR))
                                return false;
                            if (message.result == "success") {
                                
                                util.success("操作成功", function() {
                                //  window.location.reload();
                                
                                });
                                $("#link-user-modal-r").modal('hide');
                            } else {
                                util.error("操作失败请稍后尝试:" + message.result);
                            }

                        },
                        error : function(jqXHR, textStatus) {
                            util.error("操作失败请稍后尝试");
                        }
                    });
                    return false;
                });
                
                $("#link-user-btn").click(function(){
                    var groupId = $(".user-group-nav .active").data("id");
                    var userNames = $("#name-add-link").val();
                    $.ajax({
                        headers : {
                            'Accept' : 'application/json',
                            'Content-Type' : 'application/json'
                        },
                        type : "POST",
                        data : userNames,
                        url : "secure/add-user-group-" + groupId,
                        success : function(message, tst, jqXHR) {
                            if (!util.checkSessionOut(jqXHR))
                                return false;
                            if (message.result == "success") {
                                util.success("操作成功", function() {
                                    window.location.reload();
                                });
                            } else {
                                util.error("操作失败请稍后尝试:" + message.result);
                            }

                        },
                        error : function(jqXHR, textStatus) {
                            util.error("操作失败请稍后尝试");
                        }
                    });
                    return false;
                });
            });

        </script>
    </body>
</html>