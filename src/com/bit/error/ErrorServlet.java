package com.bit.error;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorServlet
 */
@WebServlet("/ErrorServlet")
public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// error 페이지를 따로 만들어 응답하지 않고,
		// Servlet에서 HTML 코드로 직접 응답!
		response.setContentType("text/html; charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>에러 페이지</title></head>");
		out.println("<body>");
		out.println("<h2>오류 내용</h2>");
		out.println("<hr>");
		
		Exception e=(Exception)request.getAttribute("exception");
		e.printStackTrace(out); // 출력 객체 전달 -> 화면에 출력!
		
		out.println("</body>");
		out.println("</html>");
		out.close();
		
		super.service(request, response);
	}

    

}
