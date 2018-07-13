package commandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTelnet {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 12345);
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader readerIn = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

		String netOut = null;
		while (true) {
			while((netOut = reader.readLine())!=null){
			System.out.println(netOut);
			}
			String command = readerIn.readLine();
			
			if(command.equalsIgnoreCase("exit")){
				break;
			}
			System.out.println(command);
			writer.println(command);
		}
	}
}
