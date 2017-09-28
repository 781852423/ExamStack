<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职业性格测试-九型人格测试报告</title>
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
							<h1><i class="fa fa-list-ul"></i>职业性格测试-九型人格测试报告</h1>
					</div>
					<ol class="nav nav-pills " style="margin: 20px 0;">
						<c:forEach items="${PersonalityScoreList }" var="item">
							<li role="presentation" ><span>${item.name }:${item.danxiangScore}</span></li>
						</c:forEach>
					</ol>
				</div>
				
			</div>
			
		</div>
		
		

		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>
