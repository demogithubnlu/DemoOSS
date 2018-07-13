package CommandServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import accessdao.DAO;
import model.User;

public class CommandThread extends Thread {
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;

	String command, temp, resquest, respond;
	boolean isLogin = false;

	String username = "";

	String arg1 = "";
	String arg2 = "";

	public CommandThread(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		netOut = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			netOut.println("Wellcom !");
			showHD();

			while (!isLogin) {
				resquest = netIn.readLine();
				AlyCommand();
				if (command.equalsIgnoreCase("exit")) {
					socket.close();
					break;
				}
				switch (command) {
				case "USER":
					ResultSet rs = DAO.getAllUser();
					while (rs.next()) {
						String temp1 = rs.getString("user");
						if (temp.equals(temp1)) {
							username = temp1;
							respond = "user ok !";
							break;
						} else {
							respond = "user fail ! try again";
						}
					}
					break;
				case "PASS":
					ResultSet rs1 = DAO.getAllUser();
					if (!username.equals("")) {
						String userTemp = "";
						String password = "";
						while (rs1.next()) {
							userTemp = rs1.getString("user");
							password = rs1.getString("pass");
							if (password.equals(temp) && userTemp.equals(username)) {
								netOut.println("login successful !");
								isLogin = true;
								showHDCommand();
								break;
							} else {
								respond = "pass fail ! try again";
							}
						}
						break;
					} else {
						respond = "user first !";
						break;
					}
				default:
					respond = "Have No Command !";
					break;
				}
				netOut.println(respond);
				respond="";
			}
		} catch (Exception e) {
			netOut.println("command err !");
		}
		// lenh duoc thuc hien sau khi login thanh cong
		try {
			while (isLogin) {
				resquest = netIn.readLine();
				AlyCommand();
				if (command.equalsIgnoreCase("exit")) {
					socket.close();
					break;
				}
				switch (command) {
				case "VIEW":
					ResultSet rs = DAO.getAllUser();
					netOut.println("--------------------------------------------------");
					while (rs.next()) {
						netOut.println("|" + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | "
								+ rs.getString(4));
					}
					netOut.println("--------------------------------------------------");
					break;
				case "VIEWSORT":
					ResultSet rs1 = DAO.getAllUserSort();
					netOut.println("--------------------------------------------------");
					while (rs1.next()) {
						netOut.println("|" + rs1.getInt(1) + " | " + rs1.getString(2) + " | " + rs1.getString(3) + " | "
								+ rs1.getString(4));
					}
					netOut.println("--------------------------------------------------");
					break;
				case "ADD":
					String nameU = temp;
					String userU = arg1;
					String passU = arg2;
					try {
						if (!nameU.equals("") && !userU.equals("") && !passU.equals("")) {
							User u = new User();
							u.setTen(nameU);
							u.setUser(userU);
							u.setPass(passU);

							DAO.insertUser(u);
							netOut.println("Da them user moi !");
						} else {
							netOut.println("Ban chua nhap day du cac truong cua lenh !");
						}
					} catch (Exception e) {
						netOut.println("Ban can nhap dung cu phap cau lenh \"add\"!");
					}
					break;
				case "DELETE":
					try {
						DAO.deleteUser(Integer.parseInt(temp));
						netOut.println("Da xoa user co ID=" + temp);
					} catch (Exception e) {
						netOut.println("Ban can nhap dung ID cua user !");
					}
					break;
				case "DELETEALL":
					try {
						DAO.deleteAll();
						netOut.println("Da xoa tat ca user !");
					} catch (Exception e) {
						netOut.println("Ban can nhap dung cau lenh !");
					}
					break;

				default:
					netOut.println("No command");
					break;
				}
			}
		} catch (Exception e) {
			netOut.println("Da co loi voi cau lenh !");
		}
	}

	private void AlyCommand() {
		try {
			StringTokenizer tk = new StringTokenizer(resquest, ":");
			command = tk.nextToken().trim().toUpperCase();
			temp = tk.nextToken().trim();
			try {
				arg1 = tk.nextToken().trim();
				arg2 = tk.nextToken().trim();
			} catch (Exception e) {
				arg1 = "";
				arg2 = "";
			}
		} catch (Exception e) {
			command = resquest.trim().toUpperCase();
			temp = "";
		}
	}

	private void showHD() {
		netOut.println();
		netOut.println("__________HUONG DAN SU DUNG____________");
		netOut.println("____CAC LENH KHONG PHAN BIET CHU HOA CHU THUONG ! ____");
		netOut.println("* De thuc hien cac cau lenh thi truoc het ban phai Dang Nhap:");
		netOut.println("* user:username (\"user:\" la lenh, username la ten dang nhap)");
		netOut.println("* pass:password (\"pass:\" la lenh, password la mat khau cua username)");
		netOut.println("* Sau do ban co the thuc hien cac cau lenh !");
		netOut.println("-------------------------------------------------------");
	}

	private void showHDCommand() {
		netOut.println();
		netOut.println("__________CU PHAP CAC CAU LENH____________");
		netOut.println("____CAC LENH KHONG PHAN BIET CHU HOA CHU THUONG ! ____");
		netOut.println("* De xem tat ca cac user dung lenh: view");
		netOut.println("* De xem tat ca cac user sap xep theo ID tang dan: viewsort");
		netOut.println(
				"* Them 1 user moi: add:name:user:pass (\"add:\" la lenh, name la Ho ten cua user, user la ten dang nhap, pass la mat khau dang nhap)");
		netOut.println("* De xoa 1 user: delete:id (\"delete:\" la lenh, id la ID cua tai khoan can xoa)");
		netOut.println("-------------------------------------------------------");
	}
}
