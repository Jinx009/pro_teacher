<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>API测试</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/shareList.css" media="all">  
	
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/apiTestController.js" ></script> 
</head>

<body ng-app="zaApp" 
	  ng-controller="apiTestController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true">
	
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->

<input ng-model="pageData.functionName">
<table>
<tr ng-repeat="paramX in pageData.params">
	<td><input ng-model="paramX.name"></td>
	<td><input ng-model="paramX.content"></td>
</tr>

</table>

<button ng-click="doTest()"
>提交</button>


----------------------------------------

<div ng-bind="pageData.ret">
</div>

<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>

<jsp:include page="_include_components.jsp"></jsp:include>
</body>

<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->

    

</html>

