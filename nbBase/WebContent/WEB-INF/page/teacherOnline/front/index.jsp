<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="zaApp">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>LINGCHOU</title>
<jsp:include page="./_include_css.jsp"></jsp:include>
<jsp:include page="./_include_js_web.jsp"></jsp:include>
<script type="text/javascript"
	src="./sp/ajController/teacherOnline/front/indexController.js"></script>
<script type="text/javascript"
	src="./sp/ajController/teacherOnline/front/headerController.js">
	
</script>
<!-- <script type="text/javascript">
			window.onload = function() {
				var carousel = $(".carousel-inner").height();
				$(".item-table").css("height",carousel+"px");
			}
			window.onresize = function() {
				var carousel = $(".carousel-inner").height();
				$(".item-table").css("height",carousel+"px");
			}
			$('.carousel').carousel({
			  	interval: 2000
			})
		</script> -->
</head>
<style>
.subjectList {
	
}
</style>
<body>
	<div ng-controller="headerController">
		<jsp:include page="header.jsp" />
	</div>
	<div id="mainContent" ng-controller="indexController"
		ng-init="initial()">

		<div>
			<div class="carousel slide banner_img">
				<uib-carousel active="active" interval="pageData.slidesInterval"
					no-wrap="noWrapSlides"> <uib-slide class="item"
					ng-repeat="slideX in pageData.slides track by slideX.id"
					index="slideX.id"> <img ng-src="{{slideX.image}}"
					alt="banner">
				<div class="item-table item-table-width">
					<div class="item-table-cell">
						<div ng-bind-html="slideX.text01" style="margin-top: 8%;"></div>
						<div class="clearfix"></div>
						<p ng-bind="slideX.text02"></p>
					</div>
				</div>
				<div class="carousel-caption carousel-bottom item-table-width">
					<button class="btn banner-btn" ng-bind="slideX.buttonText"></button>
				</div>
				</uib-slide> </uib-carousel>
			</div>
		</div>

		<div class="py2 border-bottom">
			<div class="container py2">
				<div class="row">

					<div class="title py2">
						<h2 class="nav-left">热门课程推荐</h2>
						<div class="nav-right">
							<a href="project/projectList.html"><button class="btn">更多课程</button></a>
						</div>
					</div>
					<div class="title py2"
						style="height: auto; margin-bottom: 0; background-color: rgba(164, 218, 228, 0.07);">
						<div class=" "
							ng-class="{true:'col-md-10 mobile-no-padding',false:'col-md-10 mobile-scroll-row'}[bodyWidth > 980]"
							style="vertical-align: middle; float: none; display: inline-block;">
							<!--  -->

							<div
								ng-class="{true:'mobile-scroll-row-item col-md-10'}[bodyWidth <= 980]"
								ng-repeat="infoX in pageData.subjects track by $index"
								ng-show="infoX.toggleIsActive==true || bodyWidth <= 980">
								<div class="project-card">
									<div class="project-thumbnail float-left">
										<a href="/project/projectDetail.html"> <img
											ng-src="{{infoX.info.imgUrl}}" style="width: 100%;" />
										</a>
									</div>
									<div class="project-card-interior">
										<h4 ng-bind="infoX.info.title"></h4>
										<p ng-bind="infoX.info.desc"></p>
										<br /> <a href="">
											<button class="btn">查看课程详情</button>
										</a>
										<div class="project-card-interior-bottom">
											<div class="progress progress-now">
												<div class="progress-bar" role="progressbar"
													aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
													style="width: 60%;"></div>
											</div>
											<ul class="project-stats-wrap">
												<li><strong class="block">90%</strong> <span>报名进度</span>
												</li>
												<li><strong class="block">&yen;200</strong> <span>报名费用</span>
												</li>
												<li><strong class="block">还有10天</strong> <span>报名截止</span>
												</li>


											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="col-md-2"
							style="float: none; display: inline-block; width: 15%; vertical-align: middle;">

							<nav class="border-left">
								<ul>
									<li ng-repeat="subjectX in pageData.subjects track by $index"
										ng-bind="subjectX.subjectName"
										style="font-size: 1.3em; margin-bottom: 0.5em;"
										ng-mouseenter="toggleActiveRecommTeacher($index)"
										ng-mouseleave=""
										ng-class="{true:'active'}[subjectX.toggleIsActive]"></li>
								</ul>
							</nav>

						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="container py2 mobile-no-padding">
			<div class="title py2">
				<h2 class="nav-left">优秀教师推荐</h2>
				<div class="nav-right">
					<a href="project/projectList.html"><button class="btn">更多教师</button></a>
				</div>
			</div>
			<div class="row py2 mobile-scroll-row">
				<div class="mobile-scroll-row-item col-md-4"
					ng-repeat="teacherX in pageData.recommTeachers">
					<div class="project-card-big">
						<div class="project-thumbnail">
							<a href="/project/projectDetail.html"><img
								src="sp/img/teacherOnline/popular_one.png" /></a>
						</div>
						<div class="project-card-interior">
							<h4 ng-bind="teacherX.teacherTitle"></h4>
							<p ng-bind="teacherX.teacherDesc"></p>
							<div class="project-card-interior-bottom">
								<table
									style="width: 100%; border-collapse: separate; border-spacing: 0 1em;">
									<tr>
										<td><span style="font-size: 0.9em; color: gray;">所属机构</span>
										</td>
										<td><strong class="block"
											ng-bind="teacherX.teacherEmployer"></strong></td>
									</tr>
									<tr>
										<td><span style="font-size: 0.9em; color: gray;">教学特长</span>
										</td>
										<td><strong class="block" ng-bind="teacherX.bestCourse"></strong>
										</td>
									</tr>
									<tr>
										<td><span style="font-size: 0.9em; color: gray;">教龄</span>
										</td>
										<td><strong class="block" ng-bind="teacherX.expeirence"></strong>
										</td>
									</tr>
									<tr>
										<td><span style="font-size: 0.9em; color: gray;">好评率</span>
										</td>
										<td><strong class="block" ng-bind="teacherX.likeRadio"></strong>
										</td>
									</tr>
								</table>

							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<div class="container py2 mobile-no-padding">
			<div class="title py2">
				<h2 class="nav-left">合作伙伴</h2>

			</div>
			<div class="row py2 mobile-scroll-row">
				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>

				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>

				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>

				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>

				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>

				<div class="mobile-scroll-row-item col-md-2 ">
					<div class="border">
						<a href="#"><img src="sp/img/teacherOnline/partner_one.png" /></a>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- mainContent -->
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
					<div class="site-logo footer-logo">
						<a href="index.html" class="logo"></a>
					</div>
				</div>
				<div class="copyrigth-col copyrigth-text padding-col">
					<p>Copyright&copy;2015零筹</p>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="./_include_components.jsp"></jsp:include>
</body>
</html>