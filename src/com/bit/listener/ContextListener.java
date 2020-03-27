package com.bit.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bit.dao.BoardDAO;

// Listener (������)
// -> Ư�� (Event)����� �߻��ϱ⸦ ��ٷȴٰ�, �߻��ϸ� ����Ǵ� �޼ҵ�! -> ������Ʈ
public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	// ���ø����̼� ���� �� ȣ��
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		ServletContext sc = arg.getServletContext();
		
		BoardDAO boardDAO = new BoardDAO();
		sc.setAttribute("boardDAO", boardDAO);
	}

}
