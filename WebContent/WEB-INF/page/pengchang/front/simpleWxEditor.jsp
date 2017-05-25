<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/weui.css" media="all">

<style type="text/css">
.text_button{
	display:block;
	bottom:10%;
	right:10%;
	width:3.36em;
	height:2em;
	line-height:2em;
	text-align:center;
	background:#039b02;
	color:#f0f0f0;
	text-size:10;
	z-index:1;
	border:4px solid #039b02;
	-moz-border-radius: 59px;
	-webkit-border-radius: 59px;
	border-radius:59px;
	display:inline-block;
}

.text_button_text{
	font-size:1.5em; 
	font-weight:bolder;
}

</style>
</head>

<body style="width:100%;border:0px solid #ff0000;heigh:100%;background:#ccc;">

<div id="swxEditorDiv" style="position:fixed;bottom:3.5em;top:0em;right:0;left:0;overflow:auto;-moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius:5px;border:1px solid #32bf06">

 <iframe id="swxEditor" style="width:100%;height:100%;" onclick="javascript:alert()">

 </iframe>
 </div>




<!-- 简单的按钮菜单 -->
<div id="buttonBar" style="width:100%; border:0px solid #0f0f0f;position:fixed;bottom:0px;right:0px;height:3.5em;" >

<div style="width:100%;position:absolute; bottom:0px;margin-bottom:0.5em;display:none;z-index:3;" id="textSubMenu">

	<div class="text_button" style="margin-left:1em;bottom:auto;top:auto;width:2em;"  onclick="javascript:myGetSelection('left')">
		<p class="text_button_text">左</p>
	</div>
	<div class="text_button" style="margin-left:1.6em;bottom:auto;top:auto;width:2em;"  onclick="javascript:myGetSelection('center')">
		<p class="text_button_text">中</p>
	</div>
	<div class="text_button" style="margin-left:1.6em;bottom:auto;top:auto;width:2em;"  onclick="javascript:myGetSelection('right')">
		<p class="text_button_text">右</p>
	</div>
	
</div>

<div style="width:100%;position:absolute; bottom:0px;margin-bottom:0.5em;">
	<div class="text_button" id="textButton" style="margin-left:5em;" onclick="javascript:showTextSubMenu()">
		<p class="text_button_text">字</p>
	</div>
	<div class="text_button" style="margin-right:5em;margin-left:auto;float:right" onclick="javascript:alert('sd');">
		<p class="text_button_text">图</p>
	</div>
</div>

</div>

<script type="text/javascript" src="./sp/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">


$('#swxEditor').contents().prop('designMode','on');
$('#swxEditor').contents().find('body').attr('id','mainBody');
$('#swxEditor').contents().find('#mainBody').attr('style','background:#f0f0f0;word-wrap: break-word;word-break: break-all;overflow: auto;white-space: normal; line-height: 1.6; font-family: \"Helvetica Neue\",Helvetica,Arial,Tahoma,sans-serif; color:#5f5f5f');

//$("#buttonBar").attr('style','width:100%; border:0px solid #0f0f0f;position:fixed;bottom:'+301+';right:0px;height:7em;');
/* window.addListener('resizer',function(){
	alert('window.innerHeight');
}, true); */

var theInnerHeight  = window.innerHeight;

setTimeout(windowSizeChanged,2000);

function windowSizeChanged (){
	//alert(theInnerHeight);
	setTimeout(windowSizeChanged,2000);
	$("#buttonBar").attr('style','width:100%; border:0px solid #0f0f0f;position:fixed;bottom:'+(theInnerHeight - window.innerHeight)+'px;right:0px;height:7em;');
	$("#swxEditorDiv").attr('style','position:absolute;bottom:'+(theInnerHeight - window.innerHeight+60)+'px;top:0em;right:0;left:0;overflow:auto;-moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius:5px;border:1px solid #32bf06');
}


function myGetSelection(theAlign){
	
	console.log(document.getElementById('swxEditor').contentWindow.document.getSelection().getRangeAt(0).commonAncestorContainer);
	
	var theText = $(document.getElementById('swxEditor').contentWindow.document.getSelection().getRangeAt(0).commonAncestorContainer);
	var theDivGot = $(document.getElementById('swxEditor').contentWindow.document.getSelection().getRangeAt(0).commonAncestorContainer).parent();
	if( theDivGot[0].tagName == "BODY"){
		theText.wrap("<div id=\"abcde\"></div>");
		theDivGot = theText.parent();
	}
	
	console.log(theDivGot[0].tagName);
	
	if( theAlign == "left"){
		theDivGot.attr('style','width:100%;text-align:left;');
	}
	
	if( theAlign == "center"){
		theDivGot.attr('style','width:100%;text-align:center;');
	}
	
	if( theAlign == "right"){
		theDivGot.attr('style','width:100%;text-align:right;');	
	}
	showTextSubMenu();
	//$("#swxEditorDiv").focus();
	document.getElementById('swxEditor').contentWindow.document.body.focus();//getElementById('mainBody')
}


function showTextSubMenu(){
	$("#textButton").toggle(500);
	$("#textSubMenu").toggle(500);
}
</script>


</body>