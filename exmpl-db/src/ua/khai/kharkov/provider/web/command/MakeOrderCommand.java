package ua.khai.kharkov.provider.web.command;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.bean.UserOrderBean;
import ua.khai.kharkov.provider.db.entity.User;
import ua.khai.kharkov.provider.exception.AppException;
import ua.khai.kharkov.provider.exception.Messages;

public class MakeOrderCommand extends Command {

	private static final long serialVersionUID = -4591961253109526919L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String itemId[] = request.getParameterValues("itemId");
		request.setAttribute("errorMessage", Messages.ERR_YOU_DID_NOT_SELECT_THE_ORDER);
		String forward = Path.PAGE_ERROR_PAGE;
		boolean flag = true;
		if (Objects.nonNull(itemId)) {
			for (String service_id : itemId) {
				LOG.trace("Request parameter: itemId --> " + service_id);
			}

			List<UserOrderBean> userOrderBeanListforUser = DBManager.getInstance()
					.getUserOrderBeans((User) session.getAttribute("user"));
			LOG.trace("Found in DB: userOrderBeanListforUser --> " + userOrderBeanListforUser);

			for (String service_id : itemId) {
				for (UserOrderBean uob : userOrderBeanListforUser) {
					if (Integer.parseInt(service_id) == uob.getServiceId()) {
						request.setAttribute("errorMessage", Messages.ERR_SORRY_BUT_YOU_ALREADY_MADE_THIS_ORDER);
						forward = Path.PAGE_ERROR_PAGE;
						flag = false;
					}
				}
			}
			if (flag) {
				long order_id = DBManager.getInstance().insertOrder((User) session.getAttribute("user"));
				LOG.trace("Inserting an order into the table orders in DB: order_id --> " + order_id);

				for (String service_id : itemId) {
					DBManager.getInstance().insertOrdersSevices(Long.parseLong(service_id), order_id);
					LOG.trace("Inserting an orders_services into the table orders_services in DB: services --> "
							+ service_id);
				}
				forward = Path.COMMAND_SHOW_USER_ORDERS;
			}
		}
		

		return forward;
	}

}
