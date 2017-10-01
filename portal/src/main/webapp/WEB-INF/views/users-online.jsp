<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="include/before_html.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        
        <title>培训中心</title>
         <%@include file="include/head_files.jsp" %>
         
        <style>
            .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {
                padding: 8px 0;
                line-height: 1.42857143;
                vertical-align: middle;
                border-top: 1px solid #ddd;
            }

            a.join-practice-btn {
                margin-top: 0;
            }
        </style>

    </head>

    <body>
         <%@include file="include/body_header.jsp" %>
        <!-- Navigation bar starts -->

        <%@include file="include/commen_naviBar.jsp" %>
        <!-- Navigation bar ends -->

        <!-- Slider starts -->
        <div class="content" style="margin-bottom: 100px;">
            <div class="container" style="margin-top: 40px;">
                <div class="row">
                        <div class="col-xs-12">
                            <div style="border-bottom: 1px solid #ddd;">
                            <h3 class="title"><i class="fa fa-book"></i>共${lineCount}人在前端页面 }</h3>
                            
                            </div>
                            <div>
							    <table class="table-striped table">
                                 <thead>
                                    <tr>
                                    
                                        <td>SessionId</td>
                                        <td>用户名</td>

                                    </tr>
                                </thead> 
                                <tbody>
                                    <c:forEach items="${users }" var="item">
                                        <tr>
                                           
                                            <td>
                                                ${item.key }
                                            
                                            </td>
                                           
                                            <td>
                                                ${item.value }
                                            
                                            </td>

                                        </tr>
                                    </c:forEach>

                                </tbody>
                                <tfoot></tfoot>
                            </table>
							</div>
					     </div>
				</div>
			</div>
		</div>
		
		 <%@include file="include/footer_cm_js.jsp" %>
    
</body>
</html>