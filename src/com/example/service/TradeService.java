package com.example.service;

import java.util.Set;

import com.example.dao.TradeDAO;
import com.example.dao.impl.TradeDAOImpl;
import com.example.domain.Trade;

public class TradeService {
	private TradeDAO tradeDAO = new TradeDAOImpl();

	public Set<Trade> getTradesWithUserId(int userId) {
		return tradeDAO.getTradesWithUserId(userId);
	}

}
