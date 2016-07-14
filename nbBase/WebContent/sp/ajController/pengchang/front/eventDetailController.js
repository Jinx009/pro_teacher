zaApp.controller('eventDetailController',function($scope, $rootScope, $http, $sce, wxJSSDKService, QueryParamService, sessionService){
	

	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	$scope.isLoadingShow = false;
	$scope.isPopupMenu = false;
	$scope.isBodyReady = false;
	var param = {};
	
	
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		$rootScope.urlPath = QueryParamService.getPWD(urlBase);
		var eventId = param.eventId;
		
		wxJSSDKService.wxConfig($rootScope.urlPath, $scope.initialAsync);
	}
	
	
	$scope.initialAsync = function(data){
		console.log(data);
		
		if( typeof(param.eventId) != "undefined" ){
			
			$scope.getEvent(param.eventId);
			
			wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl(data.wxappid, QueryParamService.absUrl());
			console.log(wxJSSDKService.parameter.wxonMenuShareAppMessage.link);
			wxJSSDKService.wxReady();
		}
	}
	
	
	$scope.getEvent = function(eventId){
		
		var userData = {"eventId" : eventId, "isShowEmptyText" : false};
		sessionService.postCall(
				
				$rootScope.urlPath + "/doGetEvent.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData = data.retData;
						
						if( $scope.pageData.eventIntroduce == null || 
							$scope.pageData.eventIntroduce.indexOf("http") == 0 ||
							$scope.pageData.eventIntroduce.length ==0 )
							$scope.pageData.eventIntroduce = $sce.trustAsHtml($scope.pageData.eventShortDesc);
						else
							$scope.pageData.eventIntroduce = $sce.trustAsHtml($scope.pageData.eventIntroduce);
						
						$scope.pageData.eventAmount = Math.round($scope.pageData.eventAmount)/100;
						
						var cardUrl = $rootScope.urlPath+"/eventList.html?recomEventId="+$scope.pageData.eventId+"displayMode="+($scope.pageData.eventType*10+$scope.pageData.eventSubType);
						if( $scope.pageData.eventType == 2 && $scope.pageData.eventSubType == 2){//私人活动
							 cardUrl = $rootScope.urlPath+"/eventDetail.html?eventId="+$scope.pageData.eventId;
						}
						console.log(cardUrl);
						wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = data.retData.wxCardImgUrl;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.title =  data.retData.wxCardTitle;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.desc =  data.retData.wxCardDesc;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.link =   
												wxJSSDKService.wrapAuthUrl(
														$scope.pageData.wxAppId, 
														cardUrl
														);
						
						wxJSSDKService.wxonMenuShareAppMessage();
						
						$scope.isBodyReady = true;
					}
				});
	};
	
	$scope.chooseNewPic = function(){
		
		wxJSSDKService.startWxLoadPic(function(localIds){
			for(var i = 0 ; i < localIds.length; i++){
				$scope.comPic.push( { url:localIds[i], orgUrl:localIds[i] } );
				console.log("get localIds["+i+"]="+localIds[i]);
			}
			if( localIds.length > 0){
				$scope.startUploadNewPicToWx(0);
			}
		});
	}
	
	$scope.startUploadNewPicToWx = function(picIndex){
		
		console.log($scope.comPic);
		
		if( picIndex < $scope.comPic.length ){
			
			wxJSSDKService.uploadWxPic($scope.comPic[picIndex].url, function(serverId){
				if( typeof( serverId ) != "undefined" ){
					$scope.comPic[picIndex].url = serverId;
					$scope.comPic[picIndex].orgUrl = serverId;
					console.log("get serverId ="+ serverId);
					$scope.startUploadNewPicToWx(picIndex+1);
				}else{
					alert("上传图片素材失败！");
				}
			});
			
		}else{
			//alert('开始上传内容到服务器');
			$scope.isLoadingShow = true;
			$scope.submitNewComment();
		}

	}
	
	$scope.submitNewComment = function(){
		console.log($scope.newUserMessage);
		
		var userData = {
				eventId : $scope.pageData.eventId,
				wxAppId : $scope.pageData.wxAppId,
				msgText : "",
				imgList : $scope.comPic,
		};
		
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveEventMessage.html",
				userData,
				function(data){
					if( data.retCode == 0){
						$scope.getEvent($scope.pageData.eventId);
						$scope.isLoadingShow = false;
					}else{
						$scope.isLoadingShow = false;
						alert(data.retMessage);
					}
					
				});
		}
	
	
	$scope.wxViewPhotos = function(index){
		var links = [];
		
		for(var i = 0 ; i < $scope.pageData.eventImages.length; i++){
			links.push($scope.pageData.eventImages[i].pic);
		}
		
		if( index < 0 || index >= $scope.pageData.eventImages.length)
			index == 0;
		if( $scope.pageData.eventImages.length == 0 )
			return;
		
		$scope.doWxViewPhotos(links, index);
		
	}
	$scope.doWxViewPhotos = function(picLinks, currentIndex){
		console.log(picLinks);
		console.log(currentIndex);
		wx.previewImage({
		    current: picLinks[currentIndex], // 当前显示图片的http链接
		    urls: picLinks // 需要预览的图片http链接列表
		});
	}
	
	
	//----------toggle area---------------------
	
	$scope.togglePopupMenu = function(){
		$scope.isPopupMenu = !$scope.isPopupMenu;
	}
	
	
	
	//---------goto area------------------------
	
	$scope.goToEventStatus = function(){
		window.location.href = $rootScope.urlPath+"/eventStatus.html?eventId="+$scope.pageData.eventId;
	}
	
	$scope.goToEventBBS = function(){
		window.location.href = $rootScope.urlPath+"/messageBoard.html?eventId="+$scope.pageData.eventId;
	}
	
	$scope.gotoPayDetail = function(eventRuleId, unitPrice, desc){
		
		if( typeof(unitPrice) == "undefined"){
			unitPrice = 0;
		}
		var jumpUrl = $rootScope.urlPath+'/payTheEvent.html?eventId='+$scope.pageData.eventId+'&eventRuleId='+eventRuleId+'&unitPrice='+unitPrice+"&desc="+desc;

		window.location.href = jumpUrl;
	}
	
	$scope.gotoMatchResult = function(){
		window.location.href = $rootScope.urlPath+"/eventMatchResult.html?eventId="+$scope.pageData.eventId;
	}
	
	
	
	//------------data area-----------------

	$scope.comPic = [];
	$scope.pageData = {};
	$scope.pageData.eventId = 0;
	$scope.pageData.isSample = 0;
	$scope.pageData.openId = null;
	$scope.pageData.isUserPlayer = false;
	$scope.pageData.wxAppId = null;
	$scope.pageData.wxCardTitle = "小卡片";
	$scope.pageData.wxCardDesc = "小卡片的详情";
	$scope.pageData.wxCardImgUrl = "http://pc.0angle.com/sp/img/5.jpg";
	$scope.pageData.eventTitle = "赛事众筹";
	$scope.pageData.eventStatus = 1;//0 ：不是众筹项目，肯定执行； 1：众筹中； 2：众筹成功； 3：众筹失败； 4：活动方取消
	$scope.pageData.callerHeadImgUrl = "http://pc.0angle.com/sp/img/5.jpg";
	$scope.pageData.callerNickname = "nickname";
	$scope.pageData.eventAddress = "大悦城二期626室 炫控电竞馆";
	$scope.pageData.eventTime = "2016年 03月 25日 18:00 ~ 19:00";
	$scope.pageData.eventPartiNumber = "200";
	$scope.pageData.eventAmount = "2000";
	$scope.pageData.eventIntroduce = $sce.trustAsHtml("<p>由 随时随踢 发起，召集 《实况足球》 爱好者进行实地对抗赛，总共48场，采用淘汰制决出三甲。</p>");
	$scope.pageData.registerDeadline = "2016年 03月 25日 17：00";
	$scope.pageData.eventImages = [
       {
    	   thum : "http://pc.0angle.com/sp/img/5.jpg",
    	   pic  : "http://pc.0angle.com/sp/img/5.jpg",
       },
       {
    	   thum : "http://pc.0angle.com/sp/img/5.jpg",
    	   pic  : "http://pc.0angle.com/sp/img/5.jpg",
       },
	];
	$scope.pageData.popComments = [
       {
    	  text : "很好玩1",
       },
       {
     	  text : "很好玩2",
        },
	];
	$scope.pageData.rules = [
	                {unitPrice:1, ruleId: 1, ruleDesc:"捧：1元，无私奉献"},
	                {unitPrice:2, ruleId: 1, ruleDesc:"捧：2元，得到红包抽奖机会"},
	                {unitPrice:30, ruleId: 1, ruleDesc:"捧：30元，H5动态页面开发"},
	                {unitPrice:50, ruleId: 1, ruleDesc:"捧：50元，参加6月专家座谈会"},
	                ];
	
})