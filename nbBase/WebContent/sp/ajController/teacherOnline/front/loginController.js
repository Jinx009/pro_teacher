
zaApp.controller('loginController', function($scope, $rootScope, $http, sessionService, QueryParamService) {
	$scope.username="";
	$scope.password="";
	$scope.pictureVerifyCode="";
	
	$scope.flagUsernameHasError=false;
	$scope.flagPasswordHasError=false;
	$scope.flagVerifyCodeHasError=false;
	
	$scope.retCode = 0;
	$scope.retMessage = "";
	$scope.retData = null;
	
	$scope.inital = function(){
		
	}
	
	$scope.usernameInputChanged = function(){
		$scope.flagUsernameHasError=false;
	}
	$scope.passwordInputChanged = function(){
		$scope.flagPasswordHasError=false;
	}
	
	$scope.verifyCodeInputChanged = function(){
		$scope.flagVerifyCodeHasError=false;
	}
	
	$scope.checkAndLogin = function(){
		
		if( $scope.username == "" ){
			$scope.flagUsernameHasError = true;
			return;
		}else{
			$scope.flagUsernameHasError = false;
		}

		var urlcode = $rootScope.urlPath+"/doGetVerifyCode.html";
		$http.get(urlcode)
		.success(function(data, status, headers, config){
				$scope.verifyCode = data.retData;

				if( data.retCode != 0 ){
					//alert("error check: please refresh the verify code");
				}else{
					$scope.pictureVerifyCode = $scope.pictureVerifyCode.toUpperCase();
					if( $scope.pictureVerifyCode != $scope.verifyCode ){
						$scope.flagVerifyCodeHasError=true;
						return;
					}
				}
			})
			.error(function(data, status, headers, config){
			//alert("error: function call failed");
			return;
		});
		
		
		var userData = {username:$scope.username, password:$scope.password, pictureVerifyCode:$scope.pictureVerifyCode};
		var url = $rootScope.urlPath+"/doLogin.html";
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
				$scope.retCode = data.retCode;
				$scope.retMessage = data.retMessage;
				$scope.retData = data.retData;

				
				if( $scope.retCode == 0 ){
					$scope.flagPasswordHasError = false;
					//$scope.flagVerifyCodeHasError = !$scope.flagVerifyCodeHasError;
					if( $scope.retData.jumpToUrl != null ){
					 	window.location.href=$scope.retData.jumpToUrl;
					}
					else{
						window.location.href=$rootScope.urlPath+"/index.html";
					}
				}else{
					//alert($scope.retMessage);
					if( $scope.retCode==20 || $scope.retCode == 21 ){
						$scope.flagVerifyCodeHasError = true;
						$scope.getVerifyCode(72, 25);
					}
					else{
						$scope.flagPasswordHasError = true;
						$scope.getVerifyCode(72, 25);
					}
				}
				
            	})
            	
            .error(function(data, status, headers, config){
            	//alert("error: function call failed");
            	});
	
		
		
	}
	
	$scope.verifyCodePicUrl = $rootScope.urlPath+"/doGetVerifyCodePicture.html?width=72&height=25";
	$scope.verifyCode = "";
	
	$scope.getVerifyCode = function(width, height){
		$scope.pictureVerifyCode = "";
		$scope.verifyCodePicUrl = $rootScope.urlPath+"/doGetVerifyCodePicture.html?width="+width+"&height="+height+"&random="+Math.random();
	}
	
});