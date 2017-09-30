
function displaySelectedXuepaiSummary (){
	$('#selectedXuepaiName').html('');
	$('div.xuepaiSummaryDetails pre').html('');	
	var xuepaiName = $('ol li.active .xuepaiName').html();
	var xuepaiSummary = $('li.active div.xuepaiSummary').text();
	
	//console.log(xuepaiName);
	//console.log(xuepaiSummary);
	$('#selectedXuepaiName').html(xuepaiName);
	$('div.xuepaiSummaryDetails pre').html($.trim(xuepaiSummary));
	
	$('div#xuepaiSummaryDiv').show();
}
$(function(){
		$('div#xuepaiSummaryDiv').hide();
		
		$("li.xuepaiItem").click(
	        function(){
        	
	        $('li.xuepaiItem').removeClass('active');
			$(this).addClass('active');
			displaySelectedXuepaiSummary();
		}
		);
	});
$(document).ready(
		function()
		{
			$($('ol li.xuepaiItem')[0]).addClass('active');
			$('div#xuepaiSummaryDiv').hide();
			displaySelectedXuepaiSummary();
		}
);