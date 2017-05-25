zaApp.controller('verbalListController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'VERBAL_LIST_';
	$rootScope.disableSelectedMenu = true;
	var param = {};
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		param = QueryParamService.getParam();
		$scope.getBooksList();
	}
	
	$scope.getBooksList = function(){
		var userData = {
				start : getCurrentIndex($scope.pageIndex),
				pageSize : $scope.pageSize,
				isWarehouse : param.isWarehouse,
				isVerbalOnly : true,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.books = data.retData.books;
						$scope.pageData.totalNumber = data.retData.totalNumber;
						console.log($scope.pageSize);
						$scope.pageNumber = Math.ceil($scope.pageData.totalNumber/$scope.pageSize)
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
	
	$scope.gotoVerbalDetail = function(bookid){
		window.location.href = $rootScope.urlPath + "/verbalDetail.html?bookId="+bookid;
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
		$scope.getBooksList();
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.title= $rootScope.title;
	$scope.pageIndex = 0;
	$scope.pageNumber = 0;
	$scope.pageSize = 50;
	
	$scope.pageData.books = [
	                           {
	                        	   id	: 1,												
	                        	   uploaderHeadImgUrl : "http://wx.qlogo.cn/mmopen/Q3auHgzwzM4eGl7gKRiaibZuApvw1Uk7RqvyOEB6ibAH2puSsLicFWgh1KHPFEPkmwsZgdwgI8uvg495Fbj2p7aGYg/0",										
	                        	   uploaderWxOpenId : "asdfasdf",								
	                        	   coverImgUrl : "http://wx.qlogo.cn/mmopen/Q3auHgzwzM4eGl7gKRiaibZuApvw1Uk7RqvyOEB6ibAH2puSsLicFWgh1KHPFEPkmwsZgdwgI8uvg495Fbj2p7aGYg/0",							
	                        	   bookName : "asdfagasd",							
	                        	   publisherName : "asdfa",					
	                        	   bookCode : "abc",	
	                        	   isAdminAllowed01 : true,
	                        	   isAdminAllowed02 : true,
	                        	   
	                           },
	                           {
	                        	   id	: 1,												
	                        	   uploaderHeadImgUrl : "http://wx.qlogo.cn/mmopen/Q3auHgzwzM4eGl7gKRiaibZuApvw1Uk7RqvyOEB6ibAH2puSsLicFWgh1KHPFEPkmwsZgdwgI8uvg495Fbj2p7aGYg/0",										
	                        	   uploaderWxOpenId : "asdfasdf",								
	                        	   coverImgUrl : "http://wx.qlogo.cn/mmopen/Q3auHgzwzM4eGl7gKRiaibZuApvw1Uk7RqvyOEB6ibAH2puSsLicFWgh1KHPFEPkmwsZgdwgI8uvg495Fbj2p7aGYg/0",							
	                        	   bookName : "asdfagasd",							
	                        	   publisherName : "asdfa",					
	                        	   bookCode : "abc",	
	                        	   isAdminAllowed01 : true,
	                        	   isAdminAllowed02 : true,
	                        	   
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