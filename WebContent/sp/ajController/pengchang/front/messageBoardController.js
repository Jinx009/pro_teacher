
function removeUserPic(divId, thisObj){
	console.log(divId);
	//$('#').remove();
	thisObj.remove();
	console.log(window.scope);

	window.scope.removeNewPic(divId);
}


zaApp.controller('messageBoardController',function($scope, $rootScope, $sce, wxJSSDKService, QueryParamService, sessionService){
	
	window.scope = $scope;
	$scope.isOK = false;
	
	$scope.isWxReady = 0;
	$scope.isWxConfigReady = 1;
	$scope.wxAppId ;
	$scope.isLoadingShow = false;
	var param = {};
	$scope.initial = function(urlBase){
		
		param = QueryParamService.getParam();
		
		if( typeof(urlBase) == "undefined")
			$rootScope.urlPath = ".";
		else
			$rootScope.urlPath = urlBase;
		
		
		wxJSSDKService.wxConfig($rootScope.urlPath, $scope.intialAsync);
	}
	
	$scope.intialAsync = function(data){
		
		$scope.getMessageContents();
		
	}
	
	$scope.getMessageContents = function(){
		
		var userData = {
				eventId : param.eventId,
				startIndex : 0,
				pageSize : 120,
		};
		
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetEventMessage.html",
				userData,
				function(thedata){
					if( thedata.retCode == 0){
						$scope.wxAppId = thedata.retData.wxAppId;
						$scope.eventId = thedata.retData.eventId;
						$scope.isSample = thedata.retData.isSample;
						$scope.serverName = thedata.retData.serverName;
						$scope.companyAvator = thedata.retData.companyAvator;
						$scope.projectTitle = thedata.retData.projectTitle;
						$scope.companyName = thedata.retData.companyName;
						$scope.additionalDesc = thedata.retData.additionalDesc;
						$scope.publicComments = thedata.retData.publicComments;
						$scope.privateComments = thedata.retData.privateComments;

						
						wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = thedata.retData.wxcardImgUrl;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.title =  thedata.retData.wxcardTitle;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.desc =  thedata.retData.wxcardDesc;
						wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJSSDKService.wrapAuthUrl($scope.wxAppId, QueryParamService.absUrl());
						console.log(wxJSSDKService.parameter.wxonMenuShareAppMessage.link);
						wxJSSDKService.wxonMenuShareAppMessage();
					}
				});
		
		
		//wxJSSDKService.wxReady();
	}
	
	$scope.removeNewPic = function(divId){

		for( var i = 0 ; i < $scope.comPic.length ; i++){
			console.log(i);
			console.log($scope.comPic[i].objectId);
			console.log(divId);
			if( $scope.comPic[i].objectId == divId ){
				alert('');
				$scope.comPic.splice(i,1);
			}
		}
		
		console.log($scope.comPic);
		
	}
	
	$scope.chooseNewPic = function(){
		
		wxJSSDKService.startWxLoadPic(function(localIds){
			for(var i = 0 ; i < localIds.length; i++){
				var _objectId = (new Date()).valueOf();
				$scope.comPic.push({ url:localIds[i], orgUrl:localIds[i], objectId : _objectId,  });
				console.log($scope.comPic);
				$("#userUploadPics").before("<div id=\""+$scope.comPic[i].objectId+"\" class=\"img_thum_div\" onclick=\"javascript:removeUserPic('"+_objectId+"', this)\">" +
						"<img src=\""+localIds[i]+"\" style=\"width:3em;\" />" +
						"</div>");
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
	
	$scope.wxViewPhotos = function(myPicList, index){
		var links = [];
		
		for(var i = 0 ; i < myPicList.length; i++){
			links.push(myPicList[i].orgUrl);
		}
		
		if( index < 0 || index >= myPicList.length)
			index == 0;
		if( myPicList.length == 0 )
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
	
	
	
	$scope.switchTab = function(index){
		$scope.ctlPublicTab = false;
		$scope.ctlPrivateTab = false;
		$scope.ctlLeaveMessageTab = false;
		switch(index){
		case 1:
			$scope.ctlPublicTab = true;
			break;
		case 2:
			$scope.ctlPrivateTab = true;
			break;
		case 3:
			$scope.ctlLeaveMessageTab = true;
			break;
		}
	}
	
	$scope.ctlPublicTab = true;
	$scope.ctlPrivateTab = false;
	$scope.ctlLeaveMessageTab = false;
	$scope.isShowLongText = false;
	$scope.currentLongText = "";
	
	$scope.isShowOrgPic = false;
	$scope.toggleLongText = function(text){
		$scope.currentLongText = text;
		$scope.isShowLongText = !$scope.isShowLongText;
	}
	$scope.toggleOrgPic = function( url ){
		$scope.orgPicUrl = url;
		$scope.isShowOrgPic = !$scope.isShowOrgPic;
	}
	
	$scope.submitNewComment = function(){
		console.log($scope.newUserMessage);
		
		var userData = {
				eventId : param.eventId,
				wxAppId : $scope.wxAppId,
				msgText : $scope.newUserMessage,
				imgList : $scope.comPic,
		};
		
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveEventMessage.html",
				userData,
				function(data){
					if( data.retCode == 0){
						$scope.getMessageContents();
						$scope.isLoadingShow = false;
						alert('上传结束');
						$scope.switchTab(1);
					}else{
						$scope.isLoadingShow = false;
						alert(data.retMessage);
					}
					
				});
		}
	
	$scope.companyAvator ;//= "./sp/img/0angle_icon.png";
	$scope.projectTitle ;//= "【捧场】 - 项目互动平台";
	$scope.companyName ;//= "上海零角互联网信息服务有限公司";
	$scope.additionalDesc ;//= "-";
	$scope.publicComments = [
//	                         {	userAvator:"./sp/img/5.jpg", 
//	                        	userNickname:"小甜甜", 
//	                        	comTime:"2016年03月25日", 
//	                        	comments:"我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了abc",
//	                        	comPic:[
//	                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"}, 
//	                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"},
//	                        	        ],
//	                        	companyReplyer:"零角CEO",
//	                        	companyCommentTime:"2016年03月25日",
//	                         	companyComment:$sce.trustAsHtml("感谢您的支持，您的意见对我们非常有帮助，特此送上红包 <font style=\"color:red;\">200 元</font>"),
//	                         },
//	                         {	userAvator:"./sp/img/5.jpg",
//	                        	userNickname:"老霸道",
//		                        	comTime:"2016年03月25日", 
//		                        	comments:"我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了",
//		                        	comPic:[
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"}, 
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"},
//		                        	        ],
//		                     },
//		                     {	userAvator:"./sp/img/5.jpg",
//		                    	userNickname:"老甜甜",
//		                        	comTime:"2016年03月25日", 
//		                        	comments:"我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了",
//		                        	comPic:[
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"}, 
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"},
//		                        	        ],
//		                        	companyReplyer:"零角CEO",
//		                        	companyCommentTime:"2016年03月25日",
//		                         	companyComment:$sce.trustAsHtml("感谢您的支持，您的意见对我们非常有帮助，特此送上红包 <font style=\"color:red;\">200 元</font>"),
//		                     },    
		                        
	                         ];

	$scope.privateComments = [
//	                         {	userAvator:"./sp/img/5.jpg", 
//	                        	userNickname:"老霸道",
//		                        	comTime:"2016年03月25日", 
//		                        	comments:"我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了",
//		                        	comPic:[
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"}, 
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"},
//		                        	        ],
//		                     },
//		                     {	userAvator:"./sp/img/5.jpg", 
//		                    	 userNickname:"老霸道",
//		                        	comTime:"2016年03月25日", 
//		                        	comments:"我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了我说话了",
//		                        	comPic:[
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"}, 
//		                        	        {url:"./sp/img/5.jpg", orgUrl:"./sp/img/5.jpg"},
//		                        	        ],
//		                        	companyReplyer:"零角CEO",
//		                        	companyCommentTime:"2016年03月25日",
//		                         	companyComment:$sce.trustAsHtml("感谢您的支持，您的意见对我们非常有帮助，特此送上红包 <font style=\"color:red;\">200 元</font>"),
//		                     },    
	                         ];
	$scope.comPic = [
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
//	                 	{url:"./sp/img/5.jpg", orgUrl:"./sp/img/7.jpg"},
	                 ];
	
	
})