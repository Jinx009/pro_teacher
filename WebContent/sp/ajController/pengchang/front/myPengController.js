zaApp.controller('myPengController',function($scope, $rootScope, QueryParamService, wxJSSDKService, sessionService){
	
	$scope.isRightDescShow = false;
	$scope.isBodyShow = true;
	$scope.userInfo = {};
	
	var param = {};
	
	$scope.barCodeUrl = "./sp/img/barcode.jpg";//order.bar_code_url;
	$scope.qrCodeUrl = "./sp/img/qrcode.jpg";//order.qr_code_url;
	
	$scope.initial = function(urlBase){
		param = QueryParamService.getParam();
		console.log(param);
        
		if( typeof( urlBase ) == "undefined"){
			$rootScope.urlPath = ".";
		}
		
		wxJSSDKService.wxConfig($rootScope.urlPath, initialAsync);
	}
	
	function initialAsync(data){
		$scope.userInfo = data;
		console.log($scope.userInfo);
		
		var userData = {"orderId" : param.orderId};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetOrderInfo.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						event = data.retData.event;
						order = data.retData.order;
						rule = data.retData.rule;
						
						var mchOrderCode = data.retData.order.mch_order_code
						$scope.orderCode = mchOrderCode.substr(mchOrderCode.length-24, 6) + pad(data.retData.order.id, 6);
						console.log()
						$scope.eventDetailUrl = wxJSSDKService.wrapAuthUrl($scope.userInfo.wxappid, $scope.userInfo.serverName+"/eventDetail.html?eventId="+event.eventId);
						console.log($scope.eventDetailUrl);
						$scope.status = event.status;
						$scope.statusString = convertToStatusString(event.status);
						$scope.eventTitle = event.title;
						$scope.totalFee = order.total_fee/100.00;
						
						var payTime = new Date(order.pay_succeed_time);
						$scope.payTime = pad(payTime.getFullYear(), 2)+"年"+pad(payTime.getMonth(), 2)+"月"+pad(payTime.getDay(), 2)+"日 "+pad(payTime.getHours(), 2)+"点"+pad(payTime.getMinutes(), 2);
						
						$scope.eventDateTime = cutRight(event.eventDateTime, 1);
						$scope.deadline = cutRight(event.deadLine, 1);
						
						$scope.moneyProgress = event.moneyProgress;
						$scope.moneyProgressText = event.moneyProgress + "% : 目标 "+event.targetMoney+" 元";
						
						$scope.partiProgress = event.participaterProgress;
						$scope.partiProgressText = event.participaterProgress + "% : 目标 "+event.targetMember+" 人";
						
						$scope.ruleRightLongDesc = rule.longDesc;
						
						
						$scope.realname = order.user_realname;
						$scope.phone = order.user_phone;
						$scope.address = order.user_address;
						
						$scope.isSample = event.isSample;
						$scope.eventAddress = event.eventAddress;
						
//						$scope.barCodeUrl = "./sp/img/barcode.jpg";//order.bar_code_url;
//						$scope.qrCodeUrl = "./sp/img/qrcode.jpg";//order.qr_code_url;
						
						$scope.barCodeUrl = order.bar_code_url;
						$scope.qrCodeUrl = order.qr_code_url;
					}
				});		
		
		
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