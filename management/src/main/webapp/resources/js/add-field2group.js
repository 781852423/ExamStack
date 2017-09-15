$(function() {
	create_field2group.initial();
});

var create_field2group = {

	initial : function initial() {
		this.bindSubmitForm();
		this.getFields();
	},

	bindSubmitForm : function bindSubmitForm() {
		$("#add-field2group-modal-btn").click(function() {
			var result = create_field2group.verifyInput();
			if (result) {
				var data = new Object();
				data = create_field2group.composeEntity(); // action=admin/user/add-group2field
				var action = $(this).data("action");
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : action,
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("添加成功！", function() {
								//window.location.reload();
								$(".list-group-item.group-nav-item.active").click();
							});
						}else 
						{
						   alert(message.result);
							
						}
							
						}
					
				});
			}

			return false;
		});
	},

	verifyInput : function verifyInput() {
		//return true;
		$(".form-message").empty();
		var result = true;
		var check_field = this.checkField();
		
		result = check_field;
		return result;
	},
	
	composeEntity : function composeEntity() {
		var field2group_entity = new Object();
		
		field2group_entity.groupId = $(".list-group-item.group-nav-item.active").data("id");
		
		var fieldList = new Array();

		$(".q-label-item").each(function() {
			var field = new Object();
			field.fieldId = $(this).data("id"); 
			fieldList.push(field);
		});

		field2group_entity.fieldList = fieldList;
		
		return field2group_entity;
	},
	
	checkField : function checkField() {
		var content = $("#fieldlist2group").text();
		if (content == "") {
			$("#messageforAddField").text("请输入试题内容");
			$("#field-from-select").focus();
			$("#fieldlist2group").addClass("has-error");
			return false;
		}
		
		return true;
	},
	
	getFields: function getFields()
	{
		$(".add-field-btn").click(function() {
			var label_ids = $(".q-label-item");
			var flag = 0;
			label_ids.each(function() {
				if ($(this).data("id") == $("#field-from-select").val())
					flag = 1;
			});
			if (flag == 0) {
				var selected = $("#field-from-select").find("option:selected");

				$(".q-label-list").append("<span class=\"label label-info q-label-item\"  data-id=" + $("#field-from-select").val() + ">" + $("#field-from-select :selected").text() + "  <i class=\"fa fa-times\"></i>	</span>");
			} else {
				util.error("不能重复添加");
			}
		});


		$(".q-label-list").on("click", ".fa", function() {
			$(this).parent().remove();
		});
	}
	
}; 