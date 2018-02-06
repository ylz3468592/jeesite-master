<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 		
	<title>图文广告管理</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript">
		
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
		<li class="active"><a href="${ctx}/adver/adver/">图文广告列表</a></li>
		<shiro:hasPermission name="adver:adver:edit">
		<li><a href="${ctx}/adver/adver/form">添加图文广告</a></li>
		</shiro:hasPermission>
		<%-- <shiro:hasPermission name="adver:adver:edit">
		<li><a href="${ctx}/adver/adver/preHand">下发图文广告</a></li>
		</shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="adver" action="${ctx}/adver/adver/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>标题：</label><form:input path="adTitle" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>创建人：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>终端编号：</label><form:input path="term.teNo" htmlEscape="false" maxlength="50" class="input-medium"/></li>
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
				<th>标题</th>
				<th class="sort-column login_name">创建人</th>
				<th>终端编号</th>
				<th class="sort-column name">审核人</th>
				<th>审核时间</th>
				<th>广告状态</th>
				<shiro:hasPermission name="adver:adver:edit">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${page.list}" var="adver">
			<tr>
				<td><input type="checkbox" value="${adver.id}"></td>
				<td>${adver.adTitle}</td>
				<td>${adver.user.name}</td>
				<td>${adver.term.teNo}</td>
				<td>${adver.userCheck.name}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${adver.adCheckTime}" /></td>
				<td>
					<span >
					<c:if test="${adver.adStatus==0 }">未审核</c:if>
					<c:if test="${adver.adStatus==1 }">已审核</c:if>
					<c:if test="${adver.adStatus==2 }">已下发</c:if>
					<c:if test="${adver.adStatus==3 }">已删除</c:if>
					<c:if test="${adver.adStatus==4 }">审核未通过</c:if>
					</span>
				</td>
					<td>
						<c:if test="${adver.adStatus==1 }">
						<button><a href="${ctx}/adver/adver/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看</a></button>
						<button><a href="${ctx}/adver/adver/doHand?teNo=${adver.term.teNo}&adId=${adver.id}">下发</a></button>
						</c:if>
						<c:if test="${adver.adStatus==2 }">
						<button><a href="${ctx}/adver/adver/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看</a></button>
						</c:if>
						<c:if test="${adver.adStatus==0 }">
						<button ><a href="${ctx}/adver/adver/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看</a></button>
						<button id="hand+${adver.id }"><a href="${ctx}/adver/adver/preUpdate?teNo=${adver.term.teNo}&adId=${adver.id}">修改</a></button>
    					<button><a href="${ctx}/adver/adver/delete?teNo=${adver.term.teNo}&adId=${adver.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a></button>
						</c:if>
						<c:if test="${adver.adStatus==4 }">
						<button ><a href="${ctx}/adver/adver/viewOne?teNo=${adver.term.teNo}&adId=${adver.id}">查看</a></button>
						<button><a href="${ctx}/adver/adver/delete?teNo=${adver.term.teNo}&adId=${adver.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a></button>
						</c:if>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<a href="${ctx}/adver/adver/onServer">开启服务器</a><br/>
	<a href="${ctx}/adver/adver/stopServer">关闭服务器</a>
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
     
       function doDelete(){
        var ids=[];
        var adverObj={};
        var inputArr = $("#tb").find("input");
        for(var i = 0;i<inputArr.length;i++){
        	if(inputArr.eq(i).prop('checked')){
        	var obj = {};
        	obj.term={};
        	obj.id=inputArr.eq(i).val();
        	obj.term.teNo=inputArr.eq(i).parent().next().next().next().text();
        	ids.push(obj);
        	}
        }
        adverObj.list=ids;
        var str = JSON.stringify(adverObj);
        console.log(str);
        if(ids.length==0){
            alert("所选的记录为空，请重新选择");
            return false;
        }
        $.ajax({
        	url:"${ctx}/adver/adver/",
        	data:{list:str},
        	success:function(data){
        	}
        	
        });
        } 
               
    
        </script>  
    </body>  
</html>