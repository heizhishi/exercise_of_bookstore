package com.example.test;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.example.dao.BookDAO;
import com.example.dao.impl.BookDAOImpl;
import com.example.db.JDBCUtils;
import com.example.domain.Book;
import com.example.domain.ShoppingCartItem;
import com.example.util.ConnectionContext;
import com.example.web.CriteriaBook;
import com.example.web.Page;

public class BookDaoTest {
	private BookDAO bookDAO = new BookDAOImpl();

	@Test
	public void testGetBook() {
		Connection  connection=JDBCUtils.getConnection();
		ConnectionContext.getInstance().bindConnection(connection);
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(55, 65, 300);
		Page<Book> page = bookDAO.getPage(cb);
		System.out.println("pageno:" + page.getPageNo());
		System.out.println("tatalpagenumber:" + page.getTotalPageNumber());
		System.out.println("list:" + page.getList());
		System.out.println("prevPage:" + page.getPrevPage());
		System.out.println("nextPage:" + page.getNextPage());
	}

	@Test
	public void testGetScoreNumber() {
		int storeNumber = bookDAO.getStoreNumber(5);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
		
		Book book = bookDAO.getBook(1);
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem(book);
		shoppingCartItem.setQuantity(10);
		items.add(shoppingCartItem);
		
		book = bookDAO.getBook(2);
		shoppingCartItem = new ShoppingCartItem(book);
		shoppingCartItem.setQuantity(12);
		items.add(shoppingCartItem);
		
		book = bookDAO.getBook(3);
		shoppingCartItem = new ShoppingCartItem(book);
		shoppingCartItem.setQuantity(14);
		items.add(shoppingCartItem);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);

	}

}
