<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<form id="settings_form" action="controller" method="post">
					<input type="hidden" name="command" value="updateSettings" />

					<div>
						<p><fmt:message key="settings_jsp.input.first_name"/></p>
						<input name="firstName">
					</div>

					<div>
						<p><fmt:message key="settings_jsp.input.last_name"/></p>
						<input name="lastName">
					</div>

					<div>
						<p><fmt:message key="settings_jsp.input.password"/></p>
						<input name="password" type="password">
					</div>

					<input type="submit" value="<fmt:message key='settings_jsp.form.update'/>"><br />
				</form> <a href="controller?command=updateCash"><fmt:message key="settings_jsp.link.make_a_deposit"/></a> <%-- CONTENT --%>
				<br><br>
				<form action="changeLocale.jsp" method="post">
					<fmt:message key="settings_jsp.label.set_locale" />
					: <select name="locale">
						<c:forEach items="${applicationScope.locales}" var="locale">
							<c:set var="selected"
								value="${locale.key == currentLocale ? 'selected' : '' }" />
							<option value="${locale.key}" ${selected}>${locale.value}</option>
						</c:forEach>
					</select> <input type="submit"
						value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">

				</form>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>