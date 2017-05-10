<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- base href="http://localhost:8080/Portal/" -->
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-cn"> <!-- 标明是中文 -->
    <head>
        <base href="<%=basePath%>">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <!-- 使用以下代码强制 IE 使用 Chrome Frame 渲染 -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>职梦靠岸官网-银行招聘_求职辅导培训_烟草公司招聘笔试面试_真题分享_模拟训练_笔试面试经验分享</title>
        
        <!-- 删除苹果默认的工具栏和菜单栏。 -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        
        <meta name="keywords" content="银行招聘,烟草招聘,中石油招聘,中石化招聘,进出口银行,求职辅导培训,烟草公司招聘笔试面试,真题分享,模拟训练,笔试面试经验分享">
        <meta name="description" content="专注银行、证券、石油、烟草、大型国企招聘求职辅导培训真题分享_模拟训练_笔试面试经验分享">
        
        <link rel="shortcut icon" href="<%=basePath%>resources/images/mainfavicon.ico" />
        <link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
        <link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="resources/css/style.css" rel="stylesheet">
        <!-- id选择符前面应该加前缀符号‘#’，而class选择符前面应该加前缀符号‘.’ 
             在CSS中margin是指从自身边框到另一个容器边框之间的距离，就是容器外距离。
             在CSS中padding是指自身边框到自身内部另一个容器边框之间的距离，就是容器内距离。
        -->
        <style type="text/css">
            .form-group {
                margin-bottom: 5px;
                height: 59px;
            }
            .form-horizontal .control-label{
                padding-top: 0px;
            }
        </style>
    </head>
    <body>
        <header>
            <div class="container">
                <div class="row">
                  <!-- .col-xs-*超小屏幕手机(768px),.col-sm-*小屏幕平板(≥768px),.col-md-*中等屏幕桌面显示器
                    col-xs-*超小屏幕 手机 (<768px),
                    .col-sm-*小屏幕 平板 (≥768px),
                    .col-md-*中等屏幕 桌面显示器 (≥992px)(栅格参数).
                    -*表示占列，即占自动每行row分12列栅格系统比；
                                                          不管在哪种屏幕上，栅格系统都会自动的每行row分12列 col-xs-*和col-sm-* 和col-md-*后面跟的参数表示在当前的屏幕中 每个div所占列数。
                                                           例如 <div class="col-xs-6 col-md-3"> 这个div在屏幕中占的位置是：
                     .col-xs-6 在超小屏幕中占6列 也就是屏幕的一半
                    -->
                    <div class="col-xs-5">
                        <div class="logo">
                            <h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
                        </div>
                    </div>
                    <!-- 检查是否登陆，登陆则显示用户信息，否则只显示注册信息和登陆按钮 -->
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
                <nav class="collapse navbar-collapse bs-navbar-collapse"
                role="navigation">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="home"><i class="fa fa-home"></i>主页</a>
                        </li>
                        <li>
                            <a href="student/practice-list"><i class="fa fa-edit"></i>试题练习</a>
                        </li>
                        <li>
                            <a href="exam-list"><i class="fa  fa-paper-plane-o"></i>在线考试</a>
                        </li>
                        <li>
                            <a href="training-list"><i class="fa fa-book"></i>培训资料</a>
                        </li>
                        <li>
                            <a href="student/usercenter"><i class="fa fa-dashboard"></i>会员中心</a>
                        </li>
                        <li>
                            <a href="student/setting"><i class="fa fa-cogs"></i>个人设置</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <!-- Navigation bar ends -->

        <div class="content" style="margin-bottom: 100px;">

            <div class="container">
                <div class="row">

                    <div class="col-md-12">
                        <div class="lrform">
                            <h5>登陆 </h5>
                            <div class="form">
                                <!-- Login form (not working) 
                                调用com.examstack.portal.security.filter.AuthenticationFilter验证，再security.xml中定义-->
                                <form class="form-horizontal" action="j_spring_security_check" method="post">
                                    <!-- Username -->
                                    <div class="form-group">
                                        <label class="control-label col-md-3" for="username">用户名</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" name="j_username">
                                        </div>
                                    </div>
                                    <!-- Password -->
                                    <div class="form-group">
                                        <label class="control-label col-md-3" for="password">密码</label>
                                        <div class="col-md-9">
                                            <input type="password" class="form-control" name="j_password">
                                        </div>
                                    </div>
                                    <!-- Buttons -->
                                    <div class="form-group">
                                        <!-- Buttons -->
                                        <div class="col-md-9 col-md-offset-3">
                                            <button type="submit" class="btn btn-default">
                                                                                                                                 登陆
                                            </button>
                                            <button type="reset" class="btn btn-default">
                                                                                                                                取消
                                            </button>
                                            <span class="form-message">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                                        </div>
                                    </div>
                                </form>
                                没有账号? <a href="user-register">注册</a>
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
                                职梦靠岸  Copyright © <a href="http://www.51jobpass.com/" target="_blank">职梦靠岸</a> - <a href="." target="_blank">主页</a> | <a href="http://www.51jobpass.com/" target="_blank">关于我们</a> | <a href="http://www.51jobpass.com/" target="_blank">FAQ</a> | <a href="http://www.51jobpass.com/" target="_blank">联系我们</a>
                            </p>
                        </div>
                    </div>
                </div>

            </div>

        </footer>

        <!-- Slider Ends -->

        <!-- Javascript files -->
        <!-- jQuery -->
        <script type="text/javascript"
        src="resources/js/jquery/jquery-1.9.0.min.js"></script>
        <!-- Bootstrap JS -->
        <script type="text/javascript"
        src="resources/bootstrap/js/bootstrap.min.js"></script>

    </body>
</html>