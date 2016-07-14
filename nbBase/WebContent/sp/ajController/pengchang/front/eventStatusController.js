zaApp.controller('eventStatusController',function($scope, $rootScope, $interval, QueryParamService, wxJSSDKService, sessionService){	
	
	var param = {};
	
	$scope.isLoadingShow = true;
	$scope.isSample;// = false;
	$scope.deadLine;// = "2016年12月15日 16:32:00";
	$scope.gotTotalMoney;// = "50000 元";
	$scope.gotPartiNum;// = "73 人";
	$scope.eventStatusText;// = "众筹中";
	$scope.partis = [
//	                 {
//	                	 headImgUrl : './sp/img/5.jpg', 
//	                	 partiTime : '2016年12月10日 16:32:12',
//	                	 partiMoney : '200 元',
//	                 },
//	                 {
//	                	 headImgUrl : './sp/img/5.jpg', 
//	                	 partiTime : '2016年12月10日 16:32:12',
//	                	 partiMoney : '200 元',
//	                 },
	                 ];
	$scope.leftTimeSec;// = 600000;
	$scope.leftTimeString;// = formatTimeFromSecs($scope.leftTimeSec);
	
	
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		console.log(param);

		if( typeof( urlBase ) == "undefined"){
			$rootScope.urlPath = ".";
		}
		
		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
	}
	
	
	function initialAsync(data){
		
		var userData = {"eventId" : param.eventId};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEventStatus.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.isSample = data.retData.isSample;
						$scope.deadLine = data.retData.deadLine;
						$scope.gotTotalMoney = formatMoneyFromCents(data.retData.gotTotalMoney);
						$scope.gotPartiNum = formatPartiNumber(data.retData.gotPartiNum);
						$scope.eventStatusText = convertToStatusString(data.retData.eventStatus);
						$scope.wxAppId = data.retData.wxAppId;
						$scope.partis = data.retData.parti;
						$scope.leftTimeSec = data.retData.leftTimeSec;
						$scope.leftTimeString = formatTimeFromSecs($scope.leftTimeSec);
						$scope.eventId = data.retData.eventId;
						if( $scope.leftTimeSec > 0 ){
							$scope.myTimer = $interval(function(){
								if( $scope.leftTimeSec < 1 ){
									$interval.cancel($scope.myTimer);
								}
								$scope.leftTimeString = formatTimeFromSecs($scope.leftTimeSec --);									
							},1000);
						}
					}
					$scope.isLoadingShow = false;
				});		
		
	}
	
	$scope.formatMoney = function(cents){
		return formatMoneyFromCents(cents);
	}
	
	function formatMoneyFromCents(cents){
		if( cents > 1000000 ){ //大于1万元
			return parseInt(cents/1000000)+" 万元";
		}
		return cents/100 + " 元";
	}
	
	function formatPartiNumber(pn){
		if( pn > 1000 ){ //大于1千人
			return parseInt(pn/1000)+" 千人";
		}
		return pn + " 人";
	}
	
	
	
	function formatTimeFromSecs(leftTimeSec){
		if( leftTimeSec <= 0){
			return "活动报名已经截止";
		}
		var days = parseInt($scope.leftTimeSec/(3600*24));
		var hours = parseInt(($scope.leftTimeSec-days*(3600*24))/(3600));
		var minutes = parseInt(($scope.leftTimeSec-days*(3600*24)-hours*3600)/(60));
		var secs = parseInt(($scope.leftTimeSec-days*(3600*24)-hours*3600-minutes*60));
		var string = "";
		if( days == 0)
			string = " " +secs+"秒";
		//if( minutes != 0){
			string = pad(minutes,2) +"分" + string;
		//}
		//if( hours != 0){
			string = pad(hours,2) +"小时 " + string;
		//}
		if( days != 0){
			string = days +"天 " + string;
		}
		return string;
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
	
	function pad(num, size) {
	    var s = num+"";
	    while (s.length < size) s = "0" + s;
	    return s;
	}
	
	function cutRight(string, num) {
	    return string.substr(0, string.length-num);
	}
	
})