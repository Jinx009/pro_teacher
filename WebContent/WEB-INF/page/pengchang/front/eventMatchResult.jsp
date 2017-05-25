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
	width: 22vw;
    height: 1em;
    display: block;
    margin-left: 8vw;
    border-bottom: 2px solid #026501;
    border-left: 2px solid #026501;
    margin-top: 0.3em;
    -moz-border-radius: 2em;
    border-bottom-left-radius: 0.6em;

}
.matchLineMiddleLeft{
        width: 2em;
    height: 2em;
    left: 31vw;
    position: absolute;
    top: 0.15em;
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
    width: 2em;
    height: 2em;
    right: 31vw;
    position: absolute;
    top: 0.15em;
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
        width: 22vw;
    height: 1em;
    display: block;
    float: right;
    border-top: 2px solid #026501;
    border-right: 2px solid #026501;
    margin-right: 8vw;
    border-top-right-radius: 0.6em;
}

.matchFrame{
	padding: 1em;
    border: #f0f0f0;
    margin: 0.5em;
    margin-bottom: 2em;
    -moz-border-radius: 0.38em;
    -webkit-border-radius: 0.38em;
    border-radius: 0.38em;
    background: #f0f0f0;
    -webkit-box-shadow: 0px 0px 1.5em #002D0E;
    box-shadow: 0px 0px 1.5em #002D0E;
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
	background:#f0f0f0;
}

.weui_cells:after{
border:none;
}
.weui_cells:before{
border:none;
}
.weui_cell{
	padding: 0;
    background: #f0f0f0;
    font-size: 0.85em;
}

.weui_btn_plain_default{
	border: 1px solid #026501;
	color: #026501;
	margin-right: 0.5em;
	margin-left: 0.5em;
}

.page{
	background:white;
}
</style>
</head>

<body ng-app="zaApp" ng-controller="eventMatchResultController" ng-init="initial()" style="color: #5f3f3f;">



<!-- <div class="content" id="content" ng-bind-html="contentHtml" style="margin-bottom:5em;"></div> -->
<div class="content" id="content">

<div class="page slideIn article" ng-show="isBodyReady">

<!-- 添加一个比赛结果 -->
	<a href="javascript:;" class="weui_btn weui_btn_plain_default" 
	style="
	width: 80%;
    margin-left: auto;
    margin-right: auto;
    margin-top: 1em;
    margin-bottom: 1em;
	"
			 ng-click="toggleShowSelectRival()" ndg-show="pageData.isUserPlayer">添加一个新的比赛结果</a>
		
<!-- 添加一个比赛结果 结束 -->	

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
    	
    	<div style="
    	text-align: center;
    	font-size: 0.7em;
    	line-height: 2.7em;
    	color:#aa0000;
    	">尚未得到{{matchX.playerB.nickname}}确认</div>
    
    
    </div>
    
    </div>
    
<!-- 一个比赛结果 结束 -->	

	<a href="javascript:;" class="weui_btn weui_btn_plain_default" 
	style="
	border: 1px solid #652727;
    color: #652727;
    width: 80%;
    margin-left: auto;
    margin-right: auto;
	"
			 ng-click="gotoEventDetail()">返回</a>


<br/>
<br/>
<!-- 选择对手 -->
<div class="weui_dialog" style="border:3px solid #0f0f0f; z-index:2;" ng-show="isShowSelectRival">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">选择您的比赛对手</strong></div>
        <div class="weui_dialog_bd">
       
       <div class="weui_cells weui_cells_access" 
       style="    
       		max-height: 60vh;
		    display: block;
		    position: relative;
		    overflow: scroll;">

        <a class="weui_cell" href="javascript:;" style="padding-top:5px;padding-bottom:5px;" ng-repeat="playerX in pageData.playerList"
        ng-click="toggleShowResultDialog();toggleShowSelectRival();setCurrentSelected(playerX.headImgUrl,playerX.nickname,playerX.wxId)">
            <div class="weui_cell_hd">
            <img ng-src="{{playerX.headImgUrl}}" alt="" 
            style="width:30px;margin-right:5px;display:block"></div>
            <div class="weui_cell_bd weui_cell_primary" style="text-align: left;margin-left: 1em;">
                <p ng-bind="playerX.nickname"></p>
            </div>
            <div class="weui_cell_ft"></div>
        </a>

    </div>
    

        <br/>
        </div>

        <div class="weui_dialog_ft" style="margin-top:0em;">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleShowSelectRival()">取消</a>
        </div>
    </div>
    
<!-- 选择对手 结束 -->
    
<!-- 输入比赛结果 -->
<div class="weui_dialog" style="border:3px solid #0f0f0f; z-index:2;" ng-show="isShowResultDialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">选择您的比赛结果</strong></div>
        <div class="weui_dialog_bd">
         <div class="weui_cells weui_cells_access" >
        <div class="weui_cell" href="javascript:;" style="padding-top:5px;padding-bottom:5px;">
            <div class="weui_cell_hd">
            <img ng-src="{{pageData.currentSelectedRivalHeadImgUrl}}" alt="" 
            style="width:30px;margin-right:5px;display:block"></div>
            <div class="weui_cell_bd weui_cell_primary" style="">
                <p ng-bind="pageData.currentSelectedRivalNickname"></p>
            </div>
        </div>
        </div>
        <br/>
        
       <div class="weui_cells weui_cells_checkbox">
        <label class="weui_cell weui_check_label" >
            <div class="weui_cell_hd">
                <input type="checkbox" class="weui_check" name="checkbox1" id="s11" ng-checked="pageData.currentSelectedResult == 1" ng-click="toggleResultChoose(1)">
                <i class="weui_icon_checked"></i>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p>胜利</p>
            </div>
        </label>
        <label class="weui_cell weui_check_label" >
            <div class="weui_cell_hd">
                <input type="checkbox" name="checkbox1" class="weui_check" id="s12" ng-checked="pageData.currentSelectedResult == 2" ng-click="toggleResultChoose(2)">
                <i class="weui_icon_checked"></i>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p>打平</p>
            </div>
        </label>
        <label class="weui_cell weui_check_label" >
            <div class="weui_cell_hd">
                <input type="checkbox" name="checkbox1" class="weui_check" id="s13" ng-checked="pageData.currentSelectedResult == 3" ng-click="toggleResultChoose(3)">
                <i class="weui_icon_checked"></i>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p>败北</p>
            </div>
        </label>
    </div>
	</div>
<div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleShowResultDialog();saveNewResult()">提交新的比赛结果</a>
        </div>
        <div class="weui_dialog_ft" style="margin-top:0em;">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleShowResultDialog()">取消</a>
        </div>
    </div>
    
<!-- 输入比赛结果 结束 -->
    




    
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
	<script type="text/javascript" src="./sp/ajController/pengchang/front/eventMatchResultController.js" ></script>
    

</html>