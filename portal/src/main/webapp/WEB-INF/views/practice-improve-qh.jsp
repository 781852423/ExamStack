<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>练习-精进！</title>
		
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
										<span id="knowledgePointId" style="display:none;">${knowledgePointId }</span>
										<span id="questionTypeId" style="display:none;">${questionTypeId }</span>
										<span id="fieldId" style="display:none;">${fieldId }</span>
									</div>
									<span style="color:#428bca;">题型库：</span>
									<div>
										<span id="practice-type" class="pt-singlechoice">${questionTypeName }[ 共 <span class="pt-tno">${amount }</span> 题 ]<span class="pt-qcode" style="display:none;">qt-singlechoice</span></span>
									</div>
									<span style="color:#428bca;">学习进度：</span>
										<div class="h-progress" style="margin-top:5px;" title="50%">
											<span></span>
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
					<div class="col-xs-9" style="padding-right: 0px;min-height:800px;">
						<div class="def-bk" id="bk-exampaper">
							<div class="expand-bk-content" id="bk-conent-exampaper">
								<div id="exampaper-header">
									<div id="exampaper-title"  style="margin-bottom:15px;">
										<i class="fa fa-paper-plane"></i>
										<span id="exampaper-title-name"> ${fieldName } - ${practiceName } </span>

									</div>
									<!-- <div id="exampaper-desc-container">
									<div id="exampaper-desc" class="exampaper-filter">

									</div>
									</div> -->

								</div>
								<!-- 题目内容包括答案全部从服务器载入 -->
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
										
										<button class="btn-primary btn" id="submit-q-favorite" style="margin-left:60px;width:160px;">
                                                <span class="glyphicon glyphicon-star">收藏这道题</span>
                                        </button>
                                        
										<button class="btn-warning btn" id="submit-q-btn" style="width:160px;float:right;">
												<i class="fa fa-check-circle-o"></i>交卷并查看结果
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
							<div class="expand-bk-content" id="bk-conent-comment" style="margin-top:40px;">
								<div id="comment-title" style="margin-bottom:15px;">
									<i class="fa fa-comments"></i>
									<span> 学员评论 </span>

								</div>
								<form class="comment-form">
									<textarea rows="" cols="" style="width:100%;height:95px;" placeholder="随便说点什么吧..."></textarea>
									<input class="btn btn-primary" type="submit" value="发表评论">
								</form>
								<div class="comment-total"><span class="comment-total-num">18</span>条评论</div>
								<ul class="comment-list">
									
								</ul>
								<div id="show-more-div">
									<input type="hidden" id="idx-hidden" value="1">
									<input type="hidden" id="last-floor-hidden" value="0">
									<button id="show-more-btn">更多评论</button>
								</div>
								
							</div>

						</div>
						
						
					</div>
				</div>
			</div>

		</div>
		 <%@include file="include/footer_cm_js.jsp" %>
		 
		<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
		<script type="text/javascript" src="resources/js/practice-improve-qh.js"></script>
		<script type="text/javascript" src="resources/js/comment.js"></script>

	</body>
</html>
