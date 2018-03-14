<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<%@ include file="../admin/top.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看审批详情</title>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>

<!-- confirm提示框 -->
<link rel="stylesheet" type="text/css"
	href="static/js/myconfirm/css/xcConfirm.css" />
<script src="static/js/myconfirm/js/jquery-1.9.1.js"
	type="text/javascript" charset="utf-8"></script>
<script src="static/js/myconfirm/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>



<!-- 内容居中显示 -->
<style type="text/css">
.clearfix table tr th {
	text-align: center;
}

.clearfix table tr td {
	text-align: center;
	vertical-align: middle;
	font-size: 20px;
	color: black;
}
</style>

</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">

				<!-- 查询列表显示区域 -->
				<table id="table_project_approval"
					class="table text-table able-striped table-bordered table-hover">

					<thead>
						<tr>
							<th>编号</th>
							<th>审批节点</th>
							<th>审批人</th>
							<th>意见</th>
							<th>备注</th>
						</tr>
					</thead>

					<tbody>
						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty checkApprovalProject}">
								<c:forEach items="${checkApprovalProject}" var="project" varStatus="vs">

									<tr>
										<td style="width: 30px;">${vs.index+1}</td>
										<td style="color: red;">${project.node_Name }</td>
										<td>${project.NAME }</td>
										<td>${project.detail_OptionResult }</td>
										<td><textarea rows="3" cols="" name="apply_reason" readonly="readonly"
							style="width: 95%; margin-top: 8px;font-size: 15px;color: black;">${project.detail_ApproverContent }</textarea></td>
									</tr>

								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="15">抱歉，还没有开始审批，无审批信息！</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<button class="btn btn-primary" onclick="rBanck();">返回</button>
			</div>

		</div>
	</div>

	<!-- JS代码区，写在后面是为了提高运行效率 -->
	<script type="text/javascript">
		$(top.hangge());
		
		function rBanck() {
			var key = '${key}';
			window.location = '${pageContext.request.contextPath}/asset/atp_showForm.do?key='+encodeURI(encodeURI(key));
		}
		
		
	</script>
</body>
</html>