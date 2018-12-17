<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Users" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<p><fmt:message key="users_jsp.text.registration_new_user" /></p>
				<form action="controller?command=registrationUserCommand"
					method="post">
					<table>
						<tr>
							<td><fmt:message key="users_jsp.table.login" /></td>
							<td><fmt:message key="settings_jsp.input.password" /></td>
							<td><fmt:message key="settings_jsp.input.first_name" /></td>
							<td><fmt:message key="settings_jsp.input.last_name" /></td>
							<td><fmt:message key="users_jsp.table.role" /></td>
						</tr>
						<tr>
							<td><input name="login" /></td>
							<td><input type="password" name="password" /></td>
							<td><input name="first_name" /></td>
							<td><input name="last_name" /></td>
							<td><select name="role_id">
									<option value="0">admin</option>
									<option value="1">user</option>
							</select></td>
						</tr>
					</table>
					<input type="submit"
						value="<fmt:message key="users_jsp.input.registration"/>">
				</form>


				<form action="controller?command=findUserByLogin" method="post">
					<table>
						<tr>
							<td><fmt:message key="users_jsp.table.find_user" /></td>
						</tr>
						<tr>
							<td><input name="login" /><br></td>
						</tr>
						<tr>
							<td><input type="submit"
								value="<fmt:message key="users_jsp.input.find"/>"></td>
						</tr>
					</table>
				</form> <c:if test="${not empty findUser}">
					<table>
						<tr>
							<td>Id</td>
							<td><fmt:message key="users_jsp.table.login" /></td>
							<td><fmt:message key="settings_jsp.input.first_name" /></td>
							<td><fmt:message key="settings_jsp.input.last_name" /></td>
							<td><fmt:message key="users_jsp.table.role" /></td>
							<td><fmt:message key="list_orders_jsp.table.status" /></td>
						</tr>
						<tr>
							<td>${findUser.id}</td>
							<td>${findUser.login}</td>
							<td>${findUser.firstName}</td>
							<td>${findUser.lastName}</td>
							<td>${findUser.roleId}</td>
							<td>${userStatus}</td>
						</tr>
					</table>
					<br>
					<form action="controller" method="get">
						<input name="command" type="hidden" value="editUserStatus" /><br />
						<c:choose>
							<c:when test="${findUser.userStatus == 0 }">
								<button name="status" value="1">
									<fmt:message key="users_jsp.button.lock" />
								</button>
							</c:when>
							<c:when test="${findUser.userStatus == 1}">
								<button name="status" value="0">
									<fmt:message key="users_jsp.button.unlock" />
								</button>
							</c:when>
						</c:choose>
					</form>
				</c:if>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>