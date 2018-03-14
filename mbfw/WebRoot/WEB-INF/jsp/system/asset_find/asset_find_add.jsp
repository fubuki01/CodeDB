<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%>



</style>
<style type="text/css">
 table th{
            white-space: nowrap;
        }
 table td{
            white-space: nowrap;
        }
table{
         empty-cells:show; 
         margin:20 auto;
        }
</style>
<script type="text/javascript" src="<%=basePath%>/Lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=basePath%>/Lodop/CheckActivX.js"></script>
<script type="text/javascript" src='jquery-1.9.1.min.js'></script>
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0></object>
</head>	
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	
</script>
<body>
	
	
<div class="container-fluid" id="main-container">

		
			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索ghfh  -->
					
	<form action="" name="RARForm" id="RARForm"
		method="post">
		<input type="hidden" name="id" id="user_id"
			value="${sd.id }" />
	
		<div id="zhongxin">
		
						<div style="overflow-x:auto;width: 100%;height: 100%;">
						
						<table id="table_report" class="text-table table table-striped table-bordered table-hover">
							<tbody>
									<!-- 开始循环 -->
						<td colspan="2"><iframe src="${pageContext.request.contextPath}/system/asset_find/asset_look.jsp'"></iframe></td>
						<tr>	
							<td style="text-align: center;" colspan="10">
							<button class="btn btn-mini btn-info" onclick="design();">二维码打印</button>
						    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">返回</a>
						</td>
			                </tr>
							</tbody>
								
						</table>
						</div>
						<!-- PAGE CONTENT ENDS HERE -->
				</div>
				<!--/row-->
		</form>
			</div>
			<!--/#page-content-->
		</div>
</div>

						<!-- PAGE CONTENT ENDS HERE -->
			
				<!--/row-->


	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script src="static/js/qrcode.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript"
	src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript">
	  var qrcode = new QRCode(document.getElementById("qrcode"), {
	        width : 52,//设置宽高
	        height : 45
	    });
// 	    document.getElementById("send").onclick =function(){
// 	        qrcode.makeCode(document.getElementById("getval").value);
// 	    };

	    qrcode.makeCode($("#asset_code").val());
	
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();
			
			 $("#img1").click(function(){
                 var width = $(this).width();
                 if(width==230)
                 {
                     $(this).width(500);
                     $(this).height(400);
                 }
                 else
                 {
                     $(this).width(230);
                     $(this).height(138);
                 }
             });

		});
		
	</script>

</body>
</html>