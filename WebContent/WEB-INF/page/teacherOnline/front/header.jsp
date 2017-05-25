<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<header class="site-nav navbar-fixed-top" 
	ng-init="inital('http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}')">
		<div class="container mobile-no-padding container-auto">
			<h1 class="site-logo"><a href="index.html" class="logo"></a></h1>
			<div class="nav-wrap">
				<nav class="nav-left">
					<ul class="primary-menu">
						<li class="primary-menu-link"><a href="${serverUrlPath}index.html">首页</a></li>
						<li class="primary-menu-link"><a href="${serverUrlPath}project/projectList.html">课程列表</a></li>
						<li class="primary-menu-link "><a href="${serverUrlPath}#">寻找教师</a></li>
						<li class="primary-menu-link "><a href="${serverUrlPath}#">智能查找</a></li>
						<li class="primary-menu-link " ng-show="isAuthed"><a href="${serverUrlPath}#" >我的账户</a></li>
					</ul>
				</nav>
				<nav class="nav-right">
					<ul class="user-menu" ng-hide="isAuthed">
						<li><a href="${serverUrlPath}login.html">登录</a></li>
						<li><a href="${serverUrlPath}register.html">注册</a></li>
					</ul>
					
					<ul class="user-menu" ng-show="isAuthed">
						<li class="dropdown">
							<a href="login.html" role="button" class="dropdown-toggle" data-toggle="dropdown">
							<div class="fa-user" style="min-width:80px;font-weight: bolder;font-family:'FontAwesome';"> {{showname}}<i class="caret"></i></div></a>
							<ul class="dropdown-menu login-out">
								<li>
									<a href="" 
									ng-click="logout('http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}')"
									class="fa-archive"
									style="font-family:'FontAwesome';">
									 个人中心</a>
								</li>
								<li>
									<a href="" 
									ng-click="logout('http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}')"
									class="fa-share-square-o"
									style="font-family:'FontAwesome';">
									 退 出</a>
								</li>
							</ul>
						</li>
					</ul>
					
				</nav>
			</div>
		</div>
	</header>