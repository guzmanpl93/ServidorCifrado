package fp.dam.psp.examenes._20230315;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(9000)){
			//Este es el HOST, el servidor
			System.out.println("Servidor ECHO escuchando en el puerto 9000");
			
			//Limite de threads
			ExecutorService service = Executors.newFixedThreadPool(20);
			
			//El socket debería estar dentro de un bucle que se pueda detener
			while (true) {
			//Para aceptar conexiones, usar socket.accept()
				Socket socket = serverSocket.accept();
//				socket.setSoTimeout(10000);
			//Podemos utillizar el socket para comunicar con el cliente
				service.submit(new ServiceTask(socket)::run);
			}
		} 
	}
}
