package com.example.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.dao.BookDAO;
import com.example.domain.Book;
import com.example.domain.ShoppingCartItem;
import com.example.web.CriteriaBook;
import com.example.web.Page;

public class BookDAOImpl extends BaseDao<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " FROM mybooks WHERE id=?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		// 校验pageNo的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));

		return page;
	}

	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price>=? AND price<=?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	/**
	 * mysql分页使用LIMIT，其中fromIndex从0开始
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " FROM mybooks WHERE price>=? AND price<=? LIMIT ?,?";
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(),
				(cb.getPageNo() - 1) * pageSize, pageSize);
	}
	
	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql="update mybooks set salesAmount=salesAmount+?,storeNumber=storeNumber-? where id=?";
		List<ShoppingCartItem> scis=new ArrayList<ShoppingCartItem>(items);
		Object[][] args=new Object[scis.size()][3];
		for(int i=0;i<scis.size();i++){
			args[i][0]=scis.get(i).getQuantity();
			args[i][1]=scis.get(i).getQuantity();
			args[i][2]=scis.get(i).getBook().getId();
		}
		batch(sql, args);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storeNumber from mybooks where id=?";
		return getSingleVal(sql, id);
	}
}
