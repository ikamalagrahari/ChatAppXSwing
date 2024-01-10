import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable{
	Socket clientSocket;
	BufferedReader bReader;
	PrintWriter pWriter;
	Server server;
	
	public ClientHandler(Socket clientSocket, Server server) {
		this.clientSocket = clientSocket;
		this.server = server;
		try {
			this.bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.pWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		String name = "";
		while(true) {
			pWriter.println("SUBMITNAME");
			try {
				name = bReader.readLine();
				if(name == null) return;
				if(!name.isEmpty()) break;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pWriter.println("NAMEACCEPTED");
		server.broadcast(name + " has connected");
		
		while (true) {
			try {
				String userMessage = bReader.readLine();
				if(userMessage == null) return;
				server.broadcast("MESSAGE" + name + " : " + userMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
