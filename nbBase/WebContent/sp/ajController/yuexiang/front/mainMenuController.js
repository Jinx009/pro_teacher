
zaApp.controller('mainMenuController',function($scope, $rootScope, $http, wxJSSDKService, QueryParamService, sessionService){
	
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
	$scope.pageData.qrcodeurl = $rootScope.urlPath+"/sp/img/weixinqrcode.jpg";
	console.log($scope.pageData.qrcodeurl);
var server = $rootScope.urlPath;
$scope.pageData.urls = 
[
   {
	   name : "查看所有的分享的列表",
	   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
			   server + "/shareList.html"),
   },
   
   {
	   name : "创建一个新的分享",
	   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
			   server + "/shareAdd.html"),
   },
   {
	   name : "专家问答入口",
	   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
			   server + "/qaList.html"),
   },
   {
	   name : "有声读物列表",
	   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
			   server + "/verbalBookList.html"),
   },
   {
	   name : "有声文章列表",
	   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
			   server + "/verbalArticleList.html"),
   },
   {
	   name : "查看积分入口",
	   link : wxJSSDKService.wrapAuthUrl($scope.sessionData.wxappid, 
			   server + "/pointsInfo.html"),
   },
   {
	   name : "后台入口(admin/admin)",
	   link : wxJSSDKService.wrapAuthUrl(null,//$scope.sessionData.wxappid, 
			   server + "/admin/index.html"),
   },
 ];
console.log($scope.pageData.urls);

generateMenu();
}



/*------goto area-------*/


/*------toggle area-------*/


/*------data area-------*/

$scope.pageData = {};

$scope.pageData.menu = [];

function generateMenu(){
	$scope.pageData.menu = [];
	for(var i=0; i<$scope.pageData.menuAll.length;i++){
		if( $scope.pageData.menuAll[i].gotoPage != $rootScope.currentPageText ){
			$scope.pageData.menu.push($scope.pageData.menuAll[i]);
		}
	}
}




/*---正文结束---*/
	
	$scope.pageData.menuAll = 
		[
		 {
			 icon : "fa-files-o",
			 text : "主 页",
			 gotoPage : "shareList",
			 iconPng : $rootScope.urlPath+"/sp/img/主页.png",
		 },
		 {
			 icon : "fa-book",
			 text : " 我要分享",
			 gotoPage : "shareNew",
			 iconPng : $rootScope.urlPath+"/sp/img/我要分享.png",
		 },
		 {
			 icon : "fa-comments-o",
			 text : " 在线问答",
			 gotoPage : "qaList",
			 iconPng : $rootScope.urlPath+"/sp/img/在线问答.png",
		 },
		 {
			 icon : "fa-play-circle-o",
			 text : " 有声故事",
			 gotoPage : "verbalList",
			 iconPng : $rootScope.urlPath+"/sp/img/有声故事.png",
		 },
		 {
			 icon : "fa-play-circle-o",
			 text : " 有声读物",
			 gotoPage : "verbalArticle",
			 iconPng : $rootScope.urlPath+"/sp/img/有声读物.png",
		 },
		 ];
	
	$scope.gotoPage = function(target){
		
		var url = "";
		switch(target){
		case 'shareList':
			url = $scope.pageData.urls[0].link;
			break;
		case 'shareNew':
			url = $scope.pageData.urls[1].link;
			break;
		case 'qaList':
			url = $scope.pageData.urls[2].link;
			break;
		case 'verbalList':
			url = $scope.pageData.urls[3].link;
			break;
		case 'verbalArticle':
			url = $scope.pageData.urls[4].link;
			break;
		}
		
		window.location.href= url;
		
	}			

	
});