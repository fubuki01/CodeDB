<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%>

<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#asset_code").val()==""){
			
			$("#asset_code").tips({
				side:3,
	            msg:'请输入资产编码',
	            bg:'#AE81FF',
	            time:2
				
	        });
			
			$("#asset_code").focus();
			return false;
		}
		if($("#repair_time").val()==""){
			
			$("#repair_time").tips({
				side:3,
	            msg:'请输入报修时间',
	            bg:'#AE81FF',
	            time:2
				
	        });
			
			$("#repair_time").focus();
			return false;
		}
		if($("#bank_name").val()==""){
			
			$("#bank_name").tips({
				side:3,
	            msg:'请输入银行名称',
	            bg:'#AE81FF',
	            time:2
				
	        });
			
			$("#bank_name").focus();
			return false;
		}
		if($("#department").val()==""){
			
			$("#department").tips({
				side:3,
	            msg:'请输入报修部门',
	            bg:'#AE81FF',
	            time:2
				
	        });
			
			$("#department").focus();
			return false;
		}

	if ($("#asset_person").val() == "") {

			$("#asset_person").tips({
				side : 3,
				msg : '请输入使用人',
				bg : '#AE81FF',
				time : 2

			});

			$("#asset_person").focus();
			return false;
		}
		if ($("#fault_phenomen").val() == "") {

			$("#fault_phenomen").tips({
				side : 3,
				msg : '请输入故障现象',
				bg : '#AE81FF',
				time : 2

			});

			$("#fault_phenomen").focus();
			return false;
		}
		

		if(($("#asset_code").val()!="")&&($("#repair_time").val()!="")&&($("#bank_name").val()!="")&&($
				("#department").val()!="")&&$("#asset_person").val()!=""&&$("#fault_phenomen").val()!=""
					){
			$("#RARForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	function ismail(mail) {
		return (new RegExp(
				/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/)
				.test(mail));
	}


	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					setTimeout("$('#EMAIL').val('')",2000);
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
			data : {
				NUMBER : NUMBER,
				USERNAME : USERNAME,
				tm : new Date().getTime()
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" != data.result) {
					$("#NUMBER").tips({
						side : 3,
						msg : '编号已存在',
						bg : '#AE81FF',
						time : 3
					});
					setTimeout("$('#NUMBER').val('')", 2000);
				}
			}
		});
	}
</script>
</head>
	
<body>
	
	
<div class="container-fluid" id="main-container">

		
			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索ghfh  -->
					
	<form action="asset/editRAR.do" name="RARForm" id="RARForm"
		method="post">
		<input type="hidden" name="id" id="user_id"
			value="${sd.id }" />
		<div id="zhongxin">
	
						<table id="table_report" class="text-table table table-striped table-bordered table-hover">
							<tbody>
									<!-- 开始循环 -->
								
						<tr>
								<td><label>资产编码<b style="color: red">*</b></label></td>
									<td> <input type="text" id="asset_code" name="asset_code" value="${sd.asset_code}" placeholder="资产编码" class="col-xs-10 col-sm-5"></td>
								<td><label>银行名称<b style="color: red">*</b></label></td>
									<td> <input type="text" id="bank_name" name="bank_name" value="${sd.bank_name}" placeholder="银行名称" class="col-xs-10 col-sm-5"></td>
								<td><label>报修部门<b style="color: red">*</b></label></td>
								<td>
								 <input type="text" id="department" name="department" value="${sd.department}" placeholder="报修部门" class="col-xs-10 col-sm-5"></td>
						</tr>
								
								
						<tr>
								<td><label>报修时间<b style="color: red">*</b></label></td>
									<td><input type="text" data-date-format="yyyy-mm-dd" id="repair_time" name="repair_time" value="${sd.repair_time}" placeholder="报修时间" class="span10 date-picker" style="width: 221px;"></td>	
								<td><label>使用人<b style="color: red">*</b></label></td>
									<td> <input type="text" id="asset_person" name="asset_person" value="${sd.asset_person}" placeholder="使用人" class="col-xs-10 col-sm-5"></td>
			
								<td><label>故障现象<b style="color: red">*</b></label></td>
									<td><input type="text" id="fault_phenomen" name="fault_phenomen" value="${sd.fault_phenomen}" placeholder="故障现象" class="col-xs-10 col-sm-5">
								</td>
						</tr>
					
						<tr>
								<td><label>维修结果<b style="color: red">*</b></label>
								</td>
								<td><select id="maintain_result" name="maintain_result" 
									style="">
										<option selected="selected" value="${sd.maintain_result}">${sd.maintain_result}</option>
										<c:if test="${sd.maintain_result != '已维修'}">
										<option value="已维修">已维修</option>
										</c:if>
										<c:if test="${sd.maintain_result != '未维修'}">
										<option value="未维修">未维修</option>
										</c:if>
										<c:if test="${sd.maintain_result != '无法维修'}">
										<option value="无法维修">无法维修</option>
										</c:if>
								</select>
								<td><label>维修完成时间<b style="color: red">*</b></label>
								</td>
								<td><input type="text" data-date-format="yyyy-mm-dd" id="finishi_time" name="finishi_time" value="${sd.finishi_time}" placeholder="维修完成时间"
									class="span10 date-picker" style="width: 221px;">
								</td>
								<td><label>维修机构<b style="color: red">*</b></label>
								</td>
								<td><input type="text" id="drep_department" name ="drep_department" value="${sd.drep_department}" placeholder="维修机构"
									class="col-xs-10 col-sm-5">
								</td>
						</tr>
						<tr>
								<td><label>保修截止时间<b style="color: red">*</b></label>
								</td>
								<td><input type="text" data-date-format="yyyy-mm-dd" id="defect_time" name="defect_time" value="${sd.defect_time}" placeholder="保修时间"
									class="span10 date-picker" style="width: 221px;">
								</td>
								<td><label>保修状态<b style="color: red">*</b></label>
								</td>
								<td><select id="status" name="status" 
									style="display: none : ”">
										<option selected="selected" value="${sd.status}">${sd.status}</option>
										<c:if test="${sd.status != '在保修期'}">
										<option value="在保修期">在保修期</option>
										</c:if>
										<c:if test="${sd.status != '已过保修期'}">
										<option value="已过保修期">已过保修期</option>
										</c:if>
									</select></td>
								<td><label>维修费用<b style="color: red">*</b></label>
								</td>
								<td><input type="text" id="cost" name ="cost" value="${sd.cost}" placeholder="维修费用"
									class="col-xs-10 col-sm-5">
								</td>
						</tr>
						<tr>
									<td><label>故障原因</label></td>
									<td colspan="5"><textarea rows="2" id="fault_reason" name="fault_reason"
									style="width: 95%">${sd.fault_reason}</textarea>
									</td>
						</tr>
						<tr>
									<td><label>备注</label></td>
									<td colspan="5"><textarea rows="2" id="remark" name="remark"
									style="width: 95%">${sd.remark}</textarea>
									</td>
						</tr>
	
							<tr>	
							<td style="text-align: center;" colspan="10"><a
							class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
							class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
			                </tr>
							</tbody>
								
						</table>

						<!-- PAGE CONTENT ENDS HERE -->
				</div>
				<!--/row-->
		</form>
			</div>
			<!--/#page-content-->
		</div>
</div>

						<!-- PAGE CONTENT ENDS HERE -->
			
				<!--/row-->


	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript"
	src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 下拉框 -->

	<script type="text/javascript">
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
	</script>

</body>
</html>