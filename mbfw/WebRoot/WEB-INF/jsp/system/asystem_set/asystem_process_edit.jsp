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

<script type="text/javascript">
	function tijiao() {
		alert("设置成功");
	}
</script>

<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索ghfh  -->
					<form action="user/listUsers.do" method="post" name="userForm"
						id="userForm">
						<!-- wizard -->
						<div class="col-xs-4">
							<!-- PAGE CONTENT BEGINS -->
							<h4 class="lighter">
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a data-toggle="modal" class="blue">流程名称:</a>
							</h4>
							<input type="text" id="inputInfo" class="width-40"
								placeholder="请输入流程名称" value="电子设备采购" />
						</div>
						<div class="col-xs-4">
							<h4 class="lighter">
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a data-toggle="modal" class="blue">流程类型： </a>
							</h4>
							<select id="form-field-select-1" style="display: none:">
								<option value="审批流程">审批流程</option>
								<option value="工作流程">工作流程</option>
							</select>
						</div>

						<hr />
						<!-- wizard -->

						<table id="table_report"
							class="text-table table table-striped table-bordered table-hover">
							<!-- 开始循环 -->
							<thead>
								<tr>
									<th>步骤序号</th>
									<th>步骤名称</th>
									<th>审批负责人</th>
									<th>可回退</th>
									<th>可终止</th>
									<th>可提前结束</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<!--<td style = "text-align:center;" colspan="2"><label>步骤一</label></td> -->
									<td>步骤一</td>
									<td><input type="text" id="inputInfo" class="width-40"
										placeholder="请输入步骤名称" value="提交申请" /></td>
									<td><select id="form-field-select-1"
										style="display: none:">
											<option value="负责人审核">小红</option>
											<option value="部门领导审核">小李</option>
											<option value="财务部审核">小王</option>
									</select></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
								</tr>

								<tr>
									<td>步骤二</td>
									<td><input type="text" id="inputInfo" class="width-40"
										placeholder="请输入步骤名称" value="部门审批" /></td>
									<td><select id="form-field-select-1"
										style="display: none:”">
											<option value="负责人审核">小红</option>
											<option selected="selected" value="部门领导审核">小李</option>
											<option value="财务部审核">小王</option>
									</select></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
								</tr>

								<tr>
									<td>步骤三</td>
									<td><input type="text" id="inputInfo" class="width-40"
										placeholder="请输入步骤名称" value="领导审批" /></td>
									<td><select id="form-field-select-1"
										style="display: none:”">
											<option value="负责人审核">小红</option>
											<option value="部门领导审核">小李</option>
											<option selected="selected" value="财务部审核">小王</option>
									</select></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
									<td><label class="col-sm-0" style="text-align: center"><input
											id="id-button-borders" checked="checked" type="checkbox"
											class="ace ace-switch ace-switch-5"> <span
											class="lbl middle"></span> </label></td>
								</tr>


							</tbody>
						</table>
						<!-- <button class="btn btn-info" type="button"
											onClick="javascript:window.location.href='http://localhost:8080/mbfw/process/manage.do';">
											<i class="ace-icon fa fa-check bigger-110"></i> 提交
										</button> -->



						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tbody>
									<tr>
										<td>
											<button class="btn btn-success" type="button"
												onClick="tianjia()">
												<i class="ace-icon fa fa-check bigger-110"></i> 增加步骤
											</button>
											<button class="btn btn-info" type="button"
												onClick="javascript:window.location.href='http://localhost:8080/mbfw/process/manage.do';">
												<i class="ace-icon fa fa-check bigger-110"></i> 提交设置
											</button>
										</td>

										<td></td>
									</tr>
								</tbody>
							</table>
						</div>

						<!-- PAGE CONTENT ENDS HERE -->
				</div>
				<!--/row-->

			</div>
			<!--/#page-content-->
		</div>
		<!--/.fluid-container#main-container-->

		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>

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
		<!-- 下拉框 -->
		<script type="text/javascript"
			src="static/js/bootstrap-datepicker.min.js"></script>
		<!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script>
		<!-- 确认窗口 -->
		<!-- 引入 -->
		<!--  wizard-->
		<link rel="stylesheet" href="static/css/wizard_ace.min.css" />


		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--提示框-->
		<script type="text/javascript">
			$(top.hangge());
		</script>
</body>
</html>

