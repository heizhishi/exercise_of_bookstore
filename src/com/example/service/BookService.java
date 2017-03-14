package com.example.service;



import java.util.ArrayList;
import java.util.List;

import com.example.dao.AccountDAO;
import com.example.dao.BookDAO;
import com.example.dao.TradeDAO;
import com.example.dao.TradeItemDAO;
import com.example.dao.UserDAO;
import com.example.dao.impl.AccountDAOImpl;
import com.example.dao.impl.BookDAOImpl;
import com.example.dao.impl.TradeDAOImpl;
import com.example.dao.impl.TradeItemDAOImpl;
import com.example.dao.impl.UserDAOImpl;
import com.example.domain.Book;
import com.example.domain.ShoppingCart;
import com.example.domain.ShoppingCartItem;
import com.example.domain.Trade;
import com.example.domain.TradeItem;
import com.example.web.CriteriaBook;
import com.example.web.Page;

public class BookService {
	private BookDAO bookDAO = new BookDAOImpl();

	public Page<Book> getPage(CriteriaBook cb) {
		return bookDAO.getPage(cb);

	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart shoppingCart) {
		Book book = getBook(id);
		if (book != null) {
			shoppingCart.addBook(book);
			return true;
		}
		return false;
	}

	public void deleteShoppingCartItem(int id, ShoppingCart shoppingCart) {
		shoppingCart.removeItem(id);

	}

	public void clearShoppngCart(ShoppingCart shoppingCart) {
		shoppingCart.clear();

	}

	public void updateItemQuantity(ShoppingCart shoppingCart, int id,
			int quantity) {
		shoppingCart.updateItemQuantity(id, quantity);

	}

	public int getScoreNumber(Integer id) {
		int storeNumner = bookDAO.getStoreNumber(id);
		return storeNumner;

	}

	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO=new TradeItemDAOImpl();

	/**
	 * @param shoppingCart
	 * @param username
	 * @param accountId
	 */
	/**
	 * @param shoppingCart
	 * @param username
	 * @param accountId
	 */
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {
		// 1.更新mybooks数据表相关记录的salesamount和storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		// 2.更新accont数据表的balance
		int i=10/0;
		accountDAO.updateBalance(Integer.parseInt(accountId),
				shoppingCart.getTotalMoney());
		// 3.向trade数据表插入一条记录
		// tradeId;
		// tradeTime;
		// items;
		// userId;
		Trade trade = new Trade();
		trade.setTradeTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		// 4.向tradeItem数据表插入n条记录
		List<TradeItem> tradeItems=new ArrayList<TradeItem>();
		for(ShoppingCartItem sci:shoppingCart.getItems()){
			TradeItem tradeItem=new TradeItem();
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			tradeItems.add(tradeItem);
		}
		tradeItemDAO.batchSave(tradeItems);
		// 5.清空购物车
		shoppingCart.clear();
	}
}
