<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理</title>

   <jsp:include page="./_include_css.jsp"></jsp:include>

</head>

<body ng-app="zaApp">

    <div id="wrapper" >
    
        <jsp:include page="./navigator.jsp"></jsp:include>
        <div id="page-wrapper" 
        ng-controller="adminShareListController"  ng-init="initial()">
        <!-- Page Content -->
        <!-- 这条线以上不要加页面代码 -->
        <!-- --------------------------------------------------------------- -->
        
        <br/>
        
        <div class="">
                        <div class="panel-heading">
                            用户分享的列表
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <!-- <th>注册编号</th> -->
                                            <th>头像</th>
                                            <th>openId</th>
                                            <th>封面</th>
                                            <th>书名</th>
                                            <th>出版社</th>
                                            <th>编号</th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="bookX in pageData.books" 
                                        ng-click="gotoBookShareDetail(bookX.id)">
                                            <td ng-bind="bookX.id" style="vertical-align: middle;"></td>
                                           <!--  <td ng-bind="userX.userInfoId" style="vertical-align: middle;"></td> -->
                                            <td  style="vertical-align: middle;">
                                            <div class="img_frame" style="background-image:url({{bookX.uploaderHeadImgUrl}});border:1px dotted #858585;height:4em;width:4em;"></div>
                                            </td>
                                            <td ng-bind="bookX.uploaderWxOpenId" style="vertical-align: middle;"></td>
                                            <td  style="vertical-align: middle;">
                                            <div class="img_frame" style="background-image:url({{bookX.coverImgUrl}});border:1px dotted #858585;height:4em;width:4em;"></div>
                                            </td>
                                            <td ng-bind="bookX.bookName" style="vertical-align: middle;"></td>
                                            <td ng-bind="bookX.publisherName" style="vertical-align: middle;"></td>
                                            <td ng-bind="bookX.bookCode" style="vertical-align: middle;"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                            <ul class="tableIndex" style="float:right;">
                            <li class="tableIndex Previous"
                            ng-click="showPrePage()">
                            <div ng-class="{true:'disabled'}[pageIndex==0]">Previous</div></li>
                            
                            <li class="tableIndex" 
                            ng-repeat="pageIndexX in pageIndexs"
                            ng-click="showPage($index)">
                            <div 
                            ng-class="{true:'active disabled'}[pageIndex==$index]">{{pageIndexX}}</div></li>
                         	
                         	<li class="tableIndex Next"
                         	ng-click="showNextPage()">
                         	<div ng-class="{true:'disabled'}[pageIndex==pageNumber-1]">Next</div></li>
                         	</ul>
                        </div>
                        
                        
                        <!-- /.panel-body -->
                    </div>
  
  
  <!-- --------------------------------------------------------------- -->
     
     <!-- 这条线以下不要加页面代码 -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

  <jsp:include page="./_include_js.jsp"></jsp:include>
  <script src="../sp/ajController/yuexiang/admin/adminShareListController.js"></script>

</body>

</html>
