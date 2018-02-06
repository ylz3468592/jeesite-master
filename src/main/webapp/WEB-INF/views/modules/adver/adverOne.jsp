<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看图文广告</title>
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
		<li><a href="">图文广告查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adver" action="" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<form:input path="adTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告内容：</label>
			<div class="controls">
				<form:input path="adContent" htmlEscape="false" class="input-xlarge required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告图片：</label>
			<div class="controls">
				<img src="${ctx}/adver/adver/show?name=${adver.adImage}" style="width:140px;height:140px;"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">播放时间：</label>
			<div class="controls">
				<form:input path="adLiveTime" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<form:input path="term.teNo" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>