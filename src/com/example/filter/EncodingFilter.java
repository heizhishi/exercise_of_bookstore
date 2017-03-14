package com.example.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String encoding=filterConfig.getServletContext().getInitParameter("encoding");

		request.setCharacterEncoding(encoding);
		String title=request.getParameter("title");
		//���Դ��� 	if(title!=null){
		//���Դ��� 		System.out.println(title);
		//���Դ��� 		System.out.println(new String(title.getBytes("ISO-8859-1"),"UTF-8"));	
		//���Դ��� 	}
		//���Դ���   System.out.println(encoding);
		//���Դ���   System.out.println("����˭");
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	private FilterConfig filterConfig=null;
	public void init(FilterConfig fConfig) throws ServletException {
this.filterConfig=fConfig;
	}

}
