package com.mbfw.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mbfw.entity.system.User;
import com.mbfw.util.Const;
import com.mbfw.util.Jurisdiction;

import net.sf.json.JSONObject;

/**
 * 类名称：LoginHandlerInterceptor.java
 * 
 * @author 研发中心 作者单位： 创建时间：2015年1月1日
 * @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		String userAgent = request.getHeader("user-agent");
		boolean ismobile = false;
		if(userAgent.contains("Html5Plus/1.0"))
			ismobile=true;
		
		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			//无权限的移动端设备访问发送重新登陆的json信息
			if(ismobile&&path.equals("/login_toLogin")){
				JSONObject responseJSONObject = JSONObject.fromObject("{\"result\" : \"reload\"}");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				try{
					out = response.getWriter();
					out.append(responseJSONObject.toString());
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(out!=null){
						out.close();
					}
				}
			}
			return true;
		} else {
			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				if (session.getAttribute(Const.IS_MOBILE).equals("true"))
					return true;
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path);
				if (!b) {
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return b;
			} else {
				// 登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
				// return true;
			}
		}
	}

}
