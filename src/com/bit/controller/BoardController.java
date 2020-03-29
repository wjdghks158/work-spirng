package com.bit.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.dao.BoardDAO;
import com.bit.dao.CommentDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.RequestMapping;
import com.bit.util.MyFileRenamePolicy;
import com.bit.vo.BoardVO;
import com.bit.vo.BoardViewVO;
import com.bit.vo.CommentVO;
import com.bit.vo.FileVO;
import com.bit.vo.LoginVO;
import com.oreilly.servlet.MultipartRequest;

public class BoardController {	
	
	@RequestMapping("/updateFormBoard.do")
	public ModelAndView updateFormBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("no"));
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		String id = user.getId();
		int no = Integer.parseInt(request.getParameter("no"));
		
		BoardDAO dao = new BoardDAO();
		BoardVO board = dao.selectOne(id,no);
		ModelAndView mav = new ModelAndView();
	      List<FileVO> fileList = dao.selectFileByNo(no);
	      
	      mav.addAtrribute("board", board);
	      mav.addAtrribute("fileList", fileList);
	      
		mav.setView("WEB-INF/views/board/updateForm.jsp");
		
		/*
		 * DispatcherServlet (FrontController)���� ��û�� �޾� ó���ϵ�.
		 * sendReirect ������� �̵���Ű�ڴ�.
		 * -> "redirect:" �̵� ����� �����ϱ� ���� ǥ�� �뵵.
		 * -> ���� ȭ�鿡�� �̵��� URL���� ǥ��!
		 */
		return mav;

	}
	
	@RequestMapping("/removeBoard.do")
	public ModelAndView removeBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("no"));
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO)session.getAttribute("user");
		String id = user.getId();
		int no = Integer.parseInt(request.getParameter("no"));
		
		BoardDAO dao = new BoardDAO();
		int result = dao.deleteBoard(id,no);
		
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
	
	
	
	
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      int boardNo=Integer.parseInt(request.getParameter("no"));
	      String type=request.getParameter("type"); // ��ȸ��
	      BoardDAO dao = new BoardDAO();
	      CommentDAO cmtDAO = new CommentDAO();
	      if (type != null && type.equals("list")) { // ��ȸ�� ���� //?
	         dao.updateViewCnt(boardNo);
	      }//end if
	      BoardVO board = dao.selectByNo(boardNo);
	      List<FileVO> fileList = dao.selectFileByNo(boardNo);
	      List<CommentVO> cmtList = cmtDAO.selectByNo(boardNo);
	      for(FileVO item : fileList) {
	    	  System.out.println(item);
	      }
	      ModelAndView mav = new ModelAndView();
	      mav.setView("WEB-INF/views/board/detail.jsp");
	      mav.addAtrribute("cmtList", cmtList);
	      mav.addAtrribute("board", board);
	      mav.addAtrribute("fileList", fileList);
	      return mav;
	}
	
	
	
	/*
	 * ��� �Խñ� ��ȸ
	 */
	// handlerRequest�� ����
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	      String field_=request.getParameter("f"); // ��ȸ��
	      String query_=request.getParameter("q"); // ��ȸ��
	      
	      String field = "title";
	      String query = "";
	      
	      if(field_ != null) field = field_;
	      if(query_ != null) query = query_;
	      
		BoardDAO dao = new BoardDAO();
		
//		List<BoardVO> list = dao.selectList(field, query, 1);
		List<BoardViewVO> list = dao.selectListView(field, query, 1);
		//List<BoardVO> list = dao.selectAllBoard();

		
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
		
		/*
		 * �Խ��� ���( ÷������)
		 * DataBase���� ���� ���(���� ��)�� ����
		 * �ٸ� ������ �����(�ϵ��ũ)�� ���� ������ �����Ͽ�
		 * Server�� DB�κ��� ��θ� �޾� ���� ����ҿ� ����!
		 */
		//String saveFolder = "C:\\Users\\bit\\Downloads\\jblog-20200319T061927Z-001\\work-spring\\WebContent\\upload";
		String saveFolder = "C:\\Users\\wjdgh\\git\\work-spirng\\WebContent\\upload";
		System.out.println("이거 실화냐1");
		/*
		 * MultipartRequest
		 * [Parameter]
		 * 	1. ��û ��ü
		 * 	2. ���� ���
		 *  3. ������ ũ�� �Ծ�
		 *  4. ������ ���ڵ�
		 *  5. ������ ���ϸ��� �ٲ��ִ� rename ��å
		 *  
		 */
		System.out.println("이거 실화냐4444444444444");
		MultipartRequest multi = new MultipartRequest(request, saveFolder, 1024 * 1024 * 3, "utf-8", new MyFileRenamePolicy());
		System.out.println("이거 실화냐2");
		/*
		 * �Խñ� ����
		 */
		String title = multi.getParameter("title");
		String writer = multi.getParameter("writer");
		String content = multi.getParameter("content");
		
		BoardDAO dao = new BoardDAO();
		int boardNo = dao.selectNo();
		
		BoardVO board = new BoardVO();
		board.setNo(boardNo);
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		dao.insert(board);
		
		/*
		 * ÷������ ����
		 */
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
		System.out.println("이거 실화냐3");
		ModelAndView mav = new ModelAndView();
		mav.setView("WEB-INF/views/board/list.jsp");
		
		
		return mav;

	}
	
	
}
