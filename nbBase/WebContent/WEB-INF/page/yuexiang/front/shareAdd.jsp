<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>我要分享</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/shareAdd.css" media="all">
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/shareAddController.js" ></script>
</head>

<body ng-app="zaApp" 
	  ng-controller="shareAddController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="!isBodyReady">
	<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>
	
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->


	<div class="hd" style="padding: 0.01em;background: #07A09B;color: white; margin-bottom:0.2em;">
    	<h1 class="page_title fa-book" style="font-size: 1.3em;color: white;font-family: FontAwesome;"> 
    		新建分享
    	</h1>
	</div>
	<div 
	ng-controller="mainMenuController" 
	ng-init="initial()" >
	<jsp:include page="main_menu.jsp"></jsp:include>
	</div>
	<!-- <div class="hd" style="padding: 0.01em;background: #CDD754;color: white;">
		<div style="text-align: center;color: red;">
			找到（5）本可能是同名的图书信息 &nbsp
		</div>
	</div> -->
	<div class="bd">
		<div style="width: 100%;height: 1.2em;line-height: 1em;text-align: center;color: #07a09b;cursor: pointer;text-decoration: underline;" ng-bind="pageData.promptMessage" class="ng-binding"
		ng-click="gotoShareListWithSearchWord()"></div>
		<div class="weui_cells" style="margin-top:0px;padding: 0.5em;">
			<div class="cover_frame img_frame" style="background-image: url({{pageData.coverImgUrl}});"
    			ng-click="chooseNewCover(1)">
    			<div style="width: 100%;text-align: center;position: absolute;bottom: 0em;background: rgba(20, 166, 145,0.77);color: white;"
    			ng-hide="pageData.coverImgUrl.length > 5 && pageData.coverImgUrl != 'http://pc.0angle.com/sp/img/yuexiang/add_icon.jpg'">
					上传封面图
				</div>
    		</div>
			<div style="float: right;width: 60%;">
				<table class="cover_table">
					<tr>
						<td colspan=2 align="center" style="border-bottom: 2px solid #07A09B;font-size: 1.4em;" >
							<div ng-bind="pageData.bookName" ng-show="toggle.isEditing!='BookName'"
								ng-click="toggleEdit('BookName', pageData.bookName)">
							</div>
							<div>
								<input type="text" style="border: 1px solid #07A09B;width: 100%;border-radius: 0.2em;font-size: 1em;"
										ng-show="toggle.isEditing=='BookName'"
										ng-blur="pageData.bookName = toggleEdit('BookName', pageData.bookName)"
										ng-model="pageData.bookName"
										ng-change="checkLength('bookName', 50);realtimeSearchBookName(pageData.bookName)"
										ng-focus="selectTextOnFoucs($event, pageData, 'bookName')">
								<div class="weui_cells weui_cells_access search_show" style="display: block;z-index: 1;position: absolute;width: 52vw;border: 1px solid #07A09B;border-radius: 0.3em;"
				    					ng-show="pageData.bookNameRecomSearchList.length > 0">
				        			<div class="weui_cell noleft" ng-repeat="bookX in pageData.bookNameRecomSearchList"
				        				ng-click="loadRecomByIndex($index)">
				            			<div class="weui_cell_bd weui_cell_primary" style="text-align:left;">
				                			<p ng-bind="bookX.bookName"></p>
				            			</div>
				        			</div>
				    			</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="cover_item_left">作者:</td>
						<td class="cover_item_right">
							<div 
								ng-bind="pageData.writer" 
								ng-show="toggle.isEditing!='Writer'"
								ng-click="toggleEdit('Writer',pageData.writer)">
							</div>
							<div>
								<input type="text" style="border: 1px solid #07A09B;width: 100%;border-radius: 0.2em;font-size: 1em;"
									ng-show="toggle.isEditing=='Writer'"
									ng-blur="pageData.writer = toggleEdit('Writer',pageData.writer)"
									ng-model="pageData.writer"
									ng-focus="selectTextOnFoucs($event, pageData, 'writer')"
									ng-change="checkLength('writer', 50)">
							</div>
						</td>
					</tr>
					<tr>
						<td class="cover_item_left">出版社:</td>
						<td class="cover_item_right" >
							<div 
								ng-bind="pageData.publisher"
								ng-show="toggle.isEditing!='Publisher'"
								ng-click="toggleEdit('Publisher',pageData.publisher)">
							</div>
							<div>
								<input type="text"
									style="border: 1px solid #07A09B;width: 100%;border-radius: 0.2em;font-size: 1em;"
									ng-show="toggle.isEditing=='Publisher'"
									ng-blur="pageData.publisher = toggleEdit('Publisher',pageData.publisher)"
									ng-model="pageData.publisher"
									ng-focus="selectTextOnFoucs($event, pageData, 'publisher')"
									ng-change="checkLength('publisher', 50)">
							</div>
						</td>
					</tr>
					<tr>
						<td class="cover_item_left">图书编号:</td>
						<td class="cover_item_right" >
							<div 
								ng-bind="pageData.bookCode"
								ng-show="toggle.isEditing!='BookCode'"
								ng-click="toggleEdit('BookCode',pageData.bookCode)"
								>
							</div>
							<div>
								<input type="text"
									style="border: 1px solid #07A09B;width: 100%;border-radius: 0.2em;font-size: 1em;"
									ng-show="toggle.isEditing=='BookCode'"
									ng-blur="pageData.bookCode = toggleEdit('BookCode',pageData.bookCode)"
									ng-model="pageData.bookCode"
									ng-focus="selectTextOnFoucs($event, pageData, 'bookCode')"
									ng-change="checkLength('bookCode', 50)">
							</div>
						</td>
					</tr>
				</table>
			</div>
    	</div>
    </div>
    
    <div class="bd">
    	<div class="weui_cells_title">内容简介-您所提交的内容将成为重要的信息参考</div>
    	<div class="weui_cells">
        	<!-- 图书介绍的tab内容 -->
			<div style="margin:0.2em;">
            	<textarea class="book_introduce"  placeholder="请填写该书的内容简介"
            				ng-model="pageData.bookIntroduce"
            				style="width: 100%;height: 8em;border: 1px solid #07A09B;">
				</textarea>
			</div>
			<!-- 图书介绍的tab内容-结束 -->
		</div>
	</div>
	
	
	<div class="bd">
		<div class="weui_cells_title">读后感 <div ng-bind="toggle.myComm_textSizeInfo" style="display: inline-block;float: right;"></div></div>
		<div class="weui_cells"
			style="padding-bottom: 0.5em;">    		
  				<div class="weui_cell">
				<div class="leave_message_frame" style="width: 100%;">
					<textarea class="user_new_commnets_textarea" placeholder="请分享阅读后的感想" ng-model="pageData.myComm.text" ng-change="onMyCommTextChange()"></textarea>
				</div>
			</div>
			<div style="padding-left:0.4em;">
				<!-- 这里要插入图片的 -->
				<ul style="display:inline-table;">
				<li class="img_frame" ng-repeat="myPicX in pageData.myComm.myPics track by $index"
					style="background-image: url({{myPicX}});height: 2.8em;width: 2.8em;vertical-align: middle;display: inline-block;margin-right:0.4em;border: 1px solid #07A09B;margin-top: 0.5em;" 
					ng-click="removeThePicInList(pageData.myComm.myPics, $index)">
				</li>
				
				<li class="weui_uploader_input_wrp" 
					style="height: 2.8em;width: 2.8em;vertical-align: middle;display: inline-block;margin: 0;border: 1px solid #07A09B;margin-top: 0.5em;font-size: 1em;"
					ng-click="chooseNewPic()">
					
            	</li>
            	</ul>
			</div>
  			</div>
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
    		style="font-size: 0.8em;width: 40%;float: left;margin-left: 1em;margin-top: 1em;"
			ng-click="startUpload()">
			提 交
		</div>
			
		<div class="weui_btn weui_btn_plain_default" 
			style="font-size: 0.8em;width: 40%;float: right;margin-right: 1em;margin-top: 1em; color: #afafaf;"
			ng-click="gotoShareList()">
			放 弃
		</div>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>


<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
<jsp:include page="_include_components.jsp"></jsp:include>
</body>

<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->
    

</html>

