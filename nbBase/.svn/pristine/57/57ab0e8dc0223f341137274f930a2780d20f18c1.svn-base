<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>创建活动</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">
<style type="text/css">
body{
	background: #ffffff;
	color: #808080;
}
.nbBlock{
	border:2px solid #f0f0f0;
	background: #f0f0f0;
	width:96vw;
	margin-left:auto;
	margin-right:auto;
	-moz-border-radius: 9px;
	-webkit-border-radius: 9px;
	border-radius:9px;
}
.toBedeletedBlock{
	    z-index: 1;
    position: absolute;
    width: 100%;
    height: 100%;
    font-size: 2em;
    background: rgba(240, 240, 240, 0.95);
    color: #000000;
    text-align: center;
}

.toBedeletedBlockText{
	margin-top: 30%;
    
}

.weui_cells_title{
	color: #044A27;
}

.weui_cell_hd{
	font-size:0.9em;
}

.weui_cell_bd{
	font-size:0.9em;
}

.weui_input{
	font-size:0.9em;
}

</style>
</head>

<body ng-app="zaApp" ng-controller="createSimpleEventController" ng-init="initial()" >


<div class="nbBlock" style="
    background: #545454;
    color: white;
    margin-top: 1em;
    line-height: 2em;
">
<div class="weui_panel_bd" style="
    text-align: center;
    /* background: #545454; */
    /* color: white; */
">
<div ng-bind="pageTitle"> 
</div>
</div>
</div>


<div class="weui_cells_title" style="font-size: 1.2em;color: #09bb07;">填写活动信息：</div>

<div class="nbBlock">
	<div class="weui_panel_bd">
	

<div class="weui_cells_title">给活动起个名字：</div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
				<input class="weui_input" placeholder="例：3周年约饭" ng-model="eventTitle">
			</div>
		</div> 
	</div>
	<br/>

<div class="weui_cells_title">活动时间：<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;display:inline;">24小时制，晚上8点为20:00</div></div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
			<table style="border:0px solid #0000; width:80vw">
			<tbody><tr>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" 
			type="number" pattern="[0-9]*" 
			style="text-align:center; width:16vw"
			ng-model="eventYear">
			</td>
			<td style="width:4vw">
			年
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-1]?[0-9]?" style="text-align:center; width:8vw"
			ng-model="eventMonth">
			</td>
			<td style="width:4vw">
			月
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="eventDay">
			</td>
			<td style="width:4vw">
			日
			</td>
			<td style="width:4vw">
			 
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="eventHour">
			</td>
			<td style="width:4vw">
			:
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="eventMinute">
			</td>
			</tr>
		</tbody></table>

		</div>
		</div> 
	</div>
	
<br/>

<div class="weui_cells_title">地址（50字以内）：</div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea"
	            placeholder="例：上海市 普陀区 新村路888号 快乐大厦3楼" 
	            rows="3"
	            ng-model="eventAddress"></textarea>
	        </div>
	    </div>
	</div>
<br/>

	<div class="weui_cells_title">召集人数：<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;display:inline;">人数不到自动取消，0为不限制</div></div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
			<table style="border:0px solid #0000;">
			<tbody><tr>
			<td style="margin-right:1em"></td>
			
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
				<input class="weui_input" 
				type="number" pattern="[0-9]*[/.]?[0-9]?[0-9]?" 
				ng-model="targetMember" style="text-align:center; width:80vw">
			</td>
			
			<td>人</td>
			</tr>
		</tbody></table>

		</div>
		</div> 
	</div>
<br/>

	<div class="weui_cells_title">报名截止时间：<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;display:inline;">24小时制，晚上8点为20:00</div></div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
			<table style="border:0px solid #0000; width:80vw">
			<tbody><tr>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" 
			type="number" pattern="[0-9]*" 
			style="text-align:center; width:16vw"
			ng-model="targetYear">
			</td>
			<td style="width:4vw">
			年
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-1]?[0-9]?" style="text-align:center; width:8vw"
			ng-model="targetMonth">
			</td>
			<td style="width:4vw">
			月
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="targetDay">
			</td>
			<td style="width:4vw">
			日
			</td>
			<td style="width:4vw">
			 
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="targetHour">
			</td>
			<td style="width:4vw">
			:
			</td>
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
			<input class="weui_input" type="number" pattern="[0-9]*" style="text-align:center; width:8vw"
			ng-model="targetMinute">
			</td>
			</tr>
		</tbody></table>

		</div>
		</div> 
	</div>
<br/>

<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary" style="font-size:0.8em;" ng-bind="activeText"></div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="isActive" ng-change="toggleEventActive()">
                </div>
            </div>
        </div>
<br/>

	<div class="weui_cells_title">详细介绍的图文链接地址：</div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea" 
	            placeholder="如有场地介绍或活动介绍可以把链接copy到这里，一起分享给大家。也可以不填" 
	            rows="2" 
	            ng-model="descContentUrl"></textarea>
	        </div>
	    </div>
	</div>
	<br/>






</div>
</div>

<div class="weui_cells_title" style="font-size: 1.2em;color: #09bb07;">设置报名费：</div>

<div class="nbBlock" style="margin-top:1em;" ng-repeat="rulex in rules">
<div class="weui_panel_bd">

<a href="javascript:;" class="weui_btn weui_btn_warn" ng-click="removeRule($index)" ng-bind="rulex.deleteButtonText"
style="font-size:0.7em; width:70%; margin-top:0.6em;"
ng-hide="rules.length == 1 && $index==0"
></a>
<div style="position:relative;">
<div class="toBedeletedBlock" ng-show="rulex.isToDeleted" style="font-size:0.7em;">
    <div class="toBedeletedBlockText">
    已 删 除
    </div>
</div>
<br/>


<div class="weui_cells_title">报名费：<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;display:inline;">0为金额随意，但报名者最少需支付1元</div></div>
<div class="weui_cells weui_cells_form">	
		<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<table style="border:0px solid #0000;">
			<tbody><tr>
			<td style="margin-right:1em">¥</td>
			
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
				<input class="weui_input " type="number" pattern="[0-9]*[/.]?[0-9]?[0-9]?" 
				ng-model="rulex.unitPrice" 
				style="text-align:center; width:80vw">
			</td>
			
			<td>元</td>
			</tr>
		</tbody></table>

		</div>
	</div> 
</div>


<div class="weui_cells_title">补充说明：</div>
<div class="weui_cells weui_cells_form">	
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea class="weui_textarea" 
            placeholder=
            "补充说明一下付了报名费能有啥好处，不填也行"
            ng-model="rulex.ruleDesc"
            rows="3"></textarea>
        </div>
    </div>
</div>


<br/>
</div>


</div>
</div>
<br/>
<a href="#" class="weui_btn weui_btn_plain_primary" ng-click="addNewRule()" style="width:70%;font-size:0.7em;">创建一个新的报名规则</a>
<br/>

<div
style="
	    width: 96%;
    height: 0.6em;
    border-top: 3px solid #f0f0f0;
    margin-right: auto;
    margin-left: auto;
"
></div>

<a href="#" class="weui_btn weui_btn_primary" ng-click="saveTheModification()" style="width:70%;font-size:0.8em;">保 存</a>
<br/>

<a href="#" class="weui_btn weui_btn_primary" ng-click="gotoMyEvents()" style="width:70%;font-size:0.8em;">返 回</a>
<br/>


<a href="#" class="weui_btn weui_btn_primary" ng-click="gotoEventDetail()" style="width:70%;font-size:0.8em;">查看活动内容并分享</a>
<br/>








<br/>
<br/>

<div style="position:fixed; bottom:0em;width:100%;">

</div>


<!-- loading的动画效果 -->
<div id="loadingToast" ng-class="{false: 'weui_loading_toast class_hide' , true : 'weui_loading_toast class_show'}[isLoadingShow]" class="class_hide">
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
            <p class="weui_toast_content">后台提交中...<p>
        </div>
    </div>
<!-- 对话框 -->
<div ng-class="{false: 'weui_dialog class_hide' , true : 'weui_dialog class_show'}[isDialogShow]" style="border: 2px solid #0f0f0f; background:#e5e5e5">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title"></strong></div>
        <div class="weui_dialog_bd" ng-bind="dialogMessage"></div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog primary" ng-click="hideDialog()">确定</a>
        </div>
    </div>   
 

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/createSimpleEventController.js" ></script>
</html>