package com.example.service;

import java.util.Set;

import com.example.dao.TradeDAO;
import com.example.dao.TradeItemDAO;
import com.example.dao.impl.TradeDAOImpl;
import com.example.dao.impl.TradeItemDAOImpl;
import com.example.domain.TradeItem;

public class TradeItemService {
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	public Set<TradeItem> getTradeItemsWithTradeId(int tradeId){
		return tradeItemDAO.getTradeItemsWithTradeId(tradeId);
	}
}
