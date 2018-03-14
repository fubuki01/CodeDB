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
	<form action="product/${msg }.do" name="productForm" id="productForm" method="post">
		<div id="zhongxin">
		<input type="hidden" name="flag" id="flag" value="${flag }">
		<input type="hidden" name="id" id="id" value="${pd.id }">
		<input type="hidden" name="coding" id="coding" value="${coding }">
		<input type="hidden" name="pcode" id="pcode" value="${pd.product_code }">
		<table  id="table_report" class="table text-table table-striped table-bordered">
			<tr>
				<td colspan="4"><h1 style="color:red;">产品修改</h1></td>
			</tr>
			<c:if test="${flag=='updateP' && onlyedit !='onlyedit'}">
			<tr>
				<td><label>供应商<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="provider"  id="provider"style="width: 170px;vertical-align: center"  data-placeholder="供应商代码" >
						<option ></option>
					<c:forEach items="${providerFind}" var="pf" varStatus="vs">
							<option value="${pf.provider_code }:${pf.provider_name }" <c:if test="${pd.provider_code == pf.provider_code }">selected</c:if> >${pf.provider_code }:${pf.provider_name }</option>
					</c:forEach>
					</select>
					<a class="btn btn-small btn-success" onclick="add();" style="vertical-align:top;">新增</a>
				</td> 
				<td><label>产品标记<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
				<select class="chzn-select" name="product_flag"  id="product_flag" onchange="select_asset_class1(this.value);"style="width: 218px;vertical-align: center"  data-placeholder="产品标记" >
					<option value="固定资产" <c:if test="${pd.product_flag == '固定资产'}">selected</c:if>  >固定资产</option>  
					<option value="耗材资产"   <c:if test="${pd.product_flag == '耗材资产'}">selected</c:if>  >耗材资产</option>
				</select>
				</td> 
			</tr>
			<tr>
				<td><label>产品类别<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
					<select class="chzn-select" data-placeholder="产品类别"name="product_class" id="product_class" onchange="select_product_class_name1();">
					<option></option>
					</select>
				</td>
					
				<td><label>产品名称<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
					<select class="chzn-select" name="select_product" id="select_product"  data-placeholder="请选择产品名称"style="width: 221px; vertical-align: center;">
						<option></option>						
					</select>
				</td>
			</tr>
			</c:if>
			<c:if test="${onlyedit=='onlyedit' }">
			<tr>
				<td><label>供应商<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_name" id="provider_name" value="${pd.provider_name }"></td>
				<td><label>产品标记<b style="color: red">*</b></label></td>
				<td><input type="text" name="product_flag" id="product_flag" value="${pd.product_flag }" readonly></td>
			</tr>
			<tr>
				<td><label>产品类别<b style="color: red">*</b></label></td>
				<td><input type="text" name="product_class" id="product_class" value="${pd.product_class }" readonly></td>
				<td><label>产品名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="product_name" id="product_name" value="${pd.product_name }" readonly></td>
			</tr>
			</c:if>
			
			<tr>
				<td><label>产品价格<b style="color: red">*</b></label></td>
				<td><input type="text" name="product_price" id="product_price" <c:if test="${onlyedit=='onlyedit' }">readonly</c:if> value="${pd.product_price }"   placeholder="产品价格"></td>
				<td><label>计量单位<b style="color: red">*</b></label></td>
				<td><input type="text" name="product_unit" id="product_unit"  <c:if test="${onlyedit=='onlyedit' }">readonly</c:if> value="${pd.product_unit }"  placeholder="计量单位"></td>
			</tr>
			
			
			
			<tr>
				<td><label>产品型号</label></td>
				<td>
					 <input type="text" name="product_type" id="product_type"  <c:if test="${onlyedit=='onlyedit' }">readonly</c:if> value="${pd.product_type }" placeholder="产品型号">
				</td> 
				<td><label>其他配置</label></td>
				<td colspan="3">
					<textarea rows="1" name="product_others" id="product_others" style="width:223px;" placeholder="其他配置">${pd.product_others }</textarea>
				</td>
			</tr>
			<tr>
				<td><label>备注：</label></td>
				<td colspan="3"> 
					<textarea rows="2" cols="20" name="product_note" name="product_note"  style="width:890px;" placeholder="备注">${pd.product_note }</textarea>
				</td>
			</tr>

			
			<tr style="height: 40">
				<td style="text-align: center;" colspan="4">
					<a class="btn btn-mini btn-primary" onclick="save();">保存修改</a>
					<a class="btn btn-mini btn-danger" onclick="product_close();">取消</a>
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
	
	 
	$(document).ready(function(){
		
	});
	function product_close(){
		window.location.href='<%=basePath%>product/apl_product_index.do';
	}
	//保存
	function save(){
		
		if($("#provider").val()==""){
			
			$("#provider").tips({
				side:3,
	            msg:'请选择供应商代码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#provider").focus();
			$("#provider").val('');
			$("#provider").css("background-color","white");
			return false;
		}else{
			$("#provider").val($.trim($("#provider").val()));
		}
		
		if($("#product_flag").val()==""){
			
			$("#product_flag").tips({
				side:3,
	            msg:'请输入产品代码',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#product_flag").focus();
			return false;
		}
	
		
		if($("#product_class").val()==""){
			
			$("#product_class").tips({
				side:3,
	            msg:'请输入产品代码',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#product_class").focus();
			return false;
		}
		
		if($("#product_name").val()==""){
			
			$("#product_name").tips({
				side:3,
	            msg:'请输入产品代码',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#product_name").focus();
			return false;
		}
		//验证钱
		if($("#product_price").val()==""){
			
			$("#product_price").tips({
				side:3,
	            msg:'请输入产品价格',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#product_price").focus();
			return false;
		}else if($.trim($("#product_price").val())!=""){
			if(!ismoney($.trim($("#product_price").val()))){
				$("#product_price").tips({
					side:3,
		            msg:'输入价格格式不正确',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#product_price").focus();
				return false;
			}
		}
		
		if($("#product_unit").val()==""){
			
			$("#product_unit").tips({
				side:3,
	            msg:'请输入计量单位',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#product_unit").focus();
			return false;
		}
		else{
			$("#productForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
</script>
	
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
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
					 window.location = '<%=basePath %>product/apl_product_insert'; 
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
											nextPage('${page.currentPage}');
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
		
		
	if('${flag}' == 'saveP'){
		var jsons;
		var pcn;
		function select_asset_class(value){
			
			if(value=='0'){
				pcn='${class_name}';
			}else{
				pcn='${used_class_name}';
			}
			jsons = JSON.parse(pcn);

			//开始进入 初始化产品类别下拉框
			var select_class = document.getElementById("product_class");
			select_class.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
		 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	            $("#product_class").append(option);
		 		});
				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
			$("#product_class").trigger("liszt:updated");
			}); 
		}
		
		
		
		//产品类型的点击change事件
		function select_product_class_name() {
			var product_class = document.getElementById("product_class");
	        var product_name = document.getElementById("select_product");
			var options = product_class.options;
			var key_class_code = options[product_class.selectedIndex].innerHTML;
			product_name.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
	 				if(key==key_class_code){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i].split("@")[0];
	 						var option = document.createElement("option");
	 				        option.innerHTML = te+"";
	 				        option.value = value[i]+"";
	 				        $("#select_product").append(option);
	 				        if(pn == te){
	 				        	option.selected=true;
	 				        }
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#select_product").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
	}
	
	 
	 if('${flag}' == 'updateP'){
		var jsons;
		var pcn;
		var pc = '${pc}';
		var pn = '${pn}';
		var class_flag = '${class_flag}';
		if(class_flag=='0'){
			pcn='${class_name}';
		}else{
			pcn='${used_class_name}';
		}
		
		jsons = JSON.parse(pcn);

		//开始进入初始化产品 下拉框
		var select_class = document.getElementById("product_class");
		select_class.length = 1; //清除以前的的信息
		$.each(jsons,function(key, value){
			$.each(value,function(key, value){
	 			var option = document.createElement("option");
            option.innerHTML = key+"";
            $("#product_class").append(option);
            if(key == pc){
            	option.selected=true;
            	select_product_class_name1();
            }
	 		});
		 $("#product_class").trigger("liszt:updated");
		}); 
		
		function select_asset_class1(value){
			
			if(value=='0'){
				pcn='${class_name}';
			}else{
				pcn='${used_class_name}';
			}
			jsons = JSON.parse(pcn);

			//开始进入 初始化公司下拉框
			var select_class = document.getElementById("product_class");
			select_class.length = 1; //清除以前的的信息
			$("#select_product").find("option").remove();
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
		 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	            $("#product_class").append(option);
		 		}); 
		 	$("#product_class").trigger("liszt:updated");
		 	$("#select_product").trigger("liszt:updated");
			}); 
		} 
		
		
		
		//申请公司的点击change事件
		 function select_product_class_name1() {
			var product_class = document.getElementById("product_class");
	        var product_name = document.getElementById("select_product");
			var options = product_class.options;
			var key_class_code = options[product_class.selectedIndex].innerHTML;
			product_name.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
	 				if(key==key_class_code){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i].split("@")[0];
	 						var option = document.createElement("option");
	 				        option.innerHTML = te+"";
	 				        option.value = value[i]+"";
	 				        $("#select_product").append(option);
	 				        if(pn == te){
	 				        	option.selected=true;
	 				        }
	 			        }
	 				}
	 				$("#select_product").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		} 
	}
		</script>
		
	</body>
</html>