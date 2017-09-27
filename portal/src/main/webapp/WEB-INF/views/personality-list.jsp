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
							<h1><i class="fa fa-list-ul"></i>选择心理测试类别进行测试 </h1>
					</div>
					<ol class="nav nav-pills " style="margin: 20px 0;">
						<c:forEach items="${xuepaiList }" var="item">
							<li role="presentation" ><a href="student/personalitytest-start/1/${item.id}"><span>${item.name }</span></a></li>
						</c:forEach>
					</ol>
				</div>
				
			</div>
			
		</div>
		
		

		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>
