<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职梦靠岸-主页</title>
		 <%@include file="include/head_files.jsp" %>
		
		<style>
			.question-number {
				color: #5cb85c;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			.question-number-2 {
				color: #5bc0de;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			.question-number-3 {
				color: #d9534f;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			a.join-practice-btn {
				margin: 0;
				margin-left: 20px;
			}

			.content ul.question-list-knowledge {
				padding: 8px 20px;
			}

			.knowledge-title {
				background-color: #EEE;
				padding: 2px 10px;
				margin-bottom: 20px;
				cursor: pointer;
				border: 1px solid #FFF;
				border-radius: 4px;
			}

			.knowledge-title-name {
				margin-left: 8px;
			}

			.point-name {
				width: 200px;
				display: inline-block;
			}
			.col-xs-3 {
				width: 22%;
			}
		</style>

	</head>

	<body>
		<%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div class="full-slider">
			<!-- Slider (Flex Slider) -->

			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="flexslider">
							<div class="flex-caption">
								<!-- Left column -->
								<div class="col-l">
									<p style="text-indent:2em;">
										职梦靠岸是国内专注应届生、社会招聘的笔试面试辅导机构，专注做银行、证券、保险等机构笔试面试辅导。
									</p>
									<p style="text-indent:2em;">
										团队具有相关单位求职和工作经验。
									</p>
								</div>
								<!-- Right column -->
								<div class="col-r">

									<!-- Use the class "flex-back" to add background inside flex slider -->

									<!-- <img alt="" src="../resources/images/ad.png"> -->
									<p>
										如果有任何意见或者建议，请留言给我们。
									</p>

									<!-- Button -->
									<a class="btn btn-default btn-cta" href="user-register"><i class="fa fa-arrow-circle-down"></i> 注册网站看看</a>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content" style="padding:30px 0 0 0;">
			<div class="container">
				<div class="row">
					<a class="select-test col-xs-4 home-link-anchor" href="student/practice-list">
						<div class="select-test-icon">
							<i class="fa fa-book"></i>
	
						</div> <h3> 试题练习 </h3>
						<p>
							您可以免费获取对应专业的培训视频或者文档资料，通过在线练习可以考察您的知识掌握程度。
						</p> 
					</a>
					<a class="select-test col-xs-4 home-link-anchor" href="exam-list">
						<div class="select-test-icon">
							<i class="fa fa-trophy"></i>
						</div> <h3> 在线考试 </h3>
						<p>
							参加正式或者模拟考试，您的教师可以发布正式的考试，您也可以主动申请这些考试
						</p> 
					</a>
					<div class="select-test col-xs-4">
						<div>
							<h3> 快速考试入口 </h3>
							<p>
								通过已有的准考证快速参加考试，具体的准考证请从相关教师处获取，或者在个人中心的考试信息中查找
							</p>

						</div>
						<div style="text-align: center;margin-top:20px;">
							<a class="btn btn-info quick-start-btn" href="quick-start">快速考试入口 <i class="fa fa-arrow-circle-o-right"></i> </a>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content" style="padding:30px 0 100px 0;background-color: rgb(246, 249, 250);">
			<div class="container">
				<div style="margin-bottom: 10px;">
					<h3>专业领域</h3>
					<p>
						我们提供多种专业课程供你学习
					</p>
				</div>
				<div class="row">
					<a class="field-category-div col-xs-3 home-link-anchor">
					<div class="field-category-title" style="background-color: #E97051;">
						<div class="field-category-inner">
							<i class="fa fa-medkit"></i>
						</div>
						<div class="field-category-text ">
							<p>
								国有银行考试
							</p>
						</div>
					</div>
					<div class="field-category-footer">
						<div class="field-category-footer-sub">
							<p class="field-student-subtitle">
								股份制银行考试
							</p>
						</div>
						<div class="field-category-footer-sub">
							<i class="fa fa-users"></i>
							<span class="field-student-num">512</span>

							名学员
						</div>

					</div> </a>
					<a class="field-category-div col-xs-3 home-link-anchor">
					<div class="field-category-title" style="background-color: #5C78B9;">
						<div class="field-category-inner">
							<i class="fa fa-trophy"></i>
						</div>
						<div class="field-category-text ">
							<p>
								进出口银行
							</p>
						</div>
					</div>
					<div class="field-category-footer">
						<div class="field-category-footer-sub">
							<p class="field-student-subtitle">
								国家开发银行
							</p>

						</div>
						<div class="field-category-footer-sub">
							<i class="fa fa-users"></i>
							<span class="field-student-num">1143</span>
							名学员
						</div>

					</div> </a>
					<a class="field-category-div col-xs-3 home-link-anchor">
					<div class="field-category-title" style="background-color: #5BA276;">
						<div class="field-category-inner">
							<i class="fa fa-car"></i>
						</div>
						<div class="field-category-text ">
							<p>
								邮政储蓄银行
							</p>
						</div>
					</div>
					<div class="field-category-footer">
						<div class="field-category-footer-sub">
							<p class="field-student-subtitle">
								城商行
							</p>

						</div>
						<div class="field-category-footer-sub">
							<i class="fa fa-users"></i>
							<span class="field-student-num">1213</span>
							名学员
						</div>

					</div> </a>

				</div>
			</div>
		</div>
		<div class="content" style="padding:30px 0 100px 0;">
			<c:if test="${fn:length(newsList) > 0 }">
				<div class="container">
					<div>
						<h3>最新公告</h3>
					</div>
					<div style="margin-top: 20px;">
						<ul class="news-list">
							<c:choose>
								<c:when test="${fn:length(newsList) eq 1 }">
									<li class="news-list-item clearfix">
										<a class="home-link-anchor" href="news/${newsList[0].newsId }" target="_blank">
										<div class="news-list-thumbnail">
											<img src="resources/images/news.png">
										</div>
										<div class="news-list-content">
											<div class="news-list-title">
												${newsList[0].title }
											</div>
											<div class="news-list-creater">
												<i class="fa fa-user"></i><span>${newsList[0].creator }</span>
												<i class="fa fa-clock-o"></i><span><fmt:formatDate value="${newsList[0].createTime }" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> </a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="news-list-item clearfix">
										<a class="home-link-anchor" href="news/${newsList[0].newsId }" target="_blank">
										<div class="news-list-thumbnail">
											<img src="resources/images/news.png">
										</div>
										<div class="news-list-content">
											<div class="news-list-title"">
												${newsList[0].title }
											</div>
											<div class="news-list-creater">
												<i class="fa fa-user"></i><span>${newsList[0].creator }</span>
												<i class="fa fa-clock-o"></i><span><fmt:formatDate value="${newsList[0].createTime }" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> </a>
									</li>
									<li class="news-list-item clearfix">
										<a class="home-link-anchor" href="news/${newsList[1].newsId }">
										<div class="news-list-thumbnail">
											<img src="resources/images/news.png">
										</div>
										<div class="news-list-content">
											<div class="news-list-title">
												${newsList[1].title }
											</div>
											<div class="news-list-creater">
												<i class="fa fa-user"></i><span>${newsList[1].creator }</span>
												<i class="fa fa-clock-o"></i><span><fmt:formatDate value="${newsList[1].createTime }" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> </a>
									</li>
								</c:otherwise>
							</c:choose>
							
							
						</ul>
					</div>
				</div>
			</c:if>
			
		</div>
		 <%@include file="include/footer_cm_js.jsp" %>
		 
		<script>
		$(function(){
			bindQuestionKnowledage();
			var result = checkBrowser();
			if (!result){
				alert("请至少更新浏览器版本至IE8或以上版本");
			}
		});
		
		function checkBrowser() {
			var browser = navigator.appName;
			var b_version = navigator.appVersion;
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
				return false;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return false;
			} else
				return true;
		}
		
		function bindQuestionKnowledage(){
			$(".knowledge-title").click(function(){
				var ul = $(this).parent().find(".question-list-knowledge");
				
				if(ul.is(":visible")){
					$(this).find(".fa-chevron-down").hide();
					$(this).find(".fa-chevron-up").show();
					
					$(".question-list-knowledge").slideUp();
					
				}else{
					$(".fa-chevron-down").hide();
					$(".fa-chevron-up").show();
					
					$(this).find(".fa-chevron-up").hide();
					$(this).find(".fa-chevron-down").show();
					
					$(".question-list-knowledge").slideUp();
					ul.slideDown();
					
				}
				
				
			});
		}
		</script>
		
	</body>
</html>
