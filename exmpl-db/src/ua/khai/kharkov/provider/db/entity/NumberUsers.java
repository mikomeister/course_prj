package ua.khai.kharkov.provider.db.entity;

public final class NumberUsers {

	private long service_id;
	private int number_users;

	public long getService_id() {
		return service_id;
	}

	public void setService_id(long service_id) {
		this.service_id = service_id;
	}

	@Override
	public String toString() {
		return "NumberUsers [service_id=" + service_id + ", number_users=" + number_users + "]";
	}

	public int getNumber_users() {
		return number_users;
	}

	public void setNumber_users(int number_users) {
		this.number_users = number_users;
	}
}
