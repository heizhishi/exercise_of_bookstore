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
			// 1.获取连接
			connection = JDBCUtils.getConnection();
			// 2.开启事务
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 3.利用ThreadLocal把连接和当前线程绑定
			ConnectionContext.getInstance().bindConnection(connection);
			// 4.把请求转给目标Servlet
			chain.doFilter(request, response);
			// 5.提交事务
			try {
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 6.回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
		} finally {
			// 7.解除绑定
			ConnectionContext.getInstance().removeConnection();
			// 8.关闭连接
			JDBCUtils.release(connection);

		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
