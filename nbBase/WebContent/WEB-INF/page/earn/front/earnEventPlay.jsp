<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<!-- saved from url=(0044)http://localhost:8080/nbBase/eventList2.html -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  
  <title>紧张进行中</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/weui.css" media="all">
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/example.css" media="all"> 
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/nb.css" media="all">   
	
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/earn/front/earnEventPlayController.js" ></script>
	
<style type="text/css">
.commnet_text_div{
	overflow: hidden;
    display: -webkit-box;
    text-overflow: ellipsis;
    overflow: hidden;
    -webkit-line-clamp: 5;
    -webkit-box-orient: vertical;
    color: #9f9f9f;
    margin-top: 0.4em;
}

.msg_list_bd_a{
	-moz-border-radius: 0.8em;      /* Gecko browsers */
    -webkit-border-radius: 0.8em;   /* Webkit browsers */
    border-radius:0.8em;            /* W3C syntax */
}
</style>
        		  
	</head>
	<body ng-app="zaApp" ng-controller="earnEventPlayController" ng-init="initial()">
	
	<div  ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isInitialProm&&!isBodyOK]" style="border:3px solid #0f0f0f;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
        <div class="weui_dialog_bd">开始一次新的挑战将刷新之前的挑战记录，确定开始新的挑战么？</div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog default" ng-click="gotoEarnEvents()">取消</a>
            <a href="javascript:;" class="weui_btn_dialog primary" ng-click="intialPlay()">确定</a>
        </div>
    </div>
	
	<div class="container" id="container" ng-show="isBodyOK">
	
	
	<div class="cell" ng-show="currentShow >=0 && currentShow < wordList.length">
		<div class="hd">
		    <h1 class="page_title" ng-bind="wordList[currentShow].word"></h1>
		    
		    <div class="weui_progress">
		        <div class="weui_progress_bar">
		            <div class="weui_progress_inner_bar js_progress" ng-style="progressAdditionalStyle"></div>
		        </div>
    		</div>
    		
		    <div style="
		    		position: absolute;
    				width: 100%;
    				text-align: right;"
    				>
    				<p style="	margin-right: 2em;
							    margin-top: 0.8em;
							    color: darkred;
							    font-size: 1.1em;"
							    
							    ng-bind="leftTimeString"></p></div>
		</div>
		
		<div class="bd">
			<div class="weui_cells weui_cells_checkbox" ng-repeat="explainx in wordList[currentShow].explainList">
        		<label class="weui_cell weui_check_label">
            		<div class="weui_cell_hd">
                		<input type="checkbox" class="weui_check" name="checkbox1" id="s11" ng-model="explainx.checked" ng-change="toggleChecked(currentShow,$index)">
                		<i class="weui_icon_checked"></i>
            		</div>
            		<div class="weui_cell_bd weui_cell_primary">
                		<p ng-bind="explainx.explain"></p>
            		</div>
        		</label>
			</div>
			<br/>
		</div>
	</div>
	
	
	
	<div class="cell" ng-show="currentShow<0">
		<div class="hd" style="background:#52b544">
		    <h1 class="page_title" style="color:#f0f0f0">
		    
		    <table>
		    <tr>
		    <td>正确：</td>
		    <td> {{correctNumber}} </td>
		    <td>题</td>
		    </tr>
		    <tr>
		    <td>红包：</td>
		    <td> {{theRedPack}} </td>
		    <td>元</td>
		    </tr>
		    </table>
		    </h1>
		</div>
		
	</div>
	
	
	
	<div class="bd" ng-show="currentShow<0">
			<br/>
			<a href="javascript:;" class="weui_btn weui_btn_plain_default" 
			style="border: 1px solid #731D1D;color: #731d1d; width:70%" ng-click="getAward()">领取红包</a>
	</div>
	
	
	
	
	<br/>
	<a href="javascript:;" class="weui_btn weui_btn_plain_default" ng-click="intialPlay()" style="width:70%;">重新来过</a>
	
	<br/>
	
			<a href="javascript:;" class="weui_btn weui_btn_plain_default" style="width:70%;"
			 ng-click="gotoEarnEvents()">关闭</a>
	
	</div>
	
	

<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

<div class="weui_dialog" style="border:3px solid #0f0f0f; z-index:2;" ng-show="isAskToFollowDialogShow">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
        <div class="weui_dialog_bd"> 您还未关注我们的捧场服务号，有可能会造成您收不到我们发出的红包和订单消息，请扫码关注后再尝试领取您的红包。
        <div>
        <img alt="" src="http://pc.0angle.com/sp/img/zaqrcode.jpg" style="width:80%">
        </div>
        <br/>
        </div>
        <div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleAskToFollowDialog()">知道了</a>
        </div>
    </div>

<!-- loading的动画效果 -->
<div id="loadingToast" class="class_hide" ng-class="{false: 'weui_loading_toast class_hide' , true : 'weui_loading_toast class_show'}[isLoadingShow]" 
style="z-index:3;">
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
            <p class="weui_toast_content">正在与服务器同步</p>
        </div>
    </div>
    
 <div ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isMessageDialogShow]" style="border:3px solid #0f0f0f; z-index:2;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">出错了</strong></div>
        <div class="weui_dialog_bd"> {{errMessage}}
        <br/>
        </div>
        <div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleMessageDialog()">知道了</a>
        </div>
    </div>     


</body>
    

</html>

