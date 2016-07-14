zaApp.controller('verbalDetailController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){
	

	$rootScope.selectedMenuCode = 'VERBAL_DETAIL_';
	$rootScope.disableSelectedMenu = true;
	var param = {};
	
	$scope.initial = function(){
		param = QueryParamService.getParam();
		if( param.bookId > 0 ){
			$rootScope.selectedMenuCode = 'VERBAL_LIST_';
			$rootScope.disableSelectedMenu = false;
		}else{
			$rootScope.selectedMenuCode = 'VERBAL_DETAIL_';
			$rootScope.disableSelectedMenu = false;
		}
		$rootScope.urlPath = QueryParamService.getPWD();
		$scope.getBooks(param.bookId);
		
	}
	
	$scope.getBooks = function(bookId){
		var userData = { 
				bookId : bookId,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/../doGetBookInfo.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.verbalBook.id = data.retData.id;
						$scope.pageData.verbalBook.bookImgUrl = data.retData.coverImgUrl;
						$scope.pageData.verbalBook.bookName = data.retData.bookName;
						$scope.pageData.verbalBook.writer = data.retData.writer;
						$scope.pageData.verbalBook.publisher = data.retData.publisher;
						$scope.pageData.verbalBook.bookCode = data.retData.bookCode;
						$scope.pageData.verbalBook.tags = data.retData.tags;
						$scope.pageData.verbalBook.bookIntroduce = data.retData.bookIntroduce;
						$scope.pageData.verbalBook.recomm = data.retData.recomm;
						$scope.pageData.verbalBook.wxUrl = data.retData.verbalUrl;
					}
				});	
	}
	
	$scope.updateBook = function(bookId){
		var userData = { 
				bookId : param.bookId,
				coverImgUrl : $scope.pageData.verbalBook.bookImgUrl,
				bookName : $scope.pageData.verbalBook.bookName,
				writer : $scope.pageData.verbalBook.writer,
				publisher : $scope.pageData.verbalBook.publisher,
				bookCode : $scope.pageData.verbalBook.bookCode,
				//$scope.pageData.verbalBook.tags = data.retData.tags;
				bookIntroduce : $scope.pageData.verbalBook.bookIntroduce,
				//$scope.pageData.verbalBook.recomm = data.retData.recomm;
				verbalUrl : $scope.pageData.verbalBook.wxUrl,
				isVerbalOnly : true,
				};
		console.log(userData);
		sessionService.postCall( 
				$rootScope.urlPath + "/../doCreateNewBookPost.html",
				userData,
				function(data){
					if(data.retCode == 0){
						if( param.bookId == 0)
							$scope.gotoVerbalList();
						$scope.pageData.verbalBook.id = data.retData.id;
						$scope.pageData.verbalBook.bookImgUrl = data.retData.bookCoverImg;
						$scope.pageData.verbalBook.bookName = data.retData.bookName;
						$scope.pageData.verbalBook.writer = data.retData.bookWriter;
						$scope.pageData.verbalBook.publisher = data.retData.bookPublisher;
						$scope.pageData.verbalBook.bookCode = data.retData.bookCode;
						$scope.pageData.verbalBook.tags = data.retData.tags;
						$scope.pageData.verbalBook.bookIntroduce = data.retData.bookIntroduce;
						$scope.pageData.verbalBook.recomm = data.retData.recomm;
						$scope.pageData.verbalBook.wxUrl = data.retData.verbalUrl;
					}
				});	
	}
	
	
	function checkStringEmpty(target){
		if( typeof(target) == 'undefined' || target == null || target.length == 0 )
			return true;
		else
			return false;
	}
	
	

	/*----------------toggle area----------------*/
	$scope.toggle = {};
	$scope.toggle.currentTab = "basicConfig";

	 
	/*----------------goto area----------------*/
	$scope.gotoVerbalList = function(){
		window.location.href = $rootScope.urlPath + "/verbalList.html";
	}
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.verbalBook = {
			id : 0,
			bookName : "bookName",
			wxUrl :"wxUrl",
			writer :"writer",
			publisher : "publisher",
			bookCode : "bookCode",
			bookImgUrl : "http://pc.0angle.com/sp/img/5.jpg",
			reader : "reader",
			bookIntroduce : "ajsdkjga;lkdsjfa;lhga;ldjksfa;lhg;aldjksfldfkls;j",
		};
	
	
	
	/*----------------对话框 area----------------*/
	
	
	$scope.showConfirmDialog = function(message, type){
		var confirmDialogScope = $rootScope.$new();
		confirmDialogScope.type = type; //"error"; "confirm"; "fileupload"
		confirmDialogScope.confirmMessege = message;
		confirmDialogScope.retOK = function(){
			
		};
		confirmDialogScope.retCancel = function(){
			console.log("cancel");
		};
		/*创建确认对话框数据段结束*/
		
		modalInitialService.openDialog(
				confirmDialogScope);
	};
	
	
	$scope.showSimpleFileUploadDialog = function(theEvent, varTag){
		var dialogScope = $rootScope.$new();
		dialogScope.generalFormData = {verbalId:$scope.pageData.verbalBook.id};
		dialogScope.queueLimit = 1;
		dialogScope.uploadUrl = $rootScope.urlPath + "/doSaveNewPicture.html";
		dialogScope.type = "fileupload";
		dialogScope.retOK = function(){
			console.log("showSimpleFileUploadDialog is button OK");
		};
		dialogScope.retCancel = function(){
			console.log("showSimpleFileUploadDialog is button cancel");
		};
		dialogScope.updateResult = function(fileItem, response, status){
			if( response.retCode == 0){
				$scope.pageData.bookImgUrl = response.retData[0];
				console.log($scope.pageData.event.eventHeadImg);
			}
		}
		/*创建确认对话框数据段结束*/
		
		modalInitialService.openSimpleFileUploadDialog(dialogScope);
	}
	
	
	
})