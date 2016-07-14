
zaApp.controller('shareCommentDetailController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
					$rootScope.currentPageText = "shareList";
					
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$scope.pageData.bookId = param.bookId;
						$scope.pageData.commentId = param.commentId;
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
		$scope.getBooks(param.bookId);
		$scope.getBookComments(param.commentId);
	}
	
	$scope.getBookComments = function(commentId){
		var userData = { 
				commentId : commentId,
				isIncludeChild : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookCommentInfo.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.comment = data.retData;
						$scope.toggle.ifHaveRecomComm = (typeof($scope.pageData.comment.recomComment) != "undefined");
					}
				});	
	}
	
	$scope.getBooks = function(bookId){
		var userData = { 
				bookId : bookId,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBookInfo.html",
				userData,
				function(data){
					if(data.retCode == 0){
						
						$scope.pageData.coverImgUrl = data.retData.coverImgUrl;
						$scope.pageData.bookName = data.retData.bookName;
						$scope.pageData.writer = data.retData.writer;
						$scope.pageData.publisher = data.retData.publisher;
						$scope.pageData.bookCode = data.retData.bookCode;
						$scope.pageData.tags = data.retData.tags;
						$scope.pageData.bookIntroduce = data.retData.bookIntroduce;
						$scope.pageData.recomm = data.retData.recomm;
					}
				});	
	}
	$scope.submitNewComment = function(){
		$scope.doSubmitNewComment(param.bookId, param.commentId, $scope.pageData.newCommentText, []);
	}
	$scope.doSubmitNewComment = function(bookId, parentCommentId, commentText, picList){
		var userData = { 
				bookId : bookId,
				parentCommentId : parentCommentId,
				commentText : commentText,
				picList : picList,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doCreateNewComment.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.comment.child.splice(0,0, data.retData);
						$scope.toggle.isShowCommentForm = false;
						$scope.pageData.newCommentText = "";
					}
				});	
	}
	
	/**
	 * confirm提示的代码段
	 */
	$scope.toggleConfirmDialogState = {};
	$scope.toggleConfirmDialogOK = function(){
		$scope.isConfirmDialogShow = false;
		$scope.toggleConfirmDialogState.okFoo($scope.toggleConfirmDialogState);
	}
	$scope.toggleConfirmDialogCancel = function(){
		$scope.isConfirmDialogShow = false;
		$scope.toggleConfirmDialogState.cancelFoo($scope.toggleConfirmDialogState);
	}
	function checkConfirm(text,okfunction, cancelfunction, state){
		$scope.confirmMessage = text;
		$scope.toggleConfirmDialogState = state;
		$scope.toggleConfirmDialogState.okFoo = okfunction;
		$scope.toggleConfirmDialogState.cancelFoo = cancelfunction;
		$scope.isConfirmDialogShow = true;
	}
	/**
	 * confirm提示代码段结束
	 */
	
	$scope.deleteTheComment = function(parentCommentId, commentId){
		
		var confirmDialogState = 
		{
			parentCommentId : parentCommentId,
			commentId : commentId,
		};
		
		checkConfirm(
				"您是否确认删除？",
				function doOK(confirmDialogState){
					var userData = { 
							bookId : confirmDialogState.parentCommentId == null? param.bookId: null,
							parentCommentId : confirmDialogState.parentCommentId,
							commentId: confirmDialogState.commentId,
							isIncludeChild : true,
							};
					sessionService.postCall( 
							$rootScope.urlPath + "/doDeleteComment.html",
							userData,
							function(data){
								if(data.retCode == 0){
									$scope.getBookComments($scope.pageData.commentId);
								}
							});
				},
				function doCancel(state){},
				confirmDialogState
				);
	}

	$scope.wxViewPhotos = function(list,index){
		wx.previewImage({
		    current: list[index], // 当前显示图片的http链接
		    urls: list // 需要预览的图片http链接列表
		});
	}
	
	$scope.checkILikeTheComment = function(target){
		//target.isCurrentUserLiked = ! target.isCurrentUserLiked;
		console.log(target);
		var userData = { 
				commentId : target.id,
				isUnlike : target.isCurrentUserLiked,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doLikeAComment.html",
				userData,
				function(data){
					if(data.retCode == 0){
						target.isCurrentUserLiked = data.retData.currentUserLiked;
						target.likedNumber = data.retData.likes;
					}
				});	
	}
	
	
	/*------goto area-------*/
	
	$scope.gotoShareDetail = function(){
		window.location.href = $rootScope.urlPath + "/shareDetail.html?bookId="+param.bookId;
	}
	
	
	/*------toggle area-------*/
	$scope.toggle = {};
	$scope.toggle.isShowCommentForm = false;
	$scope.toggle.ifHaveRecomComm = false;
	
	
	/*------data area-------*/
	$scope.pageData = {};
	$scope.pageData.coverImgUrl = "http://pc.0angle.com/sp/img/0angle_icon.png";
	$scope.pageData.bookName = "书名";
	$scope.pageData.writer = "作者";
	$scope.pageData.publisher = "出版社";
	$scope.pageData.bookCode = "图书编号";
	
	$scope.pageData.comment = {
//			id : 2,
//			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//			nickname : "我是发布者名字",
//			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
//			comTime : "2016年12月22日 16:32",
//			isCurrentUserLiked :false,
//			likedNumber : 32,
			commentPicThum : [
			                  ],
           commentPic : [
                             ],
	};
	
	$scope.pageData.comment.recomComment = {
//			id : 9,
//			nickname :"i am nickname",
//			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//			likedNumber : 12,
//			isCurrentUserLiked : true,
	};
	
	$scope.pageData.comment.child = [
//	        {
//	        	id : 3,
//	        	nickname :"i am nickname",
//				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//				likedNumber : 12,
//				isCurrentUserLiked : true,
//			},
//			{
//				id : 7,
//	        	nickname :"i am nickname",
//				text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//				likedNumber : 12,
//				isCurrentUserLiked : true,
//			},
	];
	
	$scope.pageData.comment.newLevel2Text = "";
	
	
	
	
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
