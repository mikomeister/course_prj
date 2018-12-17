package ua.khai.kharkov.provider.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.db.bean.UserOrderBean;
import ua.khai.kharkov.provider.db.entity.Category;
import ua.khai.kharkov.provider.db.entity.NumberUserServices;
import ua.khai.kharkov.provider.db.entity.Services;
import ua.khai.kharkov.provider.db.entity.User;
import ua.khai.kharkov.provider.exception.DBException;
import ua.khai.kharkov.provider.exception.Messages;

public class DBManager {
	private static final Logger LOG = Logger.getLogger(DBManager.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			// provider - the name of data source
			ds = (DataSource) envContext.lookup("jdbc/provider");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DataSource ds;

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	private static final String SQL_FIND_ORDERS = "SELECT * FROM orders_services";
	private static final String SQL_FIND_ALL_SERVICES = "SELECT * FROM services";
	private static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM categories";
	private static final String SQL_GET_USER_ORDER_BEANS = "SELECT os.order_id, u.first_name, u.last_name, u.id, s.price, s.name, s.service_id,st.status"
			+ " FROM users u, orders_services os,orders o, services s,statuses st"
			+ " WHERE os.order_id=o.id AND os.service_id=s.service_id AND os.status_id=st.id AND o.user_id=u.id;";
	private static final String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?)";
	private static final String SQL_INSERT_ORDERS_SERVICES = "INSERT INTO orders_services VALUES (?, ?, DEFAULT)";
	private static final String SQL_FIND_SERVICE_BY_ID = "SELECT * FROM services WHERE service_id=?";
	private static final String SQL_UPDATE_TARIFF = "UPDATE services SET price=?, name=?" + "	WHERE service_id=?";
	private static final String SQL_DELETE_TARIFF = "DELETE FROM services WHERE service_id = ? LIMIT 1";
	private static final String SQL_INSERT_TARIFF = "INSERT INTO services VALUES(DEFAULT, ?, ?, ?);";
	private static final String SQL_INSERT_USER = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, DEFAULT,DEFAULT,?);";
	private static final String SQL_FIND_USER_STATUS_BY_ID = "SELECT * FROM user_statuses WHERE id=?";
	private static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET user_status=?" + "	WHERE id=?";
	private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?"
			+ "	WHERE id=?";
	private static final String SQL_UPDATE_USER_CASH = "UPDATE users SET cash=?	WHERE id=?";
	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders_services SET status_id=? WHERE order_id=? AND service_id=?";

	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	public List<Services> findServices() throws DBException {
		List<Services> servicesList = new ArrayList<Services>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_SERVICES);
			while (rs.next()) {
				servicesList.add(extractServices(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return servicesList;
	}

	public List<NumberUserServices> findOrders() throws DBException {
		List<NumberUserServices> nus = new ArrayList<NumberUserServices>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ORDERS);
			while (rs.next()) {
				nus.add(extractOrders(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return nus;
	}

	public Services findService(int id) throws DBException {
		Services service = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_SERVICE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				service = extractServices(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_SERVICE_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICE_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return service;
	}

	public List<Category> findCategories() throws DBException {
		List<Category> categoriesList = new ArrayList<Category>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_CATEGORIES);
			while (rs.next()) {
				categoriesList.add(extractCategory(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return categoriesList;
	}

	public List<UserOrderBean> getUserOrderBeans() throws DBException {
		List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_ORDER_BEANS);
			while (rs.next()) {
				orderUserBeanList.add(extractUserOrderBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return orderUserBeanList;
	}

	public List<UserOrderBean> getUserOrderBeans(User user) throws DBException {
		List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_ORDER_BEANS);
			while (rs.next()) {
				UserOrderBean bean = extractUserOrderBean(rs, user);
				if (bean != null) {
					orderUserBeanList.add(bean);
				} else {
					continue;
				}
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return orderUserBeanList;
	}

	public int insertOrder(User user) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		int key = 0;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, user.getId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					key = rs.getInt(1);
				}
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_ORDER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return key;
	}

	public int insertOrdersSevices(long order_id, long service_id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		int key = 0;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ORDERS_SERVICES);
			pstmt.setLong(2, order_id);
			pstmt.setLong(1, service_id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_ORDERS_SERVICES, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_ORDERS_SERVICES, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return key;
	}

	public void deleteService(int id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_TARIFF);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_TARIFF, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	public void updateTariff(String name, int price, int id) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateTariff(con, name, price, id);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TARIFF, ex);
		} finally {
			close(con);
		}
	}

	private void updateTariff(Connection con, String name, int price, int id) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_TARIFF);
			pstmt.setInt(3, id);
			pstmt.setInt(1, price);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	public void updateUserStatus(int status, long user_id) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUserStatus(con, status, user_id);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TARIFF, ex);
		} finally {
			close(con);
		}
	}

	private void updateUserStatus(Connection con, int status, long user_id) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER_STATUS);
			pstmt.setInt(1, status);
			pstmt.setLong(2, user_id);
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	public void insertTariff(int category_id, int price, String name) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_TARIFF);
			pstmt.setString(1, name);
			pstmt.setInt(2, price);
			pstmt.setInt(3, category_id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_TARIFF, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	public void insertUser(String login, String password, String first_name, String last_name, int role_id)
			throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_USER);
			int k = 0;
			pstmt.setString(++k, login);
			pstmt.setString(++k, password);
			pstmt.setString(++k, first_name);
			pstmt.setString(++k, last_name);
			pstmt.setInt(++k, role_id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	public String findUserStatusById(int id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		String status = "";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_STATUS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			status = rs.getString("user_status");
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return status;
	}

	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setLong(k, user.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	public void updateUserCash(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUserCash(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	private void updateUserCash(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER_CASH);
			pstmt.setInt(1, user.getCash());
			pstmt.setLong(2, user.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	public void updateOrderStatus(int id, int service_id, int order_status) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateOrderStatus(con, id, service_id, order_status);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	private void updateOrderStatus(Connection con, int id, int service_id, int order_status) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS);
			pstmt.setInt(1, order_status);
			pstmt.setInt(2, id);
			pstmt.setInt(3, service_id);
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		user.setCash(rs.getInt(Fields.USER_CASH));
		user.setUserStatus(rs.getInt(Fields.USER_STATUS));
		return user;
	}

	private Services extractServices(ResultSet rs) throws SQLException {
		Services service = new Services();
		service.setId(rs.getLong(Fields.SERVICE_ID));
		service.setName(rs.getString(Fields.SERVICE_NAME));
		service.setPrice(rs.getInt(Fields.SERVICE_PRICE));
		service.setCategoryId(rs.getLong(Fields.SERVICE_CATEGORY_ID));
		return service;
	}

	private Category extractCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getLong(Fields.ENTITY_ID));
		category.setName(rs.getString(Fields.CATEGORY_NAME));
		return category;
	}

	private UserOrderBean extractUserOrderBean(ResultSet rs) throws SQLException {
		UserOrderBean bean = new UserOrderBean();
		bean.setId(rs.getLong(Fields.USER_ORDER_BEAN_ORDER_ID));
		bean.setOrderPrice(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_PRICE));
		bean.setUserFirstName(rs.getString(Fields.USER_ORDER_BEAN_USER_FIRST_NAME));
		bean.setUserLastName(rs.getString(Fields.USER_ORDER_BEAN_USER_LAST_NAME));
		bean.setStatusName(rs.getString(Fields.USER_ORDER_BEAN_STATUS_NAME));
		bean.setServiceName(rs.getString(Fields.USER_ORDER_BEAN_SERVICE_NAME));
		return bean;
	}

	private UserOrderBean extractUserOrderBean(ResultSet rs, User user) throws SQLException {
		UserOrderBean bean = null;
		if (rs.getLong(Fields.ENTITY_ID) == user.getId()) {
			bean = new UserOrderBean();
			bean.setId(rs.getLong(Fields.USER_ORDER_BEAN_ORDER_ID));
			bean.setOrderPrice(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_PRICE));
			bean.setUserFirstName(rs.getString(Fields.USER_ORDER_BEAN_USER_FIRST_NAME));
			bean.setUserLastName(rs.getString(Fields.USER_ORDER_BEAN_USER_LAST_NAME));
			bean.setStatusName(rs.getString(Fields.USER_ORDER_BEAN_STATUS_NAME));
			bean.setServiceName(rs.getString(Fields.USER_ORDER_BEAN_SERVICE_NAME));
			bean.setServiceId(rs.getLong(Fields.SERVICE_ID));
		}
		return bean;
	}
	
	private NumberUserServices extractOrders(ResultSet rs) throws SQLException {
		NumberUserServices uos = new NumberUserServices();
			uos.setService_id(rs.getLong("service_id"));
			uos.setOrder_id(rs.getLong("order_id"));
			uos.setStatus_id(rs.getInt("status_id"));
		return uos;
	}
}
