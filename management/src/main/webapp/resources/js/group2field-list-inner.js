$(function() {
	var height = this.body.clientHeight;

	$(parent.document.getElementById("group2field-iframe")).css("height", height);
	
	var selectGroupId = $(".user-group-nav .active", parent.document).data("id");
	
	$("#6yccyyyy").click(function() {
		 // 获取活动的点击组
		var selectGroupId = $(".user-group-nav .active", parent.document).data("id");
		if(selectGroupId == 0 || selectGroupId == "0")
		{
			util.error("好歹选个组吧亲？");
			return;
		}
		$(parent.document.getElementById("add-field2group-modal")).modal({
			backdrop : true,
			keyboard : true
		});
       
		var selectGroupName = $(".user-group-nav .active", parent.document).text().trim();

		// groupName是显示的
		$("#field-add2group-form #group-add", parent.document).val(selectGroupName);
		$("#field-add2group-form #group-add", parent.document).data("id", selectGroupId);
		// groupID是隐藏的
		$("#field-add2group-form #group-add-id", parent.document).val(selectGroupId);
		
		// 填充fields，获取对应groupID已经有的fields，
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "secure/group2Field/group2Field-detail/" + selectGroupId,
			success : function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				if (message.result == "success") {
					//将message.object里面的内容写到 div（class=q-label-list）里面
					var innerHtml = "";
					$.each(message.object, function(index, element) {
						innerHtml += "<span class=\"label label-info q-label-item\"" + " data-id=" + element.fieldId + ">" + element.fieldName + "  <i class=\"fa fa-times\"></i>	</span>";
					});
					console.log(innerHtml);
					$("#fieldlist2group.q-label-list",parent.document).html(innerHtml);
					
				} else {
					util.error("获取标签失败请稍后尝试:" + message.result);
				}

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});
	});
   // 移除关系
   //移除的按钮class： unlink-group2field-r-btn
	$(".unlink-group2field-r-btn.btn-sm.btn-danger").click(function(){
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