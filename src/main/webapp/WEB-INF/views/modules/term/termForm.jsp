<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>终端管理</title>
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
		<li><a href="${ctx}/term/term/">终端列表</a></li>
		<li class="active"><a href="${ctx}/term/term/form?id=${term.id}">终端<shiro:hasPermission name="term:term:edit">${not empty term.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="term:term:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="term" action="${ctx}/term/term/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<form:input path="teNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sim卡号：</label>
			<div class="controls">
				<form:input path="simNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终端电话号码：</label>
			<div class="controls">
				<form:input path="teTel" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属网格员：</label>
			<div class="controls">
				<select id="user" name="user.id" class="input-xlarge ">
						<option value="" selected="selected"/>请选择其所属一级网格管理员
					<c:forEach items="${firstList }" var="first">
						<option value="${first.id }">${first.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">所属共产党员：</label>
			<div class="controls">
				<select id="prc" name="empty1" class="input-xlarge ">
						<option value="" selected="selected"/>请选择其所属共产党员
					<c:forEach items="${prcList }" var="prc">
						<option value="${prc.id }">${prc.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端地址：</label>
			<div class="controls">
				<form:input path="teAddress" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">出厂时间：</label>
			<div class="controls">
				<input name="teProductTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${term.teProductTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		
		
		
		<div class="form-actions">
			<shiro:hasPermission name="term:term:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>