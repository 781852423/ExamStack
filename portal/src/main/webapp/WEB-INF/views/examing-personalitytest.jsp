<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职业性格测试</title>
		 <%@include file="include/head_files.jsp" %>
		 <link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			input[type="radio"]{
				font-size:100px;
			}
		</style>
	</head>
	<body>


		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">
					<div class="col-xs-6 col-md-3 firstrow-col1" style="padding:10px;">
						<div class="def-bk" id="bk-exam-control">
							<button class="btn btn-primary btn-lg" id="toggleClock">隐藏或者显示时钟</button>
							<div class="def-bk-content" id="exam-control">

								<div id="question-time" class="question-time-normal">


									<span style="margin-right:10px;color: #B8B8B8;">倒计时</span>
									<span id="exam-clock">&nbsp;</span>
									<span id="exam-timestamp" style="display:none;">${duration }</span>
									<div id="answer-save-info"></div>

								</div>
								<div id="question-submit">
									<button class="btn-success btn" style="width:100%;">
										我要交卷
									</button>
									
								</div>
								
								<div id="exam-info" style="display:none;">
									<span id="answer-hashcode"></span>
								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-6 col-md-9 firstrow-col2" style="padding:10px; ">
						
								<h5>职业心理测试说明：</h5>
								<ol>
									<li>本页面为职业心理测试说明</li>
									<li>考试倒计时在页面左侧</li>
									<li>不同的题目类型（例如单选、多选）会区分，并放到不同的按钮下面，可以点击下栏的提醒按钮进行切换</li>
									<li>考试时间到了，系统会自动交卷，系统会出示性格测试的评估结果</li>
									<li>考生可选择自主交卷，然后就可以看到出示性格测试的评估结果</li>
									<li>考生根据页面底部答题卡，点击展开所有题目，可以定位到不同的题目 </li>
								</ol>
							
					</div>
					
					
					</div> <!-- end 1st row -->
					<div class="row">
						<div class="col-xs-12 col-md-11" style="padding-right: 0px;">
							<div class="def-bk" id="bk-exampaper">
	
								<div class="expand-bk-content" id="bk-conent-exampaper">
									<div id="exampaper-header">
										<div id="exampaper-title">
											<i class="fa fa-send"></i>
											<span id="exampaper-title-name"> 模拟试卷 [每页展示同类题型，点击下行题目类型可以预览题目]</span>

											<span style="display:none;" id="exampaper-id">1</span>
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
											
											</div>
										</div>
										
									</div>
									<input type="hidden" id="paper-id" value="${examPaperId }">
									<input type="hidden" id="exam-id" value="${examId }">
									<ul id="exampaper-body">
										${htmlStr }
									</ul>
									<div id="exampaper-footer">
										<div id="question-navi">
											<div id="question-navi-controller">
												<i class="fa fa-arrow-circle-down"></i>
												<span>答题卡[点击可展开]：深灰色：之前做过的题目，红色五角星：收藏过，答题后按钮显示红色：答错</span>
											</div>
											<div id="question-navi-content">
											</div>
										</div>
	
									</div>
								</div>
	
							</div>
					</div>
				</div><!-- ends 2nd row -->
			</div> <!-- ends container -->

		</div> <!--  ends content -->
		 <%@include file="include/footer_cm_js.jsp" %>
		<script type="text/javascript" src="resources/js/examing-personalitytest.js"></script>

		

	</body>
</html>
