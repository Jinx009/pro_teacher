
zaApp.controller('earnEventPlayController',function($scope, $rootScope, $http, $interval, wxJSSDKService, sessionService, QueryParamService, $timeout){
	
	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	$scope.isToPayDialogShow = false;
	$scope.isToPlayDialogShow = false;
	$scope.isMessageDialogShow = false;
	$scope.isLoadingShow = false;
	$scope.isBodyOK = false;
	$scope.isInitialProm = true;
	$scope.isAskToFollowDialogShow = false;
	

	var allowTime = 4;
	$scope.orderId = 0;
	$scope.currentShow = -1;
	$scope.leftTimeMilSec = allowTime*1000;
	$scope.progressAdditionalStyle = {};
	
	var param = {}
	;
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		$scope.orderId = param.orderId;
		 
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
		
		//$scope.intialPlay();
		
	}
	
	$scope.intialPlay = function(){
		$scope.isInitialProm = false;
		$interval.cancel($scope.myTimer); 
		var userData = 
		{ orderId : $scope.orderId,
		};
		sessionService.postCall( 
			$rootScope.urlPath + "/doGenerateRandomWordsList.html",
			userData,
			function(data){
				if(data.retCode == 0){
					console.log(data.retData);
					$scope.wordList = data.retData;
					for(var i = 0 ; i < $scope.wordList.length ; i++){
						$scope.wordList[i].explainList = [];
						for( var j = 0 ; j < $scope.wordList[i].explanations.length ; j++){
							var explainx = {};
							explainx['explain'] = $scope.wordList[i].explanations[j];
							explainx['checked'] = false;
							$scope.wordList[i].explainList.push(explainx);
						}
						
					}
					$scope.currentShow = 0;
					console.log($scope.wordList);
					$scope.progressAdditionalStyle = {'width' : '0%'};
					$scope.myTimer = $interval(function(){
						if( $scope.leftTimeMilSec <= 0 ){
							$scope.goNextWord();
						}
						$scope.leftTimeString = formatTimeFromMilSec($scope.leftTimeMilSec -= 100);
					},100);
					
					$scope.isBodyOK = true;
				}else{
					$scope.isMessageDialogShow = true;
					$scope.errMessage = "链接可能过期了，请关闭后重新尝试！";
				}
				
			}, $scope);
	}
	
	function formatTimeFromMilSec(milSec){
		var string = pad(parseInt(milSec/60000),2) + ":" + pad(parseInt((parseInt(milSec%60000))/1000), 2) + ":" + pad(((parseInt(milSec%60000))%1000)/100,2);
		return string;
	}
	
	function pad(num, size) {
	    var s = num+"";
	    while (s.length < size) s = "0" + s;
	    return s;
	}
	
	$scope.goNextWord = function(){
		$scope.currentShow ++;	
		
		if( $scope.currentShow >= $scope.wordList.length){
			
			$interval.cancel($scope.myTimer); 
			$scope.currentShow = -1;
			
			var userData = 
			{ data : $scope.wordList,
			};
			sessionService.postCall( 
				$rootScope.urlPath + "/doSaveTryResult.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.correctNumber = data.retData.correctNumber;
						$scope.theRedPack = Math.round($scope.correctNumber*0.2*100)/100;
					}
				}, $scope);		
			
		}else{
			$scope.leftTimeMilSec = allowTime*1000;
			var tmpvalue = parseInt($scope.currentShow*100/($scope.wordList.length-1))+"%";
			$scope.progressAdditionalStyle = {'width' : tmpvalue};
		}
		
	}
	
	$scope.getAward = function(){
		var userData = 
		{ orderId : $scope.orderId ,
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
		{ orderId : $scope.orderId ,
		};
		sessionService.postCall( 
			$rootScope.urlPath + "/doSendEarnEventAward.html",
			userData,
			function(data){
				if(data.retCode == 0){
					window.location.href = wxJSSDKService.wrapAuthUrl("wx9b3d1cb48fb48ff4", "http://pc.0angle.com/earnEvents.html");;
				}else{
					$scope.isMessageDialogShow = true;
					$scope.errMessage = data.retMessage;
				}
			}, $scope);
	}
	
	$scope.toggleAskToFollowDialog = function(){
		$scope.isAskToFollowDialogShow = false;
	}
	
	$scope.toggleChecked = function(currentIndex, exIndex){

		$scope.wordList[currentIndex].answer = $scope.wordList[currentIndex].explainList[exIndex].explain;
		
		for(var i = 0 ; i < $scope.wordList[currentIndex].explainList.length; i++){
			if(i != exIndex){
				$scope.wordList[currentIndex].explainList[i].checked = false;
			}
		}
		
		$timeout(function(){$scope.goNextWord();},100);
	}
	
	$scope.toggleMessageDialog = function(){
		$scope.isMessageDialogShow = false;
	}
	
	$scope.gotoEarnEvents = function(){
		window.location.href = "http://pc.0angle.com/earnEvents.html";
	}
	
});


