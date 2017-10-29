$(document).ready(function() {
    var currentIndex = 0;
    var starNums = 3;
    var answers = [];
    var rightNums = 0;
    var errorNums = 0;
    var cancheck = false;
    var tdNums = [];
    var gameStatus = '';
    var addCountPer = 6; // 增加阶数
    var limit_secs = 0;
    var limit_m = 0;
    var limit_s = 0;
    var totalQuestionNumber = parseInt($('#totalQuestionNumber').val());
    
    function showtime() {
        $('#limit_m').html(limit_m);
        $('#limit_s').html(limit_s);
        limit_s--;
        if (limit_s < 0) {
            limit_m = limit_m - 1;
            limit_s = 59;
        }
        if (limit_m < 0) {
        	if(settime != null)
        		{
        		  clearInterval(settime)
        		}
          
            endAnswer();
        }
    }
    
    function initParas()
    {
         currentIndex = 0;
         starNums = 3;
         answers = [];
         rightNums = 0;
         errorNums = 0;
         cancheck = false;
         tdNums = [];
         gameStatus = '';
         addCountPer = 6; // 增加阶数
         limit_secs = totalQuestionNumber*5; // 每道题给10秒钟时间
         
    	 limit_m = parseInt(limit_secs / 60);
         limit_s = parseInt(limit_secs % 60);
         for (var i = 0; i < 36; i++) {
             tdNums.push(i);
         }
    }


    function beginExam()
    {
    	 gameStatus = 'go';
    	 initParas();
         var settime = setInterval(function () {
            showtime();
        }, 1000);
        setStar();
    }
	// 结束答题
    function endAnswer() {
    	showTipStep(0, 0, '不好意思时间到！请看答题结果', 4);
    	showAnswerResult();
    }

    function setStarNums() {
        starNums = parseInt(currentIndex / addCountPer) + 3;
    }

    function randomsort(a, b) {
        return Math.random() > .5 ? -1 : 1;
    }
	
	function showTips()
	{
		  showTipStep(6, 1, '屏幕上将呈现一个6*6的正方形网格', 4);
          setTimeout(function () {
            showTipStep(6, 2, '其中一些单元格会呈现五角星，<br />你需要尽量记住单元格上五角星的位置，<br />之后，五角星会消失。', 6);
            setTimeout(function(){ initParas();setStar()}, 2);
        }, 4000);
        setTimeout(function () {
            showTipStep(6, 3, '根据记忆，用鼠标左键选出五角星的位置，<br />被选中的单元格会变成蓝色。', 8);
            var tmpArr = answers[0].split(',');
            setTimeout(function () {
                $('#grid-table td:eq(' + tmpArr[0] + ')').trigger('click')
            }, 2000);
            setTimeout(function () {
                $('#grid-table td:eq(' + tmpArr[1] + ')').trigger('click')
            }, 3500);
            setTimeout(function () {
                $('#grid-table td:eq(' + tmpArr[2] + ')').trigger('click')
            }, 5000);
        }, 10000);
        setTimeout(function () {
            showTipStep(6, 4, '如果需要取消选择，用鼠标再单击一次。<br />被选中的单元格需要<span style="color: red;">等于</span>呈现五角星的<br />数量。', 6);
            var tmpArr = answers[0].split(',');
            setTimeout(function () {
                $('#grid-table td:eq(' + tmpArr[2] + ')').trigger('click')
            }, 2000);
            setTimeout(function () {
                $('#grid-table td:eq(' + tmpArr[2] + ')').trigger('click')
            }, 4000);
        }, 18000);
        setTimeout(function () {
            showTipStep(6, 5, '完成选择后请按“确认选择”按钮进行确认。<br />作答后系统将自动进入下一组任务。<br /><span style="color: red;">你将不能返回修改。</span>', 6);
            setTimeout(function () {
                $('#confirm').trigger('click')
            }, 4000);
        }, 24000);
        setTimeout(function () {
            showTipStep(6, 6, '请用尽可能快的速度完成作答。<br /><span style="color: red;">你将进入练习环节，熟悉作答方式。</span><br />完成练习后，点击<span style="color: red;">开始测试</span>按钮进入正式测试。', 6);
            setTimeout(function () {
                
            }, 6000);
        }, 30000);
	}


    function showTipStep(tipTotal, tipCount, tipContent, timeout) 
    {
        var paginationHtml = '';
        for (var i = 0; i < tipTotal; i++) {
            if (i < tipCount) {
                paginationHtml += '<span class="pagination-bullet pagination-bullet-active"></span>';
            } else {
                paginationHtml += '<span class="pagination-bullet"></span>';
            }
        }
        ds.dialog({
            title: '答题说明',
            content: '<div style="text-align: center;">' + tipContent + '<br/>' + paginationHtml + '</div>',
            timeout: timeout,
            showCloseButton: false
        });
    }

   
    function setStar() {
    	$('table td.check').removeClass('check');
        var tmpArr = [];
        var randomNums = tdNums.sort(randomsort);
        for (var i = 0; i < starNums; i++) {
            var tdIndex = randomNums[i];
            tmpArr.push(tdIndex);
            $('table td:eq(' + tdIndex + ')').addClass('star');
        }
        answers.push(tmpArr.sort().join(','));
        currentIndex++;
        setTimeout(clearStar, 800);
    }

    function clearStar() {
        $('table td.star').removeClass('star');
        cancheck = true;
    }

    $('#confirm').click(function () {
        if ($(this).hasClass('no-confirm')) {
            return;
        }
        var currentArr = [];
        $('#grid-table tr td.check').each(function (i, n) {
            var trIndex = $(n).parent('tr').index();
            var tdIndex = $(n).index();
            tdIndex = trIndex * 6 + tdIndex;
            currentArr.push(tdIndex);
        });
        if (currentArr.sort().join(',') == answers[currentIndex - 1]) {
            rightNums++;
        } else {
            errorNums++;
        }
        $(this).attr('class', 'no-confirm');
        
        cancheck = false;
        console.log('gameStatus:' + gameStatus );
       
        
        var question_count = answers.length;
    	
        if(question_count >= 4) 
        {
        	var msg = '此部分答题完毕，共计有' + question_count +'题，你答对了' + rightNums +"道题" + "答错了" + errorNums +'道题，请选择其它题型再练习';
        	console.log(msg);
        	showAnswerResult();
        	$('table td.check').removeClass('check');
        } 
        else {
            setStarNums();
            setStar();
        }
            
    
    });
    
    function showAnswerResult()
    {
    	$('#questionNumber').html(question_count);
    	$('#correctNumber').html(rightNums);
    	$('#incorrectNumber').html(errorNums);
    	$('#gotest').text('再来一发');
    	$('#test-result').modal('show');
    }

    $('#gotest').click(function () {
        showTipStep(0, 0, '即将进入正式测试。', 3);
        setTimeout(function () {
        	 beginExam();
        }, 5000);
       
    });

    $('#grid-table tr td').click(function () {
        if (!cancheck) {
            return;
        }

        if ($(this).hasClass('check')) {
            $(this).removeClass('check');
            $('#confirm').attr('class', 'no-confirm');
            return;
        }

        if ($('#grid-table tr td.check').length >= starNums) {
            return;
        }

        $(this).addClass('check');
        if ($('#grid-table tr td.check').length == starNums) {
            $('#confirm').attr('class', 'yes-confirm');
        }

    });
    
    $("#gotip").click(function () {showTips()});

});
