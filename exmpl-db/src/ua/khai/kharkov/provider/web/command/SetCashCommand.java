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

public class SetCashCommand extends Command {

	private static final long serialVersionUID = 9012690382572899424L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.trace("User: --> " + user);

		int cash = Integer.parseInt(request.getParameter("cash"));
		LOG.trace("Request parameter: cash --> " + cash);

		String forward = Path.PAGE_DEPOSIT;

		user.setCash(user.getCash() + cash);

		DBManager manager = DBManager.getInstance();
		manager.updateUserCash(user);

		return forward;
	}

}
