<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>记忆空间容量测试</title>
		
	     <%@include file="include/head_files.jsp" %>
		<!--  <link href="resources/css/exam.css" rel="stylesheet"> -->
		 <!-- 针对认知类题目加载的css文件 -->
		  <link href="resources/css/renzhimain.css" rel="stylesheet">
         <link href="resources/css/grid.css" rel="stylesheet">
          <link href="resources/css/dsdialog.css" rel="stylesheet">  
           <link href="resources/css/public.css" rel="stylesheet">  
	</head>
   
	<body>
		 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		<div class="header" id="gohere">
		    <span style="font-size: 16px;font-weight: bold;line-height: 40px;padding-left: 20px;">
		               空间记忆容量测试，每次<span id="totalQuestionNumber">12</span>题,点击右下角按钮查看说明，点击“开始测试”则开始正式做题
		    </span>
		    剩余时间：<span id="limit_m" style="color:red;font-size: 14px;margin-left:20px;"></span>分
		    <span style="color:#E57517;font-size: 14px;">：</span>
		    <span id="limit_s" style="color:red;font-size: 14px;"></span>秒
		</div>
		<!-- Slider starts -->
		<div id="container" style="margin-bottom: 100px;">
		      
		        <table id="grid-table">
			        <tbody><tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        <tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        <tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        <tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        <tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        <tr>
			            <td></td><td></td><td></td><td></td><td></td><td></td>
			        </tr>
			        </tbody>
		       </table>
			    <div style="float:left;left: 85%;bottom:45px;position: absolute;">
			        <button id="confirm" class="no-confirm">确认选择</button>
			        <button id="gotest" class="yes-confirm">开始测试</button>
			        <button id="gotip">点击这里看看怎么玩</button>
			     </div>
			     
                   <div class="footer">
                                <a href="student/cognitive-test/memory-space-capacity"  class="current">空间记忆容量</a>
                                <button id="WideBtn">粗加工</button>
                                <button id="ThinBtn">细加工</button>
                                <a href="student/cognitive-test/activeTrack">目标动态追踪MOT</a>
                    </div>
				    <input type="hidden" id="act" value="test">
				    <input type="hidden" id="limit_secs" value="150">
			
		</div>
		
		<div class="modal fade" id="test-result">
                          <div class="modal-dialog">
                            <div class="modal-content">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title">此部分测试完毕：</h4>
     
                              </div>
                              <div class="modal-body">
                                                                                                 总计<span id="questionNumber"></span>道题，你答对了<span id="correctNumber"></span>道题，答错或者漏答了<span id="incorrectNumber"></span>道题
                              </div>
                              <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
                                   
                                  </div>
                              
                            </div><!-- /.modal-content -->
                          </div><!-- /.modal-dialog -->
         </div>

		 <%@include file="include/footer_cm_js.jsp" %>
		  <!-- 针对记忆空间容量题目类型的dialog提示和grid展现与事件 -->
		   <script type="text/javascript" src="resources/js/dsdialog/dsdialog.js"></script>
		   <script type="text/javascript" src="resources/js/grid.js"></script>
	</body>
</html>
