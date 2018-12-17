<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Services" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/block_user.jspf"%>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<form id="make_order" action="controller">
					<input type="hidden" name="command" value="makeOrder" /> <input
						value="<fmt:message key="link_services_jsp.link.make_an_order"/>"
						type="submit" /> <a href="controller?command=showUserOrders"><fmt:message
							key="link_services_jsp.link.my_orders" /></a>
					<c:set var="n" value="0" />
					<c:forEach var="categories" items="${categories}">
						<p>${categories.name}</p>
						<table id="list_services_table">
							<thead>
								<tr>
									<td>№</td>
									<td><fmt:message key="link_services_jsp.link.name" /></td>
									<td><fmt:message key="link_services_jsp.link.price" /></td>
									<td><fmt:message key="link_services_jsp.link.order" /></td>
								</tr>
							</thead>
							<c:set var="k" value="0" />
							<c:forEach var="item" items="${services}">
								<c:if test="${categories.id == item.categoryId }">
									<c:set var="k" value="${k+1}" />
									<tr>
										<td><c:out value="${k}" /></td>
										<td>${item.name}</td>
										<td>${item.price}</td>
										<td><input type="checkbox" name="itemId"
											value="${item.id}" /></td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</c:forEach>
				</form> <a href="controller?command=writeToTxt&file=0"><fmt:message
						key="link_services_jsp.link.save_to_txt" /></a> <br> <a
				href="controller?command=writeToTxt&file=1"><fmt:message
						key="link_services_jsp.link.save_to_pdf" /></a> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>