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

<style type="text/css">

*{

margin: 0;

padding: 0;

}

 

#left {

float: left;

width: 220px;

background-color: green;

}

 

#content {

background-color: orange;

margin-left: 220px;/*==等于左边栏宽度==*/

}

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
<!-- <script type="text/javascript" src="../Lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="../Lodop/CheckActivX.js"></script> -->
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
	
	var LODOP; //打印对象声明为全局变量
	//设计二维码
	function design(){
		var assetname = $('#asset_name').val();
		var assetcode = $('#asset_code').val();
		var assetclass= $('#asset_class').val();
		if(confirm("确认打印二维码？")){
			LODOP=getLodop();  		
			LODOP.ADD_PRINT_IMAGE("5mm","15mm","10mm","10mm","<img border='0' src='<%=basePath%>/Lodop/banklogo.png'>"); 
			LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//可缩放模式
			LODOP.ADD_PRINT_TEXT("5mm","25mm","68mm","10mm","湖南慈利农村商业银行");
			LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
			LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
			LODOP.ADD_PRINT_TEXT("10mm","25mm","68mm","10mm","HUNAN CILI RURAL COMMERCIAL BANK");
			LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
			//显示相应的资产信息
			LODOP.ADD_PRINT_TEXT("18mm","20mm","28mm","5mm","资产名称:" + assetname);
			LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
			LODOP.ADD_PRINT_TEXT("25mm","20mm","38mm","5mm","资产类别:" + assetclass);
			LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
			//支持两种二维码的形式PDF417和QRCode，后面的内容就是扫描二维码显示的内容，所以需要显示什么可以在这里显示
			LODOP.ADD_PRINT_BARCODE("15mm","60mm","35mm","25mm","QRCode","资产编码:" + assetcode);
			LODOP.SET_PRINT_STYLEA(0,"GroundColor","#ffffff"); 
			LODOP.PREVIEW();
			//打印后自动关闭打印页面
			LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW",1);
		}		
	};
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
							
						<tr>
								<td style="width='*'"><label>资产名称</label></td>
									<td> <input type="text" id="asset_name" name="asset_name" value="${sd.asset_name }" readonly="readonly" class="col-xs-10 col-sm-5"></td>
								<td style="width='*'"> <label>资产编码</label></td>
									<td> <input type="text" id="asset_code" name="asset_code" value="${sd.asset_code}" readonly="readonly" class="col-xs-10 col-sm-5"></td>
								<td style="width=50px"><label>资产类别</label></td>
									<td> <input type="text" id="asset_class" name="asset_class" value="${sd.asset_class}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
								 
						</tr>
								
								
						<tr>
								<td><label>规格型号</label></td>
								<td> <input type="text" id="asset_standard_model" name="asset_standard_model" value="${sd.asset_standard_model}" readonly="readonly" class="col-xs-10 col-sm-5"></td>
								<td><label>SN号</label></td>
									<td> <input type="text" id="asset_sn" name="asset_sn" value="${sd.asset_sn}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>详细配置</label></td>
									<td> <input type="text" id="asset_detail_config" name="asset_detail_config" value="${sd.asset_detail_config}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									
						</tr>
						<tr>
									<td><label>计量单位</label></td>
									<td> <input type="text" id="asset_unit" name="asset_unit" value="${sd.asset_unit}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>价格</label></td>
									<td> <input type="text" id="asset_price" name="asset_price" value="${sd.asset_price}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>使用公司</label></td>
									<td> <input type="text" id="asset_use_company" name="asset_use_company" value="${sd.asset_use_company}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									
						</tr>
						<tr>
									<td><label>使用部门</label></td>
									<td> <input type="text" id="asset_use_dept" name="asset_use_dept" value="${sd.asset_use_dept}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>使用公司编码</label></td>
									<td> <input type="text" id="asset_usecompany_code" name="asset_usecompany_code" value="${sd.asset_usecompany_code}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>使用部门编码</label></td>
									<td> <input type="text" id="asset_usedept_code" name="asset_usedept_code" value="${sd.asset_usedept_code}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									
						</tr>
						<tr>
									<td><label>使用人</label></td>
									<td> <input type="text" id="asset_user" name="asset_user" value="${sd.asset_user}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>存放地点</label></td>
									<td> <input type="text" id="asset_storehouse" name="asset_storehouse" value="${sd.asset_storehouse}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>管理员</label></td>
									<td> <input type="text" id="asset_manager" name="asset_manager" value="${sd.asset_manager}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>		
									
									
						</tr>
						<tr>
									<td><label>购入时间</label></td>
									<td><input type="text" data-date-format="yyyy-mm-dd" id="asset_purchase_time" name="asset_purchase_time" disabled="disabled" value="${sd.asset_purchase_time}" class="span10 date-picker" style="width: 221px;"></td>
									<td><label>供应商</label></td>
									<td> <input type="text" id="asset_provider" name="asset_provider" value="${sd.asset_provider}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>使用期限</label></td>
									<td> <input type="text" id="asset_max_years" name="asset_max_years" value="${sd.asset_max_years}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
						</tr>
						<tr>
						<td><label>二维码</label></td>
									<td> <div id="qrcode" style="overflow:hidden"></div></td>
									<td><label>品牌</label></td>
									<td> <input type="text" id="asset_brand" name="asset_brand" value="${sd.asset_brand}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>资产状态</label></td>
									<td> <input type="text" id="asset_status" name="asset_status" value="${sd.asset_status}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
						</tr>
						<tr>
									
									<td><label>入库单号</label></td>
									<td> <input type="text" id="asset_into_bill" name="asset_into_bill" value="${sd.asset_into_bill}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>资产用途</label></td>
									<td> <input type="text" id="asset_use" name="asset_use" value="${sd.asset_use}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
									<td><label>备注</label></td>
									<td> <input type="text" id="asset_nod" name="asset_nod" value="${sd.asset_nod}"  readonly="readonly" class="col-xs-10 col-sm-5"></td>
						</tr>	
						<tr>
									<td><label>图片名称</label></td>
									<c:forTokens items="${sd.asset_image}" delims="#" var="proof">
									<td colspan="5" width="*" height="*"><img id="img1" src="/pic/asset_img/${proof}" 
									 style="width:230px;height:138px" alt=""/></td>
									</c:forTokens>
						</tr>	
														
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