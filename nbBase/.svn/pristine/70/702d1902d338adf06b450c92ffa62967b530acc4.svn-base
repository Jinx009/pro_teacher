<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />	
		<title>LINGCHOU</title>
		<link rel="stylesheet" href="../sp/css/animate.min.css" />
		<link rel="stylesheet" href="../sp/css/bootstrap.min.css" />
		<link rel="stylesheet" href="../sp/css/base.css" />
		<link rel="stylesheet" href="../sp/css/crowdfunding.css" />
		<script type="text/javascript" src="../sp/js/jquery.js" ></script>
		<script type="text/javascript" src="../sp/js/bootstrap.min.js" ></script>
		<script type="text/javascript">
			$(function(){
				var width = $(window).width();
				var height = $(document).height();
				
				$(".search-all-project").click(function(){
					$(".mask").show();
					$("#projectList").show();
					$(".mask").css("width",width+"px");
					$(".mask").css("height",height+"px");
				});
				
				$(".search-popular-project").click(function(){
					$(".mask").show();
					$("#projectList").show();
					$(".mask").css("width",width+"px");
					$(".mask").css("height",height+"px");
				});
				
				$(".mask").click(function(){
					$(".mask").hide();
					$("#projectList").hide();
				})
				
			})
		
			function showSearchRefine(){
				$("#search").hide();
				$("#search-btn").hide();
				$("#search-refine").slideDown().show();
			}
			
			function closeSearch(){
				$("#search").slideDown().show();
				$("#search-btn").slideDown().show();
				$("#search-refine").hide();
				$("#search-refine-box").removeClass("in");
			}
		</script>
	</head>
	<body>
		<jsp:include  page="../header.jsp"/> 
		
		<div class="border-bottom py3" style="position: relative;">
			<div class="search-table" id="search">
				<div class="search-table-cell">
					<div class="container">
						<div class="search-all-project"><a href="#"><span class="animated bounce fadeInDown">全部产品<img src="../sp/img/teacherOnline/chevron_down.png" class="chevron_down"/></span></a></div>
						<div class="search-popular-project"><span>平台</span><a href="#"><span class="animated bounce fadeInUp">最热销的产品<img src="../sp/img/teacherOnline/chevron_down.png" class="chevron_down"/></span></a></div>
					</div>
				</div>
			</div>
			
			
			<div class="mask"></div>
			<div class="mark-box" id="projectList">
				<ul class="container search-sel-option">
	        		<li><a href="projectList.html"><span>众筹1号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹2号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹3号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹4号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹5号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹6号</span></a></li>
	        		<li><a href="projectList.html"><span>众筹7号</span></a></li>
	        	</ul>
			</div>	
			
			

			<div class="search" id="search-btn">
				<div class="search-table">
					<div class="search-table-cell">
						<div class="container">
							<div class="search-refine"  onclick="showSearchRefine();"><a href="#"><span>自定义筛选</span></a></div>
						</div>
					</div>
				</div>
			</div>
			
			<div style="display: none;" id="search-refine">
				<div class="search-table">
					<div class="search-table-cell">
						<div class="container">
							<form class="form-inline">
								
								<div class="form-group form-mobile">
									<label>全部产品</label>
									<select class="form-control">
										<option>众筹大菊花1号</option>
										<option>众筹大菊花2号</option>
										<option>众筹大菊花3号</option>
										<option>众筹大菊花4号</option>
										<option>众筹大菊花5号</option>
									</select>
								</div>
								
								<div class="form-group form-right form-mobile">
									<label>最热销的产品</label>
									<select class="form-control">
										<option>众筹大菊花1号</option>
										<option>众筹大菊花2号</option>
										<option>众筹大菊花3号</option>
										<option>众筹大菊花4号</option>
										<option>众筹大菊花5号</option>
									</select>
								</div>
								
							</form>
						</div>
					</div>
				</div>
				<div class="search">
					<div class="search-table">
						<div class="search-table-cell">
							<div class="container">
								<div class="row">
									<div class="col-md-4 search-refine" ><a class="col-md-3 padding-no-col" data-toggle="collapse" data-parent="#accordion" href="#search-refine-box" aria-expanded="false" aria-controls="search-refine-box"><span>自定义筛选</span></a><a href="#" class="col-md-2"><span id="colse-search" onclick="closeSearch();">关闭</span></a></div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			
			<div class="refine-content panel-collapse collapse collapse-nav" id="search-refine-box"  role="tabpanel">
				<div class="container">
					<div class="refine-box">
						<div class="arrow"></div>
						<h4 class="search-title">请选择筛选规则</h4>
						<input class="form-group form-control" placeholder="search"/>
						<div class="row">
							<div class="col-md-6">
								<ul class="search-sel-option">
					        		<li><a href="#"><span>众筹1号</span></a></li>
					        		<li><a href="#"><span>众筹2号</span></a></li>
					        		<li><a href="#"><span>众筹3号</span></a></li>
					        		<li><a href="#"><span>众筹4号</span></a></li>
					        	</ul>
							</div>
							<div class="col-md-6">
								
								<select class="form-group form-control">
									<option>众筹大菊花1号</option>
									<option>众筹大菊花2号</option>
									<option>众筹大菊花3号</option>
									<option>众筹大菊花4号</option>
									<option>众筹大菊花5号</option>
								</select>
								
								<select class="form-group form-control">
									<option>众筹大菊花1号</option>
									<option>众筹大菊花2号</option>
									<option>众筹大菊花3号</option>
									<option>众筹大菊花4号</option>
									<option>众筹大菊花5号</option>
								</select>
								
							</div>
						</div>
			        	<hr />
			        	<h4 class="search-title">自定义标签</h4>
		        		<ul class="search-refine-label">
		        			<li><a href="#"><span>标签1号</span></a></li>
		        			<li><a href="#"><span>标签2号</span></a></li>
		        			<li><a href="#"><span>标签3号</span></a></li>
		        			<li><a href="#"><span>标签4号</span></a></li>
		        			<li><a href="#"><span>标签5号</span></a></li>
		        			<li><a href="#"><span>标签6号--众筹1号</span></a></li>
		        			<li><a href="#"><span>标签1号</span></a></li>
		        			<li><a href="#"><span>标签2号</span></a></li>
		        			<li><a href="#"><span>标签3号</span></a></li>
		        			<li><a href="#"><span>标签4号</span></a></li>
		        			<li><a href="#"><span>标签5号</span></a></li>
		        			<li><a href="#"><span>标签6号--众筹1号</span></a></li>
		        			<li><a href="#"><span>标签1号</span></a></li>
		        			<li><a href="#"><span>标签2号</span></a></li>
		        			<li><a href="#"><span>标签3号</span></a></li>
		        			<li><a href="#"><span>标签4号</span></a></li>
		        			<li><a href="#"><span>标签5号</span></a></li>
		        			<li><a href="#"><span>标签6号--众筹1号</span></a></li>
		        			<li><a href="#"><span>标签1号</span></a></li>
		        			<li><a href="#"><span>标签2号</span></a></li>
		        			<li><a href="#"><span>标签3号</span></a></li>
		        			<li><a href="#"><span>标签4号</span></a></li>
		        			<li><a href="#"><span>标签5号</span></a></li>
		        			<li><a href="#"><span>标签6号--众筹1号</span></a></li>
		        		</ul>	
					</div>
				</div>	
			</div>
			
			
		</div>	
		
		<div class="project-list">
			<div class="container">
				<h3 class="project-num">平台共有<span>1234</span>个项目</h3>
				<div class="row">
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈2222</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="project-card-big mg1">
							<div class="project-thumbnail">
								<a href="projectDetail.html"><img src="../sp/img/teacherOnline/project_list_img.png"/></a>
							</div>
							<div class="project-card-interior mobile-project-card-interior">
								<h4>众筹大菊花4号</h4>
								<p>产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1</p>
								<div class="project-card-interior-bottom">
									<div class="progress progress-now">
										<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
									</div>
									<ul class="project-stats-wrap">
										<li>
											<strong class="block">90%</strong>
											<span>众筹进度</span>
										</li>
										<li>
											<strong class="block">&yen;100,000,00</strong>
											<span>目标金额</span>
										</li>
										<li>
											<strong class="block">10天</strong>
											<span>剩余天数</span>
										</li>
									</ul> 
								</div>
							</div>
						</div>
					</div>

				</div>
				
				<div class="see-more text-center"><button class="btn btn-default btn-lg">查看更多</button></div>
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
