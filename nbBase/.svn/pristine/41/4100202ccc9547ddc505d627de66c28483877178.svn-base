zaApp.controller('resultMsgController',function($scope, $rootScope, QueryParamService){
	
	$scope.isOK = false;
	
	$scope.msgTitle = "操作成功";
	$scope.msgDesc = "提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容";
	$scope.url = "";
	$scope.buttonText = "";
	var param = {};
	
	$scope.initial = function(){
		param = QueryParamService.getParam();
		$scope.currentEventId = param.eventId;
		$scope.toUrl = "./eventDetail.html";
		if( typeof(param.isEarn) != "undefined" && param.isEarn==true)
			$scope.toUrl = "./earnEvents.html";
		
		$scope.display(param.showSet);
	}
	
	
	$scope.msgLib = function(){
		
		return {
			"paySuccess" : {
				"msgTitle" : "支付成功！",
				"msgMsg" : "谢谢您的参与，请通过服务号给您的消息查看订单详情。",
				"okIcon" : true,
				"url" : $scope.toUrl+"?eventId="+param.eventId,
				"buttonText" : "确定",
			},
			
			"payFail": {
				"msgTitle" : "支付失败！",
				"msgMsg" : "支付发生错误，请返回重试。",
				"okIcon" : false,
				"url" : $scope.toUrl+"?eventId="+param.eventId,
				"buttonText" : "返回",
			},
		};
		
	};
	
	$scope.display = function(template){
		
		console.log(template);
		$scope.isOK = $scope.msgLib()[template].okIcon;
		$scope.msgTitle = $scope.msgLib()[template].msgTitle;
		$scope.msgDesc = $scope.msgLib()[template].msgMsg;
		$scope.url = $scope.msgLib()[template].url;
		$scope.buttonText = $scope.msgLib()[template].buttonText;
	}
	
})