/**
 * 弹出对话框的启动service
 */
zaApp.service('modalInitialService', function ($modal) {
	
	this.openModal = function(dataScope, htmlTpl, modalController){
		
		var modalInstance = $modal.open({
			templateUrl : htmlTpl,
			//'../../sp/htmlTemplate/confirmDialog.html',
			modalFade: true,
			controller : modalController,
			scope : dataScope
			
		});
		
		modalInstance.result.then(function(retData){			
		},function(){
		});
	};
});