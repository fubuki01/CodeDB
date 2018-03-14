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

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
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
}
</style>

</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<c:if test="${QX.cha == 1 }">
			<div class="row-fluid">
				<!-- 操作按钮显示区域 -->
				<table>
					<tr>
						<!-- 检索 form表单提交  -->
						<form action="asset/check_list.do" method="post" name="userForm" id="userForm">						
							<td><span class="input-icon"> 
								<input autocomplete="off" id="nav-search-input" type="text"
										value="${retrieve_content }" name="retrieve_content" placeholder="这里输入关键词" 
										title="关键字可以为“盘点单名称”、“创建人”、“创建时间”、“盘点完成度”"/> 
										<i id="nav-search-icon" class="icon-search"></i>
							</span></td>

							<td style="vertical-align: top;"><button
									class="btn btn-mini btn-light" onclick="return search();"
									title="检索">
									<i id="nav-search-icon" class="icon-search"></i>
								</button></td>
						</form>
						
						
						<td style="vertical-align: top;">
							<!-- 增加 -->
							<c:if test="${QX.add == 1 }">
								<button class="btn btn-small btn-primary" id="btn_creat"
								onclick="btn_creat();">新增</button> 
							</c:if>							
							<!-- 查看 -->
							<c:if test="${QX.cha == 1 }">
								<button class="btn btn-small btn-info" id="btn_post"
								onclick="return btn_check();">查看</button>
							</c:if>
							<!-- 删除 -->
							<c:if test="${QX.del == 1 }">
								<button class="btn btn-small btn-danger" id="btn_delete"
								onclick="return btn_delete('确定要删除选中的数据吗?');">删除</button>
							</c:if>
						</td>
					
					</tr>
				</table>

				<!-- 查询列表显示区域 -->
				<table id="table_project_approval"
					class="table text-table able-striped table-bordered table-hover">

					<thead>
						<tr>
							<th><label><input type="checkbox"
									id="zcheckbox"
									onclick="project_zhuan(this.checked);"><span
									class="lbl"></span></label></th>
							<th>编号</th>
							<th>盘点单名称</th>
							<th>创建人</th>
							<th>创建时间</th>
							<th>盘点完成度</th>						
						</tr>
					</thead>

					<tbody>
						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty listCheck}">
								<c:forEach items="${listCheck}" var="ac" varStatus="vs">

									<tr>
										<td style="width: 30px;"><label><input
												type='checkbox' name='ids' value="${ac.id }"
												id="${ac.id }" /><span class="lbl"></span></label></td>
										<td>${vs.index+1}</td>
										<td><a href="asset/asset_check.do?pm=${ac.check_name }">${ac.check_name }</a></td>
										<td>${ac.build_name }</td>
										<td>${ac.build_time }</td>
										<td style="color:blue;">${ac.checkNumber}/${ac.totalNumber}</td>
									</tr>

								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="6">没有相关数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>


				<!-- 底部操作区域 -->
				<div class="page-header position-relative">
					<table style="width: 100%;">
						<tr>
							<!-- 分页内容的处理部分 -->
							<td style="vertical-align: top;">
								<div class="pagination"
									style="float: right; padding-top: 0px; margin-top: 0px;">
									<ul>
										<li><a>共<font color=red>${page.totalResult}</font>条
										</a></li>
										<li><input type="number" value="" id="jumpPageNumber"
											style="width: 50px; text-align: center; float: left"
											placeholder="页码" /></li>
										<li style="cursor: pointer;"><a onclick="jumpPage();"
											class="btn btn-mini btn-success">跳转</a></li>
										<c:choose>
											<c:when test="${page.currentPage == 1 }">
												<li><a>首页</a></li>
												<li><a>上页</a></li>
												<!-- 分是否为第一页的两种情况，不为第一页的话，那么就要设置首页和上一页为有onclick点击事件 -->
											</c:when>
											<c:otherwise>
												<li style="cursor: pointer;"><a onclick="nextPage(1);">首页</a></li>
												<li style="cursor: pointer;"><a
													onclick="nextPage(${page.currentPage}-1);">上页</a></li>
											</c:otherwise>
										</c:choose>
										<!-- 分页处理的优化工作 -->
										<c:choose>
											<c:when test="${page.currentPage + 4 > page.totalPage}">
												<!-- 现在每个分页为显示5页 ，所以先判断当前的页面+4是否大于总的页面数-->
												<c:choose>
													<c:when test="${page.totalPage-4 > 0}">
														<!-- 判断是否总的页面数也是大于5，因为这样的话，就会出现两种情况 -->
														<c:forEach var="index1" begin="${page.totalPage-4}"
															end="${page.totalPage}" step="1">
															<c:if test="${index1 >= 1}">
																<c:choose>
																	<c:when test="${page.currentPage == index1}">
																		<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																	</c:when>
																	<c:otherwise>
																		<li style="cursor: pointer;"><a
																			onclick="changePage(${index1});">${index1}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</c:forEach>
													</c:when>

													<c:otherwise>
														<!-- 当总的页面数都不足5的时候，那么直接全部显示即可，不需要考虑太多 -->
														<c:forEach var="pagenumber" begin="1"
															end="${page.totalPage}">
															<!-- 判断页码是否是当前页，是的话，就换个颜色来标记 -->
															<c:choose>
																<c:when test="${page.currentPage == pagenumber}">
																	<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																</c:when>
																<c:otherwise>
																	<li style="cursor: pointer;"><a
																		onclick="changePage(${pagenumber});">${pagenumber}</a></li>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:otherwise>
												</c:choose>

											</c:when>

											<c:otherwise>
												<!-- 当当前页面数+4都还是小于总的页面数的情况 -->
												<c:choose>
													<c:when test="${page.currentPage != 1}">
														<!-- 判断当前页面是否就是第一页，因为这样也会有两种情况的处理 -->
														<c:forEach var="index2" begin="${page.currentPage-1}"
															end="${page.currentPage+3}">
															<!-- 从当前页面减一的页面数开始，这样点击前面一页就会显示其他的页面，从而实现页面跳转 -->
															<c:choose>
																<c:when test="${page.currentPage == index2}">
																	<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																</c:when>
																<c:otherwise>
																	<li style="cursor: pointer;"><a
																		onclick="changePage(${index2});">${index2}</a></li>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>

													<c:otherwise>
														<!-- 当当前页面数就是第一页的时候，就直接显示1-5页即可 -->
														<c:forEach var="index3" begin="1" end="5">
															<c:choose>
																<c:when test="${page.currentPage == index3}">
																	<li><a><b style="color: red;">${page.currentPage}</b></a></li>
																</c:when>
																<c:otherwise>
																	<li style="cursor: pointer;"><a
																		onclick="changePage(${index3});">${index3}</a></li>
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
												<li style="cursor: pointer;"><a
													onclick="nextPage(${page.currentPage}+1)">下页</a></li>
												<li style="cursor: pointer;"><a
													onclick="nextPage(${page.totalPage});">尾页</a></li>
											</c:otherwise>
										</c:choose>

										<!-- 处理 页面大小的处理-->
										<li><a>第${page.currentPage}页</a></li>
										<li><a>共${page.totalPage}页</a></li>
										<!-- 进行每一个页面显示数据条目大小的选择 -->
										<li>
											<!-- 注意：当进行选择改变的时候，就会出发changeCount这个事件，再通过Ajax进行页面数据的请求 --> <select
											title='显示条数' style="width: 55px; float: left;"
											onchange="changeCount(this.value)">
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
			</div>
			</c:if>
			<c:if test="${QX.cha == 0 }">
				<div><label><b style="color: red"><h2>抱歉，您没有此页面的操作权限！</h2></b></label></div>
			</c:if>
		</div>
	</div>

	<!-- JS代码区，写在后面是为了提高运行效率 -->
	<script type="text/javascript">
		$(top.hangge());
		
		//判断项目增加是否成功
		var saveResult = '${saveresult}';
		if(saveResult == "success"){
			var txt=  "增加成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		if(saveResult == "false"){
			var txt=  "查询结果为空，增加失败！";
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
		
		
		//增加项目立项
		function btn_creat() {
			window.location = '${pageContext.request.contextPath}/asset/goAddCheck.do'; 
		}
		
		
		//全选和全不选事件监听
		function project_zhuan(flag) {
			var select_state_projects = document.getElementsByName("ids");
			for (var i = 0; i < select_state_projects.length; i++) {
				var select = select_state_projects[i];
				select.checked = flag;
			}
		} 
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/check_list?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/asset/check_list?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/asset/check_list?currentPage="+${page.currentPage}+"&showCount="+pagesize;
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
		
		//删除选中的项目
		function btn_delete(msg) {
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
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
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
								url: '<%=basePath%>asset/delete_CheckList.do',
						    	data: {id:str},
								dataType:'json',
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										//nextPage(${page.currentPage});
										 window.location = "${pageContext.request.contextPath}/asset/check_list";	
									 });
								}
							});
						}
						
					}
				}
			});
		}
		
		
	
		//查看选中的项目
		function btn_check() {
			
			var select_names = new Array();
			var j=0;
			var projects = document.getElementsByName("ids");
			for(var i = 0; i < projects.length; i++){
				var select = projects[i];
				if(select.checked == true){
					select_names[j] = select.value;
					j++;
				}
			}
			if(select_names.length==0){
				var txt=  "请选择要查看的项目！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				return false;
			}else if(select_names.length>1){
				var txt=  "不能一次查看多个项目，请重新选择！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				//如果选择的总按钮为ture 把他改为false
				var check_parent = document.getElementById('zcheckbox');
				if(check_parent.checked==true){
					check_parent.checked=false;
				}
				
				//把其他选中的按钮的状态变为false
				for(var i = 0; i < projects.length; i++){
					var select = projects[i];
					if(select.checked == true){
						select.checked=false;
					}
				}
				return false;
			}else{
				 var number = select_names[0];
				 var state = $('#'+number).parent().parent().next().next().children().html();
				 var temp = $("#nav-search-input").val();
				 window.location = '${pageContext.request.contextPath}/asset/asset_check.do?pm='+state; 				 
				return true;
			}
			
		}
		
		
		//关键字查询
		function search(){
			var key_name = $("#nav-search-input").val();
			if(key_name==""){
				$("#nav-search-input").tips({
					side : 3,
					msg : '请输入关键字',
					bg : '#AE81FF',
					time : 3
				});
				$("#nav-search-input").focus();
				return false;
			}
		}
		
		
		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			//判断是有无检索内容决定跳转的URL
			var temp = $("#nav-search-input").val();
			/* if(temp!=''){
				window.location = "${pageContext.request.contextPath}/asset/atp_project_search?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			}else{ */
				window.location = "${pageContext.request.contextPath}/asset/atp_showForm?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
		/* 	} */
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			var temp = $("#nav-search-input").val();
				window.location = "${pageContext.request.contextPath}/asset/atp_showForm?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			var temp = $("#nav-search-input").val();
			/* if(temp!=''){
				window.location = "${pageContext.request.contextPath}/asset/atp_project_search?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			}else{ */
				window.location = "${pageContext.request.contextPath}/asset/atp_showForm?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&key="+encodeURI(encodeURI(temp));
			/* } */
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