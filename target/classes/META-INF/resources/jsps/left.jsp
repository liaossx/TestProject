<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">
var bar = new Q6MenuBar("bar", "网上书城");
$(function() {
	bar.colorStyle = 4;
	bar.config.imgDir = "<c:url value='/menu/img/'/>";
	bar.config.radioButton=true;
	<c:forEach items="${categorypovos}"  var="povo">
		<c:forEach items="${povo.children}" var="child">
			bar.add("${povo.dad.cname}", "${child.cname}", "/goods/book/getBooksByCase?cid=${child.cid}", "body");
		</c:forEach>
	</c:forEach>

	$("#menu").html(bar.toString());
});
</script>
</head>
  
<body>  
  <div id="menu"></div>
</body>
</html>
