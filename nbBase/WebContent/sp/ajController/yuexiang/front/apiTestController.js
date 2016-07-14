
zaApp.controller('apiTestController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
					/**---------------------------
					 * 预设的控制字段和初始化函数
					 */
					$scope.isWxReady = 0;
					$scope.isWxConfigReady = 1;
					$scope.isErrorDialogShow = false;
					$scope.errorMessage = "";
					$scope.isLoadingShow = false;
					$scope.isBodyReady = false;
					$scope.sessionData = {};
					$scope.toggle = {};
					var param = {};
					
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$rootScope.urlPath = QueryParamService.getPWD(urlBase);
						wxJSSDKService.wxConfig($rootScope.urlPath, function(data){
							$scope.sessionData = data;
							//wxJSSDKService.wrapWxCard($scope.sessionData.wxappid, "wxCardImgUrl", "wxCardTitle", "wxCardDesc", true);
							console.log($scope.sessionData);
							
						});
					}
					/**----end--------------------
					 * 预设的控制字段和初始化函数
					 */
	
	/*---正文开始---*/			
	/*------function area-------*/
	$scope.initialAsync = function(data){ //本方法固定存在
			
		
	}
	
	
	
	$scope.pageData = {};
	
	$scope.pageData.functionName = "";
	
	$scope.pageData.params = [
	                          {
	                        	name : "searchKeyWord",
	                        	content : "作者",
	                          },
	                          {
	                        	name : "tagFilterList",
	                        	content : [2,21],
	                          },
	                          ];
	
	$scope.doTest = function(){
		var userData = { };
		
		for(var i = 0 ; i < $scope.pageData.params.length ; i ++){
			userData[$scope.pageData.params[i].name] = $scope.pageData.params[i].content;
		}
		console.log(userData);
		sessionService.postCall( 
				$rootScope.urlPath + "/" + $scope.pageData.functionName,
				userData,
				function(data){
					$scope.pageData.ret = data;
				});	
	}
	
	
	/*---正文结束---*/
	
});

//postCall demo
//var userData = {"eventId" : eventId};
//sessionService.postCall(
//		$rootScope.urlPath + "/doGetMatchEventResult.html",
//		userData,
//		function(data){
//			if(data.retCode == 0){
//				
//			}
//		},
//		$scope
//		);
