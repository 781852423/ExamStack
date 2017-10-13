<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include/before_html.jsp"%>

<!DOCTYPE html>
<html>
	<head>

		<title>用户管理</title>
		<%@include file="include/head_files.jsp" %>
		<style type="text/css">
			.disable-btn, .enable-btn {
				text-decoration: underline;
			}

			.disable-btn, .enable-btn {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<table class=" table">
										<!-- <h4>
											对应章节
										</h4> -->
										<thead>
											<tr>
												<td>章节内容</td>
											</tr>
										</thead>
										<!-- <thead>
											<tr>
												<td></td>
												<td style="display:none;">ID</td>
												<td>培训名</td>
												<td>创建时间</td>
												<td>创建人</td>
												<td>操作</td>
											</tr>
										</thead> -->
										<tbody>
											<c:forEach items="${sectionList }" var="item">
												<tr>
													<td><a class="training-sections" data-id="${item.sectionId }" >${item.sectionName }</a></td>
													
													<td>
														<a class="delete-paper btn-sm btn-danger">删除</a>
													</td>
												</tr>
											</c:forEach>
																																		
											
										</tbody>
										<tfoot>
											<!-- <tr>
												<td colspan="2">
												<button class="btn btn-sm btn-info add-section-btn">新增章节</button>
												</td>
											</tr> -->
										</tfoot>
									</table>
	</body>
	
</html>