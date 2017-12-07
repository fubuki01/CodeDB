/**
 * 统一json请求的装饰器函数
 */
var currentajax = null;
function jsonpost(fun, uri, jsondata){
	
	//保持仅最后一次ajax请求为有效请求，避免重复ajax请求
	if(currentajax){
		currentajax.abort();
	}
	
	currentajax = mui.ajax('http://'+app.ip+':8080/mbfw'+uri,{
		data : jsondata,
		dataType : 'json',//服务器返回json格式数据
		type : 'post',//HTTP请求类型
		timeout : 5000,//超时时间设置为5秒；
		headers : {'Content-Type':'application/x-www-form-urlencoded'},	              
		success : function(data){
			if(data.result=="reload"){
				alert("请重新登陆；");
				app.autologout('../../login.html');
				return;
			}
			else {
				fun(data);
			}
			return;
		},
		error : function(xhr,type,errorThrown){
			//异常处理；
			if(type!='abort')
				alert('网络异常，请求超时。');
		}
	});
	
}

/**
 * undefined转空字符串
 */
function undefined2EmptyString(str){
	if(typeof(str)=="undefined")
		return "";
	else
		return str;
}

