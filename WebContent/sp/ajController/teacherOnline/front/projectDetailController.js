

zaApp.controller('projectDetailController', function($scope, $http, $rootScope) {
	

	$scope.initialPage = function(projectId){
		
		var userData = {	projectId: projectId,
					   };
		
		var url = $rootScope.urlPath+"/doGetProjectDetail.html";
		alert(url);
		var data = JSON.stringify(userData)
		var postCfg = {
		        headers: { 'Content-Type': 'application/json; charset=UTF-8'},
		    };	
		
		/**
		retCode
		retMessage
		retData.userId
		retData.username
		retData.isAuthed
		retData.jumpToUrl
		*/
		
		$http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
				if( data.retCode != 0 ){
					alert(data.retMessage);
				}else{
					$scope.putTheDataIntoView(data.retData);
				}
			})
			.error(function(data, status, headers, config){
				alert("error: function call failed");
			return;
		});
	};
	
	
	$scope.putTheDataIntoView = function(data){
		$scope.projectTitle = data.projectTitle;
	    $scope.supporterNumber = data.supporterNumber;
	    $scope.targetAmount= data.targetAmount;
	    $scope.projectGeneralDesc= data.projectGeneralDesc;
	    $scope.leftDays= data.leftDays;
	    $scope.deadLine= data.deadLine;
	    $scope.leadInvesterName= data.leadInvesterName;
	    $scope.leadInvesterGeneralDesc= data.leadInvesterGeneralDesc;
	    $scope.headPicUrl = data.headPicUrl;
	    $scope.leadInvestHeadPic= data.leadInvestHeadPic;
	    $scope.projectDetailPic= data.projectDetailPic;

	    $scope.comments= data.comments;
	    
	    $scope.commentsNumber= data.commentsNumber;
	    $scope.projectStartTime= data.projectStartTime;
	    $scope.followerNumber= data.followerNumber;
	    $scope.isFollowed = data.isFollowed;
	    
	    
	    $scope.history= data.history;
	    
	    $scope.supportRules= data.supportRules;
	    
	    $scope.commentPages= data.commentPages;
	}
	
	
	
	
	
	
    $scope.projectTitle = "这里是众筹项目的标题";
    $scope.supporterNumber = "这里是当前支持人数";
    $scope.targetAmount="这里是目标金额";
    $scope.projectGeneralDesc="产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈产品简介啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
//    $scope.targetAmount = function() {
//        return $scope.firstName + " " + $scope.lastName;
//    }
    $scope.leftDays="这里是剩余天数";
    $scope.deadLine="这里是结束时间";
    $scope.leadInvesterName="领投人名字";
    $scope.leadInvesterGeneralDesc="领投人的简介阿哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
    $scope.headPicUrl="../sp/img/detail_img.png";
    $scope.leadInvestHeadPic="../sp/img/headIcon.png";
    $scope.projectDetailPic="../sp/img/project_detail.png";
    $scope.comments=[
					{commentIconUrl:"../sp/img/headIcon_small.png",commentor:"我是评论人1("+$scope.currentCommentsPageIndex+")",hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:"我是评论人2("+$scope.currentCommentsPageIndex+")",hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:"我是评论人3("+$scope.currentCommentsPageIndex+")",hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:"我是评论人4("+$scope.currentCommentsPageIndex+")",hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:'我是评论人5',hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:'我是评论人6',hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:'我是评论人7',hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:'我是评论人8',hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'},
                    {commentIconUrl:"../sp/img/headIcon_small.png",commentor:'我是评论人9',hours:'我是多少小时前君', comment:'我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论我是评论'}
				];
    $scope.commentsNumber="我是评论数";
    $scope.projectStartTime="我是项目开始时间";
    $scope.followerNumber="我是关注人数君";
    $scope.isFollowed = true;
    
    $scope.history=[
                     {time:'我是时间君1',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君2',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君3',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君4',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君5',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君6',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君7',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君8',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君9',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'},
                     {time:'我是时间君0',headIconUrl:"../sp/img/headIcon_small.png",eventDesc:'我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我是事件说明君我'}
 				];
    $scope.supportRules=[
	                    {price:'1',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君1", toInvestAmount:"1",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'5',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君2", toInvestAmount:"5",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'10',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君3", toInvestAmount:"10",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'15',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君4", toInvestAmount:"15",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'30',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君5", toInvestAmount:"30",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'100',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君6", toInvestAmount:"100",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'},
	                    {price:'300',projectId:"我是项目ID君", investRuleId:"我是投资规则ID君7", toInvestAmount:"300",rightDesc:'我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君我是权益说明君', location:'我是地域君'}
				];
    $scope.commentPages=[
    				{pageNumber:'1'},
    				{pageNumber:'2'},
    				{pageNumber:'3'},
    				{pageNumber:'4'},
    				{pageNumber:'5'},
    				{pageNumber:'6'}
    			];
    
    
    //以上为初始字段

    $scope.currentCommentsPageIndex = 1;
    
    //以下是方法
    
    $scope.taggleFollow = function(){
    	alert("Please use $http to change the follow status of this project!");
    	$scope.isFollowed = !$scope.isFollowed;
    }
    
    $scope.submitToInvest = function(price,investAmount, projectId, investRuleId){
    	if(investAmount!=price){
    		alert("请输入的跟投金额和支持金额相同！")
    	}else{
    		alert("please add $http post function here with the parameter : "+investAmount+", "+projectId+" and "+investRuleId);
    	}
    	
    }
    
    
    $scope.submitToComment = function(){
    	if($scope.comment==null||$scope.comment==""){
    		alert("评论不能为空！请输入评论！");
    	}else{
    		alert("please add $http post function here with the parameter comment is: "+$scope.comment);
    	}
    	
    }
    
    
    $scope.refreshComments = function(pageIndex){
    	$scope.currentCommentsPageIndex = pageIndex;
    	
    	alert("please add $http post function here with the parameter to get refresh the comments of page: "+ $scope.currentPageIndex);
    }
});
