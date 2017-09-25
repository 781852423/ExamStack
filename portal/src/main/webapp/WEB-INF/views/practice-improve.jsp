<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>题库练习-精进！</title>
		 <%@include file="include/head_files.jsp" %>
		<link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			.question-body {
				padding: 30px 30px 20px 30px;
				background: #FFF;
			}
			
			ul#exampaper-body{
				margin-bottom: 0px;
			}
			
			ul#exampaper-body li{
				padding-bottom:0px;
			}
			.question-body{
				min-height:300px;
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
					<div class="col-xs-3" style="padding-right: 0px;padding-bottom:15px;">
						<div class="def-bk" id="bk-exam-control">

							<div class="def-bk-content" id="exam-control">

								<div>

									<span style="color:#428bca;">知识类型：</span>
									<div>
										<span>${fieldName }</span>
									</div>
									<span style="color:#428bca;">题型库：</span>
									<div>
										<span id="practice-type" class="pt-singlechoice">${questionTypeName }[ 共 <span class="pt-tno">${amount }</span> 题 ]<span class="pt-qcode" style="display:none;">qt-singlechoice</span></span>
									</div>

									<span id="exam-timestamp" style="display:none;">0</span>
									<div id="answer-save-info"></div>

								</div>
								<div id="question-submit">
									<button class="btn-success btn" style="width:100%;" id="switch-model-btn" data-exam="true"> 
										答题模式
									</button>
								</div>
								<div id="exam-info" style="display:none;">
									<span id="answer-hashcode"></span>
								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-9" style="padding-right: 0px;">
						<div class="def-bk" id="bk-exampaper">
							<div class="expand-bk-content" id="bk-conent-exampaper">
								<div id="exampaper-header">
									<div id="exampaper-title"  style="margin-bottom:15px;">
										<i class="fa fa-paper-plane"></i>
										<span id="exampaper-title-name"> ${fieldName } - ${practiceName } </span>

									</div>


								</div>
								<ul id="exampaper-body">
									${questionStr }

								</ul>
								<div id="exampaper-footer">
										
									
									<div id="question-switch">
										<button class="btn-success btn" id="previous-q-btn" style="width:160px;">
												<i class="fa fa-chevron-circle-left"></i>上一题

										</button>
										<button class="btn-success btn" id="next-q-btn" style="margin-left:60px;width:160px;">
												下一题 <i class="fa fa-chevron-circle-right"></i>
										</button>
										<button class="btn-warning btn" id="submit-q-btn" style="width:160px;float:right;">
												<i class="fa fa-check-circle-o"></i>提交答案

										</button>
									</div>
									<div id="question-navi">
										<div id="question-navi-controller">
											<i class="fa fa-arrow-circle-down"></i>
											<span>答题卡[点击可展开]</span>
										</div>
										<div id="question-navi-content" ></div>
									</div>

								</div>
							</div>
							<div class="expand-bk-content" id="bk-conent-comment" style="margin-top:40px;display:none;">
								<div id="comment-title" style="margin-bottom:15px;">
									<i class="fa fa-comments"></i>
									<span> 学员评论 </span>

								</div>
								<ul class="comment-list">
									
									<li class="comment-list-item">
										<div class="comment-user-container">
											<div >
												<img src="resources/images/photo.jpg" class="comment-user-img">
											</div>
											<div class="comment-user-info">
												<div>
													yanhuan [电能计量]
												</div>
												<div class="comment-date">
													发表于 1天前
												</div>
											</div>
										</div>
										<p class="comment-user-text">
											应该选B不是吗？
										</p>
										
									</li>
									<li class="comment-list-item">
										<div class="comment-user-container">
											<div >
												<img src="resources/images/photo.jpg" class="comment-user-img">
											</div>
											<div class="comment-user-info">
												<div>
													yanhuan [电能计量]
												</div>
												<div class="comment-date">
													发表于 1天前
												</div>
											</div>
										</div>
										<p class="comment-user-text">
											应该选B不是吗？
										</p>
										
									</li>
								</ul>
								<div id="show-more-div">
									<input type="hidden" id="idx_hidden" value="1">
									<button id="show_more_btn">更多评论</button>
								</div>
								<form class="comment-form">
									<textarea rows="" cols="" style="width:100%;height:95px;"></textarea>
									<input class="btn btn-primary" type="submit" value="发表评论">
								</form>
								
							</div>

						</div>
						
						
					</div>
				</div>
			</div>

		</div>
		 
	    <%@include file="include/footer_cm_js.jsp" %>
		<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
		<script type="text/javascript" src="resources/js/practice-improve.js"></script>

	</body>
</html>
