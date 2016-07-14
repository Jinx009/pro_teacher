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

    <div id="wrapper" >
    
        <jsp:include page="./navigator.jsp"></jsp:include>
        <div id="page-wrapper" 
        ng-controller="adminShareDetailController"  ng-init="initial()">
        <!-- Page Content -->
        <!-- 这条线以上不要加页面代码 -->
        <!-- --------------------------------------------------------------- -->
        
        <br/>
        
        <div style="
        position:relative;
        display:inline-block;
        vertical-align: top;
        ">
       <iframe
       style="
       	width:480px;
       	height:1000px;
       " 
       src="{{pageData.detailUrl}}">
       
       </iframe>
       </div>
       
       <div style="
        position:relative;
        display:inline-block;
        vertical-align: top;
        margin-left: 2em;
        ">
        <div>
	        <p
	        style="
	        		display:inline-block;
	        	    min-width: 8em;
	        "
	        ng-bind=" pageData.approves[0].approverName"
	        >1号审批人:</p>
			        <button type="button" class="btn btn-default"
			        	style="
			        		min-width:8em;
			        		color:#cc0000;
			        	"
			        	ng-click="approve(1, pageData.approves[0].isApproved)"
			        	ng-bind="pageData.approves[0].isApproved?'撤销通过':'批准通过'"
			        	>审核通过</button>
		</div>
		<br/>
		<div>
			<p
			style="
					display:inline-block;
	        	    min-width: 8em;
	        "
	        ng-bind="pageData.approves[1].approverName"
			>2号审批人:</p>
			        <button type="button" class="btn btn-default"
			        	style="
			        		min-width:8em;
			        		color:#cc0000;
			        	"
			        	ng-click="approve(2, pageData.approves[1].isApproved)"
			        	ng-bind="pageData.approves[1].isApproved?'撤销通过':'批准通过'"
			        	>审核通过</button>
		</div>
		<br>
		<div>
			<p
			style="
					display:inline-block;
	        	    min-width: 8em;
	        "
			>针对留言管理:</p>
			        <button type="button" class="btn btn-default"
			        	style="
			        		min-width:8em;
			        		color:#cc0000;
			        	"
			        	ng-click="gotoManageComments()"
			        	>进行管理</button>
		</div>
		
		<div>
			<p
			style="
					display:inline-block;
	        	    min-width: 8em;
	        "
			>添加语音Link:</p>
			        <button type="button" class="btn btn-default"
			        	style="
			        		min-width:8em;
			        		color:#cc0000;
			        	"
			        	ng-click="addVerbalUrl()"
			        	>添加</button>
			 <textarea class="form-control" rows="3" placeholder="填写URL地址"
							ng-model="pageData.verbalUrl"></textarea>
		</div>
		
		
 
  
  
  <!-- --------------------------------------------------------------- -->
     
     <!-- 这条线以下不要加页面代码 -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

  <jsp:include page="./_include_js.jsp"></jsp:include>
  <script src="../sp/ajController/yuexiang/admin/adminShareDetailController.js"></script>

</body>

</html>
