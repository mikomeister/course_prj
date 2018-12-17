package ua.khai.kharkov.provider.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.Role;
import ua.khai.kharkov.provider.db.entity.Category;
import ua.khai.kharkov.provider.db.entity.NumberUserServices;
import ua.khai.kharkov.provider.db.entity.NumberUsers;
import ua.khai.kharkov.provider.db.entity.Services;
import ua.khai.kharkov.provider.exception.AppException;

public class ListServicesCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(ListServicesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		Role userRole = (Role) session.getAttribute("userRole");
		// get services list
		List<Services> services = DBManager.getInstance().findServices();
		LOG.trace("Found in DB: servicesList --> " + services);

		// sort menu by category
		Collections.sort(services, new Comparator<Services>() {
			public int compare(Services o1, Services o2) {
				return (int) (o1.getPrice() - o2.getPrice());
			}
		});

		// get categories list
		List<Category> categories = DBManager.getInstance().findCategories();
		LOG.trace("Found in DB: categories --> " + categories);

		// put menu items list to the request
		request.setAttribute("services", services);
		LOG.trace("Set the request attribute: services --> " + services);

		request.setAttribute("categories", categories);
		LOG.trace("Set the request attribute: categories --> " + categories);

		LOG.debug("Command finished");

		if (userRole == Role.ADMIN) {
			List<NumberUsers> numberusers = new ArrayList<NumberUsers>();
			for (Services ser : services) {
				NumberUsers numb = new NumberUsers();
				numb.setService_id(ser.getId());
				numberusers.add(numb);
			}
			List<NumberUserServices> nus = DBManager.getInstance().findOrders();
			LOG.trace("Found in DB: orders --> " + nus);
			for (NumberUsers numb : numberusers) {
				long id = numb.getService_id();
				int k = 0;
				for (NumberUserServices orders : nus) {
					if (orders.getService_id() == id & orders.getStatus_id() == 1) {
						numb.setNumber_users(++k);
					}
				}
			}
			request.setAttribute("numberusers", numberusers);
			LOG.trace("Set the request attribute: numberusers --> " + numberusers);
			return Path.PAGE_TARIFF_PLANS;

		}

		return Path.PAGE_LIST_SERVICES;
	}

}
