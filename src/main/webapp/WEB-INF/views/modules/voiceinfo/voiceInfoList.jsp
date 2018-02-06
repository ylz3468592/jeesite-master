<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>语音留言信息管理</title>
<meta name="decorator" content="default" />
<script src="amrnb.js" defer></script>
<script type="text/javascript">
	$(document).ready(function() {});
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/voiceinfo/voiceInfo/list");
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/voiceinfo/voiceInfo/">语音留言信息列表</a></li>
	</ul>

	<sys:message content="${message}" />
	<div class="panel-body">
		<div class="list-op" id="list_op">
			<button type="button" class="btn btn-default btn-sm"
				onclick="doDelete()">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="allc" type="checkbox">全选</th>
				<th>用户id</th>
				<th>终端编号</th>
				<th>居民电话</th>
				<th>语音文件</th>
				<th>居民留言时间</th>
				<shiro:hasPermission name="voiceinfo:voiceInfo:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
			<c:forEach items="${voList}" var="voiceInfo">
				<tr>
					<td><input type="checkbox" value="${voiceInfo.id}"></td>
					<td>${voiceInfo.coId}</td>
					<td>${voiceInfo.termNo}</td>
					<td>${voiceInfo.viCoTel}</td>
					<td>
						<button><a target="_blank" href ="${property.key }/FileServer/js/test.jsp?bbb=${voiceInfo.viPath}">播放</a>
						</button>
					</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${voiceInfo.viLeaveDate}" /></td>
					<td><shiro:hasPermission name="voiceinfo:voiceInfo:edit">
							<a href="${ctx}/voiceinfo/voiceInfo/delete?id=${voiceInfo.id}"
								onclick="return confirmx('确认要删除该语音留言信息吗？', this.href)">删除</a>
						</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>