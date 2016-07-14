<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>活动参与状态</title>

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

<body ng-app="zaApp" ng-controller="eventStatusController" ng-init="initial()" class="ng-scope">
<div ng-class="{true:'class_show', false:'class_hide'}[isBodyShow]">
<div class="hd" >
        <h1 class="page_title order_h1">
        <table style="border:0px; border-spacing:2em 0em;">
        <tr>
        	<td>
        	<div class="order_number_title" style="">
				剩余时间：
			</div>
        	</td>
        </tr>
        <tr>
        	<td>
        	<div  class="order_number" style="display:inline-block; padding-right:0; padding-left:0;font-size:7.5vw;" ng-bind="leftTimeString">20 天 03 小时 42 秒</div>
        	</td>
        </tr>
        </table>
				
		</h1>
</div>


<div class="weui_cells_title">状态详情：</div>
<div class="weui_cells">

			<div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle" >
                    <p>截止日期</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="deadLine"></div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>获捧金额</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="gotTotalMoney"> 3 万元</div>
            </div>
           <div class="weui_cell">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>获捧人数</p>
                </div>
                <div class="weui_cell_ft itemDesc" ng-bind="gotPartiNum"> 20 人</div>
            </div>
            <!-- weui_cells_access -->
           <div class="weui_cell ">
                <div class="weui_cell_hd itemIt">-</div>
                <div class="weui_cell_bd weui_cell_primary itemTitle">
                    <p>众筹状态</p>
                </div>
                <div class="weui_cell_ft" ng-bind="eventStatusText">
                	
                </div>
            </div>      

</div>

<div class="weui_cells_title">参与人列表：</div>
<div class="weui_cells">

		<div class="weui_media_box weui_media_appmsg" ng-repeat="partix in partis">
                    <div class="weui_media_hd">
                        <img class="weui_media_appmsg_thumb" ng-src="{{partix.headImgUrl}}" alt="">
                    </div>
                    <div class="weui_media_bd">
                        <h4 class="weui_media_title" ng-bind="partix.nickname">zhang hua sheng</h4>
                        <table style="border:0px;width:100%;border-spacing: 0em 0.5em;">
                        <tr style="margin-top:0.5em;margin-bottom:0.5em;">
                        <td style="text-align:left;width:50%;">
                        <p class="weui_media_desc" style="#font-size:1em;" ng-bind="partix.partiTime"></p>
                        </td>
                        <td style="text-align:left;">
                        <p class="weui_media_desc" style="#font-size:1em;" ng-bind="formatMoney(partix.partiMoney)"></p>
                        </td>
						</tr>
						<!-- <tr>
						<td>
						{{$index}}</td>
						</tr> -->
                        </table>
                       
                        <!-- <p class="weui_media_desc">获得自由分享</p> -->
                    </div>
       </div>
</div>


</div>

<br/>
<br/>
<br/>

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
                  
<div class="sample_div" ng-show="isSample">
</div>

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/eventStatusController.js" ></script>
</html>