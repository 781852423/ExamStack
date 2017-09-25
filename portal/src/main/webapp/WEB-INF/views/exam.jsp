<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<%@include file="include/head_files.jsp" %>
		<title>在线模拟考试中心</title>
		<style>
			.table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {
				padding: 8px 0;
				line-height: 1.42857143;
				vertical-align: middle;
				border-top: 1px solid #ddd;
			}

			a.join-practice-btn {
				margin-top: 0;
			}
		</style>

	</head>

	<body>
		<%@include file="include/body_header.jsp" %>
		<!-- Navigation bar starts -->

		<%@include file="include/commen_naviBar.jsp" %>
		<!-- Navigation bar ends -->

		<!-- Slider starts -->
		<div class="content" style="margin-bottom: 100px;">

			<div class="container" style="margin-top: 40px;">
				
				<div class="row">
					<div class="col-xs-6">
						<div style="border-bottom: 1px solid #ddd;">
							<h3 class="title"><i class="fa fa-cloud-upload"></i> 最近发布的考试</h3>
							
						</div>

						<div class="question-list">

							<table class="table-striped table">
								<thead>
									
									<tr>
										<td>考试名称</td><td>开始日期</td><td>截止日期</td><td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${examListToApply }" var="item">
										<tr>
											<td>${item.examName }</td>
											<td><span class="span-info question-number"><fmt:formatDate value="${item.effTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td><span class="span-success question-number-2"><fmt:formatDate value="${item.expTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td>
												<c:choose>
													<c:when test="${userId == 0 }">
														<a class="btn btn-success btn-sm" href="user-login-page">申请</a>
													</c:when>
													<c:otherwise>
														<button class="btn btn-success btn-sm join-practice-btn apply-exam-btn" data-id="${item.examId }">申请</button>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>

							
							

						</div>
					</div>
					<div class="col-xs-6">
						<div style="border-bottom: 1px solid #ddd;">
							<h3 class="title"><i class="fa fa-paper-plane-o"></i> 我的考试</h3>
							
						</div>
						<div class="question-list">

							<table class="table-striped table">
								<thead>
									
									<tr>
										<td>考试名称</td><td>开始日期</td><td>截止日期</td><td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${examListToStart }" var="item">
										<tr>
											<td>${item.examName }</td>
											<td><span class="span-info question-number"><fmt:formatDate value="${item.effTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td><span class="span-success question-number-2"><fmt:formatDate value="${item.expTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td>
												<c:choose>
													<c:when test="${item.approved == 1 }">
														<a href="student/exam-start/${item.examId }/1" class="btn btn-success btn-sm join-practice-btn">参加考试</a>
													</c:when>
													<c:when test="${item.approved == 3 }">
														<a href="student/student-answer-sheet/${item.examId }" class="btn btn-success btn-sm join-practice-btn">查看详情</a>
													</c:when>
													<c:when test="${item.approved == 2 }">
														<a class="btn btn-warning btn-sm">已交卷</a>
													</c:when>
													<c:when test="${item.approved == 0 }">
														<a class="btn btn-warning btn-sm">待审核</a>
													</c:when>
												</c:choose>
												
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>

						</div>
						<div style="border-bottom: 1px solid #ddd;">
							<h3 class="title"><i class="fa fa-paper-plane-o"></i> 模拟考试</h3>
							
						</div>
						<div class="question-list">

							<table class="table-striped table">
								<thead>
									
									<tr>
										<td>考试名称</td><td>开始日期</td><td>截止日期</td><td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${modelTestToStart }" var="item">
										<tr>
											<td>${item.examName }</td>
											<td><span class="span-info question-number"><fmt:formatDate value="${item.effTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td><span class="span-success question-number-2"><fmt:formatDate value="${item.expTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
											<td>
												<a href="student/model-test-start/${item.examId }/1" class="btn btn-success btn-sm join-practice-btn">参加考试</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>

						</div>
					</div>

				</div>
				

			</div>

		</div>

		<%@include file="include/footer_cm_js.jsp" %>
		 <script type="text/javascript"  src="resources/js/exam-list.js"></script>
		 <script type="text/javascript" src="resources/js/site-statistics/cnzz.js"></script>
	</body>
</html>
