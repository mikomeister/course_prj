package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.entity.User;
import ua.khai.kharkov.provider.exception.AppException;

public class FindUserByLoginCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4513124537957577158L;

	private static final Logger LOG = Logger.getLogger(ListOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Commands starts");

		HttpSession session = request.getSession();
		
		DBManager manager = DBManager.getInstance();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		User findUser = manager.findUserByLogin(login);
		LOG.trace("Found in DB: findUser --> " + findUser);
		session.setAttribute("findUser", findUser);
		LOG.trace("Set the session attribute: findUser --> " + findUser);
		if (findUser != null) {
			String status = manager.findUserStatusById(findUser.getUserStatus());
			session.setAttribute("userStatus", status);
			LOG.trace("Set the session attribute: userStatus --> " + status);
		}
		LOG.debug("Commands finished");
		return Path.PAGE_USERS;
	}

}
