zaApp.controller('myEventsController',function($scope, $rootScope, QueryParamService, wxJSSDKService, sessionService){
	
	$scope.initial = function(urlBase){
		param = QueryParamService.getParam();
		console.log(param);
        
		$rootScope.urlPath = urlBase;
		if( typeof( urlBase ) == "undefined"){
			$rootScope.host = QueryParamService.getServerName();
			$rootScope.urlPath = $rootScope.host + QueryParamService.getPath();
			console.log($rootScope.urlPath);
		}

		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
	}
	
	function initialAsync(data){
		$scope.userInfo = data;
		console.log($scope.userInfo);
		var displayMode = typeof(param.displayMode) == "undefined" ? 0 : param.displayMode;
		
		
//		if( typeof($scope.userInfo.serverName) != "undefined" ){
//			$rootScope.urlPath = $scope.userInfo.serverName;
//		}
		
		var userData = 
			{
				"createrOpenId" : $scope.userInfo.openid,
				"displayMode" : displayMode,
			};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEventList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.events = data.retData.events;
					}
				});		
	}
	
	$scope.goMyEventsConfig = function(eventId){
		//window.location.href = $rootScope.urlPath+"/createOrModifyEvent.html?eventId="+eventId;
		window.location.href = $rootScope.urlPath+"/createSimpleEvent.html?eventId="+eventId+"&displayMode="+parseInt(param.displayMode)%10;
	}
	
	function convertToStatusString(status){
		switch(status){
		case 1:
			return "正在召集参与者中";
			break;
		case 2:
			return "众筹成功";
			break;
		case 3:
			return "众筹失败";
			break;
		}
	}
	

	$scope.showLongDesc = function(text){
		$scope.longText = text;
		$scope.isRightDescShow = true;
		$scope.isBodyShow = false;
	}
	

	$scope.hideRightDesc = function(){

		$scope.isRightDescShow = false;
		$scope.isBodyShow = true;
	}
	
	function pad(num, size) {
	    var s = num+"";
	    while (s.length < size) s = "0" + s;
	    return s;
	}
	
	function cutRight(string, num) {
	    return string.substr(0, string.length-num);
	}
	
})