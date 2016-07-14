zaApp.controller('createNewEventController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){
	

	$rootScope.selectedMenuCode = 'EVENT_CREATE_';
	$rootScope.disableSelectedMenu = false;
	var param = {};
	
	$scope.initial = function(){
		param = QueryParamService.getParam();
		$rootScope.urlPath = QueryParamService.getPWD();
		$scope.getAllWxConfigs();
	}
	
	$scope.getAllWxConfigs = function(){
		var userData = {};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetWxConfigList.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.pageData.wxConfigs = data.retData;
						$scope.getCurrentEventDetail(param.eventId);
					}
				});	
	};
	
	$scope.getCurrentEventDetail = function(eventId){
		if(typeof(eventId) == "undefined" || eventId == 0 ){
			console.log("no eventId defined!");
			return;
		}
		
		var userData = {eventId:eventId};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetOneEventDetail.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						parseDataFromServer(data.retData);
						
					}
				});	
	}
	
	
	function checkStringEmpty(target){
		if( typeof(target) == 'undefined' || target == null || target.length == 0 )
			return true;
		else
			return false;
	}
	
	function parseDataFromServer(data){
		$scope.pageData.event = data;
		$scope.pageData.event.isNeedDeadline = checkStringEmpty($scope.pageData.event.deadLineDate) ? false : true;
		$scope.pageData.event.deadLineDate = parseTimeString($scope.pageData.event.deadLineDate);
		$scope.pageData.event.targetMoney = $scope.pageData.event.targetMoney/100;
		$scope.pageData.event.isEventHasTime = checkStringEmpty($scope.pageData.event.eventTime) ? false : true;
		$scope.pageData.event.eventTime = parseTimeString($scope.pageData.event.eventTime);
		$scope.pageData.event.isO2OEvent = checkStringEmpty($scope.pageData.event.eventAddress) ? false : true;
		$scope.pageData.event.isCFEvent = ($scope.pageData.event.eventType == 2 );
		$scope.pageData.event.isTargetMoneyNecessary = ($scope.pageData.event.cfEventType != 3 );
		$scope.pageData.event.isTargetMemberNecessary = ($scope.pageData.event.cfEventType != 4 );
		$scope.pageData.event.isTargetOrCondition = ($scope.pageData.event.cfEventType == 2 );
		$scope.pageData.event.isCFMatch = ($scope.pageData.event.eventSubType == 1 );
		for(var i = 0 ; i < $scope.pageData.event.rules.length; i++){
			$scope.pageData.event.rules[i].unitPrice = $scope.pageData.event.rules[i].unitPrice/100;
			$scope.pageData.event.rules[i].isAmountFreeInput = ( $scope.pageData.event.rules[i].unitPrice == 0 );
			$scope.pageData.event.rules[i].ruleMaxAmount =  $scope.pageData.event.rules[i].ruleMaxAmount / 100;
		}
		
		console.log($scope.pageData.event);
		
	}
	
	$scope.saveNewEvent = function(){
		var userData = {
				id : $scope.pageData.event.id,
				deadLineDate : $scope.pageData.event.isNeedDeadline ? makeTimeString($scope.pageData.event.deadLineDate) : null,
				eventTitle : $scope.pageData.event.eventTitle,
				eventDesc : $scope.pageData.event.eventDesc,
				eventHeadImg : $scope.pageData.event.eventHeadImg,
				eventCreateDate : $scope.pageData.event.eventCreateDate,//TODO
				eventDetailUrl : $scope.pageData.event.eventDetailUrl.length > 5 ? $scope.pageData.event.eventDetailUrl : null,
				targetMoney : $scope.pageData.event.targetMoney*100,
				targetMember : $scope.pageData.event.targetMember,
				wxCardTitle : $scope.pageData.event.wxCardTitle,
				wxCardImgUrl : $scope.pageData.event.wxCardImgUrl,
				wxCardDesc : $scope.pageData.event.wxCardDesc,
				eventTime : $scope.pageData.event.isEventHasTime ? makeTimeString($scope.pageData.event.eventTime) : null,
				eventAddress : $scope.pageData.event.isO2OEvent ? $scope.pageData.event.eventAddress : null,
				wxConfigId : $scope.pageData.event.wxConfigId, 
				eventCreaterWxId : $scope.pageData.event.eventCreaterWxId, //从后台创建只能是0
				isActive : $scope.pageData.event.isActive,
				cfEventType : $scope.pageData.event.isCFEvent ? checkCFEventType($scope.pageData.event.isTargetMoneyNecessary, $scope.pageData.event.isTargetMemberNecessary, $scope.pageData.event.isTargetOrCondition) : 0, //0: 不是众筹项目； 1： 目标金额和目标人数必须同时达到  ； 2：目标金额和目标人数只要先达到了就可以了 ； 3： 目标金额必须达到； 4：目标人数必须达到
				eventType : $scope.pageData.event.isCFEvent ? 2 : 1, //未知类型（一般表示本条内容是无效的）； 1 普通支付类的项目 ； 2 众筹项目  ； 3 单人对赌项目
				eventSubType : $scope.pageData.event.isCFMatch ? 1: 2, // event_type=2（众筹类的话） 1. 比赛众筹 2.其他众筹
				rules : makeRulesReportData($scope.pageData.event.rules),
		};
		
		console.log(userData);
		
		sessionService.postCall( 
				$rootScope.urlPath + "/doUpdateOneEvent.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						if(typeof(param.eventId) == "undefined" || param.eventId == 0 ){
							$scope.gotoEventList();
						}
						parseDataFromServer(data.retData);
					}
				});	
		
		
	};
	
	function makeRulesReportData(rules){
		var retData = [];
		
		for( var i = 0 ; i < rules.length ; i++){
			var ruleX = rules[i];
			retData.push({
				id : ruleX.id,
				eventId : $scope.pageData.event.eventId,
				unitPrice : ruleX.isAmountFreeInput ? 0 : ruleX.unitPrice*100,
				ruleDesc : ruleX.ruleDesc,
				ruleTitle : ruleX.ruleTitle,
				isNeedAddress : ruleX.isNeedAddress,
				isNeedBarcode : ruleX.isNeedBarcode,
				ruleMaxAmount : ruleX.ruleMaxAmount*100,
				ruleMinAmount	: 0,
				ruleMaxMember	: ruleX.ruleMaxMember,
				ruleMinMember	: 0,
				isCanManyCopy : ruleX.isCanManyCopy,
				isActive	: ruleX.isActive, //TODO		
				isToDeleted	: ruleX.isToDeleted,		
				isCountInMember	: ruleX.isCountInMember,
				isCountInAmount	: ruleX.isCountInAmount,
				isAcceptableAfterDeadline : ruleX.isAcceptableAfterDeadline,
			});
		};
		
		return retData;
	};
	
	function checkCFEventType(isTargetMoneyNecessary, isTargetMemberNecessary, isOr){
		//0: 不是众筹项目； 1： 目标金额和目标人数必须同时达到  ； 2：目标金额和目标人数只要先达到了就可以了 ； 3： 目标金额必须达到； 4：目标人数必须达到
		if( isOr ){
			return 2;
		}
		if( isTargetMoneyNecessary && isTargetMemberNecessary ){
			return 1;
		}
		
		if( isTargetMoneyNecessary ){
			return 3;
		}
		
		if( isTargetMemberNecessary ){
			return 4;
		}
		return 0;
	};
	
	function makeTimeString(timeS){
		return timeS.year+
			   "-"+
			   timeS.month+
			   "-"+
			   timeS.day+
			   " "+
			   timeS.hour+
			   ":"+
			   timeS.minute;
	};
	
	function parseTimeString(timeString){
		var timeS = {};
		var t = timeString.split(" ");
		if( t.length != 2)
			return null;
		var t01 = t[0].split("-");
		if( t01.length != 3)
			return null
		timeS.year = parseInt(t01[0]);
		timeS.month = parseInt(t01[1]);
		timeS.day = parseInt(t01[2]);
		
		var t02 = t[1].split(":");
		if( t02.length != 2)
			return null
		timeS.hour = parseInt(t02[0]);
		timeS.minute = parseInt(t02[1]);
		
		return timeS;
			
	};
	
	$scope.getQrCodeImgUrl = function(content, dataContainer){
		var userData = {content:content};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetQrCodeImg.html",
				userData,
				function(data){
					if(data.retCode == 0){
						dataContainer.urlQrCode = data.retData;
					}
				});	
	}
	
//
//	0	is_event_passed_review	bit	1	0	0		是否通过了审核
//	
	/*----------------toggle area----------------*/
	$scope.toggle = {};
	$scope.toggle.currentTab = "basicConfig";
	$scope.toggleTabs = function(name){
		$scope.toggle.currentTab = name;
	}
	
	$scope.toggle.showingRule = 0 ;
	
	$scope.checkDeletedRule = function(index){
		if( $scope.pageData.event.rules[index].id == 0){
			$scope.pageData.event.rules.splice(index,1);
		}
		else{
			$scope.pageData.event.rules[index].isToDeleted = ! $scope.pageData.event.rules[index].isToDeleted;
		}
	}
	
	$scope.addNewRule = function(){
		$scope.pageData.event.rules.push({
	        	 id : 0,
	        	 isToDeleted : false,
	        	 ruleTitle : "填写你的标题",
	        	 entryUrl : "http://",
	        	 unitPrice : 0,
	        	 isAmountFreeInput : true,
	        	 isCanManyCopy : true,
	        	 isNeedAddress : true,
	        	 isNeedBarcode : true,
	        	 ruleDesc : "规则权益说明",
	        	 isAmountFreeInput : true,
	        	 isCountInMember : true,//"权益支付金是否算作目标金额",
	        	 isCountInAmount : true,//"报名人是否算作人数",
	        	 isAcceptableAfterDeadline : true, //截止日期和报名金额到达后是否任然可以接受支付
	        	 payRuleUrl : "",//wxJSSDKService.wrapAuthUrl(retData.zaFrontWxConfig.wxappid, $rootScope.urlPath+"/payTheEvent.html?eventId="+$scope.eventId+"&eventRuleId="+oneRule.ruleId+"&desc="+oneRule.ruleTitle);	         
		});
	}
	
	$scope.generateUrls = function(){
		
		$scope.pageData.event.entryUrl = QueryParamService.wrapAuthUrl(
															$scope.pageData.event.isWxAuthUrl ? $scope.pageData.event.wxConfig.wxappid : null, 
															$scope.pageData.event.wxConfig.serverUrl+"/eventDetail.html?eventId="+$scope.pageData.event.id);
		
		$scope.getQrCodeImgUrl($scope.pageData.event.entryUrl, $scope.pageData.event);
		
		for( var i = 0 ; i < $scope.pageData.event.rules.length; i++){
			$scope.pageData.event.rules[i].entryUrl = QueryParamService.wrapAuthUrl(
															$scope.pageData.event.isWxAuthUrl ? $scope.pageData.event.wxConfig.wxappid : null, 
															$scope.pageData.event.wxConfig.serverUrl+"/payTheEvent.html?eventId="+$scope.pageData.event.id+"&eventRuleId="+$scope.pageData.event.rules[i].id+"&desc="+$scope.pageData.event.rules[i].ruleTitle);
			$scope.getQrCodeImgUrl($scope.pageData.event.rules[i].entryUrl, $scope.pageData.event.rules[i]);
		}
	}
	
	
	 
	/*----------------goto area----------------*/
	
	$scope.gotoEventList = function(){
		window.location.href = $rootScope.urlPath + "/eventList.html";
	}
    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.wxConfigs = [
//	                             {
//	                            	configName : '捧场',
//	                            	id : 3,
//	                             },
	                             ];
	$scope.pageData.event = {
			id : 0,
			wxConfigId : 1,
			isCFEvent : true,
			isCFMatch : true,
			eventHeadImg : "abc",
			eventTitle : "I am event title",
			isActive : true,
			eventDesc : "I am event description",
			targetMoney : 10.00,
			isTargetMoneyNecessary : true,
			targetMemeber : 20,
			isTargetMemberNecessary : true,
			isTargetOrCondition : true,
			cfEventType : 1, //0: 不是众筹项目； 1： 目标金额和目标人数必须同时达到  ； 2：目标金额和目标人数只要先达到了就可以了 ； 3： 目标金额必须达到； 4：目标人数必须达到
			deadLineDate : {
				year: 2015,
				month : 1,
				day : 27,
				hour : 12,
				minute : 32,
			},
			isNeedDeadline : true,
			eventTime : {
				year: 2016,
				month : 2,
				day : 26,
				hour : 18,
				minute : 32,
			},
			isEventHasTime : true,
			eventAddress : "",
			eventDetailUrl : "",
			wxCardTitle : "",
			wxCardDesc : "",
			wxCardImgUrl : "",
			isO2OEvent : true,
			rules : [
			         {
			        	 id : 0,
			        	 isToDeleted : false,
			        	 ruleTitle : "",
			        	 entryUrl : "",
			        	 unitPrice : 1000,
			        	 isAmountFreeInput : true,
			        	 isCanManyCopy : true,
			        	 isNeedAddress : true,
			        	 isNeedBarcode : true,
			        	 ruleDesc : "规则权益说明",
			        	 isAmountFreeInput : true,
			        	 isCountInMember : true,//"权益支付金是否算作目标金额",
			        	 isCountInAmount : true,//"报名人是否算作人数",
			        	 isAcceptableAfterDeadline : true, //截止日期和报名金额到达后是否任然可以接受支付
			        	 payRuleUrl : "",//wxJSSDKService.wrapAuthUrl(retData.zaFrontWxConfig.wxappid, $rootScope.urlPath+"/payTheEvent.html?eventId="+$scope.eventId+"&eventRuleId="+oneRule.ruleId+"&desc="+oneRule.ruleTitle);
			         },
			         ],
	}
	
	
	
	/*----------------对话框 area----------------*/
	
	
	$scope.showConfirmDialog = function(message, type){
		var confirmDialogScope = $rootScope.$new();
		confirmDialogScope.type = type; //"error"; "confirm"; "fileupload"
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
	
	
	$scope.showSimpleFileUploadDialog = function(theEvent, varTag){
		var dialogScope = $rootScope.$new();
		dialogScope.generalFormData = {wxConfigId:theEvent.wxConfigId};
		dialogScope.queueLimit = 1;
		dialogScope.uploadUrl = $rootScope.urlPath + "/doSaveNewPicture.html";
		dialogScope.type = "fileupload";
		dialogScope.retOK = function(){
			console.log("showSimpleFileUploadDialog is button OK");
		};
		dialogScope.retCancel = function(){
			console.log("showSimpleFileUploadDialog is button cancel");
		};
		dialogScope.updateResult = function(fileItem, response, status){
			if( response.retCode == 0){
				theEvent[varTag] = response.retData[0];
				console.log($scope.pageData.event.eventHeadImg);
			}
		}
		/*创建确认对话框数据段结束*/
		
		modalInitialService.openSimpleFileUploadDialog(dialogScope);
	}
	
	
	
})