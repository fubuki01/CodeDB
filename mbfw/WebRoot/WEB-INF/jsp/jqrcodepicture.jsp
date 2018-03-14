<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="<%=basePath%>static/newjs/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/newjs/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>static/newjs/jquery.browser.js"></script>
<script type="text/javascript" src="<%=basePath%>static/newjs/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/newjs/jquery.jokeer.js"></script>
<style type="text/css">
#funll {
	background-repeat: no-repeat;
	background-repeat: repeat-x;
	background-repeat: repeat-y;
	background-attachment: fixed;
	background-position: center;
	background-position: center center;
	width: 100%;
	heght: 100%;
}
</style>
</head>
<body>
<iframe src="<%=basePath%>/jsp/error.jsp" width="400" height="200"></iframe>
<input type="text" id="shu">
<button onclick="printQr();">生成二维码并打印</button>
<div id="printcontent" >
	<div>
	  <table>
	  	<tr>
	  		<td>
	  		  <img src="<%=basePath%>/Lodop/banklogo.png" width="5px;" height="5px;">
	  		</td>
	  		<!-- <td>
	  		  <p>湖南慈利农村商业银行</p>
	  		  HUNAN CILI RURAL COMMERCIAL BANK
	  		</td> -->
	  	</tr>
	  	<!-- <tr>
		  	<td>资产名称：</td>
		  	<td>111111</td>
	  	</tr>
	  	<tr>
		  	<td>资产类型：</td>
		  	<td>222222</td>
	  	</tr> -->
	  </table>
	  <%-- <div><p><img src="<%=basePath%>/Lodop/banklogo.png" width="30px;" height="30px;">湖南慈利农村商业银行<br>HUNAN CILI RURAL COMMERCIAL BANK</p></div> --%>	  
	  <!-- <div id="output" style="width: 5px; height: 5px;" style="display: none;"></div>
	  <img src="" id="myImg"  style="width: 5px; height: 5px;"> -->
	</div>	
	<div id="output" style="width: 5px; height: 5px;" style="display: none;"></div>
	<img src="" id="myImg"  style="width: 5px; height: 5px;">
</div>
<!-- <div id="output" style="width: 200px; height: 200px; padding: 100px; float: left;"></div>
<img src="" id="myImg" alt="图片" style="height: 100px; width: 100px; padding-top: 10px">
<div id="printContent">  -->
                 
<script type="text/javascript">
function printQr() {
	var qrCode = $('#shu').val();
	if (qrCode != null && qrCode != '') {
		$('#output').empty();
		$('#output').qrcode({
			width : 50,
			height : 50,
			correctLevel : 0,
			text : utf16to8(qrCode)
		});
		$('#output').css('display', 'block');
		$("canvas").attr("id", "erw");
		var canvas = document.getElementById('erw');
		var image = new Image();
		var strDataURI = canvas.toDataURL("image/png");
		document.getElementById('myImg').src = strDataURI; 
		if (!!window.ActiveXObject || "ActiveXObject" in window) {
			$('#myImg').css("width",'5px');
			$('#myImg').css("height",'5px');
 			$('#output').hide();
			$('#myImg').show();
			//$("#myImg").jqprint();
		} 
		$('#printcontent').jqprint();
		/* setTimeout('aa()', 1000);
		setTimeout('my()', 2000);
		setTimeout('haha()', 7500); */ 
	} else {
		alert('输入您准备生成二维码的数据');
	}
}
//中文处理，防止出现乱码
function utf16to8(str) {
	var out, i, len, c;
	out = "";
	len = str.length;
	for (i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
			out += str.charAt(i);
		} else if (c > 0x07FF) {
			out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
			out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		} else {
			out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		}
	}
	return out;
}

</script>
</body>
</html>