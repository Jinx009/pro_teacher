zaApp.controller('myPengListController',function($scope, $rootScope, QueryParamService, wxJSSDKService, sessionService){
	
	$scope.initial = function(urlBase){
		param = QueryParamService.getParam();
		console.log(param);
        
		$rootScope.urlPath = QueryParamService.getPWD(urlBase);

		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
	}
	
	function initialAsync(data){
		$scope.userInfo = data;
		console.log($scope.userInfo);
		
		var userData = 
			{
			};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetMyPengList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.orders = data.retData;
					}
				});		
	}
	
	$scope.goMyPeng = function(orderId){
		window.location.href = $rootScope.urlPath+"/myPeng.html?orderId="+orderId;
	}
	
})