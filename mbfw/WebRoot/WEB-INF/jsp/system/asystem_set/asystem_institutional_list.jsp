<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>机构信息</title>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>

<!-- confirm提示框 -->
<link rel="stylesheet" type="text/css"
	href="static/js/myconfirm/css/xcConfirm.css" />
<!-- <script src="static/js/myconfirm/js/jquery-1.9.1.js"
	type="text/javascript" charset="utf-8"></script> -->
<script src="static/js/myconfirm/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>
<!-- 悬浮信息提示 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>


<!-- 内容居中显示 -->
<style type="text/css">
.clearfix table{
         empty-cells:show;
         margin:20 auto;
}
.clearfix table tr th {
	text-align: center;
	/* white-space: nowrap; */
}

.clearfix table tr td {
	text-align: center;
	white-space: nowrap;
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
						<form action="asset/asystem_institutional_search.do" method="post">
							<!-- title="可以输入“业务机构代码”、“组织简称”、“组织类别”" -->
							<td><span class="input-icon"> <input
									autocomplete="off" id="nav-search-input" type="text" name="key"
									value="${key }" placeholder="这里输入关键词" /> <i
									id="nav-search-icon" class="icon-search"></i>
							</span></td>



							<td style="vertical-align: top;"><button id = "bt_search"
									class="btn btn-mini btn-light" onclick="return search();"
									title="检索">
									<i id="nav-search-icon" class="icon-search"></i>
								</button></td>
						</form>



						<td style="vertical-align: top;">
							<!-- 增加 -->
							<c:if test="${QX.add == 1 }">
								<button class="btn btn-small btn-primary" id="btn_creat" 
									onclick="btn_creat();">增加</button>
							</c:if>
							<!-- 查看 -->
							<c:if test="${QX.cha == 1 }">
								<button class="btn btn-small btn-info" id="btn_check"
									onclick="return btn_check();">查看</button> 
							</c:if>
							<!-- 修改 -->
							<c:if test="${QX.edit == 1 }">
								<button class="btn btn-small btn-warning" id="btn_modify"
								onclick="return btn_modify();">修改</button>
							</c:if>
							 <!-- 删除 -->
							<c:if test="${QX.del == 1 }">
								<button class="btn btn-small btn-danger" id="btn_delete"
									onclick="return btn_delete();">删除</button> 
							</c:if>
						</td>
					</tr>
				</table>

				<!-- 查询列表显示区域 table-condensed-->
				<table
					class="table table-striped table-bordered table-hover">

					<thead>
						<tr>
							<th class="center"><label><input type="checkbox"
									id="project_approval_checkbox"
									onclick="project_zhuan(this.checked);"><span
									class="lbl"></span></label></th>
							<th class="center">编号</th>
							<th class="center">业务机构代码</th>
							<th class="center">组织名称</th>
							<th class="center">组织简称</th>
							<th class="center">上级组织名称</th>
							<th class="center">业务机构性质</th>
							<th class="center">组织类别</th>
							<th class="center">组织级别</th>
							<th class="center">启用标示</th>
							<th class="center">报表权限机构代码</th>
							<th class="center">业务机构标示</th>
							<th class="center">业务机构类型</th>
						</tr>
					</thead>

					<tbody>
						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty institutionList}">
								<c:forEach items="${institutionList}" var="institution"
									varStatus="vs">

									<tr>
										<td class='center' style="width: 30px;"><label><input
												type='checkbox' name='ids'
												value="${institution.organizational_identification }"
												id="${project.apply_id}" /><span class="lbl"></span></label></td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class='center' style="color: red;">${institution.business_organization_code }</td>
										<td class='center'>${institution.organization_name }</td>
										<td class='center'>${institution.organization_abbreviation }</td>
										<td class='center'>${institution.superior_organization_name }</td>
										<td class='center'>${institution.business_organization_nature }</td>
										<td class='center'>${institution.organization_class}</td>
										<td class='center'>${institution.organizational_level}</td>
										<td class='center'>${institution.enable_identification }</td>
										<td class='center'>${institution.report_authority_organization_code }</td>
										<td class='center'>${institution.business_organization_identifier}</td>
										<td class='center'>${institution.business_organization_type}</td>
									</tr>

								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:if test="${permission==1||permission==2 }">
									<tr class="main_info">
										<td colspan="15" class="center">没有相关数据。</td>
									</tr>
								</c:if>
								<c:if test="${permission==3 }">
									<tr class="main_info">
										<td colspan="15" class="center">抱歉，您的权限过低。</td>
									</tr>
								</c:if>
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
		
		//当搜索框获取到焦点的时候
		$("#nav-search-input").hover(function(){
			$("#nav-search-input").tips({
				side:3,
	            msg:'检索列表范围：业务机构代码、组织简称、组织类别',
	            bg:'#AE81FF',
	            time:3
	        });	
		});
		
		
		//获取用户的权限
		var permission = '${permission}';
		if(permission==3){
			$("#btn_creat").attr("disabled","disabled");
			$("#btn_check").attr("disabled","disabled");
			$("#btn_modify").attr("disabled","disabled");
			$("#btn_delete").attr("disabled","disabled");
			$("#bt_search").attr("disabled","disabled");
		}
		
		
		//判断增加是否成功
		var saveResult = '${saveresult}';
		if(saveResult == "success"){
			var txt=  "增加成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
				
		//判断修改是否成功
		var updateResult = '${updateresult}';	
		if(updateResult == "success"){
			var txt=  "修改成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		

		//判断删除是否成功
		var delectResult = '${delectresult}';	
		if(delectResult == "success"){
			var txt=  "删除成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}else if(delectResult =="fail"){
			var txt=  "包含正在被使用的机构，删除失败！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		
		 
		
		//增加机构
		function btn_creat() {
			window.location = '${pageContext.request.contextPath}/asset/asystem_institutional_add.do'; 
			 <%-- top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增机构";
			 diag.URL = '<%=basePath%>asset/asystem_institutional_add.do';
			 diag.Width = 900;
			 diag.Height = 480;
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
			 
			diag.show(); --%>
		}
		
		
		//全选和全不选事件监听
		function project_zhuan(flag) {
			var select_state_projects = document.getElementsByName("ids");
			for (var i = 0; i < select_state_projects.length; i++) {
				var select = select_state_projects[i];
				select.checked = flag;
			}
		} 
		
		//删除选中的机构
		function btn_delete() {
			//创建数组用于存放选中的name
		    var select_names = new Array();
			var str=null;//用于存放参数
			var j = 0;//用于选中的数量
			var projects = document.getElementsByName("ids");
			for(var i = 0; i < projects.length; i++){
				var select = projects[i];
				if(select.checked == true){
					select_names[j] = select.value;
					j++;
					str = str+"@"+select.value;
				}
			}
			if(select_names.length == 0){
				var txt=  "请先选择要删除的条目！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				return false;
			}else{
				/* confirm("确实要删除选中的项目吗？") */
				var txt=  "确实要删除选中的条目吗？";
				var temp = $("#nav-search-input").val();
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.confirm,1,function(){
					window.location = '${pageContext.request.contextPath}/asset/asystem_institutional_delect.do?info='+str+"&key="+encodeURI(encodeURI(temp));
					return true;
				});
				
			}
		}
		
		//修改选中的机构
		function btn_modify() {
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
				var txt=  "请选择要修改的条目！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				return false;
			}else if(select_names.length>1){
				var txt=  "不能一次修改多个条目，请重新选择！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				//如果选择的总按钮为ture 把他改为false
				var check_parent = document.getElementById('project_approval_checkbox');
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
			}
			else{
				var number = select_names[0];
				var state = $('#'+number).parent().parent().next().next().html();
				var temp = $("#nav-search-input").val();
				var txt=  "确定要修改选中的条目吗？";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.confirm,1,function(){
					window.location = '${pageContext.request.contextPath}/asset/asystem_institutional_modify.do?info='+number+"&key="+encodeURI(encodeURI(temp)); 
					<%-- top.jzts();
					var diag = new top.Dialog();
					diag.Drag=true;
					diag.Title ="修改机构信息";
					diag.URL = '<%=basePath%>asset/asystem_institutional_modify.do?info='+number;
					diag.Width = 900;
					diag.Height = 480;//530
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
						diag.show(); --%>
						return true;
					});
				}
		}
		
		//查看选中的条目
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
				var txt=  "请选择要查看的条目！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				return false;
			}else if(select_names.length>1){
				var txt=  "不能一次查看多个条目，请重新选择！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				//如果选择的总按钮为ture 把他改为false
				var check_parent = document.getElementById('project_approval_checkbox');
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
				 var temp = $("#nav-search-input").val();
				 window.location = '${pageContext.request.contextPath}/asset/asystem_institutional_check.do?info='+number+"&key="+encodeURI(encodeURI(temp)); 
				 <%-- top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="查看机构信息";
				 diag.URL = '<%=basePath%>asset/asystem_institutional_check.do?info='+number;
				 diag.Width = 900;
				 diag.Height = 480;
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
				diag.show(); --%>
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
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutional_search?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			}else{ */
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutionalinfoshow?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			/* } */
			
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			var temp = $("#nav-search-input").val();
			/* if(temp!=''){
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutional_search?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			}else{ */
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutionalinfoshow?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));
			/* } */
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			var temp = $("#nav-search-input").val();
			/* if(temp!=''){
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutional_search?currentPage="+clickpage+"&showCount="+${page.showCount}+"&key="+encodeURI(encodeURI(temp));	
			}else{ */
				window.location = "${pageContext.request.contextPath}/asset/asystem_institutionalinfoshow?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&key="+encodeURI(encodeURI(temp));
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