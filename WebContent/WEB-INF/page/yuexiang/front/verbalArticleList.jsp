<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>有声读物</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/verbalBookList.css" media="all">   
</head>
<style type="text/css">
.weui_media_box:before{
	border-top: 1px solid #07a09b;
	left:0px;
}
</style>
<body ng-app="zaApp" 
	  ng-controller="verbalArticleListController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">
<div class="page slideIn" ng-show="true">
<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>

<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->

<div style="position: absolute; width:100%;"
ng-show="toggle.isShowingMainContent">
	<div 
	ng-controller="mainMenuController" 
	ng-init="initial()" >
	<jsp:include page="main_menu.jsp"></jsp:include>
	</div>


<div style="margin-top: 0em;">
	<a href="javascript:void(0);" class="weui_media_box weui_media_appmsg" 
		ng-repeat="bookX in pageData.verbalArticles"
		ng-click="gotoBookDetail(bookX.verbalUrl)">
		
		<div class="weui_media_hd img_frame" style="background-image : url({{bookX.imgUrl}});"></div>
		<div class="weui_media_bd">
			<h4 class="weui_media_title" style="color: #07a09b;" ng-bind="'《'+bookX.title+'》'"></h4>
			<div class="weui_media_desc" style="color: #07a09b;" ng-bind="bookX.introduce"></div>
			<!-- <div class="weui_media_desc" style="margin-top: 0.5em;">
				<ul>
				<li class="tag_div" ng-repeat="tagX in bookX.tags track by $index" 
					ng-bind="tagX" style="font-size: 0.9em;background-color: #07a09b;color: white;">  </li>
				</ul>
			</div> -->
		</div>
	</a>
</div>

<br/>
<%-- <div class="weui_cells_title" style="text-align: center;" 
			ng-click="loadMoreBooks()" 
			ng-show="pageData.verbalBooks.length < pageData.totalNumber">
			加载更多内容
		</div> --%>



</div>
<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>


<jsp:include page="_include_components.jsp"></jsp:include>
</body>
<jsp:include page="_include_js.jsp"></jsp:include>

<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->

<script type="text/javascript" src="./sp/ajController/yuexiang/front/verbalArticleListController.js" ></script>
<script type="text/javascript" src="./sp/ajController/yuexiang/front/mainMenuController.js" ></script>
    

</html>

