package fp.dam.psp.examenes._20230315;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class ServiceTask {

	private final Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	public ServiceTask(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try (socket){
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			String linea;
			while ((linea = in.readUTF()) !=null) {
				if (linea.matches(Integer.toString(200))) {
					String val1 = hashMethod(in.readUTF());
					String val2 = in.readUTF();
					if (Boolean.parseBoolean(val2))
						out.writeUTF("203");
				} else if (linea.matches(Integer.toString(300))) {
					
				} else if (linea.matches(Integer.toString(400))) {
					
				} else {
					if (linea.isEmpty()) {
						out.writeUTF("10002");
					} else {
						out.writeUTF("10003");
					}
				}
				out.writeChars(linea);;
				out.flush();
			}
			
		} catch (SocketTimeoutException e) {
			out.writeUTF("ERROR:timeout leyendo petición");
		} 
		System.out.println(socket.getRemoteSocketAddress() + ": Conexión terminada");
	}
	
	private String hashMethod(String recibo) {
		for (String s: new String []{"SHA-256", "MD5", "SHA3-512"})
			if (recibo.matches(s)) {
				return "true";
			}
		return "false";
	}
}
