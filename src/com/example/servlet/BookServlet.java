package com.example.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.domain.Account;
import com.example.domain.Book;
import com.example.domain.ShoppingCart;
import com.example.domain.ShoppingCartItem;
import com.example.domain.Trade;
import com.example.domain.TradeItem;
import com.example.domain.User;
import com.example.service.AccountService;
import com.example.service.BookService;
import com.example.service.TradeItemService;
import com.example.service.TradeService;
import com.example.service.UserService;
import com.example.util.BookStoreWebUtils;
import com.example.web.CriteriaBook;
import com.example.web.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected void getBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		int id = -1;
		Book book = null;

		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}

		if (id > 0) {
			book = bookService.getBook(id);
		}
		if (book == null) {
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
			return;
		}
		req.setAttribute("book", book);
		req.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(req, resp);
	}

	protected void getBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (Exception e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (Exception e) {
		}
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page page = bookService.getPage(cb);
		request.setAttribute("bookPage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(
				request, response);
	}

	protected void addToCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		if (id > 0) {
			ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(req);
			flag = bookService.addToCart(id, shoppingCart);

		}
		if (flag) {
			getBooks(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
	}

	/**
	 * ��ѯ���ﳵ,����
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forwardPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String page = req.getParameter("page");
		req.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(
				req, resp);
	}

	/**
	 * ɾ�����ﳵĳ����Ʒ
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String idStr = req.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(req);
		bookService.deleteShoppingCartItem(id, shoppingCart);
		if (shoppingCart.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(
					req, resp);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/pages/shoppingCart.jsp").forward(
				req, resp);
	}

	/**
	 * ��չ��ﳵ
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void clear(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(req);
		bookService.clearShoppngCart(shoppingCart);
		req.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(req,
				resp);
	}

	/**
	 * ���¹��ﳵ��Ʒ����
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updateItemQuantity(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String quantityStr = req.getParameter("quantity");
		String idStr = req.getParameter("id");

		int id = -1;
		int quantity = -1;
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(req);
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {
		}
		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(shoppingCart, id, quantity);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(shoppingCart);
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(result);
		// ����ʹ��google-gson
		// Map<String, Object> result2=new HashMap<String, Object>();
		// result2.put("bookNumber", shoppingCart.getBookNumber());
		// result2.put("totalMoney", shoppingCart.getTotalMoney());
		// Gson gson=new Gson();
		// String gsonStr=gson.toJson(result2);
		// resp.setContentType("text/javascript");
		// resp.getWriter().print(gsonStr);
	}

	/**
	 * �����˻����
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void cash(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. ����֤: ��֤�����ֵ�Ƿ���ϻ����Ĺ淶: �Ƿ�Ϊ��, �Ƿ����תΪ int ����, �Ƿ���һ�� email. ����Ҫ���в�ѯ
		// ���ݿ������κε�ҵ�񷽷�.
		String username = req.getParameter("username");
		String accountId = req.getParameter("accountId");
		StringBuffer errors = validateFormField(username, accountId);
		// ����֤ͨ����
		if (errors.toString().equals("")) {
			errors = validateUser(username, accountId);
			// �û������˺���֤ͨ��
			if (errors.toString().equals("")) {
				errors = validateBookStoreNumber(req);
				// �����֤ͨ��
				if (errors.toString().equals("")) {
					errors = validateBalance(accountId, req);
				}
			}
		}

		if (!errors.toString().equals("")) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(req,
					resp);
			return;
		}
		// ��֤ͨ��ִ�о�����߼�����
		bookService.cash(BookStoreWebUtils.getShoppingCart(req), username,
				accountId);
		resp.sendRedirect(req.getContextPath() + "/success.jsp");
	}



	/**
	 * ����֤����֤�����ֵ�Ƿ���ϻ����Ĺ淶���Ƿ�Ϊ�գ��Ƿ����תΪint���ͣ��Ƿ���һ��email������Ҫ��ѯ���ݿ������κε�ҵ�񷽷�
	 * 
	 * @param username
	 * @param accountId
	 * @return
	 */
	protected StringBuffer validateFormField(String username, String accountId) {
		StringBuffer sb = new StringBuffer("");
		if (username == null || username.trim().equals("")) {
			sb.append("�û�������Ϊ�գ�<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			sb.append("�˺Ų���Ϊ�գ�<br>");
		}
		return sb;

	}

	/**
	 * ��֤�û������˺��Ƿ�ƥ��
	 * 
	 * @param username
	 * @param accountId
	 * @return
	 */
	protected StringBuffer validateUser(String username, String accountId) {
		StringBuffer sb = new StringBuffer("");
		UserService userService = new UserService();
		User user = userService.getUserByUserName(username);
		boolean flag = true;
		int account = 1;
		try {
			account = Integer.parseInt(accountId);
		} catch (Exception e) {
			flag = false;
		}

		if (user == null) {
			flag = false;
		} else {
			if (!user.getAccountId().equals(account)) {
				flag = false;
			}
		}
		if (!flag) {
			sb.append("�û������˺Ų�ƥ�䣡");
		}
		return sb;

	}

	/**
	 * ��֤����Ƿ���
	 * 
	 * @param request
	 * @return
	 */
	protected StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("");
		BookService bookService = new BookService();
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			Book book = item.getBook();
			int saleNumber = item.getQuantity();
			int storeNumber = bookService.getScoreNumber(book.getId());
			if (saleNumber > storeNumber) {
				sb.append(item.getBook().getTitle() + "��治�㣡<br>");
			}
		}

		return sb;

	}

	/**
	 * ��֤����Ƿ���
	 * 
	 * @param accountId
	 * @return
	 */
	protected StringBuffer validateBalance(String accountId,
			HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("");
		AccountService accountService = new AccountService();
		Account account = accountService
				.getAccount(Integer.parseInt(accountId));
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		float amount = shoppingCart.getTotalMoney();
		float balance = account.getBalance();
		if (balance < amount) {
			sb.append("���㣡");
		}
		return sb;

	}
}
