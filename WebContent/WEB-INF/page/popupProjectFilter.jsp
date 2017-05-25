<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<form method="post" action="home.html">

  <div class="rs-dialog" id="myModal" height="500px">
	<div class="rs-dialog-box">			
		<a class="close" href="#">&times;</a>
		<div class="rs-dialog-header">
			<h3>项目过滤器：</h3>
		</div>
		<div class="rs-dialog-body" >

	<div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
			<tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">项目开始时间段：</label>
				</td>
				<td>
	               <input type="text" name="filterStartDate_projectStart" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterStartDate_projectStart">
	            </td>
	            <td>
	            --
	            </td>
	            <td>
	               <input type="text" name="filterEndDate_projectStart" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterEndDate_projectStart">
	            </td>
            </tr>
            </table>
    </div>
    <div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
            <tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">项目结束时间段：</label>
				</td>
				<td>
	               <input type="text" name="filterStartDate_projectEnd" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterStartDate_projectEnd">
	            </td>
	            <td>
	            --
	            </td>
	            <td>
	               <input type="text" name="filterEndDate_projectEnd" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterEndDate_projectEnd">
	            </td>
            </tr>
            </table>
     </div>
      <div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
            <tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">项目收账时间段：</label>
				</td>
				<td>
	               <input type="text" name="filterStartDate_incoming" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterStartDate_incoming">
	            </td>
	            <td>
	            --
	            </td>
	            <td>
	               <input type="text" name="filterEndDate_incoming" class="form-control" placeholder="yyyy-mm-dd" id="datepicker-filterEndDate_incoming">
	            </td>
	                       
            </tr>
            </table>
		</div>
		 <div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
            <tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">项目签约状态：</label>
				</td>
	            <td>
	            		<div class="form-group">
                            <div class="col-sm-10">
                                <select class="form-control" name="isSigned">
                                    <option>所有项目</option>
                                    <option>仅签约项目</option>
                                    <option>未签约项目</option>
                                </select>
                            </div>
                        </div>
	            </td>
	                       
            </tr>
            </table>
		</div>
		<div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
            <tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">项目交付状态：</label>
				</td>
	            <td>
	            		<div class="form-group">
                            <div class="col-sm-10">
                                <select class="form-control" name="isDelived">
                                    <option>所有项目</option>
                                    <option>已交付项目</option>
                                    <option>未交付项目</option>
                                </select>
                            </div>
                        </div>
	            </td>
	                       
            </tr>
            </table>
		</div>
		
		<div class="form-group">
			<table cellspacing="3px" cellpadding="3px" width="100%">
            <tr>
				<td width="120px">
				   <label for="filterStartDate" class="control-label">关键字：</label>
				</td>
	            <td>
	            		<div class="form-group">
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="keyword" placeholder="关键字">
                            </div>
                        </div>
	            </td>
	                       
            </tr>
            </table>
		</div>
		
		</div>
		<div class="rs-dialog-footer">
			<input type="submit"  class="btn btn-primary" value="开始过滤" style="float:right">
		</div>			
	</div>		
</div>

</form>
