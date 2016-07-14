zaApp.controller('wxConfigureListController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'WX_CONFIG_';
	$rootScope.disableSelectedMenu = true;
	
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		var userData = {};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetWxConfigList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.configs = data.retData;
					}
				});	
		
	}
	 
	/*----------------goto area----------------*/
	
	$scope.gotoConfigDetail = function(configId){
		window.location.href = $rootScope.urlPath + "/wxConfigDetail.html?configId="+configId;
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.title= $rootScope.title;
	$scope.pageData.configs = [
//	                           {
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
//	                        	   isActive : "",										
//	                        	   isDefault : "",									
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
//
//	                           },
//	                           {
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
//	                        	   isActive : "",										
//	                        	   isDefault : "",									
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
//
//	                           }
	                           ]; 
			
	
	
	
	/*----------------对话框 area----------------*/
	
	
	$scope.showConfirmDialog = function(message, type){
		var confirmDialogScope = $rootScope.$new();
		confirmDialogScope.type = type; //"error"; "confirm";
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
	
	
	
})