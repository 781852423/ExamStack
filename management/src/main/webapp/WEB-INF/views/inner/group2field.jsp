<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
		<base href="<%=basePath%>">

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>用户组-题库权限管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon"
		href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">

		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style type="text/css">

		</style>
	</head>
	<body>
		<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
		<div id="question-list">
			<div class="table-controller">

				<div class="btn-group table-controller-item" style="float: left">
					<button class="btn btn-default btn-sm" id="add-user-modal-btn">
						<i class="fa fa-plus-square"></i> 添加题库
					</button>
				</div>
				
				<div class="table-search table-controller-item-page">
					<ul class="pagination pagination-sm">
						${pageStr}
					</ul>
				</div>

			</div>
			<table class="table-striped table">
				<thead>
					<tr>
					    <td>ID</td>
					    <td>组ID</td>
                        <td>组名称</td>
						<td>题库ID</td>
						<td>题库名称</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${user2GroupList }" var="item">
						<tr>
							<td><span class="r-id">${item.id }</span></td>
							
							<td><span class="r-id">${item.groupId }</span>
							</td>
							<td>
								<div class="r-name">
									${item.groupName }
								</div>
								
							</td>
							
							<td>
                                <div class="r-id">
                                    ${item.fieldId }
                                </div>
                            </td>
                            
							<td>
								<div class="r-name">
									${item.fieldName }
								</div>
						
							</td>
							
							
							<td>	
						        <span class="unlink-group2field-r-btn btn-sm btn-danger" title="移出分组" data-id="${item.id }" data-group="${groupId }">
													<i class="fa fa-times-circle"></i>
							    </span>
								
								
								
							</td>
						</tr>

					</c:forEach>

				</tbody>
				<tfoot></tfoot>
			</table>
			<input id="authority-type" type="hidden" value="${authority }">
		</div>
		<div id="page-link-content">
			<ul class="pagination pagination-sm">
				${pageStr}
			</ul>
		</div>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript"
		src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/user-list-inner.js"></script>

		<!-- Bootstrap JS -->
		<script type="text/javascript"
		src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function() {
				var btnSearch = $("#btn-search");
				
				$(".unlink-group2field-r-btn").click(function(){
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "GET",
						url : "secure/delete-user-group-" + $(this).data("id") + "-" + $(this).data("group"),
						success : function(message, tst, jqXHR) {
							if (!util.checkSessionOut(jqXHR))
								return false;
							if (message.result == "success") {
								util.success("操作成功", function() {
									window.location.reload();
								});
							} else {
								util.error("操作失败请稍后尝试:" + message.result);
							}

						},
						error : function(jqXHR, textStatus) {
							util.error("操作失败请稍后尝试");
						}
					});

					return false;
				});
			});

			function getUrlParam(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
				//构造一个含有目标参数的正则表达式对象
				var r = window.location.search.substr(1).match(reg);
				//匹配目标参数
				if (r != null)
					return unescape(r[2]);
				return null;
				//返回参数值
			}
		</script>
	</body>
</html>