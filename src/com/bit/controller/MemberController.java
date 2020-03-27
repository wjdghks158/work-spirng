package com.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.dao.MemberDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.Controller;
import com.bit.framework.annotation.RequestMapping;
import com.bit.vo.LoginVO;
import com.bit.vo.MemberVO;

@Controller
public class MemberController {
	@RequestMapping("/updateMember.do")
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email_id = request.getParameter("email_id");
		String email_domain = request.getParameter("email_domain");
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String post = request.getParameter("post");
		String basic_addr = request.getParameter("basic_addr");
		String detail_addr = request.getParameter("detail_addr");

		MemberVO member = new MemberVO();
		member.setId(email_id);
		member.setName(name);
		member.setPassword(password);
		member.setEmail_id(email_id);
		member.setEmail_domain(email_domain);
		member.setTel1(tel1);
		member.setTel2(tel2);
		member.setTel3(tel3);
		member.setPost(post);
		member.setBasic_addr(basic_addr);
		member.setDetail_addr(detail_addr);
		MemberDAO dao = new MemberDAO();
		int result = dao.updateMember(member);

		ModelAndView mav = new ModelAndView();
		if(result > 0) {
			mav.addAtrribute("member", member);
		}
		
		mav.setView("/WEB-INF/views/member/mypage.jsp");
		return mav;
	}
	@RequestMapping("/updateForm.do")
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("/WEB-INF/views/member/updateForm.jsp");
		
		/*
		 * DispatcherServlet (FrontController)에서 요청을 받아 처리하되.
		 * sendReirect 방식으로 이동시키겠다.
		 * -> "redirect:" 이동 방식을 구분하기 위해 표시 용도.
		 * -> 유저 화면에는 이동된 URL으로 표시!
		 */
		return mav;

	}
	
	@RequestMapping("/myPage.do")
	public ModelAndView myPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		
		HttpSession session = request.getSession();
		session.getAttribute("user");
		
		LoginVO user = (LoginVO)session.getAttribute("user");
		
		// 1. 로그인 시 넘어온 파라미터 정보를 얻겠다.
		String id = user.getId();
		System.out.println("박정환" + id);
		MemberDAO dao = new MemberDAO();
		MemberVO member = dao.selectById(id);
		
		System.out.println(member);
		System.out.println(request.getContextPath());
		// 4. 성공 시 로그인 정보 세션 등록
		String msg = "";
		String url = "";
		
		if(member != null) {
			 session = request.getSession();
			session.setAttribute("member", member); // ㅔㅅ션 등록
			url = request.getContextPath(); // 컨텐ㅡ
		}else {// 실패 시
			msg = "잘못된 접급입니다.";
			url = "/work-spring";
			
		}

		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", member);
		mav.addAtrribute("msg", msg);
		mav.addAtrribute("url", url);
		
		mav.setView("/WEB-INF/views/member/mypage.jsp");
		
		/*
		 * DispatcherServlet (FrontController)에서 요청을 받아 처리하되.
		 * sendReirect 방식으로 이동시키겠다.
		 * -> "redirect:" 이동 방식을 구분하기 위해 표시 용도.
		 * -> 유저 화면에는 이동된 URL으로 표시!
		 */
		return mav;

	}
	
	
	@RequestMapping("/signUpForm.do")
	public ModelAndView signUpForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView("/WEB-INF/views/member/signUpForm.jsp");
		
		/*
		 * DispatcherServlet (FrontController)에서 요청을 받아 처리하되.
		 * sendReirect 방식으로 이동시키겠다.
		 * -> "redirect:" 이동 방식을 구분하기 위해 표시 용도.
		 * -> 유저 화면에는 이동된 URL으로 표시!
		 */
		return mav;

	}
@RequestMapping("/signUp.do")
public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	// Parameter
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String email_id = request.getParameter("email_id");

	String email_domain = "";
// 	if((email_domain = request.getParameter("email_domain1")) == null) {
	if((email_domain = request.getParameter("email_domain1")) == "") {
		email_domain = request.getParameter("email_domain2");
	}
	
	String tel1 = request.getParameter("tel1");
	String tel2 = request.getParameter("tel2");
	String tel3 = request.getParameter("tel3");
	String post = request.getParameter("post");
	String basic_addr = request.getParameter("basic_addr");
	String detail_addr = request.getParameter("detail_addr");
	
	MemberVO vo = new MemberVO();
	vo.setId(id);
	vo.setName(name);
	vo.setPassword(password);
	vo.setEmail_id(email_id);
	vo.setEmail_domain(email_domain);
	vo.setTel1(tel1);
	vo.setTel2(tel2);
	vo.setTel3(tel3);
	vo.setPost(post);
	vo.setBasic_addr(basic_addr);
	vo.setDetail_addr(detail_addr);
	
	
	MemberDAO memberDAO = new MemberDAO();
	int result = memberDAO.insertMember(vo);

	String msg = "회원 가입에 성공하였습니다";
	String url = "/work-spring/loginForm.do";
	
	ModelAndView mav = new ModelAndView();
	mav.addAtrribute("msg", msg);
	mav.addAtrribute("url", url);
	mav.setView("/WEB-INF/views/member/signUp.jsp");
	return mav;
	
	
	

}
	
	
}
