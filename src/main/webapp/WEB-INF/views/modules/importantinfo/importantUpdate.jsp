<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改停电信息</title>
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
		<li><a href="${ctx}/importantinfo/importantInformation/">返回停电信息列表</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="information" action="${ctx}/importantinfo/importantInformation/update" method="post" class="form-horizontal">
		
		
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">信息标题：</label>
			<div class="controls">
				<form:input path="imTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">停电时间：</label>
			<div class="controls">
				<input name="imStopTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${information.imStopTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="imStopTimeEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${information.imStopTimeEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">停电类型：</label>
			<div class="controls">
				<select name="imStopType" >
					<option value="紧急停电" selected="selected"/>请选择停电类型，不选则默认为紧急停电
					<option value="计划停电" />计划停电
					<option value="紧急停电"/>紧急停电
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">停电范围：</label>
			<div class="controls">
				<form:input path="imStopArea" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">停电线路：</label>
			<div class="controls">
				<form:input path="imStopLine" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		
		<input type="hidden" name="midId" value="${infoTerm.id }" />
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<select name="term.teNo" >
					<c:forEach items="${termList }" var="term">
					<c:if test="${term.teNo==information.term.teNo}">
						<option value="${information.term.teNo }" selected="selected" />${information.term.teNo }
					</c:if>
					<c:if test="${term.teNo!=information.term.teNo}">
						<option value="${term.teNo}" />${term.teNo}
					</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	
		<div class="form-actions">
			<shiro:hasPermission name="importantinfo:importantInformation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>