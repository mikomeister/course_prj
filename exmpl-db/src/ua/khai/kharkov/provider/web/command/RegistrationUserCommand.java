package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.MD5Util;
import ua.khai.kharkov.provider.exception.AppException;

public class RegistrationUserCommand extends Command {

	
	private static final long serialVersionUID = 8589551093603388592L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		
		String password = request.getParameter("password");
		password = MD5Util.hash(password,"MD5");
		LOG.trace("Password after md5: password --> " + password);
		
		String first_name = request.getParameter("first_name");
		LOG.trace("Request parameter: first_name --> " + first_name);
		
		String last_name = request.getParameter("last_name");
		LOG.trace("Request parameter: last_name --> " + last_name);
		
		String role_id = request.getParameter("role_id");
		LOG.trace("Request parameter: role_id --> " + role_id);
		
		DBManager.getInstance().insertUser(login,password,first_name,last_name,Integer.parseInt(role_id));
		
		String forward = Path.PAGE_USERS;

		return forward;

	}

}
