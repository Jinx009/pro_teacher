
zaApp.controller('homeController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService, FileUploader){
	
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
					
	$scope.uploader = new FileUploader({
        url: 'saveFormFile.html',
    });
	
	$scope.uploader.queueLimit = 2;			
	
	$scope.doUpload = function(item, formData){	
		if( typeof(formData) != "undefined"){
			item.formData.push(formData);
		}
		item.upload();
    };
    
    $scope.doUploadAll = function(formData){	
		$scope.uploader.uploadAll();
    };
					
	$scope.initialAsync = function(data){ //本方法固定存在

	}

	
	
	/*------goto area-------*/
	
	
	/*------toggle area-------*/
	
	
	/*------data area-------*/
	
	
	
	
	
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
