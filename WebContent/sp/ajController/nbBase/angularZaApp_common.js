var zaApp = angular.module('zaApp');



/**
 * 获取session的service
 */
zaApp.service('sessionService', function ($http, $rootScope) {
	
	this.getAdminUserDisplayName = function(){
		if( typeof($rootScope.currentAdminUser) == "undefined" ){
			return "n/a";
		}
		else{
			if( typeof($rootScope.currentAdminUser.realName) == "undefined" || $rootScope.currentAdminUser.realName.length == 0){
				return $rootScope.currentAdminUser.username;
			}
			else{
				return $rootScope.currentAdminUser.realName;
			}
		}
	}
	
	this.getSession = function(theUrl, callBacks){
		var userData = { "url" : window.location.href };
		var url = theUrl;
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
         /**
        retCode
        retMessage
        retData.userId
        retData.username
        retData.isAuthed
        retData.realName
        */
		.success(function(data, status, headers, config){
			this.retCode = data.retCode;
			this.retMessage = data.retMessage;
			this.sessionData = data.retData;
			for(var i = 0 ; i < callBacks.length; i++){
				if( typeof(callBacks[i]) != "undefined" )
					callBacks[i](this.sessionData);
			}
			//return this.sessionData;
			
		})
		.error(function(data, status, headers, config){
        	alert("error: getSession function call failed");
        	});
        
	};
	
	this.postCall = function(url, userData, callBack, theCallerScop){
		
		if( typeof(theCallerScop) == "undefined" ){
			theCallerScop = angular.element(document.body).scope(); 
		}
		
		if( typeof(theCallerScop) != "undefined")
			theCallerScop.isLoadingShow = true;
		
        var data = JSON.stringify(userData)
        var postCfg = {
	            headers: { 'Content-Type': 'application/json; charset=UTF-8'},
	        };
        $http.post(url, data, postCfg)
		.success(function(data, status, headers, config){
			console.log("call:["+url+"]\r\n");
			console.log(data);
			
			if( typeof(theCallerScop) != "undefined")
				theCallerScop.isLoadingShow = false;
			
			callBack(data);
		})
		.error(function(data, status, headers, config){
			
			if( typeof(theCallerScop) != "undefined")
				theCallerScop.isLoadingShow = false;
			
			callBack(data);
        	alert("error: "+url+" failed");
        	});
	};
});


zaApp.service('QueryParamService', function ($location) {

	this.wrapAuthUrl = function(wxAppId, url, state){
		if( typeof(wxAppId) == "undefined" || wxAppId == null){
			return url;
		}
		if( typeof(state) == "undefined"){
			state ="na";
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
		wxAppId +
		"&redirect_uri=" +
		window.encodeURIComponent(url)+
		"&response_type=code&scope=snsapi_userinfo&state=" +
		state +
		"#wechat_redirect";
	}
	
	this.getParam = function(fullUrl){
		var param = {};
		var url = $location.absUrl().split('?');
		
		if( typeof(fullUrl) != "undefined"){
			url = fullUrl.split('?');
		}
		
		if( url.length > 1){
			url = url[1].split('&');
			for(var i = 0 ; i < url.length ; i++){
				var pa = url[i].split('=');
				if( pa.length == 2)
					param[pa[0]] = window.decodeURIComponent(pa[1]); 
			}
		}
		return param;
	};
	
	this.absUrl = function(){
		return $location.absUrl();
	};
	
	this.getServerName = function(){
		return $location.protocol()+"://"+location.host;
	}
	
	this.getPath = function(){
		var path = $location.absUrl().split('/');
		console.log("path="+path);
		var ret = "";
		for(var i = 3 ; i < path.length - 1 ; i++){
			ret += "/" + path[i];
		}
		console.log(ret);
		return ret;
	}
	
	this.getPWD = function(urlBase){
		if( typeof( urlBase ) == "undefined"){
			var host = this.getServerName();
			var urlPath = host + this.getPath();
			return urlPath;
		}
		return urlBase;
	}
	
	function rotateImg(img, direction, canvas) {    
		
        //最小与最大旋转方向，图片旋转4次后回到原方向   
        if (img == null){
        	alert('target img is null!')
        	return;    
        }      
        var width = img.width;
        var height = img.height;
        var step = 0;
        if (direction == 'right') {    
            step++;    
        } else {    
            step--;
        }
        if( step < 0 ) step = step + 4;
        //旋转角度以弧度值为参数    
        var degree = step * 90 * Math.PI / 180;    
        var ctx = canvas.getContext('2d');     
        switch (step) {    
            case 0:    
                canvas.width = width;    
                canvas.height = height;    
                ctx.drawImage(img, 0, 0);    
                break;    
            case 1:    
                canvas.width = height;    
                canvas.height = width;    
                ctx.rotate(degree);    
                ctx.drawImage(img, 0, -height);    
                break;    
            case 2:    
                canvas.width = width;    
                canvas.height = height;    
                ctx.rotate(degree);    
                ctx.drawImage(img, -width, -height);    
                break;    
            case 3:    
                canvas.width = height;    
                canvas.height = width;    
                ctx.rotate(degree);    
                ctx.drawImage(img, -width, 0);    
                break;    
        }    
    }
	
	this.resizeImage = function(maxWidth, maxHeight, imgBase64, Orientation, callBackFunction){
			var img = new Image(); 
			var dataURL;

			img.src = imgBase64;
			img.onload = function(){
				//resize the image using canvas
				var canvas = document.createElement("canvas");
				var ctx = canvas.getContext("2d");
				ctx.drawImage(img, 0, 0);
				var width = img.width;
				var height = img.height;
				canvas.width = width;
				canvas.height = height;
				
				var width_rasio = width/maxWidth;
				var height_rasio = height/maxHeight;
				var rasio = width_rasio < height_rasio? height_rasio : width_rasio;
				console.log("rasio=",rasio);
				if( rasio > 1 ){
					width = parseInt(width/rasio);
					height = parseInt(height/rasio);
					console.log(rasio, width, height);
					canvas.width = width;
					canvas.height = height;
				}
				
				ctx = canvas.getContext("2d");
				ctx.drawImage(img, 0, 0, width, height);
				
				var image = new Image();
				image.src = canvas.toDataURL("image/jpeg");
				image.onload = function(){
					
					var cvs = document.createElement("canvas");
					var context = cvs.getContext("2d");
					cvs.width = image.width;
					cvs.height = image.height;
					
					context.drawImage(image, 0, 0,image.width, image.height);
					
					if (navigator.userAgent.match(/iphone/i)) {  
						//如果方向角不为1，都需要进行旋转 added by lzk  
						if(Orientation != "" && Orientation != 1){  
							switch(Orientation){  
							case 6://需要顺时针（向左）90度旋转  
								//alert('需要顺时针（向左）90度旋转');  
								rotateImg(image,'right',cvs);  
							break;  
							case 8://需要逆时针（向右）90度旋转  
								//alert('需要顺时针（向右）90度旋转');  
								rotateImg(image,'left',cvs);  
	                        break;  
							case 3://需要180度旋转  
								//alert('需要180度旋转');  
								rotateImg(image,'right',cvs);//转两次  
								rotateImg(image,'right',cvs);  
	                        break;  
							}
						}
					}
					
					dataURL = cvs.toDataURL('image/jpeg');
					if( typeof(callBackFunction) != "undefined" )
						callBackFunction(dataURL);
				}
			
			//change the dataUrl to blob data for uploading to server
			};		
		}
	
});


zaApp.service('SlideShowService', function () {

	var parentScope = [];
	parentScope.myInterval = 5000;
	parentScope.noWrapSlides = false;
	parentScope.active = 0;
	
//	<uib-carousel active="active" interval="myInterval" no-wrap="noWrapSlides">
//    <uib-slide ng-repeat="slide in pageData.slides track by slide.id" index="slide.id">
	
	var slides = parentScope.slides = [];
	
	this.setSlides = function(parentSlides) {
		slides = parentScope.slides = parentSlides;
//		var newWidth = 600 + slides.length + 1;
//		slides.push({
//	      image: 'http://lorempixel.com/' + newWidth + '/300',
//	      text: ['Nice image','Awesome photograph','That is so cool','I love that'][slides.length % 4],
//	      id: currIndex++
//	    });
	};
	
	this.randomize = function() {
		var indexes = generateIndexesArray();
		assignNewIndexesToSlides(indexes);
	};
	
	// Randomize logic below
	function assignNewIndexesToSlides(indexes) {
		for (var i = 0, l = slides.length; i < l; i++) {
			slides[i].id = indexes.pop();
		}
	}
	
	function generateIndexesArray() {
		var indexes = [];
		for (var i = 0; i < currIndex; ++i) {
			indexes[i] = i;
		}
		return shuffle(indexes);
	}

	// http://stackoverflow.com/questions/962802#962890
	function shuffle(array) {
		var tmp, current, top = array.length;
		if (top) {
			while (--top) {
				current = Math.floor(Math.random() * (top + 1));
				tmp = array[current];
				array[current] = array[top];
				array[top] = tmp;
			}
		}
		return array;
	}
	
});
