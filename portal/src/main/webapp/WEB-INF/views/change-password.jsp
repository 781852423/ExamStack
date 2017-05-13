<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    	
		<title>试题管理</title>
		
		<meta name="keywords" content="">
		<%@include file="include/head_files.jsp"%>
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
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
								<a href="student/setting"> <i class="fa fa-cogs"></i> 基本资料 </a>
							</li>
							<li class="active">
								<a> <i class="fa fa-wrench"></i> 修改密码 </a>
							</li>

						</ul>

					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-wrench"></i> 修改密码 </h1>
						</div>
						<div class="page-content row">
							<form class="form-horizontal" id="form-change-password" action="student/change-pwd" style="margin-top:40px;" method="post">
									
									<!-- password -->
									<div class="form-group form-password">
										<label class="control-label col-md-2" for="password">新密码</label>
										<div class="col-md-5">
											<input type="password" class="form-control" id="password">
											<span class="form-message"></span>
										</div>
									</div>
									
									<!-- password-confirm -->
									<div class="form-group form-password-confirm">
										<label class="control-label col-md-2" for="password-confirm">确认新密码</label>
										<div class="col-md-5">
											<input type="password" class="form-control" id="password-confirm">
											<span class="form-message"></span>
										</div>
									</div>

									<!-- Buttons -->
									<div class="form-group">
										<!-- Buttons -->
										<div class="col-md-5 col-md-offset-2">
											<button type="submit" class="btn btn-info" id="btn-reg">
												确认修改
											</button>
										
										</div>
									</div>
								</form>
							
							
							

						</div>
					</div>
				</div>
			</div>
		</div>

        <%@include file="include/footer_cm_js.jsp"%>
		
		<script type="text/javascript" src="resources/js/all.js"></script>
		
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
		<script type="text/javascript" src="resources/js/pwd-change.js"></script>
	</body>
</html>