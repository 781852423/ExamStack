<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    	
		<title>教师管理</title>
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
									<a href="logout"><i class="fa fa-sign-out"></i> 退出</a>
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
							<h1><i class="fa fa-bar-chart-o"></i> 专业题库 </h1>
						</div>
						<div class="page-content">
							<div id="field-list">
								<div class="table-controller">
									<div class="btn-group table-controller-item" style="float:left">
										<button class="btn btn-default btn-sm" id="add-field-modal-btn">
											<i class="fa fa-plus-square"></i> 添加专业题库
										</button>
									</div>	
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td>ID</td>
											<td>题库名</td>
											<td>描述</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${fieldList }" var="item">
											<tr>
												<td>${item.fieldId }</td>
												<td>${item.fieldName }</td>
												<td>${item.memo }</td>
												<td>
													<c:choose>
														<c:when test="${item.removeable }">
															<span class="delete-btn btn-sm btn-danger" data-id="${item.fieldId }"><i class="ace-icon fa fa-trash-o"></i></span>
														</c:when>
														<c:otherwise>
															<span class="btn-sm" data-id="${item.fieldId }" disabled="disabled"><i class="ace-icon fa fa-ban"></i></span>
														</c:otherwise>
													</c:choose>	
												</td>
											</tr>
										</c:forEach>
										
									</tbody><tfoot></tfoot>
								</table>
							</div>
							<div id="page-link-content">
								<ul class="pagination pagination-sm">${pageStr}</ul>
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
						<div class="modal fade" id="add-field-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												&times;
											</button>
											<h6 class="modal-title" id="myModalLabel">添加专业题库</h6>
										</div>
										<div class="modal-body">
											<form id="field-add-form" style="margin-top:40px;"  action="admin/common/field-add">
												<div class="form-line form-field-name" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>名称：</span>
													<input type="text" class="df-input-narrow" id="name">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-field-desc" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>描述：</span>
													<input type="text" class="df-input-narrow" id="memo">
													<span class="form-message"></span>
													<br>
												</div>
												
											</form>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭窗口
											</button>
											<button id="add-field-btn" type="button" class="btn btn-primary">
												确定添加
											</button>
										</div>
									</div>
								</div>
							</div>		
		</footer>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/field-list.js"></script>
		<script type="text/javascript" src="resources/js/add-field.js"></script>
		<script>
			$(function() {
				$("#add-field-modal-btn").click(function() {
					$("#add-field-modal").modal({
						backdrop : true,
						keyboard : true
					});
	
				});
			});
		</script>
	</body>
</html>