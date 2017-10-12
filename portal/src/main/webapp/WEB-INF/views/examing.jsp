<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>模拟考试</title>
		 <%@include file="include/head_files.jsp" %>
		 <link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			input[type="radio"]{
				font-size:100px;
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
					<div class="col-xs-6 col-md-3 firstrow-col1" style="padding:10px;">
						<div class="def-bk" id="bk-exam-control">

							<div class="def-bk-content" id="exam-control">

								<div id="question-time" class="question-time-normal">
									<div style="height:140px;text-align: center;">
										<i id="time-icon" class="fa fa-clock-o"> </i>
									</div>

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
								<div id="question-recyle">
									<button class="btn-danger btn" id="recyleExambtn" style="width:100%;">
										重新答题[清除答案重计时]
									</button>
									
								</div>
								
								<div id="exam-info" style="display:none;">
									<span id="answer-hashcode"></span>
								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-6 col-md-9 firstrow-col2" style="padding:10px; ">
						
								<h5>模拟考试说明：</h5>
								<ol>
									<li>本页面为模拟考试页面</li>
									<li>考试倒计时在页面左侧</li>
									<li>考试时间到了，系统会自动交卷，考生可以看到参考答案，主观题考生根据自身情况评分</li>
									<li>考生可选择自主交卷，然后就可以看到试题参考答案</li>
									<li>考生根据页面底部答题卡，点击展开所有题目，可以定位到不同的题目，提交答案后，答题卡会显示答题是否正确，正确的是绿色，错误的是红色，主观题不标注颜色，
									  供考生自行判断
								    </li>
								    <li>默认情况下，页面会记录之前的答题记录，并会储存之前的答题时间，方便累加；考生如果需要清空以前的答题记录，并重新计时，可以点击计时器面板的“重新答题”按钮</li>
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
											<div id="exampaper-title-name" class="mk_kstm"> ${examPaper.name}
											<span class="tot_score"><span class="tot_fs">分</span></span>
											</div>

											<span style="display:none;" id="exampaper-id">1</span>
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
											
											</div>
										</div>
										
									</div>
									<input type="hidden" id="start-time" value="${startTime }">
									<input type="hidden" id="hist-id" value="${examHistoryId }">
									<input type="hidden" id="paper-id" value="${examPaperId }">
									<input type="hidden" id="exam-id" value="${examId }">
									<ul id="exampaper-body">
									<div class="mk_optionsbox">
                                    <h2>切换题目</h2>
                                        <div class="mk_options">
                                               <c:forEach items="${examPaper.paperParts}" var="PaperPart">
                                                <a href="#part_${PaperPart.id}" class="partNavi">${PaperPart.name}</a>
                                               </c:forEach>                                 
                                        </div>
                                    </div>
                                    <div>
                                        ${htmlStr }
                                    </div>
										
									</ul>
									<div id="exampaper-footer">
										<div id="question-navi">
											<div id="question-navi-controller">
												<i class="fa fa-arrow-circle-down"></i>
												<span>答题卡[点击可展开]</span>
											</div>
											<div id="question-navi-content">
												<c:forEach items="${examPaper.paperParts}" var="PaperPart">
	                                                     <div class="mkrf_item" id="answersheet_${PaperPart.id }">
	                                                         <div class="mftm_tt">${PaperPart.name }
	                                                              <span class="mf_tip">(共${PaperPart.questionCount}题)</span>
	                                                               <span class="PointPerQuestion" style="display:none;">${PaperPart.pointPerQuestion }</span>
	                                                         </div>
	                                                         <div class="mftm_con">
	                                                         </div>
	                                                     </div>
	                                                </c:forEach>
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
		<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
		<script type="text/javascript" src="resources/js/paper-examing.js"></script>

		

	</body>
</html>
