<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改视频广告</title>
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
		<li><a href="${ctx}/vedioadver/adverVedio/">返回视频广告列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adverVedio" action="${ctx}/vedioadver/adverVedio/update" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<form:input path="veTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告文件：</label>
			<div class="controls">
				<input type="file" name="file"  />
			</div>
		</div>
		
		<input type="hidden" name="midId" value="${infoTerm.id }" />
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<select name="term.teNo" >
					<c:forEach items="${termList }" var="term">
					<c:if test="${term.teNo==adverVedio.term.teNo}">
						<option value="${adverVedio.term.teNo }" selected="selected" />${adverVedio.term.teNo }
					</c:if>
					<c:if test="${term.teNo!=adverVedio.term.teNo}">
						<option value="${term.teNo}" />${term.teNo}
					</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="vedioadver:adverVedio:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>