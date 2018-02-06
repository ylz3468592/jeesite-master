<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 		
	<title>广告查看</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adver/adver/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
 <body>  
 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/term/term/">返回终端列表</a></li>
	</ul>
	<br/>
	<br/>
	<span>终端编号：${teNo}</span>
	<hr>
    <h4>所有图片广告信息</h4>         
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">标题</th>
				<th width="10%">创建人</th>
				<th width="10%">终端编号</th>
				<th width="20%">审核人</th>
				<th width="10%">审核时间</th>
				<th width="10%">广告状态</th>
				<shiro:hasPermission name="adver:adver:edit">
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
				<td>${adver.adChecker}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${adver.adCheckTime}" /></td>
				<td>
					<c:if test="${adver.adStatus==0 }">未审核</c:if>
					<c:if test="${adver.adStatus==1 }">已审核</c:if>
					<c:if test="${adver.adStatus==2 }">已删除</c:if>
				</td>
					<td>
						<shiro:hasPermission name="adver:adver:view">
						<a href="${ctx}/adver/adver/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="adver:adver:edit">
    					<a href="${ctx}/adver/adver/preUpdate?teNo=${adver.term.teNo}&adId=${adver.id}">修改</a>
						<a href="${ctx}/adver/adver/delete?teNo=${adver.term.teNo}&adId=${adver.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<br/>
    <h4>所有视频广告信息</h4>       
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">标题</th>
				<th width="10%">创建人</th>
				<th width="10%">终端编号</th>
				<th width="20%">审核人</th>
				<th width="10%">审核时间</th>
				<th width="10%">广告状态</th>
				<shiro:hasPermission name="vedioadver:adverVedio:edit">
				<th width="20%">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${veList}" var="adverVedio">
			<tr>
				<td>${adverVedio.veTitle }</td>
				<td>${adverVedio.user.name }</td>
				<td>${adverVedio.term.teNo }</td>
				<td>${adverVedio.veChecker }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${adverVedio.veCheckTime }" /></td>
				<td>
					<c:if test="${adverVedio.veStatus==0 }">未审核</c:if>
					<c:if test="${adverVedio.veStatus==1 }">已审核</c:if>
					<c:if test="${adverVedio.veStatus==2 }">已删除</c:if>
				</td>
					<td>
						<shiro:hasPermission name="vedioadver:adverVedio:view">
						<a href="${ctx}/vedioadver/adverVedio/viewOne?teNo=${adverVedio.term.teNo}&adId=${adverVedio.id}">查看</a>
						</shiro:hasPermission>	
						<shiro:hasPermission name="vedioadver:adverVedio:edit">
    					<a href="${ctx}/vedioadver/adverVedio/preUpdate?teNo=${adverVedio.term.teNo}&adId=${adverVedio.id}">修改</a>
						<a href="${ctx}/vedioadver/adverVedio/delete?teNo=${adverVedio.term.teNo}&veId=${adverVedio.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>    
    <br/>
	<br/>
	<h4>所有停电信息</h4>     
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">标题</th>
				<th width="10%">创建人</th>
				<th width="10%">终端编号</th>
				<th width="20%">停电时间</th>
				<th width="10%">停电类型</th>
				<th width="10%">信息状态</th>
				<shiro:hasPermission name="importantinfo:importantInformation:edit">
				<th width="20%">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${inforList}" var="importantinfo">
			<tr>
				<td>${importantinfo.imTitle }</td>
				<td>${importantinfo.user.name }</td>
				<td>${importantinfo.term.teNo }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${importantinfo.imStopTime }" /></td>
				<td>${importantinfo.imStopType }</td>
				<td>
					<c:if test="${importantinfo.imStatus==0 }">未审核</c:if>
					<c:if test="${importantinfo.imStatus==1 }">已审核</c:if>
					<c:if test="${importantinfo.imStatus==2 }">已删除</c:if>
				</td>
					<td>
						<shiro:hasPermission name="importantinfo:importantInformation:view">
						<a href="${ctx}/importantinfo/importantInformation/viewOne?teNo=${importantinfo.term.teNo}&adId=${importantinfo.id}">查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="importantinfo:importantInformation:edit">
    					<a href="${ctx}/importantinfo/importantInformation/preUpdate?teNo=${importantinfo.term.teNo}&adId=${importantinfo.id}">修改</a>
						<a href="${ctx}/importantinfo/importantInformation/delete?teNo=${importantinfo.term.teNo}&infoId=${importantinfo.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>  
       
        </script>  
    </body>  
</html>