
zaApp.controller('showEntryUrlController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
					/**---------------------------
					 * 预设的控制字段和初始化函数
					 */
					$scope.isWxReady = 0;
					$scope.isWxConfigReady = 1;
					$scope.isErrorDialogShow = false;
					$scope.isConfirmDialogShow = false;
					$scope.errorMessage = "";
					$scope.isLoadingShow = false;
					$scope.isBodyReady = false;
					$scope.sessionData = {};
					var param = {};
					
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$rootScope.urlPath = QueryParamService.getPWD(urlBase);
						wxJSSDKService.wxConfig($rootScope.urlPath, function(data){
							$scope.sessionData = data;
							//wxJSSDKService.wrapWxCard($scope.sessionData.wxappid, "wxCardImgUrl", "wxCardTitle", "wxCardDesc", true);
							console.log($scope.sessionData);
							$scope.initialAsync(data);
						});
					}
					/**----end--------------------
					 * 预设的控制字段和初始化函数
					 */
	
	/*---正文开始---*/			
	/*------function area-------*/
	$scope.initialAsync = function(data){ //本方法固定存在
		var server = $rootScope.urlPath;
		$scope.pageData.urls = 
			[
			   {
				   name : "查看所有的分享的列表",
				   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
						   server + "/shareList.html"),
			   },
			   
			   {
				   name : "创建一个新的分享",
				   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
						   server + "/shareAdd.html"),
			   },
			   {
				   name : "专家问答入口",
				   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
						   server + "/qaList.html"),
			   },
			   {
				   name : "有声读物列表",
				   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
						   server + "/verbalBookList.html"),
			   },
			   {
				   name : "有声文章列表",
				   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
						   server + "/verbalArticleList.html"),
			   },
			   {
				   name : "查看积分入口",
				   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
						   server + "/pointsInfo.html"),
			   },
			   {
				   name : "后台入口(admin/admin)",
				   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
						   server + "/admin/index.html"),
			   },
			 ];
		console.log($scope.pageData.urls);
	}

	
	
	/*------goto area-------*/
	
	
	/*------toggle area-------*/
	
	
	/*------data area-------*/
	
	$scope.pageData = {};
	
	
	
	
	
	
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
