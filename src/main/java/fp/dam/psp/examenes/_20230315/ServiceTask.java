package fp.dam.psp.examenes._20230315;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
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
			socket.setSoTimeout(2000);
			String linea;
			
			try {
				while (true) {
					linea = in.readUTF();
					
					if (linea.matches("hash")) {
						
					} else if (linea.matches("cert")) {
						
					} else if (linea.matches("cifrar")) {
						
					} else {
						out.writeUTF("ERROR:'abcd' no se reconoce como una petición válida");
						out.flush();
					}
				}
			} catch (SocketTimeoutException e) {
				out.writeUTF("ERROR:timeout leyendo petición");
				out.flush();
			} catch (EOFException e) {
				out.writeUTF("ERROR:EOF leyendo petición");
				out.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String hashMethod(String recibo) {
		for (String s : new String[] { "SHA-256", "MD5", "SHA3-512" })
			if (recibo.matches(s)) {
				return "true";
			}
		return "false";
	}
}
