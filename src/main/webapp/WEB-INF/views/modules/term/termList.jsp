<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>终端管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/term/term/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/term/term/">终端列表</a></li>
		<shiro:hasPermission name="term:term:edit"><li><a href="${ctx}/term/term/form">终端添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="term" action="${ctx}/term/term/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>终端编号：</label><form:input path="teNo" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>终端地址：</label><form:input path="teAddress" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>网格员：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
				<div class="panel-body">  
                    <div class="list-op" id="list_op">  
                        <button type="button" class="btn btn-default btn-sm" onclick="doDelete()">  
                            <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span>删除  
                        </button>  
                    </div>  
                </div> 
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="allc"  type="checkbox">全选</th>
				<th>终端编号</th>
				<th class="sort-column login_name">终端地址</th>
				<th class="sort-column name">所属网格员</th>
				<th>电话号码</th>
				<th>终端状态</th>
				<shiro:hasPermission name="term:term:edit">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${page.list}" var="term">
			<tr>
				<td><input type="checkbox" value="${adver.id}"></td>
				<td><a href="${ctx}/term/term/findAllAdver?teNo=${term.teNo}">${term.teNo }</td>
				<td>${term.teAddress }</td>
				<td><a href="${ctx}/sys/user/form?id=${term.user.id}">${term.user.name }</a></td>
				<td>${term.teTel }</td>
				<td>
					<c:if test="${term.teStatus==0 }">无效</c:if>
					<c:if test="${term.teStatus==1 }">有效</c:if>
				</td>
				<td>
					<shiro:hasPermission name="term:term:view">
						<a href="${ctx}/term/term/viewOne?teNo=${term.teNo}">查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="term:term:edit">
    					<a href="${ctx}/term/term/preUpdate?teNo=${term.teNo}">修改</a>
						<a href="${ctx}/term/term/delete?termId=${term.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script >
		 var checkedAll = document.getElementById("allc")
            var tb=document.getElementById("tb")
            var oinput=tb.getElementsByTagName('input')  
             //全选 
      		checkedAll.onclick=function(){ 
       		 for(var i=0;i<oinput.length;i++){ 
         		 oinput[i].checked=this.checked 
      		 	 } 
   		  	 } 
   		  	 
       //当下级有一个没有选中时那么全选按钮则为false状态 
     	 for(var i=0;i<oinput.length;i++){ 
       		 oinput[i].onclick=function(){ 
         	 var qx=true
          for(var i=0;i<oinput.length;i++){ 
            if(oinput[i].checked==false){ 
              qx=false
            } 
          } 
          checkedAll.checked=qx
        } 
      } 
     
       function doDelete(){
        var inputArr = $("#tb").find("input");
        var ids="";
        for(var i = 0;i<inputArr.length;i++){
        	if(inputArr[i].checked){
        		ids+=inputArr[i].value+",";
        	}
        }
        if(ids.length==0){
            alert("所选的记录为空，请重新选择");
            return false;
        }
        $.ajax({
        	url:"${ctx}/term/term/batchDelete",
        	"ids":ids,
        	success:function(data){
        	}
        	
        });
    } 
	</script>
</body>
</html>