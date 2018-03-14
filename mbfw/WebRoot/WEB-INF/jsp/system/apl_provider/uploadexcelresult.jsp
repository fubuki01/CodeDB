<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存结果</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@ include file="../admin/top.jsp"%> 

</head>
<body>
	<div id="zhongxin"></div>
	<script type="text/javascript">
		var msg = '${msg}';
		//alert(msg);
		if(msg=="success" || msg==""){
			document.getElementById('zhongxin').style.display = 'none';	
			alert("导入数据成功！");
			top.Dialog.close();
		}
		
		else if(msg=="error_provider"){
			alert("产品名称，产品类别，计量单位不能为空，请确认！");
			top.Dialog.close();
		}else if(msg=="error"){
			alert("上传的Excl存在不符合格式要求的条目，请确认！");
			top.Dialog.close();
		}else if(msg=="nodata"){
			alert("上传的Excl为空，请确认！");
			top.Dialog.close();
		}
		top.Dialog.close();
	</script>
</body>
</html>