package accessdao;

import java.sql.Connection;
import java.sql.DriverManager;

public class AccessDAOConnection {

	public static Connection getConnection(){
		Connection con=null;
		try {
			String driver="sun.jdbc.odbc.JdbcOdbcDriver";
			Class.forName(driver);
			con = DriverManager.getConnection("jdbc:odbc:vm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args){
		if (getConnection() != null) {
			System.out.println("Success");
		} else {
			System.out.println("Fail");
		}
	}
}
