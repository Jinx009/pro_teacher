<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>活动详情</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">  
<link rel="stylesheet" type="text/css" href="./sp/css/front/eventDetail.css" media="all">

<style type="text/css">

.actingSheetTopMenuItem{
    background: #0aba07;
    border: 0.4em solid #f0f0f0;
    color: #f0f0f0;
}

.weui_navbar_additional_show{
	position: fixed;
	width: 100%;
	z-index:2;
	display:;
}

.weui_navbar_additional_hide{
	position: fixed;
	width: 100%;
	z-index:2;
	display:none;
}

.menu_item_top{
	border-top: 1px solid #0aba07;
}
</style>
</head>

<body ng-app="zaApp" ng-controller="eventDetailController" ng-init="initial()">



<div id="actionSheet_wrap">
        <div ng-class="{false:'weui_mask_transition', true:'weui_mask_transition weui_mask_additional'}[isPopupMenu]" 
        id="weui_mask" ng-click="togglePopupMenu()">
        </div>
        
        <div dclass="weui_navbar weui_navbar_additional_show" ng-class="{false:'weui_navbar weui_navbar_additional_hide', true:'weui_navbar weui_navbar_additional_show'}[isPopupMenu]">
                <div class="weui_navbar_item actingSheetTopMenuItem"
                ng-click="togglePopupMenu();goToEventStatus(eventId)">
                    看看谁捧了
                </div>
                <div class="weui_navbar_item actingSheetTopMenuItem"
                ng-click="togglePopupMenu();goToEventBBS(eventId)">
                    项目吐槽墙
                </div>
            </div>
        
         <!-- <div class="weui_actionsheet weui_actionsheet_toggle" style="background-color:#09BB07">
         <div class="weui_actionsheet_action">
                <div class="weui_actionsheet_cell" 
                id="actionsheet_cancel" ng-click="togglePopupMenu();goToEventStatus(eventId)">查看活动参与者详情</div>
            </div>
        </div> -->
        
        <div ng-class="{false:'weui_actionsheet', true:'weui_actionsheet weui_actionsheet_toggle'}[isPopupMenu]" id="weui_actionsheet">
            <div class="weui_actionsheet_menu">
            	<div ng-class="{false:'weui_actionsheet_cell menu_item', true:'weui_actionsheet_cell menu_item menu_item_top'}[$index==0]" 
            	ng-repeat="rulex in rules" 
            	ng-click="togglePopupMenu();gotoPayDetail(rulex.ruleId, rulex.unitPrice, rulex.ruleDesc)" 
            	>
            		<div style="display:inline-block;margin-right:1em;"><img src="./sp/img/moneyicon.png"></div>
            		<div style="display:inline-block;">{{rulex.ruleDesc}}</div>
            	</div>
            </div>
            
            <div class="weui_actionsheet_action" style="margin-top:0px; border-top:5px solid #f0f0f0;">
                <div class="weui_actionsheet_cell" id="actionsheet_cancel" ng-click="togglePopupMenu()"
                >取消</div>
            </div>
            
            
        </div>
    </div>

<div class="peng_button" ng-click="togglePopupMenu()">

	<p class="peng_button_font">捧</p>

</div>

<div class="content" id="content" ng-bind-html="contentHtml" style="margin-bottom:5em;"></div>


<!-- loading的动画效果 -->
<div id="loadingToast" ng-class="{true: 'weui_loading_toast class_show' , false : 'weui_loading_toast class_hide'}[isLoadingShow]" >
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <div class="weui_loading">
                <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                <div class="weui_loading_leaf weui_loading_leaf_11"></div>
            </div>
            <p class="weui_toast_content">正在装载图文信息</p>
        </div>
    </div>

<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

<div class="sample_div" ng-show="isSample">
</div>
</body>

	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/eventDetailController.js" ></script>
    

</html>