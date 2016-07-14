zaApp.controller('eventMatchResultConfirmController',function($scope, $rootScope, $http, $sce, wxJSSDKService, QueryParamService, sessionService){
	

	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	$scope.isLoadingShow = false;
	$scope.isShowSelectRival = false;
	$scope.isShowResultDialog = false;
	
	var param = {};
	
	
	$scope.initial = function(urlBase){
		console.log($scope.pageData);
		param = QueryParamService.getParam();
		$rootScope.urlPath = QueryParamService.getPWD(urlBase);
		var eventId = param.eventId;
		
		wxJSSDKService.wxConfig($rootScope.urlPath, $scope.initialAsync);
	}
	
	
	$scope.initialAsync = function(data){
		console.log(data);
		
		if( typeof(param.eventId) != "undefined" ){
			
			$scope.getMatchEventResult(param.eventId);
			
			wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl(data.wxappid, QueryParamService.absUrl());
			console.log(wxJSSDKService.parameter.wxonMenuShareAppMessage.link);
			wxJSSDKService.wxReady();
		}
	}
	
	
	$scope.getMatchEventResult = function(eventId){
		
		var userData = {"eventId" : eventId,
				"onlyNotConfirmed" : true, };
		sessionService.postCall(
				$rootScope.urlPath + "/doGetMatchEventResult.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData = data.retData;
						$scope.pageData.eventIntroduce = $sce.trustAsHtml($scope.pageData.eventIntroduce);
						$scope.pageData.eventAmount = Math.round($scope.pageData.eventAmount)/100;
						
						wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = data.retData.wxCardImgUrl;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.title =  data.retData.wxCardTitle;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.desc =  data.retData.wxCardDesc;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.link =   
												wxJSSDKService.wrapAuthUrl(
														$scope.pageData.wxAppId, 
														$rootScope.urlPath+"/eventList.html?recomEventId="+$scope.pageData.eventId
														);
						
						wxJSSDKService.wxonMenuShareAppMessage();
					}
				});
	};
	
	
	$scope.setCurrentSelected = function(headImgUrl, nickname, wxId){
		$scope.pageData.currentSelectedRivalId = wxId;
		$scope.pageData.currentSelectedRivalHeadImgUrl = headImgUrl;
		$scope.pageData.currentSelectedRivalNickname = nickname;
	}
	
	$scope.confirmResult = function(resultId){
		var userData = {
				resultId : resultId,
				eventId : $scope.pageData.eventId,
				};
		sessionService.postCall(
				$rootScope.urlPath + "/doConfirmMatchEventResult.html",
				userData,
				function(data){
					if(data.retCode == 0){
						location.reload();
					}
				});
	}
	
	
	//----------toggle area---------------------
	
	$scope.toggleShowSelectRival = function(){
		window.scrollTo(0,0);
		$scope.isShowSelectRival = ! $scope.isShowSelectRival;
	}
	
	$scope.toggleShowResultDialog = function(){
		$scope.isShowResultDialog = !$scope.isShowResultDialog;
	}
	
	$scope.toggleResultChoose =function(result){
		$scope.pageData.currentSelectedResult = result;
	}
	
	
	
	//---------goto area------------------------
	
	$scope.gotoEventDetail = function(){
		window.location.href = $rootScope.urlPath+"/eventDetail.html?eventId="+$scope.pageData.eventId;
	}
	
	
	
	//------------data area-----------------

	$scope.pageData = {};
	$scope.pageData.eventId = 0;
	$scope.pageData.isSample = 0;
	$scope.pageData.openId = null;
	$scope.pageData.isUserPlayer = false;
	$scope.pageData.wxAppId = null;
	$scope.pageData.wxCardTitle = "小卡片";
	$scope.pageData.wxCardDesc = "小卡片的详情";
	$scope.pageData.wxCardImgUrl = "http://pc.0angle.com/sp/img/5.jpg";
	$scope.pageData.myPlayerId = 4;
	$scope.pageData.matchResult = [
	                               {
	                            	   playerA:{
	                            		   wxId: 3,
		                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
		                            	   nickname: "A昵称昵称",
	                            	   },
	                            	   playerB:{
	                            		   wxId: 3,
		                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
		                            	   nickname: "B昵称昵称",
	                            	   },
	                            	   result : 1, //0 未知  1 a 胜b 2 a平b 3 a负b
	                            	   isPlayerBConfirmed : false,
	                               },
	                               {
	                            	   playerA:{
	                            		   wxId: 3,
		                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
		                            	   nickname: "A昵称昵称",
	                            	   },
	                            	   playerB:{
	                            		   wxId: 3,
		                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
		                            	   nickname: "B昵称昵称",
	                            	   },
	                            	   result : 1, //0 未知  1 a 胜b 2 a平b 3 a负b
	                            	   isPlayerBConfirmed : true,
	                               },
	                               ];
	$scope.pageData.playerList = [
	                               {
	                            	   wxId: 3,
	                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
	                            	   nickname: "A昵称昵称",
	                               },
	                               {
	                            	   wxId: 3,
	                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
	                            	   nickname: "A昵称昵称",
	                               },
	                               {
	                            	   wxId: 3,
	                            	   headImgUrl: "http://pc.0angle.com/sp/img/5.jpg",
	                            	   nickname: "A昵称昵称",
	                               },
	                             ];
	$scope.pageData.currentSelectedRivalId = 3;
	$scope.pageData.currentSelectedRivalHeadImgUrl = "";
	$scope.pageData.currentSelectedRivalNickname = "";
	$scope.pageData.currentSelectedResult = 2;

})