package accessdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class DAO {
	static Connection connection = AccessDAOConnection.getConnection();

	public static ResultSet getAllUser() throws SQLException {
		String sql = "select * from user";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		return rs;
	}

	public static ResultSet getAllUserSort() throws SQLException {
		String sql = "select * from user order by ID";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		return rs;
	}

	public static void insertUser(User user) throws SQLException {
		String sql = "insert into user(name,user,pass) values(?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, user.getTen());
		preparedStatement.setString(2, user.getUser());
		preparedStatement.setString(3, user.getPass());
		preparedStatement.executeUpdate();
	}

	public static void deleteUser(int id) throws SQLException {
		String sql = "delete from user where ID=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}
	
	public static void deleteAll() throws SQLException {
		String sql = "delete * user";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
	}

	public static void main(String[] args) throws SQLException {
		ResultSet rs = getAllUser();
		while (rs.next()) {
			System.out.println(rs.getString("user"));
		}
	}

}
