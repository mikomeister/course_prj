<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="New Tariff" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">


		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">

				<form id="login_form" action="controller" method="post">

					<input type="hidden" name="command" value="createNewTariff" />
					<p>${newtariff.name}</p>
					<fieldset>
						<legend>
							<fmt:message key="link_services_jsp.link.name" />
						</legend>
						<input name="name" /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend>
							<fmt:message key="link_services_jsp.link.price" />
						</legend>
						<input name="price" /><br />
					</fieldset>
					<br /> <input name="category_id" type="hidden"
						value="${newtariff.id}" /><br /> <input type="submit"
						value="<fmt:message key="new_teriff_jsp.input.create"/>">
				</form> <%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>