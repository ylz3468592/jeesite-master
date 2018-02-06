<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 		
	<title>广告查看</title>
	<meta name="decorator" content="default"/>
	 
</head>
 <body>  
 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/adverList">待审批图文广告列表</a></li>
	</ul>
	
	
	<sys:message content="${message}"/>
    <h4>审批图文广告</h4>
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
		<c:forEach items="${adverList}" var="adver">
			<tr>
				<td>${adver.adTitle}</td>
				<td>${adver.user.name}</td>
				<td>${adver.term.teNo}</td>
				<td>
					<c:if test="${adver.adStatus==0 }">未审核</c:if>
					<c:if test="${adver.adStatus==1 }">已审核</c:if>
					<c:if test="${adver.adStatus==2 }">已删除</c:if>
				</td>
					<td>
						<shiro:hasPermission name="sys:user:view">
						<a href="${ctx}/sys/user/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看&nbsp;</a>
						<a href="${ctx}/sys/user/adverOpen?adId=${adver.id}">批准&nbsp;</a>
						<a href="${ctx}/sys/user/adverStop?adId=${adver.id}">不批准&nbsp;</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
   
       
        </script>  
    </body>  
</html>