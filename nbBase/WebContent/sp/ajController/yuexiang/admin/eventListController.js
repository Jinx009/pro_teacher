zaApp.controller('eventListController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'EVENT_CREATE_';
	$rootScope.disableSelectedMenu = true;
	
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		var userData = {};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEventList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.events = data.retData;
					}
				});	
		
	}
	 
	/*----------------goto area----------------*/
	
	$scope.gotoConfigEvent = function(eventId){
		window.location.href = $rootScope.urlPath + "/createNewEvent.html?eventId="+eventId;
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.title= $rootScope.title;
	$scope.pageData.events = [
	                           {
	                        	   id	: 1,												
	                        	   eventTitle : "asdfasdf",										
	                        	   eventDesc : "asdfasdf",								
	                        	   eventHeadImgUrl : "adfasdfasdf",							
	                        	   eventWxConfigName : "asdfagasd",							
	                           },
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