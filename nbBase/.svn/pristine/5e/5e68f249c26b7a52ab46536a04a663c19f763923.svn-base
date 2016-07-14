<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  	


<!-- 微信嵌入 -->
<div ng-class="{true: 'wxReady_indicator_success', false : 'wxReady_indicator_fail'}[(isWxReady+isWxConfigReady)==2]" 
style="position:absolute;top:0px;"></div>

<!-- ERROR Page -->
 <div ng-class="{true: 'weui_dialog class_show', false : 'weui_dialog class_hide'}[isErrorDialogShow]" style="border:3px solid #0f0f0f; z-index:2;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">出错了</strong></div>
        <div class="weui_dialog_bd"> {{errorMessage}}
        <br/>
        </div>
        <div class="weui_dialog_ft">
           <a href="javascript:;" class="weui_btn_dialog primary" ng-click="toggleMessageDialog()">知道了</a>
        </div>
    </div> 

<!-- loading的动画效果 -->
<div id="loadingToast" style="z-index:3;" ng-class="{false: 'weui_loading_toast class_hide' , true : 'weui_loading_toast class_show'}[isLoadingShow]" >
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <div class="weui_loading">
                <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                <div class="weui_loading_leaf weui_loading_leaf_11"></div>
            </div>
            <p class="weui_toast_content">正在与服务器同步</p>
        </div>
    </div>