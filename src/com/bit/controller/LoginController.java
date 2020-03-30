package com.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.StringUtils;

import com.bit.dao.LoginDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.Controller;
import com.bit.framework.annotation.RequestMapping;
import com.bit.vo.LoginVO;

@Controller
public class LoginController {
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		session.invalidate();
		String msg = "·Î±×¾Æ¿ô";
		String url = "/work-spring";
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("msg", msg);
		mav.addAtrribute("url", url);
		//mav.setView("WEB-INF/views/login/logout.jsp");
		mav.setView("redirect:/work-spring");
		return mav;

	}
	
	
	@RequestMapping("/loginForm.do")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		//mav.setView("WEB-INF/views/login/loginForm.jsp");
		mav.setView("WEB-INF/views/login/loginForm.jsp");
		return mav;
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String from = (String)request.getParameter("from");
		if(from == null || !from.contains(".do")) from = "/work-spring";
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String recentURI = request.getParameter("from");

		LoginVO login = new LoginVO();
		login.setId(id);
		login.setPassword(password);
		
		LoginDAO dao = new LoginDAO();
		LoginVO user = dao.login(login);
		
		String msg = "";
		String url = "";
		
		if(recentURI != null) url = recentURI;
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user); 
			
			switch(user.getType()) {
			case "S" :
				msg = user.getName() +" ";
				break;
			case "U" :
				msg = user.getName() +"";
				break;
			}
			url = request.getContextPath(); 
		}else {
			msg = " password Æ²·È½À´Ï´Ù.";
			url = "/work-spring/loginForm.do";
		}
		
		ModelAndView mav = new ModelAndView();
		//mav.setView("WEB-INF/views/login/login.jsp");
		mav.setView("redirect:" + from );
		return mav;
	}
	
	
}
