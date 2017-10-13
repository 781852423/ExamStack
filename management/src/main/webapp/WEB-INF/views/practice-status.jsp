<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
  <head>
		<title>学习进度</title>
		<%@include file="include/head_files.jsp" %>
		<style type="text/css">
			#training-table{
				cursor:pointer;
				
			}
			#training-table tr:hover{
				background-color:#EEE;
			}	
			#field-switch{
				margin:15px 0 0 15px;;
				height:34px;
				width:200px;
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
					<div class="col-xs-9">
						<div class="page-header">
							<h1> <i class="fa fa-bar-chart-o"></i> 学习进度</h1>
						</div>
						<div class="page-content" style="border-bottom:0px;">
							<!-- 在这里添加一个专业的选择按钮  -->
							<select id="field-switch" class="form-control">
								<c:forEach items="${fieldList }" var="item">
									
									<option value="${item.fieldId }">${item.fieldName }</option>
									
									
								</c:forEach>
								<!-- <option value="4">公务员申论</option>
								<option value="5">医药行业考试</option> -->
							</select>
							<div class="col-xs-12">
								<div id="question-list">
									
									<c:forEach items="${kparl }" var="item">
										<table class="table-striped table">
											<thead>
												<tr>
													<td colspan="4">
														<h6>${item.knowledgePointName }</h6>
														<span style="color:#428bca;">学习进度：<fmt:formatNumber value="${item.finishRate }" type="percent"/></span>
													</td>
												</tr>
												<tr>
													<td>题型</td><td>未做</td><td>做对题目</td><td>做错题目</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${item.typeAnalysis }" var="i">
													<tr>
														<td>${i.questionTypeName }</td>
														<td><span class="span-info">${i.restAmount }</span> </td>
														<td><span class="span-success">${i.rightAmount }</span></td>
														<td><span class="span-danger">${i.wrongAmount }</span> </td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot></tfoot>
										</table>
									</c:forEach>
									
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
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/training-list.js"></script>
		<script type="text/javascript" src="resources/js/training-file-upload.js"></script>
		<script type="text/javascript" src="resources/js/add-training-section.js"></script>
		<script>
			$(function() {
				$("#training-table tr").click(function(){
					console.log("load section.......");
					
					  $('#section-content').load('admin/training/section-list/' + $($(this).find("td")[1]   ).find("a").data("id"));
					  
					  $(".add-section-btn").show();
					  
					  $("#training-name").val($(    $(this).find("td")[1]     ).find("a").text()                                  );
					  $("#training-add-id").val($(    $(this).find("td")[1]     ).find("a").data("id")                                  );
					  console.log($("#training-add-id").val());
				});
				
				
				$(".add-section-btn").click(function(){
						$("#add-section-modal").modal({
							backdrop : true,
							keyboard : true
						});
					
				});
				
				$("#field-switch").val("${fieldId}");
				$("#field-switch").change(function(){
					//alert(123);
					window.location.href= util.getCurrentRole() + "/training/student-practice-status/"+ $(this).val() +"/30";
				});
			});
		</script>
	</body>
</html>