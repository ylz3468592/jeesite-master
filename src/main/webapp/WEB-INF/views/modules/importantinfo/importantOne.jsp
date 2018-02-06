<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看停电信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="">停电信息查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="information" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">信息标题：</label>
			<div class="controls">
				<form:input path="imTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">停电时间：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatDate value="${information.imStopTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatDate value="${information.imStopTimeEnd}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">停电类型：</label>
			<div class="controls">
				<form:input path="imStopType" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">停电范围：</label>
			<div class="controls">
				<form:input path="imStopArea" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">停电线路：</label>
			<div class="controls">
				<form:input path="imStopLine" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<form:input path="term.teNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>	
			</div>
		</div>
	
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>