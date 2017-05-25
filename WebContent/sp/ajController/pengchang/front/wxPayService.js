
/**
 * 启动JSSDK的service
 */
zaApp.service('wxJSPayService', function ($http) {
	
	this.jsApiCall = function(dataPackage, callback, payCallBack){
		if( typeof( this.urlBase) == "undefined"){
			this.urlBase = ".";
		}
		console.log(dataPackage);
		var payParameter;
		var userData = dataPackage;
		var url = this.urlBase + "/doGetParameterForWxPay.html";

        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			this.retCode = data.retCode;
			this.retMessage = data.retMessage;
			if( typeof( callback ) != "undefined" ){
				callback(data);
				if( data.retCode != 0 )
					return;
			}
			payParameter = data.retData;
			/*获得了参数开始发起支付*/
			console.log(payParameter);
			 WeixinJSBridge.invoke('getBrandWCPayRequest',
					   {"appId" : payParameter.appId,     //公众号名称，由商户传入     
			           "timeStamp": payParameter.timeStamp,         //时间戳，自1970年以来的秒数     
			           "nonceStr" : payParameter.nonceStr, //随机串     
			           "package" : payParameter._package,     
			           "signType" : payParameter.signType,         //微信签名方式：     
			           "paySign" : payParameter.paySign //微信签名
					   },
					function(res){
						console.log(res.err_msg);
						if( typeof( payCallBack) != "undefined"){
							payCallBack(res);
						};
						
					}
				);
			
		})
//		.success(function(data, status, headers, config){
//			if( typeof( callback) != "undefined"){
//				callback(data, status, headers, config);
//			};
//		})
		.error(function(data, status, headers, config){
			
			if( typeof( callback) != "undefined"){
				callback(data, status, headers, config);
			};
        	alert("error: prepare wx pay function call failed");
        	
        	});
	};
	
	/**
	 * urlBase : 服务器地址
	 * callbak : 当从后台服务器获取到了有效的数据后，并且发起支付成功的时候的回调函数。回调之后，支付操作就开始了
	 * payCallBack : 返回支付操作的结果回调
	 */
	this.wxPay = function(urlBase, dataPackage, callback, payCallBack){
		
		this.urlBase = urlBase;
		if (typeof WeixinJSBridge == "undefined"){
		    if( document.addEventListener ){
		        document.addEventListener('WeixinJSBridgeReady', this.jsApiCall, false);
		    }else if (document.attachEvent){
		        document.attachEvent('WeixinJSBridgeReady', this.jsApiCall); 
		        document.attachEvent('onWeixinJSBridgeReady', this.jsApiCall);
		    }
		}else{
		    /**
		     * 开始发起支付
		     */
			this.jsApiCall(dataPackage, callback, payCallBack);
		}
	};
	
	
});
