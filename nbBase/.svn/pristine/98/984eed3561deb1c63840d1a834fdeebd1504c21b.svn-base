<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="CFApp" >
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />	
		<title>LINGCHOU</title>
		<link rel="stylesheet" href="../sp/css/animate.min.css" />
		<link rel="stylesheet" href="../sp/css/bootstrap.min.css" />
		<link rel="stylesheet" href="../sp/css/base.css" />
		<link rel="stylesheet" href="../sp/css/crowdfunding.css" />
		<script type="text/javascript" src="../sp/js/angular.min.js" ></script>
		<script type="text/javascript" src="../sp/angularController/projectDetailController.js" ></script>
		<script type="text/javascript" src="../sp/angularController/headerController.js"> </script>
		<script type="text/javascript" src="../sp/js/jquery.js" ></script>
		<script type="text/javascript" src="../sp/js/bootstrap.min.js" ></script>
		<script type="text/javascript">
			window.onload = function() {
				var width = $(window).width();
				if(width>=981){
					$(".mobile-is-active").addClass("active");
					var height = $(".project-thumbnail-left .project-thumbnail").height();
					$(".right-bottom").css("height",height+"px");
				}else{
					$(".mobile-is-active").removeClass("active");
				}
				
				
			}
		    
			window.onresize = function() {
				var width = $(window).width();
				if(width>=981){
					$(".mobile-is-active").addClass("active");
					var height = $("project-thumbnail-left .project-thumbnail").height();
					$(".right-bottom").css("height",height+"px");	
				}else{
					$(".mobile-is-active").removeClass("active");
				}
			}
			
			$(function(){
			
				$(".project-reward-list .project-reward").hover(function(){
					$(this).find(".project-reward-hover").toggle();
				});
				
				$(".project-reward-list .project-reward").click(function(){
					$(this).find(".project-reward-hover").addClass("hover-zoomout");
					$(this).find(".invest-form").slideDown().show();
				});

			})
			
		</script>
	</head>
	<body>
	<div ng-controller="headerController">
		<jsp:include page="../header.jsp"/>
	</div>
	
		<div ng-controller="projectDetailController">
		<div ng-init="initialPage(${projectId})">
		<div class="projectDetail-top projectDetail-top-mobile">
			<div class="container">
				<h1 class="text-center project-detail-title project-detail-title-pc">{{projectTitle}}</h1>
				<div class="row project-detail">
					<div class="col-md-7 project-thumbnail-left">
						<div class="project-thumbnail">
							<a href="#"><img src="{{headPicUrl}}"/></a>
						</div>
						<div class="project-desc">
							<h1 class="text-center project-detail-title project-detail-title-mobile">{{projectTitle}}</h1>
							<p>{{projectGeneralDesc}}</p>
						</div>
					</div>
					<div class="col-md-5">
						<div class="right-bottom py2">
							<div class="right-cell">
								<div class="project-stats-wrap">
									<li>
										<strong class="block">{{supporterNumber}}</strong>
										<span>支持者</span>
									</li>
									<li>
										<strong class="block">&yen;{{targetAmount}}</strong>
										<span>目标金额</span>
									</li>
									<li>
										<strong class="block">{{leftDays}}</strong>
										<span>剩余天数</span>
									</li>
								</div>
								
								<div class="row py2">
									<div class="col-md-5">
										<a href="#toProjectReward"><button class="btn project-detail-btn">跟投</button></a>
									</div>
									<div class="col-md-5">
										<div class="project-detail-attention">
											<span class="glyphicon glyphicon-star"></span>
											<span ng-show="isFollowed==true" ng-click="taggleFollow()">已关注</span>
											<span ng-show="isFollowed!=true" ng-click="taggleFollow()">关注</span>
										</div>
									</div>
								</div>
								<p class="js-adjust-time py2">这个项目在<span>{{deadLine}}</span>结束</p>	
							</div>
						</div>
						
						<div class="NS_projects__creator">
							<div class="col-md-3 NS_projects__creator_right padding-no-col text-center"><img src="{{leadInvestHeadPic}}" class="img-circle"/></div>
							<div class="col-md-9 NS_projects__creator_left padding-no-col">
								<h3 class="ltr">领投人</h3>
								<h3 class="name">{{leadInvesterName}}</h3>
								<p class="desc">{{leadInvesterGeneralDesc}}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="project-detail-nav-content">
			<div class="container">
				<ul class="nav nav-tabs invest-nav" role="tablist" id="toProjectReward">
					<li role="presentation" id="invest-nav" class="active"><a href="#invest" aria-controls="invest" role="tab" data-toggle="tab">投资</a></li>
					<li role="presentation" class="active mobile-is-active"><a href="#details" aria-controls="details" role="tab" data-toggle="tab">项目详情</a></li>
					<li role="presentation"><a href="#events" aria-controls="events" role="tab" data-toggle="tab">大事记</a></li>
					<li role="presentation"><a href="#comment" aria-controls="comment" role="tab" data-toggle="tab">评论({{commentsNumber}})</a></li>
				</ul>
			</div>
		</div>
		<div class="project-detail-content">
			<div class="container py2">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="invest">
						
						<div class="project-reward-list">
							<div class="project-reward" ng-repeat="supportX in supportRules" name="projectReward">
				    			<h2>支持&yen;{{supportX.price}}</h2>
				    			<p class="project-backer-count"><span class="glyphicon glyphicon-star star"></span>关注<span class="count">({{followerNumber}})</span></p>
				    			<div class="project-reward-desc">
				    				<p>{{supportX.rightDesc}}</p>
				    			</div>
				    			<div class="project-extra-info">
				    				<li>
				    					<span class="block">截至时间</span>
				    					<strong>{{deadLine}}</strong>
				    				</li>
				    				<li>
				    					<span class="block">使用范围</span>
				    					<strong>{{supportX.location}}</strong>
				    				</li>
				    			</div>
				    			<div class="project-reward-hover">
				    				<div class="project-reward-hover-table">
				    					<div class="project-reward-hover-table-cell">
				    						<p>投资这个项目</p>
				    					</div>
				    				</div>
				    			</div>
				    			<form class="invest-form py2">
				    				<div class="form-group">
									    <label>跟投数额</label>
									    <input type="text" class="form-control input-lg" ng-model="supportX.toInvestAmount" onkeyup="if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;">
								    </div>
								    <button type="submit" class="btn project-detail-btn" ng-click="submitToInvest(supportX.price,supportX.toInvestAmount,supportX.projectId,supportX.investRuleId)">确定</button>
				    			</form>
				    		</div>	
				    		
						</div>
						
					</div>
					
				    <div role="tabpanel" class="tab-pane active mobile-is-active" id="details">
				    	<div class="row">
					    	<div class="col-md-7">
					    		<div class="NS_projects_content">
						    		<img src="{{projectDetailPic}}"/>
						    	</div>
					    	</div> 	
					    	
					    	<div class="col-md-5 project-reward-content project-reward-list">
					    		<div class="project-reward" ng-repeat="supportX in supportRules" name="projectReward">
					    			<h2>支持&yen;{{supportX.price}}</h2>
					    			<p class="project-backer-count"><span class="glyphicon glyphicon-star star"></span>关注<span class="count">({{followerNumber}})</span></p>
					    			<div class="project-reward-desc">
					    				<p>{{supportX.rightDesc}}</p>
					    			</div>
					    			<div class="project-extra-info">
					    				<li>
					    					<span class="block">截至时间</span>
					    					<strong>{{deadLine}}</strong>
					    				</li>
					    				<li>
					    					<span class="block">使用范围</span>
					    					<strong>{{supportX.location}}</strong>
					    				</li>
					    			</div>
					    			<div class="project-reward-hover">
					    				<div class="project-reward-hover-table">
					    					<div class="project-reward-hover-table-cell">
					    						<p>投资这个项目</p>
					    					</div>
					    				</div>
					    			</div>
					    			<form class="invest-form py2">
					    				<div class="form-group">
										    <label>跟投数额</label>
										    <input type="text" class="form-control input-lg" ng-model="supportX.toInvestAmount" onkeyup="if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;">
									    </div>
									    <button type="submit" class="btn project-detail-btn" ng-click="submitToInvest(supportX.price,supportX.toInvestAmount,supportX.projectId,supportX.investRuleId)">确定</button>
					    			</form>
					    		</div>
					    	</div>
				    	</div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="events">
				    	<div class="timeline">
				    		<div ng-repeat="historyX in history">
				    		<div ng-class-odd="'timeline__item timeline__item--left'" ng-class-even="'timeline__item timeline__item--right'">
				    			<a class="grid-post link" href="#">
				    				<h3 class="grid-post-time">{{historyX.time}}</h3>
				    				<div class="grid-post__content">
				    					<p>{{historyX.eventDesc}}</p>
				    				</div>
				    			</a>
				    		</div>
				    		</div>
				    		
				    		<div class="timeline__divider timeline__divider--launched">
				    			<div class="timeline__divider_content">
				    				<h1>项目起始</h1>
				    				<p>{{projectStartTime}}</p>
				    			</div>
				    		</div>
				    		
				    	</div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="comment">
				    	<div class="commentList">
				    		<div class="comment-item" ng-repeat="commnetX in comments">
					    		<div class="comment-item-left">
				    				<img src="{{commnetX.commentIconUrl}}" class="img-circle"/>
				    			</div>
				    			<div class="comment-item-right">
				    				<h3>{{commnetX.commentor}}<span>{{commnetX.hours}}小时前评论</span></h3>
				    			</div>
				    			<div class="comment-item-desc">
				    				<p>{{commnetX.comment}}</p>
				    			</div>
							</div>
							
							<nav class="text-center">
							  <ul class="pagination">
							    <li>
							      <span style="cursor:pointer;" aria-label="Previous" ng-click="refreshComments(currentCommentsPageIndex-1)">
							        <span aria-hidden="true">&laquo;</span>
							      </span>
							    </li>
							    <li  ng-repeat="pageItem in commentPages"><span style="cursor:pointer;" ng-click="refreshComments(pageItem.pageNumber)">{{pageItem.pageNumber}}</span></li>
							    <li>
							      <span style="cursor:pointer;" aria-label="Next" ng-click="refreshComments(currentCommentsPageIndex+1)">
							        <span aria-hidden="true">&raquo;</span>
							      </span>
							    </li>
							  </ul>
							</nav>
							
				    	</div>
				    	
						<div class="text-center">
							<button class="btn btn-primary btn-lg text-center" data-toggle="modal" data-target="#myModalComments">发表评论</button>
						</div>
						
						<!-- 评论弹出框		start -->
						<div class="modal fade" id="myModalComments" tabindex="-1" role="dialog" aria-labelledby="myModalCommentsLabel">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
						        <h4 class="modal-title" id="myModalCommentsLabel">发表评论</h4>
						      </div>
						     
						      <div class="modal-body">
						      		<textarea class="form-control" rows="5" placeholder="请输入您的评论"  id="comment" name="comment" ng-model="comment"></textarea>
						      </div>
						      <div class="modal-footer">
						      	<button type="submit" class="btn btn-primary" ng-click="submitToComment()">发表评论</button>
						        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						      </div>	
						     
						      
						    </div>
						  </div>
						</div>
						<!-- 评论弹出框		end -->
						
				    </div>
		    	</div>
			</div>	
		</div>
		</div>
		</div>
		<footer class="footer-content">
			<div class="container  py2">
				<div class="row">
					<div class="col-md-5 mobile-col">
						<div class="row">
							<div class="col-md-6">
								<ul>
									<li><a href="#">关于我们</a></li>
									<li><a href="#">认识聚慕</a></li>
									<li><a href="#">聚慕动态</a></li>
									<li><a href="#">聚慕大家庭</a></li>
									<li><a href="#">联系我们</a></li>
								</ul>
							</div>
							<div class="col-md-6">
								<ul>
									<li><a href="#">协议规则</a></li>
									<li><a href="#">风险提示</a></li>
									<li><a href="#">政策法规</a></li>
									<li><a href="#">注册协议</a></li>
									<li><a href="#">发起人协议</a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-7 mobile-col">
						<div class="row">
							<div class="col-md-4">
								<ul>
									<li><a href="#">帮助中心</a></li>
									<li><a href="#">新手指引</a></li>
									<li><a href="#">常见问题</a></li>
									<li><a href="#">投资流程</a></li>
									<li><a href="#">友情链接</a></li>
								</ul>
							</div>
							<div class="col-md-4">
								<ul>
									<li><a href="#">帮助中心</a></li>
									<li><a href="#">新手指引</a></li>
									<li><a href="#">常见问题</a></li>
									<li><a href="#">投资流程</a></li>
									<li><a href="#">友情链接</a></li>
								</ul>
							</div>
							<div class="col-md-4">
								<ul>
									<li><a href="#">协议规则</a></li>
									<li><a href="#">风险提示</a></li>
									<li><a href="#">政策法规</a></li>
									<li><a href="#">注册协议</a></li>
									<li><a href="#">发起人协议</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</footer>
		<div class="copyrigth">
			<div class="container copyrigth-border-top">
				<div class="row">
					<div class="copyrigth-col padding-col">
						<div class="site-logo footer-logo"><a href="index.html" class="logo"></a></div>
					</div>
					<div class="copyrigth-col copyrigth-text padding-col">
						<p>Copyright&copy;2015零筹</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>