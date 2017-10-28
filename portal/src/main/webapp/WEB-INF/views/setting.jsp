<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>


<!DOCTYPE html>
<html>
<head>

         <%@include file="include/head_files.jsp" %>
		<title>试题管理</title>
		
		        
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
</head>
<body>
	   <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

	<!-- Slider starts -->

	<div>
		<!-- Slider (Flex Slider) -->

		<div class="container" style="min-height: 500px;">

			<div class="row">
				<div class="col-xs-2">
					<ul class="nav default-sidenav">
						<li class="active"><a> <i class="fa fa-cogs"></i> 基本资料
						</a></li>
						<!--  <li><a href="student/change-password"> <i
								class="fa fa-wrench"></i> 修改密码
						</a></li>-->

					</ul>

				</div>
				<div class="col-xs-10">
					<div class="page-header">
						<h1>
							<i class="fa fa-cogs"></i> 基本资料
						</h1>
					</div>
					<div class="page-content row">
						<form class="form-horizontal" id="form-change-password"
							action="setting" style="margin-top: 40px;" method="post">

							<div class="form-line form-user-id-u" style="display: none;">
								<span class="form-label"><span class="warning-label">*</span>用户ID：</span>
								<input type="text" class="df-input-narrow" id="id-update"
									maxlength="20" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.userid}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-username-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>用户名：</span>
								<input type="text" class="df-input-narrow" id="name-update"
									disabled="disabled" maxlength="20" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}"> <span
									class="form-message"></span> <br>
							</div>
							<div class="form-line form-truename-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>真实姓名：</span>
								<input type="text" class="df-input-narrow" id="truename-update"
									maxlength="20" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.trueName}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-national-id-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>身份证号：</span>
								<input type="text" class="df-input-narrow"
									id="national-id-update" maxlength="18" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.nationalId}"> <span
									class="form-message"></span> <br>
							</div>
							<div class="form-line form-phone-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>手机：</span>
								<input type="text" class="df-input-narrow" id="phone-update"
									maxlength="20" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.phoneNum}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-email-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>邮箱：</span>
								<input type="text" class="df-input-narrow" id="email-update"
									maxlength="20" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.email}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-company-u" style="display: none;">
								<span class="form-label"><span class="warning-label"></span>单位：</span>
								<input type="text" class="df-input-narrow" id="company-update"
									maxlength="20"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-department-u" style="display: block;">
								<span class="form-label"><span class="warning-label"></span>部门单位：</span>
								<select id="department-input-select-u" class="df-input-narrow">
									<option value="-1">--请选择--</option>
									<c:forEach items="${depList }" var="item">
										<option value="${item.depId }" 
											<c:if test="${item.depId == sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.depId }">
												selected="selected"
											</c:if>
										>${item.depName }</option>
									</c:forEach>
								</select> <span class="form-message"></span> <br>
							</div>

							<!-- Buttons -->
							<div class="form-group">
								<!-- Buttons -->
								<div class="col-md-5 col-md-offset-2">
									<button class="btn btn-info" id="update-student-btn">确认修改</button>

								</div>
							</div>
						</form>




					</div>
				</div>
			</div>
		</div>
	</div>

   <%@include file="include/footer_cm_js.jsp" %>
	
	<script type="text/javascript" src="resources/js/all.js"></script>
	
	<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
	<script type="text/javascript" src="resources/chart/morris.js"></script>
	<script type="text/javascript" src="resources/js/update-user.js"></script>
</body>
</html>