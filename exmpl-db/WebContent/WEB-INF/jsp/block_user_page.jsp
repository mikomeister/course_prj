<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

	<table id="main-container">

		<tr >
			<td class="content">
			<%-- CONTENT --%>
				
				<h2 class="error">
					You are blocked by an administrator.
					Contacts: admin@provider.kharkov.nure.ua
				</h2>
			
		
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>