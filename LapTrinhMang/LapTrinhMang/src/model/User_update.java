package model;

public class User {
	private int id;
	private String ten;
	private String user;
	private String pass;

	public User() {
	}

	public User(int id, String ten, String user, String pass) {
		super();
		this.id = id;
		this.ten = ten;
		this.user = user;
		this.pass=pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
