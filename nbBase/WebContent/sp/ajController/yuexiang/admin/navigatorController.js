zaApp.controller('navigatorController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$scope.username = "N/A";
	$scope.menus = {};
	$scope.param = {};
	
	$rootScope.title= '上海零角互联网信息服务有限公司 后台Admin系统';
	
	$scope.initial = function(){
		
		//console.log($attrs["ngController"]); 获取当前controller的名字
		
		$rootScope.urlPath = QueryParamService.getPWD();
	
//		var userData = {
//				eventId : 1,
//		};
//		sessionService.postCall($rootScope.urlPath +"/../doTransferMoney.html", 
//				userData, 
//				function(data) {
//				if( data.retCode == 0 ){
//					
//				}else{
//					
//				}
//			
//		}, $scope);
		
		var userData = {};
		sessionService.postCall($rootScope.urlPath +"/doGetSessionStatus.html", 
				userData, 
				function(data) {
				 /**
		        retCode
		        retMessage
		        retData.userId
		        retData.username
		        retData.isAuthed
		        retData.realName
		        */
				if( data.retCode == 0 ){
					$rootScope.currentAdminUser = data.retData;
					$rootScope.isAuthed = true;
					$scope.username = sessionService.getAdminUserDisplayName();
				}else{
					$rootScope.currentAdminUser = null;
					$rootScope.isAuthed = false;
				}
			
		}, $scope);
		$scope.getUserMenu();
		
	}
	 $rootScope.selectedMenuCode = 'WX_CONFIG_';
     $scope.collapseVar = getMenuIndexFromMenuCode($scope.menus,$rootScope.selectedMenuCode);
     $scope.selectMenuId = 0;
     $scope.multiCollapseVar = 0;
     
     function getMenuIndexFromMenuCode(menuList, selectedCode){
    	 
    	 if( typeof(menuList) == "undefined" )
    		 return -1;
    	 if( menuList.length <= 0 )
    		 return -1;
    	 $scope.disableSelectedMenu =  $rootScope.disableSelectedMenu ;
    	 for(var i = 0 ; i < menuList.length ; i++){
				if( menuList[i].code.toUpperCase() == selectedCode.toUpperCase() ){
					$scope.selectMenuId = menuList[i].id;
					console.log($scope.selectMenuId);
					return i;
				}
				if( getMenuIndexFromMenuCode(menuList[i].child, selectedCode) !=  -1 ){
					return i;
				}
			}
    	 return -1;
     }
     
     $scope.check = function(x){
       if(x==$scope.collapseVar)
         $scope.collapseVar = -1;
       else
         $scope.collapseVar = x;
     };
     
//     $scope.multiCheck = function(y){
//       
//       if(y==$scope.multiCollapseVar)
//         $scope.multiCollapseVar = 0;
//       else
//         $scope.multiCollapseVar = y;
//     };

 
	
	$scope.getUserMenu = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		var userData = {};
		sessionService.postCall($rootScope.urlPath +"/doGetUserMenu.html", 
				userData, 
				function(data) {
				if( data.retCode == 0 ){
					$scope.menus = data.retData;
//					for(var i = 0 ; i < $scope.menus.length ; i++){
//						$scope.menus[i].isCollapsIn = false;
//					}
					$scope.collapseVar = getMenuIndexFromMenuCode($scope.menus,$scope.selectedMenuCode);
				}else{
					
				}
			
		}, $scope);
	}
	
	$scope.signOff = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		var userData = {};
		sessionService.postCall($rootScope.urlPath +"/doLogout.html", 
				userData, 
				function(data) {
				if( data.retCode == 0 ){
					$rootScope.isAuthed = false;
					$rootScope.currentAdminUser = null;
					window.location.href=$rootScope.urlPath+"/index.html";
				}else{
				}
			
		}, $scope);
	};
	
	
	
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