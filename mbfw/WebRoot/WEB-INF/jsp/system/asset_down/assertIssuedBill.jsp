<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.net.URLEncoder" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	charset="utf-8"> 
</script>

</head>
<body>

 <div class="container-fluid" id="main-container"> 


 <div id="page-content" class="clearfix"> 
						
   <div class="row-fluid">
		<form action="asset/arda_assertIssued.do"  name="assetIssuedBillForm" id="assetIssuedBillForm" method="post">
			<table>
				<tbody>
				<tr>
					<td  style="vertical-align:top;">
						<c:if test="${QX.add ==1 }">
						<a  class="btn btn-mini btn-info" onclick="add_asset_issued();">新增资产下发</a>
						</c:if>
						<c:if test="${QX.del ==1 }">
						<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');">批量删除</a>
						</c:if>
					</td>
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
				</tr>
				</tbody>
			</table>
			<div style="overflow-x:auto;width: 100%;height: 100%;">
			<table id="table_report" class="table text-table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>资产编码</th>
						<th>资产名称</th>
						<th>下发状态</th>
						<th>下发支行</th>
						<th>下发部门</th>
						<th>下发人员</th>
						<th>下发时间</th>
						<th>接收支行</th>
						<th>接收部门</th>
						<th>接收人</th>
						<th>接收时间</th>
						<th>接收证明</th>
						<th>说明</th>
						<th>操作</th>
					</tr>
				</thead>
										
				<tbody>
				<c:forEach items="${asset_issued_bill}" var="afb" varStatus="vs">
					<tr>
						<td><label><input type="checkbox" id="plch" name="plch" value="${afb.id }@${afb.asset_code }@${afb.issued_status }"/><span class="lbl"></span></label></td>
						<td>${vs.index+1}</td>
						<td>${afb.asset_code}</td>
						<td>${afb.asset_name}</td>
						<td><a>${afb.issued_status}</a></td>
						<td>${afb.issued_branch}</td>
						<td>${afb.issued_department}</td>
						<td><a>${afb.issue_person}</a></td>
						<td>${afb.issue_time}</td>
						<td>${afb.receive_branch}</td>
						<td>${afb.receive_department}</td>
						<td>${afb.receiver}</td>
						<td>${afb.receive_time}</td>
						<td>
							 <c:forTokens items="${afb.receive_proof }" delims="#" var="proof">
							<a onclick="down_load('${ proof}');">${fn:split(proof,"@")[1] }</a>&nbsp;&nbsp;
							</c:forTokens> 
						</td>
						<td>${afb.instruction}</td>
						<td style="width: 30px;" class="center">
							<div class='hidden-phone visible-desktop btn-group'>
							<div class="inline position-relative">
								<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
									<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
										<li><a style="cursor:pointer;" title="编辑" onclick="updateAssetIssueBill('${afb.id }','${afb.issued_status }');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
										<li><a style="cursor:pointer;" title="删除" onclick="delAssetIssueBill('${afb.id }','${afb.asset_name }','${afb.issued_status }','${afb.asset_code }');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
									</ul>
								</div>
							</div>
						</td>
				</tr>							
			</c:forEach>
			</tbody>
			</table>
		</div>
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
</div>
	
			

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
		<!-- 确认窗口 -->
		<!-- 引入 -->
<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#assetIssuedBillForm").submit();
		}
		//判断项目修改是否成功
		var issued = '${issued}';	
		if(issued == "success"){
			var txt=  "下发成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		//判断项目修改是否成功 delResult
		var updateIssued = '${updateIssued}';	
		if(updateIssued == "success"){
			var txt=  "修改下发单成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		//判断项目修改是否成功 delResult
		var delResult = '${delResult}';	
		if(delResult == "success"){
			var txt=  "删除下发单成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}

		// 新增资产下发
		function add_asset_issued(){
			window.location.href='<%=basePath%>asset/arda_add_assertIssued.do';
		}
		
		//下载证明
		function down_load(proof){
			window.location.href='<%=basePath%>/asset/arda_downReceiveProof.do?proof='+encodeURI(encodeURI(proof));
		}

		function updateAssetIssueBill(id,status){
			if(status == '领用'){
				var txt=  "处于 [ "+status+ "状态 ]的下发单不能修改！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}else if(status == '下发中'){
				window.location.href='<%=basePath%>asset/arda_edit_issued_bill.do?id='+id+'&issuing=yes';
			}else{
				window.location.href='<%=basePath%>asset/arda_edit_issued_bill.do?id='+id+'&receiving=yes';
			}
		}
		
		//删除
		function delAssetIssueBill(issue_bill_id,msg,status,asset_code){
			if(status == '领用'){
				var txt=  "处于 [ "+status+ "状态 ]的下发单不能删除！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}else if(status== '下发中'){
				bootbox.confirm('确定要删除供应商名称[ <b style="color: red">'+msg+" </b>]吗?", function(result) {
					if(result) {
						top.jzts();
						var url = '<%=basePath%>asset/arda_deleteAssetIssueBill.do?id='+issue_bill_id+'&down_issuing=yes'+'&asset_code='+asset_code;
						$.get(url,function(data){
						 	window.location = "${pageContext.request.contextPath}/asset/arda_assertIssued?delResult=success"; 
						});
					}
				});
			}else{
				bootbox.confirm('确定要删除供应商名称[ <b style="color: red">'+msg+" </b>]吗?", function(result) {
					if(result) {
						top.jzts();
						var url = '<%=basePath%>asset/arda_deleteAssetIssueBill.do?id='+issue_bill_id+'&asset_code='+asset_code+'&issued_status='+encodeURI(encodeURI(status));
						$.get(url,function(data){
						 	window.location = "${pageContext.request.contextPath}/asset/arda_assertIssued?delResult=success"; 
						});
					}
				});
			}
			
		}
		
		
		//批量删除操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('plch').length;i++)
					{
						  if(document.getElementsByName('plch')[i].checked){
						  	if(str=='') str += document.getElementsByName('plch')[i].value;
						  	else str += ',' + document.getElementsByName('plch')[i].value;
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
						var str1 = new Array();
						var str2 = new Array();
						var str3='',str4='';
						str1 =str.split(",");
						for(var i=0;i<str1.length;i++){
							str2=str1[i].split("@");
							if(str2[2] != '领用'){
								str3+=str2[0]+'@'+str2[1]+'@'+str2[2]+',';
							}else{
								if(i!=str1.length -1){
									str4+=str2[2]+",";
								}else{
									str4+=str2[2];
								}
							}
						}
						//alert(str3);
						if(str4.length == 0 || str4 == ''){
							if(msg == '确定要删除选中的数据吗?'){
								top.jzts();
								 $.ajax({
									type: "POST",
									url: '<%=basePath%>asset/delete_all_asset_issue_bill.do',
							    	data: {'asset_issue_bill':str3},
									dataType:'json',
									//beforeSend: validateData,
									cache: false,
									success: function(data){
										 window.location = '<%=basePath %>asset/arda_assertIssued?delResult=success';
									}
								}); 
							}
						}else{
							 var txt=  "["+str4+ "状态]的下发单不能删除！";
								window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
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
		

		
		//下面都是分页处理的操作
		//(1)操作1：点击页码进行显示数据
		function changePage(clickpage){	
			//注意点：
			//(1)这里用ajax进行处理的话会存在问题，因为ajax无法进行页面跳转，所以如果用ajax的话，那么就要回到这个success中进行拼接内容，感觉有点麻烦了。
			//(2)直接重定向带参数过去，currentPage表示将要显示的页面，showCount表示为每页的数据条数
			window.location = "${pageContext.request.contextPath}/asset/arda_assertIssued?currentPage="+clickpage+"&showCount="+${page.showCount};	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			window.location = "${pageContext.request.contextPath}/asset/arda_assertIssued?currentPage="+clickpage+"&showCount="+${page.showCount};
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			window.location = "${pageContext.request.contextPath}/asset/arda_assertIssued?currentPage="+${page.currentPage}+"&showCount="+pagesize;
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