<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>终端信息查看</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/term/term/">返回终端列表</a></li>
	</ul><br/>
	<form id="inputForm"  action="" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">终端编号：</label>
			<div class="controls">
				<input type="text" value="${term.teNo }" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sim卡号：</label>
			<div class="controls">
				<input type="text" value="${term.simNo }" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终端电话号码：</label>
			<div class="controls">
				<input type="text" value="${term.teTel }" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一级网格员姓名：</label>
			<div class="controls">
				<input type="text" value="${firstUser.name }" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">一级网格员电话：</label>
			<div class="controls">
				<input type="text" value="${firstUser.mobile }" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">一级网格员照片：</label>
			<div class="controls">
				<img src="${ctx}/adver/adver/show?name=${firstUser.photo}" style="width:140px;height:140px;"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">共产党员姓名：</label>
			<div class="controls">
				<input type="text" value="${prcUser.name }" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">共产党员电话：</label>
			<div class="controls">
				<input type="text" value="${prcUser.mobile }" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">共产党员照片：</label>
			<div class="controls">
				<img src="${ctx}/adver/adver/show?name=${prcUser.photo}" style="width:140px;height:140px;"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">二级网格员姓名：</label>
			<div class="controls">
				<input type="text" value="" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">二级网格员电话：</label>
			<div class="controls">
				<input type="text" value="" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">二级网格员照片：</label>
			<div class="controls">
				<img src="${ctx}/adver/adver/show/" style="width:140px;height:140px;"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端地址：</label>
			<div class="controls">
				<input type="text" value="${term.teAddress }" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">出厂时间：</label>
			<div class="controls">
				<input type="text" value="<fmt:formatDate value="${term.teProductTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			</div>
		</div>
		
		
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>