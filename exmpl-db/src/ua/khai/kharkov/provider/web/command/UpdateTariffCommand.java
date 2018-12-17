package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.exception.AppException;

public class UpdateTariffCommand extends Command {
	
	private static final long serialVersionUID = -992052255256416688L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		String name = request.getParameter("name");
		LOG.trace("Request parameter: name --> " + name);
		String price = request.getParameter("price");
		LOG.trace("Request parameter: price --> " + price);
		String id = request.getParameter("id");
		LOG.trace("Request parameter: id --> " + id);
		DBManager.getInstance().updateTariff(name, Integer.valueOf(price), Integer.parseInt(id));

		String forward = Path.COMMAND_LIST_SERVICES;

		return forward;
	}

}
