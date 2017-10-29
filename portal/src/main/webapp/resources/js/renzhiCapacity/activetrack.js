$(document).ready(function() {
    var canvas;
    var context;
    var width;
    var height;
    var balls = [];
    var isMove = false;
    var themeColor = '#30302F';
    var addCountPer = 10; // 每几个增加一个球。
    var isEnd = false;
    var ballNums = 10;
    var flashNums = 3;
    var sh;
    var gameStatus = '';
    var rightNum = 0;
    var totalNum = 0;
    var moveSecs = 6;
    var radius = 20; // 小球半径
    var limit_secs = 0;
    var limit_m = 0;
    var limit_s = 0;
    var totalQuestionNumber = 3;
    
    initBalls();

    drawBall(1);
    startNewGame( false );
	 
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
        		  clearInterval(settime);
        		}
          
            endAnswer();
        }
    }
    
    function initParas()
    {
    	   
    	        balls = [];
    	        initBalls();
    	        isMove = false;
    	        themeColor = '#30302F';
    	        addCountPer = 10; // 每几个增加一个球。
    	        isEnd = false;
    	        ballNums = 10;
    	        flashNums = 3;
    	        sh = '';
    	        radius = 20; // 小球半径
    	        gameStatus = '';
    	        rightNum = 0;
    	        totalNum = 0;
    	        moveSecs = 6;
    	        limit_secs = totalQuestionNumber*10; // 每道题给10秒钟时间
    	        limit_m = parseInt(limit_secs / 60);
    	        limit_s = parseInt(limit_secs % 60);
    }

    function showAnswerResult()
    {
    	 
    	$('#questionNumber').html(totalNum);
    	$('#correctNumber').html(rightNum);
    	$('#incorrectNumber').html(totalNum-rightNum);
    	$('#gotest').text('再来一发');
    	$('#test-result').modal('show');
    
    }
    
    function beginExam()
    {
    	console.log('beginExam---');
    	 initParas();
    	 gameStatus = 'go';
         var settime = setInterval(function () {
            showtime();
        }, 1000);
        startNewGame(true);
        
    }
	 // 结束答题
    function endAnswer() {
    	showTipStep(0, 0, '不好意思时间到！请看答题结果', 4);
    	showAnswerResult();
    }

    // 答题导航
    function showTips()
    {
    	
    	        showTipStep(7, 1, '屏幕上会呈现10个圆球', 5);

    	        setTimeout(function () {
    	            showTipStep(7, 2, '其中有3至5个圆球将闪烁。闪烁的圆球即为需要<br />追踪的目标，请记住它们。<br />追踪目标闪烁后，10个圆球都会开始运动。', 9);
    	            setTimeout(function(){
    	                flashThreeBall(1);
    	                setTimeout(function () {
    	                    flashThreeBall(2);
    	                }, 1500);

    	                sh = setInterval(
    	                    function () {
    	                        if (isMove) {
    	                            drawBall(0);
    	                            updateBall();
    	                        }
    	                    }, 100
    	                )
    	            }, 2000);
    	        }, 5000);

    	        setTimeout(function () {
    	            showTipStep(7, 3, '当圆球运动停止后它们会变成正方形。<br />请用鼠标点选出之前闪烁的追踪目标<br />进行作答。', 6);
    	        }, 8000+moveSecs*1000);

    	        setTimeout(function () {
    	            showTipStep(7, 4, '鼠标点击一次即为选中，<br />被选中的目标显示橙色边框；<br />再点击一次则取消选择', 8);
    	            setTimeout(function () { setBallCheck(0, 1); }, 2000);
    	            setTimeout(function () { setBallCheck(1, 1); }, 3500);
    	            setTimeout(function () { setBallCheck(2, 1); }, 5000);
    	            setTimeout(function () {
    	                setBallCheck(2, 0);
    	                showTipStep(7, 5, '选择的追踪目标的个数必须<span style="color: red;">等于</span>之前闪烁的<br />圆球数量。', 5);
    	                setTimeout(function () { setBallCheck(2, 1); }, 3000);
    	            }, 8000);

    	        }, 20000);

    	        setTimeout(function () {
    	            showTipStep(7, 6, '完成作答后单击“确认选择”按钮。之后<br />进入下一个任务。一旦进入下一个任务，<br />将<span style="color: red;">无法返回</span>进行修改', 6);
    	        }, 33000);

    	        setTimeout(function () {
    	        	  showTipStep(6, 6, '请用尽可能快的速度完成作答。<br /><span style="color: red;">你将进入练习环节，熟悉作答方式。</span><br />完成练习后，点击<span style="color: red;">开始测试</span>按钮进入正式测试。', 6);
    	              setTimeout(function () {
    	                  
    	              }, 6000);
    	          }, 30000);
    	  	}


    	    function showTipStep(tipTotal, tipCount, tipContent, timeout)
    	    {
    	        var paginationHtml = '';
    	        for(var i=0; i<tipTotal; i++){
    	            if(i<tipCount){
    	                paginationHtml += '<span class="pagination-bullet pagination-bullet-active"></span>';
    	            }else{
    	                paginationHtml += '<span class="pagination-bullet"></span>';
    	            }
    	        }

    	        ds.dialog({
    	            title : '答题说明',
    	            content : '<div style="text-align: center;">'+tipContent+'<br/>'+paginationHtml+'</div>',
    	            timeout:timeout,
    	            showCloseButton:false
    	        });
    	    }
    
   

    function setBallCheck(flag, check){
        balls[flag].check = check;
        context.globalCompositeOperation = "source-over";
        context.beginPath();
        if (check) {
            context.strokeStyle = '#EA9618';
        } else {
            context.strokeStyle = balls[flag].color;
        }
        context.lineWidth = 4;
        context.strokeRect(balls[flag].x - 20, balls[flag].y - 20, balls[flag].radius * 2, balls[flag].radius * 2);
        context.closePath();
        checkConfirmBtn();
    }

    function checkConfirmBtn()
    {
        var count = 0;
        for(var i=0; i<balls.length; i++){
            if(balls[i].check == 1){
                count ++;
            }
        }
        if(count == flashNums){
            $('#confirm').attr('class', 'yes-confirm');
            return true;
        }else{
            $('#confirm').attr('class', 'no-confirm');
            return false;
        }
    }

    // 10个小球初始化
    function initBalls()
    {
    	canvas = document.getElementById("canvas");
        context = canvas.getContext("2d");
        canvas.width = 800;
        canvas.height = 500;
        width = canvas.width;
        height = canvas.height;
        context.globalAlpha = 1;
        for (var i = 0; i < ballNums; i++) 
        {
           
            var ball = {
                x: Math.random() * (width - 2 * radius) + radius,
                y: Math.random() * (height - 2 * radius) + radius,
                vx: Math.pow(-1, Math.ceil(Math.random() * 2)) * Math.random() * 8 + 2,
                vy: Math.pow(-1, Math.ceil(Math.random() * 2)) * Math.random() * 4 + 2,
                radius: radius,
                check: 0, // 0-没有选中，1-已选中
                color: "rgb(255,255,255)"
            }
            balls[i] = ball;
        }
    }
    
   // 开始就传入ytrue，否则传入false
    function startNewGame(refreshFlag)
    {
    	console.log('startNewGame:' + refreshFlag);
        if( refreshFlag ){
            context.clearRect(0, 0, width, height);
            context.fillStyle = themeColor;
            context.fillRect(0, 0, width, height);
            for(var i=0; i<balls.length; i++){
                balls[i].x = Math.random() * (width - 2 * radius) + radius;
                balls[i].y = Math.random() * (height - 2 * radius) + radius;
            }
            drawBall(0);
        }

        for(var i=0; i<balls.length; i++){
            balls[i].check = 0;
        }
        sh = setInterval(
            function () {
                if (isMove) {
                    drawBall(0);
                    updateBall();
                }
            }, 50
        )
        flashThreeBall(1);
        setTimeout(function () {
            flashThreeBall(2);
        }, 1200)
    }
    
   

    function drawBall(first_flag) {
        context.clearRect(0, 0, width, height);
        context.fillStyle = themeColor;
        context.fillRect(0, 0, width, height);

        for (var i = 0; i < balls.length; i++) {
            context.globalCompositeOperation = "lighter";
            context.beginPath();
            context.arc(balls[i].x, balls[i].y, balls[i].radius, 0, Math.PI * 2, true);
            context.closePath();
            context.fillStyle = balls[i].color;
            context.fill();
        }
        if (first_flag == 1) {
            canvas.addEventListener('click', function (e) {
                if (!isEnd) {
                    return;
                }
                var flag = isInPath(e.offsetX, e.offsetY);
                if (flag > -1) {
                    if (!balls[flag].check) {
                        balls[flag].check = 1;
                    } else {
                        balls[flag].check = 0;
                    }
                    context.globalCompositeOperation = "source-over";
                    context.beginPath();
                    if (balls[flag].check == 1) {
                        context.strokeStyle = '#EA9618';
                    } else {
                        context.strokeStyle = balls[flag].color;
                    }
                    context.lineWidth = 4;
                    context.strokeRect(balls[flag].x - 20, balls[flag].y - 20, balls[flag].radius * 2, balls[flag].radius * 2);
                    context.closePath();
                    if( checkConfirmBtn()){
                        $('#gotest').attr('class', 'yes-confirm');
                    }
                    if (balls[flag].check) {
                        if (flag < 3) {
                            //alert('选择正确');
                        } else {
                            //alert('选择错误');
                        }
                    }
                }
            })
        }
    }

    // 闪烁三个小球
    function flashThreeBall(count) {
        var gco = 'source-over';
        setTimeout(function () {
            for (var i = 0; i < flashNums; i++) {
                context.globalCompositeOperation = gco;
                context.beginPath();
                context.arc(balls[i].x, balls[i].y, balls[i].radius, 0, Math.PI * 2, true);
                context.closePath();
                context.fillStyle = '#7E7E7D';
                context.fill();
            }
        }, 100);
        setTimeout(function () {
            for (var i = 0; i < flashNums; i++) {
                context.globalCompositeOperation = gco;
                context.beginPath();
                context.arc(balls[i].x, balls[i].y, balls[i].radius, 0, Math.PI * 2, true);
                context.closePath();
                context.fillStyle = themeColor;
                context.fill();
            }
        }, 400);

        setTimeout(function () {
            for (var i = 0; i < flashNums; i++) {
                context.globalCompositeOperation = gco;
                context.beginPath();
                context.arc(balls[i].x, balls[i].y, balls[i].radius, 0, Math.PI * 2, true);
                context.closePath();
                context.fillStyle = balls[i].color;
                context.fill();
            }
        }, 700);
        if (count == 2) {
            setTimeout(function () {
                isMove = true;
                // 定时关闭
                setTimeout(function () {
                    clearInterval(sh);
                    drawRect();
                    isMove = false;
                    isEnd = true;
                }, moveSecs*1000);
            }, 1200);
        }
    }

    function drawRect() {
        context.clearRect(0, 0, width, height);
        context.fillStyle = themeColor;
        context.fillRect(0, 0, width, height);

        for (var i = 0; i < balls.length; i++) {
            context.globalCompositeOperation = "lighter";
            context.beginPath();
            context.fillStyle = balls[i].color;
            context.fillRect(balls[i].x - 20, balls[i].y - 20, balls[i].radius * 2, balls[i].radius * 2);
            //context.arc(balls[i].x,balls[i].y,balls[i].radius,0,Math.PI*2,true);
            //context.fillRect();
            context.closePath();
            context.fill();
        }
    }

    function updateBall() {
        for (var i = 0; i < balls.length; i++) {
            var aBall = balls[i];
            aBall.x += aBall.vx;
            aBall.y += aBall.vy;
            if (aBall.x < aBall.radius || aBall.x > width - aBall.radius) {
                aBall.vx = -aBall.vx;
            }
            if (aBall.y < aBall.radius || aBall.y > height - aBall.radius) {
                aBall.vy = -aBall.vy;
            }
        }
    }

    function isInPath(x, y) {
        for (var i = 0; i < balls.length; i++) {
            context.globalCompositeOperation = "destination-out";
            context.beginPath();
            context.rect(balls[i].x - 20, balls[i].y - 20, balls[i].radius * 2, balls[i].radius * 2);
            var tmp = context.isPointInPath(x, y);
            //context.clearR
            context.closePath();
            if (tmp) {
                return i;
            }
        }
        return -1;
    }

    $('#confirm').click(function(){
        if( $(this).hasClass('no-confirm')){
            return;
        }
        var flag = true;
        for(var i=0; i<flashNums; i++){
            if(balls[i].check != 1){
                flag = false;
                break;
            }
        }
        if(flag){
            rightNum ++;
        }
        totalNum ++;
        $(this).attr('class','no-confirm');
        $('table td.check').removeClass('check');
        
        // 检查是否结束，如果结束，就提醒
        if(totalNum > totalQuestionNumber)
        	{
        	var msg = '此部分答题完毕，共计有' + totalNum +'题，你答对了' + rightNum +"道题" + "答错了" + (totalNum-rightNum) +'道题，请选择其它题型再练习';
        	console.log(msg);
        	showAnswerResult();
        	$('table td.check').removeClass('check');
        	}else {
                flashNums = parseInt(totalNum/addCountPer) + 3;
                startNewGame(true);
			}

    });

    $('#gotest').click(function(){
        if(!$(this).hasClass('yes-confirm')){
            return;
        }
        showTipStep(0, 0, '即将进入正式测试。', 3);
        
        setTimeout(function () {
       	 beginExam();
       }, 5000);
    });
    
    $("#gotip").click(function () {showTips()});

});