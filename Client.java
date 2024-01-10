import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
	JFrame jFrame = new JFrame("Chat Application");
	JTextField sendMessageTextField = new JTextField(40);
	JTextArea messagesTextArea = new JTextArea(8, 40);
	BufferedReader bufferedReader;
	PrintWriter pWriter;
	
	
	public Client() {
		sendMessageTextField.setEditable(false);
		messagesTextArea.setEditable(false);
		jFrame.getContentPane().add(sendMessageTextField, "North");
		jFrame.getContentPane().add(messagesTextArea, "Center");
		jFrame.pack();
		
		sendMessageTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pWriter.println(sendMessageTextField.getText());
				sendMessageTextField.setText("");
			}
		});
	}
	
	public ActionListener sendMessage() {
		pWriter.println(sendMessageTextField.getText());
		sendMessageTextField.setText("");
		return null;
	}
	
	public void connect() {
		String serverAddress = JOptionPane.showInputDialog(jFrame, "Enter the server IP: ", 
				"Connect to the server", JOptionPane.QUESTION_MESSAGE);
		try {
			Socket socket = new Socket(serverAddress, 4444);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pWriter = new PrintWriter(socket.getOutputStream(), true);
			
			while (true) {
				String line = bufferedReader.readLine();
				if(line.startsWith("SUBMITNAME")) {
					String name = JOptionPane.showInputDialog(jFrame, "Enter your name: ",
							"Name Selection",JOptionPane.PLAIN_MESSAGE
							);
					pWriter.println(name);
				} else if (line.startsWith("NAMEACCEPTED")) {
					sendMessageTextField.setEditable(true);
				} else if(line.startsWith("MESSAGE")){
					messagesTextArea.append(line.substring(7) + "\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.jFrame.setVisible(true);
		client.connect();
	}

}
