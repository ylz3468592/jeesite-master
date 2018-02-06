<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频广告管理</title>
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
		<li><a href="">视频广告查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adverVedio" action="${ctx}/vedioadver/adverVedio/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<form:input path="veTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频广告查看：</label>
			<div class="controls">
				<%-- <input type="text" value="${adverVedio.vePath }" class="input-xlarge required" /> --%>
				<button><a target="_blank" href ="${property.key }/FileServer/js/demo.jsp?aaa=${adverVedio.vePath }">播放</a>
				</button>
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