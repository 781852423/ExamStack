<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>认知能力测试：粗加工</title>
		
	     <%@include file="include/head_files.jsp" %>
		<!--  <link href="resources/css/exam.css" rel="stylesheet"> -->
		 <!-- 针对认知类题目加载的css文件 -->
		  <link href="resources/css/css.css" rel="stylesheet">
          <link href="resources/css/dsdialog.css" rel="stylesheet">  
           <link href="resources/css/picture.css" rel="stylesheet">
           <link href="resources/css/public.css" rel="stylesheet"> 
           <link href="resources/css/games.css" rel="stylesheet"> 
	</head>
   
	<body>
		 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		
		<!-- Slider starts -->
		<div id="container">
		       <!-- 标题 -->
        <div id="top-nav-wrap">
                        <span>职业能力 -- 认知能力 粗加工</span>
                        <span id="limit_m" style="color:#E57517;font-size: 14px;margin-left:20px;"></span>
            <span style="color:#E57517;font-size: 14px;">：</span>
            <span id="limit_s" style="color:#E57517;font-size: 14px;"></span>
        </div>
        <!-- 内容 -->
        <div id="main">
            <!-- 左 -->
            <div class="main-left fl">
                <div class="left-box" style="margin-top:156px;">
                    <img width="300" height="300" src="/static/picture/wide/1.png">
                    <input type="hidden" id="original" value="1"/>
                </div>
            </div>
            <!-- 右  -->
            <div class="main-right fr">
                 <div class="right-box">
                     <ul>
                                                  <li>
                             <span>A</span>
                             <img width="300" height="300" src="/static/picture/wide/1-2.png">
                             <input type="hidden" name="comp" value="2"/>
                         </li>
                                                  <li>
                             <span>B</span>
                             <img width="300" height="300" src="/static/picture/wide/1-4.png">
                             <input type="hidden" name="comp" value="4"/>
                         </li>
                                                  <li>
                             <span>C</span>
                             <img width="300" height="300" src="/static/picture/wide/1-3.png">
                             <input type="hidden" name="comp" value="3"/>
                         </li>
                                                  <li>
                             <span>D</span>
                             <img width="300" height="300" src="/static/picture/wide/1-1.png">
                             <input type="hidden" name="comp" value="1"/>
                         </li>
                                              </ul>
                 </div>
            </div>
            <input type="hidden" id="qid" value="2"/>
            <input type="hidden" id="limit_secs" value="180"/>
            <div class="clear"></div>
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
         
         <div class="modal fade" id="prompt-msg">
                          <div class="modal-dialog">
                            <div class="modal-content">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title">本类型题目是根据左边的图，从选项中找到一模一样的图出来</h4>
     
                              </div>
                              <div class="modal-body">
                                    <ol>
                                    <li>屏幕左面是目标图片，右面是四张对比图片。</li>
                                    <li>四张对比图片中仅有一副图片与目标图片完全一致。</li>
                                     <li>请用鼠标单击选择该图片。作答后系统将自动进入下一组图片，</li>
                                     <li>你将不能返回进行修改。</li>
                                     <li>请用尽可能快的速度完成作答。</li>
                                     <li>点击“开始测试”按钮进入正式测试。</li>
                                    </ol>
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
		   <script type="text/javascript" src="resources/js/renzhiCapacity/index.js"></script>
	</body>
</html>
