<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<h4>购物车已经清空</h4>
<a href="bookServlet?method=getBooks&pageNo=${param.pageNo}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}">继续购物</a>
</center>
</body>
</html>