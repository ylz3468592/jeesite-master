<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 		
	<title>广告查看</title>
	<meta name="decorator" content="default"/>
	 
</head>
 <body>  
 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/vedioAdverList">待审批视频广告列表</a></li>
	</ul>
	
	
	<sys:message content="${message}"/>
    <h4>审批视频广告</h4>
    <br/>        
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">标题</th>
				<th width="20%">创建人</th>
				<th width="20%">终端编号</th>
				<th width="20%">广告状态</th>
				<shiro:hasPermission name="sys:user:view">
				<th width="20%">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${veList}" var="vedio">
			<tr>
				<td>${vedio.veTitle}</td>
				<td>${vedio.user.name}</td>
				<td>${vedio.term.teNo}</td>
				<td>
					<c:if test="${vedio.veStatus==0 }">未审核</c:if>
					<c:if test="${vedio.veStatus==1 }">已审核</c:if>
					<c:if test="${vedio.veStatus==2 }">已删除</c:if>
				</td>
					<td>
						<shiro:hasPermission name="sys:user:view">
						<a href="${ctx}/sys/user/viewOne2?teNo=${vedio.term.teNo}&adId=${vedio.id}">查看&nbsp;</a>
						<a href="${ctx}/sys/user/vedioOpen?adId=${vedio.id}">批准&nbsp;</a>
						<a href="${ctx}/sys/user/vedioStop?adId=${vedio.id}">不批准&nbsp;</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
   
       
        </script>  
    </body>  
</html>