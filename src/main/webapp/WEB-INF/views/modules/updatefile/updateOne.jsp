<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看升级任务</title>
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
		<li><a href="${ctx}/updatefile/updateFileInfo/">返回升级记录列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="updateFileInfo" action="" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">升级文件路径：</label>
			<div class="controls">
				<input type="text" value="${updateFileInfo.uiFilePath }" class="input-xlarge required" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">文件版本号：</label>
			<div class="controls">
				<form:input path="uiFileVersion" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<form:input path="term.teNo" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>