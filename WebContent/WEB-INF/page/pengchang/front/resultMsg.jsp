<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>呦吼页</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">

</head>

<body ng-app="zaApp" ng-controller="resultMsgController" ng-init="initial()">

<div class="page slideIn msg">
    <div class="weui_msg">
        <div class="weui_icon_area"><i ng-class="{false:'weui_icon_warn weui_icon_msg', true : 'weui_icon_success weui_icon_msg'}[isOK]"></i></div>
        <div class="weui_text_area">
            <h2 class="weui_msg_title">{{msgTitle}}</h2>
            <p class="weui_msg_desc">{{msgDesc}}</p>
        </div>
        <div class="weui_opr_area">
            <p class="weui_btn_area">
                <a href="{{url}}" ng-class="{false: 'weui_btn weui_btn_warn', true:'weui_btn weui_btn_primary'}[isOK]" style="margin-top:10vh">{{buttonText}}</a>
                <!-- <a href="javascript:;" class="weui_btn weui_btn_default">取消</a> -->
            </p>
        </div>
        <div class="weui_extra_area">
          <!--   <a href="">查看详情</a> -->
        </div>
    </div>
</div>

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/resultMsgController.js" ></script>
    
</html>