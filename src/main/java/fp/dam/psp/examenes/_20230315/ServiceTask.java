package fp.dam.psp.examenes._20230315;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ServiceTask {

	private final Socket socket;
	
	public ServiceTask(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try (socket){
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			String linea;
			while ((linea = in.readLine()) !=null) {
				if (linea.matches(Integer.toString(200))) {
					hashMethod(in.readLine());
				} else if (linea.matches(Integer.toString(300))) {
					
				} else if (linea.matches(Integer.toString(400))) {
					
				} else {
					if (linea.isEmpty()) {
						out.println(10002);
					} else {
						out.println(10003);
					}
				}
				out.println(linea);
				out.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getRemoteSocketAddress() + ": Conexión terminada");
	}
	
	private String hashMethod(String recibo) {
		for (String s: new String []{"SHA-256", "MD5", "SHA3-512"})
			if (recibo.matches(s)) {
				return s;
			}
		return "203";
	}
}
