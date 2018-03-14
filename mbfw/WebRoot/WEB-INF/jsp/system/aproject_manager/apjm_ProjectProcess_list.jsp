<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<!-- 设置表格居中 -->
<style type="text/css">
	#table_report thead tr th{
		text-align: center;
	}
	#table_report tbody tr td{
		text-align: center;
	}
</style>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
						<!-- 检索模块开始  -->
						<form action="asset/atp_approvalprojectlist.do" method="post" name="approversearchform" id="approversearchform">
							<table>
								<tr>
									<td>
										<span class="input-icon">
											<!-- 这里检索是内容检索 -->
											<input autocomplete="off"  type="text" name="retrieve_content" id="retrieve_content" value="${page.pd.retrieve_content}" placeholder="这里输入关键字检索" />
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
				
					<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: -12px;">
							<thead>
								<tr>
									<th class="center"><label><input type="checkbox" id="zcheckbox"><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>审批项目名</th>	
									<th>审批项目流程名</th>								
									<th>当前审批节点</th>
									<th>审批项目类型</th>
									<th>是否审批完成</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>
								<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty listProject}">			
								<c:forEach items="${listProject}" var="projectitem" varStatus="itemindex">
									<tr>
										<td class="center" style="width: 30px;">
										<label>
											<input type="checkbox" name="ids">
											<span class="lbl"></span>
										</label>
										</td>
										<td>${itemindex.index+1}</td>
										<td>${projectitem.project_Name}</td>
										<td>${projectitem.process_ApprovalName}</td>
										<td>${projectitem.current_NodeName}</td>
										<td>${projectitem.process_Type}</td>
										<td style="color: red;">${projectitem.process_FinishStatus}</td>
										<td>
											<a  class="btn btn-mini btn-info" title="审批" onClick="lookCurrentProject('${projectitem.projectprocess_Id}');">
											<i class="icon-edit"></i>审批</a> 							
										</td>
									</tr>
								</c:forEach>	
								</c:when>
								<c:otherwise>
									<tr class="main_info">
									<td colspan="15" class="center">暂时没有数据</td>
									</tr>							
								</c:otherwise>
								</c:choose>							
							</tbody>
						</table>
					</form>
					
				<!-- 底部操作区域 -->
			<div class="page-header position-relative">
				<table style="width:100%;">
				<tr>
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
									<%-- <!-- 循环遍历产生页码数目的数字显示-->
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
									</c:forEach>  --%>
									
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
			</div>
		</div>
	</div>

	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
	<!-- 引入 -->
	<link rel="stylesheet" href="static/css/searchInputStyle.css" type="text/css" />	
	<script type="text/javascript">window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="static/js/myjs/menusf.js"></script>
	<script type="text/javascript" src="static/js/myjs/index.js"></script>

	<script type="text/javascript">		
		$(top.hangge());
		
		//当搜索框获取到焦点的时候
		$("#retrieve_content").hover(function(){
			$("#retrieve_content").tips({
				side:3,
	            msg:'检索列表范围：审批项目名；审批项目流程名；当前审批节点；审批项目类型；是否审批完成',
	            bg:'#AE81FF',
	            time:3
	        });	
		});
		
		//处理点击审批按钮的操作
		function lookCurrentProject(projectId){
			//判断当前的这个用户是否能够进行审批操作（即当前用户的id是否是属于点击流程当前节点中的审批人员）
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/atp_editjugdepermission',
				async:false,
				data:{"projectprocess_Id":projectId},
				type:'POST',
				success:function(data){
					 if(data =="YES"){	//是当前审批人员	
						window.location = "${pageContext.request.contextPath}/asset/atp_editcurrentprojectprocess?projectprocess_Id="+projectId;
					}
					else if(data == "NO"){
						alert("不属于当前审批节点中的审批人员，无法进行审批操作，请确认！");
					} 
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"text"
			});
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
			window.location = "${pageContext.request.contextPath}/asset/atp_approvalprojectlist?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	//后面要注意编码，因为如果是中文的话，这样传会发生乱码的，那么搜索肯定就匹配不到内容
			window.location = "${pageContext.request.contextPath}/asset/atp_approvalprojectlist?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+encodeURI(encodeURI(retrieve_content));
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	
			window.location = "${pageContext.request.contextPath}/asset/atp_approvalprojectlist?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&retrieve_content="+encodeURI(encodeURI(retrieve_content));
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