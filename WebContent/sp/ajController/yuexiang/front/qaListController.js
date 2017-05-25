
zaApp.controller('qaListController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
						$rootScope.urlPath = QueryParamService.getPWD(urlBase);
						
						$scope.pageData.companyAvator = $rootScope.urlPath + "/sp/img/yuexiang_icon.png";
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
		$scope.getPublicTopicComments(null, $scope.pageData.publicComments.length, $scope.pageData.publicPageSize);
		$scope.getPrivateTopicComments(null, $scope.pageData.privateComments.length, $scope.pageData.privatePageSize);
	}
	
	$scope.getPublicTopicComments = function(topicId, startIndex, pageSize){
		var userData = { 
				topicId : topicId,
				startIndex : startIndex,
				pageSize: pageSize,
				isPublic : true,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetTopicComments.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.publicComments = $scope.pageData.publicComments.concat(data.retData);
						makeReplyPrompt($scope.pageData.publicComments);
					}
				});	
	}
	
	$scope.getPrivateTopicComments = function(topicId, startIndex, pageSize){
		var userData = { 
				topicId : topicId,
				startIndex : startIndex,
				pageSize: pageSize,
				isPublic : false,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetTopicComments.html",
				userData,
				function(data){
					if(data.retCode == 0){
						console.log("private", data);
						$scope.pageData.privateComments = $scope.pageData.privateComments.concat(data.retData);
						makeReplyPrompt($scope.pageData.privateComments);
					}
				});	
	}
	
	function makeReplyPrompt(targetList){
		for(var i = 0 ; i < targetList.length; i++){
			var text = "已有 "+targetList[i].replyNumber+" 个回答，";
			if( $scope.checkRecomComment(targetList[i].recomComment) ){
				text += "其中最赞回答是：";
			}else{
				text += "尚无最赞回答。";
			}
			targetList[i].replyPrompt = text;
		}
	}
	
	$scope.removeThePicInList = function(list, index){
		list.splice(index,1);
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
		
		if( $scope.pageData.myComm == undefined || $scope.pageData.myComm.text.length == 0){
			$scope.errorMessage = "请填写 您的问题 后再进行提交！";
			$scope.isErrorDialogShow = true;
			return;
		};
		
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
			$scope.submitNewMyComment(1, null, $scope.pageData.myComm.text, $scope.pageData.myComm.myPics);
		}
	}
	
	$scope.checkILikeTheComment = function(target, event, parentId){
		event.stopPropagation();
		var state = {
			parentId : parentId,	
		};
		var userData = { 
				commentId : target.id,
				isUnlike : target.isCurrentUserLiked,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doLikeAComment.html",
				userData,
				function(data){
					if(data.retCode == 0){

						resetTargetRecomComment($scope.pageData.publicComments, state.parentId, data.retData.newRecomComment);
						resetTargetRecomComment($scope.pageData.privateComments, state.parentId, data.retData.newRecomComment);

					}
				},
				$scope,
				state);	
	}
	
	function resetTargetRecomComment(targetList, commentId, newRecom){
		console.log(newRecom);
		for(var i = 0 ; i < targetList.length ; i++){
			if( targetList[i].id == commentId ){
				targetList[i].recomComment = newRecom;
			}
		}
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
	
	$scope.deleteTheComment = function(parentCommentId, commentId, event){
		event.stopPropagation();
		
		var confirmDialogState = 
		{
			parentCommentId : parentCommentId,
			commentId : commentId,
		};
		
		checkConfirm(
				"您是否确认删除？",
				function doOK(confirmDialogState){
					var state = {
							commentId : confirmDialogState.commentId,
						};
						var userData = { 
								topicId : confirmDialogState.parentCommentId == null? param.topicId: null,
								parentCommentId : confirmDialogState.parentCommentId,
								commentId: confirmDialogState.commentId,
								isIncludeChild : true,
								};
						sessionService.postCall( 
								$rootScope.urlPath + "/doDeleteComment.html",
								userData,
								function(data, state){
									if(data.retCode == 0){
										deleteCommentFromArray($scope.pageData.publicComments, state.commentId);
										deleteCommentFromArray($scope.pageData.privateComments, state.commentId);
									}
								},
								$scope,
								state);
				},
				function doCancel(state){},
				confirmDialogState
				);
	}
	
	function deleteCommentFromArray(targetList, commentId){
		
		for(var i = 0 ; i < targetList.length ; i++){
			if( typeof(targetList[i].recomComment) != "undefined" ){
				if( targetList[i].recomComment.id == commentId )
					targetList[i].recomComment = undefined;
			}
			if( targetList[i].id == commentId ){
				targetList.splice(i, 1);
				i--;
			}
		}
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
						$scope.pageData.publicComments.splice(0,0, data.retData);
						$scope.pageData.privateComments.splice(0,0, data.retData);
						$scope.pageData.myComm.text = "";
						$scope.pageData.myComm.myPics = [];
						
						$scope.switchTab('private');
					}
				});	
	}

	
	
	/*------goto area-------*/
	
	$scope.gotoQaDetail = function(commentId){
		//$scope.sessionData.wxappid
		window.location.href = 
			wxJSSDKService.wrapAuthUrl(null, $rootScope.urlPath+"/qaDetail.html?qaId="+commentId+"&topicId=1", null);
	}
	
	
	/*------toggle area-------*/
	$scope.toggle = {};
	$scope.toggle.currentTab = "public";
	$scope.switchTab = function(target){
		$scope.toggle.currentTab = target;
	}
	
	$scope.toggleMessageDialog = function(){
		$scope.isErrorDialogShow = false;
	}
	
	$scope.checkRecomComment = function(target){
		//console.log("checkRecomComment",target);
		if(typeof(target)!="undefined")
			return true;
		return false;
	}
	
	
	
	/*------data area-------*/
	$scope.pageData = {};
	$scope.pageData.companyAvator = "http://pc.0angle.com/sp/img/yuexiang_icon.png";
	$scope.pageData.pageTitle = "说出你的问题，分享你的经验";
	$scope.pageData.companyName = "";
	$scope.pageData.additionalDesc = "";
	$scope.pageData.publicPageSize = 50;
	$scope.pageData.privatePageSize = 50;
	$scope.pageData.privateComments = [];
	$scope.pageData.publicComments = [
//	                                  {
//	                                	id : 2,
//	                          			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//	                          			nickname : "我是发布者名字",
//	                          			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
//	                          			comTime : "2016年12月22日 16:32",
//	                          			commentPicThum : [
//	                          			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//	                          			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//	                          			                  ],
//	                                     commentPic : [
//	                                                       "http://pc.0angle.com/sp/img/0angle_icon.png",
//	                                                       "http://pc.0angle.com/sp/img/0angle_icon.png",
//	                                                       ],
//
//	                                    recomComment : {
//	                                 			id : 9,
//	                                 			nickname :"i am nickname",
//	                                 			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//	                                 			likedNumber : 12,
//	                                 			isCurrentUserLiked : true,
//	                                 	},
//	                                  },
//	                                  {
//		                                	id : 3,
//		                          			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                          			nickname : "我是发布者名字",
//		                          			text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
//		                          			comTime : "2016年12月22日 16:32",
//		                          			commentPicThum : [
//		                          			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                          			                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                          			                  ],
//		                                     commentPic : [
//		                                                       "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                                                       "http://pc.0angle.com/sp/img/0angle_icon.png",
//		                                                       ],
//
//		                                    recomComment : {
//		                                 			id : 9,
//		                                 			nickname :"i am nickname",
//		                                 			text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//		                                 			likedNumber : 12,
//		                                 			isCurrentUserLiked : true,
//		                                 	},
//		                              },
	                                 ];
	$scope.pageData.myComm = {
			text:"",
			myPics: [],
		};
	
	
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
