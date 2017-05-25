var zaApp = angular.module('zaApp', ['ui.bootstrap', 'angularFileUpload', 'ngMd5','ngAnimate']);

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

