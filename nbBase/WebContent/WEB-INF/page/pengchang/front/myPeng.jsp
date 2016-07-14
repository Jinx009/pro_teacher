<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>我捧的活动</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">
<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">
<style type="text/css">
body{
	background: #f0f0f0;
}

.itemTitle{
	min-width:4em;
}

.itemDesc{
	overflow: hidden;
	text-overflow:ellipsis;
	text-overflow: ellipsis;/* IE/Safari */
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis;/* Opera */
	text-align: left;
    white-space: nowrap;
    overflow: hidden;
    width:13em;
    text-align:right;
}

.itemShortWithleftArrow{
	overflow: hidden;
	text-overflow:ellipsis;
	text-overflow: ellipsis;/* IE/Safari */
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis;/* Opera */
    white-space: nowrap;
    overflow: hidden;
    width:10em;
    text-align:right;
    vertical-align:middle;
}

.itemIt{
	color:green;
	margin-right:1em;
	font-weight:bolder;
}

.right_desc{
	position:absolute; 
	top:0px; 
	width:100%;
	background:#fafafa;
	color:#888;
	min-height:100%;
}

.class_show{
	display:block;
}
.class_hide{
	display:none;
}

.order_h1{ 
			text-align:center;
			margin-top:2vh;
}

.full_width{
	width:100%;
}

.order_number_title{
	text-align: left;
    font-size: 0.5em;
}

.order_number{
	font-size:1.3em;
	font-weight:normal;
	padding-right:10%;
	padding-left:10%;
}
.order_codebar{
	width: 100%;
    height: 0.9em;
}
.inherit_height{
	height: inherit;
}
</style>
</head>

<body ng-app="zaApp" ng-controller="myPengController" ng-init="initial()" class="ng-scope">
<div ng-class="{true:'class_show', false:'class_hide'}[isBodyShow]">
<div class="hd" >
        <h1 class="page_title order_h1">
			<p class="order_number_title">
				订单号：
			</p>
			<p class="order_number" ng-bind="orderCode"></p>
			<p class="order_coderbar" ng-show="barCodeUrl.length > 0">
				<img ng-src="{{barCodeUrl}}" class="full_width inherit_height"/>
			</p>
		</h1>
</div>

<div class="weui_cells weui_cells_access" style="margin-top: 0.4em;">
            <a class="weui_cell" href="{{eventDetailUrl}}">
                <div class="weui_cell_bd weui_cell_primary" >
                    <p>查看活动图文介绍</p>
                </div>
                <div class="weui_cell_ft">
                </div>
            </a>

</div>

<div class="weui_cells_title" ng-show="status!=0 && status<4">众筹状态：</div>
<div class="weui_cells"  ng-show="status!=0 && status<4">

	<div class="weui_cell">
               <div class="weui_cell_ft itemDesc" style="width:100%;color:green;text-align:center;" ng-bind="statusString"></div>
      </div>        
</div>


<div class="weui_cells_title">活动详情：</div>
<div class="weui_cells">

			<div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle" >
                    <p>活动名称</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="eventTitle"></div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>活动时间</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="eventDateTime"></div>
            </div>
           <div class="weui_cell weui_cells_access" ng-click="showLongDesc(eventAddress)" ng-show="eventAddress.length > 0">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>活动地点</p>
                </div>
                <div class="weui_cell_ft">
	                <div class="itemShortWithleftArrow"  style="display:inline-block;" ng-bind="eventAddress">
	                </div>
                </div>
            </div>   
            <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>支持金额</p>
                </div>
                <div class="weui_cell_ft itemDesc"><div style="display:inline-block; width:10em;"ng-bind="totalFee"></div> 元</div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>支持时间</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="payTime"></div>
            </div>

</div>
<div class="weui_cells">

            <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>众筹截止</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="deadline"></div>
            </div>
            
            <div class="weui_cell" ng-show="moneyProgress!='n/a'">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>众筹进度</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="moneyProgressText"></div>
            </div>
            
            <div class="weui_cell" ng-show="partiProgress!='n/a'">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>众筹进度</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="partiProgressText"></div>
            </div>
            
            <div class="weui_cell weui_cells_access" ng-click="showLongDesc(ruleRightLongDesc)">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>众筹权益</p>
                </div>
                <div class="weui_cell_ft" >
                	查看权益详情
                </div>
            </div>      
     
</div>
<div class="weui_cells">
            <div class="weui_cell" ng-show="realname.length > 0">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>顾客姓名</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="realname"></div>
            </div>
            
            <div class="weui_cell" ng-show="phone.length > 0">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>联系电话</p>
                </div>
                <div class="weui_cell_ft" ng-bind="phone"></div>
            </div>
            
            <div class="weui_cell" ng-show="address.length > 0">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>联系地址</p>
                </div>
            </div>
            <div class="weui_cell" ng-show="address.length > 0">
				 <div class="weui_cell_bd weui_cell_primary">
                    <textarea class="weui_textarea" placeholder="" rows="3" style="color:#888;" readonly ng-bind="address"></textarea>
                </div>

               
            </div>
		
            
        </div>
        
<div class="weui_cells_title" style="margin-top:1.7em;" ng-show="qrCodeUrl.length > 0" >您的参加此次活动的电子票：</div>
<div style="width:10em;height:10em;margin-right:auto;margin-left:auto;margin-top:0.8em;" ng-show="qrCodeUrl.length > 0">
	<img  style="width:10em;height:10em;margin-right:auto;margin-left:auto;" alt="" ng-src="{{qrCodeUrl}}">
</div>        

<br/>
<br/>
</div>
 <div ng-class="{true:'right_desc class_show',false:'right_desc class_hide'}[isRightDescShow]" ng-click="hideRightDesc()">
                    <p class="weui_textarea" placeholder="" rows="3" readonly
                    style="width: 100%;margin-right: 0;overflow: auto;white-space: normal;word-break: break-all;font-size:1em;"
                    ng-bind="longText">
                     </p>
<div style="position:fixed; bottom:1em; width:100%; text-align:center;color:black;"> 轻点任意位置返回订单详情 </div>

</div>
              
<div class="sample_div" ng-show="isSample">
</div>

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/myPengController.js" ></script>
</html>