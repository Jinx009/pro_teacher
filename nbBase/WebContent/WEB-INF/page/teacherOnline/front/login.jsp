<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="zaApp">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>LINGCHOU</title>
		<jsp:include page="./_include_css.jsp"></jsp:include>
		<jsp:include page="./_include_js_web.jsp"></jsp:include>
		<script type="text/javascript" src="./sp/ajController/teacherOnline/front/loginController.js" ></script>
		<script type="text/javascript" src="./sp/ajController/teacherOnline/front/headerController.js"> </script>
		
	</head>
	<body class="login-bg">
		<div ng-controller="headerController">
		<jsp:include page="header.jsp"/>
		</div>
		
		<div ng-controller="loginController"
		ng-init="initial()">
		<div class="login-content py4 login-bottom-pc">
			<div class="container">
				<div class="login-wrap">
					<div class="login-top">
						<h3 class="login-title">登录</h3>
						<form>
						
							<!--错误提示		start-->
							<div ng-class="{true:'form-group has-error has-feedback',
							                false:'form-group'}
							                [flagUsernameHasError]">
							                
								<input type="text"  ng-model="username" class="form-control input-lg" placeholder="请输入手机号码" id="inputError2" aria-describedby="inputError2Status" ng-change="usernameInputChanged()">	
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagUsernameHasError">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> 手机号码有误
							</div>
							<!--错误提示		end-->
							
							<!--错误提示		start-->
							<div ng-class="{true:'form-group has-error has-feedback',
							                false:'form-group'}
							                [flagPasswordHasError]">
								<input type="password" ng-model="password" class="form-control input-lg" placeholder="密码" id="inputError2" aria-describedby="inputError2Status" ng-change="passwordInputChanged()">
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagPasswordHasError">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> 您输入的密码有误！
							</div>
							<!--错误提示		end-->
							
							
							<!--错误提示		start-->
							<div ng-class="{true:'form-group has-error has-feedback',
							                false:'form-group'}
							                [flagVerifyCodeHasError]" >
								<div class="input-group">
								<input type="text" ng-model="pictureVerifyCode" class="form-control input-lg" placeholder="请输入验证码" id="inputError2" aria-describedby="inputError2Status" ng-change="verifyCodeInputChanged()">
  								<div class="input-group-addon"><img src="{{verifyCodePicUrl}}" ng-init="getVerifyCode(72,25)" ng-click="getVerifyCode(72,25)"/></div>	
  								</div>
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagVerifyCodeHasError">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> 验证码有误！
							</div>
							<!--错误提示		end-->
							
							
							<button type="submit" class="btn project-detail-btn" ng-click="checkAndLogin(user)">确定</button>
							
							<div class="checkbox" style="text-align:right;text-decoration:underline">
							
							<a href="resetPassword.html">忘记密码</a>
							
							    <label>
							   
							     <!--   <input type="checkbox"> <span class="check-span">记住密码</span> -->
							     
							    </label>
							</div>
						</form>
					</div>
					<div class="login-bottom">
						没有账号<a href="register.html">立即注册</a>
					</div>
				</div>	
			</div>
		</div>
		
		</div>
		<div id="footer-fixed">
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
		</div>
		<jsp:include page="./_include_components.jsp"></jsp:include>
	</body>
</html>