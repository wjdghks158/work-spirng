package com.bit.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.dao.BoardDAO;
import com.bit.dao.CommentDAO;
import com.bit.dao.MemberDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.RequestMapping;
import com.bit.util.MyFileRenamePolicy;
import com.bit.vo.BoardVO;
import com.bit.vo.BoardViewVO;
import com.bit.vo.CommentVO;
import com.bit.vo.FileVO;
import com.bit.vo.LoginVO;
import com.bit.vo.MemberVO;
import com.oreilly.servlet.MultipartRequest;

public class BoardController {	
	
	
	
	@RequestMapping("/removeFile.do")
	public ModelAndView removeFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String no = (String)request.getParameter("no");
		String from = (String)request.getParameter("from");
		String fn = (String)request.getParameter("fn");
		
		StringBuilder sb = new StringBuilder();
		sb.append("/work-spring/");
		if(from != null && from.contains(".do")) sb.append(from);
		if(no != null) sb.append("?no="+no);
		
		
		
		BoardDAO dao = new BoardDAO();
		int result = dao.deleteFile(fn);
		ModelAndView mav = new ModelAndView();
		mav.setView("redirect:"+sb.toString());
		return mav;

	}
		
	
	
		
	@RequestMapping("/updateBoard.do")
	public ModelAndView updateBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String saveFolder = "C:\\Users\\bit\\Downloads\\jblog-20200319T061927Z-001\\work-spring\\WebContent\\upload";
		//String saveFolder = "C:\\Users\\wjdgh\\git\\work-spirng\\WebContent\\upload";

		MultipartRequest multi = new MultipartRequest(request, saveFolder, 1024 * 1024 * 3, "utf-8", new MyFileRenamePolicy());

		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		String writer = multi.getParameter("writer");
		String no = multi.getParameter("no");
		
	
		BoardDAO dao = new BoardDAO();
		
		BoardVO board = new BoardVO();
		board.setNo(Integer.parseInt(no));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = dao.updateBoard(board);
		
		
		

		@SuppressWarnings("rawtypes")
		Enumeration files = multi.getFileNames();
		while(files.hasMoreElements()) {
			String fileName = (String)files.nextElement();
			File f = multi.getFile(fileName);
			
			if(f != null) {
				String fileOriName = multi.getOriginalFileName(fileName);
				String fileSaveName = multi.getFilesystemName(fileName);
				int fileSize = (int)f.length();
				
				FileVO file = new FileVO();
				file.setBoardNo(Integer.parseInt(no));
				file.setFileOriName(fileOriName);
				file.setFileSaveName(fileSaveName);
				file.setFileSize(fileSize);
				
				dao.insertFile(file);
				
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setView("redirect:/work-spring/detail.do?type=list&no="+no);
		return mav;
	}
	
	
	@RequestMapping("/updateFormBoard.do")
	public ModelAndView updateFormBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession	 	session 	= request.getSession();
		LoginVO 		user 		= (LoginVO)session.getAttribute("user");
		String 			id 			= user.getId();
		int 			no 			= Integer.parseInt(request.getParameter("no"));
		
		BoardDAO dao = new BoardDAO();
		BoardVO board = dao.selectOne(id,no);
	    List<FileVO> fileList = dao.selectFileByNo(no);
	      
	      ModelAndView mav = new ModelAndView();
	      mav.addAtrribute("board", board);
	      mav.addAtrribute("fileList", fileList);
	      
		mav.setView("WEB-INF/views/board/updateForm.jsp");

		return mav;
	}
	
	@RequestMapping("/removeBoard.do")
	public ModelAndView removeBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession 	session = request.getSession();
		LoginVO 		user 	= (LoginVO)session.getAttribute("user");
		String 			id 		= user.getId();
		int 			no 		= Integer.parseInt(request.getParameter("no"));
		
		BoardDAO dao = new BoardDAO();
		CommentDAO cmt_dao = new CommentDAO();
		int result = dao.deleteBoard(id,no); //게시판 제거
		int result2 = cmt_dao.deleteByBoardNo(no);
		int result3 = dao.deleteFileByBoardNo(no);
		// 댓글 제거
		// file 제거
		
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("redirect:/work-spring/list.do");
		
		return mav;

	}
	
	
	
	
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      String boardNo 	= request.getParameter("no");
	      String type		= request.getParameter("type");
	      
	      
	      BoardDAO 		dao 	= new BoardDAO();
	      CommentDAO 	cmtDAO 	= new CommentDAO();
	      if (type != null && type.equals("list")) {
		         dao.updateViewCnt(Integer.parseInt(boardNo));
		  }
	      
	      BoardVO 			board 		= dao.selectByNo(Integer.parseInt(boardNo));
	      List<FileVO> 		fileList 	= dao.selectFileByNo(Integer.parseInt(boardNo));
	      List<CommentVO> 	cmtList 	= cmtDAO.selectByNo(Integer.parseInt(boardNo));

	      ModelAndView mav = new ModelAndView();
	      mav.setView("WEB-INF/views/board/detail.jsp");
	      mav.addAtrribute("cmtList", cmtList);
	      mav.addAtrribute("board", board);
	      mav.addAtrribute("fileList", fileList);
	      return mav;
	}

	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String field_=request.getParameter("f");
		String query_=request.getParameter("q");
		String field = "title";
		String query = "";
		  
		if(field_ != null) field = field_;
		if(query_ != null) query = query_;
		  
		BoardDAO dao = new BoardDAO();
		
		List<BoardViewVO> list = dao.selectListView(field, query, 1);
		
			
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("list", list);
		mav.setView("WEB-INF/views/board/list.jsp");
		
		return mav;
	}
	
	@RequestMapping("/writeForm.do")
	public ModelAndView writeForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list = dao.selectAllBoard();
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("list", list);
		mav.setView("WEB-INF/views/board/writeForm.jsp");
		
		return mav;
	}
	
	@RequestMapping("/write.do")
	public ModelAndView write(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String saveFolder = "C:\\Users\\bit\\Downloads\\jblog-20200319T061927Z-001\\work-spring\\WebContent\\upload";
		//String saveFolder = "C:\\Users\\wjdgh\\git\\work-spirng\\WebContent\\upload";

		MultipartRequest 	multi 	= new MultipartRequest(request, saveFolder, 1024 * 1024 * 3, "utf-8", new MyFileRenamePolicy());
		String 				title 	= multi.getParameter("title");
		String 				writer 	= multi.getParameter("writer");
		String 				content = multi.getParameter("content");
		
		BoardDAO dao = new BoardDAO();
		int boardNo = dao.selectNo();
		
		BoardVO board = new BoardVO();
		board.setNo(boardNo);
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		dao.insert(board);
		

		@SuppressWarnings("rawtypes")
		Enumeration files = multi.getFileNames();
		while(files.hasMoreElements()) {
			String fileName = (String)files.nextElement();
			File f = multi.getFile(fileName);
			
			if(f != null) {
				String fileOriName = multi.getOriginalFileName(fileName);
				String fileSaveName = multi.getFilesystemName(fileName);
				int fileSize = (int)f.length();
				
				FileVO file = new FileVO();
				file.setBoardNo(boardNo);
				file.setFileOriName(fileOriName);
				file.setFileSaveName(fileSaveName);
				file.setFileSize(fileSize);
				
				dao.insertFile(file);
				
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setView("redirect:/work-spring/list.do");
		return mav;

	}
	
	
}
