<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>主页</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/shareList.css" media="all">  
	
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/shareListController.js" ></script> 
</head>

<body ng-app="zaApp" 
	  ng-controller="shareListController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true">
	<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->
	<div style="position: absolute;width: 100%;"
		ng-show="toggle.isShowingMainContent">
		<div class="wrapSearchBar" style="position: relative;width: 100%;height: 2.7em;margin-top: -0.35em;">
		<div style="position: absolute;width: 100%;">
			<div class="weui_search_bar weui_search_focusing" id="search_bar">
				<form class="weui_search_outer">
				<div class="weui_search_inner" >
					<i class="weui_icon_search"></i>
					<input type="search" class="weui_search_input"  placeholder="搜索" required="" 
						ng-model="pageData.searchKeyWord"
						ng-change="searchRealtime()"
						ng-keypress="checkKeyPress($event)">
					<a href="javascript:" class="weui_icon_clear" ng-click="clearSearchKeyword()"></a>
            	</div>
            	<label for="search_input" class="weui_search_text" >
            		<i class="weui_icon_search"></i>
            		<span>搜索</span>
            	</label>
        		</form>
        		<a href="javascript:" class="weui_search_cancel fa-filter"
        			style="color:#07A09B;font-family: FontAwesome;"
        			ng-click="showTagSelectionPage()"> 
        			筛选
       			</a>
  			</div>
  			<div class="weui_cells weui_cells_access search_show" 
  				style="display: block;z-index: 1;"
  				ng-show="pageData.searchRealTimeAdvice.length > 0">
        		<div class="weui_cell" ng-repeat="adX in pageData.searchRealTimeAdvice track by $index">
        			<div class="weui_cell_bd weui_cell_primary" 
        				ng-click="toggleRealtimeAdv($index)">
        				<p ng-bind="adX"></p>
            		</div>
        		</div>
    		</div>
    	</div>
		</div>   
		<div 
		ng-controller="mainMenuController" 
		ng-init="initial()" >
		<jsp:include page="main_menu.jsp"></jsp:include>
		</div> 
		<!-- <div style="width:100%;margin-top:3.3em;"></div> -->
		<!-- 一个推荐event的开始 -->
		<div class="msg_list_bd" ng-repeat="bookX in pageData.books">
			<div class="msg_wrapper" ng-click="gotoBookDetail(bookX.id)">
				<p class="msg_date" ng-bind="bookX.time"></p>
  				<div class="msg_inner_wrapper default_box news_box">
	    			<a class="msg_item news redirect">
	     				<div class="msg_item_hd">
							<h4 class="msg_title" style="color:#0EA393;" ng-bind="'《'+bookX.title+'》'"></h4>
						</div>
						<div class="msg_item_bd">
							<div>
								<div class="msg_cover"style="width:40%;display: inline-block;vertical-align: top;border: 1px dotted #07a09b;">
									<div class="event_mask mask_div" ng-show="bookX.isRecom">
										<img src="./sp/img/yuexiang/hot.png" style="width: 10vw; float: right;"/>
									</div>
									<div class="event_mask mask_div" ng-show="eventx.isSample">
										<img width="50px" src="./sp/img/yuexiang/sample-stamp.png"/> 
									</div>
									<div class="img_frame contain" style="background-image:url({{bookX.imgUrl}});height: 100%;width: 100%;"></div>
								</div>
								<div style="display: -webkit-inline-box;text-overflow: ellipsis;overflow: hidden;-webkit-box-orient: vertical;-webkit-line-clamp: 7;vertical-align: top;margin-left: 0.8em;max-width: 52%;overflow-wrap: break-word;word-break: break-all;color:#0EA393;"
									ng-bind="bookX.introduce">
								</div>
							</div>
							<div class="msg_desc_sector" style="max-height: none;margin: 0.4em;line-height: 2.5em;">
								<ul class="weui_uploader_files">
									<li class="tag_div" ng-repeat="tagX in bookX.tags track by $index" ng-bind="tagX" style="font-size: 0.9em;background: #07a09b;color: white;">  </li>
						    	</ul>
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="weui_cells_title" style="text-align: center;" 
			ng-click="loadMoreBooks()" 
			ng-show="pageData.books.length < pageData.totalNumber">
			加载更多内容
		</div>
		<br/>
		<br/>
		<br/>
<!-- 一个event的结束 -->	
	</div>



<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
	<!-- 临时加一个子页面 -->
	<div class="page slideIn" ng-show="toggle.isShowingTagSelectionPage"
		style="padding: 1em;padding-top: 2em;">
	<div style="background-color: #07a09b;height: 2.5em;width: 100%;position: fixed;top: 0;margin-left: -1em;font-family: FontAwesome;line-height: 2.5em;padding-left: 1em;color:white;">
		<font class="fa-chevron-left" ng-click="showMainContent()"> 返回</font>
	</div>
		<div class="hd" style="padding:none;">
    		<h1 class="page_title"style="font-size:1.7em;color:#0EA393;margin:0;">
    			选择您感兴趣的标签
    		</h1>
		</div>
		
 		<div class="bd" ng-repeat="tagxX in pageData.allTags">
	 		<div class="weui_cells_title" ng-bind="tagxX.catalog"></div>
     		<div class="weui_cells">
        		<div class="weui_cell">
    				<ul class="weui_uploader_files" ng-init="p_index=$index"">
    					<li class="tag_div" 
    						ng-repeat="tagX in tagxX.tags"
    		 				ng-bind="tagX.text"
    						ng-click="tagSelect(p_index, $index)"
    						ng-class="{true:'checked'}[tagX.isSelected]"
    						style="font-size: 0.9em;"> 
   						</li>
    				</ul>
    			</div>
    		</div>
    	</div>
    
    	<div class="submit_button_frame" >
			<div class="weui_btn weui_btn_plain_primary" 
				style="font-size: 0.8em;width: 40%;float: left;margin-left: 1em;margin-top: 1em;color:#0EA393;border:1px solid #0EA393;"
				ng-click="reloadWithFilter();showMainContent()">
				确 定
			</div>

			<div class="weui_btn weui_btn_plain_default" 
				style="font-size: 0.8em;width: 40%;float: right;margin-right: 1em;margin-top: 1em;"
				ng-click="showMainContent()">
				取 消
			</div>
		</div>
    <br/>
    <br/>
    <br/>
    <br/>
    
	</div>
<!-- 临时加一个子页面结束 -->
<jsp:include page="_include_components.jsp"></jsp:include>
</body>

<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->

    

</html>

