<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
					
	<form action="asset/saveRAR.do" name="RARForm" id="RARForm"
		method="post">
		<input type="hidden" name="id" id="id"
			value="${pd.id }" />
		<div id="zhongxin">
						<table id="table_report"
							class="text-table table table-striped table-bordered table-hover">
							<tbody>
								<!-- 开始循环 -->
								<tr>
									<td><label>资产编码<b style="color: red">*</b>
									</label>
									<td><select class="chzn-select" name="asset_code"  id="asset_code"
										name="asset_code" data-placeholder="选择资产编码"
										onchange="A();">
										<option></option>
									<c:forEach items="${By_code }" var="pf" varStatus="vs">
										<option value="${pf.asset_code }">${pf.asset_code }</option>
									</c:forEach>
									</select></td>
									<td><label>资产名称<b style="color: red">*</b>
									</label>
									</td>
									<td><input type="text" id="asset_name"
										name="asset_name" placeholder="使用人" style="color:#000000;"
										class="col-xs-10 col-sm-5" onfocus="this.blur()">
									</td>
									<td><label>报修时间<b style="color: red">*</b>
									</label>
									</td>
									<td><input class="span10 date-picker" name="repair_time" 
										id="repair_time" type="text" data-date-format="yyyy-mm-dd"
										style="width: 221px;color:#000000;" placeholder="报修时间" /></td>
								</tr>
								<tr>
									<td><label>使用人<b style="color: red">*</b>
									</label>
									</td>
									<td><input type="text" id="asset_person" style="color:#000000;"
										name="asset_person" placeholder="使用人"
										class="col-xs-10 col-sm-5">
									</td>
								
									<td><label>公司名称<b style="color: red">*</b>
									</label></td>
									<td><input type="text" id="asset_use_company" style="color:#000000;"
										name="asset_use_company" placeholder="公司名称"
										class="col-xs-10 col-sm-5"></td>

									<td><label>报修部门<b style="color: red">*</b>
									</label>
									</td>
									<td><input type="text" id="asset_use_dept" style="color:#000000;"
										name="asset_use_dept" placeholder="报修部门"
										class="col-xs-10 col-sm-5">
									</td>
								</tr>
								<tr>
									<td><label>维修结果</label>
									</td>
									<td><input type="text" id="maintain_result" style="color:#000000;"
										name="maintain_result" readonly="readonly" value="未维修"
										class="col-xs-10 col-sm-5">
									</td>
							

									<td><label>故障现象<b style="color: red">*</b>
									</label>
									</td>
									<td colspan="3"><textarea   id="fault_phenomen" 
											name="fault_phenomen" style="width: 95%;color:#000000;"></textarea></td>
								<tr>
									<td ><label>故障原因</label>
									</td>
									<td colspan="5"><textarea id="fault_reason" 
											name="fault_reason" style="width: 95%;color:#000000;"></textarea></td>
								</tr>

								<tr>
									<td style="text-align: center;" colspan="10"><a
										class="btn btn-mini btn-primary" onclick="save();">提交</a> <a
										class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</tbody>

						</table>

					</div>
			<!--/row-->
				<div id="zhongxin2" class="center" style="display: none">
					<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
					<h4 class="lighter block green"></h4>
					</div>
				<!--/row-->
		</form>
			</div>
			<!--/#page-content-->
		</div>
</div>

				


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

	<script type="text/javascript">
	function A(){
		
		$.ajax({
			type:"POST",
			url:'<%=basePath%>asset/isAssetUserExist.do',
			data:{"asset_code":$("#asset_code").val()},
			cache:false,
			success:function(data){
				
				$("#asset_person").val(data.user);
			}
		});
		
		$.ajax({
			type:"POST",
			url:'<%=basePath%>asset/isAssetNameExist.do',
			data:{"asset_code":$("#asset_code").val()},
			cache:false,
			success:function(data){
				
				$("#asset_name").val(data.name);
			}
		});
		$.ajax({
			type:"POST",
			url:'<%=basePath%>asset/isAssetGongsiExist.do',
			data:{"asset_code":$("#asset_code").val()},
			cache:false,
			success:function(data){
				
				$("#asset_use_company").val(data.company);
				$("#asset_use_dept").val(data.dept);
			}
		});
	}
	
	
	var zn = '${institutionInfo}';
		var jsons = JSON.parse(zn)  
		//开始进入 初始化公司下拉框
		$.each(jsons,function(key, value){
			$.each(value,function(key, value){
	 			var option = document.createElement("option");
            option.innerHTML = key+"";
            $("#bank_name").append(option);
	 		});
			//select_company();
		}); 
		//申请公司的点击change事件
		function select_company() {
			var query_field = document.getElementById("bank_name");
	        var query_condition = document.getElementById("department");
			var options = query_field.options;
			var company_name = options[query_field.selectedIndex].innerHTML;
			query_condition.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key==company_name){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var option = document.createElement("option");
	 				        option.innerHTML = te+"";
	 				        $("#department").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#department").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
		
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