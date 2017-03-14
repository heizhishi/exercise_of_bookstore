package com.example.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.db.JDBCUtils;
import com.example.util.ConnectionContext;

/**
 * Servlet Filter implementation class TransactionFilter
 */
public class TransactionFilter implements Filter {
	public TransactionFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		try {
			// 1.��ȡ����
			connection = JDBCUtils.getConnection();
			// 2.��������
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 3.����ThreadLocal�����Ӻ͵�ǰ�̰߳�
			ConnectionContext.getInstance().bindConnection(connection);
			// 4.������ת��Ŀ��Servlet
			chain.doFilter(request, response);
			// 5.�ύ����
			try {
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 6.�ع�����
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
		} finally {
			// 7.�����
			ConnectionContext.getInstance().removeConnection();
			// 8.�ر�����
			JDBCUtils.release(connection);

		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
