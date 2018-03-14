<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<script type="text/javascript">
		function tijiao()
		{
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
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>

								<!-- 资产种类 -->
								<td style="vertical-align: top;"><select
									class="chzn-select" name="ASSERT_DATA__ID" id="assert_data_id"
									data-placeholder="资产种类"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<%-- <c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach> --%>
										<option value="">资产种类A</option>
										<option value="">资产种类S</option>
										<option value="">资产种类D</option>
								</select></td>

								<!-- 资产使用状态 -->
								<td style="vertical-align: top;"><select
									class="chzn-select" name="ASSERT_USE_ID" id="assert_use_id"
									data-placeholder="资产使用状态"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<%-- <c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach> --%>
										<option value="">资产使用状态A</option>
										<option value="">资产使用状态S</option>
										<option value="">资产使用状态D</option>
								</select></td>

								<!-- 资产运行状态 -->
								<td style="vertical-align: top;"><select
									class="chzn-select" name="ASSERT_RUN_ID" id="assert_run_id"
									data-placeholder="资产运行状态"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<%-- <c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach> --%>
										<option value="">资产运行状态A</option>
										<option value="">资产运行状态S</option>
										<option value="">资产运行状态D</option>
								</select></td>


								<!-- 使用部门 -->
								<td style="vertical-align: top;"><select
									class="chzn-select" name="ASSERT_DATA_USE_DEPT__ID"
									id="assert_data_use_dept_id" data-placeholder="使用部门"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<%-- <c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach> --%>
										<option value="">使用部门A</option>
										<option value="">使用部门S</option>
										<option value="">使用部门D</option>
								</select></td>

								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>

								<%-- <c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="window.location.href='<%=basePath%>/user/listtabUsers.do';" title="切换模式"><i id="nav-search-icon" class="icon-exchange"></i></a></td>
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					<c:if test="${QX.edit == 1 }"><td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td></c:if>
					</c:if> --%>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report"
							class="text-table table table-striped table-bordered table-hover">
							<tbody>
								<!-- 开始循环 -->
								<tr>
									<td><label>选择资产所属类别</label></td>
									<td><select id="form-field-select-1" style=“width：200px;display:none:”>
                <option value=""></option>
                <option selected="selected" value="电子设备">电子设备</option>
                <option value="办公用品">办公用品</option>
                <option value="一次性用品">一次性用品</option>
            </select></td>
									<td><label>选择上级</label></td>
									<td><select id="form-field-select-1" style=“width：200px;display:none:”>
                <option value=""></option>
                <option selected="selected" value="固定资产">固定资产</option>
                <option value="地址耐用品">地址耐用品</option>
                <option value="快速消耗品">快速消耗品</option>
            </select></td>
									<td><label>选择流程</label></td>
									<td><select id="form-field-select-1" style=“width：200px;display:none:”>
                <option value=""></option>
                <option selected="selected" value="电子设备流程A">电子设备流程A</option>
                <option value="电子设备流程B">电子设备流程B</option>
                <option value="电子设备流程C">电子设备流程C</option>
                <option value="电子设备流程D">电子设备流程D</option>
            </select></td>
								<tr>
									<td colspan="6"> <button class="btn btn-info" type="button" onClick="javascript:window.location.href='http://localhost:8080/mbfw/asset/setPoint.do';"> <i class="ace-icon fa fa-check bigger-110"></i> 提交 </button></td>
								</tr>

							</tbody>
						</table>
						

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
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
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

