package com.bit.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bit.dao.BoardDAO;

// Listener (리스너)
// -> 특정 (Event)사건이 발생하기를 기다렸다가, 발생하면 수행되는 메소드! -> 컴포넌트
public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	// 어플리케이션 시작 시 호출
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		ServletContext sc = arg.getServletContext();
		
		BoardDAO boardDAO = new BoardDAO();
		sc.setAttribute("boardDAO", boardDAO);
	}

}
