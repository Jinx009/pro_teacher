<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
  	
<title>支付</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/payTheEvent.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">

</head>

<body ng-app="zaApp" ng-controller="payTheEventController" ng-init="initial()">


<div class="weui_cells weui_cells_form" style="width:100%; border: 0px solid #ffFF00;">
	<div class="weui_cell" style="width:100%;">
		<div class="weui_cell_hd" style="width:100%; text-align:center;" ><label style="width:100%; text-align:center;"
		ng-bind="ruleDescDetail"></label></div>
	</div>
</div>

<div class="weui_cells weui_cells_form" style="width:100%">

	<div class="weui_cell" style="width:100%" ng-show="unitMoney>0&&ruleData.acceptManyCopy">
		<div class="weui_cell_hd" >份数：</div>
		
		<table style="border:0px solid #0000;margin-right:auto;margin-left:auto;" >
			<tr>
			
			<td class="number_adjust_button_minus" ng-click="removeCopy()"><</td>
			
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
				<input class="weui_input" type="number" pattern="[0-9]*" ng-model="copiesNumber" style="text-align:center; width:6em" ng-model="copiesNumber" ng-change="copyChanged()">
			</td>
			
			<td class="number_adjust_button_plus"  ng-click="addCopy()">></td>
			
			</tr>
		</table> 
	</div>
               
	<div class="weui_cell">          
		
		<div class="weui_cell_hd" width="100%" ><label class="weui_label" style="width:100%">金额：</label></div>
			
		<table style="border:0px solid #0000;padding-left:2.5em;margin-right:auto;margin-left:auto;">
			<tr>
			<td style="margin-right:1em">¥</td>
			
			<td style="border-bottom: 1px solid #ccc;text-align:center;">
				<input class="weui_input" type="number" pattern="[0-9]*[/.]?[0-9]?[0-9]?" ng-model="totalMoney" style="text-align:center; width:6em;" ng-readonly="notInputable">
			</td>
			
			<td >元</td>
			</tr>
		</table >
	                
	</div>
            
            
</div>

<div class="weui_cells weui_cells_form">
	<div class="weui_cell">
		<div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
		<div class="weui_cell_bd weui_cell_primary">
			<input class="weui_input"  placeholder="请输入联系人姓名（选填）" ng-model="realname"/>
		</div>
	</div>
            
</div>

<div class="weui_cells">
	<div class="weui_cell weui_cell_select weui_select_before">
		<div class="weui_cell_hd">
			<select class="weui_select" name="select2" >
				<option value="1">+86</option>
			</select>
		</div>
		<div class="weui_cell_bd weui_cell_primary" >
			<input class="weui_input" type="tel" pattern="[0-9]*" placeholder="您的电话号码（选填）" ng-model="phone"/>
		</div>
	</div>
</div>
        
<div class="weui_cells_title" ng-show="ruleData.isNeedAddress">联系地址</div>

<div class="weui_cells weui_cells_form" ng-show="ruleData.isNeedAddress">
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            <textarea class="weui_textarea" placeholder="请从【省份】开始填写您的配送地址" rows="3" ng-model="address" ng-change="addressChanged()"></textarea>
            <div class="weui_textarea_counter"><span ng-bind="addressLength"></span>/100</div>
        </div>
    </div>
</div>

        
<div class="weui_cells weui_cells_form" style="margin-top:5vh;">
	<a href="#" class="weui_btn weui_btn_primary" ng-click="wxPay()">确定支付</a>
</div>
        
<!-- loading的动画效果 -->
<div id="loadingToast" class="class_hide" ng-class="{false: 'weui_loading_toast class_hide' , true : 'weui_loading_toast class_show'}[isLoadingShow]" >
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
            <p class="weui_toast_content">正在生成支付数据</p>
        </div>
    </div>
<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

<div ng-class="{true: 'sample_div class_show', flase:'sample_div class_hide'}[eventData.isSample]">
</div>
</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/wxPayService.js" ></script>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/payTheEventController.js" ></script>
    


</html>