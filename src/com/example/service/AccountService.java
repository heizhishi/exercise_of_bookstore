package com.example.service;

import com.example.dao.AccountDAO;
import com.example.dao.impl.AccountDAOImpl;
import com.example.domain.Account;

public class AccountService {
	private AccountDAO accountDAO = new AccountDAOImpl();

	/**
	 * ͨ��accountId���account����
	 * @param acccountId
	 * @return
	 */
	public Account getAccount(int accountId) {
		Account account=accountDAO.get(accountId);
		return account;
	}
}
