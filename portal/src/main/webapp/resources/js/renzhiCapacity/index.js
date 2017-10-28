$(document).ready(function() {
	$('#prompt-msg').modal('show');
	
	var limit_secs = $("#limit_secs").val();

	var limit_m = parseInt(limit_secs / 60);
	var limit_s = parseInt(limit_secs % 60);
	
	var settime = setInterval(function () {
		showtime();
	}, 1000);

	function endAnswer(){
	    showAnswerResult();
	}
	
	function showAnswerResult()
	{
		
	}
	
	function showtime() {
		$('#limit_m').html(limit_m);
		$('#limit_s').html(limit_s);
		limit_s--;
		if (limit_s < 0) {
			limit_m = limit_m - 1;
			limit_s = 59;
		}
		if (limit_m < 0) {
			clearInterval(settime)
			endAnswer();
		}
	}

	$(".right-box ul").on("click","li",function(){
		$(this).addClass("box-curr-active");
		var ajaxUrl = '/picture/getWideMakeType';
		var data = {};
		data.ori_val = $("#original").val();
		data.comp_val = $(this).children("input[name='comp']").val();
		data.type = $("#type").val();
		data.qid = $("#qid").val();
		$.ajax({
			url: ajaxUrl,
			type: 'POST',
			dataType: 'json',
			data: data,
			success:function(json){
				if(json.status == true){
					$("#original").val(json.next);
					$("#qid").val(json.qid);
					$(".main-left .left-box").html(json.all.txt);
					$(".right-box ul").html(json.all.html);
				} else {
					 //显示结果
					
				}
			}
		});
	})
})