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
	
	<link rel="stylesheet" type="text/css" href="./sp/css/front/eventList.css" media="all">   
	<link rel="stylesheet" type="text/css" href="./sp/css/front/nb.css" media="all">   

<style type="text/css">
.msg_list_bd{
    -webkit-box-shadow: 0px 0px 1.5em rgba(28, 39, 5, 0.44);
    box-shadow: 0px 0px 1.5em rgba(28, 39, 5, 0.44);
    -moz-border-radius: 0.38em;
    -webkit-border-radius: 0.38em;
    border-radius: 0.38em;
}

.msg_list{
	margin-bottom:1.5em;
}

</style>        		  
	</head>
	<body ng-app="zaApp" ng-controller="eventListController" ng-init="initial()">
	
	<div class="msg_page" id="msg_page">  
	
	<div ng-repeat="theEventsX in allEvents">
	
<!-- 一个推荐event的开始 -->
	<div class="msg_list" ng-repeat="eventx in theEventsX">
	<div class="msg_list_hd">
			<h3 class="msg_list_date" ng-bind="eventx.eventTime"></h3>
		</div>
		<div class="msg_list_bd">
		<div id="WXAPPMSG401880160" class="msg_wrapper" msgid="401880160" ng-click="gotoEventDetail(eventx.eventId)">
				<p class="msg_date" ng-bind="eventx.eventTime"></p>
  				<div class="msg_inner_wrapper default_box news_box">
	    			<a class="msg_item news redirect">
	     				<div class="msg_item_hd">
							<h4 class="msg_title" ng-bind="eventx.title"></h4>
						</div>
						<div class="msg_item_bd">
	
							<div class="msg_cover">

						<div class="event_mask mask_div" ng-show="eventx.isRecom">
						<img src="./sp/img/hot.png" style="width: 90px; float: right;"/>
						</div>
						<div class="event_mask mask_div" ng-show="eventx.isSample">
						<img width="50px" src="./sp/img/sample-stamp.png"/> 
						</div>
								<img alt="" ng-src="{{eventx.picUrl}}"/>
							</div>
							<div class="msg_desc_sector" ng-bind="eventx.desc">
							</div>
						</div>
						<div class="msg_item_ft">
							<table class="breifing_table">
								<tbody>
								<tr class="breifing_table tr_top_line" ng-show="eventx.participaterProgress != 'n/a'">
									
									<td class="breifing_table td_2"><img src="./sp/img/peopleicon.png">
									</td>
									<td class="breifing_table td_3">获捧：<div style="display:inline-block" ng-bind="eventx.participater"></div>人
									</td>
									<td class="breifing_table td_4">进度：<div style="display:inline-block" ng-bind="eventx.participaterProgress"></div>%
									</td>
									
								</tr>
								
								<tr class="breifing_table tr_top_line" ng-show="eventx.moneyProgress != 'n/a'">
									
									<td class="breifing_table td_2"><img src="./sp/img/moneyicon.png">
									</td>
									<td class="breifing_table td_3">获捧：<div style="display:inline-block" ng-bind="eventx.moneyAmount"></div>元 
									</td>
									<td class="breifing_table td_4">进度：<div style="display:inline-block" ng-bind="eventx.moneyProgress"></div>%
									</td>
								</tr>
								<tr class="breifing_table tr_bottom_line tr_top_line">
									
									<td class="breifing_table td_2"><img src="./sp/img/timeicon.png">
									</td>
									<td class="breifing_table td_3" colspan="2">剩余时间： <div style="display:inline-block" ng-bind="eventx.leftTime"></div>
									</td>
									
								</tr>
								
							</tbody></table>
						</div>
					</a>
				</div>
			</div>
			</div>
			</div>
			
<!-- 一个event的结束 -->	
</div>
	

	
	</div>

<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/eventListController.js" ></script>
    

</html>

