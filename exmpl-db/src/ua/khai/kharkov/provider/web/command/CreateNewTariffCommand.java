package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.exception.AppException;

public class CreateNewTariffCommand extends Command {

	private static final long serialVersionUID = -2605959070318359543L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String category_id = request.getParameter("category_id");
		LOG.trace("Request parameter: category_id --> " + category_id);

		String name = request.getParameter("name");
		LOG.trace("Request parameter: name --> " + name);
		
		String price = request.getParameter("price");
		LOG.trace("Request parameter: price --> " + price);
		
		DBManager.getInstance().insertTariff(Integer.parseInt(category_id),Integer.parseInt(price),name);
		LOG.trace("Inserting an tariff into the table services in DB: order_id");

		String forward = Path.COMMAND_LIST_SERVICES;

		return forward;

	}
}
