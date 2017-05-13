<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>


<!DOCTYPE html>
<html>
	<head>
		
		<title>>职梦靠岸 登录系统</title>
		
		 <%@include file="include/head_files.jsp" %>
		 
		<style type="text/css">
			.form-group {
				margin-bottom: 5px;
				height: 59px;
			}

		</style>
	</head>
	<body>
		  <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->
		

		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">

					<div class="col-md-12">
						<div class="lrform">
							<h5>快速考试入口</h5>
							<div class="form">
								<!-- Login form (not working)-->
								<form class="form-horizontal" action="j_spring_security_check" method="post">
									<!-- Username -->
									<div class="form-group">
										<label class="control-label col-md-3" for="username">准考证号</label>
										<div class="col-md-9">
											<input type="text" class="form-control" name="j_seri_no">
											<input type="hidden" value="1" name="j_flag">
										</div>
									</div>
									
									<!-- Buttons -->
									<div class="form-group">
										<!-- Buttons -->
										<div class="col-md-9 col-md-offset-3">
											<button type="submit" class="btn btn-default">
												开始
											</button>
											<span class="form-message">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
										</div>
									</div>
								</form>
								没有账号? <a href="user-register">注册</a>
							</div>
						</div>

					</div>
				</div>

			</div>

		</div>
		
		 <%@include file="include/footer_cm_js.jsp" %>

	</body>
</html>