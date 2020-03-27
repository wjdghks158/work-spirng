package com.bit.framework;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719201454070450384L;
	private HandlerMapping mappings; 
	@Override
	public void init(ServletConfig config) throws ServletException {
		String urlCtrlName = config.getInitParameter("controllers");
		// 컨트롤러의 이름(경로)
		// -> com.bit.controller.BoardController
		
		try {
			mappings = new HandlerMapping(urlCtrlName);
		}catch (Exception e) {

		}finally {
			
		}
		super.init();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		uri = uri.substring(contextPath.length());
		
		CtrlAndMethod control = mappings.getCtrlAndMethod(uri);
		String view = "";
		
		try {
			if(control == null)
				throw new Exception("요청하는 URI가 존재하지 않습니다.");
			
			Object target = control.getTarget();
			Method method = control.getMethod();
			
			ModelAndView mav 
					= (ModelAndView)method.invoke(target, request, response ); // 실행 시켜라
			
			Map<String, Object> model = mav.getModel();
			Set<String> keys = model.keySet();
			
			for(String key : keys) {
				request.setAttribute(key, model.get(key));
			}
			view = mav.getView();
		}catch (Exception e) {
			request.setAttribute("exception", e);
			view = "/ErrorServlet";
		}
		// 페이지 이동
		// endRedirect할 경우를 알려주는 표시 용도
		// view 정보가 "redirct:"로 시작한다면
		if(view.startsWith("redirect:")) { // 리다이렉트 방식
			view = view.substring("redirect:".length());
			response.sendRedirect(view);
		}else {
			RequestDispatcher rd =request.getRequestDispatcher(view);
			rd.forward(request, response);
		}
	}
}
