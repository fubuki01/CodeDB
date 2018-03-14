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
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>

<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
</head>

	<!-- ${requestScope.department.DEPARTMENT_NAME} -->

<body>

			<div class="container-fluid" id="main-container">

				<table style="width:100%;" border="0">
					<tr>
						<td style="width:15%;" valign="top" bgcolor="#F9F9F9">
							<div style="width:15%;">
								<ul id="leftTree" class="ztree"></ul>
							</div>
						</td>
						<td style="width:85%;" valign="top">
							<!-- 右边设置节点列表 -->
							<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/department/table.do" style="margin:0 auto;width:100%;height:100%;"></iframe>
						</td>
					</tr>
				</table>

			</div>
			<!--/.fluid-container#main-container-->

			<!-- 引入 -->
			<script type="text/javascript">
				window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
			</script>
			<script src="static/js/bootstrap.min.js"></script>
			<script src="static/js/ace-elements.min.js"></script>
			<!-- 引入 -->
			<script type="text/javascript">
				$(top.hangge());
			</script>
			<SCRIPT type="text/javascript">
				<!--
				var setting = {
					view: {
						showIcon: showIconForTree
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};

				var zNodes = [{
						id: 1,
						pId: 0,
						name: "xx股份有限公司",
						open: true
					},
					{
						id: 11,
						pId: 1,
						name: "上海_信息部"
					},
					{
						id: 12,
						pId: 1,
						name: "上海_销售部",
						/*url: "#",
						target: "treeFrame"*/
					},
					{
						id: 13,
						pId: 1,
						name: "长沙_销售部",
						isParent: true
					},
					{
						id: 131,
						pId: 13,
						name: "岳麓区_销售部"
					},
					/* 	{id:33,pId:0,name:"父节点3直接说",url="http://www.baidu.com/",target:"treeFrame"} */
				];

				function showIconForTree(treeId, treeNode) {
					return !treeNode.isParent;
				};

				$(document).ready(function() {
					$.fn.zTree.init($("#leftTree"), setting, zNodes);
				});
				//-->

				function treeFrameT() {
					var hmainT = document.getElementById("treeFrame");
					var bheightT = document.documentElement.clientHeight;
					hmainT.style.width = '100%';
					hmainT.style.height = (bheightT - 7) + 'px';
				}
				treeFrameT();
				window.onresize = function() {
					treeFrameT();
				};
			</SCRIPT>
		</body>

</body>
</html>