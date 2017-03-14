package com.example.test;

import java.util.List;

import org.junit.Test;

import com.example.dao.impl.BookDAOImpl;
import com.example.domain.Book;

public class BaseDaoTest {
	BookDAOImpl bookDAOImpl;

	public BaseDaoTest() {
		bookDAOImpl = new BookDAOImpl();
	}


	@Test
	public void testInsert() {
		String sql="insert into mybooks values(null,'Tom34','Java34ÖÐÎÄ°æ',50,'2009-06-02',20,20,'good_34')";
		long l=bookDAOImpl.insert(sql);
		System.out.println(l);
	}

	@Test
	public void testUpdate() {
		String sql="update mybooks set author='Tom01' where id=?";
		bookDAOImpl.update(sql, 1);
	}

	@Test
	public void testQuery() {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id=?";
		Book book = bookDAOImpl.query(sql, 4);
		System.out.println(book);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "select count(id) from mybooks";
		long count = bookDAOImpl.getSingleVal(sql);
		System.out.println(count);
	}

	@Test
	public void testBatch() {
		String sql = "update mybooks set salesAmount=?,storeNumber=? where id=?";
		bookDAOImpl.batch(sql, new Object[] { 20, 20, 1 },
				new Object[] { 20, 20, 2 }, new Object[] { 20, 20, 3 });
	}

	@Test
	public void testQueryForList() {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id<?";
		List<Book> books = (List<Book>) bookDAOImpl.queryForList(sql, 4);
		System.out.println(books);
	}

}
