<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html lang="zh-cn"> <!-- 标明是中文 -->
    <head>
        
        <title>职梦靠岸官网-银行招聘_求职辅导培训_烟草公司招聘笔试面试_真题分享_模拟训练_笔试面试经验分享</title>

        
       <%@include file="include/head_files.jsp" %>
       
      
        <style type="text/css">
            .form-group {
                margin-bottom: 5px;
                height: 59px;
            }
            .form-horizontal .control-label{
                padding-top: 0px;
            }
        </style>
    </head>
    <body>
      <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

        <div class="content" style="margin-bottom: 100px;">

            <div class="container">
                <div class="row">

                    <div class="col-md-12">
                        <div class="lrform">
                            <h5>登陆 </h5>
                            <div class="form">
                                
                                <form class="form-horizontal" id= "form-login-account" action="j_spring_security_check" method="post">
                                    <!-- Username -->
                                    <div class="form-group">
                                        <label class="control-label col-md-3" for="username">用户名</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control" name="j_username" id="j_username">
                                        </div>
                                    </div>
                                    <!-- Password -->
                                    <div class="form-group">
                                        <label class="control-label col-md-3" for="password">密码</label>
                                        <div class="col-md-9">
                                            <input type="password" class="form-control" name="j_password" id="j_password">
                                    
                                        </div>  
                                        </div>
                                    </div>
                                     <div>  

                                    <!-- Buttons -->
                                    <div class="form-group">
                                        <!-- Buttons -->
                                        <div class="col-md-9 col-md-offset-3">
                                            <button type="submit" class="btn btn-default">
                                                                                                                                 登陆
                                            </button>
                                            <button type="reset" class="btn btn-default">
                                                                                                                                取消
                                            </button>
                                            <span class="form-message">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                                        </div>
                                    </div>
                                </form>
                                没有账号? <a href="user-register">注册</a>
                            </div>
                        </div>

                    </div>
                </div>

            </div>

        </div>
        
         <%@include file="include/footer_cm_js.jsp" %>
        
    </body>
</html>