<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>比赛结果</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/example.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">  
<link rel="stylesheet" type="text/css" href="./sp/css/front/eventDetail.css" media="all">

<style type="text/css">

.weui_uploader_file{
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
.matchLineLeft{
	width: 12vw;
    height: 1em;
    display: block;
    margin-left: 3em;
    border-bottom: 2px solid #026501;
    border-left: 2px solid #026501;
    margin-top: 3px;

}
.matchLineMiddleLeft{
    width: 14%;
    height: 2em;
    left: 35%;
    right: 51%;
    position: absolute;
    top: 2px;
    bottom: 0em;
    border: 2px solid #026501;
    text-align: center;
    color: #026501;
    font-weight: bolder;
    line-height: 2em;
     -moz-border-radius: 2em;
    -webkit-border-radius: 2em;
    border-radius: 2em;
}
.matchLineMiddleRight{
    width: 14%;
    height: 2em;
    left: 51%;
    right: 35%;
    position: absolute;
    top: 2px;
    bottom: 0em;
    border: 2px solid #026501;
    text-align: center;
    color: #026501;
    font-weight: bolder;
    line-height: 2em;
     -moz-border-radius: 2em;
    -webkit-border-radius: 2em;
    border-radius: 2em;
}
.matchLineRight{
    width: 12vw;
    height: 1em;
    display: block;
    float: right;
    border-top: 2px solid #026501;
    border-right: 2px solid #026501;
    margin-right: 3em;
}

.matchFrame{
	padding: 1em;
    border: 1px solid #026501;
    margin: 0.5em;
    margin-bottom: 2em;
    -moz-border-radius: 0.38em;
    -webkit-border-radius: 0.38em;
    border-radius: 0.38em;
}

.addMatchFrame{
    position: relative;
    display: block;
    margin: 0.5em;
}

.addOneMatch{
    margin: 0em;
    border: 2px solid #026501;
    -moz-border-radius: 0.8em;
    -webkit-border-radius: 0.8em;
    border-radius: 0.8em;
    width: auto;
    float: none;
    height: 3.5em;
}

.weui_cells{
	margin-top:0em;
}

.weui_cells:after{
border:none;
}

.weui_btn_plain_default{
	border: 1px solid #026501;
	color: #026501;
	margin-right: 0.5em;
	margin-left: 0.5em;
}
</style>
</head>

<body ng-app="zaApp" ng-controller="eventMatchResultConfirmController" ng-init="initial()" style="color: #5f3f3f;">



<!-- <div class="content" id="content" ng-bind-html="contentHtml" style="margin-bottom:5em;"></div> -->
<div class="content" id="content">

<div class="page slideIn article" style="background:#f0f0f0">

<!-- 一个比赛结果 -->
<div class="matchFrame" ng-repeat="matchX in pageData.matchResult">  
    <div class="bd">
    
    	<div class="weui_cells">
        <div class="weui_cell">
        	<div style="margin-right: 2em;">
            <img class="weui_media_appmsg_thumb" style="width:3em;" ng-src="{{matchX.playerA.headImgUrl}}" alt=""/>
            </div>
            <div ng-bind="matchX.playerA.nickname">
             
            </div> 
            
    	</div>
    </div>
    </div>
    <div style="position:relative; display:block;padding-bottom:4px;height:2.4em;" ng-show="matchX.result==1">
    <div class="matchLineLeft" style="border-bottom: 2px solid #aa0000;border-left: 2px solid #aa0000;"></div>
	<div class="matchLineMiddleLeft" style="border:2px solid #aa0000;color:#aa0000"> <p>胜</p></div>
	<div class="matchLineMiddleRight" style="border:2px solid #aaaaaa;color:#aaaaaa"> <p>负</p></div>
    <div class="matchLineRight" style="border-right: 2px solid #aaaaaa;border-top: 2px solid #aaaaaa;"></div>
</div>

 <div style="position:relative; display:block;padding-bottom:4px;height:2.4em;" ng-show="matchX.result==2">
    <div class="matchLineLeft"></div>
	<div class="matchLineMiddleLeft"> <p>平</p></div>
	<div class="matchLineMiddleRight"> <p>平</p></div>
    <div class="matchLineRight"></div>
</div>

 <div style="position:relative; display:block;padding-bottom:4px;height:2.4em;" ng-show="matchX.result==3">
    <div class="matchLineLeft" style="border-bottom: 2px solid #aaaaaa;border-left: 2px solid #aaaaaa;"></div>
	<div class="matchLineMiddleLeft" style="border:2px solid #aaaaaa;color:#aaaaaa"> <p>负</p></div>
	<div class="matchLineMiddleRight" style="border:2px solid #aa0000;color:#aa0000"> <p>胜</p></div>
    <div class="matchLineRight" style="border-right: 2px solid #aa0000;border-top: 2px solid #aa0000;"></div>
</div>
    
    <div class="bd" ng-show="matchX.isPlayerBConfirmed">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="float:right;">
        	
            <div style="word-break: break-all;">{{matchX.playerB.nickname}}
             
            </div> 
            
            <div style="margin-left: 2em;">
            <img class="weui_media_appmsg_thumb" style="width:3em;" ng-src="{{matchX.playerB.headImgUrl}}" alt=""/>
            </div>
        
    	</div>
    	</div>
    
    
    </div>
    
    <div class="bd" ng-show="!matchX.isPlayerBConfirmed">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="float:right;">
        	
            <div style="word-break: break-all;">{{matchX.playerB.nickname}}
             
            </div> 
            
            <div style="margin-left: 2em;">
            <img class="weui_media_appmsg_thumb" style="width:3em;" ng-src="{{matchX.playerB.headImgUrl}}" alt=""/>
            </div>
        
    	</div>
    	</div>
    
    
    </div>
    
        <div class="bd" ng-show="!matchX.isPlayerBConfirmed">
    
    	<div class="weui_cells">
        <div class="weui_cell" style="background:#f0f0f0">
        	
             <a href="javascript:;" class="weui_btn weui_btn_warn" style="
    width: 60%;
    line-height: 2em;
    margin-top: 0.3em;
    margin-bottom: 0.3em;
"
ng-click="confirmResult(matchX.id)"
>确认</a>
        
    	</div>
    	</div>
    
    
    </div>
    
    
    </div>
    
<!-- 一个比赛结果 结束 -->	

	<a href="javascript:;" class="weui_btn weui_btn_plain_default" style="border: 1px solid #652727; color:#652727;"
			 ng-click="gotoEventDetail()">返回</a>


<br/>
<br/>

</div>


<br/>
<br/>
<br/>
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
	<script type="text/javascript" src="./sp/ajController/pengchang/front/eventMatchResultConfirmController.js" ></script>
    

</html>