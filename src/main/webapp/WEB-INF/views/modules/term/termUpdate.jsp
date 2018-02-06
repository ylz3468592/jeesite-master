<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改终端信息</title>
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
		<li><a href="${ctx}/term/term/">返回终端列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="term" action="${ctx}/term/term/update" method="post" class="form-horizontal">
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
			<label class="control-label">所属一级网格员：</label>
			<div class="controls">
				<select id="user" name="user.id" class="input-xlarge ">
						<option value="${term.user.id }" selected="selected"/>${term.user.name }
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
						<option value="${prcUser.id }" selected="selected"/>${prcUser.name }
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
		
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<select id="status" name="teStatus" class="required">
				<c:if test="${term.teStatus==1 }">
					<option value="1" selected="selected"/>是
					<option value="0" />否
				</c:if>
				<c:if test="${term.teStatus==0 }">
					<option value="0" selected="selected"/>否
					<option value="1" />是
				</c:if>
				</select>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="term:term:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>