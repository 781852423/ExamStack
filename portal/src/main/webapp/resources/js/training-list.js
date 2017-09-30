$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		
		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId==null||pageId=="")
			{
				pageId = 0;
			}
			question_list.redirectUrl(pageId);
			
		});
	},
	
	redirectUrl : function redirectUrl(pageId)
	{
		document.location.href = 'training-list/'+pageId;
	}
	
};