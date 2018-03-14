<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核人员管理</title>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索模块开始  -->
					<form action="asset/asystem_showapprover.do" method="post" name="approversearchform" id="approversearchform">
						<table>
							<tr>
								<td style="vertical-align:top;">
									<span class="input-icon">
										<!-- 这里检索是通过审批人员得姓名来检索 -->
										<input autocomplete="off"  type="text" name="retrieve_content" id="retrieve_content" value="${page.pd.retrieve_content}" placeholder="这里输入关键字检索" title="检索列表范围：审批人序号，姓名，审批人描述信息，审批权限描述信息" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
			
								<td style="vertical-align:top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
								</td>			
							</tr>
						</table>
					</form>
					<!-- 检索模块结束部分  -->
							
				
					<!-- 显示内容控制模块 -->
					<form action="" method="post" name="approverform" id="approverform">						
						<!-- 显示所有的用户信息 -->
						<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: -15px;">
							<thead>
								<tr>
									<th style="width: 120px;"><label><input type="checkbox" id="zcheckbox" onclick="seleteIsAll(this.checked)"/><span class="lbl">全选/全不选</span></label></th>
									<th>序号</th>
									<th>审批人序号</th>
									<th>姓名</th>
									<th>审批人描述信息</th>
									<th>审批权限描述信息</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty listApprover}">
										<!-- 这里先注释，先不管权限 -->
										<%-- <c:if test="${QX.cha == 1 }"> --%>
											<c:forEach items="${listApprover}" var="approver" varStatus="vs">
												<tr>
													<td><label><input type='checkbox' name='ids' value="${approver.user_Id}"/><span class="lbl"></span></label></td>
													<td>${vs.index+1}</td>
													<td>${approver.approver_Id}</td>
													<td>${approver.user_Name}</td>
													<td>${approver.approver_Description}</td>
													<td>${approver.approver_Rights_Description}</td>
													<td style="width:40px;">
													<div class="hidden-phone visible-desktop btn-group">
														<a class='btn btn-mini btn-info' title="编辑" onclick="editApprover('${approver.user_Id}');"><i class='icon-edit'></i>编辑</a>														
														<a class='btn btn-mini btn-danger' title="删除" data-toggle="modal" data-target="#modal01" name="${approver.user_Id}" id="deletbtnlianjie" ><i class='icon-trash'></i>删除</a>
													</div>													
													</td>								
												</tr>										
											</c:forEach>							
										<%-- </c:if> --%>
										<!-- 先注释这里，不管显示权限的问题 -->
										<%-- <c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="10" class="center">您无权查看</td>
											</tr>
										</c:if> --%>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="12" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody> 
						</table>				
						<!-- 页面最下面内容 -->	
					</form>
				</div>
				<!-- 底部操作区域 -->
				<div class="page-header position-relative" style="margin-top: -5px;">
					<table style="width:100%;">
					<tr>
						<td style="vertical-align:top;">
							<a class="btn btn-small btn-success" onclick="addOne();"><i class="icon-plus">&nbsp;新增审批人员</i></a>				
							<a class="btn btn-small btn-danger" data-toggle="modal" data-target="#modal02" ><i class="icon-trash">&nbsp;批量删除审批人员</i></a>				
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
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			
			<!--/row-->
		</div>
		<!--/#page-content-->
	</div>
	
	<!-- -------模态框内容---- -->
	<!-- 单个删除数据 -->
	<div class="modal fade" id="modal01">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 style="color: red"><i class="icon-question-sign"></i>友情提示</h4>
				</div>
				<div class="modal-body">
                     <strong style="font-size: 16px;">您确定要删除所选择的数据信息吗？</strong>                 
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary"  onclick="delApprover();">确定</button>
					<button class="btn btn-warning" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
    	
    <!-- 小模态框2---批量助教信息 -->
	<div class="modal fade" id="modal02">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 style="color: red"><i class="icon-question-sign"></i>友情提示</h4>
				</div>
				<div class="modal-body">
                    <strong style="font-size: 16px;">您确定要所选择的数据信息吗？</strong>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" data-dismiss="modal" onclick="deleteAll();">确定</button>
					<button class="btn btn-warning" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- --------模态框处理结束部分------------ -->	
	
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<!-- 设置表格居中 -->
	<style type="text/css">
		#table_report thead tr th{
			text-align: center;
		}
		#table_report tbody tr td{
			text-align: center;
		}
	</style>
	<link rel="stylesheet" href="static/css/searchInputStyle.css" type="text/css" />
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());
		
		//当搜索框获取到焦点的时候
		$("#retrieve_content").hover(function(){
			$("#retrieve_content").tips({
				side:3,
	            msg:'检索列表范围：审批人序号，姓名，审批人描述信息，审批权限描述信息',
	            bg:'#AE81FF',
	            time:3
	        });	
		});
						
		
		//判断添加审核人员操作的结果
		var addResult = '${addresult}';
		if(addResult == "success"){
			alert("添加成功");
		}
		else if(addResult == "fail"){
			alert("该用户已经是审核人员，请勿重复添加");
		}
		
				
		//判断批量删除审批人员结果
		var deleteResult = '${deleteresult}';
		var deleresultnumber = '${deleresultnumber}';
		if(deleteResult == "success"){
			var result = new Array();
			result = deleresultnumber.split("@");
			alert("选择删除总条数为："+result[0]+"\n删除成功的条数为："+result[1]+"\n其中无法删除的条数为(由于已经处于审批节点中)："+(result[0]-result[1]));
		}	
		
		//删除审核人操作
		//获取进行删除的内容的用户的ID
		 var deletNumber = 0 ; 
		 $(document).on('click','#deletbtnlianjie',function () {
		        deletNumber = $(this).attr('name');  //获取点击删除的对应数据的userid
		 })
		function delApprover(){
			 var user_id = deletNumber;
			 //判断是否能够进行删除，如果当前节点是项目审批过程中的审批流程中的，那么就不能进行删除该节点
			 $.ajax({
				url:'${pageContext.request.contextPath}/asset/asystem_currentapproverdeleteStatus',
				async:false,
				data:{"user_id":user_id},
				type:'POST',
				success:function(data){
					if(data.deleteResult =="YES"){						
						window.location = '${pageContext.request.contextPath}/asset/asystem_deleteApproverSimple?user_id='+user_id;
					}
					else if(data.deleteResult =="NO"){
						alert("该审批人员信息存在于审批节点数据条目中，无法进行删除，请确认!!");
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"
			});		 
			
			//下面的代码废弃掉，用上面的方法进行解决
			<%-- bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>asset/asystem_delapprover.do?USER_ID="+user_id;
					$.get(url,function(data){							
						// nextPage(${page.currentPage});
						window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover";
					});
				}
			}); --%>
		}
		
		//修改审核人操作
		function editApprover(user_id){
			//先判断需要修改的审批人是否已经在使用中的审批节点中存在，如果存在则不能进行修改操作
			$.ajax({
			url:'${pageContext.request.contextPath}/asset/asystem_currentapproverdeleteStatus',
			async:false,
			data:{"user_id":user_id},
			type:'POST',
			success:function(data){
				if(data.deleteResult =="YES"){						
					top.jzts();
					var diag = new top.Dialog();
					diag.Drag= true;
					diag.Title = "修改审核人员信息";
					diag.URL = '<%=basePath%>asset/asystem_showeditapprover.do?USER_ID='+user_id;
					diag.width = 200;
					diag.Height = 500;
					diag.CancelEvent = function(){ //关闭事件
						 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
							 window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover"; 
						}
						diag.close();
					 };
					 diag.show();					
				}
				else if(data.deleteResult =="NO"){
					alert("审批节点数据条目中，存在使用该审批人员信息，无法进行编辑操作，请确认!!");
				}
			},
			error:function(){
				alert("网络出现问题，请稍后再试");
			},
			dataType:"json"
		});		
		}
		
		
		//全选和全不选的控制
	    function seleteIsAll(flag) {
	  		var checkselecte = document.getElementsByName("ids");
	  		for(var i = 0 ;i<checkselecte.length;i++){
	  			checkselecte[i].checked = flag;
	  	    }
	    }
		
		//进行批量删除的操作
		function deleteAll(){
			//(1)首先判断是否有选中需要删除的，如果为空，不判断的话后台是会报null错
			if($("input[name='ids']:checked").length <=0){
				alert("没有选中任何一个数据进行删除");
			}
			else{
				//(2)发送请求到后台
				$('#approverform').attr({action:'asset/asystem_deletebatchapprover.do'});
				$('#approverform').submit();
			}
		}
		//处理添加审核人员的操作
		function addOne(){
			//跳转到处理审批人员添加的后台在AssertApproerManageController进行管理
			window.location = "${pageContext.request.contextPath}/asset/saveapprover";
		}
		
		//处理姓名检索的功能
		function search(){
			//提交检索功能
			$('#approversearchform').submit();
		}
		
		
		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	
			//(3)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));
				/* $.ajax({
						url:'${pageContext.request.contextPath}/asset/asystem_showapprover',
						data:{"currentPage":clickpage},
						async:true,
						type:"POST",
						success:function(data){
		
						},
						error:function(){
							alert("切换失败！请稍后重试！");
						},
						dataType:"json"
					}); */	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	//后面要注意编码，因为如果是中文的话，这样传会发生乱码的，那么搜索肯定就匹配不到内容
			window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	//后面要注意编码，因为如果是中文的话，这样传会发生乱码的，那么搜索肯定就匹配不到内容
			window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));
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