<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	
	<!-- confirm提示框 -->
	<link rel="stylesheet" type="text/css"
		href="static/js/myconfirm/css/xcConfirm.css" />
	<script src="static/js/myconfirm/js/jquery-1.9.1.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="static/js/myconfirm/js/xcConfirm.js" type="text/javascript"
		charset="utf-8"></script>

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

	<div id="page-content" class="clearfix">						
	<div class="row-fluid">
	<div class="row-fluid">
	
			<!-- 检索ghfh  -->
				<form action="asset/aucs_abandon.do" method="post" name="userForm" id="userForm">
					<c:if test="${permission != 3}">
					<table>
						<tr>
							<c:if test="${QX.add == 1 }">
								<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增资产报废表</a></td>
							</c:if>	
							<td style="vertical-align:top;"><a class="btn btn-small btn-danger" onclick="duihua();" title="剩余资产数量">资产数量查询</a></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>																					
							<td><span class="input-icon">
								<input autocomplete="off" id="nav-search-input" type="text" name="retrieve_content" id="retrieve_content" 
								title="关键字可以为“资产编码”、“资产名称”、“领用公司”、“领用部门”、“领用人”、“报废时间”、“审批状态” "
								value="${page.pd.retrieve_content}" placeholder="这里输入关键字检索" />
								<i id="nav-search-icon" class="icon-search"></i></span>
							</td>
							<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="查询"><i id="nav-search-icon" class="icon-search"></i></button></td>		
						</tr>
					</table>
					</c:if>
				
				<div style="overflow-x:auto;width: 100%;height: 100%;">			
				<table id="table_report" class="text-table table table-striped table-bordered table-hover">				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox"><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>资产编码</th>
						<th>资产名称</th>
						<th>使用公司</th>
						<th>使用部门</th>
						<th>使用人</th>
						<th>资产配置</th>
						<th>资产用途</th>							
						<th>报废原因</th>
						<th>报废时间</th>
						<th>资产状态</th>						
						<th>报废处理意见</th>						
						<th>报废审批</th>						
						<th>审批状态</th>
						<c:if test="${permission != 3}">
							<th>办理人</th>
							<th class="center">操作</th>
						</c:if>
					</tr>
				</thead>
										
				<tbody>					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty listApprover}">						
						<c:forEach items="${listApprover}" var="assetabandonmanage" varStatus="vs">
								<tr>
									<td><label><input type="checkbox" id="zcheckbox" name='ids' value="${assetabandonmanage.id}"><span class="lbl"></span></label></td>
									<td>${vs.index+1}</td>
									<td>${assetabandonmanage.asset_code}</td>
									<td>${assetabandonmanage.asset_name}</td>
									<td>${assetabandonmanage.asset_use_company}</td>
									<td>${assetabandonmanage.asset_use_dept}</td>
									<td>${assetabandonmanage.asset_user}</td>
									<td>${assetabandonmanage.asset_detail_config}</td>
									<td>${assetabandonmanage.asset_use}</td>
									<td>${assetabandonmanage.abandon_reason}</td>
									<td>${assetabandonmanage.abandon_time}</td>
									<td style="color: blue;">报废</td>
									<td>${assetabandonmanage.abandon_idea}</td>
									<td style="color: red;">${assetabandonmanage.abandon_approve}</td>
									<td style="color: red;">${assetabandonmanage.approve_status}</td>
									<td>${assetabandonmanage.abandon_manager}</td>
									<td style="width: 60px;">
									<div class="hidden-phone visible-desktop btn-group">
										<a  value="${assetabandonmanage.id}" class="btn btn-mini btn-info" title="编辑"  >编辑<i class="icon-edit"></i></a>
<%-- 										<a class="btn btn-mini btn-success" title="审批" onclick="approval('${assetabandonmanage.id}');" >审批</a>
 --%>									</div>																									
									</td>
								</tr>							
						</c:forEach>						
						<%-- <c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="10" class="center">您无权查看</td>
							</tr>
						</c:if> --%>
					</c:when>
					
					<c:otherwise>
						<tr class="main_info">
							<td colspan="17" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>						
				</tbody>
			</table>

			<div class="page-header position-relative">
				<table style="width:100%;">
					<tr>
						<c:if test="${permission != 3}">
							 <c:if test="${QX.del == 1 }">
								 <td style="vertical-align:top;">			 
								 <!--  <a title="批量发送短信" class="btn btn-small btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="icon-envelope"></i></a>							
								 -->
								 <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a>
								 </td>	
							 </c:if>	
						</c:if>										
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
			</div>

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
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//判断项目增加是否成功
		var saveResult = '${saveresult}';
		if(saveResult == "success"){
			var txt=  "增加成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
				
		//判断项目修改是否成功
		var updateResult = '${updateresult}';	
		if(updateResult == "success"){
			var txt=  "修改成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		

		//判断项目删除是否成功
		var delectResult = '${delectresult}';	
		if(delectResult == "success"){
			var txt=  "删除成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		} 
		
		var count = '${count}';
		if(count!=0){
			alert("当前剩余资产数量为："+count+"个");
		}
		function duihua()
		{
			window.location = "${pageContext.request.contextPath}/asset/check_AssetCountA"				
		}
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/aucs_abandon?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/asset/aucs_abandon?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/asset/aucs_abandon?currentPage="+${page.currentPage}+"&showCount="+pagesize;
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
		
		//新增
		function add(){
			window.location = "${pageContext.request.contextPath}/asset/goAddC.do";
			<%--  top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资产报废表";
			 diag.URL = '<%=basePath%>asset/goAddC.do';
			 diag.Width = 850;
			 diag.Height = 550;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 window.location = "${pageContext.request.contextPath}/asset/aucs_abandon?currentPage="+${page.currentPage};
					 }
				}
				diag.close();
			 };
			 diag.show(); --%>
		}
		
		 $('#table_report a').bind("click" , function(){
			var id = $(this).attr("value");  //获取到对应点击按钮的ID
			//判断点击按钮的兄弟的兄弟的值就是状态的内容
			var clickcontent =  $(this).parent().parent().prev().prev().text();
			if(clickcontent != "已提交"){
				alert("对不起，处于审批中，无法进行修改操作!");
			}
			else{  //非提交状态则都不允许修改
				window.location = '${pageContext.request.contextPath}/asset/go_edit_abandon.do?id='+id;
			}
		});
		
		
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					window.location = "${pageContext.request.contextPath}/asset/delete_AllAbandon.do?id="+userId;
					<%-- top.jzts();
					var url = "<%=basePath%>asset/deleteAbandon.do?id="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
						 window.location = "${pageContext.request.contextPath}/asset/aucs_abandon?currentPage="+${page.currentPage};
					}); --%>
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				
				if(result) {
					var deleteFail = "";
					var str = '';
					var emstr = '';
					var phones = '';					
						 $("input[name=ids]:checkbox").each(function(index ,value){
							 if(value.checked == true){
								 var con = $(this).parent().parent().parent().children().last().prev().prev().text();
								 if(con == "已提交"){ //能删除的状态
									if(str=='') 
								  		str += value.value;
								  	else 
								  		str += ',' + value.value;
								}
								 else{
									 deleteFail = deleteFail +"第"+(index+1)+"条-----";
								 }
							 } 
							 
						 });						 

				     
					if(str==''){
						if(deleteFail == ""){
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
						}
						else{							
							alert(deleteFail +"处于审批中条目，无法进行删除！");
						}
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
						top.jzts();
						$.ajax({
								type: "POST",
								url: '<%=basePath%>asset/delete_AllAbandon.do?tm='+new Date().getTime(),
						    	data: {id:str},
								dataType:'text',
								//beforeSend: validateData,
								cache: false,
								success: function(data){										
									window.location = "${pageContext.request.contextPath}/asset/aucs_abandon.do";									
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
</body>
</html>

