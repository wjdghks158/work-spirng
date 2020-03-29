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
		// ��Ʈ�ѷ��� �̸�(���)
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
				throw new Exception("��û�ϴ� URI�� �������� �ʽ��ϴ�.");
			
			Object target = control.getTarget();
			Method method = control.getMethod();
			ModelAndView mav 
					= (ModelAndView)method.invoke(target, request, response ); // ���� ���Ѷ�
			
			Map<String, Object> model = mav.getModel();
			Set<String> keys = model.keySet();
			
			for(String key : keys) {
				request.setAttribute(key, model.get(key));
			}
			view = mav.getView();
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			view = "/ErrorServlet";
		}
		// ������ �̵�
		// endRedirect�� ��츦 �˷��ִ� ǥ�� �뵵
		// view ������ "redirct:"�� �����Ѵٸ�
		if(view.startsWith("redirect:")) { // �����̷�Ʈ ���
			view = view.substring("redirect:".length());
			response.sendRedirect(view);
		}else {
			RequestDispatcher rd =request.getRequestDispatcher(view);
			rd.forward(request, response);
		}
	}
}
