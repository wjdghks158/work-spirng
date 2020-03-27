package com.bit.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.dao.BoardDAO;
import com.bit.framework.ModelAndView;
import com.bit.framework.annotation.RequestMapping;
import com.bit.util.MyFileRenamePolicy;
import com.bit.vo.BoardVO;
import com.bit.vo.FileVO;
import com.oreilly.servlet.MultipartRequest;

public class BoardController {	
	
	@RequestMapping("/updateFormBoard.do")
	public ModelAndView updateFormBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("zzzzzzzz");
		System.out.println(request.getParameter("no"));
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("/WEB-INF/views/board/updateFormBoard.jsp");
		
		/*
		 * DispatcherServlet (FrontController)에서 요청을 받아 처리하되.
		 * sendReirect 방식으로 이동시키겠다.
		 * -> "redirect:" 이동 방식을 구분하기 위해 표시 용도.
		 * -> 유저 화면에는 이동된 URL으로 표시!
		 */
		return mav;

	}
	
	@RequestMapping("/removeBoard.do")
	public ModelAndView removeBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("no"));
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("member", session.getAttribute("member"));
		mav.setView("/WEB-INF/views/board/updateFormBoard.jsp");
		
		/*
		 * DispatcherServlet (FrontController)에서 요청을 받아 처리하되.
		 * sendReirect 방식으로 이동시키겠다.
		 * -> "redirect:" 이동 방식을 구분하기 위해 표시 용도.
		 * -> 유저 화면에는 이동된 URL으로 표시!
		 */
		return mav;

	}
	
	
	
	
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      System.out.println("@RequestMapping(/detail.do) 성공!!");
	      
	      int boardNo=Integer.parseInt(request.getParameter("no"));
	      System.out.println(boardNo);
	      String type=request.getParameter("type"); // 조회수
	      BoardDAO dao = new BoardDAO();
	      
	      if (type != null && type.equals("list")) { // 조회수 증가 //?
	         // /jblog/detail.do?type=list&no="+boardNo 여기서의 type=list
	         // type=list는 아직 안써도 되지만 Q&A할 때 비교할떄 사용한다?
	         dao.updateViewCnt(boardNo);
	      }//end if
	      BoardVO board = dao.selectByNo(boardNo);
	      List<FileVO> fileList = dao.selectFileByNo(boardNo);
	      
	      ModelAndView mav = new ModelAndView();
	      mav.setView("/WEB-INF/views/board/detail.jsp");
	      mav.addAtrribute("board", board);
	      mav.addAtrribute("fileList", fileList);
	      return mav;
	}
	
	
	
	/*
	 * 모든 게시글 조회
	 */
	// handlerRequest의 역할
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	      String field_=request.getParameter("f"); // 조회수
	      String query_=request.getParameter("q"); // 조회수
	      
	      String field = "title";
	      String query = "";
	      
	      if(field_ != null) field = field_;
	      if(query_ != null) query = query_;
	      
		BoardDAO dao = new BoardDAO();
		
		List<BoardVO> list = dao.selectList(field, query, 1);
		//List<BoardVO> list = dao.selectAllBoard();

		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("list", list);
		mav.setView("/WEB-INF/views/board/list.jsp");
		
		
		return mav;
	}
	
	@RequestMapping("/writeForm.do")
	public ModelAndView writeForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list = dao.selectAllBoard();
		
		ModelAndView mav = new ModelAndView();
		mav.addAtrribute("list", list);
		mav.setView("/WEB-INF/views/board/writeForm.jsp");
		
		
		return mav;
	}
	
	@RequestMapping("/write.do")
	public ModelAndView write(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*
		 * 게시판 등록( 첨부파일)
		 * DataBase에는 파일 경로(파일 명)을 저장
		 * 다른 물리적 저장소(하드디스크)에 실제 파일을 저장하여
		 * Server는 DB로부터 경로를 받아 실제 저장소에 접근!
		 */
		String saveFolder = "C:\\Users\\bit\\Downloads\\jblog-20200319T061927Z-001\\work-spring\\WebContent\\upload";
		
		/*
		 * MultipartRequest
		 * [Parameter]
		 * 	1. 요청 객체
		 * 	2. 저장 경로
		 *  3. 파일의 크기 규약
		 *  4. 파일의 인코딩
		 *  5. 동명의 파일명을 바꿔주는 rename 정책
		 *  
		 */
		
		MultipartRequest multi = new MultipartRequest(request, saveFolder, 1024 * 1024 * 3, "utf-8", new MyFileRenamePolicy());
		
		/*
		 * 게시글 저장
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
		 * 첨부파일 저장
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
		
		ModelAndView mav = new ModelAndView();
		mav.setView("/WEB-INF/views/board/list.jsp");
		
		
		return mav;

	}
	
	
}
