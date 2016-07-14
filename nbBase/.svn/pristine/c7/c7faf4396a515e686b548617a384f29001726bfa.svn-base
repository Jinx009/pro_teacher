<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<!-- saved from url=(0044)http://localhost:8080/nbBase/eventList2.html -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  
  <title>活动列表</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	
	<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
	<link rel="stylesheet" type="text/css" href="./sp/css/front/example.css" media="all"> 
	<link rel="stylesheet" type="text/css" href="./sp/css/front/eventList.css" media="all">   
	<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">   
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
	<body ng-app="zaApp" ng-controller="earnEventsController" ng-init="initial()">
	
	<div class="msg_page" id="msg_page">  
	

	
<!-- 一个推荐event的开始 -->
	<div class="msg_list" ng-repeat="eventx in eventList">
		<div class="msg_list_bd msg_list_bd_a">
			<div id="WXAPPMSG401880160" class="msg_wrapper" msgid="401880160" ng-click="gotoEventDetail(eventx.id)">
  				<div class="msg_inner_wrapper default_box news_box">
	    			<a class="msg_item news redirect">
	     				<div class="msg_item_hd">
							<h4 class="msg_title" ng-bind="eventx.title"></h4>
						</div>
						<div class="msg_item_bd">
	
							<div class="msg_cover">
								<img alt="" ng-src="{{eventx.picUrl}}"/>
							</div>
							<div class="commnet_text_div" ng-bind="eventx.desc" style="color:#670606">
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
	<br/>
	<br/>
	<table>
	<tr>
	<td width="30%"> <div style="text-align:center">
        
        <img alt="" src="http://pc.0angle.com/sp/img/zaqrcode.jpg" style="width:80%">
        </div>
	</td>
	<td> <div class="weui_dialog_bd"> 如果您还未关注我们的捧场服务号，有可能会造成您收不到我们发出的红包和订单消息，请扫码关注后再尝试领取您的红包。
	</td>
	</tr>
	</table>
        <br/>

<div style="    width: 100%;
    text-align: center;
    border-top: 2px solid #e8e8e8;
    padding-top: 0.5em;
    background: white;"><p style="ont-size: 1.2em;
    font-weight: bolder;
    border-bottom: 1px solid #e8e8e8;
    padding-bottom: 0.6em;">历史红包记录：</p>
    
    
 <div    style="border-bottom: 1px solid #e8e8e8;" ng-repeat="earnerx in earners">
   <div class="weui_media_box weui_media_appmsg" style="border-bottom:1px solid #f0f0f0;">
       <div class="weui_media_hd">
           <img class="weui_media_appmsg_thumb" ng-src="{{earnerx.headImgUrl}}" alt="">
       </div>
       <div class="weui_media_bd" style="position:absolute;">
           <h4 class="weui_media_title" style="text-align: left;padding-bottom: 0.3em;">{{earnerx.nickname}}</h4>
           <p class="weui_media_desc" style="text-align: left;">正确 {{earnerx.correctNumber}} 题， 获得了 {{earnerx.award*1.0/100.0}} 元红包</p>
       </div>
   </div>
</div>
			
</div>
<!-- 一个event的结束 -->	

	

	
	</div>

<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

<div ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isToPlayDialogShow]" style="border:3px solid #0f0f0f; z-index:1;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">选择</strong></div>
        <div class="weui_dialog_bd">已经尝试了{{currentSelect.playedTimes}}次，红包金额:<font style="color:red">{{currentAward}}</font>元
        <br/>
        <br/>
        <a href="javascript:;" class="weui_btn weui_btn_primary" ng-click="startPlay()">开始挑战</a>
        <br/>
        <a href="javascript:;" class="weui_btn weui_btn_warn" ng-click="getAward()">拿红包</a>
        <br/>
        <div style="text-align: right;">
        	<a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_default"
        	ng-click="toggleToPlayDialog()">取消</a>
        	</div>
        	<br/>
        </div>
    </div>

 <div ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isToPayDialogShow]" style="border:3px solid #0f0f0f;z-index:1;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
        <div class="weui_dialog_bd">押金金额【{{currentSelect.unitPrice}}】元
        <br/>
        <br/>
        <a href="javascript:;" class="weui_btn weui_btn_primary"
        ng-click="gotoWxPay()">微信支付</a>
        <br/>
        	<div style="text-align: right;">
        	<a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_default"
        	ng-click="toggleToPayDialog()">取消</a>
        	</div>
        </div>
        <div class="weui_dialog_ft">
           
        </div>
    </div> 
    
 <div ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isMessageDialogShow]" style="border:3px solid #0f0f0f; z-index:2;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">出错了</strong></div>
        <div class="weui_dialog_bd"> {{currentSelect.retMessage}}
        <br/>
        </div>
        <div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleMessageDialog()">知道了</a>
        </div>
    </div> 
    
    
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
<div id="loadingToast" style="z-index:3;" ng-class="{false: 'weui_loading_toast class_hide' , true : 'weui_loading_toast class_show'}[isLoadingShow]" >
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

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/earnEventsController.js" ></script>
    

</html>

