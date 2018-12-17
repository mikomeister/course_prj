<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>

<c:set var="title" value="My Orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/block_user.jspf"%>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%> <c:choose>
					<c:when test="${fn:length(userOrderBeanListforUser) == 0}">No such orders</c:when>

					<c:otherwise>
						<p>Your orders</p>
						<table id="list_order_table">
							<thead>
								<tr>
									<td>№</td>
									<td><fmt:message key="list_orders_jsp.table.client" /></td>
									<td><fmt:message key="list_orders_jsp.table.price" /></td>
									<td><fmt:message key="list_orders_jsp.table.status" /></td>
									<td><fmt:message key="list_orders_jsp.table.service" /></td>
									<td><fmt:message key="user_orders_jsp.table.pay" /></td>
								</tr>
							</thead>


							<c:forEach var="bean" items="${userOrderBeanListforUser}">

								<tr>
									<td>${bean.id}</td>
									<td>${bean.userFirstName}${bean.userLastName}</td>
									<td>${bean.orderPrice}</td>
									<td>${bean.statusName}</td>
									<td>${bean.serviceName}</td>
									<td><c:if test="${bean.statusName == 'unpaid'}">
											<a
												href="controller?command=payAnOrder&id=${bean.id}&orderPrice=${bean.orderPrice}&serviceId=${bean.serviceId}"><fmt:message
													key="user_orders_jsp.table.pay" /></a>
										</c:if></td>
								</tr>

							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>