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
</head>

<body>
	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">
					<!-- 检索ghfh  -->
					<form action="user/listUsers.do" method="post" name="userForm"
						id="userForm">
						<!-- 检索  -->
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
										<td><input class="span10 date-picker"
											name="lastLoginStart" id="lastLoginStart" value=""
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 88px;" placeholder="开始日期" title="设置起始领用时间"></td>
										<td><input class="span10 date-picker" name="lastLoginEnd"
											value="" type="text" data-date-format="yyyy-mm-dd"
											readonly="readonly" style="width: 88px;" placeholder="结束日期"
											title="设置终止领用时间"></td>

								</span></td>


								<!-- 资产使用状态 -->
								<td style="vertical-align: top;"><select
									class="chzn-select" name="ASSERT_USE_ID" id="assert_use_id"
									data-placeholder="流程类别"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<%-- <c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach> --%>
										<option value="">审批流程</option>
										<option value="">工作流程</option>
								</select></td>


								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
								<td style="vertical-align: top;"><a
									class="btn btn-mini btn-light"
									onclick="window.location.href='http://localhost:8080/mbfw//user/listtabUsers.do';"
									title="切换模式"><i id="nav-search-icon" class="icon-exchange"></i></a></td>
								<td style="vertical-align: top;"><a
									class="btn btn-mini btn-light" onclick="toExcel();"
									title="导出到EXCEL"><i id="nav-search-icon"
										class="icon-download-alt"></i></a></td>
								<td style="vertical-align: top;"><a
									class="btn btn-mini btn-light" onclick="fromExcel();"
									title="从EXCEL导入"><i id="nav-search-icon"
										class="icon-cloud-upload"></i></a></td>
							</tr>
						</table>
						<!-- 检索  -->

						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>

								<tr>
									<th class="center"><label><input type="checkbox"
											id="zcheckbox"><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>流程名</th>
									<th>流程类型</th>
									<th><i class="icon-time hidden-phone"></i>最近修改时间</th>
									<th>上次修改IP</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->

								<tr>
									<td class="center" style="width: 30px;"><label><input
											type="checkbox" name="ids"
											value="849e0eb85e4e46d28c4f63e57e830c04"
											id="759789581@qq.com" alt="15200228365"><span
											class="lbl"></span></label></td>
									<td class="center" style="width: 30px;">1</td>
									<td>电子设备采购</td>
									<td>审批流程</td>
									<td>2017-08-15 15:57:51</td>
									<td>0:0:0:0:0:0:0:1</td>
									<td style="width: 60px;">
										<div class="hidden-phone visible-desktop btn-group">

											<a href="http://localhost:8080/mbfw/process/edit"
												class="btn btn-mini btn-info" title="编辑"><i
												class="icon-edit"></i> 编辑</a> <a class="btn btn-mini btn-danger"
												title="删除"><i class="icon-trash"></i>删除</a>

										</div>
									</td>
								</tr>

								<tr>
									<td class="center" style="width: 30px;"><label><input
											type="checkbox" name="ids"
											value="849e0eb85e4e46d28c4f63e57e830c04"
											id="759789581@qq.com" alt="15200228365"><span
											class="lbl"></span></label></td>
									<td class="center" style="width: 30px;">2</td>
									<td>书籍采购</td>
									<td>审批流程</td>
									<td>2017-08-15 15:57:51</td>
									<td>0:0:0:0:0:0:0:1</td>
									<td style="width: 60px;">
										<div class="hidden-phone visible-desktop btn-group">

											<a href="http://localhost:8080/mbfw/process/edit"
												class="btn btn-mini btn-info" title="编辑"><i
												class="icon-edit"></i> 编辑</a> <a class="btn btn-mini btn-danger"
												title="删除"><i class="icon-trash"></i>删除</a>

										</div>
									</td>
								</tr>


								<tr>
									<td class="center" style="width: 30px;"><label><input
											type="checkbox" name="ids"
											value="849e0eb85e4e46d28c4f63e57e830c04"
											id="759789581@qq.com" alt="15200228365"><span
											class="lbl"></span></label></td>
									<td class="center" style="width: 30px;">1</td>
									<td>设备申领</td>
									<td>工作流程</td>
									<td>2017-08-15 15:57:51</td>
									<td>0:0:0:0:0:0:0:1</td>
									<td style="width: 60px;">
										<div class="hidden-phone visible-desktop btn-group">

											<a href="http://localhost:8080/mbfw/process/edit"
												class="btn btn-mini btn-info" title="编辑"><i
												class="icon-edit"></i> 编辑</a> <a class="btn btn-mini btn-danger"
												title="删除"><i class="icon-trash"></i>删除</a>

										</div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tbody>
									<tr>
										<td style="vertical-align: top;"><a
											href="http://localhost:8080/mbfw/process/add"
											class="btn btn-small btn-success">新增</a> <!-- <a href="" class="btn btn-small btn-success" onClick="add();">新增</a> 
								<a  title="生成treeview" class="btn btn-small btn-danger" onClick="duihua()">确定</a>-->
										</td>
										<td style="vertical-align: top;">
											<div class="pagination"
												style="float: right; padding-top: 0px; margin-top: 0px;">
												<ul>
													<li><a>共 <font color="red">3</font>条
													</a></li>
													<li><input type="number" value="" id="toGoPage"
														style="width: 50px; text-align: center; float: left"
														placeholder="页码"></li>
													<li style="cursor: pointer;"><a onClick="toTZ();"
														class="btn btn-mini btn-success">跳转</a></li>
													<li><a>首页</a></li>
													<li><a>上页</a></li>
													<li><a> <font color="#808080">1</font>
													</a></li>
													<li><a>下页</a></li>
													<li><a>尾页</a></li>
													<li><a>第1页</a></li>
													<li><a>共1页</a></li>
													<li><select title="显示条数"
														style="width: 55px; float: left;"
														onChange="changeCount(this.value)">
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
													</select></li>
												</ul>
												<script type="text/javascript">
													//新增
													/*function add() {
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
													}*/

													function nextPage(page) {
														top.jzts();
														if (true && document.forms[0]) {
															var url = document.forms[0]
																	.getAttribute("action");
															if (url
																	.indexOf('?') > -1) {
																url += "&currentPage=";
															} else {
																url += "?currentPage=";
															}
															url = url
																	+ page
																	+ "&showCount=10";
															document.forms[0].action = url;
															document.forms[0]
																	.submit();
														} else {
															var url = document.location
																	+ '';
															if (url
																	.indexOf('?') > -1) {
																if (url
																		.indexOf('currentPage') > -1) {
																	var reg = /currentPage=\d*/g;
																	url = url
																			.replace(
																					reg,
																					'currentPage=');
																} else {
																	url += "&currentPage=";
																}
															} else {
																url += "?currentPage=";
															}
															url = url
																	+ page
																	+ "&showCount=10";
															document.location = url;
														}
													}

													function changeCount(value) {
														top.jzts();
														if (true && document.forms[0]) {
															var url = document.forms[0]
																	.getAttribute("action");
															if (url
																	.indexOf('?') > -1) {
																url += "&currentPage=";
															} else {
																url += "?currentPage=";
															}
															url = url
																	+ "1&showCount="
																	+ value;
															document.forms[0].action = url;
															document.forms[0]
																	.submit();
														} else {
															var url = document.location
																	+ '';
															if (url
																	.indexOf('?') > -1) {
																if (url
																		.indexOf('currentPage') > -1) {
																	var reg = /currentPage=\d*/g;
																	url = url
																			.replace(
																					reg,
																					'currentPage=');
																} else {
																	url += "1&currentPage=";
																}
															} else {
																url += "?currentPage=";
															}
															url = url
																	+ "&showCount="
																	+ value;
															document.location = url;
														}
													}

													function toTZ() {
														var toPaggeVlue = document
																.getElementById("toGoPage").value;
														if (toPaggeVlue == '') {
															document
																	.getElementById("toGoPage").value = 1;
															return;
														}
														if (isNaN(Number(toPaggeVlue))) {
															document
																	.getElementById("toGoPage").value = 1;
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

				</div>


			</div>


			<!-- PAGE CONTENT ENDS HERE -->
		</div>
		<!--/row-->

	</div>
	<!--/#page-content-->

	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
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
	<!-- basic scripts -->

	<!-- basic function -->
	<script type="text/javascript">
		function duihua() {
			alert("设置成功，设置结果展示在左边！");
		}
	
	
	//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL='+EMAIL;
			 diag.Width = 660;
			 diag.Height = 470;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//去发送短信页面
		function sendSms(phone){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送短信";
			 diag.URL = '<%=basePath%>head/goSendSms.do?PHONE='+phone+'&msg=appuser';
			 diag.Width = 600;
			 diag.Height = 265;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资产领用表";
			 diag.URL = '<%=basePath%>asset/goAddA.do';
			 diag.Width = 225;
			 diag.Height = 335;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>user/goEditU.do?USER_ID='+user_id;
			 diag.Width = 225;
			 diag.Height = 415;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>user/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
						    	data: {USER_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}else if(msg == '确定要给选中的用户发送邮件吗?'){
							sendEmail(emstr);
						}else if(msg == '确定要给选中的用户发送短信吗?'){
							sendSms(phones);
						}
						
						
					}
				}
			});
		}
		
		</script>

	<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>user/goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		</script>

	<!-- basic function -->
</body>

</html>