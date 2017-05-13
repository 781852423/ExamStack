<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>培训中心</title>
		 <%@include file="include/head_files.jsp" %>
		 
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
						<div class="col-xs-12">
							<div style="border-bottom: 1px solid #ddd;">
							<h3 class="title"><i class="fa fa-book"></i>培训资料</h3>
							
							</div>
							<div id="training-list">
							<table class="table-striped table">
								 <thead>
									<tr>
										<td style="display:none;">ID</td>
										<td>培训名称</td>
										<td>描述</td>
										<!-- <td>创建人</td> -->
										<td>截止时间</td>
										<td>操作</td>
									</tr>
								</thead> 
								<tbody>
									<c:forEach items="${trainingList }" var="item">
										<tr>
											<td style="display:none;">${item.trainingId }</td>
											<td>
												${item.trainingName }
											
											</td>
											<td style="width:50%;">
												<div style="font-size:12px;padding:10px 0px;">
												
													${item.trainingDesc }
												</div>
											</td>
											<td><fmt:formatDate value="${item.expTime}"
													pattern="yyyy-MM-dd" /></td>
											
											<%-- <td>${item.trainingDesc }</td> --%>
											<%-- <td><fmt:formatDate value="${item.createTime}"
													pattern="yyyy-MM-dd" /></td> --%>
										<%-- 	<td><fmt:formatDate value="${item.expTime}"
													pattern="yyyy-MM-dd" /></td> --%>
											<td ><a class="btn btn-success approved-btn" data-id="${item.trainingId }" href="student/training/${item.trainingId }">参加培训</a></td>

										</tr>
									</c:forEach>

								</tbody>
								<tfoot></tfoot>
							</table>

							</div>
							<div class="page-link-content">
								<ul class="pagination pagination-sm">${pageStr}</ul>
							</div>
						</div>
				</div>
			</div>
			

		</div>

		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>
