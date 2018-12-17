<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Edit Tariff" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

	<table id="main-container">


		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr >
			<td class="content center">

				<form id="login_form" action="controller" method="post">

					<input type="hidden" name="command" value="updateTariff"/>

					<fieldset >
						<legend><fmt:message key="link_services_jsp.link.name"/></legend>
						<input name="name"  value="${service.name}"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend>
							<fmt:message key="link_services_jsp.link.price" />
						</legend>
						<input name="price" value="${service.price}"/><br/>
					</fieldset><br/>
					<input name="id" type="hidden" value="${service.id}"/><br/>
					<input type="submit" value="<fmt:message key="list_services_admin_jsp.table.edit"/>">								
				</form>
				
			<%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>