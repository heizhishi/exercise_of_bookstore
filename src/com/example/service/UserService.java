package com.example.service;

import java.util.Iterator;
import java.util.Set;

import com.example.dao.BookDAO;
import com.example.dao.TradeDAO;
import com.example.dao.TradeItemDAO;
import com.example.dao.UserDAO;
import com.example.dao.impl.BookDAOImpl;
import com.example.dao.impl.TradeDAOImpl;
import com.example.dao.impl.TradeItemDAOImpl;
import com.example.dao.impl.UserDAOImpl;
import com.example.domain.Trade;
import com.example.domain.TradeItem;
import com.example.domain.User;

public class UserService {

	private UserDAO userDAO = new UserDAOImpl();

	public User getUserByUserName(String username) {
		return userDAO.getUser(username);
	}

	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();

	public User getUserWithTrades(String username) {

		// 调用 UserDAO 的方法获取 User 对象
		User user = userDAO.getUser(username);
		if (user == null) {
			return null;
		}

		// 调用 TradeDAO 的方法获取 Trade 的集合，把其装配为 User 的属性
		int userId = user.getUserId();

		// 调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);

		if (trades != null) {
			Iterator<Trade> tradeIt = trades.iterator();

			while (tradeIt.hasNext()) {
				Trade trade = tradeIt.next();

				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO
						.getTradeItemsWithTradeId(tradeId);

				if (items != null) {
					for (TradeItem item : items) {
						item.setBook(bookDAO.getBook(item.getBookId()));
					}

					if (items != null && items.size() != 0) {
						trade.setItems(items);
					}
				}

				if (items == null || items.size() == 0) {
					tradeIt.remove();
				}

			}
		}

		if (trades != null && trades.size() != 0) {
			user.setTrades(trades);
		}

		return user;
	}

}
