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
		<div class="btn-group">
	        <label id="bm" class="btn">
	            <input type="radio" name="bm"  value="bm" onclick="show(this.value)"/>按管理部门显示
	        </label>
	        <label  id="zhwd" class="btn">
	            <input type="radio" name="zhwd" value="zhwd" onclick="show(this.value)" />按总行网点显示
	        </label>
	        <a class="btn" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a>
    	</div>
		<div style="overflow-x:auto;width: 100%;height: 100%;margin-top:5px;" id="tableContainer">
			<table id="table_report" class="table table-striped table-bordered table-hover">		
				<thead>
					<tr>
						<th>序号</th>
						<th>部门名称</th>
						<th>总资产</th>
						<th>年度新增资产</th>
					</tr>
				</thead>				
				<tbody id="tbodytab">
				<c:forEach var="item" items="${tableList}" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.asset_use_dept}</td>
						<td>${item.zzc}</td>
						<td>${item.ndxz}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row-fluid" id="txtContainer"  style="height:200px;margin-top:1%"></div>
	</div>
	
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/highcharts.js"></script>
		<script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"></script>
		<!-- 图标导出插件 -->
		 <script type="text/javascript" src="static/js/offline-exporting.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		 	$(top.hangge());
		</script>
		<script type="text/javascript">
			$(function(){
				if('${type}' == 'bm'){
					$("#bm").attr("class","btn btn-success");
					$("#zhwd").attr("class","btn");
				}else{
					$("#bm").attr("class","btn");
					$("#zhwd").attr("class","btn btn-success");
				}
		 		txtChart();		 		
			});
			
			function show(value){
				window.location = "${pageContext.request.contextPath}/asset/asset_count_table.do?type="+value;
			}
			
			function toExcel(){
				alert("${pageContext.request.contextPath}/asset/table_to_excel.do?type=${type}");
				window.location.href = "${pageContext.request.contextPath}/asset/table_to_excel.do?type=${type}";
			}
			function txtChart(){
			
			var dat = [];
			var jsonObj = JSON.parse('${ jsonStr }');
			var len = jsonObj.length;
			$("#txtContainer").height(40+len*28);
			for(var i = 0; i < len; i++){
				dat.push([jsonObj[i].name,jsonObj[i].share/10000]);
			}
		  	$('#txtContainer').highcharts({
		  		navigation: {
		            buttonOptions: {
		            	enabled:true,
		                align: 'center'
		            }
		        },
		        lang:{
			       contextButtonTitle:"图表导出菜单",
			       decimalPoint:".",
			       downloadJPEG:"下载JPEG图片",
			       downloadPDF:"下载PDF文件",
			       downloadPNG:"下载PNG文件",
			       downloadSVG:"下载SVG文件",
			       loading:"加载中",
			       noData:"没有数据",
			       numericSymbols: [ "千" , "兆" , "G" , "T" , "P" , "E"],
			       printChart:"打印图表",
			       resetZoom:"恢复缩放",
			       resetZoomTitle:"恢复图表",
			    },
        		 chart: {
		            type: 'bar'
		        },
		        title: {
		        	useHTML:true,
		            text: '<b>部门总资产</b>'
		        },
		        subtitle: {
		            text: '<small>点击数据可查看部门资产详情</small>'
		        },
		        tooltip: {
		            useHTML: true,
		         	valueSuffix:'万元'
        		},
		        xAxis: {
		        	type:'category',
		            //categories: ['非洲', '美洲', '亚洲', '欧洲', '大洋洲'],
		            title: {
		                text: '部门'
		            }
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '总资产 (万元)',
		                align: 'high'
		            },
		            labels: {
		                overflow: 'justify'
		            }
		        },
 		        plotOptions: {
		            bar: {
		                dataLabels: {
		                    enabled: true,
		                    allowOverlap: true
		                }
		            },
		             series: {
                		cursor: 'pointer',
                		point: {
                    		events: {
                        		click: function(event) {
                            		location.href = '${pageContext.request.contextPath}/asset/dept_asset_list.do?asset_use_dept='+event.point.name+'&type='+'${type}';
                            		
                        		}
                    		}
                		}
           			}
		        }, 
		        navigation: {
        				buttonOptions: {
        					enabled:true,
            				symbolStroke: 'blue'
        				}
    			},
 		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'top',
		            x: -40,
		            y: 20,
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
		            data: dat
		        }]
		    });
		 	
		}
		</script>
	</body>
</html>

