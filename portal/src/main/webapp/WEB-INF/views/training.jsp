<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
<head>

	<title>培训</title>
	
	<%@include file="include/head_files.jsp" %>
	
	<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
	<script type="text/javascript" src="resources/js/training-comment.js"></script>
	<!-- <script type="text/javascript" src="resources/js/mediaelement/mediaelement-and-player.min.js"></script>
	<link href="resources/js/mediaelement/mediaelementplayer.css" rel="stylesheet">
	<link href="resources/js/mediaelement/mejs-skins.css" rel="stylesheet"> -->
	<link href="resources/js/videojs/video-js.css" rel="stylesheet">

	<script type="text/javascript">
		videojs.options.flash.swf="resources/js/videojs/video-js.swf";
		$(function(){
			var myPlayer = _V_("my_video");
			var count = 0;
			var timer;
			myPlayer.on('play',function(){
				timer = setInterval(function(){
					count ++;
				},1000);
			});
			myPlayer.on('paused',function(){
				clearInterval(timer);
			});
			myPlayer.on('ended',function(){
				myPlayer.currentTime(0);
				myPlayer.pause();
				var duration = parseFloat($("#training-duration").val());
				var process = 0;
				if(duration + count >= myPlayer.duration()){
					//视频播放完毕后，如果持续时间大于视频时间，则培训完成，进度100%
					process = 1;
				}else{
					//视频播放完毕，提交培训历史，进度为持续时间/视频持续时间
					process = (duration + count) / myPlayer.duration();
				}
				var data= new Object();
				data.sectionId = $("#section-id").val();
				data.duration = duration + count;
				data.process = process;
				data.trainingId = $("#training-id").val();
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "student/set-training-hist",
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("培训进度上传成功！进度=" + process);
						} else {
							util.error("培训进度上传失败！");
						}
					},
					error : function(){
						util.error("上传培训进度错误，请稍后尝试！");
					}
				});
			});
		});
	</script>
<style>
.training-top {
	position: absolute;
	top: 0;
	left: 0;
	height: 50px;
	line-height: 50px;
	padding-left: 10px;
	background-color: #1ba1e2;
	color: #FFF;
	font-size: 16px;
	width: 100%;
}

.control-pane {
	position: absolute;
	top: 0;
	right: 0;
	background-color: #FFF;
	height: 100%;
	width: 287px;
	z-index: 100;
}

body, html {
	margin: 0px;
	height: 100%;
}

.autoHeight {
	/* width:100%; */
	position:relative;
	height: 100%;
	background: rgb(31, 31, 31);
	margin-right: 287px;
	padding-top: 50px;
}

.section-title {
	margin-left: 50px;
}

.nav-tabs>li>a {
	border-radius: 0 0 0 0;
}

.nav-tabs>li>a:hover {
	border-color: #FFF #FFF #ddd;
}

.section-list, .comment-list {
	list-style: none;
	padding-left: 0;
	padding-top: 5px;
	color:#FFF;
}

.section-list {
	background-color: #f0f0f0;
}

/* 		.comment-list{
			list-style: none;
			} */
.section-list li {
	
}

.section-list li:hover {
	background-color: #f0f0f0;
	cursor: pointer;
}

.section-list li.active {
	background-color: #1ba1e2;
	color: #FFF;
}

.section-list li.active a {
	color: #FFF;
}

.section-list li div {
	padding: 10px;
	margin-left: 20px;
	border-left: 1px solid #FFF;
	padding-left: 10px;
}

.section-list li i {
	position: relative;
	left: -16px;
}

.comment-user-info {
	padding-left: 40px;
}

.comment-user-img-content {
	width: 30px;
	position: absolute;
	top: 0px;
	left: 0px;
}

.comment-user-container {
	position: relative;
}

.comment-user-img {
	width: 30px;
}

.comment-date {
	font-size: 10px;
}

.comment-list-item {
	margin-bottom: 20px;
}

.tab-content {
	padding: 0px;
	border: 0;
}

.video-div{
	
}
</style>

</head>

<body>

	 <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->
	

	<!-- Slider starts -->

	<div class="autoHeight">

		<div class="training-top">
			<i class="fa fa-chevron-circle-left"></i>
			<!-- 课程主页 -->

			<span>${section.trainingName }</span> <span class="section-title">${section.sectionName }</span>
		</div>

		<div class="video-div" style="background-color: #000; height: 100%;">
			<input type="hidden" value="${duration }" id="training-duration">
			<input type="hidden" value="${sectionId}" id="section-id">
			<input type="hidden" value="${section.trainingId}" id="training-id">
			<input type="hidden" value="${section.trainingId}" id="training-id">	
			<video id="my_video" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%" style="width:100%;height:100%;"
		      poster="resources/images/bg.png"
		      data-setup="{}">
		      	<c:choose>
		      		<c:when test="${section.fileType eq '.mp4' }">
		      			<source src="${section.filePath }" type='video/mp4'>
		      		</c:when>
		      		<c:when test="${section.fileType eq '.flv' }">
		      			<source src="${section.filePath }" type='video/flv'>
		      		</c:when>
		      		<c:otherwise>
		      			<source src="${section.filePath }">
		      		</c:otherwise>
		      	</c:choose>
			    			    
  			</video>

		</div>

		<div style="background-color: #000; height: 100%; display: none;">

		</div>
	</div>
	<div class="control-pane">
		<ul id="myTabs" class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#home"
				id="home-tab" role="tab" data-toggle="tab" aria-controls="home"
				aria-expanded="true"> <i class="fa fa-th-list"></i> 选择章节
			</a></li>
			<li role="presentation"><a href="#profile" role="tab"
				id="profile-tab" data-toggle="tab" aria-controls="profile"><i
					class="fa fa-comments-o"></i> 评论</a></li>
		</ul>
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="home">
				<ul class="section-list">
					<c:forEach items="${sectionList }" var="item">
						<c:choose>
							<c:when test="${item.sectionId == sectionId }">
								<li class="active">
									<div>
										<i class="fa fa-video-camera"></i> <a>${item.sectionName }</a>
									</div>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<div>
										<i class="fa fa-video-camera"></i> <a href="student/training/${item.trainingId }?sectionId=${item.sectionId}">${item.sectionName }</a>
									</div>
								</li>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</ul>
			</div>
			<div role="tabpanel" class="tab-pane" id="profile">
				<div class="expand-bk-content" id="bk-conent-comment"
					style="margin-top: 10px; padding: 10px;">
					<div id="comment-title" style="margin-bottom: 15px;">
						<i class="fa fa-comments"></i> <span> 学员评论 </span>

					</div>
					<form class="comment-form">
						<textarea rows="" cols="" style="width: 100%; height: 95px;"
							placeholder="随便说点什么吧..."></textarea>
						<input class="btn btn-primary" type="submit" value="发表评论">
					</form>
					<div class="comment-total">
						<span class="comment-total-num">1</span>条评论
					</div>
					<ul class="comment-list">
												
					</ul>
				</div>
				<div id="show-more-div">
					<input type="hidden" id="idx-hidden" value="2"> 
					<input type="hidden" id="last-floor-hidden" value="0">
					<button id="show-more-btn" disabled="disabled" style="margin:10px 0 10px 0;">没有更多评论了...</button>
				</div>
			</div>
		</div>
	</div>

 <%@include file="include/footer_cm_js.jsp" %>
	
</body>
</html>
