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
		String from = (String)request.getParameter("from");
		String type = (String)request.getParameter("type");
		String no = (String)request.getParameter("no");
		StringBuilder sb = new StringBuilder();
		
		sb.append("/work-spring/");
		if(from != null && from.contains(".do")) sb.append(from);
		if(type != null) sb.append("?type="+type);
		if(no != null) sb.append("&no="+no);
		
		
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
		//mav.setView("WEB-INF/views/board/list.jsp");
		mav.setView("redirect:"+sb.toString());
		
		
		return mav;

	}
	@RequestMapping("/removeComment.do")
	public ModelAndView removeComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String from = (String)request.getParameter("from");
		String type = (String)request.getParameter("type");
		String no = (String)request.getParameter("no");
		String boardno = (String)request.getParameter("boardno");
		StringBuilder sb = new StringBuilder();
		
		sb.append("/work-spring/");
		if(from != null && from.contains(".do")) sb.append(from);
		if(type != null) sb.append("?type="+type);
		if(boardno != null) sb.append("&no="+boardno);
		
		
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		String id = user.getId();
		
		CommentDAO dao = new CommentDAO();
		int result = dao.delete(id,Integer.parseInt(no));
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("redirect:"+sb.toString());
		return mav;
	}
	
}
