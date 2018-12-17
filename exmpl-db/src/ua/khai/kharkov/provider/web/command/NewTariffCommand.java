package ua.khai.kharkov.provider.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.entity.Category;
import ua.khai.kharkov.provider.exception.AppException;

public class NewTariffCommand extends Command {

	private static final long serialVersionUID = -2028583765317332688L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		LOG.trace("Request parameter: Id --> " + id);
		List<Category> category = DBManager.getInstance().findCategories();
		for (Category cat : category) {
			if (cat.getId()==Integer.parseInt(id)) {
				session.setAttribute("newtariff", cat);
			}
		}
		String forward = Path.PAGE_NEW_TARIFF;

		return forward;

	}
}
