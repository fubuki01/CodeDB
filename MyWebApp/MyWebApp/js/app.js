/**
 * 用户账号管理注册模块
 **/
(function($, owner) {
	/**
	 * 服务器URL
	 **/
	owner.ip='192.168.1.114';
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.account = loginInfo.account || '';
		loginInfo.password = loginInfo.password || '';
//		if (loginInfo.account.length < 6) {
//			return callback('账号最短为 6 个字符');
//		}
//		if (loginInfo.password.length < 6) {
//			return callback('密码最短为 6 个字符');
//		}
		var code = "qq123456789mbfw"+loginInfo.account+",mbfw,"+loginInfo.password+"QQ123456789mbfw"+",mbfw,OvO";
		mui.post('http://'+owner.ip+':8080/mbfw/login_login',{
				KEYDATA: code,
				tm: new Date().getTime(),
				ismobile: "true"
		},function(data){
				if (data.result=="success") {
					owner.saveUser(data);
					return owner.createState(loginInfo, callback);
				} else {
					return callback('用户名或密码错误');
				}
			},'json'
		);

	};

	/**
	 * 储存用户信息
	 **/
	owner.saveUser = function(data){
		var user = {};
		user.name = data.name;
		user.organization_name = data.organization_name;
		user.superior_organization_name = data.superior_organization_name;
		user.user_permission = data.user_permission;
		localStorage.setItem('$user', JSON.stringify(user));
	};
	/**
	 * 获取用户信息
	 **/
	owner.getUser = function(){
		var userText = localStorage.getItem('$user') || "{}";
		return JSON.parse(userText);
	};

	owner.createState = function(loginInfo, callback) {
		var state = owner.getState();
		state.account = loginInfo.account;
		state.password = loginInfo.password;
		state.token = "token123456789";
		owner.setState(state);
		return callback('success');
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
		//var settings = owner.getSettings();
		//settings.gestures = '';
		//owner.setSettings(settings);
	};
	
	/**
	 * 无权限访问下调回登陆界面
	 **/
	owner.autologout = function(login_path) {
//		owner.setState({});
//		if (mui.os.ios) {
//			mui.openWindow({
//				url: login_path,
//				id: 'login',
//				show: {
//					aniShow: 'pop-in'
//				},
//				waiting: {
//					autoShow: false
//				}
//			});
//			return;
//		}	
//		mui.openWindow({
//			url: login_path,
//			id: 'login',
//			show: {
//				aniShow: 'pop-in'
//			}
//		});
		$.back();
		plus.webview.getLaunchWebview().show("pop-in");
		return;
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	};

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
			var settingsText = localStorage.getItem('$settings') || "{}";
			return JSON.parse(settingsText);
	};
	
}(mui, window.app = {}));