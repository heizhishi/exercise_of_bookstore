package com.example.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.example.dao.TradeItemDAO;
import com.example.dao.impl.TradeItemDAOImpl;
import com.example.domain.TradeItem;

public class TradeItemDAOTest {
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<TradeItem>();

		TradeItem tradeItem = new TradeItem();
		tradeItem.setBookId(1);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(12);
		items.add(tradeItem);

		tradeItem = new TradeItem();
		tradeItem.setBookId(2);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(12);
		items.add(tradeItem);

		tradeItem = new TradeItem();
		tradeItem.setBookId(3);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(12);
		items.add(tradeItem);

		tradeItemDAO.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(12);
		System.out.println(items.size());

	}

}
