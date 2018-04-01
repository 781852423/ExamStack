<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		
		<title>51jobpass</title>
		<%@include file="include/head_files.jsp" %>
		<style>
			.question-number{
				color: #5cb85c;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			
			.question-number-2{
				color: #5bc0de;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			.question-number-3{
				color: #d9534f;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			
			a.join-practice-btn{
				margin:0;
				margin-left:20px;
			}
			
			.content ul.question-list-knowledge{
				padding:8px 20px;
			}
			
			.knowledge-title{
				background-color:#EEE;
				padding:2px 10px;
				margin-bottom:20px;
				cursor:pointer;
				border:1px solid #FFF;
				border-radius:4px;
			}
			
			.knowledge-title-name{
				margin-left:8px;
			}
			
			.point-name{
				width:200px;
				display:inline-block;
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
							<h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
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
							<h1><i class="fa fa-list-ul"></i> 学员名单 </h1>
							
						</div>
						<div class="page-content">
							<input type="hidden" value="${examId }" id="exam-id-hidden">
							<div id="exampaper-list">
								<div class="table-search table-controller-item"	style="float: left; margin-top:10px;">
									
									<div class="input-group search-form" style="float: left;margin-right:20px;">
										<input type="text" class="form-control" placeholder="搜索学员" value="${searchStr }"
										id="txt-search">
										<span class="input-group-btn">
											<button class="btn btn-sm btn-default" type="button"
											id="btn-search" >
												<i class="fa fa-search"></i>搜索
											</button> </span>
									</div>
									<div class="btn-group table-controller-item" style="float: left">
										<button class="btn btn-default btn-sm" id="link-user-r-btn">
											<i class="fa fa-random"></i> 关联用户
										</button>
									</div>
									<div class="btn-group table-controller-item" style="float: left">
										<button class="btn btn-default btn-sm" id="link-user-group-r-btn">
											<i class="fa fa-random"></i> 关联用户组
										</button>
									</div>
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td>学员ID</td>									
											<td>过期时间</td>	
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${user2ExamList}" var="item">
											<tr>
												<td>									 
												  ${item.userName }
												</td>
												<td>
												  <fmt:formatDate value="${item.expiredTime}" pattern="yyyy-MM-dd HH:mm"/>
												 
												  
												</td>
												
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
	
									</tfoot>
								</table>
	
							</div>
							<div id="page-link-content">
								${pagingStr }
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 关联用户modal -->
		<div class="modal fade" id="link-user-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h6 class="modal-title" id="myModalLabel">关联用户</h6>
					</div>
					<div class="modal-body">
						<form id="link-user-form">
							<div class="form-line form-user-id" style="display: block;">
								<span class="form-label"><span class="warning-label"></span>用户ID：</span>
								<input type="text" class="df-input-narrow" id="name-add-link">
								<span class="form-message"></span> <br>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭窗口</button>
						<button id="link-user-btn" type="button" class="btn btn-primary">
							确定添加</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 管理用户组modal -->
		<div class="modal fade" id="link-user-group-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h6 class="modal-title" id="myModalLabel">关联用户组</h6>
					</div>
					<div class="modal-body">
						<form id="link-user-group-form">
							<div class="form-line form-user-id" style="display: block;">
								<fieldset>
									<c:forEach items="${groupList }" var="item">
										<label><input type="checkbox" value="${item.groupId }"/></label>
										<label>${item.groupName }</label>
										<br>
									</c:forEach>
								</fieldset>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭窗口</button>
						<button id="link-user-group-btn" type="button" class="btn btn-primary">
							确定添加</button>
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
		<script type="text/javascript" src="resources/js/exam-user-list.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#link-user-r-btn").click(function(){
				//	$("#link-user-btn").data("id",$(this).data("id"));
					$("#link-user-modal").modal({
						backdrop : true,
						keyboard : true
					});
				});
				$("#link-user-group-r-btn").click(function(){
				//	$("#link-user-btn").data("id",$(this).data("id"));
					$("#link-user-group-modal").modal({
						backdrop : true,
						keyboard : true
					});
				});
			});
		</script>
		
	</body>
</html>