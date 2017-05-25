zaApp.controller('adminShareDetailController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'SHARE_LIST_';
	$rootScope.disableSelectedMenu = false;
	var param = {};
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		param = QueryParamService.getParam();
		param.isWarehouse = (param.isWarehouse.toLowerCase() == "true");
		if( param.isWarehouse == true )
			$rootScope.selectedMenuCode = 'SHARE_LIST_WH_';
		else
			$rootScope.selectedMenuCode = 'SHARE_LIST_UF_';
		$scope.pageData.iframeBaseUrl = $rootScope.urlPath+"/../shareDetail.html?bookId="+param.bookId+"&isWarehouse="+param.isWarehouse;
		$scope.pageData.detailUrl = $scope.pageData.iframeBaseUrl + "&timestamp="+(new Date()).getTime();
		console.log($scope.pageData.detailUrl);
		
		$scope.getApprovalStatus(param.bookId);
		$scope.getVerbalStatus(param.bookId);
	}
	
	$scope.getApprovalStatus = function(bookId){
		var userData = {
				bookId : bookId,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetApprovalStatus.html",
				userData,
				function(data){
					console.log(data);
					if(data.retCode == 0){
						$scope.pageData.approves = data.retData;
					}
				});	
	}
	
	$scope.getVerbalStatus = function(bookId){
		var userData = {
				bookId : bookId,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetVerbalStatus.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.verbalUrl = data.retData.verbalUrl;
					}
				});	
	}
	
	$scope.approve = function(flowindex, isUnApprove){
		var userData = {
				bookId : param.bookId,
				approvelIndex : flowindex,
				isUnApprove : isUnApprove,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doApprove.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.approves = data.retData;
					}
				});	
	}
	
	$scope.addVerbalUrl = function(){
		var userData = {
				bookId : param.bookId,
				verbalUrl : $scope.pageData.verbalUrl,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doAddVerbalToBook.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.verbalUrl = data.retData.verbalUrl;
						$scope.pageData.detailUrl = $scope.pageData.iframeBaseUrl + "&timestamp="+(new Date()).getTime();
					}
				});
	}
	
	

	 
	/*----------------goto area----------------*/
	
	$scope.gotoManageComments = function(){
		window.location.href = $rootScope.urlPath + "/manageComments.html?bookId="+param.bookId+"&isWarehouse="+param.isWarehouse;
	}

	
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.approves = [
	                           {
	                        	   isApproved : false,
	                        	   approverId : 1,
	                        	   approverName: "abc",
	                        	   approvedTime : "2015年11月12日",
	                           },
	                           {
	                        	   isApproved : false,
	                        	   approverId : 1,
	                        	   approverName: "abc",
	                        	   approvedTime : "2015年11月12日",
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