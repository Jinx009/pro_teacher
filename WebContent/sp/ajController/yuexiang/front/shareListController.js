
zaApp.controller('shareListController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
					$scope.toggle = {};
					var param = {};
					$rootScope.currentPageText = "shareList";
					
					//var wxAppId = "wx9b3d1cb48fb48ff4";
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$rootScope.urlPath = QueryParamService.getPWD(urlBase);
						if( typeof(param.searchWord) != "undefined")
							$scope.pageData.searchKeyWord = param.searchWord;
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
		$scope.getAllTags(function(){
			$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
		});
		
	}
	
	function getSelectedTagIds(tags){
		var ret = [];
		for(var i = 0 ; i < tags.length; i++){
			var oneCat = tags[i];
			for( var j = 0 ; j < oneCat.tags.length; j++){
				var oneTag = oneCat.tags[j];
				if( oneTag.isSelected ){
					ret.push(oneTag.id);
				}
			}
		}
		return ret;
	}
	
	$scope.getAllTags = function(callback){
		var userData = { isIncludeAdmin : true };
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookTags.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.allTags = data.retData;
						setPreDefinedTag($scope.pageData.allTags);
						if( callback != undefined){
							callback();
						}
					}
				});	
	}
	
	$scope.getBooks = function(startIndex, pageSize, tagFilterList, searchKeyWord){
		var userData = { 
				startIndex : startIndex,
				pageSize : pageSize,
				searchKeyWord : searchKeyWord,
				tagFilterList : tagFilterList,
				isVerbalOnly : false,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBooks.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.books = $scope.pageData.books.concat(data.retData.books);
						$scope.pageData.totalNumber = data.retData.totalNumber;
						console.log("hahahah",$scope.pageData.books);
					}
				});	
	}
	
	

	$scope.clearSearchKeyword = function(){
		$scope.pageData.searchKeyWord="";
		$scope.searchRealtime();
	}
	$scope.searchRealtime = function(){
		if( typeof($scope.pageData.searchKeyWord) == "undefined" ||
			$scope.pageData.searchKeyWord.length < 1 ){
			$scope.pageData.searchRealTimeAdvice = [];
			return;
		}
		
		var userData = { 
				inputedKeywords : $scope.pageData.searchKeyWord,
				isWarehouse : true,
				isVerbalOnly : false,
				//forceVerbalTag : null,
				maxNumber : 5,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetPromptKeywords.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.searchRealTimeAdvice = [];
						for(var i=0 ; i < data.retData.recomList.length; i++){
							$scope.pageData.searchRealTimeAdvice.push(data.retData.recomList[i].bookName);
						}
					}
				},
				null);	
		
//		$scope.pageData.searchRealTimeAdvice = ["abc",
//		                                        "123",
//		                                        "452",
//		                                        "aksdjflaskdjklajs;ldkjga;lskjdfl;sk;lakjsl;gkj;l",
//		                                        ];
	}
	$scope.startSearch = function(){
		//alert($scope.pageData.searchKeyWord);
		$scope.pageData.books = [];
		$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}
	
	$scope.checkKeyPress = function(keyEvent){
		if (keyEvent.which === 13){
			$scope.pageData.searchRealTimeAdvice = [];
			$scope.startSearch();
		}
	}
	
	$scope.loadMoreBooks = function(){
		$scope.getBooks($scope.pageData.books.length, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}
	
	
	/*------goto area-------*/
	
	$scope.gotoBookDetail = function(bookId){
		window.location.href = wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, $rootScope.urlPath + "/shareDetail.html?bookId="+bookId,"na");
			//$rootScope.urlPath + "/shareDetail.html?bookId="+bookId;
	}
	
	
	/*------toggle area-------*/
	
	$scope.toggleRealtimeAdv = function(index){
		$scope.pageData.searchKeyWord= $scope.pageData.searchRealTimeAdvice[index];
		$scope.pageData.searchRealTimeAdvice = [];
		$scope.startSearch();
	}
	
	$scope.toggle.isShowingTagSelectionPage = false;
	$scope.toggle.isShowingMainContent = true;
	
	$scope.showTagSelectionPage = function(){
		$scope.toggle.isShowingTagSelectionPage = true;
		$scope.toggle.isShowingMainContent = false;
		var stateObj = { };
		history.pushState(stateObj, "图书列表", "shareList.html");
	}
	
	$scope.showMainContent = function(){
		$scope.toggle.isShowingTagSelectionPage = false;
		$scope.toggle.isShowingMainContent = true;
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
	$scope.reloadWithFilter = function(){
		$scope.pageData.books = [];
		$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}
	
	function setPreDefinedTag(tags){
		if( typeof(param.preTagIds) != "undefined"){
			var preIds = param.preTagIds.split(":");
			var ret = [];
			for(var i = 0 ; i < tags.length; i++){
				var oneCat = tags[i];
				for( var j = 0 ; j < oneCat.tags.length; j++){
					var oneTag = oneCat.tags[j];
					var isPredefined = false;
					for(var ti = 0 ;ti < preIds.length; ti++){
						if( oneTag.id == preIds[ti] ){
							oneTag.isSelected = true;
							console.log(oneTag.text+"被选中！");
						}
					}
				}
			}
			console.log(param.preTagIds.split(":"));
		}
	}
	
	
	/*------data area-------*/
	$scope.pageData = {};
	$scope.pageData.searchKeyWord = "";
	$scope.pageData.searchRealTimeAdvice = [];
	$scope.pageData.totalNumber = 0;
	$scope.pageData.pageSize = 50;
	$scope.pageData.books = [
//	                         {
//								id : 3,
//								time:"2015年11月11日",
//								title:"《我是书名》",
//								imgUrl:"http://pc.0angle.com/sp/img/0angle_icon.png",
//								introduce : "计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦",
//								tags : ["2-3岁","适合女孩","适合内向","对英语产生兴趣","适合女孩","适合内向","适合女孩","适合内向","适合女孩","适合内向",
//								        ],	
//								isRecom : false,
//							},
//							{
//								id : 3,
//								time:"2015年11月11日",
//								title:"《我是书名》",
//								imgUrl:"http://pc.0angle.com/sp/img/0angle_icon.png",
//								introduce : "计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦计算机上刊登开个价啦",
//								tags : ["2-3岁","适合女孩","适合内向","对英语产生兴趣","适合女孩","适合内向","适合女孩","适合内向","适合女孩","适合内向",
//								        ],	
//								isRecom : true,
//							},
//							
	                         ];
	
	
	$scope.pageData.allTags = [
//	                           {
//	                        	   catalog : "我是标签类别",
//	                        	   isSingleCheck : true,
//	                        	   tags : [
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           ],
//	                        	   
//	                           },
//	                           {
//	                        	   catalog : "我是标签类别",
//	                        	   isSingleCheck : false,
//	                        	   tags : [
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           {
//	                        	        	   id: 2,
//	                        	        	   text : "asdkfj",
//	                        	        	   isSelected : false,
//	                        	           },
//	                        	           ],
//	                        	   
//	                           },
	                           ]
	
	
	
	
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
