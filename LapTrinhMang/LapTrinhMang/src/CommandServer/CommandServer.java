package CommandServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandServer {
public static void main(String[] args) throws IOException {
	ServerSocket serverSocket=new ServerSocket(12345);
	Socket socket;
	while (true) {
		System.out.println("Server is running...");
		socket=serverSocket.accept();
		CommandThread thread=new CommandThread(socket);
		thread.start();
	}
}
}
