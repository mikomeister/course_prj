package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.MD5Util;
import ua.khai.kharkov.provider.db.entity.User;
import ua.khai.kharkov.provider.exception.AppException;

public class UpdateSettingsCommand extends Command {

	private static final long serialVersionUID = -3708100563092694142L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String firstName = request.getParameter("firstName");
		LOG.trace("Request parameter: firstName --> " + firstName);
		String lastName = request.getParameter("lastName");
		LOG.trace("Request parameter: lastName --> " + lastName);
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (password != "") {
			user.setPassword(MD5Util.hash(password,"MD5"));
		}
		if (firstName != "") {
			user.setFirstName(firstName);
		}
		if (lastName != "") {
			user.setLastName(lastName);
		}
		session.setAttribute("user", user);
		DBManager.getInstance().updateUser(user);
		String forward = Path.PAGE_SETTINGS;
		return forward;
	}

}
