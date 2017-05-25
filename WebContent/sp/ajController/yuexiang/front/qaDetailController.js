
zaApp.controller('qaDetailController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
					$rootScope.currentPageText = "qaList";
					
					$scope.initial = function(urlBase){
						param = QueryParamService.getParam();
						$scope.pageData.topicId = param.topicId;
						$scope.pageData.commentId = param.qaId;
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
		$scope.getTopicComments(param.qaId);
	}
	
	$scope.getTopicComments = function(commentId){
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
	
	$scope.submitNewLevel2Comment = function(){
		
		if( $scope.pageData.comment.newLevel2Text == undefined || $scope.pageData.comment.newLevel2Text.length == 0){
			$scope.errorMessage = "请填写 点评内容 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return;
		}
		$scope.submitNewMyComment(null, param.qaId, $scope.pageData.comment.newLevel2Text, []);
	}
	
	$scope.submitNewMyComment = function(topicId, parentCommentId, commentText, picList){
		var userData = { 
				topicId : topicId,
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
						$scope.pageData.comment.newLevel2Text = "";
					}
				});	
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
						$scope.getTopicComments(param.qaId);
					}
				});	
	}
	
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
	$scope.deleteTheComment = function(parentCommentId, commentId){
		var state = 
		{
			parentCommentId : parentCommentId,
			commentId : commentId,
		};
		
		checkConfirm(
				"您是否确认删除？",
				function doOK(state){
					var userData = { 
							topicId : state.parentCommentId == null? param.topicId: null,
							parentCommentId : state.parentCommentId,
							commentId: state.commentId,
							isIncludeChild : true,
							};
					sessionService.postCall( 
							$rootScope.urlPath + "/doDeleteComment.html",
							userData,
							function(data){
								if(data.retCode == 0){
									$scope.getTopicComments(param.qaId);
								}
							});
				},
				function doCancel(state){},
				state
				);
	}
	
	$scope.checkRecomComment = function(target){
		console.log("checkRecomComment",target);
		if(typeof(target)!="undefined")
			return true;
		return false;
	}
	
	
	/*------goto area-------*/
	
	
	/*------toggle area-------*/
	$scope.toggle = {};
	$scope.toggle.isShowCommentForm = -1;
	
	$scope.toggleMessageDialog = function(){
		$scope.isErrorDialogShow = false;
	}
	
	
	/*------data area-------*/
	$scope.pageData = {};
	$scope.pageData.comment = {
			id : 2,
			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
			nickname : "我是发布者名字",
			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
			comTime : "2016年12月22日 16:32",
			commentPicThum : [
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
			                  ],
           commentPic : [
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             "http://pc.0angle.com/sp/img/0angle_icon.png",
                             ],
	};
	
	$scope.pageData.comment.recomComment = {
			id : 9,
			nickname :"i am nickname",
			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
			likedNumber : 12,
			isCurrentUserLiked : true,
	};
	
	$scope.pageData.comment.child = [
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
