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
		function duihua()
		{
			alert("设置成功，设置结果展示在左边！");
		}
		</script>

	<head>
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
	
			<!-- 检索  -->

			<form action="user/listUsers.do" method="post" name="userForm"
						id="userForm">
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
							</tr>
							</table>

				<table id="table_report"class="text-table table table-striped table-bordered table-hover">
							
				<thead>
					<tr>
						<th class="center">
							<label><input type="checkbox" id="zcheckbox"><span class="lbl"></span></label>
						</th>
						<th>资产编号</th>
						<th>资产名称</th>
						<th>创建时间</th>
						<th>所属类别</th>
						<th>上级</th>
						<th>创建人</th>
						<th>审批流程</th>					
						<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>

					<!-- 开始循环 -->

					<tr>
						<td class="center" style="width: 30px;">
							<label><input type="checkbox" name="ids" value="849e0eb85e4e46d28c4f63e57e830c04" id="759789581@qq.com" alt="15200228365"><span class="lbl"></span></label>

						</td>
						<td class="center" style="width: 30px;">1</td>
						<td>笔记本</td>
						<td>2016.6.08</td>
						<td>固定资产</td>
						<td>电子设备</td>
						<td>廖学文</td>

						<td>电子设备流程A</td>
						<td style="width: 60px;">
							<div class="hidden-phone visible-desktop btn-group">

								<a class="btn btn-mini btn-info" title="编辑" onClick="editUser('849e0eb85e4e46d28c4f63e57e830c04');"><i class="icon-edit"></i> 编辑</a>

								<a href = "http://localhost:8080/mbfw/asset/defProcess" class="btn btn-mini btn-danger" title="删除" onClick="delUser('849e0eb85e4e46d28c4f63e57e830c04','liaoxuewen');"><i class="icon-trash"></i>设置流程</a>

							</div>
						</td>
					</tr>

					<tr>
						<td class="center" style="width: 30px;">
							<label><input type="checkbox" name="ids" value="089d664844f8441499955b3701696fc0" id="asdadf@qq.com" alt="18766666666"><span class="lbl"></span></label>

						</td>
						<td class="center" style="width: 30px;">2</td>
						<td>档案袋</td>
						<td>2015.06.08</td>
						<td>地址耐用品</td>
						<td>办公设备</td>
						<td>罗杰</td>
						<td>办公设备流程B</td>

						<td style="width: 60px;">
							<div class="hidden-phone visible-desktop btn-group">

								<a class="btn btn-mini btn-info" title="编辑" onClick="editUser('089d664844f8441499955b3701696fc0');"><i class="icon-edit"></i>编辑</a>

								<a href = "http://localhost:8080/mbfw/asset/defProcess" class="btn btn-mini btn-danger" title="删除" onClick="delUser('849e0eb85e4e46d28c4f63e57e830c04','liaoxuewen');"><i class="icon-trash"></i>设置流程</a>

							</div>
						</td>
					</tr>

					<tr>
						<td class="center" style="width: 30px;">
							<label><input type="checkbox" name="ids" value="0e2da7c372e147a0b67afdf4cdd444a3" id="q324@qq.com" alt="18767676767"><span class="lbl"></span></label>

						</td>
						<td class="center" style="width: 30px;">3</td>
						<td>塑料袋</td>
						<td>2017.08.24</td>
						<td>快速消费品</td>
						<td>一次性用品</td>
						<td>杨平安</td>
						<td>一次性用品流程A</td>
						<td style="width: 60px;">
							<div class="hidden-phone visible-desktop btn-group">

								<a class="btn btn-mini btn-info" title="编辑" onClick="editUser('0e2da7c372e147a0b67afdf4cdd444a3');"><i class="icon-edit"></i>编辑</a>

								<a href = "http://localhost:8080/mbfw/asset/defProcess" class="btn btn-mini btn-danger" title="删除" onClick="delUser('849e0eb85e4e46d28c4f63e57e830c04','liaoxuewen');"><i class="icon-trash"></i>设置流程</a>

							</div>
						</td>
					</tr>

				</tbody>
			</table>


			<div class="page-header position-relative">
				<table style="width:100%;">
					<tbody>
						<tr>
							<td style="vertical-align:top;">

								<a class="btn btn-small btn-success" onClick="add();">新增</a>

								<a  title="生成treeview" class="btn btn-small btn-danger" onClick="duihua()">确定</a>
							</td>
							<td style="vertical-align:top;">
								<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">
									<ul>
										<li>
											<a>共
												<font color="red">3</font>条</a>
										</li>
										<li><input type="number" value="" id="toGoPage" style="width:50px;text-align:center;float:left" placeholder="页码"></li>
										<li style="cursor:pointer;">
											<a onClick="toTZ();" class="btn btn-mini btn-success">跳转</a>
										</li>
										<li>
											<a>首页</a>
										</li>
										<li>
											<a>上页</a>
										</li>
										<li>
											<a>
												<font color="#808080">1</font>
											</a>
										</li>
										<li>
											<a>下页</a>
										</li>
										<li>
											<a>尾页</a>
										</li>
										<li>
											<a>第1页</a>
										</li>
										<li>
											<a>共1页</a>
										</li>
										<li>
											<select title="显示条数" style="width:55px;float:left;" onChange="changeCount(this.value)">
												<option value="10">10</option>
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="40">40</option>
												<option value="50">50</option>
												<option value="60">60</option>
												<option value="70">70</option>
												<option value="80">80</option>
												<option value="90">90</option>
												<option value="99">99</option>
											</select>
										</li>
									</ul>
									<script type="text/javascript">
									
									
									//新增
									function add() {
										top.jzts();
										var diag = new top.Dialog();
										diag.Drag = true;
										diag.Title = "新增";
										diag.URL = 'http://localhost:8080/mbfw/asset/addPoint.do';
										diag.Width = 225;
										diag.Height = 415;
										diag.CancelEvent = function() { //关闭事件
											if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
												if('1' == '0') {
													top.jzts();
													setTimeout("self.location=self.location", 100);
												} else {
													nextPage(1);
												}
											}
											diag.close();
										};
										diag.show();
									}
									
										function nextPage(page) {
											top.jzts();
											if(true && document.forms[0]) {
												var url = document.forms[0].getAttribute("action");
												if(url.indexOf('?') > -1) {
													url += "&currentPage=";
												} else {
													url += "?currentPage=";
												}
												url = url + page + "&showCount=10";
												document.forms[0].action = url;
												document.forms[0].submit();
											} else {
												var url = document.location + '';
												if(url.indexOf('?') > -1) {
													if(url.indexOf('currentPage') > -1) {
														var reg = /currentPage=\d*/g;
														url = url.replace(reg, 'currentPage=');
													} else {
														url += "&currentPage=";
													}
												} else {
													url += "?currentPage=";
												}
												url = url + page + "&showCount=10";
												document.location = url;
											}
										}

										function changeCount(value) {
											top.jzts();
											if(true && document.forms[0]) {
												var url = document.forms[0].getAttribute("action");
												if(url.indexOf('?') > -1) {
													url += "&currentPage=";
												} else {
													url += "?currentPage=";
												}
												url = url + "1&showCount=" + value;
												document.forms[0].action = url;
												document.forms[0].submit();
											} else {
												var url = document.location + '';
												if(url.indexOf('?') > -1) {
													if(url.indexOf('currentPage') > -1) {
														var reg = /currentPage=\d*/g;
														url = url.replace(reg, 'currentPage=');
													} else {
														url += "1&currentPage=";
													}
												} else {
													url += "?currentPage=";
												}
												url = url + "&showCount=" + value;
												document.location = url;
											}
										}

										function toTZ() {
											var toPaggeVlue = document.getElementById("toGoPage").value;
											if(toPaggeVlue == '') {
												document.getElementById("toGoPage").value = 1;
												return;
											}
											if(isNaN(Number(toPaggeVlue))) {
												document.getElementById("toGoPage").value = 1;
												return;
											}
											nextPage(toPaggeVlue);
										}
									</script>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<!--/.fluid-container#main-container-->
		<!-- basic scripts -->
		<!-- 引入 -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
		</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->

		<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="static/js/myjs/menusf.js"></script>

		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/index.js"></script>
	</body>

</html>