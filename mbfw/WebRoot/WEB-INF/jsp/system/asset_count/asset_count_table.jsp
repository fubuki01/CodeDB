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
	</head> 
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
	
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">						
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="" method="post" id="form" style="margin:0px;">
			<table>
				<tr>
					<td style="vertical-align:top">
						<input type="checkbox" id="bm" onchange="isChecked(this.value,this.checked);" value="bm"/><label class="lbl" for="bm">部门: </label>	
						<select class="chzn-select" name="asset_use_dept" id="cse" data-placeholder="请选择部门" style="vertical-align:top;width: 700px;" multiple="multiple">
							<c:forEach items="${orgAbbrList}" var="item">
				       			<option value="${item}" >${item}</option>
				       		</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top"> 
					 	<input type="checkbox" id="zclx" onchange="isChecked(this.value,this.checked);" value="zclx" /><label class="lbl" for="zclx">资产类型: </label>
						<c:forEach items="${zclxList}" var="item" varStatus="status">
		       				<input type="checkbox" name="asset_class" value="${item.name}" id="zxlc${status.index}" /><label class="lbl" for="zxlc${status.index}">${item.name}</label>
		       			</c:forEach>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top"> 
					 	<input type="checkbox" id="zczt" onchange="isChecked(this.value,this.checked);" value="zczt"/><label class="lbl" for="zczt">资产状态: </label>
		       			<input type="checkbox" id="xz" value="闲置" name="asset_status" /><label class="lbl" for="xz">闲置</label>
		       			<input type="checkbox" id="xf" value="下发" name="asset_status" /><label class="lbl" for="xf">下发</label>
		       			<input type="checkbox" id="js" value="接收" name="asset_status" /><label class="lbl" for="js">接收</label>
		       			<input type="checkbox" id="ly" value="领用" name="asset_status" /><label class="lbl" for="ly">领用</label>
		       			<input type="checkbox" id="bx" value="保修" name="asset_status" /><label class="lbl" for="bx">保修</label>
		       			<input type="checkbox" id="hs" value="回收" name="asset_status" /><label class="lbl" for="hs">回收</label>
		       			<input type="checkbox" id="bf" value="报废" name="asset_status" /><label class="lbl" for="bf">报废</label>
					</td>
				</tr>
			</table>
			<table style="margin-top:3px;">
				<tr>		
					<td><input class="span10 date-picker" name="ksrq"  value="" type="text" data-date-format="yyyy-mm-dd" style="width:88px;" placeholder="开始日期" title="采购时间"/></td>
					<td><input class="span10 date-picker" name="jsrq"  value="" type="text" data-date-format="yyyy-mm-dd"  style="width:88px;" placeholder="结束日期" title="采购时间"/></td>
					<!-- <td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="je" value="" placeholder="金额" style="width:60px;" />
						</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="number" name="num" placeholder="数量" style="width:60px;" />
						</span>
					</td> -->
					
					<td style="width:60px;vertical-align:top;" class="center">
						<label>
							<input type="radio"  name="data" value="dataTable" id="tableRadio" checked /><span class="lbl" for="tableRadio" >报表</span>
							<input type="radio" name="data" value="dataGraph" id="graphRadio" /><label class="lbl" for="graphRadio">图表</label>
						</label>
					</td>
					<td style="vertical-align:top;"><span class="btn btn-mini btn-light" id="search"  title="检索"><i id="nav-search-icon" class="icon-search"></i></span></td>
				</tr>
			</table>
			</form>
			<!-- 检索  -->
			
			<div style="overflow-x:auto;width: 100%;height: 100%;margin-top:0px;" id="tableContainer">
				<table id="table_report" class="table table-striped table-bordered table-hover">		
					<thead>
						<tr>
							<th>资产类型</th>
							<th>数量</th>
							<th>金额</th>
						</tr>
					</thead>				
					<tbody id="tbodytab">
					</tbody>
				</table>
			</div>
			<div id="chartContainer">
				<div class="row-fluid" id="zxtContainer"></div>
			  	<div class="row-fluid" id="qxtContainer"></div>
			  	<div class="row-fluid" id="pieContainer"></div>
  			</div>
<!-- 		<div class="page-header position-relative">
			
		</div> -->
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
		
		<script type="text/javascript" src="static/js/highcharts.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->

		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
			$(top.hangge());
			$("#search").click(function(){
				if(!submitCheck()){
					return;
				}
	 	  	  	var obj = $('input[name=data]:checked').val();
			  	var url = '';
			  	if(obj == "dataTable"){
			  		url = '${pageContext.request.contextPath}/asset/assetCountTable.do';
			  	}else{
			  		url = '${pageContext.request.contextPath}/asset/assetCountGraph.do';
			  	}
			 $.ajax({
				type: "post",
				url: url,
			   	data: $("#form").serialize(),
				cache: false,
				success: function(data){
					if(obj == "dataTable"){
						$("#tbodytab").empty();
				   		$.each(data , function(key , value){
							var row1 = $("<tr><td>"+value.asset_class+"</td><td>"+value.num+"</td><td>"+value.totalPrice+"</td></tr>");
				   			$("#tbodytab").append(row1);
				   		});
				   		$("#tableContainer").show();
				   		$("#chartContainer").hide();
				   		
					}else{
						$("#tableContainer").hide();
						$("#chartContainer").show();
						
					   zxChart(data);
					   pieChart(data);
					}
				},
				error:function(data){  
			        alert("出错了！！:"+data);
			    }
			});
		});
		  	function isChecked(value,flag){
		  	if(value == "bm"){
		  		$("#cse option").each(function(){
		  			$(this).prop("selected",flag);
		  		});
		  	}else if(value == "zclx"){
		  		$('input[name="asset_class"]').each(function(){
		  			$(this).prop("checked",flag);
		  		});
		  	}else{//资产状态
		  		$('input[name="asset_status"]').each(function(){
		  			$(this).prop("checked",flag);
		  		});
		  	}
		  	$(".chzn-select").trigger("liszt:updated"); 
		  }
		  
		  function submitCheck(){
		  	var bm = $("#cse").val();
		  	if(bm == null || bm == ""){
		  		$("select").tips({
					side:2,
		            msg:'请至少选择一个部门',
		            bg:'#AE81FF',
		            time:2
	        	});
				$("#select").focus();
				return false
		  	}
		  	if($('input[name=asset_class]:checked').length == 0){
		  		$("#zclx").tips({
		  			side:2,
		  			msg:'至少选择一种资产类型',
		  			bg:'#AE81FF',
		  			time:0.5
		  		});
		  		return false;
		  	}
		  	if($('input[name=asset_status]:checked').length == 0){
		  		$("#zczt").tips({
		  			side:2,
		  			msg:'至少选择一种资产状态',
		  			bg:'#AE81FF',
		  			time:0.5
		  		});
		  		return false;
		  	}
		  	return true;
		  }
		  function qxChart(data){
		  	var title = {
	      		text: '各类资产总价格'   
	   		};
		   var subtitle = {
		      text: ''
		   };
		   var xAxis = {
		      categories:data.asset_class
		   };
		   var yAxis = {
		      title: {
		         text: '价格 (元)'
		      },
		      plotLines: [{
		         value: 0,
		         width: 1,
		         color: '#808080'
		      }]
		   };   
		
		   var tooltip = {
		      valueSuffix: '元'
		   }
		
		   var legend = {
		      layout: 'vertical',
		      align: 'right',
		      verticalAlign: 'middle',
		      borderWidth: 0
		   };
		   var json = {};
		   
		   json.title = title;
		   json.subtitle = subtitle;
		   json.xAxis = xAxis;
		   json.yAxis = yAxis;
		   json.tooltip = tooltip;
		   json.legend = legend; 
		   json.series = data.data;
		   $('#qxtContainer').highcharts(json);
		  }
		  
		  function pieChart(data){
		  		var dat = [];
		  		var list = data.pieData;
		  		for(var i = 0; i < list.length; i++){
		  			dat.push([list[i].name,list[i].share]);
		  		}
			  	var chart = {
		       		plotBackgroundColor: null,
		       		plotBorderWidth: null,
		      		plotShadow: false
		   		};
			   	var title = {
			      	text: '资产状态占比'   
			   	};      
			   var tooltip = {
			      pointFormat: '<b>{series.name}:{point.y} 元</b> '
			      
			   };
			   var plotOptions = {
			      pie: {
			         allowPointSelect: true,
			         cursor: 'pointer',
			         dataLabels: {
			            enabled: true,
			            format: '<b>{point.name}%</b>: {point.percentage:.1f} {point.share}%',
			            style: {
			               color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			            }
			         }
			      }
			   };
			   var series= [{
			      type: 'pie',
			      name: '金额',
			      data:dat
			   }];     
			   var json = {};   
			   json.chart = chart; 
			   json.title = title;     
			   json.tooltip = tooltip;  
			   json.series = series;
			   json.plotOptions = plotOptions;
			   $('#pieContainer').highcharts(json);
		  }
		  
		  function zxChart(data){
		  	$('#zxtContainer').highcharts({
        		 chart: {
		            type: 'bar'
		        },
		        title: {
		            text: '各洲不同时间的人口条形图'
		        },
		        subtitle: {
		            text: '数据来源: Wikipedia.org'
		        },
		        xAxis: {
		        	type:'category',
		            //categories: ['非洲', '美洲', '亚洲', '欧洲', '大洋洲'],
		            title: {
		                text: null
		            }
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '人口总量 (百万)',
		                align: 'high'
		            },
		            labels: {
		                overflow: 'justify'
		            }
		        },
		        tooltip: {
		            valueSuffix: ' 百万'
		        },
 		        plotOptions: {
		            bar: {
		                dataLabels: {
		                    enabled: true,
		                    allowOverlap: true
		                }
		            }
		        }, 
 		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'top',
		            x: -40,
		            y: 100,
		            floating: true,
		            borderWidth: 1,
		            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
		            shadow: true
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		            name: '总资产',
		            pointWidth:13,
		            data: [
		            	['部门1',100],
		            	['部门2',200],
		            	['部门3',300]
		            ]
		        }]
		    });
		}
		</script>
		
		<script type="text/javascript">
			$(function() {
				$(top.hangge());
				//日期框
				$('.date-picker').datepicker();
				//下拉框
				$(".chzn-select").chosen(); 
				$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			});
		</script>
	</body>
</html>
