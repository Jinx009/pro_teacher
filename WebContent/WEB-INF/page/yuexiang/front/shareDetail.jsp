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
	<link rel="stylesheet" type="text/css" href="./sp/css/yuexiang/front/shareDetail.css" media="all">   
	
	<jsp:include page="_include_js.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/yuexiang/front/shareDetailController.js" ></script>
</head>

<body ng-app="zaApp" 
	  ng-controller="shareDetailController" 
	  ng-init="initial()" 
	  style="color: #5f3f3f;font-family: 'FontAwesome';">

<div class="page slideIn" ng-show="isBodyReady==false">
	<div class="img_frame" style="background-image:url(./sp/img/yuexiang/logolong.jpg);height: 3.5em;background-size: cover;margin-bottom: 0.4em;"></div>
	<div 
	ng-controller="mainMenuController" 
	ng-init="initial()" >
	<jsp:include page="main_menu.jsp"></jsp:include>
	</div>
<!----------------------------- ↑↑↑↑固定段，不要更改（除title字段外）↑↑↑↑↑ -->


	<div class="bd">
    	<div class="weui_cells" style="margin-top:10px;padding: 0.5em;">
    		<div class="cover_frame img_frame contain" style="background-image: url({{pageData.coverImgUrl}});border: 1px dotted #07a09b;">
    			<div class="fa-play" style="width: 100%;height: 100%;text-align: center;font-size: 3em;line-height: 3em;color: white;text-shadow: 0px 0px 1em black;"
    			ng-show="pageData.verbalUrl.length > 5"></div>
    		</div>
			<div style="float: right;width: 60%;">
				<table class="cover_table" style="color: #07a09b;">
					<tr><td colspan=2 align="center" style="border-bottom: 2px solid #07a09b;font-size: 1.4em;" ng-bind="pageData.bookName"></td></tr>
					<tr><td class="cover_item_left">作者:</td><td class="cover_item_right" ng-bind="pageData.writer"></td></tr>
					<tr><td class="cover_item_left">出版社:</td><td class="cover_item_right" ng-bind="pageData.publisher"></td></tr>
					<tr><td class="cover_item_left">图书编号:</td><td class="cover_item_right" ng-bind="pageData.bookCode"></td></tr>
				</table>
			</div>
		</div>
	</div>
    
	<div class="bd">
		<div class="weui_cells_title">图书标签</div>
		<div class="weui_cells">
			<div class="weui_cell">
				<ul class="weui_uploader_files">
					<li class="tag_div" ng-repeat="tagX in pageData.tags" ng-bind="tagX.tag" style="background: #07a09b;color: white;"> </li>
				</ul>
			</div>
		</div>
	</div>
    
    <div class="bd">
		<div class="weui_cells_title">简介及推荐</div>
		<div class="weui_cells">
			<div class="weui_cell">
            	<div class="weui_navbar" style="position:inherit">
                	<div class="weui_navbar_item" ng-class="{true:'active'}[toggle.tabShowing==1]" ng-click="toggleTabShowing(1)" style="color:#07a09b;">
                    	图书简介
                	</div>
                	<div class="weui_navbar_item" ng-class="{true:'active'}[toggle.tabShowing==2]" ng-click="toggleTabShowing(2)" style="color:#07a09b;">
                    	最佳读后感
                	</div>
            	</div>
        	</div>
        
        	<!-- 图书介绍的tab内容 -->
			<div style="margin:0.2em;" ng-show="toggle.tabShowing==1">
            	<div class="book_introduce" ng-class="{true:'collapse'}[toggle.bookIntroduceCollapse]" ng-bind="pageData.bookIntroduce">
            </div>
            <div class="draw_for_more" ng-click="toggleCollapse('bookIntroduceCollapse', $event)" > {{toggle.bookIntroduceCollapse?'︾':'︽'}} </div>
			</div><!-- 图书介绍的tab内容-结束 -->
		
			<!-- 读者推荐的tab内容 -->
			<div style="margin:0.2em;" ng-show="toggle.tabShowing==2">
            	<div style="overflow:hidden;display: -webkit-box;text-overflow: ellipsis;overflow: hidden;-webkit-box-orient: vertical;">
					<div class="messageBoard_div">
						<!-- 用户的留言展示 -->
						<div class="message_text_style" style="margin-left:0em;display:block;"
								ng-click="gotoShareCommentDetail(pageData.recomm.id)">
							<div class="img_frame" style="display: inline-block;position: relative;background-image: url({{pageData.recomm.headImgUrl}});height: 2em;width: 2em;margin-top:0.5em;vertical-align: top;"></div>
							<div class="messager_nickname" style="display: inline-block;height: 2em;line-height: 2em;position: relative;margin-top:0.5em;vertical-align: top;" 
								ng-bind="pageData.recomm.nickname"></div>
							<div  class="commnet_text_div" style="max-height:none;-webkit-line-clamp:3;"
								ng-bind="pageData.recomm.text"
								
								></div>
						<!-- 	<div class="draw_for_more" ng-click="toggleCollapse('recommCollapse', $event)" > {{toggle.recommCollapse?'︾':'︽'}} </div> -->
							<div class="commnet_text_div" style="margin-top:0.5em;max-height:none;-webkit-line-clamp:none;">
								<ul class="weui_uploader_files" >
			    					<li class="weui_uploader_file" ng-repeat="picX in pageData.recomm.commentPicThum track by $index" 
			    					style="background-image:url({{picX}});border:1px dotted #858585;" 
			    						ng-click="wxViewPhotos(pageData.recomm.commentPic, $index)"></li>
			    				</ul>
							</div>
							<div style="font-size:0.8em;margin-top:0.6em;color:#aaa"
								ng-bind="pageData.recomm.comTime"></div>
						</div>
					</div>
            	</div>  
			</div><!-- 读者推荐的tab内容-结束 -->
   		</div>
    </div>
    
	<div class="bd">
		<div class="weui_cells_title">发表您的读后感 <div ng-bind="toggle.myComm_textSizeInfo" style="display: inline-block;float: right;"></div></div>
		<div class="weui_cells">
			<div class="weui_cell">
				<div class="leave_message_frame" style="width: 100%;">
					<textarea class="user_new_commnets_textarea" placeholder="请分享阅读后的感想" ng-model="pageData.myComm.text" ng-change="onMyCommTextChange()"></textarea>
				</div>
			</div>
			<div><!-- 这里要插入图片的 -->
				<div class="img_frame" ng-repeat="myPicX in pageData.myComm.myPics track by $index"
						style="background-image: url({{myPicX}});height: 2.8em;width: 2.8em;vertical-align: middle;display: inline-block;margin-left:0.4em;" 
						ng-click="removeThePicInList(pageData.myComm.myPics, $index)">
				</div>
				<div class="weui_uploader_input_wrp" 
					style="vertical-align: middle;"
					ng-click="chooseNewPic()">
					<div class="weui_uploader_input" ></div>
             	</div>
			</div>
			<div class="submit_button_frame" >
				<div class="weui_btn weui_btn_plain_primary" style="font-size: 0.8em;"
						ng-click="startUploadNewPicToWx(0)">提交</div>
		
			</div>
    	</div>
    </div>
    
    
	<div class="bd">
		<div class="weui_cells_title">读者推荐</div>
	  	<!-- 一段留言的开始 -->
		<div class="weui_cells" style="background: white;padding-bottom: 0.5em;margin:0.2em;box-shadow: 0px 0px 8px #07a09b;margin-bottom: 0.8em;"
     			ng-repeat="commentX in pageData.comments">
   			<div class="weui_cell" ng-click="gotoShareCommentDetail(commentX.id)">
				<div class="messageBoard_div">
					<!-- 用户的留言展示 -->
					<div>
						<div class="img_frame" style="background-image:url({{commentX.headImgUrl}});width: 2.8em;height: 2.8em;vertical-align: top;border-radius: 0.5em;display: inline-block;position:absolute;"></div>				
						<div class="message_text_style" style="vertical-align: top;display: block;border: 0;border-bottom: 1px dotted #07a09b;">
							<div class="messager_nickname" ng-bind="commentX.nickname"></div>
							<div class="commnet_text_div" ng-bind="commentX.text" style="-webkit-line-clamp:3;"></div>
							<div style="margin-top:0.5em;" >
								<div class="img_frame"
										ng-repeat="picX in commentX.commentPicThum track by $index"
										style="background-image:url({{picX}});height:2.8em;width:2.8em;display: inline-block;margin:0.3em;"
										ng-click="wxViewPhotos(commentX.commentPic,$index)">
								</div>
							</div>
							<div>
								<div style="font-size:0.8em;margin-top:0.6em;color:#aaa" ng-bind="commentX.comTime"></div>
							</div>
			
							<div style="text-align: right;">
								<div class="weui_btn weui_btn_mini weui_btn_default" style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;"
										ng-show="commentX.isCurrentUserCreator" 
										ng-click="deleteTheComment(commentX.id, $index, $event)">删 除</div>
								<div class="weui_btn weui_btn_mini weui_btn_default ng-binding fa-thumbs-o-up" 
										ng-class="{true:'fa-thumbs-up', false:'fa-thumbs-o-up'}[commentX.isCurrentUserLiked]" 
										style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;" 
										ng-click="checkILikeTheComment($index, $event)">({{commentX.likedNumber}})</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	
			<!-- 项目主的反馈 -->
			<div style="postion:relative;" ng-show="checkIfHaveRecomComment(commentX.recomComment)">
				<div class="message_text_style reply_frame" style="background-color: rgba(7,160,155,0.1);border: 0;">
					{{commentX.recomComment.nickname+" 回复说: "+commentX.recomComment.text}}
					<!-- <div style="text-align: right;">
						<div class="weui_btn weui_btn_mini weui_btn_default" 
								style="font-size: 0.8em;min-width: 5em;margin-right: 0.8em;margin-bottom: 0.4em;vertical-align: bottom;" 
								ng-click="deleteTheComment($index, $event)">删 除</div>
					</div> -->
				</div>
			</div>
			<div ng-show="toggle.isShowCommentForm==$index">
				<div class="weui_cell">
					<textarea class="user_new_commnets_textarea" placeholder="" ng-model="commentX.newCommentText"
								style="border: 1px solid #afafaf;height: 4em;"></textarea>
    			</div>
	    		<div class="weui_cell">
	    			<a href="javascript:;" class="weui_btn weui_btn_plain_default" style="font-size: 0.7em;width: 100vw;"
	    				ng-click="submitNewLevel2Comment($index)">提交</a>
				</div>
			</div>
		</div>
	<!-- 一段留言的结束 -->
	
<%-- 		<div class="weui_cells_title" style="text-align: center;"
				ng-click="loadMoreComments()"
				ng-show="pageData.comments.length < (pageData.commentsTotalNumber-1)">
				加载更多评论
		</div> --%>
	</div>
	
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

