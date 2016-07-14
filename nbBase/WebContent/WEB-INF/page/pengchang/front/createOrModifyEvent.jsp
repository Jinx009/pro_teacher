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
}
.nbBlock{
	border:2px solid #044A27;
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
    background: rgba(0, 0, 0, 0.8);
    color: #ffffff;
}

.toBedeletedBlockText{
	margin-top: 50%;
    margin-bottom: auto;
    text-align: center;
}

.weui_cells_title{
	color: #044A27;
}

</style>
</head>

<body ng-app="zaApp" ng-controller="createOrModifyEventController" ng-init="initial()" >


<div class="weui_cells_title" style="font-size: 1.2em;color: #09bb07;">-基本设置：</div>
<br/>

<div class="nbBlock">
	<div class="weui_panel_bd">
	
	<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">是否开放报名</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="isActive">
                </div>
            </div>
        </div>
<br/>
	
	<div class="weui_cells_title">名称（15字以内）：</div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
				<input class="weui_input" placeholder="例：【带摸】活动，开启手感之旅" ng-model="eventTitle">
			</div>
		</div> 
	</div>
	<br/>
	
	<div class="weui_cells_title">简介（50字以内）：</div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea" 
	            placeholder="例：带摸（英文：demo）项目是为了方便用户快速理解和使用 捧场-大家筹 功能的演示项目，支付是真实的！" 
	            rows="3"
	            ng-model="eventDesc"></textarea>
	        </div>
	    </div>
	</div>
	<br/>

	<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">是否线下活动</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="isO2OEvent">
                </div>
            </div>
        </div>
<br/>
<div ng-show="isO2OEvent">
<div class="weui_cells_title">活动地址（50字以内）：</div>
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
<div class="weui_cells_title">活动时间：</div>
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
	<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">时间是24小时制，如：晚上8点半表示为 20:30</div>
	<br/>	
	
</div>
	
	<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">是否众筹项目</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="isCFEvent" ng-change="checkIfAllowNoneCFEvent()">
                </div>
            </div>
        </div>
<br/>
	<div ng-show="isCFEvent">
	<div class="weui_cells_title">目标金额：</div>
	<div class="weui_cells weui_cells_form">	
			<div class="weui_cell">
			<div class="weui_cell_bd weui_cell_primary">
			<table style="border:0px solid #0000;">
			<tbody><tr>
			<td style="margin-right:1em">¥</td>
			
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
				<input class="weui_input" 
				type="number" pattern="[0-9]*[/.]?[0-9]?[0-9]?" 
				ng-model="targetMoney" style="text-align:center; width:80vw">
			</td>
			
			<td>元</td>
			</tr>
		</tbody></table>

		</div>
		</div>
	</div>
	<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">0 表示不设置目标金额，没有上限</div> 
	<br/>
	
	<div class="weui_cells_title">目标人数：</div>
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
	<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">0 表示不设置目标人数，没有上限</div>
	<br/>
	
	<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">目标金额与目标人数是否要同时实现</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="isAndSucceed">
                </div>
            </div>
        </div>
<br/>
	
	<div class="weui_cells_title">募集有效期至：</div>
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
	<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">时间是24小时制，如：晚上8点半表示为 20:30</div>
	<br/>
	
	<div style="width:60vw;margin-right:auto;margin-left:auto;">
	<a href="javascript:;" class="weui_btn weui_btn_plain_primary" style="color:#044a27;" ng-click="toggleEventEntryDialog()">查看活动的入口地址</a>
	</div>
	<br/>
	</div>
</div>
</div>
<br/>
<div class="weui_cells_title" style="font-size: 1.2em;color: #09bb07;">-补充信息配置：</div>
<br/>

<div class="nbBlock">
	
	<div class="weui_cells_title">详情介绍的图文链接地址：</div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea" 
	            placeholder="例：https://mp.weixin.qq.com/s?__biz=MzIyMjE0MjMxNw==&mid=401786414&idx=1&sn=ae576c6218f1183da8f28b4863adce50&scene=1&srcid=0315MhnC3oB1c1W6LBikowgs&key=710a5d99946419d9eb20a255d5238e30a381164f3613fcc4bf598b7ee23e0f345279958661002e59e4dfec3b7723713c&ascene=0&uin=MjMyOTI2NzI2MQ%3D%3D&devicetype=iMac+MacBookPro11%2C1+OSX+OSX+10.10.5+build(14F1021)&version=11020201&pass_ticket=QWKfz0b44T2FjPkFCT8YR3C869RoQ9Jdz35%2BQylFFmJBP1a19qqYrsB2PT5yZmhQ" 
	            rows="6" 
	            ng-model="descContentUrl"></textarea>
	        </div>
	    </div>
	</div>
	<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">如果没有可以暂时不填</div>
	<br/>
	
	
	
	<!-- <div class="weui_cells_title">活动的入口地址：</div>
	<div class="weui_cells weui_cells_form">	
	    <div class="weui_cell">
	        <div class="weui_cell_bd weui_cell_primary">
	            <textarea class="weui_textarea" rows="3"
	            ng-model="eventEntryUrl"></textarea>
	        </div>
	    </div>
	</div> -->
	<br/>
	
	
	</div>
</div>

<br/>

<div class="weui_cells_title" style="font-size: 1.2em;color: #09bb07;">-支付金额及权益规则设置：</div>
<br/>
<div class="nbBlock" style="margin-top:1em;" ng-repeat="rulex in rules">
<div class="weui_panel_bd">
<a href="javascript:;" class="weui_btn weui_btn_warn" ng-click="removeRule($index)" ng-bind="rulex.deleteButtonText"></a>
<div style="position:relative;">
<div class="toBedeletedBlock" ng-show="rulex.isToDeleted">
    <div class="toBedeletedBlockText">
    已 删 除
    </div>
</div>
<br/>
<div class="weui_cells_title">捧场说明（10字以内）：</div>
<div class="weui_cells weui_cells_form">	
		<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<input class="weui_input ng-pristine ng-untouched ng-valid" placeholder="例：支持1元获取抽奖机会" 
			ng-model="rulex.ruleTitle">
		</div>
	</div> 
</div>
<br/>

<div class="weui_cells_title">单笔金额：</div>
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
<div style="color:#942323;margin-right:1.3em; margin-left:1.3em;margin-top:0.2em;font-size:0.7em;">0 表示金额随意，由用户自己决定</div>
<br/>
<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">购买时是否需要用户快递信息</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox"
                    ng-model="rulex.isNeedAddress">
                </div>
            </div>
        </div>
<br/>
<div class="weui_cells_title">捧场规则的详细说明：</div>
<div class="weui_cells weui_cells_form">	
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea class="weui_textarea" 
            placeholder="例：支持1元即可获得抽奖机会，奖品是由ABC公司提供的儿童教育礼包一份，内有《儿童心理》等多本儿童书籍。" 
            ng-model="rulex.ruleDesc"
            rows="3"></textarea>
        </div>
    </div>
</div>
<br/>
<div ng-show="isCFEvent">
<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">参与人是否计入有效人数</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="rulex.isCountInMember">
                </div>
            </div>
        </div>
<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">参与金额是否计入有效金额</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="rulex.isCountInAmount">
                </div>
            </div>
        </div>
<div class="weui_cells weui_cells_form">
            <div class="weui_cell weui_cell_switch">
                <div class="weui_cell_hd weui_cell_primary">截止日期之后是否仍接受支持</div>
                <div class="weui_cell_ft">
                    <input class="weui_switch" type="checkbox" ng-model="rulex.isAcceptableAfterDeadline">
                </div>
            </div>
        </div>
</div>
<br/>
<div style="width:60vw;margin-right:auto;margin-left:auto;">
	<a href="javascript:;" class="weui_btn weui_btn_plain_primary" style="color:#044a27;" ng-click="togglePayEntryDialog(rulex.ruleId, rulex.ruleTitle)">查看支付入口地址</a>
	</div>

<!-- <div class="weui_cells_title">捧场支付的入口地址：</div>
<div class="weui_cells weui_cells_form">	
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea class="weui_textarea" rows="3"
            ng-model="rulex.payRuleUrl"></textarea>
        </div>
    </div>
</div> -->
<br/>
</div>


</div>
</div>
<br/>
<a href="#" class="weui_btn weui_btn_plain_primary" ng-click="addNewRule()">添加新的权益规则</a>
<br/>
<br/>

<br/>






<br/>
<br/>

<div style="position:fixed; bottom:0em;width:100%;">
<a href="#" class="weui_btn weui_btn_primary" ng-click="saveTheModification()">提交修改</a>
</div>

<div ng-class="{true: 'weui_dialog_alert', false: 'class_hide'}[isShowUrlAddress]" >
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">您的URL地址</strong></div>
            <div class="weui_dialog_bd" style="word-break: break-all;" ng-bind="urlToShow"></div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleDialog()">确定</a>
            </div>
        </div>
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
	<script type="text/javascript" src="./sp/ajController/pengchang/front/createOrModifyEventController.js" ></script>
</html>