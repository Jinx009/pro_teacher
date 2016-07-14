<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理</title>

   <jsp:include page="./_include_css.jsp"></jsp:include>

</head>

<body ng-app="zaApp">

    <div id="wrapper" >
    
        <jsp:include page="./navigator.jsp"></jsp:include>
        <div id="page-wrapper" 
        ng-controller="manageCommentsController"  ng-init="initial()">
        <!-- Page Content -->
        <!-- 这条线以上不要加页面代码 -->
        <!-- --------------------------------------------------------------- -->
        
        <br/>
        

					
					<div class="messageBoard_public" 
					style="margin: 0.4em;margin-top:4px;"
					ng-repeat="commX in pageData.comments"
					>
					
					<!-- 一段留言的开始 -->
						<div class="messageBoard_div""
						style="box-shadow: 0px 0px 8px #0f0f0f;background:white;padding: 1em;">
					
					<!-- 用户的留言展示 -->
							<div>
								<div class="message_text_style"
								style="background: white;padding: 0.4em;padding-left: 0.8em;">
							
									<div style="color:#b0b0b0;font-size:1.2em;margin-left:0.3em;margin-top:0.3em;margin-bottom:0.5em;">
							
										<div class="img_frame"
										style="background-image: url({{commX.headImgUrl}});display: inline-block;height: 3em;width: 3em;vertical-align: middle;margin-right: 0.8em;"></div>
							
										<div
										style="display: inline-block;vertical-align: middle;">
											{{commX.nickname}}
										</div>
									</div>
									<div class="commnet_text_div">
										{{commX.text}}
									</div>
									<div style="margin-top:0.5em;" >
										<div class="img_thum_div img_frame" ng-click="wxViewPhotos(pageData.comment.commentPic, $index)" 
											ng-repeat="picx in commX.commentPicThum track by $index"
											style="background-image: url({{picx}});"></div>
									</div>
									<div style="font-size:0.8em;margin-top:0.6em;color:#aaa">
									{{commX.comTime}}
									</div>
								</div>
							</div>
							
					<!-- 点赞和 -->
							<div style="padding: 0.2em;text-align: right;">
									<div class="weui_btn weui_btn_mini weui_btn_default fa-thumbs-o-up" 
										style="font-size: 0.8em;min-width: 5em;font-family:'FontAwesome';"
									>({{commX.likedNumber}})</div>
							</div>
							<br>
							<button ng-click="setRecommComment(pageData.bookId, null, commX.id)">
							设置为推荐读后感</button>
							<button ng-click="deleteTheComment(pageData.bookId, null, commX.id)">
							删除这个读后感
							</button>
							<br>
							<br>
					
							
							
					
					<!-- 推荐回复 -->
					<div ng-show="checkIfHaveRecomComment(commX.recomComment)">
					<p style="border-bottom: 1px solid;">
						推荐回复：
					</p>
					<div style="postion:relative;" >
						<div class="message_text_style reply_frame"
						style="margin: 0.25em;width: auto;">
						{{commX.recomComment.nickname+" 回复说: "+commX.recomComment.text}}
						<br>
									<button ng-click="deleteTheComment(pageData.bookId, commX.id, commX.recomComment.id)">
									删除这个评论</button>
									<br>
									<br>
						</div>
					</div>
					</div>
					<br/>
					<p
					style="
					    border-bottom: 1px solid;
					">
						其他回复：
					</p>
					<!-- 其他2级留言 -->	
						<div style="postion:relative;" ng-repeat="subCommentX in commX.child">
									<div class="message_text_style reply_frame" 
									style="
									margin: 0.25em;
					    			width: auto;
					    			border-bottom: 1px solid #afafaf;">
									{{subCommentX.nickname+" 说: "+subCommentX.text}}
									<br>
									<button ng-click="setRecommComment(pageData.bookId, commX.id, subCommentX.id)">
									设置为推荐点评</button>
									<button ng-click="deleteTheComment(pageData.bookId, commX.id, subCommentX.id)">
									删除这个点评
									</button>
									<br>
									<br>
									</div>
									<div>
									
									</div>
										
						</div>
						<br/>
						
						</div>
						<!-- 一段留言的结束 -->
					
					</div> <!-- class -messageBoard_public //-->

  <!-- --------------------------------------------------------------- -->
     
     <!-- 这条线以下不要加页面代码 -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

  <jsp:include page="./_include_js.jsp"></jsp:include>
  <script src="../sp/ajController/yuexiang/admin/manageCommentsController.js"></script>

</body>

</html>
