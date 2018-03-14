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
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	</head> 
<body>

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

	<div class="container-fluid" id="main-container">
	<div id="page-content" class="clearfix">
						
	<div class="row-fluid">

	<div class="row-fluid">

			<div class="row-fluid">

			<!-- 检索  -->
			<form action="asset/derepair.do" method="post" name="asset_derepairForm"
				id="asset_derepairForm">
				<c:if test="${pd.userPermission != 2}">
				<table>
					<tr>
						<td style="vertical-align: top;"><a
							class="btn btn-small btn-success" onclick="addRAR();">新增报修表</a> 
						</td>
					
						<c:if test="${pd.userPermission == 1}">
						<td style="vertical-align:top;"><a class="btn btn-small btn-danger"  
						 onclick="makeAll('确定要删除选中的数据吗?');">批量删除</a></td>
						
						</c:if>
						<td>&nbsp;&nbsp;&nbsp;</td> 
						<td>
						  <span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="retrieve_content"  placeholder="这里输入关键字检索" />
							<i id="nav-search-icon" class="icon-search"></i>
						  </span>
						</td>
						<td style="vertical-align:top;">
							<button class="btn btn-mini btn-light" onclick="search();" title="检索">
							 <i id="nav-search-icon" class="icon-search"></i>
							</button>
						</td>
					</tr>
				</table>
				</c:if>
			</form>
		</div>
		</div>
		<!--/row-->
		<div style="overflow-x:auto;width: 100%;height: 100%;">
		<table id="table_report"
			class="table table-striped table-bordered table-hover">

			<thead>
				<tr>
					<th class="center"><label><input type="checkbox"
							id="zcheckbox" /><span class="lbl"></span>
					</label></th>
					<th class="center">序号</th>
					<th class="center">资产编码</th>
					<th class="center">资产名称</th>
					<th class="center">银行名称</th>
					<th class="center">报修部门</th>
					
					<th class="center"><i class="icon-time hidden-phone"></i>报修时间</th>
					<th class="center">使用人</th>
					<th class="center">故障现象</th>
					<th class="center">故障原因</th>
					<th class="center">维修结果</th>
					<th class="center"><i class="icon-time hidden-phone"></i>维修完成时间</th>
					<th class="center">维修机构</th>
					<th class="center"><i class="icon-time hidden-phone"></i>保修截止时间</th>
					<th class="center">保修状态</th>
					<th class="center">维修费用</th>
					<th class="center">备注</th>
					<th class="center">操作</th>
				</tr>
			</thead>

			<tbody>

				<!-- 开始循环 -->

					<c:choose>
					<c:when test="${not empty rarList}">
						
						<c:forEach items="${rarList}" var="allot" varStatus="vs">

							<tr >
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${allot.id}" id="18766666666@qq.com" alt="18766666666"/><span class="lbl"></span></label>
								</td>
								<td class="center">${vs.index+1}</td>
								<td class='center' style="width: 30px;">${allot.asset_code}</td>
								<td class="center">${allot.asset_name}</td>
								<td class="center">${allot.bank_name}</td>
								<td class="center">${allot.department}</td>
								<td class="center">${allot.repair_time}</td>
								<td class="center">${allot.asset_person}</td>
								<td class="center">${allot.fault_phenomen}</td>
								<td class="center">${allot.fault_reason}</td>
								<td class="center">${allot.maintain_result}</td>
								<td class="center">${allot.finishi_time}</td>
								<td class="center">${allot.drep_department}</td>
								<td class="center">${allot.defect_time}</td>
								<td class="center">${allot.status}</td>
								<td class="center">${allot.cost}</td>
								<td class="center">${allot.remark}</td>
								
								 <td style="width: 30px;" class="center">
								 <div class="hidden-phone visible-desktop btn-group">
							
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">														
									<c:if test="${pd.userPermission <= 2}">	<li><a style="cursor:pointer;" title="维修" onclick="editRAR1('${allot.id}');" class="btn btn-small btn-success"><i class="icon-con"></i>维修</a></li>
										<li><a style="cursor:pointer;" title="编辑" onclick="editRAR('${allot.id}');" class="tooltip-success " data-rel="tooltip" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>	</c:if>		
									<c:if test="${pd.userPermission == 3}">	<li><a style="cursor:pointer;" title="您无权编辑"  onclick="" class="tooltip-success btn-default" data-rel="tooltip" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>	</c:if>					
										<c:if test="${pd.userPermission == 1 }"><li><a style="cursor:pointer;" title="删除" onclick="delRAR('${allot.id}','${allot.asset_code}');" class="tooltip-error" data-rel="tooltip" data-placement="left"><span class="red"><i class="icon-trash"></i></span></a></li></c:if>
									<c:if test="${pd.userPermission == 3}">		<li><a style="cursor:pointer;" title="您无权删除" onclick="" class="tooltip-error btn-default" data-rel="tooltip" data-placement="left"><span class="red"><i class="icon-trash"></i></span></a></li></c:if>
										</ul>
									</div>

								</td> 
							</tr>
				
						</c:forEach>					
						</c:when>
						</c:choose>		
				
				</tbody>
			</table>
		</div>
				<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
				</td>
				<!-- 分页内容的处理部分 -->
						<td style="vertical-align:top;">
							<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">
								<ul>
									<li><a>共<font color=red>${page.totalResult}</font>条</a></li>
									<li><input type="number" value="" id="jumpPageNumber" style="width:50px;text-align:center;float:left" placeholder="页码"/></li>
									<li style="cursor:pointer;"><a onclick="jumpPage();"  class="btn btn-mini btn-success">跳转</a></li>
									<c:choose>
									<c:when test="${page.currentPage == 1 }">	
										<li><a>首页</a></li>
										<li><a>上页</a></li>
									<!-- 分是否为第一页的两种情况，不为第一页的话，那么就要设置首页和上一页为有onclick点击事件 -->
									</c:when>
									<c:otherwise>
										<li style="cursor:pointer;"><a onclick="nextPage(1);">首页</a></li>
										<li style="cursor:pointer;"><a onclick="nextPage(${page.currentPage}-1);">上页</a></li>
									</c:otherwise>
									</c:choose>
									<!-- 循环遍历产生页码数目的数字显示-->
									 <c:forEach  var="pagenumber"  begin="1" end="${page.totalPage}">
										<!-- 判断页码是否是当前页，是的话，就换个颜色来标记 -->
										<c:choose>
											<c:when test="${page.currentPage == pagenumber}">
												<li><a><b style="color: red;">${page.currentPage}</b></a></li>
											</c:when>
											<c:otherwise>
												<li style="cursor:pointer;"><a onclick="changePage(${pagenumber});">${pagenumber}</a></li>												
											</c:otherwise>
										</c:choose>				
									</c:forEach> 
									
									<!-- 处理 当前页是否最后一页，不是的话，就需要添加下一页的点击时间-->
									<c:choose>
										<c:when test="${page.currentPage == page.totalPage }">
											<li><a>下页</a></li>
											<li><a>尾页</a></li>
										</c:when>
										<c:otherwise>
											<li style="cursor:pointer;"><a onclick="nextPage(${page.currentPage}+1)">下页</a></li>
											<li style="cursor:pointer;"><a onclick="nextPage(${page.totalPage});">尾页</a></li>
										</c:otherwise>						
									</c:choose>
										
									<!-- 处理 页面大小的处理-->
									<li><a>第${page.currentPage}页</a></li>
									<li><a>共${page.totalPage}页</a></li>
									<!-- 进行每一个页面显示数据条目大小的选择 -->
									<li>
									<!-- 注意：当进行选择改变的时候，就会出发changeCount这个事件，再通过Ajax进行页面数据的请求 -->
									<select title='显示条数' style="width:55px;float:left;" onchange="changeCount(this.value)">
									<option value="${page.showCount}">${page.showCount}</option>
									<option value='10'>10</option>
									<option value='20'>20</option>
									<option value='30'>30</option>
									<option value='40'>40</option>
									<option value='50'>50</option>
									<option value='60'>60</option>
									<option value='70'>70</option>
									</select>
									</li>
								</ul>					
							</div>
						
						</td>
				
			</tr>
		</table>
		</div>
			</div>
		</div>
			
			<!--/#page-content-->
	
		<!--/.fluid-container#main-container-->
<script type="text/javascript">
function nextPage(page){ top.jzts();	if(true && document.forms[0]){
		var url = document.forms[0].getAttribute("action");
		if(url.indexOf('?')>-1){url += "&currentPage=";}
		else{url += "?currentPage=";}
		url = url + page + "&showCount=10";
		document.forms[0].action = url;
		document.forms[0].submit();
	}else{
		var url = document.location+'';
		if(url.indexOf('?')>-1){
			if(url.indexOf('currentPage')>-1){
				var reg = /currentPage=\d*/g;
				url = url.replace(reg,'currentPage=');
			}else{
				url += "&currentPage=";
			}
		}else{url += "?currentPage=";}
		url = url + page + "&showCount=10";
		document.location = url;
	}
}
function changeCount(value){ top.jzts();	if(true && document.forms[0]){
		var url = document.forms[0].getAttribute("action");
		if(url.indexOf('?')>-1){url += "&currentPage=";}
		else{url += "?currentPage=";}
		url = url + "1&showCount="+value;
		document.forms[0].action = url;
		document.forms[0].submit();
	}else{
		var url = document.location+'';
		if(url.indexOf('?')>-1){
			if(url.indexOf('currentPage')>-1){
				var reg = /currentPage=\d*/g;
				url = url.replace(reg,'currentPage=');
			}else{
				url += "1&currentPage=";
			}
		}else{url += "?currentPage=";}
		url = url + "&showCount="+value;
		document.location = url;
	}
}
function toTZ(){var toPaggeVlue = document.getElementById("toGoPage").value;if(toPaggeVlue == ''){document.getElementById("toGoPage").value=1;return;}if(isNaN(Number(toPaggeVlue))){document.getElementById("toGoPage").value=1;return;}nextPage(toPaggeVlue);}

</script>


	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i> </a>

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
		
		//检索
		function search(){
			top.jzts();
			$("#asset_derepairForm").submit();
		}
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = 'http://localhost:8080/mbfw/head/goSendEmail.do?EMAIL='+EMAIL+'&msg=appuser';
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
			 diag.URL = 'http://localhost:8080/mbfw/head/goSendSms.do?PHONE='+phone+'&msg=appuser';
			 diag.Width = 600;
			 diag.Height = 265;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//新增报修表
		function addRAR(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="填写报修表";
			 diag.URL = '<%=basePath%>asset/goAddRepair.do';
			diag.Width = 1050;
			diag.Height = 370;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document
						.getElementById('zhongxin').style.display == 'none') {
					if ('1' == '0') {
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
	  //新增维修表
		function addRAR1(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="填写维修表";
			 diag.URL = '<%=basePath%>asset/goAddRepair1.do';
			 diag.Width = 1050;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('1' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(1);
					 }
				}
				diag.close();
			 };
			 diag.show();
		} 
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/derepair?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/asset/derepair?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/asset/derepair?currentPage="+${page.currentPage}+"&showCount="+pagesize;
		}
		//操作4：处理跳转按钮页面的处理
		function jumpPage(){
			//1.获取页码框中的数值大小
			var toPaggeVlue = $('#jumpPageNumber').val();
			//2.对数值进行一些判断，是否符合正常的规范
			if(toPaggeVlue == ''){  //如果是空，就设置为1
				$('#jumpPageNumber').val(1);
				toPaggeVlue =1;
			}
			if(isNaN(Number(toPaggeVlue))){ //如果是非数字，也就设置为1，其实这个在input组件中，已经可以控制了
				$('#jumpPageNumber').val(1);	
				toPaggeVlue =1;
			}
			//3:执行nextPage函数就可以了
			nextPage(toPaggeVlue);
		}
		//修改
		function editRAR(user_id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "修改表";
			diag.URL = '<%=basePath%>asset/goEditRAR.do?id='+user_id;
			diag.Width = 1080;
			diag.Height = 450;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document
						.getElementById('zhongxin').style.display == 'none') {
					nextPage(1);
				}
				diag.close();
			};
			diag.show();
		}
		//维修
		function editRAR1(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="请填写维修情况";
			 diag.URL = '<%=basePath%>asset/goAddRepair1.do?id='+id;
			 diag.Width = 800;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(1);
				}
				diag.close();
			 };
			 diag.show();
		}

		//删除
		function delRAR(user_id,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = '<%=basePath%>asset/deleteRAR.do?id='+user_id;
					$.get(url,function(data){
					 	window.location = "${pageContext.request.contextPath}/asset/derepair"; 
					});
				}
			});
		}
	</script>

	<script type="text/javascript">
		$(function() {

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

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
								url: '<%=basePath%>asset/deleteAllRAR.do?tm='+new Date().getTime(),
						    	data: {id:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						} else if(msg == '确定要给选中的用户发送邮件吗?'){
							sendEmail(emstr);
						}else if(msg == '确定要给选中的用户发送短信吗?'){
							sendSms(phones);
						}
						
					} 
				}
			});
		}

		//导出excel
		function toExcel() {
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			var STATUS = $("#STATUS").val();
			window.location.href = 'http://localhost:8080/mbfw/happuser/excel.do?USERNAME='
					+ USERNAME
					+ '&lastLoginStart='
					+ lastLoginStart
					+ '&lastLoginEnd='
					+ lastLoginEnd
					+ '&ROLE_ID='
					+ ROLE_ID
					+ '&STATUS=' + STATUS;
		}
	</script>

</body>
</html>

