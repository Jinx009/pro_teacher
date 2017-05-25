
zaApp.controller('casio160531Controller',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
					/**---------------------------
					 * 预设的控制字段和初始化函数
					 */
					$scope.isWxReady = 0;
					$scope.isWxConfigReady = 1;
					$scope.isErrorDialogShow = false;
					$scope.errorMessage = "";
					$scope.isLoadingShow = false;
					$scope.isBodyReady = false;
					$scope.sessionData = {};
					var param = {};
					
					$scope.initial = function(clientType){ //clientType:0 weibo; 1 weixin
						$scope.statusRecord.client = clientType;
						
						param = QueryParamService.getParam();
						$rootScope.urlPath = QueryParamService.getPWD();
						wxJSSDKService.wxConfig($rootScope.urlPath, function(data){
							$scope.sessionData = data;
							//wxJSSDKService.wrapWxCard($scope.sessionData.wxappid, "wxCardImgUrl", "wxCardTitle", "wxCardDesc", true);
							console.log($scope.sessionData);
							$scope.initialAsync(data);
							var wxJump = QueryParamService.absUrl();
							if( wxJump.indexOf("?") != -1 ){
								wxJump += "&fromWxShare=true";
							}else{
								wxJump += "?fromWxShare=true";
							}
							wxJSSDKService.parameter.wxonMenuShareAppMessage.link = wxJump;
							wxJSSDKService.parameter.wxonMenuShareAppMessage.title = "如果我有一台时光机";
							wxJSSDKService.parameter.wxonMenuShareAppMessage.desc = "我想回到过去：音为梦想，乐在传承";
							wxJSSDKService.parameter.wxonMenuShareAppMessage.headimgurl = "http://pc-res.0angle.com/casio/128.jpg";
							wxJSSDKService.parameter.wxonMenuShareAppMessage.trigger = function(res){
								console.log(param.state);
								if( typeof(param.state) == "undefined" )
									return;
								var userData = {
										picUrl : param.state,
										weixinShared : true,
									};
								sessionService.postCall( 
										$rootScope.urlPath + "/doUpdateWeixinShared.html",
										userData,
										function(data){
											if(data.retCode == 0){
												console.log("微信转发已经记录！");
											}
										});	
							}
							wxJSSDKService.parameter.wxonMenuShareAppMessage.success = function(res){
								console.log(param.state);
								if( typeof(param.state) == "undefined" )
									return;
								var userData = {
										picUrl : param.state,
										weixinSharedResult : true,
									};
								sessionService.postCall( 
										$rootScope.urlPath + "/doUpdateWeixinSharedResult.html",
										userData,
										function(data){
											if(data.retCode == 0){
												console.log("微信转发成功结果已经记录！");
											}
										});	
							}
							wxJSSDKService.wxonMenuShareAppMessage();
						});
					}
					/**----end--------------------
					 * 预设的控制字段和初始化函数
					 */
	
	/*---正文开始---*/			
	/*------function area-------*/
	$scope.initialAsync = function(data){ //本方法固定存在
		
		$scope.pageData.resultImgUrl = "http://pc-res.0angle.com/casio/"+param.state+".jpg";
		console.log($scope.pageData.resultImgUrl);

	}
	
	/**webBrowser上传的代码**/
	$scope.uploadUrl = "doSaveNewPictureBase.html";
	
//	$scope.uploader000 = new FileUploader({url: $scope.uploadUrl,});
//	$scope.uploader000.queueLimit = 1;
//	$scope.uploader001 = new FileUploader({url: $scope.uploadUrl,});
//	$scope.uploader001.queueLimit = 1;
	
	var Orientation = -1;
	$scope.onFileSelect = function(files, index){
		console.log(files);
		var Orientation = 0;
		EXIF.getData(files[0], function() {  
			// alert(EXIF.pretty(this));  
			EXIF.getAllTags(this);   
	        //alert(EXIF.getTag(this, 'Orientation'));   
	        Orientation = EXIF.getTag(this, 'Orientation');
	        //alert(Orientation);
	        //return;  
	    });  
		
		var reader = new FileReader();
		reader.onload = function (e) {
			if( typeof(Orientation) == "undefined" )
				Orientation = 0;
			QueryParamService.resizeImage(1024,1024,e.target.result, Orientation,function(data){
				$scope.pageData.picData[index] = data;
				//console.log($scope.pageData.picData[index]);
				$scope.$apply();
			});
        }
		reader.readAsDataURL(files[0]);
	}
	
	function numberToChar62( number ){
		var chars = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9',];
		var ret = "";
		
		do{
			var index = parseInt(number) % parseInt(chars.length);
			number = parseInt( parseInt(number) / parseInt(chars.length) );
			ret = ret + chars[index];
		}while( number != 0 )
		
		return ret;
	}
	
	function compressedCurrentDateString(){
		var myDate = new Date();
		var year = parseInt(myDate.getYear());        //获取当前年份(2位)
		var fullYear = parseInt(myDate.getFullYear());    //获取完整的年份(4位,1970-????)
		var month = parseInt(myDate.getMonth());       //获取当前月份(0-11,0代表1月)
		var date = parseInt(myDate.getDate());        //获取当前日(1-31)
		var day = parseInt(myDate.getDay());         //获取当前星期X(0-6,0代表星期天)
		//var fullYear = parseInt(myDate.getTime());        //获取当前时间(从1970.1.1开始的毫秒数)
		var hour = parseInt(myDate.getHours());       //获取当前小时数(0-23)
		var minute = parseInt(myDate.getMinutes());     //获取当前分钟数(0-59)
		var second = parseInt(myDate.getSeconds());     //获取当前秒数(0-59)
		var msec = parseInt(myDate.getMilliseconds());    //获取当前毫秒数(0-999)
		
		return numberToChar62(month) + 
			   numberToChar62(date) +
			   numberToChar62(day) + 
			   numberToChar62(hour) +
			   numberToChar62(second) +
			   numberToChar62(msec) + "";
		
	}
	
	$scope.wbPostData = function(){
		if( $scope.pageData.picData[0].length == 0 ||
			$scope.pageData.picData[1].length == 0){
				$scope.errorMessage = "请先上传图片！";
				$scope.isErrorDialogShow = true;
				return;
			}
		
		//console.log(compressedCurrentDateString());
		
		var userData = {
				imgId : compressedCurrentDateString(),
				imgNumber : 2,
				imgData : 
					[
					 {
						 info:{},
						 base64:$scope.pageData.picData[0],
					 }, 
					 {
						 info:{},
						 base64:$scope.pageData.picData[1],
					 }, 
					 ]
				           
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveNewPictureBase64.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						$scope.statusRecord.weiboShared = $scope.pageData.isShareToWeibo;
						$scope.statusRecord.picUrl = data.retData;
						if( $scope.pageData.isShareToWeibo ){
							$scope.jumpUrl = 
								"https://api.weibo.com/oauth2/authorize?client_id=779516139&redirect_uri=http://casio001.0angle.com/other/casioPost.html&response_type=code&state="+data.retData+"&scope=all";
						}
						else{
							$scope.jumpUrl = 
								"http://casio001.0angle.com/other/casioPost.html?state="+data.retData;
								//"http://localhost:8080/other/casioPost.html?state="+data.retData;
						}
						$scope.updateStatusRecord();
					}
				});	
//		$scope.uploader000.queue.push($scope.uploader001.queue[0]);
//		$scope.uploader000.queueLimit = 2;
//		console.log($scope.uploader000);
//		console.log($scope.uploader001);
//		$scope.uploader000.uploadAll();
		
	}
	/**webBrowser上传的代码-end**/
	
	/**微信上传的代码**/
	$scope.chooseNewPic = function(index){
		
		wxJSSDKService.startWxLoadPic(function(localIds){
			if( localIds.length > 0){
				$scope.pageData.picUrl[index] = localIds[0];
				//alert($scope.pageData.picUrl[index]);
				$scope.$apply();
			}
//			if( localIds.length > 0){
//				$scope.startUploadNewPicToWx(0);
//			}
		});
	}
	
	$scope.doSubmit = function(){
		if( $scope.pageData.picUrl[0].length == 0 ||
			$scope.pageData.picUrl[1].length == 0){
			$scope.errorMessage = "请先上传图片！";
			$scope.isErrorDialogShow = true;
			return;
		}
		$scope.startUploadNewPicToWx(0);
	}
	
	$scope.startUploadNewPicToWx = function(picIndex){
		
		console.log($scope.pageData.picUrl);
		
		if( picIndex < $scope.pageData.picUrl.length - 1 ){
			
			wxJSSDKService.uploadWxPic($scope.pageData.picUrl[picIndex], function(serverId){
				if( typeof( serverId ) != "undefined" ){
					$scope.pageData.picUrl[picIndex] = serverId;
					console.log("get serverId ="+ serverId);
					$scope.startUploadNewPicToWx(picIndex+1);
				}else{
					alert("上传图片素材失败！");
				}
			});
		}else{
			$scope.isLoadingShow = true;
			var userData = {
					imgId : compressedCurrentDateString(),
					imgNumber : 2,
					imgData : 
						[
						 {
							 info:{},
							 wx:$scope.pageData.picUrl[0],
						 }, 
						 {
							 info:{},
							 wx:$scope.pageData.picUrl[1],
						 }, 
						 ]
					           
			};
			sessionService.postCall( 
					$rootScope.urlPath + "/doSaveNewPictureWx.html",
					userData,
					function(data){
						if(data.retCode == 0){
							console.log(data.retData);
							$scope.statusRecord.weiboShared = $scope.pageData.isShareToWeibo;
							$scope.statusRecord.picUrl = data.retData;
							if( $scope.pageData.isShareToWeibo ){
								$scope.jumpUrl = 
									"https://api.weibo.com/oauth2/authorize?client_id=779516139&redirect_uri=http://casio001.0angle.com/other/casioPost.html&response_type=code&state="+data.retData+"&scope=all";
							}
							else{
								$scope.jumpUrl = 
									"http://casio001.0angle.com/other/casioPost.html?state="+data.retData;
							}
							$scope.updateStatusRecord();
						}
					});	
		}

	}
	
	$scope.updateStatusRecord = function(){
		var userData = $scope.statusRecord;
		console.log(userData);
		sessionService.postCall( 
				$rootScope.urlPath + "/doSaveCasio001EventStatus.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log(data.retData);
						window.location.href = $scope.jumpUrl;
					}
				});	
	}
	
	$scope.gotoViewPicWx = function(){
		var picList=[$scope.pageData.resultImgUrl];
		wx.previewImage({
		    current: picList[0], // 当前显示图片的http链接
		    urls: picList // 需要预览的图片http链接列表
		});
	}
	$scope.gotoViewPic = function(){
		window.location.href=""+$scope.pageData.resultImgUrl;
	}
	
	/**微信上传的代码-end**/

	
	
	/*------goto area-------*/
	
	$scope.gotoCheck = function(){
		if(typeof(param.fromWxShare) == "undefined")
			$scope.toggle.isShowWxShareMsg = !$scope.toggle.isShowWxShareMsg;
		else
			window.location.href="http://casio001.0angle.com/other/casio.html";
	}
	
	
	/*------toggle area-------*/
	$scope.toggle = {};
	$scope.toggle.isShowWxShareMsg = false;
	
	
	/*------data area-------*/
	$scope.pageData = {};
	$scope.pageData.picUrl=["","",""];
	$scope.pageData.picData = ["","",""];
	$scope.pageData.isShareToWeibo = true;
	$scope.pageData.seeRules = false;
	$scope.pageData.resultImgUrl = "";
	
	
	$scope.statusRecord = {
			id : 0,
			client : 0,
			picUrl : "",
			weiboShared : false,
			weixinShared : false,
	};
	
	$scope.jumpUrl = "";
	
	
	
	/*---正文结束---*/
	
	
	
	
	
	
});

//postCall demo
//var userData = {"eventId" : eventId};
//sessionService.postCall(
//		$rootScope.urlPath + "/doGetMatchEventResult.html",
//		userData,
//		function(data){
//			if(data.retCode == 0){
//				
//			}
//		},
//		$scope
//		);
