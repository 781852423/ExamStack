<%@include file="include/before_html.jsp" %>
<%@ page language="java" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>

<head>
	
	
	<title>职梦靠岸:用户注册</title>
	 
	<%@include file="include/head_files.jsp" %>
	
	<!-- Javascript files -->
	<style type="text/css">
	.form-group {
		margin-bottom: 5px;
		height: 59px;
	}
	</style>
</head>

<body>
	<!-- header part begin-->
	<%@include file="include/body_header.jsp"  %>
	<!-- header part end-->
	
    <!-- Navigation bar starts -->
	<%@include file="include/commen_naviBar.jsp" %>
	<!-- Navigation bar ends -->

	<div class="content" style="margin-bottom: 100px;">

		<div class="container">
			<div class="row">

				<div class="col-md-12">
					<div class="lrform">
						<h5>注册账号</h5>
						<span class="form-message"></span>
						<div class="form">
							<!-- Register form (not working)-->
							<form class="form-horizontal" id="form-create-account"
								action="user-register">
								<div class="form-line form-username" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>用户名：</span>
									<input type="text" class="df-input-narrow" id="name-add"
										maxlength="20" placeholder="请输入用户名,5-20个字符,数字、字母或者下划线的组合"> <span class="form-message"></span> <br>
								</div>
								<div class="form-line form-password" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>密码：</span>
									<input type="password" class="df-input-narrow" id="password-add"
										maxlength="20" placeholder="请输入密码，6到20个字符"> <span class="form-message"></span> <br>
								</div>
								<div class="form-line form-password-confirm" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>重复密码：</span>
									<input type="password" class="df-input-narrow" id="password-add"
										maxlength="20" placeholder="再次输入密码，6到20个字符,和前面的相同"> <span class="form-message"></span> <br>
								</div>
								
								<div class="form-line form-email" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>邮箱：</span>
									<input type="text" class="df-input-narrow" id="email-add"
										maxlength="60" placeholder="请输入邮箱地址，邮箱不能为空，作为密码忘记密码重置使用"> <span class="form-message"></span> <br>
								</div>
								
								<div class="form-line form-confirm">
									
										<label class="checkbox-inline"> <input type="checkbox"
											id="inlineCheckbox1" value="agree"> <a>
												自行独立注册的账户有0.5小时系统使用权限</a>
										</label> <span class="form-message"></span>
									

								</div>
								
                                <div class="form-line form-reg-msg" id="reg-result-msg" style="display: none;">
                                    <span class="form-message"></span> <br>
                                </div>
								<!-- Buttons -->
								<div class="form-line">
									<!-- Buttons -->
									<!-- <div class="col-md-9 col-md-offset-3"> -->
										<button type="submit" class="btn" id="btn-reg">注册账号</button>
										<button type="reset" class="btn">重置</button>
									<!-- </div> -->
								</div>
							</form>
							已有账号? <a href="user-login-page">直接登录</a>
						</div>
					</div>

				</div>
			</div>

		</div>

	</div>
	
	<%@include file="include/footer_cm_js.jsp" %>
	
	<script type="text/javascript" src="resources/js/all.js"></script>
	<script type="text/javascript" src="resources/js/register.js"></script>

</body>
</html>
