<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>派发升级任务</title>
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
		<li><a href="${ctx}/updatemid/updateTermInfo/">升级记录</a></li>
		<li class="active"><a href="${ctx}/updatemid/updateTermInfo/form?id=${updateTermInfo.id}">派发升级任务<shiro:hasPermission name="updatemid:updateTermInfo:edit">${not empty updateTermInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="updatemid:updateTermInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="updateTermInfo" action="${ctx}/updatemid/updateTermInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">升级时间：</label>
			<div class="controls">
				<input name="utUpdateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${updateTermInfo.utUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<form:input path="utTermId" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择升级文件：</label>
			<div class="controls">
			
				<input type="file" name="txt_file" id="txt_file" multiple class="file-loading" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">升级文件版本号：</label>
			<div class="controls">
				<form:input path="utUiId" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="updatemid:updateTermInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="升级"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>