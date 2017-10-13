<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
  <head>

		<title>试题管理</title>
		<%@include file="include/head_files.jsp" %>
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
							<h1><i class="fa fa-list-ul"></i> 添加知识分类 </h1>
						</div>
						<div class="page-content">

							<form id="point-add-form" style="margin-top:40px;"  action="admin/point-add">
								<div class="form-line form-field" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>题库：</span>
									<select id="field-input-select" class="df-input-narrow">
										<option value="-1">--请选择--</option>
										<c:forEach items="${fieldList }" var="item">
											<option value="${item.fieldId }">${item.fieldName }</option>
										</c:forEach>
									</select>
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-name" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>知识分类名：</span>
										<input type="text" class="df-input-narrow" id="name"><span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-memo" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>知识分类描述：</span>
										<input type="text" class="df-input-narrow" id="memo"><span class="form-message"></span>
									<br>
								</div>
								
								
								<div class="form-line">
									<input id="btn-save" value="确认添加" type="submit" class="df-submit btn btn-info">
								</div>
							</form>
							

						</div>
					</div>
				</div>
			</div>
		</div>

	    <%@include file="include/footer_cm_js.jsp"%>
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
		<script type="text/javascript" src="resources/js/add-point.js"></script>
		
	</body>
</html>