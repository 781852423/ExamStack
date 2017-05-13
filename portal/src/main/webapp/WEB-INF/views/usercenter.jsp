<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    	
		<title>用户中心</title>
		 <%@include file="include/head_files.jsp" %>
		 
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<!--[if lte IE 8]>
			<script type="text/javascript" src="resources/chartjs/excanvas.js"></script>
		<![endif]-->
		<style>
			.table-striped a{
				text-decoration: underline;
			}
			
			.span-success{
				color:#5cb85c;
				font-weight:bolder;
			}
			
			.span-danger{
				color:#d9534f;
				font-weight:bolder;
			}
			
			.span-info{
				color:#5bc0de;
				font-weight:bolder;
			}
			
			h6{
				font-weight:bold !important; 
			}
			
			.radar-legend li span {
				display: block;
				position: absolute;
				left: 0;
				top: 0;
				width: 20px;
				height: 100%;
				border-radius: 5px;
			}
			
			.radar-legend li {
				display: block;
				padding-left: 30px;
				position: relative;
				margin-bottom: 4px;
				border-radius: 5px;
				padding: 2px 8px 2px 28px;
				font-size: 14px;
				cursor: default;
				-webkit-transition: background-color 200ms ease-in-out;
				-moz-transition: background-color 200ms ease-in-out;
				-o-transition: background-color 200ms ease-in-out;
				transition: background-color 200ms ease-in-out;
			}				
			
			#field-switch{
				margin:15px 0 10px 0px;;
				height:34px;
				width:200px;
			}
		</style>
	</head>
	<body>
		<%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2">
						<ul class="nav default-sidenav">
							<li class="active">
								<a> <i class="fa fa-dashboard"></i> 用户中心 </a>
							</li>
							<li>
								<a href="student/analysis"> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li>
								<a href="student/exam-history"> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
							<li>
								<a href="student/training-history"> <i class="fa fa-book"></i> 培训历史 </a>
							</li>
							
						</ul>
					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-dashboard"></i> 用户中心</h1>
						</div>
						<div class="page-content row">
							<div class="col-xs-4">
								<h6>个人信息</h6>
								<div>
									<span >姓名：</span>
									<span> ${username }</span>
								</div>
								<div>
									<span >邮箱：</span>
									<span> ${email } </span>
								</div>
								<%-- <div>
									<span >专业：</span>
									<span> ${field } </span>
								</div>
								<div>
									<span >上次登录：</span>
									<span> <fmt:formatDate value="${lastLoginTime }" pattern="yyyy-MM-dd"/> </span>
								</div> --%>
								
							</div>
							
							

						</div>
						<div class="page-content row">
							<div class="col-xs-12" id="radar-div">
								<h6>知识掌握情况</h6>
								<select id="field-switch" class="form-control">
										<c:forEach items="${fieldList }" var="item">
											<option value="${item.fieldId }">${item.fieldName }</option>
										</c:forEach>
										<!-- <option value="4">公务员申论</option>
										<option value="5">医药行业考试</option> -->
								</select>
								<div class="page-content row">
									
									<div class="col-xs-8">
										<div id="mychart" style="height:450px;"></div>
										<p>此统计数据不包括简答、论述、分析等主观题</p>
									</div>
									<div class="col-xs-4" id="radar-legend">
										
									</div>
								</div>
								
							</div>
								
							
						</div>

					</div>
				</div>
			</div>
		</div>

		 <%@include file="include/footer_cm_js.jsp" %>
		
		<script type="text/javascript" src="resources/js/echarts-all.js"></script>
		<!-- <script type="text/javascript" src="resources/chartjs/Chart.min.js"></script> -->
		
		
		<script type="text/javascript">

		$("#field-switch").val("${fieldId}");
		$("#field-switch").change(function(){
			window.location.href="student/usercenter/"+ $(this).val();
		});
		
			
			$(function(){
				var option = {
					    
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'right',
					        y : 'bottom',
					        data:['完成率','正确率']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    polar : [
					       {
					           indicator : ${labels}
					        }
					    ],
					    calculable : true,
					    series : [
					        {
					            name: '预算 vs 开销（Budget vs spending）',
					            type: 'radar',
					            data : [
					                {
					                    value : ${finishrate},
					                    name : '完成率'
					                },
					                 {
					                    value : ${correctrate},
					                    name : '正确率'
					                }
					            ]
					        }
					    ]
					};
				var myChart = echarts.init(document.getElementById('mychart'));
				myChart.setOption(option);
			});
		</script>
	</body>
</html>