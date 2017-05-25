//var zaApp = angular.module('CFApp', ['ngMd5' // you may also use 'ngMd5' or 'gdi2290.md5' or 'angular-md5'
//                                    ]);

zaApp.controller('registerController', function($scope, $rootScope, $http, md5, $timeout, sessionService, QueryParamService) {
	
	$scope.flagNameError = 0; //0 normal; 1 error; 2 ok;
	$scope.flagPhoneNumberError = 0; //0 normal; 1 error; 2 ok;
	$scope.flagPhoneCodeError = 0; //0 normal; 1 error; 2 ok;
	$scope.flagVerifyCodeError = 0; //0 normal; 1 error; 2 ok;
	$scope.flagPasswordError = 0; //0 normal; 1 error; 2 ok;
	$scope.flagConfirmPasswordError = 0; //0 normal; 1 error; 2 ok;

	
	$scope.flagNameErrorMessage = "姓名有误"; 
	$scope.flagPhoneNumberErrorMessage = "电话号码有误";
	$scope.flagPhoneCodeErrorMessage = "验证码错误";
	$scope.flagVerifyCodeErrorMessage = "验证码错误";
	$scope.flagPasswordErrorMessage = "密码格式不正确";
	$scope.flagConfirmPasswordErrorMessage = "两次密码输入不一致";

	
	$scope.realname = "";
	$scope.username = "";
	$scope.password = "";
	$scope.confirmPassword = "";
	$scope.phoneCode = "";
	$scope.verifyCode = "";
	
	
	$scope.checkInput = function(isCheckForSubmit, checkTarget){
		//check realname
		if( checkTarget == "realname" || checkTarget == "all"){
			$scope.flagNameError = 0;
			if( isCheckForSubmit )
					$scope.flagNameError = 1;
				if( $scope.realname.length > 1 ){
					$scope.flagNameError = 2;
				}else{
					$scope.flagNameErrorMessage = "姓名不能为空";
				}
			
		}
		
		//check phone unmber
		if( checkTarget == "username" || checkTarget == "all"){
			//alert("$scope.flagPhoneNumberError"+$scope.flagPhoneNumberError);
			$scope.flagPhoneNumberError = 0;
			if( isCheckForSubmit )
				$scope.flagPhoneNumberError = 1;
			var phoneNumberExpress = /1\d{10}$/;
			if( phoneNumberExpress.exec($scope.username) !=null ){
				$scope.flagPhoneNumberError = 2;
			}
		}
		//check picture verify code
		if( checkTarget == "verifyCode" || checkTarget == "all"){
			//alert("$scope.flagVerifyCodeErrorMessage"+$scope.flagVerifyCodeErrorMessage);
			if( $scope.verifyCode.length == 4){//每次输入第4个的时候，去后台拿一下最新的验证码
				var urlcode = $rootScope.urlPath+"/doGetVerifyCode.html";
				$http.get(urlcode)
				.success(function(data, status, headers, config){
						$scope.pictureVerifyCode = data.retData;
						if( data.retCode != 0 ){
							//alert("error check: please refresh the verify code");
						}else{
							$scope.flagVerifyCodeError = 0;
							if( isCheckForSubmit )
								$scope.flagVerifyCodeError = 1;
							if( $scope.pictureVerifyCode == $scope.verifyCode.toUpperCase() ){
								$scope.flagVerifyCodeError = 2;
							}else{
								$scope.flagVerifyCodeErrorMessage = "验证码过期或错误";
							}
						}
					})
					.error(function(data, status, headers, config){
					//alert("error: function call failed");
					return;
				});
			}
			$scope.flagVerifyCodeError = 0;
		}
		
		//check Phonecode
		if( checkTarget == "phoneCode" || checkTarget == "all"){
			$scope.flagPhoneCodeError = 0;
			var epc = md5.createHash($scope.phoneCode || '');
			window.console.log($scope.retPhoneCode+"   "+$scope.phoneCode+"   "+epc);
			if( isCheckForSubmit )
				$scope.flagPhoneCodeError = 1;
			if( $scope.retPhoneCode == epc ){
				$scope.flagPhoneCodeError = 2;
			}else{
				$scope.flagPhoneCodeErrorMessage = "手机验证码错误";
			}
		}
		
		//check password
		if( checkTarget == "password" || checkTarget == "all"){
			$scope.flagPasswordError = 0;
			if( isCheckForSubmit )
				$scope.flagPasswordError = 1;
			if( $scope.password.length >= 6 ){
				$scope.flagPasswordError = 2;
			}
		}
		
		//check confirmPassword
		if( checkTarget == "confirmPassword" || checkTarget == "all"){
			if( $scope.flagPasswordError != 1){
				$scope.flagConfirmPasswordError = 0;
				if( isCheckForSubmit )
					$scope.flagConfirmPasswordError = 1;
	
				if( $scope.password == $scope.confirmPassword ){
					$scope.flagConfirmPasswordError = 2;
				}
			}
		}
		
		if( $scope.flagNameError == 1 ||
			$scope.flagPhoneNumberError == 1 ||
			$scope.flagPhoneCodeError == 1 ||
			$scope.flagVerifyCodeError == 1 ||
			$scope.flagPasswordError == 1 ||
			$scope.flagConfirmPasswordError == 1 ){
			return false;
		}	
		else{
			return true;
		}
	}
	
	$scope.registerUser = function(){

		if( !$scope.checkInput(true, "all") )
			return;
		
		//do post to register
		
		var userData = { realname:$scope.realname, 
				         username:$scope.username, 
				         password:$scope.password, 
				         confirmPassword:$scope.confirmPassword, 
				         phoneCode:$scope.phoneCode,
				         verifyCode:$scope.verifyCode,
				         mobile:$scope.username,
				         phoneCodeId:$scope.retPhoneCodeRecId};
		
		
		var url = $rootScope.urlPath+"/doRegisterUser.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
   
         /**
        retCode
        retMessage
        retData.userId
        retData.username
        retData.isAuthed
        */
		.success(function(data, status, headers, config){
			$scope.retCode = data.retCode;
			$scope.retMessage = data.retMessage;
			$scope.retData = data.retData;
			switch( $scope.retCode){
			case 0: //success;
				if( $scope.retData.jumpToUrl == null ){
					$scope.retData.jumpToUrl = $rootScope.urlPath+"/login.html";
				}
				window.location.href=$scope.retData.jumpToUrl;
				break;
			default:
				alert($scope.retMessage);
				$scope.getVerifyCode(0,0);//图片验证码使用过了就过期了，需要重新获取一次
				$scope.phoneCodeCountDown = 0; //手机验证码也过期了，需要重新获取一次
				$scope.phoneCode = "";
				$scope.flagPhoneCodeError = 0;
				break;
			}
			})
        	
        .error(function(data, status, headers, config){
        	alert("error: function call failed");
        	});
		
	}
	
	$scope.verifyCodePicUrl = $rootScope.urlPath+"/doGetVerifyCodePicture.html?width=72&height=25";
	$scope.verifyCode = "";
	
	$scope.getVerifyCode = function(width, height){
		if( width == 0) width = 72; //default width
		if( height == 0) height = 25; //default height
		
		var urlpic = $rootScope.urlPath+"/doGetVerifyCodePicture.html?width="+width+"&height="+height+"&random="+Math.random();
		$scope.verifyCodePicUrl = urlpic;
		$scope.flagVerifyCodeError = 0;
		$scope.verifyCode ="";
	}
	
	$scope.getPhoneCheckCode = function(){
		if( $scope.phoneCodeCountDown > 0 ){
			return;
		}
		if( !$scope.checkInput(true,'username') ){
			return;
		}
		
		if( !$scope.checkInput(true,'verifyCode') ){
			return;
		}
		
		$scope.phoneCode = "";
		$scope.flagPhoneCodeError = 0;
		
		var userData = {username:$scope.username, 
				        verifyCode:$scope.verifyCode};
		var url = $rootScope.urlPath+"/doSendPhoneCode.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };	
		
        /**
        retCode
        retMessage
        retData.userId
        retData.username
        retData.isAuthed
        retData.jumpToUrl
        */
        
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
				if( data.retCode != 0 ){
					alert(data.retMessage);
				}else{
					$scope.retPhoneCode = data.retData.phoneCheckCode;
					$scope.retPhoneCodeRecId = data.retData.phoneCheckCodeId;
					$scope.retPhoneCodeResendTime = data.retData.resendTime;
					$scope.phoneCodeCountDown = data.retData.allowCountDown;
					$scope.retPhoneCodeResendExpireTime = data.retData.expireTime;
					$scope.runPhoneCodeCountDown();
					window.console.log($scope.retPhoneCode +"   "+ $scope.retPhoneCodeRecId +"   "+ $scope.retPhoneCodeResendTime +"   "+ $scope.retPhoneCodeResendExpireTime);
				}
			})
			.error(function(data, status, headers, config){
				alert("error: function call failed");
			return;
		});
	}
	
	$scope.phoneCodeCountDown = 0;
	$scope.buttonOfGetPhoneCode = "获取手机验证码";
	
	var mytimeout;
	$scope.runPhoneCodeCountDown = function() {
		
		 if( $scope.phoneCodeCountDown <= 0 ){
			 $scope.buttonOfGetPhoneCode = "获取手机验证码";
	    	 $timeout.cancel(mytimeout);
	    	 return;
	     }
	
	     $scope.phoneCodeCountDown --;
	     mytimeout = $timeout($scope.runPhoneCodeCountDown,1000);
	     $scope.buttonOfGetPhoneCode = ""+$scope.phoneCodeCountDown+" 秒后重试";
	     
	}
	
});