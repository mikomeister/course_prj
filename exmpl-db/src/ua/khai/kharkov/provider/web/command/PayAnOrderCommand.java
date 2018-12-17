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
import ua.khai.kharkov.provider.exception.Messages;

public class PayAnOrderCommand extends Command {

	private static final long serialVersionUID = -8563787515892888921L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int cash = user.getCash();
		LOG.trace("User: cash --> " + cash);
		String id = request.getParameter("id");
		LOG.trace("Request parameter: id --> " + id);
		String serviceId = request.getParameter("serviceId");
		LOG.trace("Request parameter: serviceId --> " + serviceId);
		
		int orderPrice = Integer.parseInt(request.getParameter("orderPrice"));
		LOG.trace("Request parameter: orderPrice --> " + orderPrice);
		String forward = Path.COMMAND_SHOW_USER_ORDERS;
		if (orderPrice>cash) {
			request.setAttribute("errorMessage", Messages.ERR_THERE_IS_NOT_ENOUGH_MONEY);
			forward = Path.PAGE_ERROR_PAGE;
		} else {
			cash = cash - orderPrice;
			user.setCash(cash);
			DBManager manager = DBManager.getInstance();
			manager.updateUserCash(user);
			manager.updateOrderStatus(Integer.parseInt(id), Integer.parseInt(serviceId),1);
		}
		return forward;
	}

}
