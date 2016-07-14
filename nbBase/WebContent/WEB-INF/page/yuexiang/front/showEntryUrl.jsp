<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>活动列表</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/showEntryUrl.css" media="all">   
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/showEntryUrlController.js" ></script>
</head>

<body ng-app="zaApp" 
	  ng-controller="showEntryUrlController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true">
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->
<div ng-repeat="urlx in pageData.urls">
	<div class="weui_cells_title"
	>{{urlx.name}}: &nbsp&nbsp&nbsp<a href="{{urlx.link}}" style="text-decoration: underline;">跳 转</a></div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea" 
	            placeholder="" 
	            rows="3"
	            readonly="readonly"
	            ng-model="urlx.link"
	            >jaksdlfkjalskdfj</textarea>
	        </div>
	    </div>
	</div>
</div>	









<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
<jsp:include page="_include_components.jsp"></jsp:include>
</body>


<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->


</html>

