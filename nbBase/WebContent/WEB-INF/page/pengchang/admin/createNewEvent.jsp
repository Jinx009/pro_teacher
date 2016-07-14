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
   
   <style type="text/css">
   

.slide_left {
    
    margin-left: -100vw;

    transition: 0.5s;
}

.slide_right {
    margin-left: 100vw;
   
    
    transition: 0.5s;
}

.slide_show {
    transition: 0.5s;
    margin-right: 1.5em;
    margin-left: 0em;
    
}
.slide_panel{
	display:inline-block;
	    
	    width:100%;
	
}
   
   </style>

</head>

<body ng-app="zaApp">

    <div id="wrapper" >
    
        <jsp:include page="./navigator.jsp"></jsp:include>
        <div id="page-wrapper" 
        ng-controller="createNewEventController"  ng-init="initial()">
        <!-- Page Content -->
        <!-- 这条线以上不要加页面代码 -->
        <!-- --------------------------------------------------------------- -->
        
        <br/>
        
        <div class="panel-body">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs">
            	<li ng-class="{true:'active'}[toggle.currentTab=='basicConfig']"><a href="#" ng-click="toggleTabs('basicConfig')">活动基本配置</a>
                </li>
                <li ng-class="{true:'active'}[toggle.currentTab=='awardFeeConfig']"><a href="#" ng-click="toggleTabs('awardFeeConfig')">金额及权益配置</a>
                </li>
                <li ng-class="{true:'active'}[toggle.currentTab=='urlReffer']"><a href="#" ng-click="toggleTabs('urlReffer');generateUrls()">生成的URL信息</a>
                </li>
            </ul>

            
            <div class="tab-content">
            	
            	<!-- 活动基本信息配置 -->
                <div class="tab-pane fade in active"
                style="
                padding:0.8em;
                "
                ng-show="toggle.currentTab=='basicConfig'">
                
                	<div class="form-group">
                       <label>活动是哪个微信公众号主办的:</label>
                       <select class="form-control" ng-model="pageData.event.wxConfigId"
                       ng-options="wxConfigX.id as wxConfigX.configName for wxConfigX in pageData.wxConfigs" >
                       </select>
                     </div>
                
                	<div class="form-group ">
						<label>活动是否有效：</label>
						<div class="form-control nb-form-control-frame-bar">
							<p class="nb-form-control-checkbox-button-text"
							ng-bind="pageData.event.isActive?'发布完成，有效':'下架，不对外显示'"></p>	
							<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
							type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isActive">
						</div>
					</div>
					
                	<div class="form-group">
                            <label>活动标题:</label>
                            <input class="form-control" placeholder="填写活动的标题，字数在10字左右"
                            ng-model="pageData.event.eventTitle">
					</div>
                                        
					<div>
						<div class="form-group">
							<label>活动的简要描述:</label>
							<textarea class="form-control" rows="3" placeholder="填写活动的简要介绍，字数在50字左右"
							ng-model="pageData.event.eventDesc"></textarea>
						</div>
					</div>
					
					<div>
						<div class="form-group">
							<label>已经在微信或其他地方发布过的详情URL:</label>
							<textarea class="form-control" rows="3" placeholder="填写活动的详情URL"
							ng-model="pageData.event.eventDetailUrl"></textarea>
						</div>
					</div>
					
					<div>
						<div class="form-group">
							<label>活动的标题图片:</label>
							<div class="form-group input-group">
                                            <input type="text" class="form-control" readonly="readonly"
                                            ng-model="pageData.event.eventHeadImg">
                                            <span class="input-group-addon"><a href="#" ng-click="showSimpleFileUploadDialog(pageData.event,'eventHeadImg')">上传活动图片</a></span>
                                        </div>
						</div>
					</div>
					
					<div class="form-group">
                            <label>微信转发时小卡片的标题:</label>
                            <input class="form-control" placeholder="填写微信转发时，小卡片内显示的标题内容"
                            ng-model="pageData.event.wxCardTitle">
					</div>
                                        
					<div>
						<div class="form-group">
							<label>微信转发时小卡片内的描述:</label>
							<textarea class="form-control" rows="3" placeholder="填写微信转发时，小卡片内显示的简要说明"
							ng-model="pageData.event.wxCardDesc"></textarea>
						</div>
					</div>
					
					<div>
						<div class="form-group">
							<label>微信转发时小卡片内的图片:</label>
							<div class="form-group input-group">
                                            <input type="text" class="form-control" readonly="readonly"
                                            ng-model="pageData.event.wxCardImgUrl">
                                            <span class="input-group-addon"><a href="#" ng-click="showSimpleFileUploadDialog(pageData.event,'wxCardImgUrl')">上传图标图片</a></span>
                                        </div>
						</div>
					</div>
                
                 	<div class="form-group ">
						<label>是否众筹项目：</label>
						<div class="form-control nb-form-control-frame-bar">
							<p class="nb-form-control-checkbox-button-text"
							ng-bind="pageData.event.isCFEvent?'是':'不是'"></p>	
							<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
							type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isCFEvent">
						</div>
					</div>
					
					<div ng-show="pageData.event.isCFEvent">
						<div class="form-group ">
							<label>是否赛事众筹项目：</label>
							<div class="form-control nb-form-control-frame-bar">
								<p class="nb-form-control-checkbox-button-text" ng-bind="pageData.event.isCFMatch?'是':'不是'"></p>	
								<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
								type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isCFMatch">
							</div>
						</div>
						<div>
							<div class="form-group">
								<label>目标筹集人数:</label>
								<div class="form-group input-group">
	                                            <input type="text" type="number" placeholder="本活动目标筹集多少人？" class="form-control"
	                                            ng-model="pageData.event.targetMember">
	                                            <span class="input-group-addon">人</span>
	                                        </div>
							</div>
						</div>
						
						<div>
							<div class="form-group">
								<label>目标筹集金额:</label>
								<div class="form-group input-group">
	                                            <input type="text" type="number" placeholder="本活动目标筹集多少金额？" class="form-control"
	                                            ng-model="pageData.event.targetMoney">
	                                            <span class="input-group-addon">元</span>
	                                        </div>
							</div>
						</div>
						
						<div class="form-group ">
							<label>目标筹集金额和目标人数只要有一个达标就算众筹成功：</label>
							<div class="form-control nb-form-control-frame-bar">
								<p class="nb-form-control-checkbox-button-text"
								ng-bind="pageData.event.isTargetOrCondition?'是的':'不是'"></p>	
								<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
								type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isTargetOrCondition">
							</div>
						</div>
						
						<div ng-show="!pageData.event.isTargetOrCondition">
							<div class="form-group ">
								<label>必须达成目标筹集人数才能达标：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text"
									ng-bind="pageData.event.isTargetMemberNecessary?'必须':'不是必须'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isTargetMemberNecessary">
								</div>
							</div>
							
							<div class="form-group ">
								<label>必须达成目标筹集金额才能达标：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text"
									ng-bind="pageData.event.isTargetMoneyNecessary?'必须':'不是必须'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isTargetMoneyNecessary">
								</div>
							</div>
						</div>
					
					</div>
					
					
					<div class="form-group ">
						<label>是否线下活动：</label>
						<div class="form-control nb-form-control-frame-bar">
							<p class="nb-form-control-checkbox-button-text"
							ng-bind="pageData.event.isO2OEvent?'是，O2O活动':'不是'"></p>	
							<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
							type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isO2OEvent">
						</div>
					</div>
					
					<div ng-show="pageData.event.isO2OEvent">
						<div class="form-group">
							<label>活动地址:</label>
							<textarea class="form-control" rows="3" placeholder="填写活动的举办地址"
							ng-model="pageData.event.eventAddress"></textarea>
						</div>
					</div>
					
					<div class="form-group ">
						<label>是否线下活动：</label>
						<div class="form-control nb-form-control-frame-bar">
							<p class="nb-form-control-checkbox-button-text"
							ng-bind="pageData.event.isEventHasTime?'是，活动有具体的举办时间':'不是，活动没有具体的举办时间'"></p>	
							<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
							type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isEventHasTime">
						</div>
					</div>
					
					<div ng-show="pageData.event.isEventHasTime">
						<div class="form-group">
							<label>活动举办时间:</label>
							<table style="
							width:100%;
							">
							<tr >
							<td style="
							text-align:center;
							"
							><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:10em;text-align:center;float:right;" ng-model="pageData.event.eventTime.year"></td>
							<td>年</td>
							<td style="
							text-align:center;
							"><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.eventTime.month"></td>
							<td>月</td>
							<td style="
							text-align:center;
							"><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.eventTime.day"></td>
							<td>日</td>
							<td style="
							text-align:center;
							"><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.eventTime.hour"></td>
							<td> : </td>
							<td style="
							text-align:center;
							"><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:left;" ng-model="pageData.event.eventTime.minute"></td>
							</tr>
							</table>
						</div>
					</div>
					
					<div class="form-group ">
						<label>是否有报名截止时间：</label>
						<div class="form-control nb-form-control-frame-bar">
							<p class="nb-form-control-checkbox-button-text"
							ng-bind="pageData.event.isNeedDeadline?'是的，报名有截止时间':'不设置报名截止时间'"></p>	
							<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
							type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isNeedDeadline">
						</div>
					</div>
					
					<div ng-show="pageData.event.isNeedDeadline">
						<div class="form-group">
							<label>报名截止时间:</label>
							
							<table style="
							width:100%;
							">
							<tr >
							<td><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:10em;text-align:center;float:right;" ng-model="pageData.event.deadLineDate.year"></td>
							<td>年</td>
							<td><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.deadLineDate.month"></td>
							<td>月</td>
							<td><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.deadLineDate.day"></td>
							<td>日</td>
							<td><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:right;" ng-model="pageData.event.deadLineDate.hour"></td>
							<td> : </td>
							<td><input class="form-control" type="number" style="border:0px;border-bottom:1px solid #afafaf;width:5em;text-align:center;float:left;" ng-model="pageData.event.deadLineDate.minute"></td>
							</tr>
							</table>

						</div>
					</div>
					
					
					
					
					
                </div>
             	 <!-- 基本配置 end //-->   
              
              
              	<!-- 金额及权益配置 -->
                 <div class="tab-pane fade in active"
                style="
                padding:0.8em;
                /* overflow: hidden; */
               /*  white-space : nowrap; */
                "
                ng-show="toggle.currentTab=='awardFeeConfig'"
                >
                <div class="panel panel-default"
                ng-repeat="ruleX in pageData.event.rules"<%-- 
               ng-class="{ 
                'slide_right' : toggle.showingRule<$index,
                'slide_show' : toggle.showingRule==$index ,
                'slide_left' : toggle.showingRule>$index,
                }"
 --%>                >
                        <div class="panel-heading">
                        <table style="width:100%;">
                        <tr>
                        	<td
                        	ng-click="toggle.showingRule = toggle.showingRule - 1;"
                        	>
	                        	<div class="fa-chevron-left"
	                        	style="
	                        	font-family:'FontAwesome';
	                        	width:100%;
	                        	display:inline-block;
	                        	vertical-align:middle;
	                        	">
	                        	</div>
                        	</td>
                        	<td>
	                        	<div
	                        	style="
	                        	display: inline-block;
							    width: 100%;
							    vertical-align:middle;
	                        	">
	                            	规则ID号: {{ruleX.id}}
	                            </div>
                        	</td>
                        	<td>
	                        	<div
	                            style="    
	                            text-align: right;
							    display: inline-block;
							    vertical-align:center;
							    width: 100%;">
	                            <button type="button" class="btn btn-default" style="
					        		min-width:8em;
					        		color:#cc0000;
					        		
					        	" ng-click="checkDeletedRule($index)"
					        	ng-bind="ruleX.isToDeleted ? '取 消 删 除' : '删 除'"></button>
					        	</div>
                        	</td>
                        	<td>
					        	<div class="fa-chevron-right"
					        	style="
					        	font-family:'FontAwesome';
					        	display:inline-block;
	                        	width:100%;
	                        	text-align:right;
	                        	vertical-align:middle;
	                        	
	                        	"
	                        	ng-click="toggle.showingRule = toggle.showingRule + 1;"
	                        	>
                        	</div>
                        	</td>
                        </tr>
                        </table>

                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body" style="position:relative;">
                        <div
                        style="
                        	width: 100%;
						    height: 100%;
						    background-color: rgba(128,128,128,0.9);
						    z-index: 3;
						    position: absolute;
						    margin: -15px;
						    text-align: center;
						    font-size: 3em;
    						line-height: 10em;
    						color:white;
                        "
                        ng-show="ruleX.isToDeleted">
                        提交后删除！
                        </div>
                        	
                        	<div class="form-group">
		                            <label>规则标题:</label>
		                            <input class="form-control" placeholder="例：支持100元" ng-model="ruleX.ruleTitle">
							</div>
		                                        
							<div>
								<div class="form-group">
									<label>规则权益的说明:</label>
									<textarea class="form-control" rows="3" placeholder="例：您的支持将获得热吻一个，还可以取得主播网红的亲笔签名照片"
									ng-model="ruleX.ruleDesc"></textarea>
								</div>
							</div>
							
							<div class="form-group">
									<label>本规则允许的最大金额:（0为没有上限）</label>
									<div class="form-group input-group">
		                                            <input type="text" type="number" placeholder="" class="form-control"
		                                            ng-model="ruleX.ruleMaxAmount">
		                                            <span class="input-group-addon">元</span>
		                                        </div>
							</div>
							<div class="form-group">
									<label>本规则允许的最大人数:（0为没有上限）</label>
									<div class="form-group input-group">
		                                            <input type="text" type="number" placeholder="" class="form-control"
		                                            ng-model="ruleX.ruleMaxMember">
		                                            <span class="input-group-addon">人</span>
		                                        </div>
							</div>
							
							<div class="form-group ">
								<label>支付金额是否固定还是可以让用户自由填写：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text" ng-bind="ruleX.isAmountFreeInput ? '认购者自由填写金额' : '认购金额由商家固定'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isAmountFreeInput">
								</div>
							</div>
							<div ng-show="!ruleX.isAmountFreeInput">
								<div class="form-group">
									<label>每份的固定金额为:</label>
									<div class="form-group input-group">
		                                            <input type="text" type="number" placeholder="" class="form-control"
		                                            ng-model="ruleX.unitPrice">
		                                            <span class="input-group-addon">元</span>
		                                        </div>
								</div>
							
							
								<div class="form-group ">
									<label>每个用户是否可以认购多份：</label>
									<div class="form-control nb-form-control-frame-bar">
										<p class="nb-form-control-checkbox-button-text" ng-bind="ruleX.isCanManyCopy ? '可以认购多份' : '不可以，只能一人一份'"></p>	
										<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
										type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isCanManyCopy">
									</div>
								</div>
							</div>
							
							<div class="form-group ">
								<label>认购金额是否计入目标金额：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text" ng-bind="ruleX.isCountInAmount?'是的':'不计入'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isCountInAmount">
								</div>
							</div>
							
							<div class="form-group ">
								<label>认购人数是否计入目标人数：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text" ng-bind="ruleX.isCountInMember?'是的':'不计入'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isCountInMember">
								</div>
							</div>
							
							<div class="form-group ">
								<label>认购者是否需要填写邮寄地址：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text" ng-bind="ruleX.isNeedAddress?'是的,需要客户提供物理地址':'不需要'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isNeedAddress">
								</div>
							</div>
							
							<div class="form-group ">
								<label>是否需要给认购者生成入场二维码：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text"  ng-bind="ruleX.isNeedBarcode?'是的,需要给用户生成入场二维码':'不需要'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="ruleX.isNeedBarcode">
								</div>
							</div>
                        	
                        	
						</div>
                        <!-- .panel-body -->
                 </div>
                
                
                <div class="panel panel-default slide_panel"
                ng-click ="addNewRule()"
                >
                        <div class="panel-heading">
                            添加一个新的支付及权益规则
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body" style="text-align: center;">
                        	<img style="max-width: 6em;" src="../sp/img/plus2_.png">
						</div>
                        <!-- .panel-body -->
                    </div>
                </div>
                <!-- 金额及权益配置 end -->
                
            </div>
		</div>
		
		 <div class="tab-pane fade in active"
                style="
                padding:0.8em;
                /* overflow: hidden; */
               /*  white-space : nowrap; */
                "
                ng-show="toggle.currentTab=='urlReffer'"
                >
		
							<div class="form-group ">
								<label>是否生成微信授权地址：</label>
								<div class="form-control nb-form-control-frame-bar">
									<p class="nb-form-control-checkbox-button-text" ng-bind="pageData.event.isWxAuthUrl ? '是的，我要看授权地址' : '不，我只要看普通地址'"></p>	
									<input class="weui_switch nb-form-control-checkbox-button-checkbox" 
									type="checkbox" style="margin: 0px;margin-right: 1em;" ng-model="pageData.event.isWxAuthUrl"
									ng-change="generateUrls()">
								</div>
							</div>
						<div class="form-group">
							<label>活动入口地址:</label>
							<textarea class="form-control" rows="3" placeholder="例：您的支持将获得热吻一个，还可以取得主播网红的亲笔签名照片"
							readonly="readonly"
							ng-model="pageData.event.entryUrl"
							ng-change="getQrCodeImgUrl(pageData.event.entryUrl, pageData.event)"></textarea>
						</div>
						<div class="form-group">
							<label>二维码:</label>
							
								<img class="form-control" ng-src="{{pageData.event.urlQrCode}}"
								style="width: 256px;height: auto;"		
								></img>
							
						</div>
						
						 <div class="panel panel-default slide_panel">
		                        <div class="panel-heading">
		                            不同支付的入口地址
		                        </div>
		                        <!-- .panel-heading -->
		                        <div style="
		                        padding : 1em;
		                        ">
		                        <div ng-repeat="ruleX in pageData.event.rules">
			                        <div class="form-group" >
										<label>【{{ruleX.ruleTitle}}】</label>
										<textarea class="form-control" rows="3" placeholder="例：您的支持将获得热吻一个，还可以取得主播网红的亲笔签名照片"
										readonly="readonly"
										ng-model="ruleX.entryUrl"
										ng-change="getQrCodeImgUrl(ruleX.entryUrl, ruleX)"></textarea>
									</div>
									
									<div class="form-group">
										<label>二维码:</label>
										
											<img class="form-control" ng-src="{{ruleX.urlQrCode}}"
											style="width: 256px;height: auto;"		
											></img>
										
									</div>
								</div>
								</div>
		                        <!-- .panel-body -->
		                    </div>
		                </div>
						
	<div class="col-lg-12"
		style="
		    border-bottom: 2px solid #a0a0a0;
		">
		&nbsp
        </div>
		
		<div class="" style="text-align:right;padding:1.5em;">
		<br/>

        	<button type="button" class="btn btn-default"
        	style="
        		min-width:8em;
        		color:#cc0000;
        		
        	"
        	ng-click="saveNewEvent()"
        	>保 存</button>
        	&nbsp&nbsp&nbsp
        	<button type="button" class="btn btn-default"
        	style="
        		min-width:8em;
        		color:#0f0f0f;
        		
        	"
        	ng-click="gotoEventList()">取 消</button>

        </div>	
     
  
  
  
  <!-- --------------------------------------------------------------- -->
     
     <!-- 这条线以下不要加页面代码 -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

  <jsp:include page="../../_include_js_web.jsp"></jsp:include>
  <script src="../sp/ajController/pengchang/admin/navigatorController.js"></script>
  <script src="../sp/ajController/pengchang/admin/createNewEventController.js"></script>

</body>

</html>
