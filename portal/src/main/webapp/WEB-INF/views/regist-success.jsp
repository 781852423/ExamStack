<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职梦靠岸 注册成功</title>
		
		 <%@include file="include/head_files.jsp" %>
		
	</head>
	<body>
		 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
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

		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>
