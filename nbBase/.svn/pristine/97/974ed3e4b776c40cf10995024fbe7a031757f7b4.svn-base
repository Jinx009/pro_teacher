 zaApp.controller('headerController', function($scope, $rootScope, $http, sessionService, QueryParamService) {
	
	$scope.retCode = 0;
	$scope.retMessage = "";
	$scope.retData = null;
	
	$scope.isAuthed = false;//$scope.retData.isAuthed;
	$scope.username = null;//$scope.retData.username;
	$scope.userId = null;//$scope.retData.userId;
	$scope.showname = null;//$scope.retData.username;
	
	$scope.inital = function(urlPath){
		$rootScope.urlPath = QueryParamService.getPWD();
		var userData = {};
		var url = urlPath+"/doGetSessionStatus.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
   
         /**
        retCode
        retMessage
        retData.userId
        retData.username
        retData.isAuthed
        retData.realName
        */
		.success(function(data, status, headers, config){
			$scope.retCode = data.retCode;
			$scope.retMessage = data.retMessage;
			$scope.retData = data.retData;
			
			   $scope.isAuthed = $scope.retData.isAuthed;
			   $scope.username = $scope.retData.username;
			   $scope.userId = $scope.retData.userId;
			   if( $scope.retData.realName != null ){
				   $scope.showname = $scope.retData.realName;
			   }else{
				   $scope.showname = $scope.username;
			   }
			})
        	
        .error(function(data, status, headers, config){
        	alert("error: function call failed");
        	});
	};
	
	
	$scope.logout = function(urlPath){
		var userData = {};
		var url = urlPath+"/doLogout.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        
        $http.post(url, data, postCfg)
         /**
        retCode
        retMessage
        */
		.success(function(data, status, headers, config){
			$scope.retCode = data.retCode;
			$scope.retMessage = data.retMessage;
			$scope.isAuthed = false;
			window.location.href=urlPath+"/index.html";
			})
        	
        .error(function(data, status, headers, config){
        	alert("error: function call failed");
        	});
	};
	
	});