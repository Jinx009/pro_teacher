<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>在线问答</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/qaList.css" media="all">   
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/qaListController.js" ></script>
</head>

<body ng-app="zaApp" 
	  ng-controller="qaListController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true" style="background-color:white;">
	<!-- <div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div> -->
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->


<div style="background-color:white;">
<div class="messageBoardTitle" style="height: 5.3em;background-color: #07a09b;">
	<div class="circle_img_div img_frame" style="margin-top: 0.7em;margin-left: 1.28em;display:inline-block;border:0px;background-image: url({{pageData.companyAvator}});vertical-align: middle;">
	</div>
	
	<div class="messageBoard_text_div" style="vertical-align: top;margin-top: 0;margin-left: 5%;color: white;overflow: hidden;width: 60%;vertical-align: middle;">
		<p ng-bind="pageData.pageTitle"></p>
		<!-- <p style="font-size:0.7em;" ng-bind="pageData.companyName"></p>
		<p ng-bind="pageData.additionalDesc"></p> -->
	</div>
</div>
<div 
ng-controller="mainMenuController" 
ng-init="initial()" >
<jsp:include page="main_menu.jsp"></jsp:include>
</div>



            <div class="weui_navbar" style="position:inherit;margin-bottom: 8px;">
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[toggle.currentTab=='public']" ng-click="switchTab('public')">
                    流言榜
                </div>
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[toggle.currentTab=='private']"  ng-click="switchTab('private')">
                    我的
                </div>
                <div class="weui_navbar_item" ng-class="{true:'nb_bar_item_on', flase:'nb_bar_item_off'}[toggle.currentTab=='leaveMessage']"  ng-click="switchTab('leaveMessage')">
                    发言
                </div>
            </div>




<div class="messageBoard_public"  ng-show="toggle.currentTab=='public'" 
style="margin: 0.4em;margin-top:4px;">

	<!-- 一段留言的开始 -->
	<div class="messageBoard_div" ng-repeat="comx in pageData.publicComments"
		style="box-shadow: 0px 0px 8px #a2cb63;background:white;overflow: hidden;"
		ng-click="gotoQaDetail(comx.id)">
		<!-- 用户的留言展示 -->
		<div style="padding-top: 0.2em;">
		
			<div class="message_text_style"
			style="background: white;padding: 0.4em;padding-left: 0.8em;display:block;border:0px;border-bottom: 1px dotted #07a09b;">
			
				<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			
					<div class="img_frame"
						style="background-image: url({{comx.headImgUrl}});display: inline-block;height: 3em;width: 3em;vertical-align: middle;margin-right: 0.8em;">
						<!-- <img src="{{comx.headImgUrl}}" class="circle_img" style="width:3em; height:3em;"> -->
					</div>
			
					<div style="display: inline-block;vertical-align: middle;">
						{{comx.nickname}}
					</div>
				</div>
				<div class="commnet_text_div">
					{{comx.text}}
				</div>
				<div style="margin-top:0.5em;" >
					<div class="img_thum_div img_frame" ng-click="wxViewPhotos(comx.commentPic, $index)" ng-repeat="picx in comx.commentPicThum track by $index"
						style="background-image: url({{picx}});">
					</div>
				</div>
				<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
					{{comx.comTime}}
				</div>
				<div style=" text-align: right;">
					<div class="weui_btn weui_btn_mini weui_btn_default" 
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="deleteTheComment(null, comx.id, $event)"
						ng-show="comx.isCurrentUserCreator"
						>删 除</div>
				</div>
			</div>
		</div>
		
		<div style="font-size: 0.8em;width: 100%;background: aliceblue;color: #afafaf;padding-left: 0.8em;" 
		ng-bind="comx.replyPrompt">
		
		</div>

		<!-- 推荐回复 -->
		<div style="postion:relative;" 
		ng-show="checkRecomComment(comx.recomComment)">
			<div class="message_text_style reply_frame"
				style="margin: 0.25em;width: auto;background-color: rgba(162,203,99,0.2);">
				{{comx.recomComment.nickname+" 回复说: "+comx.recomComment.text}}
				<div style="text-align:right;width:100%;">
					<div class="weui_btn weui_btn_mini weui_btn_default" 
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="deleteTheComment(comx.id, comx.recomComment.id, $event)"
						ng-show="comx.recomComment.isCurrentUserCreator"
						>删 除
						</div>
						<div class="weui_btn weui_btn_mini weui_btn_default" 
						ng-class="{true:'fa-thumbs-up', false:'fa-thumbs-o-up'}[comx.recomComment.isCurrentUserLiked]"
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="checkILikeTheComment(comx.recomComment, $event, comx.id)"
						>({{comx.recomComment.likedNumber}})
						</div>
					</div>
			</div>
			
		</div>	
		<br/>
	</div>
	<!-- 一段留言的结束 -->
</div> <!-- class -messageBoard_public //-->



<div class="messageBoard_private" ng-show="toggle.currentTab=='private'"
style="margin: 0.4em;margin-top:4px;">

<!-- 一段留言的开始 -->
	<div class="messageBoard_div" ng-repeat="comx in pageData.privateComments"
		style="box-shadow: 0px 0px 8px #a2cb63;background:white;overflow: hidden;"
		ng-click="gotoQaDetail(comx.id)">
		<!-- 用户的留言展示 -->
		<div style="padding-top: 0.2em;">
		
			<div class="message_text_style"
			style="background: white;padding: 0.4em;padding-left: 0.8em;display:block;border:0px;border-bottom: 1px dotted #07a09b;">
			
				<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
			
					<div class="img_frame"
						style="background-image: url({{comx.headImgUrl}});display: inline-block;height: 3em;width: 3em;vertical-align: middle;margin-right: 0.8em;">
						<!-- <img src="{{comx.headImgUrl}}" class="circle_img" style="width:3em; height:3em;"> -->
					</div>
			
					<div style="display: inline-block;vertical-align: middle;">
						{{comx.nickname}}
					</div>
				</div>
				<div class="commnet_text_div">
					{{comx.text}}
				</div>
				<div style="margin-top:0.5em;" >
					<div class="img_thum_div img_frame" ng-click="wxViewPhotos(comx.commentPic, $index)" ng-repeat="picx in comx.commentPicThum track by $index"
						style="background-image: url({{picx}});">
					</div>
				</div>
				<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
					{{comx.comTime}}
				</div>
				<div style=" text-align: right;">
					<div class="weui_btn weui_btn_mini weui_btn_default" 
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="deleteTheComment(null, comx.id, $event)"
						ng-show="comx.isCurrentUserCreator"
						>删 除</div>
				</div>
			</div>
		</div>
		
		<div style="font-size: 0.8em;width: 100%;background: aliceblue;color: #afafaf;padding-left: 0.8em;" 
		ng-bind="comx.replyPrompt">
		
		</div>

		<!-- 推荐回复 -->
		<div style="postion:relative;" 
		ng-show="checkRecomComment(comx.recomComment)">
			<div class="message_text_style reply_frame"
				style="margin: 0.25em;width: auto;background-color: rgba(162,203,99,0.2);">
				{{comx.recomComment.nickname+" 回复说: "+comx.recomComment.text}}
				<div style="text-align:right;width:100%;">
					<div class="weui_btn weui_btn_mini weui_btn_default" 
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="deleteTheComment(comx.id, comx.recomComment.id, $event)"
						ng-show="comx.recomComment.isCurrentUserCreator"
						>删 除
						</div>
						<div class="weui_btn weui_btn_mini weui_btn_default" 
						ng-class="{true:'fa-thumbs-up', false:'fa-thumbs-o-up'}[comx.recomComment.isCurrentUserLiked]"
						style="font-size: 0.8em;min-width: 5em;font-family: FontAwesome;"
						ng-click="checkILikeTheComment(comx.recomComment, $event, comx.id)"
						>({{comx.recomComment.likedNumber}})
						</div>
					</div>
			</div>
			
		</div>	
		<br/>
	</div>
	<!-- 一段留言的结束 -->

</div> <!-- class -messageBoard_private //-->


<div class="messageBoard_leavemessage" ng-show="toggle.currentTab=='leaveMessage'">

		<div style="margin-top: 0.5em;border: 4px solid white;">
		<textarea class="user_new_commnets_textarea" placeholder="请留下您的问题，会有专家或其他用户为您解答" ng-model="pageData.myComm.text"
		style="background-color: rgba(162,203,99,0.2);color: #07a09b;padding: 1.5%;width: 97%;"></textarea>
		
		</div>

		<div style=" margin: 4px;margin-top:0.5em;background:#afafaf; display: -webkit-box; background-color: rgba(162,203,99,0.2);">
		<!-- 这里要插入图片的 -->
			<ul style="display:inline-table;">
				<li class="img_frame" ng-repeat="myPicX in pageData.myComm.myPics track by $index"
					style="background-image: url({{myPicX}});height: 2.8em;width: 2.8em;vertical-align: middle;display: inline-block;margin:0.4em;border: 1px solid #07a09b;" 
					ng-click="removeThePicInList(pageData.myComm.myPics, $index)">
				</li>
			
				<li class="weui_uploader_input_wrp" 
					style="vertical-align: middle;border: 1px solid #07a09b;margin: 0.4em;height: 2.8em;width: 2.8em;display: inline-block;float: none;"
					ng-click="chooseNewPic()">
					<div class="weui_uploader_input"></div>
	            </li>
            </ul>
		
		</div>
		
		<div style="margin: 0.3em;margin-top: 1em;background: none;" 
			ng-click="startUploadNewPicToWx(0)">
		
			<div class="weui_btn weui_btn_mini weui_btn_default" 
				style="background: rgba(162,203,99,0.2);width: 100%;min-height: 3em;line-height: 3em;border: 4px solid white;box-shadow: 0px 0px 8px #07a09b;color: #07a09b;">
				确认提交
			</div>
		
		</div>
		<br/>
		
		
		

		

</div> <!-- class -messageBoard_leavemessage //-->

</div>
		<br/>
		<br/>
		<br/>


<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
<jsp:include page="_include_components.jsp"></jsp:include>
</body>
<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->


    

</html>

