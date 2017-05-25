var zaApp = angular.module('zaApp', ['ui.bootstrap', 'angularFileUpload']);

zaApp.service('modalInitialService', function ($modal) {
	
	this.openDialog = function(dataScope){
		var modalInstance = $modal.open({
			templateUrl : '../sp/htmlTemplate/confirmDialog.html',
			//'../../sp/htmlTemplate/confirmDialog.html',
			modalFade: true,
			controller : 'dialogController',
			scope : dataScope
			
		});
		
		modalInstance.result.then(
				function(retData){			
					console.log("function one "+retData);
				},
				function(){
					console.log("function two ");
				});
	};
	
	this.openSimpleFileUploadDialog = function(dataScope){
		var modalInstance = $modal.open({
			templateUrl : '../sp/htmlTemplate/simpleFileUploadDialog.html',
			modalFade: true,
			controller : 'dialogController',
			scope : dataScope
		});
		
		modalInstance.result.then(
				function(retData){
					console.log("function one "+retData);
				},
				function(){
					console.log("function two ");
				});
	};
	
	this.openModal = function(dataScope, htmlTpl, modalController){
		
		var modalInstance = $modal.open({
			templateUrl : htmlTpl,
			//'../../sp/htmlTemplate/confirmDialog.html',
			modalFade: true,
			controller : modalController,
			scope : dataScope
			
		});
		
		modalInstance.result.then(function(retData){			
		},function(){
		});
	};
});

/**
 * 获取session的service
 */
zaApp.service('sessionService', function ($http, $rootScope) {
	
	this.getAdminUserDisplayName = function(){
		if( typeof($rootScope.currentAdminUser) == "undefined" ){
			return "n/a";
		}
		else{
			if( typeof($rootScope.currentAdminUser.realName) == "undefined" || $rootScope.currentAdminUser.realName.length == 0){
				return $rootScope.currentAdminUser.username;
			}
			else{
				return $rootScope.currentAdminUser.realName;
			}
		}
	}
	
	this.postCall = function(url, userData, callBack, theCallerScop){
		
		if( typeof(theCallerScop) == "undefined" ){
			theCallerScop = angular.element(document.body).scope(); 
		}
		
		if( typeof(theCallerScop) != "undefined")
			theCallerScop.isLoadingShow = true;
		
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			console.log("call:["+url+"]\r\n");
			console.log(data);
			
			if( typeof(theCallerScop) != "undefined")
				theCallerScop.isLoadingShow = false;
			
			callBack(data);
		})
		.error(function(data, status, headers, config){
			
			if( typeof(theCallerScop) != "undefined")
				theCallerScop.isLoadingShow = false;
			
			callBack(data);
        	alert("error: "+url+" failed");
        	});
	};
});


zaApp.service('QueryParamService', function ($location) {

	this.getParam = function(fullUrl){
		var param = {};
		var url = $location.absUrl().split('?');
		
		if( typeof(fullUrl) != "undefined"){
			url = fullUrl.split('?');
		}
		
		if( url.length > 1){
			url = url[1].split('&');
			for(var i = 0 ; i < url.length ; i++){
				var pa = url[i].split('=');
				if( pa.length == 2)
					param[pa[0]] = window.decodeURIComponent(pa[1]); 
			}
		}
		return param;
	};
	
	this.absUrl = function(){
		return $location.absUrl();
	};
	
	this.getServerName = function(){
		return $location.protocol()+"://"+location.host;
	}
	
	this.getPath = function(){
		var path = $location.absUrl().split('/');
		console.log("path="+path);
		var ret = "";
		for(var i = 3 ; i < path.length - 1 ; i++){
			ret += "/" + path[i];
		}
		console.log(ret);
		return ret;
	}
	
	this.getPWD = function(urlBase){
		if( typeof( urlBase ) == "undefined"){
			var host = this.getServerName();
			var urlPath = host + this.getPath();
			return urlPath;
		}
		return urlBase;
	}
	

	this.wrapAuthUrl = function(wxAppId, url, state){
		if( typeof(wxAppId) == "undefined" || wxAppId == null || wxAppId.length < 2){
			return url;
		}
		if( typeof(state) == "undefined"){
			state ="na";
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
		wxAppId +
		"&redirect_uri=" +
		window.encodeURIComponent(url)+
		"&response_type=code&scope=snsapi_userinfo&state=" +
		state +
		"#wechat_redirect";
	}
	
});


zaApp.controller('dialogController',function($scope,$modalInstance, FileUploader){

	$scope.ok = function(){
		
		var isUploading = false;
		if($scope.type == 'fileupload'){
			for(var i = 0 ; i < $scope.uploader.queue.length; i++){
				if( !$scope.uploader.queue[i].isUploaded ){
					if( typeof($scope.$parent.generalFormData) != "undefined")
						$scope.uploader.queue[i].formData.push($scope.$parent.generalFormData);
					$scope.uploader.queue[i].upload();
					isUploading = true;
				}
			}
		}
		if( $scope.type != 'fileupload' || !isUploading){
			$modalInstance.close();
		}
		$scope.retOK();
	};
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
		$scope.retCancel();
	}
	
	if($scope.type == 'fileupload'){
		
		$scope.$parent.onFileSelect = function(files){
			console.log(files);
		}
		
		$scope.$parent.uploader = new FileUploader({
	        url: $scope.uploadUrl,
	    });
		
		$scope.$parent.uploader.queueLimit = $scope.queueLimit;
		
		$scope.doUpload = function(item){	
			if( typeof($scope.$parent.generalFormData) != "undefined"){
				item.formData.push($scope.$parent.generalFormData);
			}
			item.upload();
        };
		
		$scope.$parent.uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        $scope.$parent.uploader.onAfterAddingFile = function(fileItem) {
            console.info('onAfterAddingFile', fileItem);
        };
        $scope.$parent.uploader.onAfterAddingAll = function(addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        $scope.$parent.uploader.onBeforeUploadItem = function(item) {
            console.info('onBeforeUploadItem', item);
        };
        $scope.$parent.uploader.onProgressItem = function(fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
        };
        $scope.$parent.uploader.onProgressAll = function(progress) {
            console.info('onProgressAll', progress);
        };
        $scope.$parent.uploader.onSuccessItem = function(fileItem, response, status, headers) {
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        $scope.$parent.uploader.onErrorItem = function(fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };
        $scope.$parent.uploader.onCancelItem = function(fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
        };
        $scope.$parent.uploader.onCompleteItem = function(fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
            console.log(fileItem);
            console.log(response);
            console.log(status);
            console.log(headers);
            if( typeof($scope.$parent.uploadRet) == "undefined"){
            	$scope.$parent.uploadRet = [];
            }
            $scope.$parent.uploadRet.push({
            	localFileName:fileItem,
            	remoteResponse:response,
            	status:status,
            	});
            if( typeof($scope.$parent.updateResult) != "undefined")
            	$scope.$parent.updateResult(fileItem, response, status);
        };
        $scope.$parent.uploader.onCompleteAll = function() {
            console.info('onCompleteAll');
        };
		
	}
});






//var modalDefaults = {
//backdrop: true,
//keyboard: true,
//modalFade: true,
//templateUrl: '/app/partials/modal.html'
//};
//
//var modalOptions = {
//closeButtonText: 'Close',
//actionButtonText: 'OK',
//headerText: 'Proceed?',
//bodyText: 'Perform this action?'
//};