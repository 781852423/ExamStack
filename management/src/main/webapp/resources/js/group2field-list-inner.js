$(function() {
	var height = this.body.clientHeight;

	$(parent.document.getElementById("group2field-iframe")).css("height", height);
	
	var selectGroupId = $(".user-group-nav .active", parent.document).data("id");
	
	$("#add-field2group-modal-btn").click(function() {
		$(parent.document.getElementById("add-field2group-modal")).modal({
			backdrop : true,
			keyboard : true
		});
        // 获取活动的点击组
		var selectGroupId = $(".user-group-nav .active", parent.document).data("id");
		var selectGroupName = $(".user-group-nav .active", parent.document).text().trim();

		// groupName是显示的
		$("#field-add2group-form #group-add", parent.document).val(selectGroupName);
		$("#field-add2group-form #group-add", parent.document).data("id", selectGroupId);
		// groupID是隐藏的
		$("#field-add2group-form #group-add-id", parent.document).val(selectGroupId);
	});
   // 移除关系
   //移除的按钮class： unlink-group2field-r-btn
	$(".unlink-group2field-r-btn").click(function(){
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "secure/delete-group2field-" + $(this).data("id"),
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