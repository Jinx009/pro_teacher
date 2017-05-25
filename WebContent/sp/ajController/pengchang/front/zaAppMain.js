var zaApp = angular.module('zaApp2',['ui.router']);

zaApp.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.when("", "/PageTab");
	 
    $stateProvider
       .state("PageTab", {
           url: "/PageTab",
           templateUrl: "PageTab.html"
       })
       .state("PageTab.Page1", {
           url:"/Page1",
           templateUrl: "Page-1.html"
       })
       .state("PageTab.Page2", {
           url:"/Page2",
           templateUrl: "Page-2.html"
       })
       .state("PageTab.Page3", {
           url:"/Page3",
           templateUrl: "Page3.html"
       });
});

zaApp.controller('mainController',function($scope, $rootScope){
	
	$scope.isOK = false;
	
	$scope.msgTitle = "操作成功";
	$scope.msgDesc = "提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容提示内容";
	
	$scope.initial = function(){
		
	}
	
	
})