<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
  <head>

		<title>试题管理</title>
		<%@include file="include/head_files.jsp" %>
		<link href="resources/css/question-add.css" rel="stylesheet">

		<link href="resources/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.uploadify-button-text{
				text-decoration: underline;
			}
			
			span.add-img{
				text-decoration: underline;
				cursor:pointer;
			}
			
			span.add-img:hover{
				text-decoration: underline;
			}
			
			.swfupload {
   				z-index: 10000 !important;
			}
			
			.add-content-img{
				display:block;
			}
			
			.diaplay-img{
				margin-right:5px;
			}
			
			.diaplay-img:hover{
				text-decoration: underline;
				
			}
		</style>
	</head>
	<body>
		<header>
			<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#">网站管理系统</a></h1>
							<div class="hmeta">
								专注互联网在线考试解决方案
							</div>
						</div>
					</div>
					<div class="col-xs-7" id="login-info">
						<c:choose>
							<c:when test="${not empty sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
								<div id="login-info-user">
									
									<a href="user-detail/${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}" id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
									<span>|</span>
									<a href="j_spring_security_logout"><i class="fa fa-sign-out"></i> 退出</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" href="user-register">用户注册</a>
								<a class="btn btn-success" href="user-login-page">登录</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</header>
		<!-- Navigation bar starts -->

		<div class="navbar bs-docs-nav" role="banner">
			<div class="container">
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<c:import url="/common-page/top-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2" id="left-menu">
						<c:import url="/common-page/left-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />

					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-pencil-square-o"></i> 添加试题 </h1>
						</div>
						<div class="page-content">
							<form id="question-add-form">
								<div class="form-line question-type" id="question-type">
									<span class="form-label"><span class="warning-label">*</span>试题类型：</span>
									<select id="question-type-select" class="df-input-narrow">

										<option value="1">单选题</option>

										<option value="2">多选题</option>

										<option value="3">判断题</option>

										<option value="4">填空题</option>

										<option value="5">简答题</option>

										<option value="6">论述题</option>
										
										<option value="7">计算题</option>
										
										<option value="8">名词解释</option>
										
										<option value="9">QuestionParent</option>
										
										<option value="10">性格测试</option>


									</select>
                 
									<span class="form-message"></span>
								</div>
								<div class="form-line question-parentId" id="question-parentId">
								 <a  class="btn-lit" id="searchParentIds"><span>高级查询:题目parentID(表et_question->ParentID)</span></a>
								    
                                    <select id="question-parentId-select" class="df-input-narrow">

                                        <option value="" selected="selected">请选择...</option>

                                    </select>
								</div>
								<div class="form-line question-knowledge">
									<span class="form-label"><span class="warning-label">*</span>知识分类：</span>
									<div>
										<div class="clearfix">
											<div id="aq-course1" style="padding:0px;float:left; width:48%;">
												<select id="field-select" class="df-input-narrow" size="4" style="width:100%;">
													<c:forEach items="${fieldList }" var="item">
														<option value="${item.fieldId }">${item.fieldName }</option>
													</c:forEach>
												</select>
											</div>
											<div id="aq-course2" style="padding:0px; float:right;width:48%;">
												<select id="point-from-select" class="df-input-narrow" size="4"  style="width:100%;">
												</select>
											</div>
										</div>
										
										<div style="text-align:center;margin:10px 0;" id="knowledgeClassifiedSpan">
											<button id="add-point-btn" class="btn btn-primary btn-xs">选择知识分类</button>
											<button id="del-point-btn" class="btn btn-danger btn-xs">删除知识分类</button>
											<button id="remove-all-point-btn" class="btn btn-warning btn-xs">清除列表</button>
										</div>
										<div  id="kn-selected" style="padding-left:0px;text-align:center;margin-bottom:20px;">
												<select id="point-to-select" class="df-input-narrow" size="4"  style="width:80%;">
												</select>
												<p style="font-size:12px;color:#AAA;">您可以从上面选择4个知识分类</p>
										</div>
									</div>
									<span class="form-message"></span>
								</div>
								
								<div class="form-line question-content">
									<span class="form-label"><span class="warning-label">*</span>试题内容：</span>
									<textarea class="add-question-ta"></textarea>									
									<span class="add-img add-content-img" style="width:100px;">添加图片</span>
									<span class="form-message"></span>
								</div>
								<div class="form-line form-question-opt" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>选项：</span>
									<div class="add-opt-items">
										<span class="add-opt-item"><label class="que-opt-no">A</label>
											<input type="text" class="df-input-narrow form-question-opt-item">
											<span class="add-img add-opt-img">添加图片</span>
										</span>
										<span class="add-opt-item"><label class="que-opt-no">B</label>
											<input type="text" class="df-input-narrow form-question-opt-item">
											<span class="add-img add-opt-img">添加图片</span>
										</span>
										<span class="add-opt-item"><label class="que-opt-no">C</label>
											<input type="text" class="df-input-narrow form-question-opt-item">
											<span class="add-img add-opt-img">添加图片</span> <span><i class="small-icon ques-remove-opt fa fa-minus-square" title="删除此选项"></i></span>
										</span>
										<span class="add-opt-item"><label class="que-opt-no">D</label>
											<input type="text" class="df-input-narrow form-question-opt-item">
											<span class="add-img add-opt-img">添加图片</span> <span><i class="small-icon ques-remove-opt fa fa-minus-square" title="删除此选项"></i></span>
										</span>
									</div>
								
									<span id="ques-add-opt"><i class="small-icon fa fa-plus-square" title="添加选项"></i></span>
									<br>
									<span class="form-message"></span>
								</div>
								<div class="form-line form-question-answer1 correct-answer" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>正确答案：</span>
									<select class="df-input-narrow">
										<option value="A">A</option><option value="B">B</option><option value="C">C</option><option value="D">D</option>
									</select><span class="form-message"></span>
								</div>
								<div class="form-line form-question-answer-muti correct-answer" style="display: none;">
									<span class="form-label"><span class="warning-label">*</span>正确答案：</span>

									<span class="muti-opt-item">
										<input type="checkbox" value="A">
										<label class="que-opt-no">A</label>
										<br>
									</span>
									<span class="muti-opt-item">
										<input type="checkbox" value="B">
										<label class="que-opt-no">B</label>
										<br>
									</span>
									<span class="muti-opt-item">
										<input type="checkbox" value="C">
										<label class="que-opt-no">C</label>
										<br>
									</span>
									<span class="muti-opt-item">
										<input type="checkbox" value="D">
										<label class="que-opt-no">D</label>
										<br>
									</span>
									<span class="form-message"></span>
								</div>
								<div class="form-line form-question-answer-boolean correct-answer" style="display: none;">
									<span class="form-label"><span class="warning-label">*</span>正确答案：</span>
									<select class="df-input-narrow">
										<option value="T">正确</option>
										<option value="F">错误</option>
									</select><span class="form-message"></span>
								</div>
								<div class="form-line correct-answer form-question-answer-text" style="display: none;">
									<span class="form-label form-question-answer-more"><span class="warning-label">*</span>参考答案：</span>
									<textarea class="add-question-ta"></textarea>									<span class="form-message"></span>
									<br>

								</div>
								
								<!-- 添加tag部分 -->
								<div class="form-line exampaper-type" id="aq-tag">
										<span class="form-label"><span class="warning-label">*</span>标签：</span>
										<select id="tag-from-select" class="df-input-narrow">
											<c:forEach items="${tagList }" var="item">
												<option value="${item.tagId }" data-privatee="${item.privatee }" data-creator="${item.creator}" data-memo="${item.memo }" data-createtime="${item.createTime }">${item.tagName } </option>
											</c:forEach>
									
										</select><a class="add-tag-btn">添加</a><span class="form-message"></span>
									
										<div class="q-tag-label-list">
										</div>
								</div>
								
								<!-- 添加属性部分，目前有性格测试使用，知名该题目是为哪种性格做测试 -->
								<div class="form-line exampaper-type" id="aq-tag-charactortype">
										<span class="form-label"><span class="warning-label">*</span>性格类型：</span>
										<select id="charactortype-from-select" class="df-input-narrow">
											<c:forEach items="${CharactorTypeList }" var="item">
												<option value="${item.charactorTypeId }" data-privatee="${item.name }" >${item.name } </option>
											</c:forEach>
									
										</select><a class="add-charactorType-btn">添加</a><span class="form-message"></span>
									
										<div class="q-charactorType-label-list">
										</div>
								</div>
								<div class="form-line form-question-reference" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>来源：</span>
										<input type="text" class="df-input-narrow"><span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-question-examingpoint" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>考点：</span>
										<input type="text" class="df-input-narrow"><span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-question-keyword" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>关键字：</span>
										<input type="text" class="df-input-narrow"><span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-question-analysis" style="display: block;">
									<span class="form-label"><span class="warning-label"></span>题目解析：</span>
									<textarea class="add-question-ta"></textarea><span class="form-message"></span>
									<br>

								</div>

								<div class="form-line">
									<input id="btn-save" value="保存" type="submit" class="df-submit btn btn-info">
								</div>
							</form>

						</div>
						<div class="modal fade">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title">图片上传工具</h4>
						      </div>
						      <div class="modal-body">
						        <div id="add-question-img-dialog" title="图片上传工具">
									 <form>
										<div class="form-line img-destination">
											<span class="form-label">添加至：</span>
											<label></label>
											<input type="hidden" value=""/>
										</div>
										<div class="form-line add-update-quetstionfile">
											<span class="form-label">上传图片：</span>
											<div id="div-file-list">
											</div>
											<div class="form-line" id="uploadify"></div>
											<span class="form-message">请上传png、jpg图片文件，且不能大于1000KB。为了使得图片显示正常，请上传的图片长宽比例为2:1</span>
										</div>
									</form> 
								</div>
						      </div>
						      
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
						</div>
					</div>
				</div>
			</div>
			
		</div>

		<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="copy">
							<p>
								51jobpass.com Copyright © <a href="http://www.51jobpass.com/" target="_blank">51jobpass</a> - <a href="." target="_blank">主页</a> | <a href="http://www.51jobpass.com/" target="_blank">关于我们</a> | <a href="http://www.51jobpass.com/" target="_blank">FAQ</a> | <a href="http://www.51jobpass.com/" target="_blank">联系我们</a>
							</p>
						</div>
					</div>
				</div>

			</div>

		</footer>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
		
		<script type="text/javascript" src="resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
		<script type="text/javascript" src="resources/js/question-upload-img.js"></script>
		<script type="text/javascript" src="resources/js/field-2-point.js"></script>
		<script type="text/javascript" src="resources/js/question-add.js"></script>
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		
	</body>
</html>