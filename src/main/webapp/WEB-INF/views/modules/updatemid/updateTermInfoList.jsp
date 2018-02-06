<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>升级中间表关联管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/updatemid/updateTermInfo/">升级记录</a></li>
		<shiro:hasPermission name="updatemid:updateTermInfo:edit"><li><a href="${ctx}/updatemid/updateTermInfo/form">派发升级任务</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="updateTermInfo" action="${ctx}/updatemid/updateTermInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>终端编号：</label><form:input path="" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>起始时间：</label><form:input path="" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>结束时间：</label><form:input path="" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>终端编号</th>
				<th class="sort-column login_name">升级时间</th>
				<th class="sort-column name">终端地址</th>
				<th>升级文件版本号</th>
				<th>升级状态</th>
				<shiro:hasPermission name="updatemid:updateTermInfo:edit">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="update">
			<tr>
				<td></td>
				<td><a href=""></a></td>
				<td></td>
				<td></td>
				<td></td>
				<shiro:hasPermission name="updatemid:updateTermInfo:edit"><td>
    				<a href="">修改</a>
					<a href="" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>