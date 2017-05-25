<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html><head><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}.ng-animate-shim{visibility:hidden;}.ng-anchor{position:absolute;}</style>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>活动列表</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	
 

<link rel="stylesheet" type="text/css" href="../sp/css/other/weui.css" media="all">
<!-- <link rel="stylesheet" type="text/css" href="../sp/css/other/example.css" media="all"> -->  
<link rel="stylesheet" type="text/css" href="../sp/css/other/nb.css" media="all">  
<link rel="stylesheet" type="text/css" href="../sp/css/other/casio160531.css" media="all">
<jsp:include page="../_include_js_mobile.jsp"></jsp:include>
<script type="text/javascript" src="../sp/ajController/other/casio160531Controller.js"></script>   
</head>

<body ng-app="zaApp" ng-controller="casio160531Controller" ng-init="initial()" style="color: #5f3f3f;border: 20px solid transparent;border-image: url(../sp/img/casio/背景.png) 27 27 round;height: auto;overflow-x: hidden;width:auto;min-height: 95%;position: absolute;background-image: url(../sp/img/casio/木纹背景.png);background-size: cover;" class="ng-scope">

<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->

<div style="padding-left: 3em;padding-right: 3em;padding-top: 2vh;">
<img src="../sp/img/casio/30年.png" style="width: 100%;">
</div>
<div style="
	padding-left: 1.5em;
	padding-right: 1.5em;
	font-size: 2vh;
	text-align: center;
	margin-top: 1.5vh;
	line-height: 1.7em;
">
<div>
穿越回到过去，对话曾经的自己：
</div>
<div>
当初的愿望实现了吗？那时的梦想可还在吗？
</div>
<div style="
margin-top:0.5em;
">
在这台“时光机”里，上传你小时候的照片和近期的照片，回味儿时的记忆，找回最初的自己......
</div>
</div>
<div style="
    padding-left: 3em;
    padding-right: 3em;
    margin-top: 2em;
    display:none;
">
<img src="../sp/img/casio/文字.png" style="
    width: 100%;
">
</div>

<div style="padding-left: 4vw;padding-right: 4vw;margin-top: 1vh;">
<table style="width: 100%;">
<tbody>
<tr>
	<td style="width: 50%;text-align: center;">
		<div style="width:100%;position:relative;display: inline-block;">
		<div style="margin-top:100%;"></div>
		<div style="position:absolute;top: 0;bottom: 0;left: 0;right: 0;border: 0.7em solid transparent;border-image: url(../sp/img/casio/相框底.png) 10 10 round;" ng-show="pageData.picUrl[0].length==0" ng-click="chooseNewPic(0)">
		<!-- <img src="../sp/img/casio/相框底.png" style="width:100%;"/> -->
			<div style="background: -webkit-linear-gradient(#B9A996,#F2EEE6,white);width:100%;height:100%;">
			</div>
			<div style="font-size: 4vw;position: absolute;width: 100%;top: 10%;color: white;">
		    		请先上传图片
		    </div>
			<div style="font-size: 3vw;position: absolute;width: 100%;bottom: 10%;color: white;text-shadow: 0px 0px 10px #624242;">
		    		#30年，音为梦想#
		   	</div>
		   	<img src="../sp/img/casio/相框添加符号.png" style="width: 30%;position: absolute;top: 35%;left: 35%;">
		</div>
		<div style="position:absolute;top: 0;bottom: 0;left: 0;right: 0;border: 0.7em solid transparent;border-image: url(../sp/img/casio/相框底.png) 10 10 round;" ng-show="pageData.picUrl[0].length>0" ng-click="chooseNewPic(0)" class="ng-hide">
			<div style="position:absolute;top:0;left:0;width:100%;height:100%;background-image:url();background-size:cover;">
			<div style="font-size: 3vw;position: absolute;width: 100%;bottom: 10%;color: white;text-shadow: 0px 0px 10px #624242;">
		    		#30年，音为梦想#
		   	</div>
			</div>
		</div>
		</div>
		<label style="text-decoration: underline;margin-top: 0.5em;" ng-click="chooseNewPic(0)">点击上传</label>
	</td>
	<td style="width: 50%;text-align: center;">
		<div style="width:100%;position:relative;display: inline-block;">
		<div style="margin-top:100%;"></div>
		<div style="position:absolute;top: 0;bottom: 0;left: 0;right: 0;border: 0.7em solid transparent;border-image: url(../sp/img/casio/相框底.png) 10 10 round;" ng-show="pageData.picUrl[1].length==0" ng-click="chooseNewPic(1)">
		<!-- <img src="../sp/img/casio/相框底.png" style="width:100%;"/> -->
			<div style="background: -webkit-linear-gradient(#B9A996,#F2EEE6,white);width:100%;height:100%;">
			</div>
			<div style="font-size: 4vw;position: absolute;width: 100%;top: 10%;color: white;">
		    		请先上传图片
		    </div>
			<div style="font-size: 3vw;position: absolute;width: 100%;bottom: 10%;color: white;text-shadow: 0px 0px 10px #624242;">
		    		#30年，音为梦想#
		   	</div>
		   	<img src="../sp/img/casio/相框添加符号.png" style="width: 30%;position: absolute;top: 35%;left: 35%;">
		</div>
		<div style="position:absolute;top: 0;bottom: 0;left: 0;right: 0;border: 0.7em solid transparent;border-image: url(../sp/img/casio/相框底.png) 10 10 round;" ng-show="pageData.picUrl[1].length>0" ng-click="chooseNewPic(1)" class="ng-hide">
			<div style="position:absolute;top:0;left:0;width:100%;height:100%;background-image:url();background-size:cover;">
			<div style="font-size: 3vw;position: absolute;width: 100%;bottom: 10%;color: white;text-shadow: 0px 0px 10px #624242;">
		    		#30年，音为梦想#
		   	</div>
			</div>
		</div>
		</div>
		<label style="text-decoration: underline;margin-top: 0.5em;" ng-click="chooseNewPic(1)">点击上传</label>
	</td>
</tr>
</tbody>
</table>
</div>
<div style="
	padding-left: 1.5em;
	padding-right: 1.5em;
	font-size: 1.75vh;
	text-align: center;
	margin-top: 0.8em;
">
<p style="
    display: inline-block;
    font-weight: bolder;
    text-shadow: 1px 1px 1px whitesmoke;
">
#30年，音为梦想#</p><p style="
    display: inline;
"> 如果我有一台"时光机"，我想回到过去</p>
</div>

<div style="text-align: center;">
<img src="../sp/img/casio/一键按钮.png" style="height: 6vh;margin-top: 1.5vh;" ng-click="doSubmit()">
</div>

<div style="text-align:center;color: white;font-size: 0.8em;line-height:2em;">
<div style="width: 1.1em;height: 1.1em;display:inline-block;background-color: white;vertical-align: middle;border: 2px solid #A8A095;" ng-click="pageData.isShareToWeibo = !pageData.isShareToWeibo">
<img src="../sp/img/casio/勾.png" style="width: 100%;display: inline-block;vertical-align: top;margin-top: 30%;" ng-show="pageData.isShareToWeibo">
</div>
<div style="display:inline-block;vertical-align: middle;">
关注@中国CASIO电子乐器
</div>
</div>

<div style="text-align:center;color: white;font-size: 0.8em;text-decoration: underline;line-height:2em;">
活动细则
</div>

<div style="width: 100%;text-align: center;bottom: 0em;background-color: black;position: absolute;">
	<img src="../sp/img/casio/CASIO.png" style="height: 4.5vh;margin: 1em;display: inline-block;vertical-align: middle;">
</div>







<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->


  	


<!-- 微信嵌入 -->
<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" style="position:absolute;top:0px;left: 0px;" class="wxReady_indicator_fail"></div>

<!-- ERROR Page -->
 <div style="border:3px solid #0f0f0f; z-index:2;" class="weui_dialog ng-hide" ng-show="isErrorDialogShow">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
        <div class="weui_dialog_bd ng-binding" ng-bind="errorMessage"></div>
        <div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="isErrorDialogShow = !isErrorDialogShow">知道了</a>
        </div>
    </div> 

<!-- loading的动画效果 -->
<div id="loadingToast" style="z-index:3;" class="weui_loading_toast ng-hide" ng-show="isLoadingShow">
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

<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->


    





</body></html>

