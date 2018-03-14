<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<script type="text/javascript">
	function tijiao() {
		alert("设置成功");
	}
</script>

<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="" method="post" name="processaddform" id="processaddform">
						<!-- 用个隐藏的input保存添加了多少个审批节点 -->
						<input type="hidden" value="1" name="savenodenumber" id="savenodenumber"> 
						<!-- 用一个input来保存点击的是哪一个流程ID -->
						<input type="hidden" value="${processcontent.process_Id}" name="process_Id" id="process_Id"> 
						<table >
							<tr>
								<td>
									<div class="col-xs-6">
									<h4 class="lighter">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a data-toggle="modal" class="blue">流程名称</a>
									</h4>
									<input name="process_Name" id="process_Name" type="text" class="width-40" placeholder="请输入流程名称" value="${processcontent.process_Name}" disabled="disabled"/>
									</div>
								</td>
								
								<td>
									<div class="col-xs-6">
										<h4 class="lighter">
										<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
										<a data-toggle="modal" class="blue">流程类型 </a>
										</h4>
									<select id="process_Type"  name="process_Type" disabled="disabled">
										<c:if test="${processcontent.process_Type=='审批流程' }">
											<option value="审批流程" selected="selected">审批流程</option>
											<option value="工作流程">工作流程</option>			
										</c:if> 
										<c:if test="${processcontent.process_Type=='工作流程' }">
											<option value="审批流程" >审批流程</option>
											<option value="工作流程" selected="selected">工作流程</option>			
										</c:if>								
									</select>
									</div>						
								</td>
							</tr>
							
							<tr>
								<td>
									<h4 class="lighter">
										<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
										<a data-toggle="modal" class="blue">流程内容 </a>
										</h4>
								</td>	
							</tr>				
						</table>
							<textarea name="process_Content" id="process_Content" rows="4" cols="" style="width: 100%" placeholder="请输入审批流程内容" title="审批流程内容" disabled="disabled">${processcontent.process_Content}</textarea>				
						<hr />
						<!-- 关于审批流程中节点管理 -->
						<h4 class="lighter">
							<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
							<a data-toggle="modal" class="blue">流程详情 </a>
							<div style="float: right;"> 
							<button  class="btn btn-danger" type="button" onClick="clearProcessDetail();">清空详情</button>
							</div>
						</h4>
						<table id="processnodedetail"
							class="text-table table table-striped table-bordered table-hover">
							<!-- 开始循环 -->
							<thead>
								<tr>
									<th>审批顺序</th>
									<th>审批节点</th>
									<th>是否可回退</th>
									<th>审批操作</th>
								</tr>
							</thead>

							<tbody id="addprocessnode">
								<!-- 循环遍历显示所有的信息 -->
								<c:forEach items="${currentnode}" var="currentnode" varStatus="currentindex">
									<tr>
										<td>${currentindex.index+1}</td>
										<td id="selectnode" name="selectnode">
											<select id="processdetail_NodeId${currentindex.index}" name="processdetail_NodeId${currentindex.index}" disabled="disabled">										
													<c:forEach var="nodeinfo" items="${allappropeople}">	
														<c:choose>
														<c:when test="${currentnode.processdetail_NodeId == nodeinfo.node_Id}">
															<option value="${nodeinfo.node_Id}" selected="selected">${nodeinfo.node_Name}</option>
														</c:when>
														<c:otherwise>
															<option value="${nodeinfo.node_Id}">${nodeinfo.node_Name}</option>
														</c:otherwise>	
														</c:choose>					
													</c:forEach> 																							
										 	 </select>									
										</td>
										<td id="checkboxinput" name="checkboxinput">
										<!-- 标识是否可以被打回 -->
											<c:choose>
											    <c:when test="${currentnode.processdetail_Back == 'on'}">
													<label class="col-sm-0" style="text-align:center">
													<input id="processdetail_Back${currentindex.index}" name="processdetail_Back${currentindex.index}"  type="checkbox" checked="checked"
													class="ace ace-switch ace-switch-5" disabled="disabled" > 
													<span class="lbl middle"></span>
													</label>
												</c:when>
												<c:otherwise>
													<label class="col-sm-0" style="text-align:center">
													<input id="processdetail_Back${currentindex.index}" name="processdetail_Back${currentindex.index}"  type="checkbox"
													class="ace ace-switch ace-switch-5" disabled="disabled"> 
													<span class="lbl middle"></span>
													</label>
												</c:otherwise>	
											</c:choose>
										</td>
										<!-- 对于审批详情节点中操作 -->
										<td style="text-align: center;" name="nodeoption" id="nodeoption" >
											<!-- 上移 -->
											<a name="upbtn${currentindex.index}" id="upbtn${currentindex.index}" class="btn btn-mini btn-info" onclick="upNode('${currentindex.index}');" title="上移" style="margin-right: 20px;"><i class="icon-arrow-up" ></i></a>
											<!-- 下移 -->
											<a name="downbtn${currentindex.index}" id="downbtn${currentindex.index}" class="btn btn-mini btn-info" onclick="downNode('${currentindex.index}');" title="下移" style="margin-rigth: 20px;"><i class="icon-arrow-down" ></i></a>
											<!-- 删除 -->
											<a name="delebtn${currentindex.index}" id="delebtn${currentindex.index}" class="btn btn-mini btn-danger" onclick="deleteNodeSimple('${currentindex.index}');" title="删除" style="margin-left: 20px;"><i class="icon-trash"></i></a>										
										</td>
									</tr>	
								</c:forEach>						
							</tbody>
						</table>
					</form>
						<!-- 底部操作按钮 -->
						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tbody>
									<tr>
										<td>
											<button class="btn btn-success" type="button"
												onClick="addProcessNode();">
												<i class="ace-icon fa fa-check bigger-110"></i> 增加步骤
											</button>	
										</td>
										<td>							
											<button class="btn btn-danger" type="button"
												onClick="editContent();">
												<i class="ace-icon fa fa-check bigger-110"></i> 修改内容
											</button>
										</td>
										<td>
											<button class="btn btn-info" type="button"
												onClick="submmitContent();">
												<i class="ace-icon fa fa-check bigger-110"></i> 提交设置
											</button>																				
										</td>
										<td>
											<button class="btn btn-warning" type="button"
												onClick="backToShow();">
												<i class="ace-icon fa fa-check bigger-110"></i> 返回显示界面
											</button>
										</td>
									</tr>
					
								</tbody>
							</table>
						</div>

						<!-- PAGE CONTENT ENDS HERE -->
				</div>
				<!--/row-->

			</div>
			<!--/#page-content-->
		</div>
		<!--/.fluid-container#main-container-->

		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>

		<!-- 引入 -->
		<script type="text/javascript">
			window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
		</script>
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
		<!--  wizard-->
		<link rel="stylesheet" href="static/css/wizard_ace.min.css" />
		<link rel ="stylesheet" href ="static/css/bootstrap.min.css"/>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!--提示框-->
		<script type="text/javascript">
			$(top.hangge());
			//点击添加按钮的操作
			function addProcessNode(){
				//首先判断是否是处于修改状态,则可以用输入框是否是可编辑状态判断
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					alert("如果要修改内容，请先点击修改按钮！");
				}
				else{
				//（1）首先获取已经有多少个步骤，即获取tbody中的tr的长度
				var leng = $("#addprocessnode").children().length;
				 var $addnode = $('<tr>'+
					'<td>'+(leng+1)+'</td><td>'+
						'<select id="processdetail_NodeId'+leng+'" name="processdetail_NodeId'+leng+'">'+
						'<c:forEach var="nodeinfo" items="${allappropeople}">'+
						'<option value="${nodeinfo.node_Id}">${nodeinfo.node_Name}</option>'+
						'</c:forEach>'+
				 		'</select>'+
					'</td>'+
					'<td>'+
						'<label class="col-sm-0" style="text-align: center">'+
						'<input name="processdetail_Back'+leng+'" id="processdetail_Back'+leng+'" checked="checked" type="checkbox"'+
							'class="ace ace-switch ace-switch-5">'+
						'<span class="lbl middle"></span>'+
						'</label>'+
					'</td>'+
					'<td style="text-align: center;">'+
				 	'<a name="downbtn'+leng+'" id="downbtn'+leng+'" class="btn btn-mini btn-info" onclick="upNode('+(leng)+');" title="上移" style="margin-right: 20px;"><i class="icon-arrow-up" ></i></a>'+
					'<a name="downbtn'+leng+'" id="downbtn'+leng+'" class="btn btn-mini btn-info" onclick="downNode('+(leng)+');" title="下移" style="margin-rigth: 20px;"><i class="icon-arrow-down" ></i></a>'+
					'<a name="delebtn'+leng+'" id="delebtn'+leng+'" class="btn btn-mini btn-danger" onclick="deleteNodeSimple('+(leng)+');" title="删除" style="margin-left: 20px;"><i class="icon-trash"></i></a>'+									
				'</td>'+
				'</tr>');   	
			  $addnode.appendTo($("#addprocessnode"));
			  alert("添加成功!"); 
				}
			}
			//点击修改按钮的操作
			function editContent(){
				//改变不可修改的内容为可修改的
				$('#process_Name').removeAttr('disabled');  //流程名称
				$('#process_Content').removeAttr('disabled');  //流程内容
				$('#process_Type').removeAttr('disabled');      //流程类型
				$('#selectnode select').removeAttr('disabled');  //审核节点下拉列表
				$('#checkboxinput input').removeAttr('disabled'); //是否可退回
			}
			//点击清空按钮的操作
			function clearProcessDetail(){
				//首先判断是否是可修改的状态
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					alert("如果要清空详情内容，请先点击修改按钮！");
				}
				else{
					if(confirm("确认要清空所有的流程内容吗？")){
						$("#addprocessnode").children().remove();
					}				
				}
			}
			//点击提交按钮的操作
			function submmitContent(){
				//首先判断是否是可修改的状态，如果不是的话，就不需要进行修改什么内容，直接返回到显示页面当中
				//首先判断是否是处于修改状态,则可以用输入框是否是可编辑状态判断
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					window.location = "${pageContext.request.contextPath}/asset/asystem_showapprovalprocess";
				}
				//如果是修改状态，那么就将数据进行保存处理
				else{
					//(1)先判断是否填入的信息符合规范
					//流程名称
					if($('#process_Name').val() == "" || $('#process_Name').val() == null){
						$("#process_Name").tips({
							side:3,
				            msg:'请输入流程名称',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#process_Name").focus();
						return false;
					}
					//流程内容
					if($('#process_Content').val() == "" || $('#process_Content').val() == null){
						$("#process_Content").tips({
							side:3,
				            msg:'请输入流程内容',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#process_Content").focus();
						return false;
					}
					//判断添加的审批节点是否存在重复的节点(便利判断option中的value是否存在一样)
					var repeatSuccess = false;
					//获取每个下拉列表选中的值
					$('#addprocessnode tr').each(function(key1 , value){
						var currentValue = $(this).children("td").children("select").val();
						$('#addprocessnode tr').each(function(key2 , value){
							if(key1 != key2){
								var nextValue = $(this).children("td").children("select").val();
								if(currentValue == nextValue){
									alert("审批节点中:["+(key1+1)+"]与["+(key2+1)+"]重复，请确认！");
									repeatSuccess = true;
									return false;
								}
							}						
						});
						//跳出循环
						if(repeatSuccess){
							return false;
						}
					});
					//返回整个判断程序
					if(repeatSuccess){
						return false;
					}
									
					//(2)进行页面跳转
					//要获得审批节点中的个数，就是tr的条数，方便后台进行遍历搜索的次数
					var number = $("#addprocessnode").children().length;
					$('#savenodenumber').val(number);
					$('#processaddform').attr({action:'asset/asystem_updateprocessdetail.do'});
					$('#processaddform').submit();
				}
			}
			
			//返回显示所有流程的界面
			function backToShow(){
				window.location = "${pageContext.request.contextPath}/asset/asystem_showapprovalprocess";
			}
			
			//处理上移按钮的操作,参数clickindex为点击的是第几个，从0开始
			function upNode(clickindex){
				//首先判断是否处于可修改内容状态
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					alert("如果要进行操作，请先点击修改按钮！");
				}
				else{
				//(1)如果点击的位置是0，那么就不能再上移
				if(clickindex == 0){
					alert("已经在第一个，不能再上移了哦！");
				}
				//(2)非第一个的情况 
				else{
					//(3)改变上下两个位置的所有信息内容
					//获取到当前点击行的所有信息
					var currentNodeId = $('#processdetail_NodeId'+clickindex).val();
					var currentNodeName = $('#processdetail_NodeId'+clickindex +' option:checked').text();
					var currentNodeBack = $('#processdetail_Back'+clickindex).attr("checked");
					//获取点击行对应的上一行的所有信息，获取上一行的索引
					lastindex = parseInt(clickindex) -1;
					var lastNodeId = $('#processdetail_NodeId'+(lastindex)).val();
					var lastNodeName = $('#processdetail_NodeId'+(lastindex) +' option:checked').text();
					var lastNodeBack = $('#processdetail_Back'+(lastindex)).attr("checked");
					//(4)交换当前行和上一行中标签对应的ID属性和Name属性的值
					//修改当前节点的内容，其实就是交换当前行和上一行的数据内容
					$('#processdetail_NodeId'+clickindex).val(lastNodeId);
					$('#processdetail_NodeId'+clickindex +' option:checked').text(lastNodeName);
					//如果点击节点上面的是点击Yes状态，那么就进行相应的修改即可
					if(lastNodeBack == "checked"){
						$('#processdetail_Back'+clickindex).attr({"checked":"checked"});
					}
					else{
						$('#processdetail_Back'+clickindex).removeAttr("checked");
					}
					//修改上一个节点的内容，其实就是交换当前行和上一行的数据内容
					$('#processdetail_NodeId'+(lastindex)).val(currentNodeId);
					$('#processdetail_NodeId'+(lastindex) +' option:checked').text(currentNodeName);
					if(currentNodeBack == "checked"){
						$('#processdetail_Back'+(lastindex)).attr({"checked":"checked"});
					}
					else{
						$('#processdetail_Back'+(lastindex)).removeAttr("checked");
					}
				  }
				}
			}
			//处理下移按钮的操作,参数clickindex为点击的是第几个，从0开始
			function downNode(clickindex){
				//首先判断是否处于可修改内容状态
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					alert("如果要进行操作，请先点击修改按钮！");
				}
				else{
				//(1)首先判断是否是点击的最后一个，如果是，那么就无法再进行下移操作
				//拿到一共有多少条数据
				var leng = $("#addprocessnode").children().length;			
				if((leng-1) == clickindex){  //-1判断是因为后面的是从0开始算
					alert("已经是最后一个，无法再进行下移哦");
				}
				//(2)非最后一个，再进行后面的操作，这个同上移的内容是一样的
				else{
					//获取到当前点击行的所有信息
					var currentNodeId = $('#processdetail_NodeId'+clickindex).val();
					var currentNodeName = $('#processdetail_NodeId'+clickindex +' option:checked').text();
					var currentNodeBack = $('#processdetail_Back'+clickindex).attr("checked");
					//获取点击行对应的下一行的所有信息,要进行转型才能加减
					var nextindex = parseInt(clickindex) +1;
					var lastNodeId = $('#processdetail_NodeId'+(nextindex)).val();
					var lastNodeName = $('#processdetail_NodeId'+(nextindex) +' option:checked').text();
					var lastNodeBack = $('#processdetail_Back'+(nextindex)).attr("checked");
					//(4)交换当前行和下一行中标签对应的ID属性和Name属性的值
					//修改当前节点的内容，其实就是交换当前行和下一行的数据内容				
					 $('#processdetail_NodeId'+clickindex).val(lastNodeId);
					$('#processdetail_NodeId'+clickindex +' option:checked').text(lastNodeName);
					//如果点击节点上面的是点击Yes状态，那么就进行相应的修改即可
					if(lastNodeBack == "checked"){
						$('#processdetail_Back'+clickindex).attr({"checked":"checked"});
					}
					else{
						$('#processdetail_Back'+clickindex).removeAttr("checked");
					}
					//修改下一个节点的内容，其实就是交换当前行和下一行的数据内容
					$('#processdetail_NodeId'+(nextindex)).val(currentNodeId);
					$('#processdetail_NodeId'+(nextindex) +' option:checked').text(currentNodeName);
					if(currentNodeBack == "checked"){
						$('#processdetail_Back'+(nextindex)).attr({"checked":"checked"});
					}
					else{
						$('#processdetail_Back'+(nextindex)).removeAttr("checked");
					} 
				  }
				}
			}
			//处理删除单个审批节点的操作,参数clickindex为点击的是第几个，从0开始
			function deleteNodeSimple(clickindex){
				//首先判断是否处于可修改内容状态
				if(typeof($('#process_Name').attr('disabled')) != "undefined"){
					alert("如果要进行操作，请先点击修改按钮！");
				}
				else{
				//弹出一个确认框
				if(confirm("确认要删除该条审批信息吗？")){
					//(1)获取已经存在的条数
					var number = $('#addprocessnode').children().length;				
					//(2)删除点击的那行的数据
					/* $('#addprocessnode tr').eq(clickindex).remove(); */
					//(3)将删除索引后面的信息进行移动交换(其实就是后面的把前面的进行覆盖)
					var currentNodeId = "";
					var currentNodeName="";
					var currentNodeBack="";
					clickindex = parseInt(clickindex); //将字符串型的clickindex转为int
					for(var i = clickindex + 1 ; i < number ; i++ ){  //这里将i=clickindex+1进行处理，是因为想获取后面的内容，再来覆盖前面的内容
						//获取到当前行的所有信息
						currentNodeId = $('#processdetail_NodeId'+i).val();
						currentNodeName = $('#processdetail_NodeId'+i +' option:checked').text();
						currentNodeBack = $('#processdetail_Back'+i).attr("checked");
						//将前面的数据进行依次覆盖
						 $('#processdetail_NodeId'+(i-1)).val(currentNodeId); //这里-1是因为，在for的时候首先+1了，那么减一才代表上一个
						 $('#processdetail_NodeId'+(i-1) +' option:checked').text(currentNodeName);
						 if( currentNodeBack =="checked"){
							 $('#processdetail_Back'+(i-1)).attr("checked");
						 }
						 else{
							 $('#processdetail_Back'+(i-1)).removeAttr("checked");
						 }
					}
					//删除最后一行的数据就可以了(因为我是通过后者一直覆盖)
					$('#addprocessnode tr').eq((number-1)).remove();
				   }
				}
			}
		</script>
</body>
</html>

