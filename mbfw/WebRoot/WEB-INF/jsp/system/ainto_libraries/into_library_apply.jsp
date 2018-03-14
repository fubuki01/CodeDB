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
		<%@ include file="../admin/top.jsp"%> 
	</head>
<body>
	<form action="asset/${msg }.do" name="intoLibraryApplyForm" id="intoLibraryApplyForm" method="post">
		<div id="zhongxin">
		<input type="hidden" name="flag" id="flag" value="${flag }">
		<input type="hidden" name="id" id="id" value="${ila.id }">
		<input type="hidden" name="device_code" id="device_code"> 
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<c:if test="${flag =='saveI' }">
				<td><label>采购单号</label></td>
				<td style="vertical-align: top;">
					  <select class="chzn-select" name="into_purchase_bill" id="into_purchase_bill" onchange="select_finish_purchase_bill();" data-placeholder="采购单号"style="width: 221px; vertical-align: center">
						<option></option>
						</select>
				</td>
				</c:if>
				<c:if test="${flag =='editI' }">
				<td><label>采购单号</label></td>
				<td><input type="text" name="into_purchase_bill" id="into_purchase_bill" value="${ila.into_purchase_bill }" readonly="readonly"></td>
				</c:if>
				<td><label><a title="由系统自动生成">入库单号</a></label></td>
				<td><input type="text" name="into_code" id="into_code" placeholder="入库单号,由系统自动生成" value="${ila.into_code }" readonly="readonly"></td>		
			</tr>
			<tr>
			    <td><label>入库设备<b style="color: red">*</b></label></td>
			    <td><input type="text" name="into_device" id="into_device" value="${ila.into_device }" readonly="readonly"></td>
		 		<td><label>资产数量<b style="color: red">*</b></label></td>
				<td><input type="text" name="into_count" id="into_count" value="${ila.into_count }" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label>入库人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="into_person" id="into_person" value="${ila.into_person }"></td>
				<td><label>入库日期<b style="color: red">*</b></label></td>
				<td>
					<input  type="text" class="span10 date-picker"  name="into_time" id="into_time" readonly="readonly" data-date-format="yyyy-mm-dd" value="${into_time }" style="width:222px;" placeholder="入库日期(必填)" title="入库日期" />
				</td>
			</tr>
			<tr>
				<td><label>存放地点</label></td>
				<td style="vertical-align:top;">
				  <select class="chzn-select" name="into_house" id="into_house" data-placeholder="存放地点" style="width: 221px;vertical-align: center">
					<option ></option>
					<option  <c:if test="${ila.into_house == '存放库位A' }">selected</c:if> >存放库位A</option>
					 <option  <c:if test="${ila.into_house == '存放库位S' }">selected</c:if> >存放库位S</option>
					<option  <c:if test="${ila.into_house == '存放库位D' }">selected</c:if> >存放库位D</option>
				  </select>
				</td>
				<td><label>总金额<b style="color: red">*</b></label></td>
			    <td colspan="3"><input type="text" name="asset_total_money" id="asset_total_money"  readonly="readonly"  value="${ila.asset_total_money }" style="width: 221px;" ></td>
			</tr>
			<tr>
				<td><label>备注：</label></td>
				<td colspan="4"><textarea rows="3" cols="40" name="into_note" id="into_note" style="width: 87%">${ila.into_note }</textarea></td>
			</tr>

			 <tr style="height: 40">
				<td style="text-align: center;" colspan="4">
				  <a class="btn btn-mini btn-primary" onclick="save();">提交</a>
				  <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增供应商";
			 diag.URL = '<%=basePath%>provider/apl_provider_insert.do';
			 diag.Width = 230;
			 diag.Height = 425;
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
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>user/goEditU.do?USER_ID='+user_id;
			 diag.Width = 225;
			 diag.Height = 415;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
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
			$('.date-picker').datepicker({
				todayHighlight : true,
				startDate: "${into_time }"
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
		
		</script>
	<script type="text/javascript">
	$(top.hangge());
	
	var finish_purchase_bill_info='${finish_purchase_bill_info}';
	var jsons = JSON.parse(finish_purchase_bill_info);
	var pc ='${purchase_code}';
	// ---- 查询采购申请通过的表 多级联动}项目名称下拉框
	$.each(jsons,function(key,value){
		$.each(value,function(key,value){
			var option = document.createElement("option");
			option.innerHTML = key+"";
			$("#into_purchase_bill").append(option);
			 if(key == pc){
				option.selected=true;
				select_finish_purchase_bill();
			} 
		});
		$("#into_purchase_bill").trigger("liszt:update");
	});
	
	// 自动填写
 	function select_finish_purchase_bill(){
		
		var into_purchase_bill= document.getElementById("into_purchase_bill");
		var options = into_purchase_bill.options;
		var key_intolibrary = options[into_purchase_bill.selectedIndex].innerHTML;

		$.each(jsons,function(key,value){
			$.each(value,function(key,value){
				
				if(key == key_intolibrary){
					var splits = new Array();
					splits= value.split("@");
					var total_money=splits[1] * splits[2];
					$("#into_device").val(splits[0]);
					$("#into_count").val(splits[1]);
					$("#asset_total_money").val(total_money);
					$("#device_code").val(splits[3]);
				}
			});
			
		}); 
	} 
	
	//保存
	function save(){
	 	if($("#into_purchase_bill").val()==""){
			$("#into_purchase_bill").tips({
				side:3,
	            msg:'请输入采购单号',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#into_purchase_bill").focus();
			$("#into_purchase_bill").val('');
			$("#into_purchase_bill").css("background-color","white");
			return false;
		}else{
			$("#into_purchase_bill").val($.trim($("#into_purchase_bill").val()));
		}
		 
		
		
		if($("#into_device").val()==""){
			
			$("#into_device").tips({
				side:3,
	            msg:'请输入设备',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#into_device").focus();
			return false;
		}
		
		if($("#into_count").val()==""){
			
			$("#into_count").tips({
				side:3,
	            msg:'请输入设备数量',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#into_count").focus();
			$("#into_count").val('');
			return false;
		}
		
		if($("#into_person").val()==""){
			
			$("#into_person").tips({
				side:3,
	            msg:'请输入入库人',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#into_person").focus();
			$("#into_person").val('');
			return false;
		}
		
		if($("#into_time").val()==""){
			
			$("#into_time").tips({
				side:3,
	            msg:'请输入库时间',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#into_time").focus();
			return false;
		}
		
		 if($("#into_house").val()==""){
			
			$("#into_house").tips({
				side:3,
	            msg:'请输入存放地点',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#into_house").focus();
			return false;
		} 
		
		if($("#asset_total_money").val()==""){
			
			$("#asset_total_money").tips({
				side:3,
	            msg:'请输入总金额',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_total_money").focus();
			return false;
		}
		
		
		else{
			$("#intoLibraryApplyForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	</script>
	</body>
</html>