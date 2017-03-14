package com.example.test;

import org.junit.Test;

import com.example.dao.AccountDAO;
import com.example.dao.impl.AccountDAOImpl;
import com.example.domain.Account;

public class AccountDaoTest {
	AccountDAO accountDAO = new AccountDAOImpl();

	@Test
	public void testGet() {
		Account account = accountDAO.get(1);
		System.out.println(account);
	}

	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(1, 50);
	}

}
