package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class process the request of each client as a separated Thread.
 */
public class EchoService extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket tcpSocket;
	private ArrayList<Usuario> usuarios;
	public EchoService(Socket socket) {
		try {
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.start();
		} catch (Exception e) {
			System.out.println("# EchoService - TCPConnection IO error:" + e.getMessage());
		}
		Usuario user0= new Usuario();
		Usuario user1= new Usuario();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		user0.setContrasenya("$!9PhNz,");
		user0.setEmail("thomas.e2001@gmail.com");
		user0.setFecha_ncto(new Date(2001,11,12));
		user0.setFrecuencia("100");
		user0.setName("Thomas");
		user0.setPeso(100.0);
		user0.setSesiones(null);
		usuarios.add(user0);
		
//		user1.setContrasenya(getName());
//		user1.setEmail(getName());
//		user1.setFecha_ncto(null);
//		user1.setFrecuencia(getName());
//		user1.setName(getName());
//		user1.setPeso(null);
//		user1.setSesiones(null);
		
	}

	public void run() {
		//Echo server
		try {
			//Read request from the client
			String data = this.in.readUTF();			
			System.out.println("   - EchoService - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");		
			String si= "Si esta";
			String no= "No esta";
			//Send response to the client
			if (data.contains("thomas.e2001@gmail.com") && data.contains("$!9PhNz,")){
				this.out.writeUTF(si);			
				System.out.println("   - EchoService - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + si + "'");

			}else {
				this.out.writeUTF(no);			
				System.out.println("   - EchoService - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + no + "'");

			}
		} catch (Exception e) {
			System.out.println("   # EchoService error" + e.getMessage());
		} finally {
			try {
				tcpSocket.close();
			} catch (Exception e) {
				System.out.println("   # EchoService error:" + e.getMessage());
			}
		}
	}
}