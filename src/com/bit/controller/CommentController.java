package com.bit.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.bit.dao.CommentDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.RequestMapping;
import com.bit.vo.CommentVO;
import com.bit.vo.LoginVO;

public class CommentController {
	@RequestMapping("/addComment.do")
	public ModelAndView addComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String boardWriter = request.getParameter("boardwriter");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardno"));
		
		CommentVO cmt = new CommentVO();
		cmt.setBoardno(boardNo);
		cmt.setBoardwriter(boardWriter);
		cmt.setContent(content);
		cmt.setWriter(writer);
		
		
		
		CommentDAO dao = new CommentDAO();
		int result = dao.insert(cmt);

		ModelAndView mav = new ModelAndView();
		mav.setView("WEB-INF/views/board/list.jsp");
		
		
		return mav;

	}
	@RequestMapping("/removeComment.do")
	public ModelAndView removeComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("no"));
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		String id = user.getId();
		int no = Integer.parseInt(request.getParameter("no"));
		
		CommentDAO dao = new CommentDAO();
		int result = dao.delete(id,no);
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("WEB-INF/views/board/list.jsp");
		
		/*
		 * DispatcherServlet (FrontController)���� ��û�� �޾� ó���ϵ�.
		 * sendReirect ������� �̵���Ű�ڴ�.
		 * -> "redirect:" �̵� ����� �����ϱ� ���� ǥ�� �뵵.
		 * -> ���� ȭ�鿡�� �̵��� URL���� ǥ��!
		 */
		return mav;

	}
	
}
