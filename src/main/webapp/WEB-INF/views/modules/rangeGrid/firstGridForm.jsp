<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一级网格员分配</title>
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
		<li><a href="${ctx}/gridman/gridman/list">返回一级网格员列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/gridman/gridman/firstSave" method="post" class="form-horizontal" >
		<sys:message content="${message}"/>
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">一级网格员姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">选择二级网格员:</label>
			<div class="controls">
				<select id="prc" name="secondGrid" class="input-xlarge-required " style='width:150px;'>
					<c:forEach items="${secondList }" var="prc">
						<c:if test="${secondUser.id==prc.id}">
							<option value="${secondUser.id}" selected="selected"/>${secondUser.name}</option>
						</c:if>
						<c:if test="${secondUser.id!=prc.id}">
							<option value="${prc.id }">${prc.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
		
	
		<div class="form-actions">
			<shiro:hasPermission name="gridman:gridman:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>