zaApp.controller('payTheEventController',function($scope, $rootScope, $http, $sce, wxJSSDKService, wxJSPayService, QueryParamService, sessionService){
	
	
	
	$scope.ruleDescDetail;
	$scope.copiesNumber = 1;
	$scope.unitMoney;
	$scope.realname = "";
	$scope.phone = "";
	$scope.addressLength = 0;
	$scope.eventId;
	$scope.eventRuleId;
	$scope.eventData = {};
	$scope.ruleData = {};
	$scope.isSample = false;
	$scope.notInputable = false;
	
	
	var param = {};
		
		
	$scope.isLoadingShow = false;
	
	$scope.orderCode ="n/a";
	$scope.prePayId ="n/a";
	
	$scope.addressChanged = function(){
		$scope.addressLength = $scope.address.length;
	}
	
	
	$scope.addCopy = function(){
		$scope.copiesNumber ++;
		$scope.totalMoney = $scope.copiesNumber*$scope.unitMoney;
	}
	
	$scope.removeCopy = function(){
		if( $scope.copiesNumber > 1)
		$scope.copiesNumber --;
		$scope.totalMoney = $scope.copiesNumber*$scope.unitMoney;
	}
	
	$scope.copyChanged = function(){
		$scope.totalMoney = $scope.copiesNumber*$scope.unitMoney;
	}
	
	$scope.initial = function(urlBase){
		param = QueryParamService.getParam();
		
		if( typeof(param.isEarn) != "undefined" && param.isEarn)
			$scope.additionalString = "isEarn=true";
		else
			$scope.additionalString = "isEarn=false";
		
		console.log(param);

		$scope.ruleDescDetail = param.desc;
		$scope.eventId = parseInt(param.eventId);
		$scope.eventRuleId = parseFloat(param.eventRuleId);
		
		$rootScope.urlPath = QueryParamService.getPWD(urlBase);
		
		$scope.isLoadingShow = false;
//		alert("支付前请确保您关注了【捧场】服务号，否则无法收到支付成功的通知，也无法查看【订单示例】。");
//		alert("为了展示真实的支付功能，此处将请您支付1元RMB。");
//		alert("哦~，另外，1元钱将作为您对我们事业的无私奉献，我们并没有退换给您的打算。 :P ");
		
		
		var userData = { eventId : $scope.eventId,
						 eventRuleId : $scope.eventRuleId,
						 };
		sessionService.postCall( 
				$rootScope.urlPath + "/doCheckIfPayWindowOpen.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.getPayRelatedInfo();
					}else{
						alert(data.retMessage);
						window.history.go(-1);
					}
				});			
	}
	
	
	$scope.wxPayInitialCallBack = function(res){
		$scope.isLoadingShow = false;
		$scope.orderCode = res.retData.product_id;
		$scope.prePayId = res.retData.prepay_id;
		console.log(res);
		//alert(res.retCode + "-_-" + res.retMessage);
	}
	
	$scope.wxPayResultCallBack = function(res){
		$scope.isLoadingShow = false;
		console.log(res);
		//alert(res.err_code + "--" + res.err_msg.toString());
		if( res.err_msg.toString() == "get_brand_wcpay_request:ok" ){
			//$scope.sendPayOKTplMessage();
			
			var userData = {"prepayId" : $scope.prePayId,};
			sessionService.postCall( 
					$rootScope.urlPath + "/doSetFrontPaySucceed.html",
					userData,
					function(data){
						if(data.retCode == 0){
							window.location.href="resultMsg.html?eventId="+$scope.eventId+"&showSet=paySuccess&"+$scope.additionalString;
						}
					});			
		}else if( res.err_msg.toString() == "get_brand_wcpay_request:cancel"){
		
		}else{
			window.location.href="resultMsg.html?eventId="+$scope.eventId+"&showSet=payFail&"+$scope.additionalString;
		}
	}
	
	$scope.wxPay = function(){
		$scope.isLoadingShow = true;
		if( typeof($scope.totalMoney) == "undefined" ){
			alert("输入的金额格式有错误");
			return;
		}
		
		var dataPackage = {
				"eventId" : $scope.eventId,
				"eventRuleId" : $scope.eventRuleId,
				"copies" : $scope.copiesNumber,
				"totalFee" : $scope.totalMoney*100,
				"realname" : $scope.realname,
				"userPhone" : $scope.phone,
				"userAddress" : $scope.address,
		};
		console.log(dataPackage);
		
		wxJSPayService.wxPay($rootScope.urlPath, dataPackage, $scope.wxPayInitialCallBack, $scope.wxPayResultCallBack);
	};
	
	$scope.getPayRelatedInfo = function(){
		var userData = {
				"eventId" : $scope.eventId,
				"eventRuleId" : $scope.eventRuleId,
				};

		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEventDetailInfo.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.eventData = data.retData;
						if( data.retData.rules.length == 1 ){
							$scope.ruleData = data.retData.rules[0];
							
							if( typeof($scope.ruleData.unitPrice)=="undefined" || $scope.ruleData.unitPrice == 0 ){ 
								$scope.ruleData.unitPrice = 100;
								$scope.notInputable = false;
							}else{
								$scope.notInputable = true;
							}
							
							if( $scope.ruleData.acceptManyCopy == false){
								$scope.copiesNumber = 1.00;
							}
							
							$scope.unitMoney = $scope.ruleData.unitPrice/100.0;
							$scope.totalMoney = $scope.copiesNumber*$scope.unitMoney*1.0;
							
							
							$scope.isSample = $scope.eventData.isSample;
						}
					}
				});	
	};
	
	$scope.sendPayOKTplMessage = function(){
		
		var userData = { 
				"template_id":"tD6FOuIKz6JyvSbh_KtKzZYHpqi4RCPvEdUtzgPOKMA",
				"url":"http://pc.0angle.com/myPeng.html?orderId="+$scope.orderCode,  
				"needJumpAuth":true, //强制auth包装
				"data":{
                         "first":{
			        "value":"这是您的支付凭证。",
			        "color":"#0f0f0f"
			                         },
			             "keyword1":{
			        "value":$scope.orderCode,
			        "color":"#173177"
			             },
			             "keyword2": {
			        "value":$scope.prePayId,
			        "color":"#173177"
			             },
			             "keyword3": {
			        "value":"1.00 元",
			        "color":"#173177"
			             },
			            "keyword4": {
			        "value":"就在刚刚",
			        "color":"#173177"
			             },
		            "remark":{
                       "value":"感谢您捧场~，我们牢记您的大恩大德！",
                       "color":"#173177"
		            },
				},
		};
		
		var url = $rootScope.urlPath + "/doSendTplMessage.html";
		
		
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			console.log(data);
			window.location.href="resultMsg.html?eventId=3&showSet=paySuccess";
		})
		.error(function(data, status, headers, config){
        	alert("error: doSendTplMessage failed");
        	});
	};
	
	
})