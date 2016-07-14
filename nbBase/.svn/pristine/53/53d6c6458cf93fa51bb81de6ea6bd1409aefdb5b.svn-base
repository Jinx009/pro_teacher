<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>流言榜</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">

<style type="text/css">
.circle_img_div{
	display:block;
	width:4em;
	height:4em;
	line-height:4em;
	text-align:center;
	background:#039b02;
	color:#f0f0f0;
	text-size:10;
	z-index:1;
	border:4px solid #039b02;
	-moz-border-radius: 59px;
	-webkit-border-radius: 59px;
	border-radius:59px;
	
}

.frozen_div{
	pointer_events:none;
}

.avator_icon{
	margin-top: 1em;
	margin-left: 1.28em; 
	margin-bottom:1em;
	height:3em; 
	width:3em; 
	display:inline-block;
	position:absolute;
}

.message_text_style{
	
	display: inline-block;
    min-width: 4em;
    min-height: 4em;
    line-height: 1.4em;
    color: #0f0f0f;
    border: 2px solid #dfdfdf;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    margin-top: 1em;
    margin-left: 8em;
    margin-right:1em;
    margin-bottom:1em;
    word-break: break-all;
    word-wrap: break-word;
    white-space: normal;
    font-size:0.7em;
    padding-left:0.5em;
    padding-right:0.3em;
    width:70%;
	
}

.circle_img{
	width:4em;
	height:4em;
	line-height:4em;
	border-radius: 4em;

}
.messageBoardTitle{
	widht:100%;
	height:6em;
	border: 4px solid #ffffff;
	background: #5c4033;
}

.messageBoard_text_div{
	margin-top: 0.7em;
	margin-left: 1.28em;
	display:inline-block;
	color:#f0f0f0;
	
}

.nb_bar_item_on{
	border-bottom:2px solid #33b86c;
}

.nb_bar_item_off{
	
	
}

.commnet_text_div{
	font-size:1.2em;
	max-height:10.2em;
	overflow:hidden;
	display: -webkit-box;
    text-overflow: ellipsis;
    overflow: hidden;
    -webkit-line-clamp: 8;
    -webkit-box-orient: vertical;
}

.messageBoard_div{
	background:#f0f0f0;
	width:100%;
	min-height:5em;
	border-bottom:1px solid #0f0f0f0;
	margin-bottom:1em;
}
.weui_navbar:after{
	content:none;
}

.right_desc{
	position:absolute; 
	top:0px; 
	width:100%;
	background:#fafafa;
	color:#888;
	min-height:100%;
	z-index:3;
}

.class_show{
	display:block;
}
.class_hide{
	display:none;
}

.img_thum_div{
	color:#b0b0b0;
	font-size:1.2em;
	margin-left:0.4em;
	margin-top:0.2em;
	margin-bottom:0.5em; 
	width:3em; 
	height:3em;
	display:inline-block;
	overflow:hidden;
}
.user_new_commnets_textarea{
	width:100%; 
	height:10em;
	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; 
	font-size:1em;
	border:0;
	color:#4f4f4f;
}
</style>
</head>

<body style="width:100%;border:0px solid #ff0000;heigh:100%;background:#ccc;" ng-app="zaApp" ng-controller="messageBoardController" ng-init="initial()">
<div >
<div class="messageBoardTitle">
	<div class="circle_img_div" style="margin-top: 0.7em;margin-left: 1.28em;display:inline-block;">
		<img src="{{companyAvator}}" class="circle_img">
	</div>
	
	<div class="messageBoard_text_div">
		<p ng-bind="projectTitle"></p>
		<p style="font-size:0.7em;" ng-bind="companyName"></p>
		<p ng-bind="additionalDesc"></p>
	</div>
</div>



            <div class="weui_navbar" style="position:inherit">
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[ctlPublicTab]" ng-click="switchTab(1)">
                    流言榜
                </div>
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[ctlPrivateTab]"  ng-click="switchTab(2)">
                    我的
                </div>
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[ctlLeaveMessageTab]"  ng-click="switchTab(3)">
                    发言
                </div>
            </div>




<div class="messageBoard_public"  ng-class="{true:'class_show', false:'class_hide'}[ctlPublicTab]">

<!-- 一段留言的开始 -->
	<div class="messageBoard_div" ng-repeat="comx in publicComments">

<!-- 用户的留言展示 -->
		<div class="circle_img_div avator_icon">
			<img src="{{comx.userAvator}}" class="circle_img" style="width:3em; height:3em;">
		</div>
		
		<div class="message_text_style">
		<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			{{comx.userNickname}}
		</div>
		<div ng-click="toggleLongText(comx.comments)" class="commnet_text_div">
			{{comx.comments}}
		</div>
		
		<div style="margin-top:0.5em;" >
			<div class="img_thum_div" ng-click="wxViewPhotos(comx.comPic, $index)" ng-repeat="picx in comx.comPic"
			style="
			background-image: url({{picx.url}});
			 background-repeat: no-repeat;
    background-position: center;
    -webkit-background-size: contain;
    -moz-background-size: contain;
    -o-background-size: contain;
    background-size: contain;
			">
			<!-- <img src="{{picx.url}}" style="width:3em;"/> -->
			</div>
		</div>
		
		<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
		{{comx.comTime}}
		</div>
		
		
		</div>

<!-- 项目主的反馈 -->
<div style="postion:relative;" ng-class="{true:'class_hide', false:'class_show'}[comx.companyComment==null]">
		<div class="circle_img_div avator_icon">
			<img src="{{companyAvator}}" class="circle_img" style="width:3em; height:3em;">
		</div>
		
		<div class="message_text_style"  sytle="width:70%;">
		<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			{{comx.companyReplyer}}
		</div>
		<div ng-bind="comx.companyComment" class="commnet_text_div">
		</div>
		<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
		{{comx.companyCommentTime}}
		</div>
		</div>
</div>
	
	</div>
	<!-- 一段留言的结束 -->

</div> <!-- class -messageBoard_public //-->



<div class="messageBoard_private" ng-class="{true:'class_show', false:'class_hide'}[ctlPrivateTab]">

<!-- 一段留言的开始 -->
	<div class="messageBoard_div" ng-repeat="comx in privateComments">

<!-- 用户的留言展示 -->
		<div class="circle_img_div avator_icon">
			<img src="{{comx.userAvator}}" class="circle_img" style="width:3em; height:3em;">
		</div>
		
		<div class="message_text_style"  sytle="width:70%;">
		<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			{{comx.userNickname}}
		</div>
		<div ng-click="toggleLongText(comx.comments)" class="commnet_text_div">
			{{comx.comments}}
		</div>
		
		<div style="margin-top:0.5em;" >
			<div class="img_thum_div" ng-click="wxViewPhotos(comx.comPic, $index)" ng-repeat="picx in comx.comPic"
			style="
			background-image: url({{picx.url}});
			 background-repeat: no-repeat;
    background-position: center;
    -webkit-background-size: contain;
    -moz-background-size: contain;
    -o-background-size: contain;
    background-size: contain;
			"
			>
			<!-- <img src="{{picx.url}}" style="width:3em;"/> -->
			</div>
		</div>
		
		<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
		{{comx.comTime}}
		</div>
		</div>

<!-- 项目主的反馈 -->
<div style="postion:relative;" ng-class="{true:'class_hide', false:'class_show'}[comx.companyComment==null]">
		<div class="circle_img_div avator_icon">
			<img src="{{companyAvator}}" class="circle_img" style="width:3em; height:3em;">
		</div>
		
		<div class="message_text_style"  sytle="width:70%;">
		<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			{{comx.companyReplyer}}
		</div>
		<div ng-bind="comx.companyComment" class="commnet_text_div">
		</div>
		<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
		{{comx.companyCommentTime}}
		</div>
		</div>
</div>
	
	</div>

	<!-- 一段留言的结束 -->

</div> <!-- class -messageBoard_private //-->


<div class="messageBoard_leavemessage" ng-class="{true:'class_show', false:'class_hide'}[ctlLeaveMessageTab]">

		<div style="margin-top:0.5em;background:#f0f0f0;">
		<textarea class="user_new_commnets_textarea" placeholder="请畅所欲言吧，精彩的吐槽将会得到创始人发的红包" ng-model="newUserMessage"></textarea>
		
		</div>

		<div style="margin-top:0.5em;background:#f0f0f0;">
		

			<!-- <div class="img_thum_div" ng-repeat="picx in comPic">
				<img ng-src="{{picx.url}}" style="width:3em;"/>
			</div> -->

		<!-- 这里要插入图片的 -->
		
		<div id="userUploadPics" style="color:#b0b0b0;font-size:1.2em;margin-left:0.4em;margin-top:0.2em;margin-bottom:0.5em; width:3em; height:3em;display:inline-block;text-align:center;" ng-click="chooseNewPic()">
			<img src="./sp/img/add_icon.jpg" style="width:3em;"/>
		</div>
		
		</div>
		
		<div style="margin-top:0.5em;background:none;margin-right:auto;margin-left:auto;" ng-click="startUploadNewPicToWx(0)">
			<div class="weui_btn weui_btn_plain_primary" style="background:#f0f0f0">确认提交</div>
		
		</div>
		
		

		

</div> <!-- class -messageBoard_leavemessage //-->

</div>

<!-- main div 结束 -->

<div class="right_desc" style="position:fixed;top:0;bottom:0;overflow:auto;"  ng-class="{true:'class_show', false:'class_hide'}[isShowLongText]" ng-click="toggleLongText('N/A')">
                    <div class="weui_textarea" placeholder="" readonly
                    style="width: 100%; height:100%; margin-right: 0;overflow: auto;white-space: normal;word-break: break-all;font-size:1em;"
                    ng-bind="currentLongText"
                    >

                     </div>
<div style="position:fixed; bottom:1em; width:100%; text-align:center;color:black;"> 轻点任意位置返回订单详情 </div>

</div>

<div class="right_desc" style="position:fixed;bottom:0px;text-align:center;padding-top:2em;"  ng-class="{true:'class_show', false:'class_hide'}[isShowOrgPic]" ng-click="toggleOrgPic('http://')">
<div class="weui_textarea" placeholder="" readonly
                    style="width: 100%; height:100%; margin-right: 0;overflow: auto;white-space: normal;word-break: break-all;font-size:1em;">
                   <img src='{{orgPicUrl}}' style="margin-right:auto;margin-left:auto; margin-top:auto; margin-bottom:auto; width:70%;"   />
                     </div>
                  
                   
<div style="position:fixed; bottom:1em; width:100%; text-align:center;color:black;"> 轻点任意位置返回订单详情 </div>
</div>


<!-- <div class="sample_div">
</div> -->

<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>


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
            <p class="weui_toast_content">正在上传您的意见</p>
        </div>
    </div>


<!-- 测试 -->
<!-- <div id="test">
		
		<img ng-src="picx.url" width="100%"/>
		</div>
		
		
		<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.4em;margin-top:0.2em;margin-bottom:0.5em; width:3em; height:3em;display:inline-block;text-align:center;" onclick="javascript:load()">
			<img src="./sp/img/add_icon.jpg" style="width:3em;"/>
		</div> -->

</body>


	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/messageBoardController.js" ></script>

<!-- 
<script type="text/javascript">
function load(){
	wx.chooseImage({
	    count: 9, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	    	alert(res.localIds.length);
	    	for (var i = 0; i < res.localIds.length; i++) 
			{
	    		alert(i);
				$("#test").html("");
				alert(i);
				var imgHtml = "<img src='"+res.localIds[i]+"'   width='100%'   />";
				alert(imgHtml);
				$("#test").append(imgHtml);
			}
	    }
	});
}

</script> -->

</html>