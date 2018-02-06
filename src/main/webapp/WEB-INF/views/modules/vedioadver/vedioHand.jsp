<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频广告下发</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/vedioadver/adverVedio/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/vedioadver/adverVedio/">返回视频广告列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adverVedio" action="${ctx}/vedioadver/adverVedio/preHand" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>标题：</label><form:input path="veTitle" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>创建人：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>终端编号：</label><form:input path="term.teNo" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="panel-body">  
                    <div class="list-op" id="list_op">  
                        <button type="button" class="btn btn-default btn-sm" onclick="doHand()">  
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>下发  
                        </button>  
                    </div>  
                </div>  
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="allc"  type="checkbox">全选</th>
				<th>标题</th>
				<th class="sort-column login_name">创建人</th>
				<th>终端编号</th>
				<th class="sort-column name">审核人</th>
				<th>审核时间</th>
				<th>广告状态</th>
				<shiro:hasPermission name="vedioadver:adverVedio:edit">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${page.list}" var="adverVedio">
			<tr>
				<td><input type="checkbox" value="${adverVedio.id}"></td>
				<td>${adverVedio.veTitle }</td>
				<td>${adverVedio.user.name }</td>
				<td>${adverVedio.term.teNo }</td>
				<td>${adverVedio.userChecker.name }</td>
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
    					<a href="${ctx}/vedioadver/adverVedio/doHand?teNo=${adverVedio.term.teNo}&adId=${adverVedio.id}">下发</a>
						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script>
       
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
     
     function doHand(){
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
        	url:"${ctx}/vedioadver/adverVedio/doBatchHand",
        	"ids":ids,
        	success:function(data){
        	}
        	
        });
    } 
        </script>  
</body>
</html>