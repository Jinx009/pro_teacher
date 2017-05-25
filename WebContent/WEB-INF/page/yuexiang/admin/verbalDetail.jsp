<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理</title>

   <jsp:include page="./_include_css.jsp"></jsp:include>

</head>

<body ng-app="zaApp">

    <div class="wrapper" >
    
        <jsp:include page="./navigator.jsp"></jsp:include>
        <div class="page-wrapper" 
        ng-controller="verbalDetailController"  
        ng-init="initial()"
        style="min-height:100vh;"
        >
        <!-- Page Content -->
        <!-- 这条线以上不要加页面代码 -->
        <!-- --------------------------------------------------------------- -->
        
        <br/>
        
        
        <div class="col-lg-12">
        <div class="form-group">
        <div class="panel panel-default">
	        <div class="panel-heading">
	        	当前的ID号:{{pageData.verbalBook.id}}
	        </div>
	        <div class="panel-body">
	        	<div class="form-group">
					<label>图书的名称：</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.bookName">
						<p class="help-block">&nbsp</p>
				</div>
	
			
				<div class="form-group">
					<label>图书作者：</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.writer">
						<p class="help-block">&nbsp</p>
				</div>
			
			
				<div class="form-group">
					<label>出版社：</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.publisher">
						<p class="help-block">&nbsp</p>
				</div>
			
			
				<div class="form-group">
					<label>图书代码：</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.bookCode">
						<p class="help-block">&nbsp</p>
				</div>
				
				<div class="form-group">
					<label>图书简介：</label>
						<textarea class="form-control " rows="3" placeholder="填写图书简介" ng-model="pageData.verbalBook.bookIntroduce"></textarea>
						<p class="help-block">&nbsp</p>
				</div>
				
				
			
	
				<div class="form-group">
					<label style="width:100%;">图书封面 <a href="" style="float:right;text-decoration:underline;"
					ng-click="showSimpleFileUploadDialog()"
					>重新上传</a></label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.bookImgUrl" readonly="readonly">
						<div style="text-align:center;margin-top:1em;">
							<img ng-src="{{pageData.verbalBook.bookImgUrl}}" style="max-width:10em;max-height:10em;">
						</div>
						<p class="help-block">&nbsp</p>
				</div>
	
			
	
				<!-- <div class="form-group">
					<label>朗诵者：</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.reader">
						<p class="help-block">&nbsp</p>
				</div> -->
	
			
		
				<div class="form-group">
					<label>微信素材地址:</label>
						<input class="form-control" type="text" ng-model="pageData.verbalBook.wxUrl">
						<p class="help-block">&nbsp</p>
				</div>
	        </div><!-- panel-body -->
        </div>
        </div>
        </div>
        

			

		
		<br/>
		
		
		<div class="col-lg-12"
		style="
		    border-bottom: 2px solid #a0a0a0;
		">
		&nbsp
        </div>
		
		<div class="col-lg-12" style="text-align:right;">
		<br/>

        	<button type="button" class="btn btn-default"
        	style="
        		min-width:8em;
        		color:#cc0000;
        		
        	"
        	ng-click="updateBook()"
        	>保 存</button>
        	&nbsp&nbsp&nbsp
        	<button type="button" class="btn btn-default"
        	style="
        		min-width:8em;
        		color:#0f0f0f;
        		
        	"
        	ng-click="gotoVerbalList()">取 消</button>

        </div>
        

	
		
		
                
                
                
  <!--       <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            {{pageData.title}}
                        </div>
                        /.panel-heading
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>名称</th>
                                            <th>支付私钥</th>
                                            <th>商户ID</th>
                                            <th>微信号ID</th>
                                            <th>P12认证文件</th>
                                            <th>默认的服务器IP</th>
                                            <th>APP Secret</th>
                                            <th>默认订单有效时间</th>
                                            <th>微信支付通知URL</th>
                                            <th>AES秘钥</th>
                                            <th>是否有效</th>
                                            <th>是否默认</th>
                                            <th>域名地址</th>
                                            <th>支付成功模板消息号</th>
                                            <th>上传资源存储地址</th>
                                            <th>资源浏览地址</th>
                                            <th>p12证书bin</th>
                                            <th>公司名称</th>
                                            <th>公司logo地址</th>
                                            <th>众筹结果通知模板号</th>
                                            <th>当前的pageToken</th>
                                            <th>pageToken有效期</th>
                                            <th>新比赛结果通知模板号</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="">
											<td ng-bind="configX.id"></td>												
											<td ng-bind="configX.payKey"></td>									
											<td ng-bind="configX.mchId"></td>										
											<td ng-bind="configX.wxappid"></td>									
											<td ng-bind="configX.certFileP12"></td>							
											<td ng-bind="configX.serverDefaultIp"></td>					
											<td ng-bind="configX.appSecret"></td>								
											<td ng-bind="configX.orderDefault"></td>							
											<td ng-bind="configX.wxPayNotifyUrl"></td>						
											<td ng-bind="configX.encodingAesKey"></td>						
											<td ng-bind="configX.configName"></td>								
											<td ng-bind="configX.isActive"></td>									
											<td ng-bind="configX.isDefault"></td>								
											<td ng-bind="configX.serverName"></td>								
											<td ng-bind="configX.tplmsgPaySuccess"></td>					
											<td ng-bind="configX.resourcePath"></td>							
											<td ng-bind="configX.resourceBrowsPath"></td>				
											<td ng-bind="configX.certP12Bin"></td>								
											<td ng-bind="configX.companyName"></td>							
											<td ng-bind="configX.companyLogoUrl"></td>						
											<td ng-bind="configX.tplmsgCfResult"></td>						
											<td ng-bind="configX.currentPageToken"></td>					
											<td ng-bind="configX.currentPageTokenExpire"></td>		
											<td ng-bind="configX.tplmsgMatchResultConfirm"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            /.table-responsive
                        </div>
                        /.panel-body
                    </div>
                    /.panel
                </div> -->
     
  
  
  
  <!-- --------------------------------------------------------------- -->
     
     <!-- 这条线以下不要加页面代码 -->
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     &nbsp
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

  <jsp:include page="./_include_js.jsp"></jsp:include>
  <script src="../sp/ajController/yuexiang/admin/verbalDetailController.js"></script>

</body>

</html>
