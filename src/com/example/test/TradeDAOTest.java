package com.example.test;




import java.util.Set;

import org.junit.Test;

import com.example.dao.TradeDAO;
import com.example.dao.impl.TradeDAOImpl;
import com.example.domain.Trade;

public class TradeDAOTest {
	TradeDAO tradeDAO = new TradeDAOImpl();

	@Test
	public void testInsertTrade() {
		
		Trade trade = new Trade(new java.sql.Timestamp(new java.util.Date().getTime()), 2);
		tradeDAO.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> trades=tradeDAO.getTradesWithUserId(2);
		System.out.println(trades);
	}

}
