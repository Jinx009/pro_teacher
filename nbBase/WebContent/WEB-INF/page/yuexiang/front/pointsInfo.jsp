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
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/eventList.css" media="all">   
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/pointsInfoController.js" ></script>
</head>

<body ng-app="zaApp" 
	  ng-controller="pointsInfoController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true">
<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->

<div style="padding:10%;text-align: center;color: #07a09b;font-weight: bolder;line-height: 3em;font-size: 1.5em;">
您已经获得了<font style="color:red;" ng-bind="pageData.points"></font>积分。<br/>
<!-- <div style="
    line-height: 1.3em;
">
阅享商城积分兑换系统<br>
正在紧锣密鼓搭建中，<br>
</div>
敬请期待！
</div>

<div style="
    text-align: center;
">
<img src="./sp/img/yuexiang/under-construction.png" style="
    width: 70%;
">
</div> -->








<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
<jsp:include page="_include_components.jsp"></jsp:include>
</body>


<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->


</html>

