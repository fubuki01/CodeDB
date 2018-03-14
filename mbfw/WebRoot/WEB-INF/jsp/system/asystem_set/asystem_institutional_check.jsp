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
<title>查看机构信息</title>
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
						style="color: red"><h2>查看机构信息</h2></b></label></td>
			</tr>

			<tr>
				<td><label>上级组织名称</label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="superior_organization_name" id="superior_organization_name"
					data-placeholder="请选上级组织" disabled="disabled"
					style="width: 221px; vertical-align: center">
						<option></option>
				</select></td>

				<td><label>组织名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="organization_name"
					id="organization_name" disabled="disabled"
					value="${pd.organization_name }" placeholder="组织名称"></td>

				<td><label>组织简称<b style="color: red">*</b></label></td>
				<td><input type="text" name="organization_abbreviation"
					id="organization_abbreviation" disabled="disabled"
					value="${pd.organization_abbreviation }" placeholder="组织简称"></td>

			</tr>

			<tr>
				<td><label>组织类别<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="organization_class" id="organization_class"
					data-placeholder="请选择组织类别" disabled="disabled"
					style="width: 221px; vertical-align: center">
						<option></option>
						<option>联社</option>
						<option>部门</option>
						<option>支行/社</option>
						<option>网点/社</option>
				</select></td>

				<td><label>组织级别<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="organizational_level" id="organizational_level"
					data-placeholder="请选择组织级别" disabled="disabled"
					style="width: 221px; vertical-align: center">
						<option></option>
						<option>农商行/县联社</option>
						<option>支行/社/部门</option>
						<option>网点/分社</option>
				</select></td>

				<td><label>业务机构类型</label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="business_organization_type" id="business_organization_type"
					data-placeholder="请选择业务机构类型" disabled="disabled"
					style="width: 221px; vertical-align: center">
						<option></option>
						<option>综合制</option>
				</select></td>
			</tr>

			<tr>
				<td><label>业务机构代码</label></td>
				<td><input type="text" name="business_organization_code"
					id="business_organization_code" disabled="disabled"
					value="${pd.business_organization_code }" placeholder="数字长度不能大于11"></td>


				<td><label>业务机构性质</label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="business_organization_nature"
					id="business_organization_nature" data-placeholder="请选业务机构性质"
					disabled="disabled" style="width: 221px; vertical-align: center">
						<option></option>
						<option>有贷款城区支行</option>
						<option>有贷款农村支行</option>
						<option>无贷款支行</option>
				</select></td>

				<td><label>业务机构标识<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="business_organization_identifier"
					id="business_organization_identifier" data-placeholder="请选择业务机构标识"
					disabled="disabled" style="width: 221px; vertical-align: center">
						<option></option>
						<option>是</option>
						<option>否</option>
				</select></td>

			</tr>

			<tr>

				<td><label>启用标识<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
					name="enable_identification" id="enable_identification"
					data-placeholder="是否启用标识" disabled="disabled"
					style="width: 221px; vertical-align: center">
						<option></option>
						<option>是</option>
						<option>否</option>
				</select></td>

				<td><label>所在区域</label></td>
				<td colspan="3"><textarea rows="2" cols="" name="location"
						disabled="disabled" id="location" style="width: 90%"> </textarea></td>
			</tr>


			<tr style="height: 40">
				<td style="text-align: center;" colspan="6">
					<button class="btn btn-primary" onclick="rBack();">返回</button>
				</td>
			</tr>
			<!-- <tr>
					<th style="text-align: center;" colspan="6"><label>提示</label>
					<textarea rows="2" style="width: 40%;color: red;" disabled="disabled"  
					placeholder="">注意：加“*”的为必填项，无“*”的为可选项</textarea>
					</th>
				</tr> -->
		</table>
	</div>

	<div id="zhongxin2" class="center" style="display: none">
		<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
		<h4 class="lighter block green"></h4>
	</div>



	<script type="text/javascript">
		$(top.hangge());
		
		//上级组织简称
		var option = document.createElement("option");
	    option.innerHTML = '${pd.superior_organization_name }';
	    option.selected='selected';
	    $("#superior_organization_name").append(option); 
		//业务机构性质
		$("#business_organization_nature").val('${pd.business_organization_nature }');
		//业务机构标识
		$("#business_organization_identifier").val('${pd.business_organization_identifier }');
		//组织类别
		$("#organization_class").val('${pd.organization_class }');
		//组织级别
		$("#organizational_level").val('${pd.organizational_level }');
		//业务机构类型
		$("#business_organization_type").val('${pd.business_organization_type }');
		//启用标识
		$("#enable_identification").val('${pd.enable_identification }');
		//所在区域
		$("#location").val('${pd.location }');
		
		
		function rBack() {
			var key = '${pd.key }';
			window.location = '${pageContext.request.contextPath}/asset/asystem_institutionalinfoshow.do?key='+encodeURI(encodeURI(key));
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