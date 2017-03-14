<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
$(function(){
	$("a").each(function(){
		this.onclick=function(){
			var hidden=$(":hidden").serialize();
			var href=this.href+"&"+hidden;
			this.href=href;   //或者window.location.href=href;
		};
	});
});


</script>
<input type="hidden" value="${param.minPrice }" name="minPrice">
<input type="hidden" value="${param.maxPrice }" name="maxPrice">