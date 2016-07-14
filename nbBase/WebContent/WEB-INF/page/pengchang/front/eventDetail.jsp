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
<link rel="stylesheet" type="text/css" href="./sp/css/front/example.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">  
<link rel="stylesheet" type="text/css" href="./sp/css/front/eventDetail.css" media="all">

<style type="text/css">

.weui_uploader_file{
	width:3.5em;
	height:3.5em;
}
.weui_uploader_input_wrp{
	width:3.5em;
	height:3.5em;	
}

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

.bd{
	width:95%;
	margin-right:auto;
	margin-left:auto;
}
</style>
</head>

<body ng-app="zaApp" ng-controller="eventDetailController" ng-init="initial()" style="color: #5f3f3f;">

<div class="page slideIn" ng-show="isBodyReady">

<div id="actionSheet_wrap">
        <div ng-class="{false:'weui_mask_transition', true:'weui_mask_transition weui_mask_additional'}[isPopupMenu]" 
        id="weui_mask" ng-click="togglePopupMenu()">
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
            	ng-repeat="rulex in pageData.rules" 
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

<!-- <div class="content" id="content" ng-bind-html="contentHtml" style="margin-bottom:5em;"></div> -->
<div class="content" id="content">

<div class="page slideIn article" style="background:#f0f0f0">
    <div class="hd">
        <h1 class="page_title" ng-bind="pageData.eventTitle"></h1>
    </div>
    
     <div class="bd" ng-show="pageData.eventStatus == 2 && pageData.eventType == 2 && pageData.eventSubType == 1">
     
    	<div class="weui_cells_title"></div>
    	<div class="weui_cells weui_cells_access">
        
        	<a class="weui_cell" href="javascript:;" ng-click="gotoMatchResult()">
            <div class="weui_cell_bd weui_cell_primary" style="text-align:center;">
                <p style="color:red">比赛结果</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
		</div>
    </div>
    
    <div class="bd" ng-show="pageData.eventStatus == 2 && pageData.eventType == 2 && pageData.eventSubType != 1">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="display:block;text-align:center; color: #039B02;">
        	活动成功
    	</div>
    </div>
    </div>
    
    <div class="bd" ng-show="pageData.eventStatus == 1 && pageData.eventType == 2 ">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="display:block;text-align:center; color: #039B02;">
        	报名者召集中
    	</div>
    </div>
    </div>
    
    <div class="bd" ng-show="(pageData.eventStatus == 3 || pageData.eventStatus == 4) && pageData.eventType == 2">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="display:block;text-align:center; color: #999999;">
        	活动取消
    	</div>
    </div>
    
    </div>
    
    
    <div class="bd">
    
    	<div class="weui_cells_title">活动发起人</div>
    	<div class="weui_cells">
        <div class="weui_cell">
        	<div style="margin-right: 2em;">
            <img class="weui_media_appmsg_thumb" style="width:3em;" ng-src="{{pageData.callerHeadImgUrl}}" alt=""/>
            </div>
            <div ng-bind="pageData.callerNickname">
             
            </div> 
    	</div>
    </div>
    </div>
    
    
    
     <div class="bd">
     
    	<div class="weui_cells_title">活动时间及地点</div>
    	<div class="weui_cells">
        <div class="weui_cell" style="display:block">
        	 <div style="padding-bottom: 0.4em;border-bottom: 1px solid #cfcfcf;" ng-bind="pageData.eventAddress">
        	 
        	 </div>
        	 <div style="padding-top: 0.4em;" ng-bind="pageData.eventTime">
        	 
        	 </div>
    	</div>
    </div>
    </div>
    
    
    
    <div class="bd">
     
    	<div class="weui_cells_title">已报名人数</div>
    	<div class="weui_cells weui_cells_access">
        
        	<a class="weui_cell" href="javascript:;" ng-click="goToEventStatus()">
            <div class="weui_cell_bd weui_cell_primary">
                <p>有 {{pageData.eventPartiNumber}} 人报名 募集金额 {{pageData.eventAmount}}元</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
		</div>
    </div>
    
     <div class="bd">
     <div class="weui_cells_title">活动详情</div>
    	<article class="weui_article" style="background: white;">
                <div ng-bind-html="pageData.eventIntroduce" style="word-wrap: break-word; word-break: break-all;">
                </div>
                     
                    <div style="border-top: 1px dotted black;
                                margin-top: 1em;
                                padding-top: 0.5em;"
                                >
                                <div>
                                <p style="color:red">
                                报名截止时间 ： </p><p ng-bind="pageData.registerDeadline"></p>
                                </div>
                                
                                </div>
                
        </article>
    </div>
    
     <div class="bd" >
     <div class="weui_cells_title">活动图片</div>
     <div class="weui_cells">
        <div class="weui_cell" style="display:block;text-align:center;">
        <div>
    	<ul class="weui_uploader_files" ng-show="pageData.eventImages.length > 0">
    		<li class="weui_uploader_file" style="background-image:url({{eventImageX.thum}});border:1px dotted #858585;" 
    		ng-repeat="eventImageX in pageData.eventImages"
    		ng-click="wxViewPhotos($index)"></li>
    	</ul>
    	<div class="weui_uploader_input_wrp" ng-show="pageData.isUserPlayer">
                            <div class="weui_uploader_input" ng-click="chooseNewPic()">
                            </div>
                        </div>
		</div>
    	</div>
    	</div>
    </div>
    <div class="bd" ng-show="true">
     
    	<div class="weui_cells_title">评论</div>
    	<div class="weui_cells weui_cells_access">
        
        	<a class="weui_cell" href="javascript:;" ng-repeat="commentX in pageData.popComments" ng-click="goToEventBBS()">
	            <div class="weui_cell_bd weui_cell_primary">
	                <p ng-bind="commentX.text"></p>
	            </div>
	            <div class="weui_cell_ft">
	            </div>
        	</a>
        	<a class="weui_cell" href="javascript:;" ng-click="goToEventBBS()">
	            <div class="weui_cell_bd weui_cell_primary">
	                <p
	                style="
	                color:#52b654;
	                " 
	                ng-bind="pageData.popComments.length==0?'快去抢沙发':'查看全部留言板'"></p>
	            </div>
	            <div class="weui_cell_ft">
	            </div>
        	</a>
		</div>
    </div>
    
    <br/>
<br/>
<br/>
<br/>
<br/>
    </div>
    
</div>

</div>


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