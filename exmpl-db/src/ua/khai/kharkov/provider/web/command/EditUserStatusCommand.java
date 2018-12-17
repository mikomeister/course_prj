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

public class EditUserStatusCommand extends Command {

	private static final long serialVersionUID = -6959822682242216858L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String status = request.getParameter("status");
		LOG.trace("Request parameter: status --> " + status);
		HttpSession session = request.getSession();
		User findUser = (User)session.getAttribute("findUser");
		long user_id = findUser.getId();
		DBManager.getInstance().updateUserStatus(Integer.parseInt(status),user_id);
		String forward = Path.COMMAND_FIND_USER_BY_LOGIN+"&login="+findUser.getLogin();
		return forward;
	}

}
