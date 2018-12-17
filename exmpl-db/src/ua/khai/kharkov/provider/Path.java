package ua.khai.kharkov.provider;

public final class Path {
	// pages
		public static final String PAGE_LOGIN = "/login.jsp";
		public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
		public static final String PAGE_LIST_SERVICES = "/WEB-INF/jsp/user/list_services.jsp";
		public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";
		public static final String PAGE_PRIVATE_OFFICE = "/WEB-INF/jsp/admin/private_office.jsp";
		public static final String PAGE_LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
		public static final String PAGE_USER_ORDERS = "/WEB-INF/jsp/user/user_orders.jsp";
		public static final String PAGE_TARIFF_PLANS = "/WEB-INF/jsp/admin/list_services_admin.jsp";
		public static final String PAGE_EDIT_SERVICE = "/WEB-INF/jsp/admin/edit_service.jsp";
		public static final String PAGE_NEW_TARIFF = "/WEB-INF/jsp/admin/new_tariff.jsp";
		public static final String PAGE_USERS = "/WEB-INF/jsp/admin/users.jsp";
		public static final String PAGE_FORM_OF_PAYMENT = "/WEB-INF/jsp/user/form_of_payment.jsp";
		public static final String PAGE_DEPOSIT = "/WEB-INF/jsp/deposit.jsp";
		public static final String PAGE_SERVICES_TXT = "/services.txt";
		public static final String PAGE_SERVICES_PDF = "/services.pdf";
		

		// commands
		public static final String COMMAND_LIST_ORDERS = "/controller?command=listOrders";
		public static final String COMMAND_LIST_SERVICES = "/controller?command=listServices";
		public static final String COMMAND_FIND_USER_BY_LOGIN = "/controller?command=findUserByLogin";
		public static final String COMMAND_SHOW_USER_ORDERS = "/controller?command=showUserOrders";
}
