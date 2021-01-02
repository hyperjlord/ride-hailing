package top.wx.pojo;

public class Passenger {
	private String user_id;
	private String password;
	private String name;
	private String sex;
	private Double balance;
	private String upic_url;

	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) { this.user_id = user_id; }

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getBalance() { return balance; }
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getPicUrl() {
		return upic_url;
	}
	public void setPicUrl(String upic_url) { this.upic_url = upic_url; }
}
