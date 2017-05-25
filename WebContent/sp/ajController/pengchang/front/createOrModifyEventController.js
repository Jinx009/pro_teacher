zaApp.controller('createOrModifyEventController',function($scope, $rootScope, QueryParamService, wxJSSDKService, sessionService){
	
	$scope.eventId;
	$scope.isCFEvent = true;
	$scope.eventTitle = "";
	$scope.isActive = true;
	$scope.eventDesc = "";
	$scope.wxAppId = "";
	
	$scope.targetMoney=100;
	$scope.targetMember = 2;
	$scope.targetYear=2016;
	$scope.targetMonth = 04;
	$scope.targetDay = 01;
	$scope.targetHour = 20;
	$scope.targetMinute = 30;
	$scope.targetSecond = 00;

	$scope.eventYear=2016;
	$scope.eventMonth = 04;
	$scope.eventDay = 01;
	$scope.eventHour = 20;
	$scope.eventMinute = 30;
	$scope.eventSecond = 00;
	$scope.eventAddress = "";
	
	$scope.descContentUrl = "";
	$scope.eventEntryUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b3d1cb48fb48ff4&redirect_uri=http%3a%2f%2fpc.0angle.com%2fmessageBoard.html%3feventId%3d1&response_type=code&scope=snsapi_base&state=autoAuth#wechat_redirect";
	
	$scope.isLoadingShow = false;
	$scope.isDialogShow = false;
	function OneRule(){
		this.isToDeleted = false;
		this.ruleId = 0;
		this.ruleTitle = "";
      	this.unitPrice = 0;
      	this.isNeedAddress = false;
      	this.ruleDesc = "";
      	this.payRuleUrl = "";
      	this.deleteButtonText = "删除:本规则";
      	this.isCountInMember = true;
      	this.isCountInAmount = true;
      	this.isAcceptableAfterDeadline = false;
      	
	}
	var oneRule = {
			 	};
	
	$scope.rules=[
	              ];
	
	
	$scope.isShowUrlAddress = false;
	//-------------------------------以下为方法
	
	$scope.toggleDialog = function(url){
		$scope.urlToShow = url;
		$scope.isShowUrlAddress = true;
		if( typeof(url) == "undefined"){
			$scope.isShowUrlAddress = false;
		}
	}
	
	$scope.toggleEventEntryDialog = function(){
		$scope.urlToShow = wxJSSDKService.wrapAuthUrl($scope.wxAppId, 
					$rootScope.urlPath+"/eventList.html?recomEventId="+$scope.eventId);
		$scope.isShowUrlAddress = true;
	}
	
	$scope.togglePayEntryDialog = function(ruleId, desc){
		$scope.urlToShow = wxJSSDKService.wrapAuthUrl($scope.wxAppId, 
				$rootScope.urlPath+"/payTheEvent.html?eventId="+$scope.eventId+"&eventRuleId="+ruleId+"&desc="+desc);
		$scope.isShowUrlAddress = true;
	}
	
	$scope.getRuleDelButText = function(loopIndex){
		if( $scope.rules[loopIndex].isToDeleted){
			$scope.rules[loopIndex].deleteButtonText = "取消删除";
		}else{
			$scope.rules[loopIndex].deleteButtonText = "删除："+$scope.rules[loopIndex].ruleId+"号支付规则";
		}
	}
	
	$scope.checkIfAllowNoneCFEvent = function(){
		if( !param.isCreateFree ){
			alert("目前只能创建众筹类的活动！");
			$scope.isCFEvent = true;
		}
	}
	
	$scope.addNewRule = function(){
		var newOneRule = new OneRule();
		newOneRule.loopIndex = $scope.rules.length;
		$scope.rules.push(newOneRule);
	}
	
	$scope.removeRule = function(loopIndex){
		if( $scope.rules[loopIndex].ruleId == 0 ){ //还没有保存到数据库直接删除
			$scope.rules.splice(loopIndex, 1);
		}
		else{//已经是保存到数据库中的内容，需要等待提交按钮按下后，提交数据库来处理
			$scope.rules[loopIndex].isToDeleted = !$scope.rules[loopIndex].isToDeleted;
			$scope.getRuleDelButText(loopIndex);
		}
	}
	
	$scope.initial = function(urlBase){
		param = QueryParamService.getParam();
		console.log(param);
		$scope.eventId = param.eventId;
        
		$rootScope.urlPath = urlBase;
		if( typeof( urlBase ) == "undefined"){
			$rootScope.host = QueryParamService.getServerName();
			$rootScope.urlPath = $rootScope.host + QueryParamService.getPath();
			console.log($rootScope.urlPath);
		}
		
		
		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
	}
	
	function initialAsync(data){
		$scope.userInfo = data;
		console.log($scope.userInfo);
		
//		if( typeof($scope.userInfo.serverName) != "undefined" ){
//			$rootScope.urlPath = $scope.userInfo.serverName;
//		}
		if( typeof($scope.eventId) != "undefined" && $scope.eventId != 0){
			var userData = {"eventId" : $scope.eventId};
			sessionService.postCall( 
					$rootScope.urlPath + "/doGetEventDetailInfoByWxCreater.html",
					userData,
					function(data){
						if(data.retCode == 0){
							console.log(data.retData);
							$scope.parseTheData(data.retData);
						}
					});	
		}
	}
	
	$scope.parseTheData = function(retData){
		$scope.isActive = retData.isEventActive;
		$scope.eventTitle = retData.eventTitle;
		$scope.eventDesc = retData.eventShortDesc;
		$scope.isCFEvent = retData.cfEventType == 0 ? false : true;
		$scope.targetMoney = retData.targetAmount/100;
		$scope.targetMember = retData.targetMember;
		$scope.isAndSucceed = retData.cfEventType == 1 ? true : false;
		var date = new Date(retData.eventDeadlineDate);
		$scope.targetYear= date.getFullYear();
		$scope.targetMonth = date.getMonth()+1;
		$scope.targetDay = date.getDate();
		$scope.targetHour = date.getHours();
		$scope.targetMinute = date.getMinutes();
		//只要有活动时间就是O2O的
		$scope.isO2OEvent = typeof(retData.eventTime) == "undefined" ? false : true;
		$scope.eventAddress = retData.eventAddress;
		date = new Date(retData.eventTime);
		$scope.eventYear= date.getFullYear();
		$scope.eventMonth = date.getMonth()+1;
		$scope.eventDay = date.getDate();
		$scope.eventHour = date.getHours();
		$scope.eventMinute = date.getMinutes();
		$scope.descContentUrl = retData.eventDetailUrl;
		$scope.eventId = retData.id;
		$scope.wxAppId = retData.wxCreater.wxAppId;
		
		
		var rules = [];
		for( var i = 0 ; i < retData.zaCoreEventRules.length ; i++){
			var oneRule = new OneRule();
			oneRule.isToDeleted = retData.zaCoreEventRules[i].ruleIsDeleted;
			oneRule.ruleId = retData.zaCoreEventRules[i].id;
			oneRule.ruleTitle = retData.zaCoreEventRules[i].ruleShortDesc;
			oneRule.unitPrice = retData.zaCoreEventRules[i].ruleUnitFee/100;
			oneRule.isNeedAddress = retData.zaCoreEventRules[i].ruleIsNeedAddress;
			oneRule.ruleDesc = retData.zaCoreEventRules[i].ruleAwardLongDesc;
			oneRule.isCountInMember = retData.zaCoreEventRules[i].isCountInMember;
			oneRule.isCountInAmount = retData.zaCoreEventRules[i].isCountInAmount;
			oneRule.isAcceptableAfterDeadline = retData.zaCoreEventRules[i].isAcceptableAfterDeadline;
			//oneRule.deleteButtonText = $scope.getRuleDelButText();
			oneRule.payRuleUrl = wxJSSDKService.wrapAuthUrl(retData.zaFrontWxConfig.wxappid, 
					$rootScope.urlPath+"/payTheEvent.html?eventId="+$scope.eventId+"&eventRuleId="+oneRule.ruleId+"&desc="+oneRule.ruleTitle);
			rules.push(oneRule);
		}
		
		$scope.rules = rules;
		console.log($scope.rules);

		for(var i = 0 ; i < $scope.rules.length;i++){
			$scope.getRuleDelButText(i);
		}
	}
	
	$scope.saveTheModification = function(){
		var userData = 
		{		
				"eventId" : $scope.eventId,
				"isCFEvent" : $scope.isCFEvent,
				"eventTitle" : $scope.eventTitle,
				"isActive" : $scope.isActive,
				"eventDesc" : $scope.eventDesc,
				"targetMoney" : $scope.targetMoney,
				"targetMember" : $scope.targetMember,
				"cfEventType" : $scope.isCFEvent == false ? 0 : ($scope.isAndSucceed ? 1 : 2), //0: 不是众筹项目； 1： 目标金额和目标人数必须同时达到  ； 2：目标金额和目标人数只要先达到了就可以了 ； 3： 目标金额必须达到； 4：目标人数必须达到
				"deadlineDate" : $scope.targetYear + "-" + pad($scope.targetMonth,2) + "-" + pad($scope.targetDay,2) + " " + pad($scope.targetHour,2) + ":" + pad($scope.targetMinute,2) + ":" + pad($scope.targetSecond,2),
				"eventDate" : $scope.isO2OEvent == false ? null : ($scope.eventYear + "-" + pad($scope.eventMonth,2) + "-" + pad($scope.eventDay,2) + " " + pad($scope.eventHour,2) + ":" + pad($scope.eventMinute,2) + ":" + pad($scope.eventSecond,2)),
				"eventAddress" : $scope.eventAddress,
				"eventDetailUrl" : $scope.descContentUrl,
				"rules" : $scope.rules,
		};
		$scope.isLoadingShow = true;
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveEventModify.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.isLoadingShow = false;
						$scope.isDialogShow = true;
						console.log(data.retData);
						if( typeof($scope.eventId) == "undefined" && $scope.eventId == 0){
							window.history.back();
						}else{
							$scope.parseTheData(data.retData);
							
						}
						$scope.dialogMessage = "更新成功";
					}else{
						$scope.dialogMessage = "更新失败";
					}
					$scope.isLoadingShow = false;
				});		
	}
	$scope.hideDialog = function(){
		$scope.isDialogShow = false;
	}
	
	function convertToStatusString(status){
		switch(status){
		case 1:
			return "正在召集参与者中";
			break;
		case 2:
			return "众筹成功";
			break;
		case 3:
			return "众筹失败";
			break;
		}
	}
	

	$scope.showLongDesc = function(text){
		$scope.longText = text;
		$scope.isRightDescShow = true;
		$scope.isBodyShow = false;
	}
	

	$scope.hideRightDesc = function(){

		$scope.isRightDescShow = false;
		$scope.isBodyShow = true;
	}
	
	function pad(num, size) {
	    var s = num+"";
	    while (s.length < size) s = "0" + s;
	    return s;
	}
	
	function cutRight(string, num) {
	    return string.substr(0, string.length-num);
	}
	
	
	
})