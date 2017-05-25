
zaApp.controller('shareDetailController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
		$scope.getBookComments(param.bookId, $scope.toggle.currentStartIndex, $scope.toggle.commentPageSize);
	}
	
	$scope.getBookComments = function(bookId, startIndex, pageSize){
		var userData = { 
				bookId : bookId,
				startIndex : startIndex,
				pageSize: pageSize,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doGetBooksComments.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.comments = $scope.pageData.comments.concat(data.retData);
						$scope.toggle.currentStartIndex = $scope.pageData.comments.length;
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
						$scope.pageData.verbalUrl = data.retData.verbalUrl;
					}
				});	
	}
	
	$scope.submitNewLevel2Comment = function(index){
		$scope.pageData.comments[index];
		//TODO:
	}

	$scope.loadMoreComments = function(){
		$scope.pageData.comments.length;
		//TODO:
	}
	
	$scope.wxViewPhotos = function(list,index){
		wx.previewImage({
		    current: list[index], // 当前显示图片的http链接
		    urls: list // 需要预览的图片http链接列表
		});
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
	
	function checkInputString(){
		if( $scope.pageData.myComm.text.length == 0){
			$scope.errorMessage = "请先填写读后感后再进行提交！";
			$scope.isErrorDialogShow = true;
			return false;
		}
		return true;
	}
	
	$scope.toggleMessageDialog = function(){
		$scope.isErrorDialogShow = false;
	}
	
	$scope.startUploadNewPicToWx = function(picIndex){
		
		if( !checkInputString() ){
			return;
		}
		
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
			$scope.submitNewMyComment();
		}
	}
	
	$scope.submitNewMyComment = function(){
		
		var userData = { 
				bookId : param.bookId,
				commentText : $scope.pageData.myComm.text,
				picList : $scope.pageData.myComm.myPics,
				};
		sessionService.postCall( 
				$rootScope.urlPath + "/doCreateNewComment.html",
				userData,
				function(data){
					if(data.retCode == 0){
						$scope.pageData.myComm.myPics = [];
						$scope.pageData.myComm.text = "";
						$scope.pageData.comments.splice(0,0,data.retData); 
					}
				});	
	}
	
	
	$scope.checkILikeTheComment = function(index, event){
		event.stopPropagation();
		var target = $scope.pageData.comments[index];
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
						$scope.pageData.comments[index].isCurrentUserLiked = data.retData.currentUserLiked;
						$scope.pageData.comments[index].likedNumber = data.retData.likes;
						$scope.pageData.recomm = data.retData.newBookComment;
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
		
		
	}
	
	$scope.deleteTheComment = function(commentId, index, event){
		event.stopPropagation();
		
		var confirmDialogState = 
		{
			index : index,
			commentId : commentId,
		};
		
		checkConfirm(
				"您是否确认删除？",
				function doOK(confirmDialogState){
					var userData = { 
							bookId : param.bookId,
							commentId: confirmDialogState.commentId,
							isIncludeChild : false,
							};
					sessionService.postCall( 
							$rootScope.urlPath + "/doDeleteComment.html",
							userData,
							function(data){
								if(data.retCode == 0){
									//$scope.getBookComments(param.bookId, $scope.toggle.currentStartIndex, $scope.toggle.commentPageSize);
									$scope.pageData.comments.splice(confirmDialogState.index,1);
								}
							});
				},
				function doCancel(state){},
				confirmDialogState
				);
	}

	
	
	/*------goto area-------*/
	$scope.gotoShareCommentDetail = function(commId){
		window.location.href = 
			wxJSSDKService.wrapAuthUrl(null, $rootScope.urlPath+"/shareCommentDetail.html?commentId="+commId+"&bookId="+param.bookId, null);
	}
	
	$scope.gotoVerbalUrl = function(){
		window.location.href=
			wxJSSDKService.wrapAuthUrl(null, $scope.pageData.verbalUrl, null);
	}
	
	/*------toggle area-------*/
	$scope.toggle = {};
	$scope.toggle.tabShowing = 1;
	$scope.toggle.bookIntroduceCollapse = true;
	$scope.toggle.recommCollapse = true;
	$scope.toggle.isShowCommentForm = -1;
	
	$scope.toggleCollapse = function(target, event){
		event.stopPropagation(); 
		$scope.toggle[target]=!$scope.toggle[target];
	}
	
	$scope.toggleTabShowing = function(showId){
		$scope.toggle.tabShowing = showId;
	}
	
	$scope.removeThePicInList = function(list, index){
		list.splice(index,1);
	}
	
	$scope.toggle.currentStartIndex = 0;
	$scope.toggle.commentPageSize = 999;
	$scope.checkIfHaveRecomComment = function(target){
		return !(typeof(target)=="undefined");
	}
	
	var maxSize = 500;
	$scope.toggle.myComm_textSizeInfo = " 字数 (0 / "+maxSize+")";
	$scope.onMyCommTextChange = function(){
		if( $scope.pageData.myComm.text.length > maxSize ){
			$scope.pageData.myComm.text = $scope.pageData.myComm.text.substring(0, maxSize);
		}
		$scope.toggle.myComm_textSizeInfo = " 字数 ("+$scope.pageData.myComm.text.length + " / "+maxSize+")";
		
		
	}
	
	/*------data area-------*/
	$scope.pageData = {};
//	$scope.pageData.coverImgUrl = "http://img3x1.ddimg.cn/71/10/23641271-1_w_1.jpg";
//	$scope.pageData.bookName = "我是书名";
//	$scope.pageData.writer = "我是作者";
//	$scope.pageData.publisher = "我是出版社";
//	$scope.pageData.bookCode = "我是图书编码";
//	$scope.pageData.score = 8.8;
//	$scope.pageData.tags = [
//	                        {
//	                        	catalog: "1",
//	                        	tag:"2-3岁",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"男孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        {
//	                        	catalog: "2",
//	                        	tag:"女孩",
//	                        },
//	                        ];
//	$scope.pageData.bookIntroduce = "巴巴爸爸认知故事系列”巧妙运用巴巴爸爸一家无所不能的变形特点，把小读者带进一个极富想想的神奇阅读情境。跟随巴巴爸爸，小读者可以轻松认知颜色、形状、英文字母，巧妙培养数概念、观察力和方位知觉。★谁是“巴巴爸爸”" +
//			"巴巴爸爸出生在法国。他从土里长出来，但他不是一棵植物，而是一个动物。巴巴爸爸是粉红色的，他有一个漂亮的妻子和七个可爱的孩子。他们*神奇的本领就是可以随意改变身体的形状，变成各种各样的东西。" +
//			"“巴巴爸爸经典系列”的**本《巴巴爸爸的诞生》出版于1970年，书一出来就受到了英国评论界权威的赞誉，并在同年的博洛尼亚书展上受到广泛好评。1975年，法国和荷兰将巴巴爸爸的故事改变成动画片，即刻风靡全球。" +
//			"★谁创作了巴巴爸爸？" +
//			"巴巴爸爸的形象是法国漫画家德鲁斯?泰勒根据棉花糖的形状而创作出来的。泰勒先生回忆说，巴巴爸爸的形象跟他家附近的一家咖啡馆很有渊源：“这家咖啡馆在巴黎有100多年的历史了，它完整地保留着20世纪初‘美丽时代’的风情，上铺着五颜六色的马赛克瓷砖，这是那个时代的标志之一……”他和他的夫人安娜特?缇森经常光临这家咖啡馆。那天，夫人和她的朋友们在哪里喝咖啡聊天，当时法语还不太好的他，就在咖啡馆的一角，拿着画夹，随手画画。这时，他看到一位妈妈领着一个孩子，孩子手上举着一个粉红色的棉花糖。受到棉花糖的启发，泰勒勾勒出了“巴巴爸爸”这个形象的雏形……" +
//			"在法国，棉花糖就叫“巴巴爸爸”，因为法国的棉花糖都是粉红色的，所以巴巴爸爸也是粉红色。" +
//			"★巴巴爸爸图画书进入中国的曲折历程。" +
//			"接力出版社的总编辑白冰先生非常喜爱巴巴爸爸，早在2003年接力出版社就想引进巴巴爸爸图画书，他甚至还找了一个版本，请翻译试译了部分样稿，并亲自和编辑对译稿进行了逐字逐句地推敲和编校。但因为各种原因，当时没有取得授权，只好作罢，但出版“巴巴爸爸”中文简体版，一直是他的一个梦想。" +
//			"接力社国际版权部的谢逢蓓在法国留学时，曾与泰勒先生多次联系，表达接力出版社出版这套书中文简体字版的意愿，为了见到泰勒先生，她曾经特意去泰勒先生常去的那家咖啡馆，一次，两次，三次……*长的一次，她在那里等待了一个多小时，连咖啡馆的侍者都被打动了。" +
//			"也许是接力社的诚意感动了泰勒先生，2009年四月份，结束了意大利博洛尼亚书展后，白冰先生专程赶往巴黎，终于见到了他敬慕已久的泰勒先生。当白冰先生再次表达接";
//	$scope.pageData.recomm = {
//			id : 4,
//			headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//			nickname : "我是nick",
//			text : "这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好这书很好很好",
//			comTime : "2015年12月2日",
//			commentPicThum : ["http://pc.0angle.com/sp/img/0angle_icon.png",
//			                  "http://pc.0angle.com/sp/img/0angle_icon.png",],
//            commentPic     : ["http://pc.0angle.com/sp/img/0angle_icon.png",
//		                      "http://pc.0angle.com/sp/img/0angle_icon.png",],
//	};
	$scope.pageData.myComm = {
		text:"",
		myPics: [],
	};
//	
	$scope.pageData.comments = [
//						{
//							id : 2,
//							headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//							nickname : "我是发布者名字",
//							text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
//							comTime : "2016年12月22日 16:32",
//							isCurrentUserLiked :false,
//							likedNumber : 32,
//							isCurrentUserCreator : false,
//							likeId : 0,
//							commentPicThum : [
//							                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//							                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//							                  ],
//						   commentPic : [
//						                     "http://pc.0angle.com/sp/img/0angle_icon.png",
//						                     "http://pc.0angle.com/sp/img/0angle_icon.png",
//						                     ],
//						   recomComment : {
//									id : 9,
//									nickname :"i am nickname",
//									text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//									likedNumber : 12,
//									isCurrentUserLiked : true,
//							},
//						},
//						
//						{
//							id : 2,
//							headImgUrl : "http://pc.0angle.com/sp/img/0angle_icon.png",
//							nickname : "我是发布者名字",
//							text : "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
//							comTime : "2016年12月22日 16:32",
//							isCurrentUserLiked :false,
//							likedNumber : 32,
//							commentPicThum : [
//							                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//							                  "http://pc.0angle.com/sp/img/0angle_icon.png",
//							                  ],
//						   commentPic : [
//						                     "http://pc.0angle.com/sp/img/0angle_icon.png",
//						                     "http://pc.0angle.com/sp/img/0angle_icon.png",
//						                     ],
//						   recomComment : {
//									id : 9,
//									nickname :"i am nickname",
//									text : "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext",
//									likedNumber : 12,
//									isCurrentUserLiked : true,
//							},
//						},
//	                   
	                   ];
	$scope.pageData.commentsTotalNumber = 100;
	
	
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
