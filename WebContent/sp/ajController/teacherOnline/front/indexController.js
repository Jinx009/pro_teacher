
zaApp.controller('indexController', function($scope, $rootScope, $http, $sce, sessionService, QueryParamService, SlideShowService)
{
	
	$scope.isLoadingShow = false;
	$scope.pageData = [];
	
	
	window.addEventListener('resize', function() {
	    $scope.$apply(function(){ 
	    	$scope.bodyWidth = document.body.clientWidth;
	    	console.log($scope.bodyWidth);
	    });
	}, true);
	
	$scope.initial = function(){
		$rootScope.urlPath = QueryParamService.getPWD();
		for (var i = 0; i < 4; i++) {
		    $scope.addSlide();
		  }
		console.log($scope.slides);
		$scope.bodyWidth = document.body.clientWidth;
		$scope.toggleActiveRecommTeacher(1);
	}
	
	$scope.pageData.slides = [];
	$scope.pageData.slidesInterval = 5000;
	
	
	//toggle area
	
	$scope.toggleActiveRecommTeacher = function(index){
		for( var i = 0 ; i < $scope.pageData.subjects.length ; i++)
			$scope.pageData.subjects[i].toggleIsActive = false;
		$scope.pageData.subjects[index].toggleIsActive = true;
	}
	
	
	// data area
	
	$scope.pageData.subjects = 
		[
		 {
			 grade : "1",
			 subjectName : "语文", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/recommend.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 {
			 grade : "1",
			 subjectName : "数学", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/recommend.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 {
			 grade : "1",
			 subjectName : "英语", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/recommend.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 {
			 grade : "1",
			 subjectName : "物理", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/recommend.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 {
			 grade : "1",
			 subjectName : "政治", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/banner.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 {
			 grade : "1",
			 subjectName : "体育", 
			 toogleIsActive : false,
			 info : 
			 {
				imgUrl : "sp/img/teacherOnline/recommend.png",
				title : "师范二附中 - 王XX",
				desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
				//desc : "王XX老师是blablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla",
			 },
		 },
		 ];
		$scope.pageData.recommTeachers = 
			[
			 {
				 teacherTitle : "王淑娟-初中语文特级教师",
				 teacherDesc : "教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，",
				 teacherEmployer : "华师大二附中（曾）",
				 bestCourse : "儿童学习心理建设",
				 expeirence : "25年",
				 likeRadio : "80%",
			 },
			 {
				 teacherTitle : "王淑娟-初中语文特级教师",
				 teacherDesc : "教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，",
				 teacherEmployer : "华师大二附中（曾）",
				 bestCourse : "儿童学习心理建设",
				 expeirence : "25年",
				 likeRadio : "80%",
			 },
			 {
				 teacherTitle : "王淑娟-初中语文特级教师",
				 teacherDesc : "教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，教学经历和教学特长的文字介绍，",
				 teacherEmployer : "华师大二附中（曾）",
				 bestCourse : "儿童学习心理建设",
				 expeirence : "25年",
				 likeRadio : "80%",
			 },
			 ];
	
	
	var currIndex = 0;
	$scope.addSlide = function(){
		$scope.pageData.slides.push({
	      image: "sp/img/teacherOnline/banner.png",
	      text01: $sce.trustAsHtml("<font><span>格局</span>越大</font>&nbsp;&nbsp;&nbsp;<font>你的<span>机会</span>就越多</font>"),
	      text02: "The larger the pattern, the more your chances",
	      buttonText: "查看项目",
	      id: currIndex++
	    });
	}
	
	//SlideShowService.setSlides($scope.pageData.slides);

	
	});
