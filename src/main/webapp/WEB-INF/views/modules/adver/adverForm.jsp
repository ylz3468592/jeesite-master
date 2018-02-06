<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加图文广告</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adver/adver/">图文广告列表</a></li>
		<li class="active"><a href="${ctx}/adver/adver/form?id=${adver.id}">图文广告<shiro:hasPermission name="adver:adver:edit">${not empty adver.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="adver:adver:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adver" action="${ctx}/adver/adver/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<form:input path="adTitle" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告内容：</label>
			<div class="controls">
				<form:input path="adContent" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告图片：</label>
			<div class="controls">
				<input type="file" name="file" class="input-xlarge required" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">播放时间：</label>
			<div class="controls">
				<select name="adLiveTime" style='width:220px;'>
					<option value="5" selected="selected"/>请选择播放时间
					<option value="5" />5秒
					<option value="10"/>10秒
					<option value="15"/>15秒
				</select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<input type="text" id="mod" name="ids" readonly="readonly" onclick="selectNo()">
				<%-- <c:forEach items="${termList }" var="term">
					<input type="checkbox" name="termList" value="${term.id }" class="input-xlarge required">${term.teNo }
				</c:forEach> --%>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="adver:adver:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div class="modal fade" id="findSCCJInfo" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">终端选择</h4>
				</div>
				<div class="modal-body" style="overflow: scroll; height: 400px">	
			<table class="table table-striped" id="findSCCJvalue" >
			
			<thead>
				<tr>
					<th><input id="allc"  type="checkbox" onclick=" selectAll(this)">全选</th>
					<th>终端编号</th>
					<th>地址</th>
					<th>电话</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>			
				</div>
			<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="IDQR()">确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<script type="text/javascript">
		
 
   function selectNo(){    
       
    	$.post("${ctx}/adver/adver/asd",{
    		},function(data){ 
    			 $("#findSCCJvalue tbody").html("");
    			 
    					for(var i in data){								    					   					    	    					    	
    					  	            var de = data[i]; 
    					    	$("#findSCCJvalue tbody").append("<tr onclick=\"xgtable2(this)\" ondblclick='xgtable22(this)'>"+
    					    			 "<td><input type='checkbox' id='sos' style='width:15px;height:15px;' /></td>"+
    									 "<td>"+de.teNo+"</td>"+
    									 "<td>"+de.teAddress+"</td>"+
    									 "<td>"+de.teTel+"</td>"+					 
    									 " </tr>"								 
    									 )
    					}	
    			},"json");  
    			$("#findSCCJInfo").modal({
    			show:true,backdrop:false});
             
   }   
    var id;
	function xgtable2(self){
		 $("#findSPMvalue tbody tr").css("backgroundColor","");
		 $(self).closest("tr").css("backgroundColor","#AAA");	
		 id =$(self).closest("tr").find("td").eq(1).html();		
	}
function IDQR(){
	
	 var inputArr = $("#findSCCJvalue").find("input");
        var ids="";
        for(var i = 1;i<inputArr.length;i++){
        	if(inputArr.eq(i)[0].checked){
		       		ids+=inputArr.eq(i).parent().next().text()+",";
        	}
        }
        if(ids.length>0){
        	ids=ids.substring(0,ids.length-1);
        }
     $("#mod").val(ids);
	 $("#findSCCJInfo").modal("hide");
	 $("input[type='checkbox']")[0].checked=false;
}
function xgtable22(self){
	 $("#mod").val(id);	
	 $("#findSCCJInfo").modal("hide");
}
//////
	
      
      function selectAll(obj) {
		if (obj.checked == true) {
			$("#findSCCJvalue input").prop("checked", true);
		} else if (obj.checked == false) {
			$("#findSCCJvalue input").prop("checked", false);
		}
	};
      
       
	</script>
</body>
</html>