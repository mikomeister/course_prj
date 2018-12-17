package ua.khai.kharkov.provider.db.entity;

public final class NumberUserServices {
	private long service_id;
	private long order_id;
	private int status_id;

	public long getService_id() {
		return service_id;
	}

	public void setService_id(long service_id) {
		this.service_id = service_id;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	@Override
	public String toString() {
		return "NumberUserServices [orderId=" + order_id + ", service_id=" + service_id + ", status_id=" + status_id + "]";
	}
}
