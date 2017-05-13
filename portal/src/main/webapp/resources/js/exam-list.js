/**
 * 用于/exam-list页面 ====> views/exam.jsp
 */
			$(function(){
				$(".apply-exam-btn").click(function(){
					var examId = $(this).data("id");
					
					jQuery.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "GET",
						url : "student/exam/send-apply/" + examId,
						success : function(message, tst, jqXHR) {
							if (message.result == "success") {
								window.location.reload();
							} else {
								alert(message.result);
							}
						}
					});
				});
			});

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
				if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
					return false;
				} else if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE6.0") {
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
