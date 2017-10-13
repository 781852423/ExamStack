<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		
		<title>ExamStack 注册成功</title>
		<%@include file="include/head_files.jsp" %>
		
	</head>
	<body>
		<header>
			<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
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
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li>
							<a href="start-exam"><i class="fa fa-edit"></i>试题练习</a>
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

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="height:500px;margin-top: 50px;">
				<h2>欢迎注册ExamStack</h2>
				<p class="big grey">
					您的账号为<span>${username}</span>
				</p>
				<a href="user-login-page" class="btn btn-success"><i class="fa fa-sign-in"></i> 登陆系统</a>
				<hr>
				<div class="link-list">
					<h5>您可以选择访问其他页面</h5>
					<a href="#">主页</a><a href="#">试题练习</a><a href="#">练习历史</a><a href="#">联系我们</a><a href="#">FAQ</a>
				</div>
				<div class="row">

				</div>
			</div>
		</div>

		 <%@include file="include/footer_cm_js.jsp"%>

	</body>
</html>
