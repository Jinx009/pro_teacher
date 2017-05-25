zaApp.controller('wxConfigDetailController',function(
		$scope, 
		FileUploader, 
		$rootScope, 
		$http, 
		$location, 
		sessionService, 
		QueryParamService,
		modalInitialService
		){

	$rootScope.selectedMenuCode = 'WX_CONFIG_';
	$rootScope.disableSelectedMenu = false;
	$scope.param = {};
	
	$scope.initial = function(){
		$rootScope.urlPath = QueryParamService.getPWD();
		$scope.param = QueryParamService.getParam();
		
		var userData = { configId: $scope.param.configId, };
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetWxConfigDetail.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.wxConfig = data.retData;
					}
				});	
		
	}
	 
	/*-----------function area-----------------*/
	$scope.saveConfig = function(){
		$scope.showConfirmDialog("目前暂时仅支持程序员手动修改数据库。", "error");
		return;
		var userData = { configure: $scope.pageData.wxConfig };
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveWxConfigDetail.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.wxConfig = data.retData;
					}
				});	
	}
	
	/*----------------goto area----------------*/
	
	$scope.gotoWxConfigList = function(configId){
		window.location.href = $rootScope.urlPath + "/wxConfigList.html";
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.wxConfig = {
//	                        	   id	: 1,												
//	                        	   payKey : "asdfasdf",										
//	                        	   mchId : "asdfasdf",								
//	                        	   wxappid : "adfasdfasdf",							
//	                        	   certFileP12 : "asdfagasd",							
//	                        	   serverDefaultIp : "asdfa",					
//	                        	   appSecret : "abc",							
//	                        	   orderDefault : " ",								
//	                        	   wxPayNotifyUrl : " ",						
//	                        	   encodingAesKey : " ",						
//	                        	   configName : "这里是公众号名称",					
//	                        	   isActive : true,										
//	                        	   isDefault : false,									
//	                        	   serverName : "",									
//	                        	   tplmsgPaySuccess : "",					
//	                        	   resourcePath : "",								
//	                        	   resourceBrowsPath : "",					
//	                        	   certP12Bin : "",								
//	                        	   companyName : "这里是公司名称",								
//	                        	   companyLogoUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",						
//	                        	   tplmsgCfResult : "",						
//	                        	   currentPageToken : "",					
//	                        	   currentPageTokenExpire : "",		
//	                        	   tplmsgMatchResultConfirm : "",

	                           }; 
			
	
	
	
	/*----------------对话框 area----------------*/
	
	
	$scope.showConfirmDialog = function(message, type){
		var confirmDialogScope = $rootScope.$new();
		confirmDialogScope.type = type; //"error"; "confirm";"fileupload"
		confirmDialogScope.confirmMessege = message;
		confirmDialogScope.retOK = function(){
			
		};
		confirmDialogScope.retCancel = function(){
			console.log("cancel");
		};
		/*创建确认对话框数据段结束*/
		
		modalInitialService.openDialog(
				confirmDialogScope);
	};
	
	$scope.showSimpleFileUploadDialog = function(){
		var dialogScope = $rootScope.$new();
		dialogScope.generalFormData = {wxConfigId:$scope.pageData.wxConfig.id};
		dialogScope.queueLimit = 1;
		dialogScope.uploadUrl = $rootScope.urlPath + "/doSaveP12BinFile.html";
		dialogScope.type = "fileupload";
		dialogScope.retOK = function(){
			console.log("showSimpleFileUploadDialog is button OK");
			console.log();
		};
		dialogScope.retCancel = function(){
			console.log("showSimpleFileUploadDialog is button cancel");
		};
		dialogScope.updateResult = function(fileItem, response, status){
			if( response.retCode == 0){
				$scope.pageData.wxConfig.certFileP12 = response.retData[0];
			}
		}
		/*创建确认对话框数据段结束*/
		
		modalInitialService.openSimpleFileUploadDialog(dialogScope);
	}
	
	
	
})