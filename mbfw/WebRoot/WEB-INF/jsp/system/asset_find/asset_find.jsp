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
<%-- <script type="text/javascript" src="<%=basePath%>/Lodop/LodopFuncs.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>/Lodop/CheckActivX.js"></script> 
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0></object>
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
			<c:if test="${pd.userPermission <= 2}">
				
			
				<!-- 检索ghfh  -->
			<form action="asset/afc_assetFind.do" method="post" name="userForm" id="userForm">
			<table style="width:100%">
			
<!-- 			<h4><font color="red">下拉框可进行条件查询:</font></h4> -->
			
			<c:if test="${pd.userPermission < 2}">
				<tr >	
					<td ><label>资产编码</label></td>
					<td>
					 <span class="input-icon">
									<input autocomplete="off" id="asset_code" style="width: 188px ;height: 17px" type="text" name="asset_code"  value="${pd.asset_code }" placeholder="这里输入关键字检索" />
									<i id="nav-search-icon" class="icon-search"></i>
					 </span>
					 </td>
<!-- 						<td> -->
<!-- 						<select  class="chzn-select" name="asset_code" id="asset_code" data-placeholder="选择资产编码" > -->
<!-- 									<option></option> -->
<!-- 									<option  value="">无</option> -->
								
<%-- 									<c:forEach items="${assetCodeFind2}" var="pf" varStatus="vs">	 --%>
																													
<%-- 												<option value="${pf.asset_code }" <c:if test="${pd.asset_code == pf.asset_code}">selected</c:if>>${pf.asset_code }</option>  										 --%>
<%-- 									</c:forEach> --%>
									
<!-- 									</select> -->
<!-- 						</td>  -->
						
						<td><label>使用人</label></td>
						<td>
					 <span class="input-icon">
									<input autocomplete="off" id="asset_user" style="width: 188px;height: 17px" type="text" name="asset_user" value="${pd.asset_user }" placeholder="这里输入关键字检索" />
									<i id="nav-search-icon" class="icon-search"></i>
					 </span>
					 </td>
<!-- 						<td> -->
<!-- 						<select class="chzn-select" name="asset_user" id="asset_user"  data-placeholder="选择使用人" > -->
<!-- 										<option ></option> -->
<!-- 										<option  value="">无</option> -->
<%-- 										<c:forEach items="${asset_user2}" var="pf" varStatus="vs">											 --%>
<%-- 												<option value="${pf }"<c:if test="${pd.asset_user == pf }">selected</c:if>>${pf }</option>  										 --%>
<%-- 										</c:forEach> --%>
<!-- 									</select>	 -->
<!-- 						</td> -->
						<td><label>资产名称</label></td>
						<td>
						<select class="chzn-select" name="asset_name" id="asset_name" data-placeholder="选择资产名称" >
												<option></option>
										<option  value="">无</option>
										<c:forEach items="${asset_name2}" var="pf">											
												<option value="${pf}" <c:if test="${pd.asset_name == pf}">selected</c:if>>${pf }</option>  										
										</c:forEach>
									</select>	
						</td>
						<td><label>资产状态</label></td>
						<td>
						<select class="chzn-select" name="asset_status" id="asset_status" data-placeholder="选择资产状态" >
										<option ></option>
										<option  value="">无</option>
										<c:forEach items="${asset_status2}" var="pf" varStatus="vs">											
												<option value="${pf }" <c:if test="${pd.asset_status == pf }">selected</c:if>>${pf }</option>  										
										</c:forEach>
									</select>	
						</td>
						
					</tr>	
					</c:if>
					<tr>
						<td><label>公司名称</label></td>
									<td>
									<select class="chzn-select" id="bank_name"
										name="bank_name" data-placeholder="选择公司"
										onchange="select_company();">
										<option></option>
										<option  value="">无</option>
									</select>
									</td>

						<td><label>公司部门</label></td>
						<td><select class="chzn-select" data-placeholder="选择部门"
										id="department" name="department">
										<option></option>
										<option  value="">请先选择公司名称</option>
						</select>
						</td>
						<td><label>资产类别</label></td>
						<td>
						<select class="chzn-select" name="asset_class" id="asset_class" data-placeholder="选择资产类别" >
										<option ></option>
										<option  value="">无</option>
										
										<c:forEach items="${asset_class2}" var="pf" >											
												<option value="${pf }" <c:if test="${pd.asset_class == pf }">selected</c:if>>${pf }</option>  										
										</c:forEach>
									</select>	
						</td>
						<c:if test="${pd.userPermission < 2}">
						<td><label>入库单号</label></td>
						<td>
						<select class="chzn-select" name="asset_into_bill" id="asset_into_bill" data-placeholder="选择入库单号" >
										<option ></option>
										<option  value="">无</option>
										<c:forEach items="${asset_into_bill2}" var="pf" varStatus="vs">											
												<option value="${pf }"<c:if test="${pd.asset_into_bill == pf }">selected</c:if> >${pf }</option>  										
										</c:forEach>
									</select>	
						</td>
						</c:if>
					</tr>
				
<!-- 					<tr> -->
<!-- 						<td><label>购入时间</label></td> -->
<!-- 						<td> -->
<!-- 						<select class="chzn-select" name="asset_purchase_time" id="asset_purchase_time" data-placeholder="选择购入时间" > -->
<!-- 										<option ></option> -->
<!-- 										<option  value="">无</option> -->
<%-- 										<c:forEach items="${asset_purchase_time2}" var="pf" varStatus="vs">											 --%>
<%-- 												<option value="${pf }" <c:if test="${pd.asset_purchase_time == pf }">selected</c:if>>${pf }</option>  										 --%>
<%-- 										</c:forEach> --%>
<!-- 									</select>	 -->
<!-- 						</td> -->
						
						
						
<!-- 					</tr> -->
					<tr>
					<td><label>开始日期</label></td><td><input class="span10 date-picker" name="creatuser_Time" id="creatuser_Time"  value="${pd.creatuser_Time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="开始日期" title="开始日期" style="width: 220px;"/></td>
					<td><label>结束日期</label></td><td><input class="span10 date-picker" name="creatuser_endTime" id="creatuser_endTime"  value="${pd.creatuser_endTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="结束日期" title="结束日期" style="width: 220px;"/> </td>
				
					</tr>
				
					<tr>
						<td style="text-align:left" >
							<button class="btn btn-small btn-success" onclick="search();"> 查询  </button> 
					<input type="button" value="返回" onclick="javascript:window.location.href='asset/afc_assetFind.do'"> 						
						</td>			
						<td>
						<button style="float: right;" class="btn btn-small btn-primary" onclick="printmang2code();">打印二维码</button>
						</td>
					</tr>
				
			</table>
			</form>		
			</c:if>
			</div>
			</div>
			<!-- 检索结束  -->
			<div style="overflow-x:auto;width: 100%;height: 100%;">
		    <table id="table_report" class="text-table table table-striped table-bordered table-hover" >
				<thead>
					<tr>
						<th><label><input type="checkbox" id="zcheckbox" onclick="seleteIsAll(this.checked)"/><span class="lbl">全选/全不选</span></label></th>
						<th>序号</th>
						<th>资产名称</th>
						<th>资产编码</th>
						<th>资产类别</th>
<!-- 						<th>规格型号</th> -->
<!-- 						<th>SN号</th> -->
<!-- 						<th>详细配置</th> -->
<!-- 						<th>计量单位</th> -->
<!-- 						<th>价格</th> -->
<!-- 						<th>使用公司编码</th> -->
						<th>使用公司</th>
						<th>使用部门</th>
<!-- 						<th>使用部门编码</th> -->
						<th>使用人</th>
<!-- 						<th>存放地点</th> -->
<!-- 						<th>管理员</th> -->
						<th>购入时间</th>
<!-- 						<th>供应商</th> -->
<!-- 						<th>使用期限</th> -->
<!-- 						<th>二维码</th> -->
				
<!-- 						<th>品牌</th> -->
						<th>资产状态</th>
						<th>入库单号</th>
<!-- 						<th>备注</th> -->
<!-- 						<th>操作</th> -->
						
					</tr>
				</thead>
			 <tbody>
					<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty allotList}">
						<%-- <c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if> --%>
						<c:forEach items="${allotList}" var="assetm" varStatus="vs">
								 <tr>
									<td><label><input type="checkbox" name="ids" value="${assetm.asset_code}@${assetm.asset_name}@${assetm.asset_class}" /><span class="lbl"></span></label></td>
									<td onclick="find('${assetm.id}');">${vs.index+1}</td>
									<td onclick="find('${assetm.id}');">${assetm.asset_name} </td>
									<td onclick="find('${assetm.id}');">${assetm.asset_code}</td>
									<td onclick="search1('${assetm.asset_class}','asset_class');">${assetm.asset_class}</td>
<%-- 									<td>${assetm.asset_standard_model}</td> --%>
<%-- 									<td>${assetm.asset_sn}</td> --%>
<%-- 									<td>${assetm.asset_detail_config}</td> --%>
<%-- 									<td>${assetm.asset_unit}</td> --%>
<%-- 									<td>${assetm.asset_price}</td> --%>
<%-- 									<td>${assetm.asset_usecompany_code}</td> --%>
									<td onclick="find('${assetm.id}');">${assetm.asset_use_company}</td>
									<td onclick="search2('${assetm.asset_use_company}','${assetm.asset_use_dept}');">${assetm.asset_use_dept}</td>
<%-- 									<td>${assetm.asset_usedept_code}</td> --%>
									<td onclick="find('${assetm.id}');">${assetm.asset_user}</td>
<%-- 									<td>${assetm.asset_storehouse}</td> --%>
<%-- 									<td>${assetm.asset_manager}</td> --%>
									<td onclick="find('${assetm.id}');">${assetm.asset_purchase_time}</td>
<%-- 									<td><img src="${filePath }${proof}"/></td>  --%>
<%-- 									<td>${assetm.asset_provider}</td> --%>
<%-- 									<td>${assetm.asset_max_years}</td> --%>
<!-- 									<td><div style="width:200px;height:80px;border:1px solide #aaccff; display:none;" id="divHover" > -->
<!-- 									<a> -->
									
<!-- 									<img src="/pic/1.jpg" width="100px" height="200px" id="bigimg"/>查看 -->
<!-- 									</a> -->
<!-- 									</div> -->
<!-- 									<img src="/pic/1.jpg" id="smallimg" width="10px" height="20px" onmouseout="hoverHiddendiv()"  -->
<!-- 									onmouseup="hoverShowDiv()" /></td> -->
<%-- 									<td>${assetm.asset_brand}</td> --%>
									<td onclick="find('${assetm.id}');">${assetm.asset_status}</td>
									<td onclick="find('${assetm.id}');">${assetm.asset_into_bill}</td>
<%-- 									<td>${assetm.asset_nod}</td>									 --%>
<!--  								<td style="width: 30px;" class="center"> -->
<!-- 								 <div class="hidden-phone visible-desktop btn-group">		 -->
								
<%-- 								<button class="btn btn-small btn-success" onclick="find('${assetm.id}');"> 查看详情  </button>  --%>
<!-- 								</div> -->
<!-- 								</td> -->
								</tr>	 						
				
								<%-- <c:if test="${QX.cha == 1 }"><td>${vs.index+1}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_name}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_code}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_class}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_standard_model}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_sn}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_detail_config}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_unit}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_price}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_usecompany_code}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.bank_name}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.department}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_usedept_code}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_user}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_storehouse}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_manager}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_purchase_time}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_provider}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_max_years}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_qr_code}</td></c:if>
								<c:if test="${QX.cha == 1 }"><td>${assetm.asset_brand}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_status}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_into_bill}</td></c:if>
								<c:if test="${QX.cha == 1 }">	<td>${assetm.asset_nod}</td></c:if> --%>
								
<!-- 								</tr>
 -->							
					</c:forEach>
						
						
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose> 
			</tbody> 
		</table>
	</div>
			
		<div class="page-header position-relative">
		<table style="width:100%;float: right;">
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
		</div>
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
		<script src="static/js/qrcode.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->	
		<!-- 引入 -->			
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">	
		$(top.hangge());	
		$(function(){
			
			if('${pd.asset_use_company}' != "" &&  '${pd.department}' == ""){
				select_company();
			}
			if('${pd.asset_use_company}' != "" &&  '${pd.department}' != ""){
				select_company();
			}
		});
		
		var p = '${pd}';
		
		var zn = '${institutionInfo}';
		var jsons = JSON.parse(zn)  
		//开始进入 初始化公司下拉框
		if('${pd.userPermission}' > 1){
			var option = document.createElement("option");
			option.innerHTML = '${pd.asset_use_dept}';
			option.selected='selected';
			$("#bank_name").append(option);					
		}
		else{
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
		 			var option = document.createElement("option");
		        option.innerHTML = key+"";
		        if(key=='${pd.bank_name}'){
		        	option.selected='selected';
		        }
		        if(key=='${pd.asset_use_company}'&& '${pd.userPermission}'==2){
		        $("#bank_name").append(option);
		        }else if('${pd.userPermission}'==1){
		        	$("#bank_name").append(option);
		        }
		 		});
				//select_company();
			}); 
		}
		//申请公司的点击change事件
		function select_company() {
			var query_field = document.getElementById("bank_name");
	        var query_condition = document.getElementById("department");
			
			var options = query_field.options;
			var company_name = options[query_field.selectedIndex].innerHTML;
			if(company_name != $('pd.asset_use_company') && $('pd.userPermission') > 1){
				company_name = $('pd.asset_use_company') ; 
			}
			query_condition.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key==company_name){	 					
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var option = document.createElement("option");
	 						if('${userPermission}' == 2){
	 							if(te == '${pd.asset_use_company}'){
		 							option.selected='selected';
		 						}
	 						}
	 						else{
	 							if(te == '${pd.department}'){
		 							option.selected='selected';
		 						}
	 						}
	 				        option.innerHTML = te+"";
	 				        $("#department").append(option);				        
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#department").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
		
		/* var option = document.createElement("option");
	    option.innerHTML = '${pd.department}';
	    option.selected='selected';
	    $("#department").append(option); */
		
		function search() {
			$("#userForm").submit();
		}
	    function search1(str,classStr) {
	    	var obj = document.getElementById(classStr);
	    	for(i=0;i<obj.length;i++){
	            if(obj[i].value==str){
	                obj[i].selected = 'selected';
	                break;
	            }
	        }
	    	$("#"+classStr).trigger("liszt:updated");
			$("#userForm").submit();
			
		}
	    function search2(company,dept) {
	    	var obj = document.getElementById("bank_name");
	    	for(i=0;i<obj.length;i++){
	            if(obj[i].value==company){
	                obj[i].selected = 'selected';
	                break;
	            }
	        }
	    	$("#bank_name").trigger("liszt:updated");
	    	
	    	select_company();
	    	
	    	obj = document.getElementById("department");
	    	for(i=0;i<obj.length;i++){
	            if(obj[i].value==dept){
	                obj[i].selected = 'selected';
	                break;
	            }
	        }
	    	$("#department").trigger("liszt:updated");
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
		function addFind(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="高级查询";
			 diag.URL = '<%=basePath%>asset/goFind.do';
			 diag.Width = 1080;
			 diag.Height = 350;
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
		//查看
		function addCK(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="详细信息";
			 diag.URL = '<%=basePath%>asset/goFind.do';
			 diag.Width = 1080;
			 diag.Height = 900;
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
		function find(user_id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "资产详情";
			diag.URL = '<%=basePath%>asset/goFind.do?id='+user_id;
			diag.Width = 1140;
			diag.Height = 650;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document
						.getElementById('zhongxin').style.display == 'none') {
					nextPage(1);
				}
				diag.close();
			};
			diag.show();
		}
		
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>user/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
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

		//获取需要进行打印二维码条目的操作
		var LODOP; //打印对象声明为全局变量
		function printmang2code(){
			//获取所选择的复选框中的value值，方便进行传输
			var checkselecte = document.getElementsByName("ids");
			var selectarr = new Array();
			var selectindex = 0;
			for(var i=0 ; i <checkselecte.length ; i++){
				if(checkselecte[i].checked == true){
					selectarr[selectindex] = checkselecte[i].value;
					selectindex++;
				}
			}		
			if(confirm("你确定要打印所选中的条目二维码吗？")){
				//检查是否安装了打印控件
				checkIsInstall();	
				print2CodeOption(selectarr , selectindex);
			} 
		}		
		//进行二维码的打印（因为这里是存在多条条目的二维码打印，所以这里就不进行预览，而直接进行打印了）
		function print2CodeOption(selectarr , selectlength){
			var subarr = new Array();
			for(var i=0 ; i <selectlength ; i++){
				//分割每一个条目中存在的资产编码，名称，类别信息
				subarr = selectarr[i].split("@");
				LODOP=getLodop();  	
				LODOP.ADD_PRINT_IMAGE("5mm","15mm","10mm","10mm","<img border='0' src='<%=basePath%>/Lodop/logo.jpg'>"); 
				LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//可缩放模式
				LODOP.ADD_PRINT_TEXT("5mm","25mm","68mm","10mm","湖南慈利农村商业银行");
				LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
				LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
				LODOP.ADD_PRINT_TEXT("10mm","25mm","68mm","10mm","HUNAN CILI RURAL COMMERCIAL BANK");
				LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
				LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
				//显示相应的资产信息
				LODOP.ADD_PRINT_TEXT("18mm","20mm","28mm","5mm","资产名称:" + subarr[1]);
				LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
				LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
				LODOP.ADD_PRINT_TEXT("25mm","20mm","38mm","5mm","资产类别:" + subarr[2]);
				LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
				LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
				//支持两种二维码的形式PDF417和QRCode，后面的内容就是扫描二维码显示的内容，所以需要显示什么可以在这里显示
				LODOP.ADD_PRINT_BARCODE("15mm","60mm","35mm","25mm","QRCode","资产编码:" + subarr[0]);
				LODOP.SET_PRINT_STYLEA(0,"GroundColor","#ffffff"); 
				LODOP.SET_SHOW_MODE("LANGUAGE",0);
				//直接打印
				LODOP.PRINT();
			}
		}
		
		//检查是否已经安装了控件 
		function checkIsInstall() {	 
			try{ 
			    var LODOP=getLodop(); 
				if (LODOP.VERSION) {
					 if (LODOP.CVERSION){
					 }
					 else
					 alert("本机已成功安装了Lodop控件！\n 版本号:"+LODOP.VERSION); 

				};
			 }catch(err){ 
	 		 } 
		}; 
		
		//全选或者全不选的操作
		function seleteIsAll(selectstatu){
			var checkselecte = document.getElementsByName("ids");
	  		for(var i = 0 ;i<checkselecte.length;i++){
	  			checkselecte[i].checked = flag;
	  	    }
		}
			
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

		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			var asset_code = $('#asset_code').val();
			var asset_class = $('#asset_class').val();
			var bank_name = $('#bank_name').val();
			var department = $('#department').val();
			var asset_user = $('#asset_user').val();
// 			var asset_purchase_time = $('#asset_purchase_time').val();
			var asset_into_bill = $('#asset_into_bill').val();
			var asset_status = $('#asset_status').val();
			var asset_name = $('#asset_name').val();
			var creatuser_Time =$('#creatuser_Time').val();
			var creatuser_endTime =$('#creatuser_endTime').val();
			
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/afc_assetFind?currentPage="+clickpage+"&showCount="+${page.showCount}+"&asset_name="+encodeURI(encodeURI(asset_name))
					+"&asset_code="+encodeURI(encodeURI(asset_code))
					+"&asset_class="+encodeURI(encodeURI(asset_class))
					+"&bank_name="+encodeURI(encodeURI(bank_name))
					+"&department="+encodeURI(encodeURI(department))
					+"&asset_user="+encodeURI(encodeURI(asset_user))
// 					+"&asset_purchase_time="+encodeURI(encodeURI(asset_purchase_time))
					+"&asset_into_bill="+encodeURI(encodeURI(asset_into_bill))
					+"&creatuser_Time="+encodeURI(encodeURI(creatuser_Time))
					+"&creatuser_endTime="+encodeURI(encodeURI(creatuser_endTime))
					+"&asset_status="+encodeURI(encodeURI(asset_status));	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			var asset_code = $('#asset_code ').val();
			var asset_class = $('#asset_class ').val();
			var bank_name = $('#bank_name ').val();
			var department = $('#department ').val();
			var asset_user = $('#asset_user ').val();
// 			var asset_purchase_time = $('#asset_purchase_time ').val();
			var asset_into_bill = $('#asset_into_bill ').val();
			var asset_status = $('#asset_status ').val();
			var asset_name = $('#asset_name ').val();
			var creatuser_Time =$('#creatuser_Time').val();
			var creatuser_endTime =$('#creatuser_endTime').val();
			window.location = "${pageContext.request.contextPath}/asset/afc_assetFind?currentPage="+clickpage+"&showCount="+${page.showCount}+"&asset_name="+encodeURI(encodeURI(asset_name))
			+"&asset_code="+encodeURI(encodeURI(asset_code))
			+"&asset_class="+encodeURI(encodeURI(asset_class))
			+"&bank_name="+encodeURI(encodeURI(bank_name))
			+"&department="+encodeURI(encodeURI(department))
			+"&asset_user="+encodeURI(encodeURI(asset_user))
// 			+"&asset_purchase_time="+encodeURI(encodeURI(asset_purchase_time))
			+"&asset_into_bill="+encodeURI(encodeURI(asset_into_bill))
			+"&creatuser_Time="+encodeURI(encodeURI(creatuser_Time))
			+"&creatuser_endTime="+encodeURI(encodeURI(creatuser_endTime))
			+"&asset_status="+encodeURI(encodeURI(asset_status));	
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			var asset_code = $('#asset_code ').val();
			var asset_class = $('#asset_class ').val();
			var bank_name = $('#bank_name ').val();
			var department = $('#department ').val();
			var asset_user = $('#asset_user ').val();
// 			var asset_purchase_time = $('#asset_purchase_time ').val();
			var asset_into_bill = $('#asset_into_bill ').val();
			var asset_status = $('#asset_status ').val();
			var asset_name = $('#asset_name ').val();
			var creatuser_Time =$('#creatuser_Time').val();
			var creatuser_endTime =$('#creatuser_endTime').val();
			window.location = "${pageContext.request.contextPath}/asset/afc_assetFind?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&asset_name="+encodeURI(encodeURI(asset_name))
			+"&asset_code="+encodeURI(encodeURI(asset_code))
			+"&asset_class="+encodeURI(encodeURI(asset_class))
			+"&bank_name="+encodeURI(encodeURI(bank_name))
			+"&department="+encodeURI(encodeURI(department))
			+"&asset_user="+encodeURI(encodeURI(asset_user))
// 			+"&asset_purchase_time="+encodeURI(encodeURI(asset_purchase_time))
			+"&asset_into_bill="+encodeURI(encodeURI(asset_into_bill))
			+"&creatuser_Time="+encodeURI(encodeURI(creatuser_Time))
			+"&creatuser_endTime="+encodeURI(encodeURI(creatuser_endTime))
			+"&asset_status="+encodeURI(encodeURI(asset_status));	
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

