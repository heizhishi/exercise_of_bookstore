package com.example.util;

import java.sql.Connection;

import com.example.db.JDBCUtils;

public class ConnectionContext {
	public ConnectionContext() {
	}

	private static ConnectionContext instance = new ConnectionContext();
	private ThreadLocal<Connection> conThreadLocal = new ThreadLocal<Connection>();

	public static ConnectionContext getInstance() {
		return instance;

	}

	public void bindConnection(Connection connection) {
		conThreadLocal.set(connection);
	}

	public Connection getConnection() {
		return conThreadLocal.get();
	}

	public void removeConnection() {
		conThreadLocal.remove();
	}
}
