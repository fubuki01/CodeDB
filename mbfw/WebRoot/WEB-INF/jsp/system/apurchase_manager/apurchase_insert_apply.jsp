<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<%@ include file="../admin/top.jsp"%> 
		<script type="text/javascript">
		$(document).ready(function(){
			//alert('${file_address}');
			if($("#user_id").val()!=""){
				$("#loginname").attr("readonly","readonly");
				$("#loginname").css("color","gray");
			}
		});
			function change_file(value){
				$(".file_name").hide();
			}
		</script>
	</head>
<body>
	<form action="asset/${msg }.do" name="apurchaseMForm" id="apurchaseMForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<input type="hidden" name="flag" id="flag" value="${flag }">
		<input type="hidden" name="id" id="id" value="${pd.id }">
		<input type="hidden" name="apply_id" id="apply_id" ><!-- 查找项目id,更新状态 -->
		<input type="hidden" name="file_hidden" id="file_hidden" >
		<input type="hidden" name="file_address" id="file_address" value="${file_address }">
		<input type="hidden" name="device_code" id="device_code"> 
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<c:if test="${flag == 'editP' }">
				<td colspan="6"><label><b style="font-weight: 20px;color:red"><h3>修改采购申请<h3></h3></b></label></td>
				</c:if>
				<c:if test="${flag == 'saveP' }">
				<td colspan="6"><label><b style="font-weight: 20px;color:red"><h3>采购申请<h3></h3></b></label></td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${flag =='saveP' }">
				<td><label>项目名称 </label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="project_name" id="project_name" onchange="select_apply_name();"data-placeholder="项目名称" style="width: 221px;vertical-align: center">
					<option ></option>
					
					</select>
				</td> 
				</c:if>
				<c:if test="${flag =='editP' }">
				<td><label>项目名称 </label></td>
				<td><input type="text" name="project_name" id="project_name" value="${pd.project_name }" readonly="readonly"></td>
				</c:if>
				<td><label>资产名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_asset_name" id="purchase_asset_name" value="${pd.purchase_asset_name }" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label>资产类别<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_asset_class" id="purchase_asset_class" value="${pd.purchase_asset_class }" readonly="readonly"></td>
				<td><label>资产数量<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_asset_count" id="purchase_asset_count" value="${pd.purchase_asset_count }" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label>供应商<b style="color: red">*</b></label></td>
				<td>
					<input type="text" name="provider" id="provider" value="${pd.provider_name}:${pd.provider_code}""  readonly="readonly" data-placeholder="供应商代码:供应商">
				</td>

				<td><label>采购单价<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_price" id="purchase_price" value="${pd.purchase_price }" readonly="readonly"></td>
				
			</tr>
			
			<tr>
				<td><label>采购人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_person" id="purchase_person" value="${pd.purchase_person }"></td>
				<td><label>验收人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="check_person" id="check_person" value="${pd.check_person }"></td>
			</tr>
			
			<tr>
				<td><label>采购方式<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="purchase_way" id="purchase_way" data-placeholder="采购方式" style="width: 221px;vertical-align: center">
					<option ></option>
					<option <c:if test="${pd.purchase_way == '采购方式A' }">selected</c:if> >采购方式A</option>
					<option <c:if test="${pd.purchase_way == '采购方式S' }">selected</c:if> >采购方式S</option>
					<option <c:if test="${pd.purchase_way == '采购方式D' }">selected</c:if> >采购方式D</option>
					</select>
				</td>
				<td><label>交货日期${delivery_time }<b style="color: red">*</b></label></td>
				<td>
					<input type="text" class="span10 date-picker" name="delivery_date" id="delivery_date" readonly="readonly" value="${delivery_time }" data-date-format="yyyy-mm-dd"  style="width:218px;" placeholder="交货日期" title="交货日期"/>
				</td>
			</tr>
			
			<tr>
				<td><label>配送方式<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="dispatch_way" id="dispatch_way" data-placeholder="付款方式" style="width: 221px;vertical-align: center">
					<option ></option>
					<option <c:if test="${pd.dispatch_way == '配送方式A' }">selected</c:if> >配送方式A</option>
					<option <c:if test="${pd.dispatch_way == '配送方式S' }">selected</c:if> >配送方式S</option>
					<option <c:if test="${pd.dispatch_way == '配送方式D' }">selected</c:if> >配送方式D</option>
					</select>
				</td>
				<td><label>资金来源<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="money_from" id="money_from" data-placeholder="资金来源" style="width: 221px;vertical-align: center">
					<option ></option>
					<option <c:if test="${pd.money_from == '资金来源A' }">selected</c:if> >资金来源A</option>
					<option <c:if test="${pd.money_from == '资金来源S' }">selected</c:if> >资金来源S</option>
					<option <c:if test="${pd.money_from == '资金来源D' }">selected</c:if> >资金来源D</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label>付款方式<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="puchase_payway" id="puchase_payway" data-placeholder="付款方式" style="width: 221px;vertical-align: center">
					<option ></option>
					<option  <c:if test="${pd.puchase_payway == '付款方式A' }">selected</c:if> >付款方式A</option>
					<option <c:if test="${pd.puchase_payway == '付款方式S' }">selected</c:if> >付款方式S</option>
					<option <c:if test="${pd.puchase_payway == '付款方式D' }">selected</c:if> >付款方式D</option>
					</select>
				</td>
				<td><label>附件上传<b style="color: red">*</b></label></td>
				<td>
					<c:if test="${flag =='saveP' }">
						<input type="file"  name="file_upload" id="file_upload" multiple style="width: 221px;vertical-align: center" >
					</c:if>
					<c:if test="${flag =='editP' }">
						<input type="file"  name="file_upload" id="file_upload" multiple onchange="change_file(this.value);" style="width: 221px;vertical-align: center" ><br>
						<c:if test="${file_name !='no_file' }">
						<c:forTokens items="${file_name }" delims="#" var="split_file">
							<a class="file_name" id="ss" name="ss">${fn:split(split_file,"@")[1] }</a>&nbsp;
						</c:forTokens> 
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="4"><b style="color: red">上传文件说明:&nbsp;&nbsp;</b><b>重要设备资料、验收资料、合同、票据等文件进行图文上传打包到word文件，再上传</b></td>
			</tr>
			<tr style="height: 40">
				<td style="text-align: center;" colspan="4">
					<a class="btn btn-mini btn-primary" onclick="save();">提交</a>
					<a class="btn btn-mini btn-danger" onclick="project_apply_close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
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
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		function project_apply_close(){
			window.location.href='<%=basePath%>asset/apl_Caigou_apply.do';
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增供应商";
			 diag.URL = '<%=basePath%>provider/apl_provider_insert.do';
			 diag.Width = 900;
			 diag.Height = 538;
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
		


		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker({
				todayHighlight : true,
				startDate: "${delivery_time }"
			});
			

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
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
		
		</script>
<script type="text/javascript">
	$(top.hangge());
	
	var project_manager_info='${project_manager_info}';
	var jsons = JSON.parse(project_manager_info);
	var an ='${apply_name}';
	// ---- 查询采购申请通过的表 多级联动}项目名称下拉框
	$.each(jsons,function(key,value){
		$.each(value,function(key,value){
			var option = document.createElement("option");
			var apply_id=key.split("@")[1];
			option.innerHTML = key.split("@")[0]+"";
			$("#project_name").append(option);
			$("#apply_id").val(apply_id);
			if(key.split("@")[0] == an){
				option.selected=true;
				select_apply_name();
			}
		});
		$("#project_name").trigger("liszt:update");
	});
	
	// 自动填写
 	function select_apply_name(){
		
		var project_name= document.getElementById("project_name");
		var options = project_name.options;
		var key_project = options[project_name.selectedIndex].innerHTML;

		$.each(jsons,function(key,value){
			$.each(value,function(key,value){
				if(key.split("@")[0] == key_project){
					var splits = new Array();
					splits= value.split("@");
					var provider_code = splits[0].split("-")[2];
					var provider_name=splits[3]+":"+provider_code;
					$("#device_code").val(splits[0]);
					$("#purchase_asset_name").val(splits[2]);
					$("#purchase_asset_class").val(splits[1]);
					$("#purchase_asset_count").val(splits[4]);
					$("#purchase_price").val(splits[5]); 
					$("#provider").val(provider_name);
				}
			});
			
		}); 
	} 
	
	//保存
	function save(){
		if($("#purchase_code").val()==""){
			
			$("#purchase_code").tips({
				side:3,
	            msg:'请输入采购单号',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#purchase_code").focus();
			$("#purchase_code").val('');
			$("#purchase_code").css("background-color","white");
			return false;
		}
		
		if($("#purchase_asset_name").val()==""){
			
			$("#purchase_asset_name").tips({
				side:3,
	            msg:'请输入资产名称',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#purchase_asset_name").focus();
			$("#purchase_asset_name").val('');
			$("#purchase_asset_name").css("background-color","white");
			return false;
		}
		
		if($("#purchase_asset_class").val()==""){
			
			$("#purchase_asset_class").tips({
				side:3,
	            msg:'请选择资产类别',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#purchase_asset_class").focus();
			$("#purchase_asset_class").val('');
			$("#purchase_asset_class").css("background-color","white");
			return false;
		}else{
			$("#purchase_asset_class").val($.trim($("#purchase_asset_class").val()));
		}
		
		// 验证资产数量
		if($("#purchase_asset_count").val()==""){
			
			$("#purchase_asset_count").tips({
				side:3,
	            msg:'请输入资产数量',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#purchase_asset_count").focus();
			return false;
		}else if($.trim($("#purchase_asset_count").val() != "")){
			if(!isint($.trim($("#purchase_asset_count").val()))){
				$("#purchase_asset_count").tips({
					side:3,
		            msg:'请输入一个整数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_asset_count").focus();
				return false;
			}
		}
		
		if($("#provider").val()==""){
			
			$("#provider").tips({
				side:3,
	            msg:'请选择供应商',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#provider").focus();
			$("#provider").val('');
			return false;
		}

		//验证价格
		if($("#purchase_price").val()==""){
			
			$("#purchase_price").tips({
				side:3,
	            msg:'请输入采购单价',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#purchase_price").focus();
			$("#purchase_price").val('');
			return false;
		}else if($.trim($("#purchase_price").val())!=""){
			if(!ismoney($.trim($("#purchase_price").val()))){
				$("#purchase_price").tips({
					side:3,
		            msg:'输入价格格式不正确',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purchase_price").focus();
				return false;
			}
		}
		
		if($("#purchase_person").val()==""){
			
			$("#purchase_person").tips({
				side:3,
	            msg:'请输入采购人员',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#purchase_person").focus();
			return false;
		}
		
		if($("#check_person").val()==""){
			
			$("#check_person").tips({
				side:3,
	            msg:'请输入验收人员',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#check_person").focus();
			return false;
		}
		
		if($("#purchase_way").val()==""){
			
			$("#purchase_way").tips({
				side:3,
	            msg:'请输入采购方式',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#purchase_way").focus();
			return false;
		}
		
		if($("#delivery_date").val()==""){
			
			$("#delivery_date").tips({
				side:3,
	            msg:'请选择交货日期',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#delivery_date").focus();
			$("#delivery_date").val('');
			return false;
		}
		
		if($("#dispatch_way").val()==""){
			
			$("#dispatch_way").tips({
				side:3,
	            msg:'请选择运送方式',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#dispatch_way").focus();
			return false;
		}

		if($("#money_from").val()==""){
			
			$("#money_from").tips({
				side:3,
	            msg:'请输入资金来源',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#money_from").focus();
			return false;
		}
		if($("#puchase_payway").val()==""){
			
			$("#puchase_payway").tips({
				side:3,
	            msg:'请选择付款方式',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#puchase_payway").focus();
			return false;
		}
		
		var file_upload = $("#file_upload").val(); // 用于拆分文件
		var f = '${flag}';
		if(f=='editP' && document.getElementById('ss').innerHTML != ""){
			if($("#file_upload").val()==""){
				$("#file_hidden").val("former");
				$("#apurchaseMForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
				return false;
			}else if($("#file_upload").val() != "" || $("#file_upload").val() != null){
				var str = new Array();
				str = file_upload.split("\\");
				var word = new Array();
				word = str[str.length-1].split(".");
				var file_name = word[word.length-1];
				//if(file_name =="doc" || file_name =="docx" || file_name == "pdf" || file_name =="xlsx" || file_name =="xls"){
				$("#apurchaseMForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
					
				/* }else{
					$("#file_upload").tips({
						side:3,
			            msg:'你选择文件格式不正确，请选择word后Excel文件',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#file_upload").focus();
					
				} */
			}
		}
		
	if(f == 'saveP'){
		if($("#file_upload").val()==""){
			
			$("#file_upload").tips({
				side:3,
	            msg:'请选择上传文件',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#file_upload").focus();
			return false;
		}else if($("#file_upload").val() != ""){
			var str = new Array();
			str = file_upload.split("\\");
			var word = new Array();
			word = str[str.length-1].split(".");
			var file_name = word[word.length-1];
			$("#apurchaseMForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		} 
	}
 }
	
</script>
	</body>
</html>