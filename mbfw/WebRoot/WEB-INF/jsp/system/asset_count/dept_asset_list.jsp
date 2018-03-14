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
			<!-- 检索  -->
			<form action="asset/dept_asset_list.do" method="post" name="assetForm" id="assetForm" style="margin:0">
			<table>
				<tr>
					<input type="hidden" name="asset_use_dept" value="${asset_use_dept}" />
					<td style="vertical-align:top;"><input name="condition"  value="${condition}" type="text" style="width:200px;" placeholder="资产类型 | 使用者 | 资产编码" /></td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" title="查询" style="margin-left:3px;"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align:top;"><a href="${pageContext.request.contextPath}/asset/asset_count_table.do?type=${type}" class="btn btn-mini btn-info" style="margin-left:5px;width:50px;">返回</a></td>
				</tr>
			</table>
			</form>
			<!-- 检索  -->
			
			<div style="overflow-x:auto;width: 100%;height: 100%;">
			<table id="table_report" class="table table-striped table-bordered table-hover">		
				<thead>
					<tr>
						<th>序号</th>
						<th>资产编号</th>
						<th>资产名称</th>
						<th>类型</th>
						<th>所在部门</th>
						<th>使用人</th>
						<th>状态</th>
						<th>价格</th>
						<!-- <th class="center">操作</th> -->
					</tr>
				</thead>						
				<tbody>
				<c:forEach items="${list}" var="item" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.assetGet.asset_code}</td>
						<td>${item.asset_name}</td>
						<td>${item.asset_class}</td>
						<td>${item.assetGet.asset_use_dept}</td>
						<td>${item.assetGet.asset_user}</td>
						<td>${item.asset_status}</td>
						<td>${item.asset_price}</td>
						<!-- <td>疯狂操作</td> -->
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page-header position-relative">
					<table style="width: 100%;">
						<tr>
							<!-- 分页内容的处理部分 -->
							<td style="vertical-align: top;">
								<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">
									<ul>
										<li><a>共<font color="red">${page.totalResult}</font>条</a></li>
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
												<option value='1'>1</option>
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
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
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
		function changePage(clickpage){
			var url = "${pageContext.request.contextPath}/asset/dept_asset_list.do?currentPage="+clickpage+"&showCount="+${page.showCount};
			$("#assetForm").attr("action",url);
			$("#assetForm").submit();
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){	
			var url = "${pageContext.request.contextPath}/asset/dept_asset_list.do?currentPage="+clickpage+"&showCount="+${page.showCount};
			$("#assetForm").attr("action",url);
			$("#assetForm").submit();
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			var url = "${pageContext.request.contextPath}/asset/dept_asset_list.do?currentPage="+0+"&showCount="+pagesize;	
			$("#assetForm").attr("action",url);
			$("#assetForm").submit();
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

