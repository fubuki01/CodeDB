﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="java.net.URLEncoder" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
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
	<link rel="stylesheet" type="text/css"
	href="static/js/myconfirm/css/xcConfirm.css" />
<script src="static/js/myconfirm/js/jquery-1.9.1.js"
	type="text/javascript" charset="utf-8"></script>
<script src="static/js/myconfirm/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">

	<div  class="row-fluid" ><!--  style="position:absolute; height:400px; overflow:auto"  -->
	
			<!-- 检索  -->
			<form action="provider/apl_provider_manager.do" method="post" name="providerForm" id="providerForm">
			<table>
				<tr>
					<td style="vertical-align:top;">
					<a class="btn btn-small btn-success" onclick="add();">新增供应商</a>
					<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');">批量删除</a>
					</td><td>&nbsp;&nbsp;&nbsp;</td>
					<td >
					  <span class="input-icon">
						<input autocomplete="off" id="nav-search-input" type="text" name="retrieve_content"  placeholder="这里输入关键词" />
						<i id="nav-search-icon" class="icon-search"></i>
					  </span>
					</td>
					<td style="vertical-align:top;">
						<button class="btn btn-mini btn-light" onclick="search();" title="检索">
						 <i id="nav-search-icon" class="icon-search"></i>
						</button>
					</td>
					<c:if test="${QX.edit == 1 }"><td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td></c:if>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
			<!-- 检索  -->
			
			<div style="overflow-x:auto;width: 100%;height: 100%;">
			<table id="table_report" class="table text-table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>供应商代码</th>
						
						<th>供应商名称</th>
						<th>供应商地址</th>
						<th>联系电话</th>
						
						<th>传真</th>
						<th>邮箱</th>
						<th>负责人或联系人姓名</th>
						<th>联系人电话</th>
						<th>产品质量标准</th>
						<th>主要产品</th>
						<th>供应商资质</th>
						<th>评价</th>
						<th>备注</th>
						 <th>操作</th> 
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty providerFind}">
						<c:forEach items="${providerFind}" var="pf" varStatus="vs">
							<tr>
								<td><label><input type="checkbox" name="plsc" id="aprovider_id" value="${pf.provider_code }@${pf.provider_name}"/><span class="lbl"></span></label></td>
								<td>${vs.index+1}</td>
								<td>
									<c:if test="${ pf.provider_code < 10 }">
										0${ pf.provider_code }
									</c:if>
									<c:if test="${ pf.provider_code >= 10 }">
										${ pf.provider_code }
									</c:if>
								</td>
								<td>${ pf.provider_name}</td>
								<td>${ pf.provider_address}</td>
								<td>${ pf.provider_tel}</td>
								
								<td>${ pf.provider_fax}</td>
								<td>${ pf.provider_email}</td>
								<td>${ pf.provider_conn_person}</td>
								<td>${ pf.provider_conn_tel}</td>
								<td>${ pf.products_quality_standard}</td>
								<td>${ pf.provider_main_products}</td>
								<td>${ pf.provider_aptitude}</td>
								<td>${ pf.provider_comment}</td>
								<td>${ pf.provider_note}</td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<c:if test="${QX.edit == 1 }">
											<li><a style="cursor:pointer;" title="编辑" onclick="editProvider('${pf.provider_code }','${pf.provider_name }');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											</c:if>
											<c:choose>
											<c:when test="${user.USERNAME=='admin'}">
												<li><a style="cursor:pointer;" title="删除" onclick="delProvider('${pf.provider_code }','${pf.provider_name }');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>												
											</c:when>
											<c:otherwise>
												<c:if test="${QX.del == 1 }">
												<li><a style="cursor:pointer;" title="删除" onclick="delProvider('${pf.provider_code }','${pf.provider_name }');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
												</c:if>
											</c:otherwise>
											</c:choose>
										</ul>
										
										</div>
										
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
										<!-- 分页处理的优化工作 -->
									<c:choose>
										<c:when test="${page.currentPage + 4 > page.totalPage}">  <!-- 现在每个分页为显示5页 ，所以先判断当前的页面+4是否大于总的页面数-->
											<c:choose>
												<c:when test="${page.totalPage-4 > 0}">   <!-- 判断是否总的页面数也是大于5，因为这样的话，就会出现两种情况 -->
													<c:forEach var="index1" begin="${page.totalPage-4}" end="${page.totalPage}" step="1">
														<c:if test="${index1 >= 1}">
															<c:choose>
																<c:when test="${page.currentPage == index1}">
																	<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																</c:when>
																<c:otherwise>
																	<li style="cursor:pointer;"><a onclick="changePage(${index1});">${index1}</a></li>												
																</c:otherwise>
															</c:choose>
														</c:if>
													</c:forEach>
												</c:when>
												
												<c:otherwise>  <!-- 当总的页面数都不足5的时候，那么直接全部显示即可，不需要考虑太多 -->
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
												</c:otherwise>
											</c:choose>
											
										</c:when>
										
										<c:otherwise>  <!-- 当当前页面数+4都还是小于总的页面数的情况 -->
											<c:choose>
												<c:when test="${page.currentPage != 1}">	<!-- 判断当前页面是否就是第一页，因为这样也会有两种情况的处理 -->												
														<c:forEach var="index2" begin="${page.currentPage-1}" end="${page.currentPage+3}"> <!-- 从当前页面减一的页面数开始，这样点击前面一页就会显示其他的页面，从而实现页面跳转 -->
															<c:choose>
																<c:when test="${page.currentPage == index2}">
																	<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																</c:when>
																<c:otherwise>
																	<li style="cursor:pointer;"><a onclick="changePage(${index2});">${index2}</a></li>												
																</c:otherwise>	
															</c:choose>										
														</c:forEach>												
												</c:when>
												
												<c:otherwise>	<!-- 当当前页面数就是第一页的时候，就直接显示1-5页即可 -->											
													<c:forEach var="index3" begin="1" end="5">
														<c:choose>
															<c:when test="${page.currentPage == index3}">
																<li><a><b style="color: red;">${page.currentPage}</b></a></li>
															</c:when>
															<c:otherwise>
																<li style="cursor:pointer;"><a onclick="changePage(${index3});">${index3}</a></li>												
															</c:otherwise>
														</c:choose>
													</c:forEach>													
												</c:otherwise>												
											</c:choose>
										</c:otherwise>
									</c:choose>
									
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
									<option value='5'>5</option>
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
		</form>
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#providerForm").submit();
		}
		var msg='${msg_provider}';
		var pname='${provider_name}';
		var provider_del="${delProvider}";
		//判断项目删除是否成功
		if(provider_del == "success"){
			var txt=  "删除成功！";
			bootbox.confirm('<b style="color: red">'+txt+" </b>", function(result) {});
		} 
		
		if(msg == 'hadprovider_code'){
			bootbox.confirm('供应商名称[<b style="color: red">'+pname+" </b>]已经被使用，不能删除！", function(result) {});
			
		}else if(msg=="del_all_provider"){
			bootbox.confirm('供应商名称[ <b style="color: red">'+pname+" </b>]已经被使用，不能删除！", function(result) {});
		}
		
		var update_result="${update_result}"; // 不能编辑提示
		if(update_result=='usuccess'){
			bootbox.confirm('<b style="color: red">该供应商已经被使用，不能修改！</b>', function(result) {});
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
			 diag.Title ="新增供应商";
			 diag.URL = '<%=basePath%>provider/apl_provider_insert.do';
			 diag.Width = 900;
			 diag.Height = 530;
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
		function editProvider(provider_code,provider_name){
			window.location.href='<%=basePath%>provider/apl_provider_edit.do?provider_code='+provider_code;
		}
		
		
		//删除
		function delProvider(provider_id,msg){
			var del='del';
			bootbox.confirm('你确定要删除供应商名称[ <b style="color: red">'+msg+" </b>]吗？", function(result) {
				window.location.href="<%=basePath%>provider/delete_provider.do?provider_code="+provider_id+ "&flag="+del+"&provider_name="+encodeURI(encodeURI(msg));
			}
			);
			
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('plsc').length;i++)
					{
						  if(document.getElementsByName('plsc')[i].checked){
						  	if(str=='') str += document.getElementsByName('plsc')[i].value;
						  	else str += ',' + document.getElementsByName('plsc')[i].value;
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
								url: '<%=basePath%>provider/delete_all_provider.do',
						    	data: {del_all_provider:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: true,
								success: function(data){
									if(data.provider == null){
										 window.location = '<%=basePath %>provider/apl_provider_manager?delProvider=success';
									}else{
										window.location = '<%=basePath %>provider/apl_provider_manager?provider='+encodeURI(encodeURI(data.provider));
									}
								}
							});
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
			 diag.URL = '<%=basePath%>provider/goUploadExcel.do';
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
		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/provider/apl_provider_manager?currentPage="+clickpage+"&showCount="+${page.showCount};	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/provider/apl_provider_manager?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/provider/apl_provider_manager?currentPage="+${page.currentPage}+"&showCount="+pagesize;
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
		</script>
		
	</body>
</html>

