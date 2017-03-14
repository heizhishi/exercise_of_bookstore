package com.example.test;

import org.junit.Test;

import com.example.dao.UserDAO;
import com.example.dao.impl.UserDAOImpl;
import com.example.domain.User;

public class UserDAOTest {
	UserDAO userDao = new UserDAOImpl();

	@Test
	public void testGetUser() {
		User user = userDao.getUser("AAA");
		System.out.println(user);
	}

}
