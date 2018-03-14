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
<!-- js函数 -->
<script type="text/javascript">
	//点击取消按钮
	function project_apply_cancal() {
		window.location.href='asset/atp_showForm.do';
	}

</script>
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%>
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	
	<style type="text/css">
		.project_apply_add td{
			vertical-align: middle;	
		}
	</style>
	<script type="text/javascript">
		function post(){
			
			
		}
	</script>
</head>
<body>
	 <h1 class="center"><small> <i class="ace-icon fa fa-angle-double-right"></i> 请查看项目详细信息后填写审批意见 </small></h1>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">

			<div class="row-fluid">
			
				<div class="row-fluid">	
				<!-- enctype="multipart/form-data" -->
						<table id="table_report"
							class=" table-text table table-striped table-bordered table-hover">
							<thead class="project_apply_add">
								<!-- 开始循环 -->
								<tr>
									<th>项目名称</th>
									<td><input type="text" id="project_apply_name" name="project_apply_name"
										placeholder="项目名称" class="col-xs-10 col-sm-5" disabled="disabled"></td>
										
									<th>申请部门</th>
									<td><input type="text" id="project_apply_name" name="project_apply_name"
										placeholder="申请部门" class="col-xs-10 col-sm-5" disabled="disabled"></td>
									
									<th>申请时间</th>
									<td><input type="date" id="project_apply_time" name="project_apply_time"
										class="form-control hasDatepicker" disabled="disabled"></td>
									
								</tr>
								<tr>
									<th>申请人</th>
									<td><input type="text" id="project_applicant" name="project_apply_person" placeholder="申请人"
										class="col-xs-10 col-sm-5" disabled="disabled"></td>

									<th>设备名称</th>
									<td><input type="text" id="device_name" name="project_device_name" 
										placeholder="设备名称" class="col-xs-10 col-sm-5" disabled="disabled"></td>
									<th>型号</th>
									<td><input type="text" id="device_model" name="project_device_model" placeholder="型号"
										class="col-xs-10 col-sm-5" disabled="disabled"></td>
								</tr>
								<tr>
									<th>市场价格(元)</th>
									<td><input type="text" id="market_price" name="project_device_price"
										placeholder="市场价格" class="col-xs-10 col-sm-5" disabled="disabled"></td>
									
									<th>数量(个)</th>
									<td><input type="text" id="device_number" name="project_device_number" placeholder="数量"
										class="col-xs-10 col-sm-5" disabled="disabled"></td>

									<th>用途</th>
									<td><input type="text" id="apply_purpose" name="project_device_purpose" placeholder="用途"
										class="col-xs-10 col-sm-5" disabled="disabled"></td>


								</tr>
								
								<tr>
									<th>申请原因</th>
									<td colspan="5"><textarea id="apply_reason" name="project_apply_reason" rows="4" cols="50" placeholder="请输入原因" style="width: 98%" disabled="disabled"></textarea></td>
								</tr>
								
								<tr>
								
									<th>预计使用年限(年)</th>
									<td><input type="text" id="durable_years" name="project_device_use_years"
										placeholder="预计使用年限" class="col-xs-10 col-sm-5" disabled="disabled"></td>
									
									<th>下载申请报告</th>
									<td style="text-align: center; vertical-align: middle;">
									<button
											class="btn btn-info" onclick="return post();">
											<i class="ace-icon fa fa-check bigger-110"></i> 点击下载
									</button></td>
   									
										
									<th>审批意见</th>
									<td colspan="5"><textarea id="project_apply_opinion" placeholder="提交审批意见后无法修改！"
											name="project_apply_opinion"
											class="autosize-transition form-control"
											style="overflow: hidden; word-wrap: break-word; resize: horizontal;"></textarea></td>
								</tr>

								
								
								<tr>
									<td style="text-align: center;" colspan="6">
										<button
											class="btn btn-info" onclick="return post();">
											<i class="ace-icon fa fa-check bigger-110"></i> 提交
										</button>
										<button class="btn btn-info" type="button" onClick="project_apply_cancal()">
											<i class="ace-icon fa fa-check bigger-110"></i> 取消
										</button>

								</tr>

							</thead>
						</table>
				</div>
				<!--/row-->

			</div>
			<!--/#page-content-->
		</div>
		<!--/.fluid-container#main-container-->
		</div>

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
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--提示框-->
		<script type="text/javascript">
	$(top.hangge());
	</script>
</body>
</html>

