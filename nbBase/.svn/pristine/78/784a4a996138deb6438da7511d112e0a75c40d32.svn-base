<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  	


<style type="text/css">
<!--
.mainMenuTab{
    width: 33%;
    box-shadow: 0px 0px 8px #07a09b;
    text-align: center;
    font-size: 1em;
    line-height: 2.2em;
    border-top-left-radius: 0.2em;
    border-top-right-radius: 0.2em;
    background-color: #65b978;
    border: 0.2em solid white;
    color: white;
    font-family: FontAwesome;
}
-->
</style>

<!-- 主菜单 -->

<div style="height: 2.5em;width: 100%;bottom: 0.28em;display: block;padding-left: 0.05%;padding-right: 0.05%;margin-bottom: 0.6em;/* text-align: center; */height: 11em;margin-top: 1em;background-color: rgba(121,191,115,0.15);margin: 0;padding-bottom: 1.2em;">
<div  style="display: inline-block;width: 55%;vertical-align: middle;text-align: center;">
<div ng-repeat="menuX in pageData.menu" style="display: inline-block; margin-left: 1em;margin-right: 1em; margin-top: 0.6em;"
ng-click="gotoPage(menuX.gotoPage)">
<table>
<tr><td><img ng-src="{{menuX.iconPng}}" style="text-align: center; width: 3em;height: 3em;"/></td></tr>
<tr><td ng-click="gotoPage(menuX.gotoPage)" ng-bind="menuX.text" style="font-size: 0.8em;font-weight: bolder;color: #07a09b;"></td></tr>
</table>
</div>
</div>
<div style="width: 40%;vertical-align: middle;display: inline-block;text-align: center;">
<img src="{{pageData.qrcodeurl}}" style="width: 8em;"/>
</div>
</div>


<!-- <table style="
    width: 99%;
    height: 100%;
    border-collapse: separate;
    -webkit-border-horizontal-spacing: 0.2em;
    margin-left: 0.5%;
">
<tbody><tr style="
    padding-left: 5%;
    padding-right: 5%;
">
<td ng-repeat="menuX in pageData.menu" class="mainMenuTab" ng-class"menuX.icon" ng-click="gotoPage(menuX.gotoPage)" ng-bind="menuX.text"></td>
<td class="mainMenuTab fa-files-o" ng-click="gotoPage('shareList')"> 首 页</td>
<td class="mainMenuTab fa-book" ng-click="gotoPage('shareNew')"> 分 享</td>
<td class="mainMenuTab fa-comments-o" ng-click="gotoPage('qaList')"> 问 答</td>

</tr>
</tbody></table> -->
