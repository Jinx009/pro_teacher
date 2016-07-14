<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
  <title>内容详情</title>
  
	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<jsp:include page="_include_css.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/shareCommentDetail.css" media="all">   
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/shareCommentDetailController.js" ></script>
	<!-- <link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/eventList.css" media="all">    -->
</head>

<body ng-app="zaApp" 
	  ng-controller="shareCommentDetailController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;">

<div class="page slideIn" ng-show="true">
	<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->


	<div class="bd">
    	<div class="weui_cells" style="margin-top:10px;padding: 0.5em;height:42vw;">
    		<div class="cover_frame img_frame contain" style="background-image: url({{pageData.coverImgUrl}});border: 1px dotted #07a09b;"></div>
			<div style="float: right;width: 60%;height: 9.2em;">
				<table class="cover_table" style="color: #07a09b;">
					<tr><td colspan=2 align="center" style="border-bottom: 2px solid #07a09b;font-size: 1.4em;" ng-bind="pageData.bookName"></td></tr>
					<tr><td class="cover_item_left">作者:</td><td class="cover_item_right" ng-bind="pageData.writer"></td></tr>
					<tr><td class="cover_item_left">出版社:</td><td class="cover_item_right" ng-bind="pageData.publisher"></td></tr>
					<tr><td class="cover_item_left">图书编号:</td><td class="cover_item_right" ng-bind="pageData.bookCode"></td></tr>
					<td colspan="2" align="center" style="font-size: 0.8em;text-decoration: underline;color: blue;" ng-click="gotoShareDetail()">回 到 图 书 详 情 页</td>
				</table>
			</div>
		</div>
	</div>


	<div class="messageBoard_public" style="margin: 0.4em;margin-top:4px;">

		<!-- 一段留言的开始 -->
		<div class="messageBoard_div" style="box-shadow: 0px 0px 8px #07a09b;background:white;">

			<!-- 用户的留言展示 -->
			<div>
				<div class="message_text_style" style="background: white;padding: 0.4em;padding-left: 0.8em;display: block;border: 0px;border-bottom: 2px dotted #07a09b;">
					<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
						<div class="img_frame" style="background-image: url({{pageData.comment.headImgUrl}});display: inline-block;height: 3em;width: 3em;vertical-align: middle;margin-right: 0.8em;">
						</div>		
						<div style="display: inline-block;vertical-align: middle;">
							{{pageData.comment.nickname}}
						</div>
					</div>
					<div class="commnet_text_div">
						{{pageData.comment.text}}
					</div>
					<div style="margin-top:0.5em;" >
						<div class="img_thum_div img_frame" ng-click="wxViewPhotos(pageData.comment.commentPic, $index)" 
								ng-repeat="picx in pageData.comment.commentPicThum track by $index"
								style="background-image: url({{picx}});">
						</div>
					</div>
					<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
						{{pageData.comment.comTime}}
					</div>
					<div style="text-align: right;" ng-show="pageData.comment.isCurrentUserCreator">
						<div class="weui_btn weui_btn_mini weui_btn_default" style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;" 
								ng-click="deleteTheComment(null, pageData.commentId)"
								ng-show="pageData.comment.isCurrentUserCreator">删 除</div>
					</div>
				</div>
			</div>

			<!-- 点赞和 -->
			<div style="padding: 0.2em;text-align: right;">
				<div class="weui_btn weui_btn_mini weui_btn_default" 
						ng-class="{true:'fa-thumbs-up', false:'fa-thumbs-o-up'}[pageData.comment.isCurrentUserLiked]"
						style="font-size: 0.8em;min-width: 5em;font-family:'FontAwesome';"
						ng-click="checkILikeTheComment(pageData.comment)">
							({{pageData.comment.likedNumber}})
				</div>
				<div class="weui_btn weui_btn_mini weui_btn_default" style="font-size: 0.8em;min-width: 5em;" 
					ng-click="toggle.isShowCommentForm = ! toggle.isShowCommentForm">
					{{toggle.isShowCommentForm?'取消':'点评'}}
				</div>
			</div>

			<div ng-show="toggle.isShowCommentForm">
				<div class="weui_cell">
					<textarea class="user_new_commnets_textarea" placeholder="" ng-model="pageData.newCommentText"
							style="border: 1px solid #07a09b;height: 4em;background-color: rgba(7, 160, 155, 0.1);color:black;"></textarea>
	    		</div>
	    		<div class="weui_cell">
	    			<a href="javascript:;" class="weui_btn weui_btn_plain_default" style="font-size: 0.7em;width: 100vw;color: #07a09b; border: 1px solid #07a09b;"
	    				ng-click="submitNewComment()">
	    				提交
	    			</a>
				</div>
			</div>
			<!-- 推荐回复 -->
			<div style="postion:relative;" ng-show="toggle.ifHaveRecomComm">
				<div class="message_text_style reply_frame"
					style="margin: 0.25em;width: auto;background-color: rgba(7, 160, 155, 0.1);border: 0;">
					{{pageData.comment.recomComment.nickname+" 回复说: "+pageData.comment.recomComment.text}}
					<div style="text-align: right;">
						<div class="weui_btn weui_btn_mini weui_btn_default" style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;" 
						ng-click="deleteTheComment(pageData.commentId, pageData.comment.recomComment.id)"
						ng-show="pageData.comment.recomComment.isCurrentUserCreator">删 除</div>
					</div>
				</div>
			</div>
			<br/>
	
			<!-- 其他2级留言 -->	
			<div style="postion:relative;" ng-repeat="subCommentX in pageData.comment.child">
				<div class="message_text_style reply_frame" style="margin: 0.25em;width: auto;background-color: rgba(7, 160, 155, 0.1);border: 0;">
					{{subCommentX.nickname+" 说: "+subCommentX.text}}
					<div style="text-align: right;">
						<div class="weui_btn weui_btn_mini weui_btn_default" style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;" 
						ng-click="deleteTheComment(pageData.commentId, subCommentX.id, $event)"
						ng-show="subCommentX.isCurrentUserCreator">删 除</div>
					</div>
				</div>
			</div>
			<br/>
		</div>
	<!-- 一段留言的结束 -->
	<br/>
	<br/>
	</div> <!-- class -messageBoard_public //-->


<!---------------------- ↓↓↓↓↓固定段，不要更改↓↓↓↓ -->
</div>
<jsp:include page="_include_components.jsp"></jsp:include>
</body>
<!-------------------- ↑↑↑↑固定段，不要更改↑↑↑↑↑ -->

   
</html>

