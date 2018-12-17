package ua.khai.kharkov.provider.db.bean;

import ua.khai.kharkov.provider.db.entity.Entity;

public class UserOrderBean extends Entity {

	private static final long serialVersionUID = -5654982557199337483L;

	private long orderId;

	private String userFirstName;

	private String userLastName;

	private int orderPrice;

	private String statusName;

	private String serviceName;

	private long serviceId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "OrderUserBean [orderId=" + orderId + ", userFirstName=" + userFirstName + ", userLastName="
				+ userLastName + ", orderPrice=" + orderPrice + ", serviceName=" + serviceName + ", serviceId="
				+ serviceId + ", statusName=" + statusName + "]";
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

}
