package ua.khai.kharkov.provider.web.command;

import java.io.IOException;
import java.io.Serializable;
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
import ua.khai.kharkov.provider.db.bean.UserOrderBean;
import ua.khai.kharkov.provider.db.entity.User;
import ua.khai.kharkov.provider.exception.AppException;


public class ShowUserOrdersCommand extends Command {

	private static final long serialVersionUID = 63502215962186541L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	private static class CompareById implements Comparator<UserOrderBean>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(UserOrderBean bean1, UserOrderBean bean2) {
			if (bean1.getId() > bean2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	private static Comparator<UserOrderBean> compareById = new CompareById();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();

		List<UserOrderBean> userOrderBeanListforUser = DBManager.getInstance()
				.getUserOrderBeans((User) session.getAttribute("user"));
		LOG.trace("Found in DB: userOrderBeanListforUser --> " + userOrderBeanListforUser);

		Collections.sort(userOrderBeanListforUser, compareById);

		// put user order beans list to request
		request.setAttribute("userOrderBeanListforUser", userOrderBeanListforUser);
		LOG.trace("Set the request attribute: userOrderBeanListforUser --> " + userOrderBeanListforUser);

		String forward = Path.PAGE_USER_ORDERS;

		return forward;
	}

}
