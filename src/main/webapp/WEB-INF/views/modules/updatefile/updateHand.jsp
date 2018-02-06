<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>终端升级</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/updatefile/updateFileInfo/preHand");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/updatefile/updateFileInfo/">返回升级记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="updateFileInfo" action="${ctx}/updatefile/updateFileInfo/preHand" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>终端编号：</label><form:input path="term.teNo" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>文件版本号：</label><form:input path="uiFileVersion" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
				<div class="panel-body">  
                    <div class="list-op" id="list_op">  
                        <button type="button" class="btn btn-default btn-sm" onclick="doHand()">  
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>升级  
                        </button>  
                    </div>  
                </div> 
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input id="allc"  type="checkbox">全选</th>
				<th>终端编号</th>
				<th>升级文件路径</th>
				<th>升级文件版本号</th>
				<th>升级状态</th>
				<shiro:hasPermission name="updatefile:updateFileInfo:edit">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="tb">
		<c:forEach items="${page.list}" var="updateInfo">
			<tr>
				<td><input type="checkbox" value="${updateInfo.id}"></td>
				<td>${updateInfo.term.teNo }</td>
				<td>${updateInfo.uiFilePath }</td>
				<td>${updateInfo.uiFileVersion }</td>
				<td>
					<c:if test="${updateInfo.empty1==0 }">未升级</c:if>
					<c:if test="${updateInfo.empty1==1 }">已升级</c:if>
				</td>
				<td>
					<shiro:hasPermission name="updatefile:updateFileInfo:view">
					<a href="${ctx}/updatefile/updateFileInfo/viewOne?teNo=${updateInfo.term.teNo}&veId=${updateInfo.id}">查看&nbsp;&nbsp;</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="updatefile:updateFileInfo:edit">
    				<a href="${ctx}/updatefile/updateFileInfo/doHand?teNo=${updateInfo.term.teNo}&veId=${updateInfo.id}">升级</a>
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
        	url:"${ctx}/",
        	"ids":ids,
        	success:function(data){
        	}
        	
        });
    } 
        </script>  
</body>
</html>