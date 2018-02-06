<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改图文广告</title>
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
		<li><a href="${ctx}/adver/adver/">返回图文广告列表</a></li>
	</ul><br/>

	<form id="inputForm"  action="${ctx}/adver/adver/update" method="post" class="form-horizontal" enctype="multipart/form-data">
		
		<input type="hidden" name="id" value="${adver.id }" />
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<input type="text" name="adTitle" value="${adver.adTitle }" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">广告内容：</label>
			<div class="controls">
				<input type="text" name="adContent" value="${adver.adContent }" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告图片：</label>
			<div class="controls">
				<h5>点击图片更换广告图片</h5>
					<div id="div1">
						<img src="${ctx}/adver/adver/show?name=${adver.adImage }" id="avatar" onclick="getPicture()" 
						style="width:140px;height:140px;"/>
					</div>
        			<input type="file" name="file" onchange="setImage(this,div1,avatar);" id="photo" style="display: none"/>
			</div>
		</div>
		
		<input type="hidden" name="midId" value="${infoTerm.id }" />
		
		<div class="control-group">
			<label class="control-label">播放时间：</label>
			<div class="controls">
				<select name="adLiveTime" >
					<option value="5" selected="selected"/>请选择播放时间，默认5秒钟
					<option value="5" />5秒
					<option value="10"/>10秒
					<option value="15"/>15秒
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<input type="hidden" name="user.id" value="${adver.user.id}">
		
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<select name="term.teNo" >
					<c:forEach items="${termList }" var="term">
					<c:if test="${term.teNo==adver.term.teNo}">
						<option value="${adver.term.teNo }" selected="selected" />${adver.term.teNo }
					</c:if>
					<c:if test="${term.teNo!=adver.term.teNo}">
						<option value="${term.teNo}" />${term.teNo}
					</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="adver:adver:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
	<script >

function getPicture(){
    $("#photo").click();
    }

function setImage(docObj, localImagId, imgObjPreview) {
    var f = $(docObj).val();
    f = f.toLowerCase();
    var strRegex = ".bmp|jpg|jpeg$";
    var re=new RegExp(strRegex);
    if (re.test(f.toLowerCase())){
         //return true;
    }
    else{
        alert("请选择正确格式的图片");
         file = $("#photo");
         file.after(file.clone());
         file.remove();
        return false;
    }
    if (docObj.files && docObj.files[0]) {
        //火狐下，直接设img属性
        //imgObjPreview.style.display = 'block';
        //imgObjPreview.style.width = '140px';
        //imgObjPreview.style.height = '187px';
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

    } else {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        //必须设置初始大小
        //localImagId.style.width = "140px";
        //localImagId.style.height = "187px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        try {
            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        } catch(e) {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}
</script>
</body>
</html>
