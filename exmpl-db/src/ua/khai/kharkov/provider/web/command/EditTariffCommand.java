package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.entity.Services;
import ua.khai.kharkov.provider.exception.AppException;

public class EditTariffCommand extends Command {

	private static final long serialVersionUID = -5842308127752277580L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		LOG.trace("Request parameter: Id --> " + id);
		Services service = DBManager.getInstance().findService(Integer.parseInt(id));
		session.setAttribute("service", service);
		String forward = Path.PAGE_EDIT_SERVICE;

		return forward;
	}

}
