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
	</head> 
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
	
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">						
  <div class="row-fluid">
	<div class="row-fluid">
			<c:if test="${QX.cha == 1 }">
			<!-- 检索  -->
			<form action="user/listUsers.do" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td style="vertical-align:top;">
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="USERNAME" value="${pd.USERNAME }" placeholder="这里输入检索关键词" title="检索范围列表：从用户名，用户姓名，邮箱，部门，备注列表中进行搜索" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>				
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择系统角色" style="vertical-align:top;">
						<option value=""></option>
						<option value="">全部</option>
						<c:forEach items="${roleList}" var="role">
							<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
						</c:forEach>
					  	</select>
					</td>
					<!-- 用户权限搜索 -->
					<td style="vertical-align:top;">
						<select class="chzn-select" name="user_Permission" id="user_Permission" data-placeholder="请选择用户部门权限" style="vertical-align:top;">
						<option value=""></option>
						<option value="">全部</option>
						<c:forEach items="${userDepartmentAuthoritys}" var="department">
							<option value="${department.authority_Code}" <c:if test="${department.authority_Code==pd.user_Permission}">selected</c:if>>						
							${department.authority_Name}							
							</option>
						</c:forEach>
					  	</select>
					</td>
					<td><input class="span10 date-picker" name="creatuser_Time" id="creatuser_Time"  value="${pd.creatuser_Time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="用户创建开始日期搜索" title="用户创建开始日期" style="width: 155px;"/></td>
					<td><input class="span10 date-picker" name="creatuser_endTime" id="creatuser_endTime"  value="${pd.creatuser_endTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="用户创建截止日期搜索" title="用户创建截止日期" style="width: 155px;"/></td> 
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<%-- <!--这个功能现在不要 -->
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="window.location.href='<%=basePath%>/user/listtabUsers.do';" title="切换模式"><i id="nav-search-icon" class="icon-exchange"></i></a></td>
 					--%>
 					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					<c:if test="${QX.edit == 1 }"><td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td></c:if>
					</c:if>
				</tr>
			</table>
			</c:if>
			<!-- 检索  -->
		
			<div style="overflow-x:auto;width: 100%;height: 100%;">
			<table id="table_report" class="table table-striped table-bordered table-hover" >		
				<thead >
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>柜台号</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>系统角色</th>
						<th>邮箱&nbsp;&nbsp;<i class="icon-envelope"></i></th>
						<th>电话号码</th>
						<th>上一级部门</th>
						<th>所属部门</th>
						<th>用户权限(1为最高权限)</th>
						<th>创建时间</th>
						<td>备注</td>
						<!-- <th><i class="icon-time hidden-phone"></i>最近登录</th>
						<th>上次登录IP</th> -->
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty userList}">
 						<c:if test="${QX.cha == 1 }">
 						<c:forEach items="${userList}" var="user" varStatus="vs">							
							<tr>
								<td class='center' >
									<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.EMAIL }" alt="${user.PHONE }"/><span class="lbl"></span></label></c:if>
									<c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" /><span class="lbl"></span></label></c:if>
								</td>
								<td class='center' >${vs.index+1}</td>
								<td>${user.NUMBER }</td>
								<td><a>${user.USERNAME }</a></td>
								<td>${user.NAME }</td>
								<td>${user.ROLE_NAME }</td>
								<c:if test="${QX.FX_QX == 1 }">
								<td><a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" onclick="sendEmail('${user.EMAIL }');">${user.EMAIL }&nbsp;<i class="icon-envelope"></i></a></td>
								</c:if>
								<c:if test="${QX.FX_QX != 1 }">
								<td><a title="您无权发送电子邮件" style="text-decoration:none;cursor:pointer;">${user.EMAIL }&nbsp;<i class="icon-envelope"></i></a></td>
								</c:if>
								<td>${user.PHONE}</td>
								<td>${user.superior_organization_name}</td>
								<td>${user.organization_name}</td>
								<!-- 将对应的信息进行显示出来 -->
								<c:forEach items="${userDepartmentAuthoritys}" var="department">
									<c:if test="${department.authority_Code == user.user_Permission}">
										<td>${user.user_Permission}(${department.authority_Name})</td>
									</c:if>							
								</c:forEach>
								<td>${user.creatuser_Time}</td>								
								<td>${user.BZ}</td>
								<td>
									<div class='hidden-phone visible-desktop btn-group'>										
										<%-- <!-- 暂时取消发送短信的功能 -->
										<c:if test="${QX.FW_QX == 1 }">
										<a class='btn btn-mini btn-warning' title="发送短信" onclick="sendSms('${user.PHONE }');"><i class='icon-envelope'></i></a>
										</c:if> --%>
										
										<!-- 编辑操作的处理 -->
										<c:if test="${QX.edit == 1 }">
											<c:choose>
												<c:when test="${user.approverinfo == 'YES' }"> <!-- 如果是审核人员了，则不允许进行编辑操作 -->
													<c:if test="${user.USERNAME != 'admin'}">
														<a class='btn btn-mini btn-default' title="无法编辑已经是审核人员的用户" style="margin-left: 13px;margin-right: 3px;"><i class='icon-edit'></i></a>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${user.USERNAME == 'admin'}">
														<a class='btn btn-mini btn-default' title="您不能编辑" style="margin-left: 13px;margin-right: 3px;"><i class='icon-edit'></i></a>
													</c:if>
													<c:if test="${user.USERNAME != 'admin'}">
														<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');" style="margin-left: 13px;margin-right: 3px;"><i class='icon-edit'></i></a>
													</c:if>																									
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:choose>
											<c:when test="${user.USERNAME=='admin'}"> <!-- 不允许删除admin这个用户，因为这个作为系统的管理员用户 -->
												    <a class='btn btn-mini btn-danger' title="不能删除" style="margin-left: 3px;"><i class='icon-trash'></i></a>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${user.approverinfo == 'YES' }"> <!-- 如果是审核人员了，则不允许进行删除 -->
														<a class='btn btn-mini btn-default' title="无法删除已经是审核人员的用户" style="margin-left: 3px;"><i class='icon-trash'></i></a>															
													</c:when>
													<c:otherwise>													
														 	<a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.USER_ID }','${user.USERNAME }','${user.NAME}');" style="margin-left: 3px;"><i class='icon-trash'></i></a>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										
										<!-- 添加为审核人员 ,判断该用户是否已经是审核人员，是的话就不能继续添加为审核人员-->
										<c:choose>
											<c:when test="${user.approverinfo == 'YES'}">
													<a  class = 'btn btn-mini btn-default' title="无法重复添加，该用户已经是审核人员" style="margin-left: 3px;"><i class='icon-plus'></i></a>												
											</c:when>
											<c:otherwise>
													<a class = 'btn btn-mini btn-success' title="添加为审核人员" onclick="addApprover('${user.USER_ID }')" style="margin-left: 3px;"><i class='icon-plus'></i></a>												
											</c:otherwise>
										</c:choose>																		
									</div>
								</td>
							</tr>
						
						</c:forEach>
					</c:if> 
						
					<!-- 当系统角色权限是没有查权限的时候，就只能显示当前用户的信息 -->
					<c:if test="${QX.cha == 0 }">
							<tr>
								<c:forEach items="${userList}" var="user" varStatus="vs">
								    <!-- 显示当前用户的用户信息 -->
									<c:if test="${currentUserInfo == user.USER_ID}">
										<tr>
											<td><label><input type='checkbox' /><span class="lbl"></span></label></td>
											<td>1</td>
											<td>${user.NUMBER }</td>
											<td><a>${user.USERNAME }</a></td>
											<td>${user.NAME}</td>
											<td>${user.ROLE_NAME }</td>
											<td>${user.EMAIL }</td>
											<td>${user.superior_organization_name}</td>
											<td>${user.organization_name}</td>
											<c:forEach items="${userDepartmentAuthoritys}" var="department">
											<c:if test="${department.authority_Code == user.user_Permission}">
												<td>${user.user_Permission}(${department.authority_Name})</td>
											</c:if>							
											</c:forEach>
											<td>${user.creatuser_Time}</td>									
											<td>${user.BZ}</td>
											<td style="color: red;">您无权进行操作</td>
										</tr>
									</c:if>
								</c:forEach>								
							</tr>
						</c:if>
						<!--  <td colspan="10" class="center">您无权查看</td> -->
					</c:when>
					
					<c:otherwise>
						<tr class="main_info">
							<td colspan="12" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>			
				</tbody>
			</table>
		</div>
		
		<!-- 当权限是非系统管理员的时候，是不能进行新增，删除那些操作的 -->
		<c:if test="${QX.cha == 1 }">	
		<div class="page-header position-relative">
			<table style="width:100%;margin-top: -15px;">
				<tr>
					<td style="vertical-align:top;float: left;">
						<c:if test="${QX.add == 1 }">
						<a class="btn btn-small btn-success" onclick="add();"><i class="icon-plus"></i>&nbsp;新增用户</a>
						</c:if>
						<%-- 
						<!-- 暂时发送邮件和短信的功能不添加 -->
						<c:if test="${QX.FX_QX == 1 }">
						<a title="批量发送电子邮件" class="btn btn-small btn-info" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="icon-envelope-alt"></i></a>
						</c:if>
						<c:if test="${QX.FW_QX == 1 }">
						<a title="批量发送短信" class="btn btn-small btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="icon-envelope"></i></a>
						</c:if> --%>
						<c:if test="${QX.del == 1 }">
						<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'>&nbsp;批量删除</i></a>
						</c:if>
					</td>
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
		</div>
		</c:if>
	</form>
</div>
 
 
 
 
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
		
		//当搜索框获取到焦点的时候
		$("#nav-search-input").focus(function(){
			$("#nav-search-input").tips({
				side:3,
	            msg:'检索范围列表：从用户名，用户姓名，邮箱，部门，备注列表中进行搜索',
	            bg:'#AE81FF',
	            time:8
	        });	
		});
		
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
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>user/goAddU.do';
			 diag.Width =  800;
			 diag.Height = 465;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 /* if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{ */
						 nextPage(${page.currentPage});
					 /* } */
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
			 diag.Width = 800;
			 diag.Height = 415;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//添加审核人员
		function addApprover(user_id){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag= true;
			diag.Title = "添加为审核人员";
			diag.URL = '<%=basePath%>user/addapprover.do?USER_ID='+user_id;
			diag.width = 200;
			diag.Height = 500;
			diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,msg , name){
			bootbox.confirm("确定要删除用户名为：["+msg+"]----姓名为：["+ name +"]----的用户吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>user/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
						alert("删除成功");
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
			var checkContent = $("#nav-search-input").val();//获取关键子搜索的内容
			var creatuser_Time = $("#creatuser_Time").val(); //获取用户创建检索内容
			var creatuser_endTime = $("#creatuser_endTime").val(); //获取用户创建截止时间检索内容
			var ROLE_ID = $("#role_id").val(); // 获取角色管理检索内容
			var department = $("#user_Permission").val(); //获取用户部门检索内容
			window.location.href='<%=basePath%>user/excel.do?checkContent='+checkContent+'&creatuser_Time='+creatuser_Time+'&creatuser_endTime='+creatuser_endTime+'&ROLE_ID='+ROLE_ID+'&department'+department;
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

