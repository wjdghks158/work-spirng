package com.bit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// Filter
// -> request, response의 객체를 먼저 받아,
// 선처리 또는 후처리 등의 작업을 수행하기 위한 클래스
public class EncodingFilter implements Filter {
	FilterConfig config;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String charset = config.getInitParameter("encoding");
		request.setCharacterEncoding(charset);
		System.out.println("===== before(filter) =====");
		chain.doFilter(request, response);
		 System.out.println("===== after(filter) =====");
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config=filterConfig;
		System.out.println("Filter init...");
		
	}
	
}
