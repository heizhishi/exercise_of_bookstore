package com.example.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.dao.TradeDAO;
import com.example.domain.Trade;


public class TradeDAOImpl extends BaseDao<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade (userid, tradetime) VALUES " +
				"(?, ?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet(queryForList(sql, userId));
	}

}
