<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function() {
		//ajax修改单个商品的数量
		//1.获取页面所有的：text，并添加change响应函数
		//2.请求地址：bookServlet
		//3.请求参数：method:updateItemQuantity,id:xxx,quantity:xxx,time:new Date()
		//4.在updateItemQuantity方法中，获取quantity,id，以及购物车对象,在调用service的方法修改
		//5.传回json数据：bookNumber:,totalMoney
		//6.更新当前页面的bookNumber和totalMoney
		$(":text").change(
				function() {
					var name = $.trim($(this).parent().parent()
							.find("td:first").text());
					var flag = confirm("确定要修改" + name + "的数量吗？");
					if (!flag) {
						$(this).val($(this).attr("quantity"));
						return;
					}
					var id = $.trim(this.name);
					var quantity = $.trim(this.value);
					var reg = /^\d+$/;
					flag = reg.test(quantity);
					if (flag) {
						quantity = parseInt(quantity);
						if (quantity < 0) {
							flag = false;
						}
					}
					if (!flag) {
						alert("输入格式不合法！");
						$(this).val($(this).attr("quantity"));
						return;
					}
					if (quantity == 0) {
						var flag2 = confirm("确定要删除" + name + "吗？");
						if (flag2) {
							var href = $(this).parent().parent()
									.find("td:last a").attr("href");
							var hidden=$(":hidden").serialize();
							href=href+"&"+hidden;
							window.location.href=href;
							return;
						} else {
							$(this).val($(this).attr("quantity"));
							return;
						}
					}
					$(this).attr("quantity", $.trim(this.value));
					var url = "bookServlet";
					var args = {
						"method" : "updateItemQuantity",
						"quantity" : quantity,
						"id" : id,
						"time" : new Date()
					};
					$.post(url, args, function(data) {
						var num = data.bookNumber;
						var money = data.totalMoney;
						$("#totalMoney").text(money);
						$("#totalNumber").text(num);
					}, "JSON");
				});
		$(".delete").click(function() {
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除" + title + "的信息吗？");
			return flag;
		});

	})
</script>
<%@ include file="/commons/queryCondition.jsp"%>
</head>
<body>
	<center>
		<h1>查看购物车</h1>
		<br> 您的购物车中有<span id="totalNumber">${sessionScope.shoppingCart.bookNumber}</span>本书
		<br>
		<table cellpadding="10" cellspacing="0">
			<tr>
				<th>书名</th>
				<th>数量</th>
				<th>价格</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${sessionScope.shoppingCart.items}" var="item">
				<tr>
					<td>${item.book.title }</td>
					<td><input type="text" size="1" value="${item.quantity}"
						name="${item.book.id}" quantity="${item.quantity}" />
					</td>
					<td>${item.book.price}</td>
					<td><a
						href="bookServlet?method=delete&pageNo=${param.pageNo}&id=${item.book.id}"
						class="delete">删除</a>
					</td>
				</tr>

			</c:forEach>
			<tr>
				<td colspan="4" align="left">总金额：￥<span id="totalMoney">${sessionScope.shoppingCart.totalMoney}</span>
				</td>

			</tr>
			<tr>
				<th colspan="4"><a
					href="bookServlet?method=getBooks&pageNo=${param.pageNo}">继续购物</a>
					<a href="bookServlet?method=clear&pageNo=${param.pageNo}">清空购物车</a>
					<a
					href="bookServlet?method=forwardPage&pageNo=${param.pageNo}&page=cash">结账</a>
				</th>


			</tr>
		</table>
	</center>
</body>
</html>