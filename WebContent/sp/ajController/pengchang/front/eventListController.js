
zaApp.controller('eventListController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService){
	
	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	
	$scope.initialResult="";
	$scope.recomEventId;
	$scope.allEvents = [];
	var param = {};
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		$scope.recomEventId = param.recomEventId;
		
		if( typeof(urlBase) == "undefined")
			$rootScope.urlPath = ".";
		else
			$rootScope.urlPath = urlBase;
		
		wxJSSDKService.wxConfig($rootScope.urlPath);
		
		/**
		 * 通过更改service里面定义好的object对象来更改内容
		 */

		wxJSSDKService.parameter.wxonMenuShareAppMessage.link = QueryParamService.absUrl();

		wxJSSDKService.wxReady();
		
		
		$scope.getEventList(0,120, param.displayMode, $scope.recomEventId);
	}
	
	$scope.gotoEventDetail = function(eventId){

		if( typeof($rootScope.urlPath) == "undefined"){
			$rootScope.urlPath = ".";
		}
			
			window.location.href= $rootScope.urlPath + "/eventDetail.html?eventId="+eventId;
	}
	
	$scope.getEventList = function(startIndex, pageSize, displayMode, recomEventId){
		
		var userData = {"startIndex" : startIndex, "pageSize" : pageSize, "displayMode" : displayMode, "recomEventId" : recomEventId};
		var url = $rootScope.urlPath + "/doGetEventList.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			console.log(data);
			if( data.retCode == 0 ){
				
				//$scope.events = data.retData.events;
				//$scope.recomEvents = data.retData.recomEvents;
				
				$scope.allEvents[0] = data.retData.recomEvents; //第一个推荐的event的list
				for(var i = 0 ; i < $scope.allEvents[0].length ; i++){
					$scope.allEvents[0][i].isRecom = true;
				}
				$scope.allEvents[1] = data.retData.events; //第二个其他所有活动的event list
				
				$scope.pageSize = data.retData.pageSize;
				$scope.startIndex = data.retData.startIndex;
				$scope.thisAmount = data.retData.thisAmount;
				$scope.totalAmount = data.retData.totalAmount;
				$scope.wxAppId = data.retData.wxAppid;
				
				wxJSSDKService.parameter.wxonMenuShareAppMessage.link = QueryParamService.absUrl();
				console.log($scope.wxAppId);
				if( typeof($scope.wxAppId) != "undefined" ){
					
					wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl($scope.wxAppId, QueryParamService.absUrl());
					
				}
				
				if( typeof($scope.allEvents[0]) != "undefined" ){
					if( $scope.allEvents[0].length > 0){
						wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = $scope.allEvents[0][0].wxCardImgUrl;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.title =  $scope.allEvents[0][0].wxCardTitle;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.desc =  $scope.allEvents[0][0].wxCardDesc;
						
					}
				}
				wxJSSDKService.wxonMenuShareAppMessage();
			}
		})
		.error(function(data, status, headers, config){
        	alert("error: doGetEventList failed");
        	});
	}
	
});
	
//	$scope.recomEvents = [
//	                 {
//	                	 eventTime:"2016年12月02日", 
//	                	 title:"【带摸】活动众筹，手感之旅", 
//	                	 picUrl:"http://cpc.people.com.cn/NMediaFile/2014/0218/MAIN201402181440396908425232572.jpg", 
//	                	 desc:"带摸（英文：demo）项目是为了方便用户快速理解和使用 捧场-大家筹 功能的演示项目，支付是真实的，感激之情是真实，但是权益是假设的，敬请知晓！",
//	                	 participater: "500",
//	                	 participaterProgress: "30",
//	                	 moneyAmount: "30 万",
//	                	 moneyProgress: "30",
//	                	 leftTime: "20 天",
//	                	 leftTimeSec : 1728000,
//	                	 eventId:1,
//	                 },
//	               ];
	
	
//	$scope.events = [
//	                 {
//	                	 eventTime:"2016年12月02日", 
//	                	 title:"【带摸】活动众筹，手感之旅", 
//	                	 picUrl:"http://cpc.people.com.cn/NMediaFile/2014/0218/MAIN201402181440396908425232572.jpg", 
//	                	 desc:"带摸（英文：demo）项目是为了方便用户快速理解和使用 捧场-大家筹 功能的演示项目，支付是真实的，感激之情是真实，但是权益是假设的，敬请知晓！",
//	                	 participater: "500",
//	                	 participaterProgress: "30",
//	                	 moneyAmount: "30 万",
//	                	 moneyProgress: "30",
//	                	 leftTime: "20 天",
//	                	 leftTimeSec : 1728000,
//	                	 eventId:1,
//	                 },
//	                 
//	                 ];



//$scope.wxPay = function(){
//	wxJSPayService.wxPay($rootScope.urlPath);
//	
//	wxJSSDKService.parameter.wxonMenuShareAppMessage.title = "我是小卡片标题3";
//	wxJSSDKService.wxonMenuShareAppMessage();
//}
