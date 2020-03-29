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
		 * DispatcherServlet (FrontController)���� ��û�� �޾� ó���ϵ�.
		 * sendReirect ������� �̵���Ű�ڴ�.
		 * -> "redirect:" �̵� ����� �����ϱ� ���� ǥ�� �뵵.
		 * -> ���� ȭ�鿡�� �̵��� URL���� ǥ��!
		 */
		return mav;

	}
	
	@RequestMapping("/myPage.do")
	public ModelAndView myPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		
		// 1. �α��� �� �Ѿ�� �Ķ���� ������ ��ڴ�.
		String id = user.getId();
		System.out.println("����ȯ" + id);
		MemberDAO dao = new MemberDAO();
		MemberVO member = dao.selectById(id);
		
		System.out.println(member);
		System.out.println(request.getContextPath());
		// 4. ���� �� �α��� ���� ���� ���
		String msg = "";
		String url = "";
		
		if(member != null) {
			 session = request.getSession();
			session.setAttribute("member", member); // �Ĥ��� ���
			url = request.getContextPath(); // ���٤�
		}else {// ���� ��
			msg = "�߸��� �����Դϴ�.";
			url = "/work-spring";
			
		}

		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", member);
		mav.addAtrribute("msg", msg);
		mav.addAtrribute("url", url);
		
		mav.setView("/WEB-INF/views/member/mypage.jsp");
		
		/*
		 * DispatcherServlet (FrontController)���� ��û�� �޾� ó���ϵ�.
		 * sendReirect ������� �̵���Ű�ڴ�.
		 * -> "redirect:" �̵� ����� �����ϱ� ���� ǥ�� �뵵.
		 * -> ���� ȭ�鿡�� �̵��� URL���� ǥ��!
		 */
		return mav;

	}
	
	
	@RequestMapping("/signUpForm.do")
	public ModelAndView signUpForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView("/WEB-INF/views/member/signUpForm.jsp");
		
		/*
		 * DispatcherServlet (FrontController)���� ��û�� �޾� ó���ϵ�.
		 * sendReirect ������� �̵���Ű�ڴ�.
		 * -> "redirect:" �̵� ����� �����ϱ� ���� ǥ�� �뵵.
		 * -> ���� ȭ�鿡�� �̵��� URL���� ǥ��!
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

	String msg = "ȸ�� ���Կ� �����Ͽ����ϴ�";
	String url = "/work-spring/loginForm.do";
	
	ModelAndView mav = new ModelAndView();
	mav.addAtrribute("msg", msg);
	mav.addAtrribute("url", url);
	mav.setView("/WEB-INF/views/member/signUp.jsp");
	return mav;
	
	
	

}
	
	
}
