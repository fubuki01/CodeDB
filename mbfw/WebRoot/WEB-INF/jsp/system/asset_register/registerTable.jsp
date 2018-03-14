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
	</head>
<body>
	<form action="asset/${msg }.do" name="asset_updateForm" id="asset_updateForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<input type="hidden" name="id" id="id" value="${agm.id }">
		<input type="hidden" name="product_code" id="product_code">
		<div style="overflow-x:auto;width: 100%;height: 100%;">
		<table  id="table_report" class="table text-table table-striped table-bordered">
			<tr>
				<td style="text-align: center;" colspan="6"><label><b style="color: red">资产信息修改</b></label></td>
			</tr>
			<tr>
				<td><label>设备名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_name" id="asset_name"  placeholder="设备名称" value="${agm.asset_name }" readonly="readonly"></td>
				<td><label>资产类别<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_class" id="asset_class"  placeholder="资产类别" value="${agm.asset_class }" readonly="readonly"></td>
				<td><label>价格<b style="color: red">*</b></label></td>
			    <td ><input type="text" name="asset_price" id="asset_price" placeholder="价格" value="${agm.asset_price }" readonly="readonly"></td>
			</tr>
			<tr>
			    <td><label>计量单位<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_unit" id="asset_unit"  placeholder="计量单位" value="${agm.asset_unit }" readonly="readonly"></td>
				<td><label>供应商<b style="color: red">*</b></label></td>
				<td>
					<c:if test="${agm.asset_provider == '不祥' || agm.asset_provider==null }">
						<input type="text" name="asset_provider" id="asset_provider"  placeholder="供应商">
					</c:if>
					<c:if test="${agm.asset_provider != '不祥' && agm.asset_provider !=null }">
					<input type="text" name="asset_provider" id="asset_provider"  placeholder="供应商" value="${agm.asset_provider }" readonly="readonly">
					</c:if>
				</td>
				<td><label>登记人<b style="color: red">*</b></label></td>
			    <td ><input type="text" name="asset_manager" id="asset_manager" placeholder="登记人" readonly="readonly" value="${agm.asset_manager }"></td>
			</tr>
			<tr>
			    <td><label>使用期限<b style="color: red">*</b></label></td>
			    <td ><input type="text" name="asset_max_years" id="asset_max_years" placeholder="使用期限"value="${agm.asset_max_years }"></td>
				<td><label>设备品牌<b style="color: red">*</b></label></td>
			    <td><input type="text" name="asset_brand" id="asset_brand" placeholder="品牌" value="${agm.asset_brand }"></td>	
		 		<td><label>规格型号<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_standard_model" id="asset_standard_model" placeholder="规格型号" value="${agm.asset_standard_model }"></td>
			</tr>
			
			<tr>
			    <td><label>存放地点<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_storehouse" id="asset_storehouse" placeholder="存放地点" value="${agm.asset_storehouse }"> </td>
				<td><label>入库时间<b style="color: red">*</b></label></td>
				<td>
					<input  type="text"  name="asset_purchase_time" id="asset_purchase_time"  data-date-format="yyyy-mm-dd" style="width:222px;" placeholder="入库日期" title="入库日期" readonly="readonly" value="${asset_purchase_time }"/>
				</td>
				<td><label>详细配置</label></td>
				<td >
					<textarea rows="1" cols="" style="width:211px" name="asset_detail_config" id="asset_detail_config">${agm.asset_detail_config }</textarea>
				</td>
			</tr>
			
			<tr>
				
				<td><label>SN号</label></td>
				<td><input type="text" name="asset_sn" id="asset_sn" placeholder="SN号" value="${agm.asset_sn }"></td>
				<td><label>使用公司<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select" data-placeholder="使用公司"
					name="asset_use_company" id="asset_use_company" onchange="select_company();">
						<option></option>
								
					</select>
				</td>
			    <td><label>使用部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" name="asset_use_dept" id="asset_use_dept"  data-placeholder="使用部门"
					style="width: 221px; vertical-align: center;">
					<option></option>						
					</select>
				</td>
				
			</tr>
			
			<tr>
				<td><label>资产用途</label></td>
				<td><input type="text" name="asset_use" id="asset_use" placeholder="资产用途" value="${agm.asset_use }" ></td>
				<td><label>使用人<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_user" id="asset_user"  placeholder="使用人" value="${agm.asset_user }"></td>
				<td><label>备注：</label></td>
				<td colspan="5">
					<textarea rows="1" cols="40" style="width:94%" name="asset_nod" id="asset_nod">${agm.asset_nod }</textarea>
				</td>
			</tr>
			<tr>
				<td><label>资产图片</label></td>
				<td colspan="5">
				<div>
				  <section >
				    <p class="up-p" style="text-align: center;"><b style="color: red">资产图片：最多可以上传5张图片,且每张图片大小不能超过500K!</b></p>
				    <div class="z_photo upimg-div clear" >
				   <section class="z_file fl">
					<img src="plugins/img/a11.png" class="icon-upload">
					<input type="file"   name="asset_img" id="asset_img" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp"  multiple />
				  </section>
		          </div>
	             </section>
              </div>
             <aside class="mask works-mask">
	           <div class="mask-content">
	        	<p class="del-p ">您确定要删除作品图片吗？</p>
		        <p class="check-p"><span class="del-com wsdel-ok">确定</span><span class="wsdel-no">取消</span></p>
	          </div>
            </aside>
            </td>
			</tr>
			 <tr style="height: 40">
				<td style="text-align: center;" colspan="6">
				  <a class="btn btn-mini btn-primary" onclick="save();">保存修改</a>
				  <a class="btn btn-mini btn-danger" onclick="asset_info_close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
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
		
		
		function asset_info_close(){
			window.location.href='<%=basePath%>asset/arda_assertRegister.do';
		}
		

		//新增
		function add_product(){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增产品";
			 diag.URL = '<%=basePath%>product/apl_product_insert.do';
			 diag.Width = 800;
			 diag.Height = 430;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 window.location = '<%=basePath %>asset/arda_asset_into_house'; 
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

		//入库选择下拉框到后台请求对应入库单内容
		function add_asset(id){
			var idd = id.split("@")[1];
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/arda_fill_asset',
				async:false,
				data:{"id":idd},
				type:'POST',
				success:function(data){
					$('#asset_class').val(data.product_class);
					$('#asset_price').val(data.product_price);
					$('#asset_unit').val(data.product_unit);
					$('#asset_provider').val(data.provider_name);
					$('#asset_standard_model').val(data.asset_standard_model);
					$('#product_total').val(data.product_total);
					$('#product_code').val(data.product_code);
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"
			});
		}
		</script>
	<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		if($("#asset_name_select_select").val()==""){
			
			$("#asset_name_select_select").tips({
				side:3,
	            msg:'请选择设备名称',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#asset_name_select_select").focus();
			$("#asset_name_select_select").val('');
			return false;
		}
		
		if($("#asset_qr_code").val()==""){
			
			$("#asset_qr_code").tips({
				side:3,
	            msg:'请输入二维码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#asset_qr_code").focus();
			$("#asset_qr_code").val('');
			return false;
		}
		
		
		if($("#asset_max_years").val()==""){
			
			$("#asset_max_years").tips({
				side:3,
	            msg:'请输入使用期限',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_max_years").focus();
			return false;
		}else if($.trim($("#asset_max_years").val() != "")){
			if(!isint($.trim($("#asset_max_years").val()))){
				$("#asset_max_years").tips({
					side:3,
		            msg:'请输入一个整数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#asset_max_years").focus();
				return false;
			}
		}
		
		if($("#asset_brand").val()==""){
			
			$("#asset_brand").tips({
				side:3,
	            msg:'请输入设备品牌',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_brand").focus();
			$("#asset_brand").val('');
			return false;
		}
		
		
		if($("#asset_storehouse").val()==""){
			
			$("#asset_storehouse").tips({
				side:3,
	            msg:'请选择存放地点',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_storehouse").focus();
			return false;
		}
		 if($("#asset_purchase_time").val()==""){
			
			$("#asset_purchase_time").tips({
				side:3,
	            msg:'请选择入库时间',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_purchase_time").focus();
			return false;
		} 
		
		if($("#asset_status").val()==""){
			
			$("#asset_status").tips({
				side:3,
	            msg:'请选择设备状态',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_status").focus();
			return false;
		}
	
		if($("#asset_use_company").val()==""){
			
			$("#asset_use_company").tips({
				side:3,
	            msg:'请选择使用公司',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_use_company").focus();
			return false;
		}
		
		if($("#asset_use_dept").val()==""){
			
			$("#asset_use_dept").tips({
				side:3,
	            msg:'请选择使用部门',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_use_dept").focus();
			return false;
		}
		
		if($("#asset_user").val()==""){
			
			$("#asset_user").tips({
				side:3,
	            msg:'请输入使用人',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_user").focus();
			return false;
		}
		
		if($("#asset_manager").val()==""){
			
			$("#asset_manager").tips({
				side:3,
	            msg:'请输入管理员',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#asset_manager").focus();
			return false;
		}else{
			$("#asset_updateForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	var permission = '${permission}';
	var gognsi='${gongsi}';
	var bumen ='${bumen}';
	if(permission==1||permission==2){
		var zn = '${institutionInfo}';
 		var jsons = JSON.parse(zn)  
 		//开始进入 初始化公司下拉框
 		$.each(jsons,function(key, value){
 			$.each(value,function(key, value){
 	 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	           // alert(key);
	            $("#asset_use_company").append(option);
	           /*  if(key == gongsi){
	            	alert(gongsi);
	            	option.selected=true;
	            	select_company();
	            } */
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("asset_use_company");
	        var apply_dept = document.getElementById("asset_use_dept");
			var options = apply_company.options;
			var company_name = options[apply_company.selectedIndex].innerHTML;
			apply_dept.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key==company_name){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var option = document.createElement("option");
	 				        option.innerHTML = te+"";
	 				        $("#asset_use_dept").append(option);
	 				       /*  if(te == bumen){
	 				        	option.selected=true;
	 				        } */
	 			        }
	 				}
	 				$("#asset_use_dept").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
	}
	if(permission==3){
		var apply_person = '${apply_person}';//申请人
		var apply_company = '${apply_company}';//申请公司
		var apply_dept = '${apply_dept}';//申请部门
		if(apply_person!=""||apply_person!=null){
			$("#asset_user").val(apply_person);
			$("#asset_user").attr("readonly","readonly");
		}
		if(apply_company!=""||apply_company!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_company;
            option.selected='selected';
            $("#asset_use_company").append(option);
		}
		if(apply_dept!=""||apply_dept!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_dept;
            option.selected='selected';
            $("#asset_use_dept").append(option);
		}
	}
	</script>
	</body>
</html>