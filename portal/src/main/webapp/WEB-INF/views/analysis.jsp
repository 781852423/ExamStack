<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/before_html.jsp"%>


<!DOCTYPE html>
<html>
  <head>
    	
    	<%@ include file="include/head_files.jsp"%>
		<title>统计分析</title>
		
		<meta name="keywords" content="">

		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			.table-striped a{
				text-decoration: underline;
			}
			
			.span-success{
				color:#5cb85c;
				font-weight:bolder;
			}
			
			.span-danger{
				color:#d9534f;
				font-weight:bolder;
			}
			
			.span-info{
				color:#5bc0de;
				font-weight:bolder;
			}
			h6{
				font-weight:bold !important; 
			}
			#field-switch{
				margin:15px 0 0 0px;;
				height:34px;
				width:200px;
			}
		</style>
	</head>
	<body>
		<%@include file="include/body_header.jsp"%>
		<!-- Navigation bar starts -->

		<%@include file="include/commen_naviBar.jsp"%>

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
							<li class="active">
								<a> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li>
								<a href="student/exam-history"> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
							<li>
								<a href="student/training-history"> <i class="fa fa-book"></i> 培训历史 </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-bar-chart-o"></i> 统计分析</h1>
						</div>
						<div class="page-content row">
							<div class="col-xs-12">
								<select id="field-switch" class="form-control">
									<c:forEach items="${fieldList }" var="item">
										<option value="${item.fieldId }">${item.fieldName }</option>
									</c:forEach>
									<!-- <option value="4">公务员申论</option>
									<option value="5">医药行业考试</option> -->
								</select>
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

		<%@include file="include/footer_cm_js.jsp"%>
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
		<script type="text/javascript" src="resources/js/exam-finished.js"></script>
		<script type="text/javascript">
		
			$("#field-switch").val("${fieldId}");
			$("#field-switch").change(function(){
				window.location.href="student/analysis/"+ $(this).val();
			});
		
		
		</script>
	</body>
</html>