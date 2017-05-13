<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
<head>

<title>练习</title>

 <%@include file="include/head_files.jsp" %>
<style>
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
	.table>tbody>tr>td, .table>tfoot>tr>td {
	padding: 8px 0;
	line-height: 1.42857143;
	vertical-align: middle;
	border-top: 1px solid #ddd;
}

a.join-practice-btn {
	margin-top: 0;
}
</style>

</head>

<body>
	   <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

	<!-- Slider starts -->
	<div class="content" style="margin-bottom: 100px;">

		<div class="container">
			<ul class="nav nav-pills " style="margin: 20px 0;">
				<c:forEach items="${fieldList }" var="item">
					<li role="presentation" <c:if test="${item.fieldId == fieldId }"> class="active"</c:if>><a href="student/practice-list?fieldId=${item.fieldId }">${item.fieldName }</a></li>
				</c:forEach>
			</ul>
			<div class="row">
				<div class="col-xs-6">
					<div style="border-bottom: 1px solid #ddd;">
						<h3 class="title">
							<i class="fa fa-cloud-upload"></i> 强化练习
						</h3>
						<p>自主选择具体考点，各个击破</p>
					</div>

					<div class="question-list">

						<c:forEach items="${kparl}" var="item">

							<table class="table-striped table">
								<thead>
									<tr>
										<td colspan="4"><h6>${item.knowledgePointName }</h6>
											<span style="color: #428bca;">学习进度：<fmt:formatNumber
													value="${item.finishRate }" type="percent" /></span></td>
									</tr>
									<tr>
										<td>题型</td>
										<td>全部</td>
										<td>已做题目</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${item.typeAnalysis }" var="tp">
										<tr>
											<td>${tp.questionTypeName }</td>
											<td><span class="span-info question-number">${tp.restAmount + tp.rightAmount + tp.wrongAmount }</span></td>
											<td><span class="span-success question-number-2">${tp.rightAmount + tp.wrongAmount }</span></td>
											<td><a href="student/practice-improve/${fieldId }/${item.knowledgePointId }/${tp.questionTypeId }"
												class="btn btn-success btn-sm join-practice-btn">参加练习</a></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>
						</c:forEach>

					</div>
				</div>
				<div class="col-xs-6">
					<div style="border-bottom: 1px solid #ddd;">
						<h3 class="title">
							<i class="fa fa-eraser"></i> 错题练习
						</h3>
						<p>收录做过的所有错题</p>
					</div>
					<div class="question-list">

						<table class="table-striped table">
							<thead>

								<tr>
									<td>知识分类</td>
									<td>题目数量</td>
									<td></td>
									<td></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${historyMap }" var="item">
									<tr>
										<td>${pointMap[item.key].pointName }</td>
										<td><span class="span-info question-number">${item.value.wrongAmount }</span></td>
										<td><a href="student/practice-incorrect/${item.value.fieldId }/${item.value.pointId }"
											class="btn btn-success btn-sm join-practice-btn">参加练习</a></td>
									</tr>
								</c:forEach>

							</tbody>
							<tfoot></tfoot>
						</table>

					</div>
					<div style="border-bottom: 1px solid #ddd; margin-bottom: 20px;">
						<h3 class="title">
							<i class="fa fa-superscript"></i> 随机练习
						</h3>
						<p>从题库中随机取出试题练习</p>

					</div>
					<a class="btn btn-success " href="student/practice-test/${fieldId }">随机来20道 </i>
					</a>
				</div>

			</div>


		</div>

	</div>

	  <%@include file="include/footer_cm_js.jsp" %>
	  
	<script>
		$(function() {
			bindQuestionKnowledage();
			var result = checkBrowser();
			if (!result) {
				alert("请至少更新浏览器版本至IE8或以上版本");
			}
		});

		function checkBrowser() {
			var browser = navigator.appName;
			var b_version = navigator.appVersion;
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE7.0") {
				return false;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return false;
			} else
				return true;
		}

		function bindQuestionKnowledage() {
			$(".knowledge-title").click(function() {
				var ul = $(this).parent().find(".question-list-knowledge");

				if (ul.is(":visible")) {
					$(this).find(".fa-chevron-down").hide();
					$(this).find(".fa-chevron-up").show();

					$(".question-list-knowledge").slideUp();

				} else {
					$(".fa-chevron-down").hide();
					$(".fa-chevron-up").show();

					$(this).find(".fa-chevron-up").hide();
					$(this).find(".fa-chevron-down").show();

					$(".question-list-knowledge").slideUp();
					ul.slideDown();

				}

			});
		}
	</script>
	
</body>
</html>

