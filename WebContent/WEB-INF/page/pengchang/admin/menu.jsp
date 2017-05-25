<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
<!--



-->
</style>


<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav in" id="side-menu">
            <sidebar-search>
            <li class="sidebar-search">
			    <div class="input-group custom-search-form">
			        <input type="text" class="form-control" placeholder="Search...">
			        <span class="input-group-btn">
			            <button class="btn btn-default" type="button" ng-click="showConfirmDialog('搜索功能还未完成！','error')">
			                <i class="fa fa-search"></i>
			            </button>
			        </span>
			    </div>
			</li>
            </sidebar-search>
            
            <li ng-repeat="menuItemX in menus" ng-class="{active: collapseVar==$index}" 
            > 
                <a href="{{menuItemX.url}}" ng-click="check($index)"><i class="fa fa-th-large fa-fw"></i> {{menuItemX.text}}<span
                        class="fa arrow"
                        ng-show="menuItemX.child.length > 0"
                        ></span></a>
                <ul class="nav nav-second-level" collapse="collapseVar!=$index" ng-show="menuItemX.child.length > 0">
                    <li ui-sref-active="active" ng-repeat="subItemX in menuItemX.child"  ng-class="{menuActive: selectMenuId == subItemX.id}">
                        <a href="{{(selectMenuId == subItemX.id && disableSelectedMenu)?'#':subItemX.url}}"> &nbsp&nbsp&nbsp{{subItemX.text}}</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>
            
          
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div> 
