<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>公告</title>
		  <%@include file="include/head_files.jsp" %>
		
		<style>
			.question-number {
				color: #5cb85c;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			.question-number-2 {
				color: #5bc0de;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			.question-number-3 {
				color: #d9534f;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			a.join-practice-btn {
				margin: 0;
				margin-left: 20px;
			}

			.content ul.question-list-knowledge {
				padding: 8px 20px;
			}

			.knowledge-title {
				background-color: #EEE;
				padding: 2px 10px;
				margin-bottom: 20px;
				cursor: pointer;
				border: 1px solid #FFF;
				border-radius: 4px;
			}

			.knowledge-title-name {
				margin-left: 8px;
			}

			.point-name {
				width: 200px;
				display: inline-block;
			}
			.col-xs-3 {
				width: 22%;
			}
		</style>

	</head>

	<body>
		<%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->
        
		<div class="content" style="padding:30px 0 0 0;">
			<div class="container">
				<div id="content" class="clearfix">
					<div class="def-bk">
						<div class="def-bk-title">
							
						</div>
						<div class="def-bk-content" id="bk-conent-news-detail">
							<div class="news-body">
								<h1>${news.title }</h1>
								<div class="news-body-info">
									<span class="news-body-date"><fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd"/></span>
									<span>发布人：</span><span class="news-body-creator">${news.creator }</span>
								</div>
								<p>
									${news.content }
								</p>
	
							</div>
	
						</div>
					</div>
				</div>
			</div>
		</div>
		 <%@include file="include/footer_cm_js.jsp" %>
	</body>
</html>

