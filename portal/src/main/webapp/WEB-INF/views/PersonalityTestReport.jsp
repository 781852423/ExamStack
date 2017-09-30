<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		
		<title>职业性格测试-九型人格测试报告</title>
		 <%@include file="include/head_files.jsp" %>
		  <link href="resources/css/exam.css" rel="stylesheet">
	</head>
   
	<body>
		 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		<!-- Slider starts -->
		<div class="content" style="margin-bottom: 100px;">
			<div class="container">
				<div class="row">
				    <div class="page-header">
							<h1><i class="fa fa-list-ul"></i>职业性格测试-九型人格测试报告</h1>
					</div>
					<div>
						<h4 class="personalityTestreportParagrahTitle">总分：${totalScore}</h4>
						<h5>各性格因素测试分数(由高到低排列，若分数小于10说明该方面特质不突出，分数最高的就是你的主要人格属性):</h5>
					</div>
					<div style="width:50%">
					<table class="table table-striped table-hover">
					<caption><strong>职业性格测试-九型人格测试分项分数</strong></caption>
					<thead>
					  <tr>
					    <th>人格分类</th>
					    <th>单项得分</th>
					  </tr>
					  </thead>
					  <tbody>
						  <c:forEach items="${PersonalityScoreList }" var="item">
							  <tr>
							    <td><span>${item.name }</span></td>
							    <td><span>${item.danxiangScore}</span></td>
							  </tr>
						  </c:forEach>
					  </tbody>
					</table>
					</div>
					<div>
						<div>
							<h5 class="personalityTestreportParagrahTitle">结果分析：</h5>
							  在以上9中特质中，你在<strong>${resultCharactorName}</strong>（得分最高） 方面的特质比较突出，你属于
							<strong>${resultCharactorName}</strong>人格。
						</div>
					<div>
						<h5 class="personalityTestreportParagrahTitle">关于${resultCharactorName}人格释义</h5>
						<div>
						<pre class="personalityTestreportSummary">${resultCharactorSummary}</pre>
						
						</div>
					</div>
					<div>
					        <h5 class="personalityTestreportParagrahTitle">求职建议：</h5>
					        <div>
					                                   在求职上，可以重点寻找合适自己性格的工作作为终身的事业去发展。具体可以咨询我们的老师。
					        </div>            
					                
					</div>
					
					<div>
                    ----------------------------------
					
					（本报告仅供参考）
					</div>

					</div>
				</div>
				
			</div>
			
		</div>
		
		
			
		 <%@include file="include/footer_cm_js.jsp" %>
		 
	</body>
</html>
