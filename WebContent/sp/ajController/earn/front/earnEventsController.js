
zaApp.controller('earnEventsController',function($scope, $rootScope, $http, wxJSSDKService, sessionService, QueryParamService){
	
	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	$scope.isToPayDialogShow = false;
	$scope.isToPlayDialogShow = false;
	$scope.isMessageDialogShow = false;
	$scope.isLoadingShow = false;
	$scope.isAskToFollowDialogShow = false;
	$scope.earners=[];
	

	$scope.eventList = [];
	
	var param = {}
	;
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		
		 
		$rootScope.urlPath = QueryParamService.getPWD(urlBase);
		
		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
		
		/**
		 * 通过更改service里面定义好的object对象来更改内容
		 */
	}
	
	
	function initialAsync(data){
		
		if( typeof(param.recomEventId == "undefined") ){
			param.recomEventId = 7;
		}
		
		
		wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl("wx9b3d1cb48fb48ff4", "http://pc.0angle.com/earnEvents.html?"+
				(typeof(param.recomEventId == "undefined") ? "" : "recomEventId="+param.recomEventId));
		wxJSSDKService.parameter.wxonMenuShareAppMessage.title = "背单词要你钱 3000";
		wxJSSDKService.parameter.wxonMenuShareAppMessage.desc = "从GRE 3000词中随机抽取 20 个单词，每个词押金 0.1 元，每答对一个，可获得 0.2 元红包。";
		wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = "http://pc.0angle.com/sp/img/0angle_icon.png";
		wxJSSDKService.wxonMenuShareAppMessage();
		
		
		$scope.userInfo = data;
		console.log($scope.userInfo);
		
		
		
		var userData = 
			{ recomEventId : param.recomEventId,
			  
			};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEarnEvents.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.eventList = data.retData.eventList;
						$scope.earners = data.retData.earnerList;
					}
				}, $scope);		
	}
	
	$scope.currentSelect={
			eventId : 0,
			retMessage : "",
			ruleId : 0,
			unitPrice : 0,
			ruleTitle : "",
			orderId : 0,
	}
	
	$scope.gotoEventDetail = function(eventId){
		var userData = 
			{ eventId : eventId,
			};
		$scope.currentSelect.selectedEventId = eventId;
		
		sessionService.postCall( 
				$rootScope.urlPath + "/doCheckUserStatusOfThePlay.html",
				userData,
				function(data){
					if(data.retCode == 0 || data.retCode == 24 ){//超过次数了错误码
						
						$scope.currentSelect.eventId = data.retData.id;
						$scope.currentSelect.unitPrice = typeof(data.retData.payAmountInCent)=="undefined" ? 0: data.retData.payAmountInCent/100;
						$scope.currentSelect.retMessage = data.retData.retMessage;
						$scope.currentSelect.ruleId = data.retData.ruleId;
						$scope.currentSelect.ruleTitle = data.retData.ruleTitle;
						$scope.currentSelect.orderId = data.retData.orderId;
						$scope.currentSelect.correctNumber = data.retData.correctNumber;
						$scope.currentSelect.totalNumber = data.retData.totalNumber;
						$scope.currentSelect.playedTimes = data.retData.playedTimes;
						$scope.currentAward = Math.round($scope.currentSelect.correctNumber*0.2*100)/100;
						
						
						// 返回的状态有： error ; OK 有一个order说明有一个进行中的; OK 没有order 没有正在进行中的
						if( typeof(data.retData.orderId) == "undefined" || data.retCode == 24){//没有正在进行中的
							$scope.isToPayDialogShow = true;
						}else{//有正在进行中的
							$scope.isToPlayDialogShow = true;
						}

					}else{//出错了
							$scope.currentSelect.retMessage = data.retMessage;
							$scope.isMessageDialogShow = true;
					}
				}, $scope);		
		
		//	window.location.href= $rootScope.urlPath + "/earnEventPlay.html?eventId="+eventId;
	}
	
	$scope.startPlay = function(){
		
		var jumpUrl = $rootScope.urlPath+'/earnEventPlay.html?'+
		'orderId='+$scope.currentSelect.orderId;
		window.location.href = jumpUrl;
	}
	
	$scope.gotoWxPay = function(){
		
			var jumpUrl = $rootScope.urlPath+'/payTheEvent.html?'+
			'eventId='+$scope.currentSelect.eventId+
			'&eventRuleId='+$scope.currentSelect.ruleId +
			'&unitPrice='+$scope.currentSelect.unitPrice +
			"&desc="+$scope.currentSelect.ruleTitle +
			"&isEarn=true";

			window.location.href = jumpUrl;
	}
	
	$scope.toggleToPayDialog = function(){
		$scope.isToPayDialogShow = false;
	}
	$scope.toggleToPlayDialog = function(){
		$scope.isToPlayDialogShow = false;
	}
	
	$scope.toggleMessageDialog = function(){
		$scope.isMessageDialogShow = false;
	}
	
	$scope.toggleAskToFollowDialog = function(){
		$scope.isAskToFollowDialogShow = false;
	}
	
	$scope.getAward = function(){
		
//		$scope.doSendAward();
		var userData = 
		{ orderId : $scope.currentSelect.orderId ,
		};
		$scope.isLoadingShow = true;
		sessionService.postCall( 
			$rootScope.urlPath + "/doCheckIfOpenIdIsUser.html",
			userData,
			function(data){
				if( typeof( data.retData) != "undefined"  && typeof( data.retData.isUser) != "undefined"){
					$scope.isFollowedUser = data.retData.isUser;
				}
				else{
					$scope.isFollowedUser = false;
				}
				
				if( $scope.isFollowedUser == true ){
					$scope.isLoadingShow = false;
					$scope.doSendAward();
				}else{
					$scope.isLoadingShow = false;
					$scope.isAskToFollowDialogShow = true;
				}
				
			});
		
	}
	
	$scope.doSendAward = function(){
		var userData = 
		{ orderId : $scope.currentSelect.orderId ,
		};
		sessionService.postCall( 
			$rootScope.urlPath + "/doSendEarnEventAward.html",
			userData,
			function(data){
				if(data.retCode == 0){
					window.location.href = wxJSSDKService.wrapAuthUrl("wx9b3d1cb48fb48ff4", "http://pc.0angle.com/earnEvents.html");;
				}else{
					$scope.isMessageDialogShow = true;
					$scope.currentSelect.retMessage = data.retMessage;
				}
			}, $scope);
	}

	
});
