<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网格员注册</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
      select{
      	width:200px;
      	height:30px;
      }
    </style>
	
</head>
<body>
	<h1 class="form-signin-heading">网格员注册界面</h1>
	
	<form id="inputForm" role="form"  action="${ctx}/doSave" method="post" class="form-horizontal" enctype="multipart/form-data">
		
	
		<div class="control-group">
			<div class="content">
			<label class="form-inline">用户照片:</label>
				<input type="file" name="file" class="required" style="width:220px;height:32px" >
				<span class="form-inline"><font color="red">*</font> </span>
			</div>
		</div>
	
		<div class="control-group">
			<div class="content">
				<label class="form-inline">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
				<input type="text" name="no" class="required">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<div class="content">
				<label class="form-inline">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
				<input type="text" name="name" id="name" class="required" onblur="conName()">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<div class="content">
				<label class="form-inline">登&nbsp;&nbsp;录&nbsp;&nbsp;名:</label>
				<input type="text" name="loginName" id="user_name" class="required" onblur="confirmName()">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<div class="content">
			<label class="form-inline">身份证号:</label>
				<input type="text" name="phone" class="required" id="idCard" onblur="conIdCard()">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<div class="content">
				<label class="form-inline">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机:</label>
				<input type="text" class="required" id="phone" name="mobile" onblur="conPhone()">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<div class="content">
				<label class="form-inline">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<div class="content">
			<label class="form-inline">确认密码:</label>
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<div class="content">
				<label for="email" class=form-inline>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
				<input type="text"  name="email" id="email" onblur="conEmail()">
				<span class="help-inline"><font color="red">&nbsp;</font> </span>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<div class="content">
			<label class="form-inline">用户角色:</label>
				<select name="role" style="width:220px;height:32px">
					<c:forEach items="${allRoles }" var="role">
						<option value="${role.name }" >${role.name }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
		<div class="content">
		<label class="form-inline">协&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;议:</label>
   		<textarea name="request" style="width:220px" rows="5" readonly="readonly">网格员信息必须真实、准确，并且同意国家电网的使用协议。</textarea>
		<div class="content">
	    	<input name="confirm"  type="checkbox" onclick="agree();" id="cb"/>我认真阅读并接受以上协议。
		</div>
		</div>
		</div>
		
		
		
		
		
		<div class="content">
			<input id="tj" class="btn btn-primary" type="submit" value="提交" disabled="disabled"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
	
	
<script type="text/javascript">
function agree(){
		
       			if(document.getElementById('cb').checked)
              			document.getElementById('tj').disabled=false;
   				else
        				document.getElementById('tj').disabled='disabled';  
 		 }   
 		
 		function confirmName(){
  				var name=document.getElementById("user_name");
     			reg=/^[\u4e00-\u9fa5_a-zA-Z0-9-]{4,16}$/g;
      			if(!reg.test(name.value)){
       		 	alert("对不起，输入的用户名限4-16个字符，支持中英文、数字、减号或下划线且不能为空 ");
       		 	return false;
      				} 
				}
				
		function conPhone(){
  				var name=document.getElementById("phone");
     			reg=/^0?1[3|4|5|7|8][0 -9]\d{8}$/;  
      			if(!reg.test(name.value)){
       		 	alert("对不起，输入的手机号不符合规则 ");
       		 	return false;
      				} 
				}		
				
		function conName(){
  				var name=document.getElementById("name");
     			reg=/^([\u4e00-\u9fa5]){2,7}$/;  
      			if(!reg.test(name.value)){
       		 	alert("对不起，输入的姓名长度限2-7个汉字且不能为空 ");
       		 	return false;
      				} 
				}	
				
		function conEmail(){
  				var name=document.getElementById("email");
     			reg=/^([a-z0 -9_\. -]+)@([\da -z\. -]+)\.([a -z\.]{2,6})$/;  
      			if(!reg.test(name.value)){
       		 	alert("对不起，输入的邮箱不符合规则 ");
       		 	return false;
      				} 
				}
				
		function conIdCard(){
  				var name=document.getElementById("idCard");
     			reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
      			if(!reg.test(name.value)){
       		 	alert("对不起，输入的身份证号不符合规则 ");
       		 	return false;
      				} 
				}

</script >	
	
</body>
</html>
<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
				var loginName=document.getElementsByName("loginName");
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkName?loginName=loginName"}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
