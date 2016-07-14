<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form>
<script type="text/ng-template" id="popupConfirmDialog">
		<div class="modal-header">
			<h3 class="modal-title">确认</h3>
		</div>
		<div class="modal-body">

	<div class="form-group">
		<label for="" class="control-label confirmMessage">jlkjhlkj{{confirmMessege}}</label>
    </div>

</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary" ng-click="ok()">
			确认
			</button>
			<button class="btn btn-warning" ng-click="cancel()">退出</button>
		</div>
	</script>
	
	</form>
