
zaApp.controller('verbalBookListController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
					$rootScope.currentPageText = "verbalList";
					
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
		$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
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
	
	$scope.getAllTags = function(){
		var userData = { isIncludeAdmin : true };
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookTags.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.allTags = data.retData;
					}
				});	
	}
	
	$scope.getBooks = function(startIndex, pageSize, tagFilterList, searchKeyWord){
		var userData = { 
				startIndex : startIndex,
				pageSize : pageSize,
				searchKeyWord : searchKeyWord,
				tagFilterList : tagFilterList,
				forceVerbalTag : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBooks.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.verbalBooks = $scope.pageData.verbalBooks.concat(data.retData.books);
						$scope.pageData.totalNumber = data.retData.totalNumber;
					}
				});	
	}
	
	$scope.clearSearchKeyword = function(){
		$scope.pageData.searchKeyWord="";
		$scope.searchRealtime();
	}
	
	$scope.searchRealtime = function(){
		console.log($scope.pageData.searchKeyWord);
		if( typeof($scope.pageData.searchKeyWord) == "undefined" ||
			$scope.pageData.searchKeyWord.length < 1 ){
			$scope.pageData.searchRealTimeAdvice = [];
			return;
		}
		
		var userData = { 
				inputedKeywords : $scope.pageData.searchKeyWord,
				isWarehouse : true,
				//isVerbalOnly : null,
				forceVerbalTag : true,
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
		
	}
	$scope.startSearch = function(){
		$scope.pageData.verbalBooks = [];
		$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}
	
	$scope.checkKeyPress = function(keyEvent){
		if (keyEvent.which === 13){
			$scope.pageData.searchRealTimeAdvice = [];
			$scope.startSearch();
		}
	}
	
	$scope.loadMoreBooks = function(){
		$scope.getBooks($scope.pageData.verbalBooks.length, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}

	
	/*------goto area-------*/
	
	$scope.gotoBookDetail = function(url){
		window.location.href = url;
	}
	
	/*------toggle area-------*/
	$scope.toggle = {};
	
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
		$scope.pageData.searchRealTimeAdvice = [];
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
		$scope.pageData.verbalBooks = [];
		$scope.getBooks(0, $scope.pageData.pageSize, getSelectedTagIds($scope.pageData.allTags), $scope.pageData.searchKeyWord);
	}
	
	
	/*------data area-------*/
	
	$scope.pageData = {};
	$scope.pageData.searchKeyWord = "";
	$scope.pageData.totalNumber = 0;
	$scope.pageData.pageSize = 50;
	$scope.pageData.verbalBooks = 
		[
//		 {
//			 id : 5,
//			 headImgUrl : 'http://pc.0angle.com/sp/img/0angle_icon.png',
//			 bookTitle : '图书标题',
//			 desc : "图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明",
//			 tags : [
//			         "tags",
//			         "tags",
//			         "tags",
//			        ],
//		 },
//		 {
//			 id : 5,
//			 headImgUrl : 'http://pc.0angle.com/sp/img/0angle_icon.png',
//			 bookTitle : '图书标题',
//			 desc : "图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明图书说明",
//			 tags : [
//			         "tagsss",
//			         "tags",
//			         "tagsss",
//			         "tagsss",
//			         "tags",
//			         "tagsss",
//			         "tagsss",
//			         "tags",
//			         "tagsss",
//			         "tagsss",
//			         "tags",
//			         "tagsss",
//			         "tagsss",
//			         "tags",
//			         "tagsss",
//			        ],
//			 
//		 },
		];
	
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
	                           ];
	
	
	
	
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
