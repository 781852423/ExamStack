<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
        <div class="container">
            <div class="row">
                <div class="col-xs-5" >
                    <div class="logo"> 
                          <h1>   
                            <a href="#"><img alt="职梦靠岸-专注银行、电信、烟草、铁路局招聘笔试面试辅导" src="resources/images/logo.png" class="img-responsive"></a>
                         </h1>  
                           
                    </div>
                   
                   
                </div>
                
                
                <div class="col-xs-7" id="login-info">
                  <a class="btn  btn-danger" href="https://shop106542288.taobao.com/" target="blank">点击进入本站淘宝店铺</a>
                    <c:choose>
                        <c:when
                            test="${not empty sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
                            <div id="login-info-user">

                                <a
                                    href="user-detail/${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}"
                                    id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
                                <span>|</span> <a href="logout"><i
                                    class="fa fa-sign-out"></i> 退出</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary" href="user-register">用户注册</a>
                            <a class="btn btn-success" href="user-login-page">登录</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </header>