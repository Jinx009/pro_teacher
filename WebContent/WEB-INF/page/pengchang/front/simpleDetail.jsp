<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  	
<title></title>

<link rel="stylesheet" type="text/css" href="./sp/css/front/example.css" media="all">

</head>
<body>
<div class="page slideIn article">
    <div class="hd">
        <h1 class="page_title">${ ret.object.title }</h1>
    </div>
    <div class="bd">
        <article class="weui_article">
            <h1></h1>
            <section>
                <h2 class="title">活动详情</h2>
                <section>
                    <h3>- 发起人</h3>
                    <h4 class="weui_media_title" ng-bind="nickname">{ ${ret.object.createrNickname} }</h4>
                    <br/>
                    <img class="weui_media_appmsg_thumb" style="width:5em;" src="${ret.object.createrHeadImg}" alt=""/> 
                </section>
                <section>
                    <h3>- 活动说明</h3>
                    <p>${ ret.object.desc }</p>
                </section>
                <section>
                    <h3>- 活动时间</h3>
                    <p>${ ret.object.eventDateTime }</p>
                </section>
                <section>
                    <h3>- 活动地点</h3>
                    <p>${ ret.object.eventAddress }</p>
                </section>
                <section>
                    <h3>- 报名截止日期</h3>
                    <p>${ ret.object.deadLine }</p>
                </section>
                <section>
                    <h3>- 目标人数</h3>
                    <p>${ ret.object.targetMember } 人</p>
                </section>
                <section>
                    <h3>- 目标金额</h3>
                    <p>${ ret.object.targetMoney } 元</p>
                </section>
            </section>
        </article>
    </div>
</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<div> _ </div>

</body>
	<jsp:include page="../../_include_js_mobile.jsp"></jsp:include>
	<script type="text/javascript" src="./sp/ajController/pengchang/front/simpleDetailController.js" ></script>
</html>