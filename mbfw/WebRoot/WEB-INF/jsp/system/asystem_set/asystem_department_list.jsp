<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>部门设置</title>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
 <script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
</head>

<body>
	<div class="container-fluid" id="main-container" >
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid" id="nodeContentdiv">		
					<!-- 检索模块开始  -->
					<form action="asset/asystem_showallnode.do" method="post" name="approversearchform" id="approversearchform">
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<!-- 这里检索是通过审批人员得姓名来检索 -->
										<input autocomplete="off" id="nav-search-input" type="text" name="retrieve_content" id="retrieve_content" value="${page.pd.retrieve_content}" placeholder="这里输入关键字检索" />
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
				
				<!-- 显示所有的信息 -->		
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center"><label><input type="checkbox" id="zcheckbox"><span class="lbl"></span></label></th>
							<th>序号</th>
							<th>代码</th>
							<th>部门</th>
							<th>上级</th>
							<th>级别</th>
							<th>负责人</th>
							<th>机构类型</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
						<!-- 开始循环 -->
						<tr>
							<td class="center" style="width: 30px;"><label><input
									type="checkbox" name="ids" ><span class="lbl"></span></label></td>
							<td class="center" style="width: 30px;">4</td>
							<td>13250114</td>
							<td>长沙_销售部</td>
							<td>xx股份有限公司</td>
							<td>2</td>
							<td>小红</td>
							<td>销售</td>
							<td style="width: 60px;">
								<div class="hidden-phone visible-desktop btn-group">
		
									<a class="btn btn-mini btn-info" title="编辑" onClick="edit();"><i
										class="icon-edit"></i>编辑</a>

									<a class="btn btn-mini btn-danger" title="删除"><i
										class="icon-trash"></i>删除</a>
		
								</div>
							</td>
						</tr>
					</tbody>
				</table>
		
				<!------------------ 底部内容--------------- -->
				<div class="page-header position-relative">
					<table style="width: 100%;">
						<tbody>
							<tr>
								<td style="vertical-align: top;"><a
									class="btn btn-small btn-success" onclick="addDepartment();">新增</a>
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
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

	<!-- basic scripts -->
	<script type="text/javascript">
		var addresult = '${addresult}';
		if(addresult == "success"){
			alert("添加成功");
		}
		$(top.hangge());
		
		
		//新部分按钮的处理
		function addDepartment(){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="添加部门";
			 diag.URL = '<%=basePath%>asset/asystem_departmentsavedialog.do';
			 diag.Width = 850;
			 diag.Height = 360;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 setTimeout("self.location=self.location",100);
					 /* if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 } */
				}
				diag.close();
			 };
			 diag.show();		
		}

		
		
		/* //处理姓名检索的功能
		function search(){
			//提交检索功能
			$('#approversearchform').submit();
		}
		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+clickpage+"&showCount="+${page.showCount};	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+${page.currentPage}+"&showCount="+pagesize;
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
		} */
		
	</script>

</body>
</html>