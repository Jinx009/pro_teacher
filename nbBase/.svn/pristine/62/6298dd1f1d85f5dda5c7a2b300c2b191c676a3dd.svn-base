zaApp.controller('eventDetailController',function($scope, $rootScope, $http, $sce, wxJSSDKService, QueryParamService){
	

	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	
	$scope.isLoadingShow = true;
	$scope.eventId = 0;
	$scope.eventRuleId = 0;
	$scope.openId = "";
	$scope.isSample = false;
	$scope.isPopupMenu = false;
	$scope.initialResult="";
	$scope.articlUrl = "";
	$scope.bbsUrl = "";
	var param = {};
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		
		$rootScope.urlPath = urlBase;
		if( typeof( urlBase ) == "undefined"){
			$rootScope.host = QueryParamService.getServerName();
			$rootScope.urlPath = $rootScope.host + QueryParamService.getPath();
			console.log($rootScope.urlPath);
		}
		
		
		var eventId = param.eventId;
		$scope.getEvent(eventId);
	
		
		wxJSSDKService.wxConfig($rootScope.urlPath, function(data){
			/**
			 * 通过更改service里面定义好的object对象来更改内容
			 */
			console.log(data);
			wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl(data.wxappid, QueryParamService.absUrl());
			console.log(wxJSSDKService.parameter.wxonMenuShareAppMessage.link);
			wxJSSDKService.wxReady();
		});
	}
	
	$scope.gotoPayDetail = function(eventRuleId, unitPrice, desc){
		//$state.go('./producer.html', {'eventId': $scope.eventId, 'eventRuleId' : $scope.eventRuleId, 'openId' : $scope.openId } );
		if( typeof(unitPrice) == "undefined"){
			unitPrice = 0;
		}
		var jumpUrl = $rootScope.urlPath+'/payTheEvent.html?eventId='+$scope.eventId+'&eventRuleId='+eventRuleId+'&unitPrice='+unitPrice+"&desc="+desc;

		window.location.href = jumpUrl;
	}
	
//	var inputCopyNumberDialogScope = $rootScope.$new();
//	inputCopyNumberDialogScope.ruleId = 0;
//	inputCopyNumberDialogScope.copyNumber = 1;
//	inputCopyNumberDialogScope.retOK = function(){
//		$scope.copies.copyNumber = inputCopyNumberDialogScope.copyNumber ;
//		$scope.copies.ruleId = inputCopyNumberDialogScope.ruleId;
//	};
//	inputCopyNumberDialogScope.retCancel = function(){
//		console.log('haha cancel');
//	};
//	
//	$scope.inputCopyNumberDialog = function(){
//		inputCopyNumberDialogScope.copyNumber = $scope.copies.copyNumber;
//		inputCopyNumberDialogScope.ruleId = $scope.copies.ruleId;
//		modalInitialService.openModal(inputCopyNumberDialogScope, $rootScope.urlPath +'/sp/htmlTemplate/inputCopies.html', 'dialogController');
//	};
	
	
	
	$scope.togglePopupMenu = function(){
		$scope.isPopupMenu = !$scope.isPopupMenu;
	}

	$scope.wxPay = function(){
		wxJSPayService.wxPay($rootScope.urlPath);
	}
	
	$scope.contentHtml = "";
	$scope.getContentViaUrl = function(){ 
		
		var payParameter;
		var userData = {"url" : $scope.articlUrl};
		var url = $rootScope.urlPath + "/doCurl.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			
			var content = data.retData.content;
			content = content.replace(/http:\/\/mmbiz.qpic.cn/g,"doGetProtectedPicStream.html?target=http://mmbiz.qpic.cn");
			//content = content.replace(/http:\/\/mmbiz.qpic.cn/g,"doGetProtectedPicStream.html?target=http://mmbiz.qpic.cn");
			content = content.replace(/data-src/g,"src");
			
			$scope.contentHtml = $sce.trustAsHtml(content);
			$scope.isLoadingShow = false;
			console.log($scope.isLoadingShow);
		})
		.error(function(data, status, headers, config){
        	alert("error: doCurl failed");
        	});
	};
	
	
	
	
	$scope.getEvent = function(eventId){

		var userData = {"eventId" : eventId };
		var url = $rootScope.urlPath + "/doGetEvent.html";
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			console.log(data);
			if( data.retCode == 0 ){
				$scope.eventId = data.retData.eventId;
				$scope.rules = data.retData.rules;
				$scope.articlUrl = data.retData.artUrl;
				console.log($scope.articlUrl);
				if( $scope.articlUrl == "simpleDetail"){
					$scope.articlUrl = $scope.urlPath + "/simpleDetail.html?eventId="+$scope.eventId;
				}
				
				$scope.isSample = data.retData.isSample;
				$scope.bbsUrl = $scope.urlPath + "/messageBoard.html?eventId=" + $scope.eventId;
				
						/**
						 * 开始包装转发小卡片
						 */
						wxJSSDKService.parameter.wxonMenuShareAppMessage.link = QueryParamService.absUrl();
						if( typeof($scope.wxAppId) != "undefined" ){
							wxJSSDKService.parameter.wxonMenuShareAppMessage.link = 
									"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
									$scope.wxAppId +
									"&redirect_uri=" +
									window.encodeURIComponent(QueryParamService.absUrl())+
									"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
							
							$scope.bbsUrl = 
								"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
								$scope.wxAppId +
								"&redirect_uri=" +
								window.encodeURIComponent($scope.bbsUrl)+
								"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
						}

						wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = data.retData.wxCardImgUrl;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.title =  data.retData.wxCardTitle;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.desc =  data.retData.wxCardDesc;
						wxJSSDKService.wxonMenuShareAppMessage();
						
				
				$scope.getContentViaUrl();
			}
		})
		.error(function(data, status, headers, config){
        	alert("error: doGetEvent failed");
        	});
	};
	
	$scope.goToEventStatus = function(eventId){
		window.location.href = $scope.urlPath+"/eventStatus.html?eventId="+eventId;
	}
	
	$scope.goToEventBBS = function(eventId){
		window.location.href = $scope.bbsUrl;
	}
	
	
	$scope.rules = [
	                {unitPrice:1, ruleId: 1, ruleDesc:"捧：1元，无私奉献"},
	                {unitPrice:2, ruleId: 1, ruleDesc:"捧：2元，得到红包抽奖机会"},
	                {unitPrice:30, ruleId: 1, ruleDesc:"捧：30元，H5动态页面开发"},
	                {unitPrice:50, ruleId: 1, ruleDesc:"捧：50元，参加6月专家座谈会"},
	                ];
	
})