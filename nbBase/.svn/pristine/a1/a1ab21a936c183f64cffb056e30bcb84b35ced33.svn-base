zaApp.controller('wxUserListController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'WX_USERS_';
	$rootScope.disableSelectedMenu = true;
	
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		$scope.getUserList();
	}
	
	$scope.getUserList = function(){
		var userData = {
				start : getCurrentIndex($scope.pageIndex),
				pageSize : $scope.pageSize,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetWxUserList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData = data.retData;
						console.log($scope.pageSize);
						$scope.pageNumber = Math.ceil($scope.pageData.totalUserNumber/$scope.pageSize)
						$scope.pageIndexs = [];
						console.log($scope.pageNumber);
						for(var i = 0 ; i < $scope.pageNumber; i++){
							$scope.pageIndexs.push(i+1);
						}
						console.log($scope.pageIndexs);
					}
				});	
	}
	
	function getCurrentIndex(pageIndex){
		return $scope.pageSize * pageIndex;
	}
	 
	/*----------------goto area----------------*/
	
	$scope.gotoConfigDetail = function(configId){
		window.location.href = $rootScope.urlPath + "/wxConfigDetail.html?configId="+configId;
	}
	
	$scope.showPrePage = function(){
		if( $scope.pageIndex == 0 )
			return;
		$scope.pageIndex -= 1;
		$scope.getUserList();
	}
	
	$scope.showNextPage = function(){
		if( $scope.pageIndex == ($scope.pageNumber-1) )
			return;
		$scope.pageIndex += 1;
		$scope.getUserList();
	}
	
	$scope.showPage = function(index){
		if( $scope.pageIndex == index ||
			index < 0 ||
			index >= $scope.pageNumber)
		
			return;
		
		$scope.pageIndex = index;
		$scope.getUserList();
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.title= $rootScope.title;
	$scope.pageIndex = 0;
	$scope.pageNumber = 0;
	$scope.pageSize = 50;
	
	$scope.pageData.users = [
	                           {
	                        	   id	: 1,												
	                        	   userInfoId : "asdfasdf",										
	                        	   wxOpenId : "asdfasdf",								
	                        	   wxAppName : "adfasdfasdf",							
	                        	   wxNickName : "asdfagasd",							
	                        	   wxSex : "asdfa",					
	                        	   headImgUrl : "abc",							
	                           },
	                           {
	                        	   id	: 1,												
	                        	   userInfoId : "asdfasdf",										
	                        	   wxOpenId : "asdfasdf",								
	                        	   wxAppName : "adfasdfasdf",							
	                        	   wxNickName : "asdfagasd",							
	                        	   wxSex : "asdfa",					
	                        	   headImgUrl : "abc",							
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