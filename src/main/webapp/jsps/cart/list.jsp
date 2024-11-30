<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jq.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
<script type="text/javascript">
$(function() {
	showTotal();//显示合计
	// 给全选按钮添加点击事件
	$("#selectAll").click(function() {
		var flag = $(this).prop("checked");//获取全选的状态
		setAll(flag);//让所有条目复选框与全选同步
		setJieSuanStyle(flag);//让结算按钮与全选同步
	});
	
	// 给条目复选框添加事件
	$(":checkbox[name=checkboxBtn]").click(function() {
		var selectedCount = $(":checkbox[name=checkboxBtn]:checked").length;//被勾选复选框个数
		var allCount = $(":checkbox[name=checkboxBtn]").length;//所有条目复选框个数
		if(selectedCount == allCount) {//全选了
			$("#selectAll").prop("checked", true);//勾选全选复选框
			setJieSuanStyle(true);//使结算按钮可用
		} else if(selectedCount == 0) {//全撤消了
			$("#selectAll").prop("checked", false);//撤消全选复选框
			setJieSuanStyle(false);//使结算按钮不可用			
		} else {//未全选
			$("#selectAll").prop("checked", false);//撤消全选复选框
			setJieSuanStyle(true);//使结算按钮可用
		}
		showTotal();//重新计算合计
	});
	
	// 给jia、jian添加事件
	$(".jian").click(function() {
		var cartItemId = $(this).attr("id").substring(0, 32);
		var quantity = Number($("#" + cartItemId + "Quantity").val());
		if(quantity == 1) {
			if(confirm("您是否真要删除该条目？")) {
				location.href="/goods/cart/removeCartByCid/"+cartItemId;
			}
		} else {
			sendUpdate(cartItemId, quantity-1);
		}
	});
	$(".jia").click(function() {
		var cartItemId = $(this).attr("id").substring(0, 32);
		var quantity = Number($("#" + cartItemId + "Quantity").val());
		sendUpdate(cartItemId, quantity+1);
	});
});

// 异步请求，修改数量
function sendUpdate(cartItemId, quantity) {
	/*
	 1. 通过cartItemId找到输入框元素
	 2. 通过cartItemId找到小计元素
	*/
	var input = $("#" + cartItemId + "Quantity");
	var subtotal = $("#" + cartItemId + "Subtotal");
	var currPrice = $("#" + cartItemId + "CurrPrice");
	/*异步请求*/
	$.ajaxSettings.async=false;
	$.post("/goods/cart/updateCartForQuantityByCid",
			{"cartitemid":cartItemId,"quantity":quantity},
			function(res){
		if(res){
			input.val(quantity);
			subtotal.text(round(currPrice.text() * quantity, 2));
			showTotal();
		}
	});
}

// 设置所有条目复选框
function setAll(flag) {
	$(":checkbox[name=checkboxBtn]").prop("checked", flag);//让所有条目的复选框与参数flag同步
	showTotal();//重新设置合计
}

// 设置结算按钮的样式
function setJieSuanStyle(flag) {
	if(flag) {// 有效状态
		$("#jiesuan").removeClass("kill").addClass("jiesuan");//切换样式
		$("#jiesuan").unbind("click");//撤消“点击无效”
	} else {// 无效状态
		$("#jiesuan").removeClass("jiesuan").addClass("kill");//切换样式
		$("#jiesuan").click(function() {//使其“点击无效”
			return false;
		});
	}
}

// 显示合计
function showTotal() {
	var total = 0;//创建total，准备累加
	/*
	1. 获取所有被勾选的复选框，遍历之
	*/
	$(":checkbox[name=checkboxBtn]:checked").each(function() {
		/*
		2. 通过复选框找到小计
		*/
		var subtotal = Number($("#" + $(this).val() + "Subtotal").text());
		total += subtotal;
	});
	/*
	3. 设置合计
	*/
	$("#total").text(round(total, 2));
}




</script>
  </head>
  <body>

	<%--如果当前用户，没有购物车条目，显示空车图片--%>
	<c:if test="${empty cartpovos || fn:length(cartpovos)==0 }">

		<table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
					<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
				</td>
				<td>
					<span class="spanEmpty">您的购物车中暂时没有商品</span>
				</td>
			</tr>
		</table>
	</c:if>

<br/>
<br/>

<c:if test="${not empty cartpovos && fn:length(cartpovos)!=0}">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr align="center" bgcolor="#efeae5">
			<td align="left" width="50px">
				<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
			</td>
			<td colspan="2">商品名称</td>
			<td>单价</td>
			<td>数量</td>
			<td>小计</td>
			<td>操作</td>
		</tr>



	<c:forEach items="${cartpovos}" var="cartpovo">
		<tr align="center">
			<td align="left">
				<input value="${cartpovo.cartitem.cartitemid}" type="checkbox" name="checkboxBtn"
					   checked="checked"/>
			</td>
			<td align="left" width="70px">
				<a class="linkImage" href="<c:url value='/book/getBookByBid?bid=${cartpovo.book.bid}'/>">
					<img border="0" width="54" align="top" src="<c:url value='/${cartpovo.book.imageB}'/>"/>
				</a>
			</td>
			<td align="left" width="400px">
				<a href="<c:url value='/book/getBookByBid?bid=${cartpovo.book.bid}'/>">
					<span>${cartpovo.book.bname}</span>
				</a>
			</td>
			<td><span>&yen;<span class="currPrice" id="${cartpovo.cartitem.cartitemid}CurrPrice">${cartpovo.book.currprice}</span></span></td>
			<td>
				<a class="jian" id="${cartpovo.cartitem.cartitemid}Jian"></a>
				<input class="quantity" readonly="readonly" id="${cartpovo.cartitem.cartitemid}Quantity" type="text" value="${cartpovo.cartitem.quantity}"/>
				<a class="jia" id="${cartpovo.cartitem.cartitemid}Jia"></a>
			</td>
			<td width="100px">
				<span class="price_n">&yen;<span class="subTotal" id="${cartpovo.cartitem.cartitemid}Subtotal">${cartpovo.subtotal}</span></span>
			</td>
			<td>
				<a href="<c:url value='/cart/removeCartByCid/${cartpovo.cartitem.cartitemid}'/>">删除</a>
			</td>
		</tr>
	</c:forEach>
		<script>
			function  piliang(){
				/*1.定义个数组*/
				let ids = new Array();
				/*2. 获取页面上选中了的 checkbox*/
				$(":checkbox[name=checkboxBtn]:checked").each(function(){
					let  id =$(this).val();
					ids.push(id);
				});
				/*3. 把要删除的 数组 发送给后端*/
				location.href ="/goods/cart/removeBatchByIds?ids="+ids;
			}
		</script>
		<tr>
			<td colspan="4" class="tdBatchDelete">
				<a href="javascript:piliang();">批量删除</a>
			</td>
			<td colspan="3" align="right" class="tdTotal">
				<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
			</td>
		</tr>
		<tr>
			<td colspan="7" align="right">
				<script>
					function  yushengcheng(){
						let  array = new Array();
						$(":checkbox[name=checkboxBtn]:checked").each(function(){
							let  id =$(this).val();
							array.push(id);
						});
						location.href="/goods/cart/getCartsByCids?ids="+array;
					}

				</script>
				<a href="javascript:yushengcheng()" id="jiesuan" class="jiesuan"></a>
			</td>
		</tr>
	</table>
		<form id="form1" action="<c:url value='/jsps/cart/showitem.jsp'/>" method="post">
			<input type="hidden" name="cartItemIds" id="cartItemIds"/>
			<input type="hidden" name="method" value="loadCartItems"/>
		</form>
</c:if>

  </body>
</html>
