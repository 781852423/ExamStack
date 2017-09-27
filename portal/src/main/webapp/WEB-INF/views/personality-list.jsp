<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职业性格测试</title>
		 <%@include file="include/head_files.jsp" %>
	</head>

	<body>
		 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		<!-- Slider starts -->
		<div class="content" style="margin-bottom: 100px;">
			<div class="container">
				<div class="row">
				    <div class="page-header">
							<h1><i class="fa fa-list-ul"></i>选择类别进行测试 </h1>
					</div>
					<ul class="nav nav-pills " style="margin: 20px 0;">
						<c:forEach items="${xuepaiList }" var="item">
							<li role="presentation" ><a href="">${item.name }</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="row">
						<div class="col-xs-10">
								<iframe width="100%" id="personality-exam-iframe" style="height: 800px;" 
								 frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
								 src = "student/exam-start/3/1"
								 >
								 
								 </iframe>
							</div>
				</div>
				
			</div>
			
		</div>
		
		

		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>
