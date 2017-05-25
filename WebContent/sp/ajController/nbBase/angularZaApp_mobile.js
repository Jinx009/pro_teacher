var zaApp = angular.module('zaApp', []);

/**
 * 启动JSSDK的service
 */
zaApp.service('wxJSSDKService', function ($http, sessionService, QueryParamService) {
	
	this.parameter = {};
	
	this.parameter["wxonMenuShareAppMessage"] = {};
	this.parameter.wxonMenuShareAppMessage["title"] = "捧场 - 项目互动广场";
	this.parameter.wxonMenuShareAppMessage["desc"] = "欢迎来到【捧场】，我们提供 \"活动众筹\" 以及 \"问答红包\" 功能。";
	this.parameter.wxonMenuShareAppMessage["link"] = "我是小卡片link";
	this.parameter.wxonMenuShareAppMessage["headimgurl"] = "http://pc.0angle.com/sp/img/0angle_icon.png";
	this.parameter.wxonMenuShareAppMessage["trigger"] = function(res){};
	this.parameter.wxonMenuShareAppMessage["success"] = function(res){};
	this.parameter.wxonMenuShareAppMessage["cancel"] = function(res){}
	this.parameter.wxonMenuShareAppMessage["fail"] = function(res){}
	
	
	
	this.wxConfig = function(urlBase, callback){
		if( typeof(urlBase) == "undefined"){
			urlBase = ".";
		}

		sessionService.getSession(urlBase+"/doGetFrontSessionInfo.html", [this.wxConfigCallBack, callback]);

	}
	
	this.wrapWxCard = function(
			wxAppId,
			wxCardImgUrl,
			wxCardTitle,
			wxCardDesc, 
			isAuthWrap){
		if( typeof(wxAppId)  == "undefined" && isAuthWrap){
			console.log("get session data error!")
			return;
		}
		this.parameter.wxonMenuShareAppMessage.headimgurl = wxCardImgUrl;
		this.parameter.wxonMenuShareAppMessage.title =  wxCardTitle;
		this.parameter.wxonMenuShareAppMessage.desc =  wxCardDesc;
		this.parameter.wxonMenuShareAppMessage.link =  isAuthWrap ? this.wrapAuthUrl(wxAppId, QueryParamService.absUrl()) : QueryParamService.absUrl();
		this.wxonMenuShareAppMessage();
	}
	
	this.wxConfigCallBack = function(parameter){
		
		console.log(parameter);
		console.log(parameter.wxappid);
		console.log(parameter.timestamp);
		console.log(parameter.noncestr);
		console.log(parameter.signature);
		wx.config({
		      debug: false,
		      appId: parameter.wxappid,
		      timestamp: parameter.timestamp,
		      nonceStr: parameter.noncestr,
		      signature: parameter.signature,
		      jsApiList: [
		        'checkJsApi',
		        'onMenuShareTimeline',
		        'onMenuShareAppMessage',
		        'onMenuShareQQ',
		        'onMenuShareWeibo',
		        'onMenuShareQZone',
		        'hideMenuItems',
		        'showMenuItems',
		        'hideAllNonBaseMenuItem',
		        'showAllNonBaseMenuItem',
		        'translateVoice',
		        'startRecord',
		        'stopRecord',
		        'onVoiceRecordEnd',
		        'playVoice',
		        'onVoicePlayEnd',
		        'pauseVoice',
		        'stopVoice',
		        'uploadVoice',
		        'downloadVoice',
		        'chooseImage',
		        'previewImage',
		        'uploadImage',
		        'downloadImage',
		        'getNetworkType',
		        'openLocation',
		        'getLocation',
		        'hideOptionMenu',
		        'showOptionMenu',
		        'closeWindow',
		        'scanQRCode',
		        'chooseWXPay',
		        'openProductSpecificView',
		        'addCard',
		        'chooseCard',
		        'openCard'
		      ]
		  });
	};
	
	this.wxonMenuShareAppMessage = function(){
		var parameter = this.parameter.wxonMenuShareAppMessage;
		console.log(parameter);
		wx.onMenuShareAppMessage({
		      title: parameter.title,
		      desc: parameter.desc,
		      link: parameter.link,
		      imgUrl: parameter.headimgurl,
		      trigger: function (res) {
		        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		    	if( typeof(parameter.trigger) != "undefined") {
		    		parameter.trigger(res);
		    	}
		      },
		      success: function (res) {
		    	  if( typeof(parameter.success) != "undefined") {
		    		  parameter.success(res);
			    	}
		      },
		      cancel: function (res) {
		    	  if( typeof(parameter.cancel) != "undefined") {
		    		  parameter.cancel(res);
			    	}
		      },
		      fail: function (res) {
		    	  if( typeof(parameter.fail) != "undefined") {
		    		  parameter.fail(res);
			    	}
		      }
		    });
		
		wx.onMenuShareTimeline({
		      title: parameter.title,
		      desc: parameter.desc,
		      link: parameter.link,
		      imgUrl: parameter.headimgurl,
		      trigger: function (res) {
		        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		    	if( typeof(parameter.trigger) != "undefined") {
		    		parameter.trigger(res);
		    	}
		      },
		      success: function (res) {
		    	  if( typeof(parameter.success) != "undefined") {
		    		  parameter.success(res);
			    	}
		      },
		      cancel: function (res) {
		    	  if( typeof(parameter.cancel) != "undefined") {
		    		  parameter.cancel(res);
			    	}
		      },
		      fail: function (res) {
		    	  if( typeof(parameter.fail) != "undefined") {
		    		  parameter.fail(res);
			    	}
		      }
		    });
	};
	
	
	this.startWxLoadPic = function(callback){
		wx.chooseImage({
		    count: 9, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        callback(res.localIds); // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		    }
		});
	}
	
	this.uploadWxPic = function(picId, callback){
		wx.uploadImage({
		    localId: picId, // 需要上传的图片的本地ID，由chooseImage接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		       callback(res.serverId); // 返回图片的服务器端ID
		    }
		});
	}
	
	this.wxReady = function(){
		var topParentScope = this;
		wx.ready(function () {
			topParentScope.wxonMenuShareAppMessage();
		});
	};
	
	
	this.wrapAuthUrl = function(wxAppId, url, state){
		if( typeof(wxAppId) == "undefined" || wxAppId == null || wxAppId.length < 2){
			return url;
		}
		if( typeof(state) == "undefined"){
			state ="na";
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
		wxAppId +
		"&redirect_uri=" +
		window.encodeURIComponent(url)+
		"&response_type=code&scope=snsapi_userinfo&state=" +
		state +
		"#wechat_redirect";
	}
	
});
