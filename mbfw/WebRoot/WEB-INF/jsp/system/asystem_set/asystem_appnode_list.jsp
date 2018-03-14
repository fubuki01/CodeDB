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
<title>审批节点</title>

<!-- 引入 -->
<link rel="stylesheet" href="static/css/searchInputStyle.css" type="text/css" />
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 设置表格居中 -->
<style type="text/css">
	#table_report thead tr th{
		text-align: center;
	}
	#table_report tbody tr td{
		text-align: center;
	}
</style>
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
							
				<!-- 列表显示所有的审批节点信息 -->
				<form action="" method="post" name="approvalnodeform" id="approvalnodeform">
				<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: -15px;">
					<thead>
						<tr>
							<th><label><input type="checkbox" id="zcheckbox" onclick="seleteIsAll(this.checked)"/><span class="lbl">全选/全不选</span></label></th>
							<th>序号</th>
							<th>审批节点名称</th>
							<th>审批节点总人数</th>
							<th>审批通过最小人数</th>
							<th>审批节点描述</th>
							<th>操作</th>
						</tr>
					</thead>
					<!-- 表格内容，，，循环显示 -->
					<tbody>
					<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty listPage}">
										<!-- 先注释权限的控制这里 -->
										<%-- <c:if test="${QX.cha == 1 }"> --%>
											<c:forEach items="${listPage}" var="node" varStatus="vs">
												<tr>
													<td><label><input type="checkbox" name="ids" value="${node.node_Id}" /><span class="lbl"></span></label></td>
													<td>${vs.index+1}</td>
													<td>${node.node_Name}</td>
													<td>${node.node_TotalNumber}</td>
													<td>${node.node_PassNumber}</td>
													<td>${node.node_Description}</td>
													<td style="width: 40px;">
													<div class="hidden-phone visible-desktop btn-group">
														<a class='btn btn-mini btn-info' title="编辑" onclick="editApprover('${node.node_Id}');"><i class='icon-edit'></i>编辑</a>	
														<a class='btn btn-mini btn-danger' title="删除"  data-toggle="modal" data-target="#modal01" name="${node.node_Id}" id="deletbtnlianjie"><i class='icon-trash'></i>删除</a>
													</div>
													</td>								
												</tr>										
											</c:forEach>							
										<%-- </c:if> --%>
                                         <!--这里先注释，， 先不控制显示的权限先 -->
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
			</form>
			<!-- 底部操作区域 -->
			<div class="page-header position-relative">
				<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;">
						<a class="btn btn-small btn-success" onclick="addOne();"><i class="icon-plus">&nbsp;新增审批节点</i></a>				
						<a class="btn btn-small btn-danger" data-toggle="modal" data-target="#modal02" ><i class="icon-trash">&nbsp;批量删除审批节点</i></a>				
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
		
		<!-- 添加节点的界面 ，默认开始不显示-->
		<div id="addNodeInfodiv" style="display: none;" class="row-fluid" >
 		<form action="" method="post" id="submmitapprovnode" name="submmitapprovnode">
 			<input type="hidden" id="saveapproverid" name="saveapproverid">
 			<table id="table_report" class=" table-text table table-striped table-bordered table-hover" >
				<thead>
					<tr>
						<th>审批节点名称</th>
						<td>
							<input type="text" placeholder="审批节点名称" name="node_Name" id="node_Name">
						</td>
						<th>审批通过最少人数</th>
						<td>
							<input type="text" placeholder="审批节点需要通过的最少人数" name="node_PassNumber" id="node_PassNumber" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
						</td>
						<th>审批总人数</th>
						<td>
							<input type="text" placeholder="审批节点的总审批人数" name="node_TotalNumber" id="node_TotalNumber" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
						</td>
					</tr>
					
					<!-- 关于显示审核人员列表内容 -->
					<tr>
						<th>添加审核人员</th>
						<td>
							<select id="showapproverpeople" name="showapproverpeople">
								<option>选择要添加的人员</option>
								<c:forEach items="${approverlistPage}" var="approver" varStatus="vs">
									<option value="${approver.user_Id}">${approver.user_Name}</option>
								</c:forEach>
							</select>
							<button type="button" class="btn btn-success" id="addApprover" name="addApprover" onclick="addApproverjs();">添加</button>						
						</td>
					</tr>
					
					<tr>
						<th>节点审批人员名单</th>
						<td colspan="6" id="approverOrder" style="height: 150px;width: 92%">
							
						</td>
					</tr >
									
					<tr>
						<th>审批节点描述</th>
						<td colspan="6">
							<textarea rows="6" cols="30" style="width: 98%" name="node_Description" id="node_Description"></textarea>
						</td>
					
					</tr>	
					<tr>
 						<th>温馨提示</th>	
 						<td colspan="6">
 							<textarea  rows="1" cols="80" style="width: 98%;font-size:18px;color: red;" placeholder="(1)点击提交后添加到审批节点列表中;(2)点击取消返回节点列表界面" readonly="readonly"></textarea>
 						</td>								
					</tr>
					
					<tr>
						<td style="text-align:center;" colspan="6">
							<button class="btn btn-info" type="button" onClick="submmitcontent()"> <i class="ace-icon fa fa-check bigger-110"></i> 提交 </button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-danger" type="button" onClick="cancelcontent()"> <i class="ace-icon fa fa-check bigger-110"></i> 取消</button>
						</td>
					</tr>							
					</thead>
				</table>
			</form>
		</div>
	<!-- 流式布局控制 -->												
		</div>
	</div>
</div>
	
	
	
	<!-- JS代码区，写在后面是为了提高运行效率 -->
	<script type="text/javascript">
		$(top.hangge());
		
		//当搜索框获取到焦点的时候
		$("#retrieve_content").hover(function(){
			$("#retrieve_content").tips({
				side:3,
	            msg:'检索列表范围：审批节点名称，审批节点人数，审批通过最小人数，审批节点描述信息',
	            bg:'#AE81FF',
	            time:3
	        });	
		});
				
		//判断节点新增和编辑操作的结果
		var editResult = '${editresult}';	
		if(editResult == "success"){
			alert("信息保存成功");
		}
		//判断删除操作的结果
		var deleteResult = '${deleteresult}';
		var deleresultnumber = '${deleresultnumber}';
		if(deleteResult == "success" ){
			var result = new Array();
			result = deleresultnumber.split("@");
			alert("选择删除总条数为："+result[0]+"\n删除成功的条数为："+result[1]+"\n其中无法删除的条数为(由于已经处于审批流程中)："+(result[0]-result[1]));
		}

		
		//新增审批节点
		function addOne(){
			$('#nodeContentdiv').css({"display":"none"});
			$("#addNodeInfodiv").css({"display":""});
		}
		//批量删除节点
		function deleteAll(){
			//(1)首先判断是否有选中需要删除的，如果为空，不判断的话后台是会报null错
			if($("input[name='ids']:checked").length <=0){
				alert("没有选中任何一个数据进行删除");
			}
			else{
			//(2)发送请求到后台
				$('#approvalnodeform').attr({action:'asset/asystem_deletebatchnode.do'});
				$('#approvalnodeform').submit();
			}
		}
		
		//全选和全不选的控制
	    function seleteIsAll(flag) {
	  		var checkselecte = document.getElementsByName("ids");
	  		for(var i = 0 ;i<checkselecte.length;i++){
	  			checkselecte[i].checked = flag;
	  	    }
	    }
		
		//处理添加审核人员的操作
		function addApproverjs(){
			//获取下拉列表中选中的内容
			var selectvalue = $("#showapproverpeople").val();
			var selectshow = $("#showapproverpeople option:checked").text();//获取选中的显示内容			
			if( selectshow == "选择要添加的人员"){
				alert("请选择所要添加的名单哦！！");
			}
			else{
				var ifAddResult = true;  //标识是否进行添加操作
				//判断选择的人员中是否已经添加过了
				$('#approverOrder div').each(function(i , n){
					if($(this).attr("id") == selectvalue){
						alert("该人员已经在审批人员列表中，请不要重复添加");
						ifAddResult = false;
					}
				});
				if(ifAddResult == true){
					//获取在审核人员中，已经有多少个人，这样能够方便进行点击取消操作
					var numberdiv = $("#approverOrder").children().length;
					//将选中的内容添加到审核人员顺序中
					var $addcontent = $('<div style="display:inline-block; border:1px #000000 solid;margin: 10px;" id="'+selectvalue+'"><span>'+selectshow+'</span><button class="btn btn-danger btn-sm" type="button"  onclick="cancelCurrentPeopel(\''+selectvalue+'\');">取消</button></div>');
					//添加到审核人员的td标签中
					$addcontent.appendTo($("#approverOrder"));
					alert("添加成功!");
				}
			}  
		}
		
		//审核人员中的取消审核人员的操作,这样等会以后直接通过userid进行搜索判断td中对应的div的id为这个的就删除就可以了
		function cancelCurrentPeopel(userid){		
			//遍历整个审核人列表的td，找到是点击取消的这个userid的div进行移除
			$('#approverOrder div').each(function(i , n){
				  //判断是否是点击的那个内容的ID，如果是就进行删除
				  if($(this).attr("id") == userid ){
					  $(this).remove();
				  }
			  });
		}
		
		//点击添加审核节点中的提交按钮
		function submmitcontent(){
			 //（1）当点击之后，需要要判断其他的必要填的内容是否已经填写了
			 //节点名称
			if($('#node_Name').val() == "" || $('#node_Name').val() == null){
				$("#node_Name").tips({
					side:3,
		            msg:'请输入节点名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_Name").focus();
				return false;
			}
			//通过人数
			if($('#node_PassNumber').val() == "" || $('#node_PassNumber').val() == null){
				$("#node_PassNumber").tips({
					side:3,
		            msg:'请输入最小通过人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_PassNumber").focus();
				return false;
			}
			//最大人数
			if($('#node_TotalNumber').val() == "" || $('#node_TotalNumber').val() == null){
				$("#node_TotalNumber").tips({
					side:3,
		            msg:'请输入节点最多人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_TotalNumber").focus();
				return false;
			}
			//节点描述内容
			if($('#node_Description').val() == "" || $('#node_Description').val() == null){
				$("#node_Description").tips({
					side:3,
		            msg:'请输入节点描述信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_Description").focus();
				return false;
			}
			//判断添加的审核人员列表中的个数是否等于的填入的总的人数大小
			if($("#approverOrder").children().length != $('#node_TotalNumber').val()){
				$("#node_TotalNumber").tips({
					side:3,
		            msg:'输入的总人数不等于审批列表人员个数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_TotalNumber").focus();
				return false;
			}
			
			//判断是否填入的总的审批人数小于最小通过的人数
			if($('#node_TotalNumber').val() < $('#node_PassNumber').val()){
				$("#node_PassNumber").tips({
					side:3,
		            msg:'输入的最小通过人员个数大于审批总人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_PassNumber").focus();
				return false;
			}
			 
			//（2）把审核人员的真正序号的名单进行提交
			var approverId = "";
			//首先遍历审核人员顺序中的div标签的id，因为之前就已经把userid进行封装了
			  $('#approverOrder div').each(function(i , n){
				  //alert($(this).attr("id"));
				  approverId = approverId +"-"+$(this).attr("id"); //通过"-"来进行分割
			  });
			//将得到的审核人员的userid放到form表单中的一个隐藏控制，方便后面进行提交到后台
			$('#saveapproverid').val(approverId);
			//（3）提交form表单到后台
			$('#submmitapprovnode').attr({action:'asset/asystem_saveapprovernode.do'});
			$('#submmitapprovnode').submit();	
		}
		
		//获取发生删除操作模态框的值，即要传送删除的审批节点的ID
		 var deletNumber = 0 ; 
		 $(document).on('click','#deletbtnlianjie',function () {
		        deletNumber = $(this).attr('name');  //获取点击删除的对应数据的学号
		 })
		//点击删除，将对应的审批节点进行删除处理
		function delApprover(){	
			 var nodeid = deletNumber ;//得到进行删除的审批节点ID
			 //判断是否能够进行删除，如果当前节点是项目审批过程中的审批流程中的，那么就不能进行删除该节点
			 $.ajax({
				url:'${pageContext.request.contextPath}/asset/asystem_currentnodedeleteStatus',
				async:false,
				data:{"nodeid":nodeid},
				type:'POST',
				success:function(data){
					if(data.deleteResult =="YES"){						
						window.location = '${pageContext.request.contextPath}/asset/asystem_deleteapprovernode?nodeid='+nodeid;
					}
					else if(data.deleteResult =="NO"){
						alert("审批流程数据条目中，存在使用该审批节点，无法进行删除，请确认!!");
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"
			});
			
			/* //这里不用ajax进行删除了，因为为了友好的弹出一个删除成功的提示
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/asystem_deleteapprovernode',
				async:false,
				data:{"nodeid":nodeid},
				type:'POST',
				success:function(data){
					if(data.delresult == "success"){
						alert("删除成功！");
						//删除成功后，重定向到显示页面
						window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode";
					}
					else{
						alert("删除失败!，请重试");
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"
			}); */
		}
		//点击查看按钮，显示对应的信息()
		function editApprover(nodeid){			
			window.location = '${pageContext.request.contextPath}/asset/asystem_lookcurrentnode?node_Id='+nodeid;
		}
		
		//在添加界面中，点击取消按钮，返回主界面
		function cancelcontent(){
			if(confirm("确定要返回到审批信息显示界面吗？")){
				window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode";
			}
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
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));	
		}
		//操作2：点击上下页，进行页码内容的改变
		function nextPage(clickpage){
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	//后面要注意编码，因为如果是中文的话，这样传会发生乱码的，那么搜索肯定就匹配不到内容
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+clickpage+"&showCount="+${page.showCount}+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));
		}
		//操作3：选择下拉框的时候，进行页面大小的改变
		function changeCount(pagesize){	
			//(1)获取搜索框中是否有内容
			var retrieve_content = $('#retrieve_content').val();	//后面要注意编码，因为如果是中文的话，这样传会发生乱码的，那么搜索肯定就匹配不到内容
			window.location = "${pageContext.request.contextPath}/asset/asystem_showallnode?currentPage="+${page.currentPage}+"&showCount="+pagesize+"&retrieve_content="+ encodeURI(encodeURI(retrieve_content));
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