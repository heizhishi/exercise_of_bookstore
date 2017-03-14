package com.example.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<Integer, ShoppingCartItem> books = new HashMap<Integer, ShoppingCartItem>();

	/**
	 * �޸�ָ�������������
	 */
	public void updateItemQuantity(Integer id, int quantity) {
		ShoppingCartItem sci = books.get(id);
		if (sci != null) {
			sci.setQuantity(quantity);
		}
	}

	/**
	 * �Ƴ�ָ���Ĺ�����
	 * 
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}

	/**
	 * ��չ��ﳵ
	 */
	public void clear() {
		books.clear();
	}

	/**
	 * ���ع��ﳵ�Ƿ�Ϊ��
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}

	/**
	 * ��ȡ���ﳵ�����е���Ʒ���ܵ�Ǯ��
	 * 
	 * @return
	 */
	public float getTotalMoney() {
		float total = 0;

		for (ShoppingCartItem sci : getItems()) {
			total += sci.getItemMoney();
		}

		return total;
	}

	/**
	 * ��ȡ���ﳵ�е����е� ShoppingCartItem ��ɵļ���
	 * 
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}

	/**
	 * ���ع��ﳵ����Ʒ��������
	 * 
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;

		for (ShoppingCartItem sci : books.values()) {
			total += sci.getQuantity();
		}

		return total;
	}

	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}

	/**
	 * ���鹺�ﳵ���Ƿ��� id ָ������Ʒ
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}

	/**
	 * ���ﳵ�����һ����Ʒ
	 * 
	 * @param book
	 */
	public void addBook(Book book) {
		//1.��鹺�ﳵ����û�и���Ʒ, ����, ��ʹ������ +1, ��û��,
		//�´������Ӧ�� ShoppingCartItem, ��������뵽 books ��
		ShoppingCartItem shoppingCartItem = books.get(book.getId());
		if (shoppingCartItem == null) {
			shoppingCartItem = new ShoppingCartItem(book);
			books.put(book.getId(), shoppingCartItem);
		} else {
			shoppingCartItem.increment();
		}
	}

	public ShoppingCartItem getShoppingCartItem(Integer id) {
		ShoppingCartItem shoppingCartItem = books.get(id);
		return shoppingCartItem;
	}

	public ShoppingCart() {
	}

}
