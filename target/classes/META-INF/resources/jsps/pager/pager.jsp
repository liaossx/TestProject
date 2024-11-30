<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(pc > ${pageinfo.pages}) {//判断当前页码是否大于最大页
			alert('超出了最大页码！');
			return;
		}
		location = "${url}&pageNum="+pc;
	}
</script>

<div class="divBody">
  <div class="divContent">
    <%--上一页 --%>
        <c:if test="${pageinfo.isFirstPage}">
            <span class="spanBtnDisabled">上一页</span>
        </c:if>
        <c:if test="${!pageinfo.isFirstPage}">
            <a href="${url}&pageNum=${pageinfo.prePage}" class="aBtn bold">上一页</a>
        </c:if>
    
    <%-- 显示页码列表 --%>
        <%--和百度一样，默认10个格子，每页8条记录 --%>

        <%--分成两种情况，10个格子以上，10个格子以下--%>

        <%--1.10个格子以内--%>
        <c:if test="${pageinfo.pages<=10}" >
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${pageinfo.pages}"/>
        </c:if>
        <%--
            2.10个格子以上

            bengin ,end 取值  通用定式

            当前页码： 8          3       12
                      11         6        15
                      10         5        14

                  begin =  pageNum- 5
                  end   =   pageNum +4
        --%>
        <%--先写通用定式--%>
        <c:if test="${pageinfo.pages>10}">
            <c:set var="begin" value="${pageinfo.pageNum-5}"/>
            <c:set var="end" value="${pageinfo.pageNum+4}"/>
        </c:if>
        <%--左侧扶正--%>
        <c:if test="${begin<1}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${begin + 9 }"/>
        </c:if>
        <%--右侧扶正--%>
        <c:if test="${end>pageinfo.pages}">
            <c:set  var="end" value="${pageinfo.pages}"/>
            <c:set var="begin" value="${end-9}"/>
        </c:if>

        <%--按照 begin  end  输出格子--%>
        <c:forEach begin="${begin}"  end="${end}" var="i">
                <c:if test="${i eq pageinfo.pageNum}">
                    <a href="${url}&pageNum=${i}" class="spanBtnSelect">${i}</a>
                </c:if>
            <c:if test="${i != pageinfo.pageNum}">
                <a href="${url}&pageNum=${i}" class="aBtn">${i}</a>
            </c:if>

        </c:forEach>

     <%--下一页 --%>
        <c:if test="${pageinfo.isLastPage}">
            <span class="spanBtnDisabled">下一页</span>
        </c:if>
        <c:if test="${!pageinfo.isLastPage}">
            <a href="${url}&pageNum=${pageinfo.nextPage}" class="aBtn bold">下一页</a>
        </c:if>

    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%-- 共N页 到M页 --%>
    <span>共${pageinfo.pages}页</span>
    <span>到</span>
    <input type="text" class="inputPageCode" id="pageCode" value="1"/>
    <span>页</span>
    <a href="javascript:_go();" class="aSubmit">确定</a>
  </div>
</div>