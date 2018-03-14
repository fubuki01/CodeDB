<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../admin/top.jsp"%>
<!-- 引入 -->
<script type="text/javascript">
	window.jQuery
			|| document
					.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<script type="text/javascript"
	src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

</head>
<body>

	<div id="zhongxin" class="zhongxin"
		style="margin-left: 10px; margin-right: 10px; margin-top: 15px;">
		<table id="table_report"
			class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td style="text-align: center;" colspan="6"><label><b
						style="color: red"><h2>查看项目</h2></b></label></td>
			</tr>

			<tr>
				<td><label>项目名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="apply_name" id="apply_name"
					readonly="readonly" placeholder="请输入项目名称" value="${pd.apply_name }"></td>

				<td><label>设备名称<b style="color: red">*</b></label></td>
				<td><select class="chzn-select" name="device_name"
					id="device_name" disabled="disabled" data-placeholder="请选择设备名称"
					style="width: 221px; vertical-align: center">
				</select></td>

				<td><label>申请时间<b style="color: red">*</b></label></td>
				<td><input name="apply_time" id="apply_time"
					class="span10 date-picker" type="text" disabled="disabled"
					data-date-format="yyyy-mm-dd" value="${pd.apply_time }"
					style="width: 208px;" placeholder="申请日期" title="申请日期" /></td>


			</tr>

			<tr>
				<td><label>申请公司<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					disabled="disabled" name="apply_company" id="apply_company"
					data-placeholder="请选择申请公司"
					style="width: 221px; vertical-align: center">
				</select></td>

				<td><label>申请部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					disabled="disabled" name="apply_dept" id="apply_dept"
					data-placeholder="请选择申请部门"
					style="width: 221px; vertical-align: center">
				</select></td>

				<td><label>申请人<b style="color: red">*</b></label></td>
				<td><input type="text" name="apply_person" id="apply_person"
					readonly="readonly" value="${pd.apply_person }" placeholder="申请人"></td>
			</tr>

			<tr>


				<td><label>型号<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_model" id="device_model"
					readonly="readonly" value="${pd.device_model }" placeholder="型号"></td>

				<td><label>市场价格（元）<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_price" id="device_price"
					readonly="readonly" value="${pd.device_price }" placeholder="资产预单价"></td>

				<td><label>数量（个）<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_number" id="device_number"
					readonly="readonly" value="${pd.device_number }" placeholder="资产数量"></td>
			</tr>


			<tr>
				<td><label>设备用途<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_purpose"
					readonly="readonly" value="${pd.device_purpose }"
					id="device_purpose" placeholder="设备用途"></td>

				<td><label>预计使用年限(年)<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_use_years"
					readonly="readonly" value="${pd.device_use_years }"
					id="device_use_years" placeholder="预计使用年限"></td>

				<td><label>审批流程<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					disabled="disabled" name="apply_procedure" id="apply_procedure"
					data-placeholder="请选择审批流程"
					style="width: 221px; vertical-align: center">
				</select></td>

			</tr>


			<tr>
				<td><label>申请报告</label></td>
				<td style="vertical-align: middle;"><a
					href='javascript:void(0);'
					style="display: block; text-decoration: none; height: 15px; float: left;"show">${file_name }
				</a></td>


				<td><label>申请备注</label></td>
				<td colspan="3"><textarea rows="2" cols="" disabled="disabled"
						name="apply_remarks" id="apply_remarks" style="width: 91%"> </textarea></td>
			</tr>
			<tr>
				<td><label>申请原因</label></td>
				<td colspan="6"><textarea rows="2" cols="" name="apply_reason"
						disabled="disabled" id="apply_reason" style="width: 95%"> </textarea></td>
			<tr>

			</tr>
			<tr style="height: 40">
				<td style="text-align: center;" colspan="6">
					<!-- <a class="btn btn-mini btn-primary" onclick="top.Dialog.close();">返回</a> -->
					<button class="btn btn-primary" onclick="rBanck();">返回</button>
				</td>
			</tr>
		</table>
	</div>

	<div id="zhongxin2" class="center" style="display: none">
		<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
		<h4 class="lighter block green"></h4>
	</div>



	<script type="text/javascript">
		$(top.hangge());
		var key = '${pd.key }';
		//显示申请公司
		var option = document.createElement("option");
	    option.innerHTML = '${pd.apply_company }';
	    option.selected='selected';
	    $("#apply_company").append(option); 
		
		//显示申请部门
		var option = document.createElement("option");
	    option.innerHTML = '${pd.apply_dept }';
	    option.selected='selected';
	    $("#apply_dept").append(option); 
		
		
	    //显示设备名称
	    var str = "${pd.device_name }";
	    var device_name = str.split("@")[2]+"-"+str.split("@")[3];
	    var option = document.createElement("option");
	    option.innerHTML = device_name;
	    option.selected='selected';
	    $("#device_name").append(option); 
		
		//显示审批流程
		var option = document.createElement("option");
	    option.innerHTML = '${pd.apply_procedure }';
	    option.selected='selected';
	    $("#apply_procedure").append(option); 
		
		//显示申请原因
		$("#apply_reason").val('${pd.apply_reason }');
		
		//显示备注
		$("#apply_remarks").val('${pd.apply_remarks }');
		
		function rBanck(){
			window.location = '${pageContext.request.contextPath}/asset/atp_showForm.do?key='+encodeURI(encodeURI(key)); 
		}
		
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});

	</script>

</body>
</html>