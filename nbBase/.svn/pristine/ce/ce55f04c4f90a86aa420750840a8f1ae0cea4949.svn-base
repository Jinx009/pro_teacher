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
		<script type="text/javascript" src="./sp/ajController/teacherOnline/front/registerController.js"> </script>
		<script type="text/javascript" src="./sp/ajController/teacherOnline/front/headerController.js" ></script>
	</head>
	<body class="login-bg">
		<div ng-controller="headerController">
		<jsp:include page="header.jsp"/>
		</div>
		
		<div class="login-content py4 login-bottom-pc" ng-controller="registerController">
			<div class="container">
				<div class="login-wrap">
					<div class="login-top">
						<h3 class="login-title">注册</h3>
						
												
						<form>
						<!-- 姓名 start -->
							<div ng-class="{'form-group':flagNameError==0,
							                'form-group has-error has-feedback':flagNameError==1,
							                'form-group has-success has-feedback':flagNameError==2}" >
								<input type="text" class="form-control input-lg" placeholder="姓名" ng-model="realname" ng-change="checkInput(false,'realname')">
								<div ng-show="flagNameError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     							</div>	
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagNameError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagNameErrorMessage}}
							</div>
						<!-- 姓名 end -->
													
						<!-- 电话号码 start -->
							<div ng-class="{'form-group':flagPhoneNumberError==0,
							                'form-group has-error has-feedback':flagPhoneNumberError==1,
							                'form-group has-success has-feedback':flagPhoneNumberError==2}" >
							                
								<input type="text" class="form-control input-lg" placeholder="电话号码 " id="inputSuccess3" aria-describedby="inputSuccess3Status"  ng-model="username" ng-change="checkInput(false,'username')">
								<div ng-show="flagPhoneNumberError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     							</div>
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagPhoneNumberError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagPhoneNumberErrorMessage}}
							 </div>
						<!-- 电话号码 end -->
							
							<!--正确提示		start
							<div class="alert alert-success alert-dismissible" role="alert">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> 手机号码正确
							</div>
							<!--正确提示		end-->
							
						<!-- 图片验证码 start -->
							<div class="form-group">
								<div class="input-group">
								<div ng-class="{'form-group':flagVerifyCodeError==0,
							                'form-group has-error has-feedback':flagVerifyCodeError==1,
							                'form-group has-success has-feedback':flagVerifyCodeError==2}" >
									<input type="text" class="form-control input-lg" placeholder="请输入验证码" ng-model="verifyCode" ng-change="checkInput(false,'verifyCode')">
									<div ng-show="flagVerifyCodeError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     								</div>
     							</div>
									<div class="input-group-addon" style="min-width:122px"><img src="{{verifyCodePicUrl}}" ng-click="getVerifyCode(72,25)" style="cursor:pointer"/></div>	
								</div>	
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagVerifyCodeError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagVerifyCodeErrorMessage}}
							 </div>
						<!-- 图片验证码 end -->
						
						<!-- 电话验证码 start -->
							<div class="form-group">
								<div class="input-group">
								<div ng-class="{'form-group':flagPhoneCodeError==0,
							                'form-group has-error has-feedback':flagPhoneCodeError==1,
							                'form-group has-success has-feedback':flagPhoneCodeError==2}" >
									<input type="text" class="form-control input-lg" placeholder="手机验证码" ng-model="phoneCode" ng-change="checkInput(false,'phoneCode')">
									<div ng-show="flagPhoneCodeError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     								</div>
     								</div>
									<div class="input-group-addon" style="min-width:122px"><span style="cursor:pointer" ng-click="getPhoneCheckCode()">{{buttonOfGetPhoneCode}}</span></div>		
								</div>	
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagPhoneCodeError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagPhoneCodeErrorMessage}}
							 </div>
						<!-- 电话验证码 end -->
							
							
						<!-- 密码 start -->
							<div ng-class="{'form-group':flagPasswordError==0,
							                'form-group has-error has-feedback':flagPasswordError==1,
							                'form-group has-success has-feedback':flagPasswordError==2}" >
									<input type="password" class="form-control input-lg" placeholder="密码" ng-model="password" ng-change="checkInput(false,'password');checkInput(false,'confirmPassword')">
									<div ng-show="flagPasswordError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     								</div>
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagPasswordError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagPasswordErrorMessage}}
							</div>
						<!-- 密码 end -->
						
						<!-- 再次密码 start -->
							<div ng-class="{'form-group':flagConfirmPasswordError==0,
							                'form-group has-error has-feedback':flagConfirmPasswordError==1,
							                'form-group has-success has-feedback':flagConfirmPasswordError==2}" >
									<input type="password" class="form-control input-lg" placeholder="再输入一遍密码" ng-model="confirmPassword" ng-change="checkInput(false,'confirmPassword')">
									<div ng-show="flagConfirmPasswordError==2">
									<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
     								<span id="inputSuccess3Status" class="sr-only">(success)</span>
     								</div>
							</div>
							<div class="alert alert-danger alert-dismissible" role="alert" ng-show="flagConfirmPasswordError==1">
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							  <strong>Warning!</strong> {{flagConfirmPasswordErrorMessage}}
							</div>
						<!-- 再次密码 end -->
							
							<div class="checkbox">
							    <label>
							      <input type="checkbox" checked="true" checked disabled> <span class="check-span">同意协议</span>
							    </label>
							</div>
							<button type="button" class="btn project-detail-btn" ng-click="registerUser()">确定</button>
						</form>
					</div>
					<div class="login-bottom">
						已有账号<a href="login.html">立即登录</a>
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