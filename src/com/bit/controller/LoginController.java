package com.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		session.invalidate(); // 세션 정보 삭제
		String msg = "로그아웃 되었습니다.";
		String url = "/work-spring";
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("msg", msg);
		mav.addAtrribute("url", url);
		mav.setView("/WEB-INF/views/login/logout.jsp");
		return mav;

	}
	
	
	@RequestMapping("/loginForm.do")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView("/WEB-INF/views/login/loginForm.jsp");
		return mav;
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 1. 로그인 시 넘어온 파라미터 정보를 얻겠다.
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// 2. LoginVO에 파라미터 설정
		LoginVO login = new LoginVO();
		login.setId(id);
		login.setPassword(password);
		
		// 3. 데이터베이스의 로그인 과정 수행(DAO)
		//	-> id,password를 전달하여 성공이라면, name과 type까지 받아오겠다.
		LoginDAO dao = new LoginDAO();
		LoginVO user = dao.login(login);
		
		// 4. 성공 시 로그인 정보 세션 등록
		String msg = "";
		String url = "";
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user); // ㅔㅅ션 등록
			
			switch(user.getType()) {
			case "S" :
				msg = user.getName() +" 관리자님 환영합니다.";
				break;
			case "U" :
				msg = user.getName() +" 회원님 환영합니다.";
				break;
			}
			url = request.getContextPath(); // 컨텐ㅡ
		}else {// 실패 시
			msg = " password가 잘못되었습니다";
			url = "/work-spring/loginForm.do";
			
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("msg", msg);
		mav.addAtrribute("url", url);
		mav.setView("/WEB-INF/views/login/login.jsp");
		return mav;
	}
	
	
}
