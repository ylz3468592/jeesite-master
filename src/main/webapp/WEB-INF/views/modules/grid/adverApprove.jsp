<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发布图文广告的一级网格员列表</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/adverList">一级网格员列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">用户名</th>
				<th width="20%">姓名</th>
				<th width="20%">身份证号码</th>
				<th width="20%">手机</th>
				<shiro:hasPermission name="sys:user:view">
				<th width="20%">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
	<tbody>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<shiro:hasPermission name="sys:user:view">
					<td>
    					<a href="${ctx}/sys/user/adverPro?id=${user.id}">审批</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>