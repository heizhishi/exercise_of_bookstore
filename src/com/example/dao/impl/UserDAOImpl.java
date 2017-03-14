package com.example.dao.impl;

import com.example.dao.UserDAO;
import com.example.domain.User;


public class UserDAOImpl extends BaseDao<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql = "SELECT userId, username, accountId " +
				"FROM userinfo WHERE username = ?";
		return query(sql, username); 
	}

}
