<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String servletPath = (String)request.getAttribute("javax.servlet.forward.servlet_path");
String[] list = servletPath.split("\\/");
request.setAttribute("role",list[1]);
request.setAttribute("topMenuId",list[2]);
request.setAttribute("leftMenuId",list[3]);
%>

<!DOCTYPE html>
<html>
  <head>
   
		<title>试卷编辑</title>
		<%@include file="include/head_files.jsp" %>
		<style>
			.add-more-qt-to-paper{
				cursor: pointer;
				color: #1ba1e2;
			}
			.add-more-qt-to-paper:hover{
				color:#ff7f74;
			}
			.add-more-qt-to-paper i{
				color: #47a447;
				cursor: pointer;
				margin-right:5px;	
			}
			
			#qt-selector-iframe{
				border:none;
				height:600px;
			}
			.tmp-ques-remove{
				margin-left:10px;
			}
			
			.question-point{
				padding:0 8px;
				margin:0 2px;
				border-bottom: 1px solid;
				cursor:pointer;
			}
			.question-point:hover{
				color:#ff7f74;
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
							<h1><i class="fa fa-file-text"></i> 修改试卷 </h1>
						</div>
						<div class="page-content">
							<div class="def-bk" id="bk-exampaper">

								<div class="expand-bk-content" id="bk-conent-exampaper">
									<div id="exampaper-header">
										<div id="exampaper-title">
											<i class="fa fa-send"></i>
											<span id="exampaper-title-name"> ${exampapername} </span>
											<span style="display:none;" id="exampaper-id">${exampaperid}</span>
											
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
												
											
											</div>
											<div style="margin-top: 5px;">
												<span>试卷总分：</span><span id="exampaper-total-point" style="margin-right:20px;"></span>
												
											</div>
											<div style="float: right;position: relative;top: -50px;">
											<button class="btn btn-danger save-paper-btn"><i class="fa fa-save"></i>保存试卷</button>
											</div>
										</div>
										<table class="table">
										<thead><tr><th>选择</th><th>ID</th><th>名称</th><th>每题分数</th><th>设计题目数量</th><th>已经添加题目数量</th><th>添加题目</th></tr></thead>
										<tbody>
											<c:forEach items="${paperParts}" var="PaperPart">
											 <tr>
											     <td><input type='checkbox' name='ckb'/></td>
											     <td>${PaperPart.id}</td>
	                                             <td><a href="#part_${PaperPart.id}" class="partNavi">${PaperPart.name}</a></td>
	                                             <td>${PaperPart.pointPerQuestion}</td>
	                                             <td>${PaperPart.questionCount}</td>
	                                             <td></td>
	                                             <td>
	                                             	  <span class="add-more-qt-to-paper">
	                                                     <i class="small-icon fa fa-plus-square" title="添加选项"></i>
	                                                     <span>增加更多题目</span>
	                                                   </span>
	                                             </td>	                                          
	                                           </tr>
	                                        </c:forEach>
										</tbody>
										</table>
									</div>
									
									
									<div id="exampaper-body" style="padding:0px;">
									<div class="mk_optionsbox">
                                    <h2>切换题目</h2>
                                        <div class="mk_options">
                                               <c:forEach items="${paperParts}" var="PaperPart">
                                                <a href="#part_${PaperPart.id}" class="partNavi">${PaperPart.name}</a>
                                               </c:forEach>                                 
                                        </div>
                                    </div>
                                    <div>
										${paperPartsStr}
									</div>
									<div id="exampaper-footer">
										<div id="question-navi">
											<div id="question-navi-controller">
												<i class="fa fa-arrow-circle-down"></i>
												<span>答题卡[点击可展开]</span>
											</div>
											<!-- 答题卡的内容 ,分段表示-->
											<div id="question-navi-content">
											    <c:forEach items="${paperParts}" var="PaperPart">
                                                     <div class="mkrf_item" id="answersheet_${PaperPart.id }">
                                                         <div class="mftm_tt">${PaperPart.name }
                                                              <span class="mf_tip">(共${PaperPart.questionCount}题)</span>
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
					</div>
				</div>
			</div>
			<div class="modal fade" id="question-selector-modal">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title">选择试题添加到该试题PartId：</h4><span id="partIdSpanInModelDailog"></span>
						      </div>
						      <div class="modal-body">
						        <iframe  id="qt-selector-iframe" src="<%=list[1]%>/question/question-list/filterdialog-0-0-0-0-1.html" width="100%"></iframe>
						      </div>
						      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
							        <button id="add-list-to-exampaper" type="button" class="btn btn-primary">添加选中</button>
							      </div>
						      
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
			</div>
			<div class="modal fade" id="question-point-modal">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title">修改分数</h4>
						      </div>
						      <div class="modal-body">
						        	<form>
										<div class="form-line qt-point-destination">
											<span class="form-label">分数：</span>
											<label></label>
											<input type="text" value=""/>
										</div>
										<div class="form-line">
											<button class="btn btn-sm btn-success" id="update-point-btn"><i class="fa fa-check"></i>仅修改第<span id="qt-point-target-index"></span>题</button>
											<button class="btn btn-sm btn-warning" id="update-point-type-btn"><i class="fa fa-random"></i>批量更新该题型</button>
										</div>
									</form> 

						      </div>
						      
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
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
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/exampaper-edit.js"></script>
		
	</body>
</html>