<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Private Office" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>
<tr>
			<td class="content">
			<a href="controller?command=listOrders"><fmt:message key="private_office_jsp.link.orders"/></a>
			<br>	
			<a href="controller?command=listServices"><fmt:message key="private_office_jsp.link.tariff_plans"/></a>
			<br>
			<a href="controller?command=usersCommand"><fmt:message key="private_office_jsp.link.users"/></a>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>