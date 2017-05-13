<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>


<!DOCTYPE html>
<html>
  <head>
    	
		<title>培训历史</title>
		   <%@include file="include/head_files.jsp" %>
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			a.btn{
				    margin-top: 0px; 
			}
			.section-navi-item {
			    display: inline-block;
			    height: 24px;
			    background: rgb(239,237,237);
			     padding:0 10px;
			    margin: 3px;
			    font-family: arial;
			    text-align: center;
			    line-height: 24px;
			    color: #428bca;
			    font-size: 12px;
			    cursor: pointer;
			}
		</style>
	</head>
	<body>
		<%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->


		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2">
						<ul class="nav default-sidenav">
							<li>
								<a href="student/usercenter"> <i class="fa fa-dashboard"></i> 用户中心 </a>
							</li>
							<li>
								<a href="student/analysis"> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li>
								<a href="student/exam-history"> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
							<li class="active">
								<a href="student/training-history"> <i class="fa fa-book"></i> 培训历史 </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-history"></i> 培训历史</h1>
						</div>
						<div class="page-content row">
							<div id="question-list">
								<table class="table-striped table">
									<thead>
										<tr>
											<td>培训名称</td><td>学习进度</td><td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${trainingList }" var="item">
											<tr>
												<td>${item.trainingName }</td>
												<td>
													<div>
														<c:forEach items="${processMap[item.trainingId] }" var="processItem">
															<c:choose>
																<c:when test="${processItem.process == 1 }">
																	<a class="section-navi-item navi-item-success" href="student/training/${item.trainingId }?sectionId=${processItem.sectionId}">${processItem.sectionName }</a>
																</c:when>
																<c:otherwise>
																	<a class="section-navi-item" href="student/training/${item.trainingId }?sectionId=${processItem.sectionId}">${processItem.sectionName }</a>
																</c:otherwise>
															</c:choose>
															
														</c:forEach>
														
													</div>
												</td>	
												<td><a class="btn btn-success approved-btn" data-id="" href="student/training/${item.trainingId }">参加培训</a></td>
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

		 <%@include file="include/footer_cm_js.jsp" %>
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
	</body>
</html>