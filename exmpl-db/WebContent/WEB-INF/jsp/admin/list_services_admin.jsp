<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Tariff Plans" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%> <c:set var="n" value="0" /> <c:forEach
					var="categories" items="${categories}">
					<p>${categories.name}</p>
					<table id="list_services_table">
						<thead>
							<tr>
								<td>№</td>
								<td><fmt:message key="link_services_jsp.link.name" /></td>
								<td><fmt:message key="link_services_jsp.link.price" /></td>
								<td><fmt:message key="list_services_admin_jsp.table.edit" /></td>
								<td><fmt:message key="list_services_admin_jsp.table.delete" /></td>
								<td>Users</td>
								<td>Money</td>
							</tr>
						</thead>
						<c:set var="number" value="0" />
						<c:set var="k" value="0" />
						<c:forEach var="item" items="${services}">
							<c:forEach var="numbers" items="${numberusers}">
								<c:if test="${item.id == numbers.service_id}">
									<c:set var="number" value="${numbers.number_users}" />
								</c:if>
							</c:forEach>
							<c:if test="${categories.id == item.categoryId }">
								<c:set var="k" value="${k+1}" />
								<tr>
									<td><c:out value="${k}" /></td>
									<td>${item.name}</td>
									<td>${item.price}</td>
									<td><a href="controller?command=editTariff&id=${item.id}"><fmt:message
												key="list_services_admin_jsp.table.edit" /></a></td>
									<td><a
										href="controller?command=deleteTariff&id=${item.id}"><fmt:message
												key="list_services_admin_jsp.table.delete" /></a></td>
									<td><c:out value="${number}" /></td>
									<td><c:out value="${number * item.price}" /></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					<a href="controller?command=newTariff&id=${categories.id}"><fmt:message
							key="list_services_admin_jsp.link.new_tariff" /></a>
				</c:forEach>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>