zaApp.controller('manageCommentsController',function($scope, $rootScope, $attrs, $http, $location, sessionService, QueryParamService, modalInitialService){

	$rootScope.selectedMenuCode = 'SHARE_LIST_';
	$rootScope.disableSelectedMenu = false;
	var param = {};
	$scope.initial = function(){
		
		$rootScope.urlPath = QueryParamService.getPWD();
		param = QueryParamService.getParam();
		
		if( param.isWarehouse == true )
			$rootScope.selectedMenuCode = 'SHARE_LIST_WH_';
		else
			$rootScope.selectedMenuCode = 'SHARE_LIST_UF_';
		$scope.pageData.bookId = param.bookId;
		$scope.getBookComments(param.bookId, 0, 9999);
	}
	
	$scope.getBookComments = function(bookId, startIndex, pageSize){
		var userData = { 
				bookId : bookId,
				startIndex : startIndex,
				pageSize: pageSize,
				isIncludeChild : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/../doGetBooksComments.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.comments = data.retData;
					}
				});	
	}
	
	$scope.setRecommComment = function(bookId, parentCommentId, commentId){
		var userData = { 
				bookId : bookId,
				parentCommentId : parentCommentId,
				commentId: commentId,
				isIncludeChild : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doSetBestComment.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.getBookComments(param.bookId, 0, 9999);
					}
				});
	}
	$scope.deleteTheComment = function(bookId, parentCommentId, commentId){
		var userData = { 
				bookId : bookId,
				parentCommentId : parentCommentId,
				commentId: commentId,
				isIncludeChild : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/../doDeleteComment.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.getBookComments(param.bookId, 0, 9999);
					}
				});
	}
	
	$scope.checkIfHaveRecomComment = function(recomComment){
		//console.log(recomComment);
		if( typeof(recomComment) == 'undefined')
			return false;
		else
			return true;
	}
	
	function getCurrentIndex(pageIndex){
		return $scope.pageSize * pageIndex;
	}
	 
	/*----------------goto area----------------*/
	

    
	
	/*----------------data area----------------*/
	$scope.pageData = {};
	$scope.pageData.comments = [
	   {
			id : 2,
			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
			nickname : "我是发布者名字",
			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
			comTime : "2016年12月22日 16:32",
			isCurrentUserLiked :false,
			likedNumber : 32,
			commentPicThum : [
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  ],
           commentPic : [
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             ],
           recomComment : {
       			id : 9,
       			nickname :"i am nickname",
       			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
       			likedNumber : 12,
       			isCurrentUserLiked : true,
           },
           level2Comments : [
     	        {
     	        	id : 3,
     	        	nickname :"i am nickname",
     				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
     				likedNumber : 12,
     				isCurrentUserLiked : true,
     			},
     			{
     				id : 7,
     	        	nickname :"i am nickname",
     				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
     				likedNumber : 12,
     				isCurrentUserLiked : true,
     			},
     			],
       	},
       	
       	{
			id : 2,
			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
			nickname : "我是发布者名字",
			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
			comTime : "2016年12月22日 16:32",
			isCurrentUserLiked :false,
			likedNumber : 32,
			commentPicThum : [
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  ],
           commentPic : [
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             ],
           recomComment : {
       			id : 9,
       			nickname :"i am nickname",
       			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
       			likedNumber : 12,
       			isCurrentUserLiked : true,
           },
           level2Comments : [
     	        {
     	        	id : 3,
     	        	nickname :"i am nickname",
     				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
     				likedNumber : 12,
     				isCurrentUserLiked : true,
     			},
     			{
     				id : 7,
     	        	nickname :"i am nickname",
     				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
     				likedNumber : 12,
     				isCurrentUserLiked : true,
     			},
     			],
       	},
       	
       	];
			
	
	
	
	/*----------------对话框 area----------------*/
	
	
	$scope.showConfirmDialog = function(message, type){
		var confirmDialogScope = $rootScope.$new();
		confirmDialogScope.type = type; //"error"; "confirm";
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
	
	
	
})