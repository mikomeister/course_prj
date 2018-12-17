<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf" %>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<form id="settings_form" action="controller" method="post">
					<input type="hidden" name="command" value="setCash" />
					
					<div>
						<p><fmt:message key="deposit_jsp.link.cash"/></p>
						<input name="cash">
					</div>
					
					<input type="submit" value="<fmt:message key="deposit_jsp.link.update"/>"><br/>
				</form> 
				<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>