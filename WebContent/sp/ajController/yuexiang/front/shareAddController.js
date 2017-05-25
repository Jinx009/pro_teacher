
zaApp.controller('shareAddController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService,$interval){
	
					/**---------------------------
					 * 预设的控制字段和初始化函数
					 */
					$scope.isWxReady = 0;
					$scope.isWxConfigReady = 1;
					$scope.isErrorDialogShow = false;
					$scope.isConfirmDialogShow = false;
					$scope.errorMessage = "";
					$scope.isLoadingShow = false;
					$scope.isBodyReady = false;
					$scope.sessionData = {};
					var param = {};
					$rootScope.currentPageText = "shareNew";
					
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$rootScope.urlPath = QueryParamService.getPWD(urlBase);
						wxJSSDKService.wxConfig($rootScope.urlPath, function(data){
							$scope.sessionData = data;
							//wxJSSDKService.wrapWxCard($scope.sessionData.wxappid, "wxCardImgUrl", "wxCardTitle", "wxCardDesc", true);
							console.log($scope.sessionData);
							$scope.initialAsync(data);
						});
					}
					/**----end--------------------
					 * 预设的控制字段和初始化函数
					 */
	
	/*---正文开始---*/			
	/*------function area-------*/
	$scope.initialAsync = function(data){ //本方法固定存在
		$scope.getAllTags();
	}
	
	$scope.getAllTags = function(){
		var userData = { isIncludeAdmin : false, };
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookTags.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.allTags = data.retData;
						normalizeTags($scope.pageData.allTags);
					}
				});	
	}
	
	function normalizeTags(target){
		for(var i = 0 ; i < target.length; i++){
			if( typeof(target[i].tags) == "undefined" || target[i].tags.length == 0 ){
				target.splice(i,1);
				i --;
			} 
		}
	}
	
	$scope.realtimeSearchBookName = function(searchKeyWord){
		$scope.pageData.bookNameRecomSearchList = [];
		$scope.pageData.promptMessage = "";
		$scope.pageData.referBookId = null;
		if( $scope.toggle.isEditing!='BookName')
			return;
		if( searchKeyWord.length == 0 )
			return;
		
		var userData = { 
				inputedKeywords : searchKeyWord,
				maxNumber : 3,
				isWarehouse : true,
				isVerbalOnly : false,
				//forceVerbalTag : null,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetPromptKeywords.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.bookNameRecomSearchList = data.retData.recomList;
						if( data.retData.sameNameBookNumber != 0 )
							$scope.pageData.promptMessage = "共找到 （"+data.retData.sameNameBookNumber+"） 条同名记录，点击查看";
					}
				},
				null);	
		$scope.pageData.bookNameRecomSearchList = [
//		                                           {
//		                                        	   id : 1,
//		                                        	   coverImgUrl:"http://pc.0angle.com/sp/img/0angle_icon.png",
//		                                        	   bookName:"书名",
//		                                        	   writer : "我是作者",
//		                                        	   publisher : "我是出版社",
//		                                        	   bookCode : "我是图书编码",
//		                                        	   bookIntroduce : "介绍介绍",
//		                                           },
//		                                           {
//		                                        	   id : 1,
//		                                        	   coverImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                                        	   bookName:"书名",
//		                                        	   introduce:"akldj;glakjds;lkgha;lsdjfklagha;sldjfal;skgha;lsdkfj",
//		                                        	   writer : "我是作者",
//		                                        	   publisher : "我是出版社",
//		                                        	   bookCode : "我是图书编码",
//		                                        	   score : 8.8,
//		                                        	   bookIntroduce : "介绍介绍",
//		                                           },
//		                                           {
//		                                        	   id : 1,
//		                                        	   coverImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                                        	   bookName:"书名",
//		                                        	   introduce:"akldj;glakjds;lkgha;lsdjfklagha;sldjfal;skgha;lsdkfj",
//		                                        	   writer : "我是作者",
//		                                        	   publisher : "我是出版社",
//		                                        	   bookCode : "我是图书编码",
//		                                        	   score : 8.8,
//		                                        	   bookIntroduce : "介绍介绍",
//		                                           },
		                                           ]; 
	}
	
	$scope.loadRecomByIndex = function(index){
		
		if( index >= 0 ){
			selectTags($scope.pageData.allTags, null);
			$scope.pageData.coverImgUrl = 
				$scope.pageData.bookNameRecomSearchList[index].coverImgUrl;
			console.log($scope.pageData.bookNameRecomSearchList[index]);

			$scope.pageData.referBookId = 
				$scope.pageData.bookNameRecomSearchList[index].id;
			$scope.pageData.bookName = 
				$scope.pageData.bookNameRecomSearchList[index].bookName;
			$scope.pageData.writer = 
				$scope.pageData.bookNameRecomSearchList[index].writer;
			$scope.pageData.publisher = 
				$scope.pageData.bookNameRecomSearchList[index].publisher;
			$scope.pageData.bookCode = 
				$scope.pageData.bookNameRecomSearchList[index].bookCode;
			$scope.pageData.score = 
				$scope.pageData.bookNameRecomSearchList[index].score;
			$scope.pageData.bookIntroduce = 
				$scope.pageData.bookNameRecomSearchList[index].bookIntroduce;
			selectTags($scope.pageData.allTags, $scope.pageData.bookNameRecomSearchList[index].tags);
		}
		else{
		}
		
		$scope.pageData.bookNameRecomSearchList = [];
	}

	function selectTags(target, reqTags){
		
		for(var i = 0 ; i < target.length; i++){
			for( var j = 0 ; j < target[i].tags.length; j++){
				if( typeof(reqTags)=="undefined" || reqTags == null){
					target[i].tags[j].isSelected = false;
				}else{
					for( var h = 0 ; h < reqTags.length; h++){
						if( target[i].tags[j].id == reqTags[h].id ){
							target[i].tags[j].isSelected = true;
						}
					}
				}
			}
		}
		
	}
	
	$scope.chooseNewCover = function(picMaxCount){
		wxJSSDKService.startWxLoadPic(function(localIds){
			if( localIds.length > 0 )
				$scope.pageData.coverImgUrl = localIds[0];
			$scope.$apply();
		}, picMaxCount);
	}
	
	$scope.chooseNewPic = function(picMaxCount){
		wxJSSDKService.startWxLoadPic(function(localIds){
			for(var i = 0 ; i < localIds.length; i++){
				$scope.pageData.myComm.myPics.push(localIds[i]);
			}
			console.log($scope.pageData.myComm.myPics);
			$scope.$apply();
		}, picMaxCount);
	}
	
	$scope.startUploadNewPicToWx = function(picIndex){
		
		if( picIndex < $scope.pageData.myComm.myPics.length ){
			
			wxJSSDKService.uploadWxPic($scope.pageData.myComm.myPics[picIndex], function(serverId){
				if( typeof( serverId ) != "undefined" ){
					$scope.pageData.myComm.myPics[picIndex] = serverId;
					$scope.startUploadNewPicToWx(picIndex+1);
				}else{
					alert("上传图片素材失败！");
				}
			});
		}else{
			//alert('开始上传内容到服务器');
			$scope.isLoadingShow = true;
			$scope.uploadBookInfoToServer();//完成后开始post数据到后台
		}
	}
	
	$scope.startUpload = function(){
		
		if( !checkInputString() ){
			return;
		}
		if( $scope.pageData.myComm.myPics != undefined && $scope.pageData.myComm.myPics.length > 45  ){
			$scope.errorMessage = "上传图片最多不能超过45张！";
			$scope.isErrorDialogShow = true;
			return;
		}
		
		if( $scope.pageData.coverImgUrl.toLowerCase().indexOf("http") == 0 )
			$scope.startUploadNewPicToWx(0);
		else{
			//先上传封面图到微信服务器获取serverId
			wxJSSDKService.uploadWxPic($scope.pageData.coverImgUrl, function(serverId){
				if( typeof( serverId ) != "undefined" ){
					$scope.pageData.coverImgUrl = serverId;
					$scope.startUploadNewPicToWx(0);//完成后上传评论的图片到微信服务器
				}else{
					alert("上传图片素材失败！");
				}
			});
		}
	}
	
	
	$scope.uploadBookInfoToServer = function(){
		//alert();
		var userData = {
				referBookId : $scope.pageData.referBookId,
				coverImgUrl : $scope.pageData.coverImgUrl,
				bookName : $scope.pageData.bookName,
				writer : $scope.pageData.writer,
				publisher : $scope.pageData.publisher,
				bookCode : $scope.pageData.bookCode,
				tagsId : getSelectedTagsId($scope.pageData.allTags),
				bookIntroduce : $scope.pageData.bookIntroduce, 
				myComText : $scope.pageData.myComm.text,
				myComPic : $scope.pageData.myComm.myPics,
		};
		sessionService.postCall( 
				$rootScope.urlPath + "/doCreateNewBookPost.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.isErrorDialogShow = true;
						$scope.errorMessage = "您的分享已提交，请耐心等待管理员审核。谢谢！";
						$scope.toggleMessageDialog = function(){
							window.location.href = $rootScope.urlPath+"/shareList.html";
						};
					}
				});	
	}
	
	function getSelectedTagsId(selTagList){
		console.log(selTagList);
		var ret = [];
		
		for( var i = 0 ; i < selTagList.length; i++){
			console.log(selTagList[i].tags);
			for( var j = 0 ; j < selTagList[i].tags.length; j++){
				if( selTagList[i].tags[j].isSelected ){
					ret.push(selTagList[i].tags[j].id);
				}
			}
		}
		
		return ret;
	}
	
	$scope.checkLength = function(targetName, maxLength){
		if( $scope.pageData[targetName].length > maxLength ){
			$scope.pageData[targetName] = $scope.pageData[targetName].substring(0,maxLength);
		}
	}
	
	
	/*------goto area-------*/
	
	$scope.gotoShareListWithSearchWord = function(){
		if( $scope.pageData.promptMessage != undefined && $scope.pageData.promptMessage.length > 0 )
			window.location.href = $rootScope.urlPath+"/shareList.html?searchWord="+$scope.pageData.bookName;
	}
	
	$scope.gotoShareList = function(){
		window.location.href = $rootScope.urlPath+"/shareList.html";
	}
	
	
	/*------toggle area-------*/
	var tempRec = {};
	$scope.toggle = {};
	$scope.toggle.isEditing;// BookName; Writer ; Publisher ;BookCode; Score ;
	$scope.toggleEdit = function(targetInput, currentInputText){
		
		if( $scope.toggle.isEditing == targetInput){
			$scope.toggle.isEditing = "n/a";
		}
		else
			$scope.toggle.isEditing = targetInput;
		
		if( $scope.toggle.isEditing != "n/a"){
			if($scope.toggle.isEditing == 'BookName' ){
				$scope.startTimerCheck();
			}
			tempRec.bookName = currentInputText;
			return currentInputText;
		}
		else{
			if( currentInputText.length == 0){
				 return tempRec.bookName;
			}else{
				return currentInputText;
			}
		}
	}
	$scope.startTimerCheck = function(){
		$scope.myTimer = $interval(function(){
			if( $scope.toggle.isEditing != 'BookName'){
				$scope.loadRecomByIndex(-1);
				$interval.cancel($scope.myTimer);
			}
		},100);
	}
	$scope.removeThePicInList = function(list, index){
		list.splice(index,1);
	}
	
	$scope.tagSelect = function(catalogIndex, tagIndex){
		console.log($scope.pageData.allTags[catalogIndex]);
		$scope.pageData.allTags[catalogIndex].tags[tagIndex].isSelected = !$scope.pageData.allTags[catalogIndex].tags[tagIndex].isSelected;
		if( $scope.pageData.allTags[catalogIndex].isSingleCheck ){
			for( var i = 0 ; i < $scope.pageData.allTags[catalogIndex].tags.length; i++){
				if( i != tagIndex ){
					$scope.pageData.allTags[catalogIndex].tags[i].isSelected = false;
				}
			}
		}
	}
	
	$scope.selectTextOnFoucs = function (event, target, proName) {
	    if( target[proName].indexOf("点击编辑") == 0 ){
	    	target[proName] = "";
	    }
	};
	
	var maxSize = 500;
	$scope.toggle.myComm_textSizeInfo = " 字数 (0 / "+maxSize+")";
	$scope.onMyCommTextChange = function(){
		if( $scope.pageData.myComm.text.length > maxSize ){
			$scope.pageData.myComm.text = $scope.pageData.myComm.text.substring(0, maxSize);
		}
		$scope.toggle.myComm_textSizeInfo = " 字数 ("+$scope.pageData.myComm.text.length + " / "+maxSize+")";
		
		
	}

	function checkInputString(){
		if( $scope.pageData.myComm.text.length == 0){
			$scope.errorMessage = "请填写 读后感 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		if( $scope.pageData.bookName.length == 0 || $scope.pageData.bookName.indexOf("点击编辑") == 0){
			$scope.errorMessage = "请填写 书名 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		if( $scope.pageData.writer.length == 0 || $scope.pageData.writer.indexOf("点击编辑") == 0){
			$scope.errorMessage = "请填写 作者信息 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		if( $scope.pageData.publisher.length == 0 || $scope.pageData.publisher.indexOf("点击编辑") == 0){
			$scope.errorMessage = "请填写 出版社信息 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		if($scope.pageData.coverImgUrl == "http://pc.0angle.com/sp/img/add_icon.jpg"){
			$scope.errorMessage = "请提供 图书封面信息 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		return true;
	}
	
	$scope.toggleMessageDialog = function(){
		$scope.isErrorDialogShow = false;
	}
	
	/*------data area-------*/
	$scope.pageData = {};
	
	$scope.pageData.referBookId = 0;
	$scope.pageData.coverImgUrl = "http://pc.0angle.com/sp/img/add_icon.jpg";
	$scope.pageData.bookName = "点击编辑书名";
	$scope.pageData.writer = "点击编辑";
	$scope.pageData.publisher = "点击编辑";
	$scope.pageData.bookCode = "点击编辑";
	$scope.pageData.score = "点击编辑";
	
	$scope.pageData.allTags = [
	                           {
	                        	   catalog : "我是标签类别",
	                        	   isSingleCheck : true,
	                        	   tags : [
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           ],
	                        	   
	                           },
	                           {
	                        	   catalog : "我是标签类别",
	                        	   isSingleCheck : false,
	                        	   tags : [
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           {
	                        	        	   id: 2,
	                        	        	   text : "asdkfj",
	                        	        	   isSelected : false,
	                        	           },
	                        	           ],
	                        	   
	                           },
	                           ]
	$scope.pageData.bookIntroduce = "";
	
	$scope.pageData.bookNameRecomSearchList = []; 
	
	$scope.pageData.myComm = {
			text:"",
			myPics: [],
		};
	
	$scope.pageData.promptMessage = ""; 
	
	
	
	
	/*---正文结束---*/
	
})
;

//zaApp.directive('selectOnClick', function () {
//    return {
//        restrict: 'A',
//        link: function (scope, element) {
//            var focusedElement;
//            element.on('click', function () {
//                if (focusedElement != this) {
//                    this.select();
//                    focusedElement = this;
//                }
//            });
////            element.on('blur', function () {
////                focusedElement = null;
////            });
//        }
//    };
//})


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
