package fp.dam.psp.examenes._20230315;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeoutException;

public class ServiceTask {

	private final Socket socket;
	DataInputStream in;
	DataOutputStream out;

	public ServiceTask(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try (socket) {

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			socket.setSoTimeout(2000);
			String linea;

			try {
				while (true) {
					linea = in.readUTF();
					switch (linea) {
					default:
						out.writeUTF("ERROR:'" + linea + "' no se reconoce como una petición válida");
						out.flush();
						break;
					case "hash":
						try {
							checkerHash();
						} catch (SocketTimeoutException e) {
							out.writeUTF("ERROR:timeout leyendo nombre de algoritmo");
							out.flush();
						} catch (EOFException e) {
							out.writeUTF("ERROR:EOF leyendo nombre de algoritmo");
							out.flush();
						}
						break;
					case "cert":
						try {
							tester();
						} catch (SocketTimeoutException e) {
							out.writeUTF("ERROR:timeout leyendo nombre de algoritmo");
							out.flush();
						} catch (EOFException e) {
							out.writeUTF("ERROR:EOF leyendo nombre de algoritmo");
							out.flush();
						}
						break;
					case "cifrar":
						try {
							tester();
						} catch (SocketTimeoutException e) {
							out.writeUTF("ERROR:timeout leyendo nombre de algoritmo");
							out.flush();
						} catch (EOFException e) {
							out.writeUTF("ERROR:EOF leyendo nombre de algoritmo");
							out.flush();
						}
						break;
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

	private void checkerHash() throws IOException, EOFException{
		try {
			String value = in.readUTF();
			if (hashMethod(value))
				try {
					byte[] content = in.readAllBytes();
					MessageDigest md;
					md = MessageDigest.getInstance(value);
					String result = Base64.getEncoder().encodeToString(md.digest(content));
					out.writeUTF("OK:" + result);
				} catch (SocketTimeoutException e) {
					out.writeUTF("ERROR:timeout leyendo datos");
					out.flush();
				} catch (EOFException e) {
					out.writeUTF("ERROR:EOF leyendo datos");
					out.flush();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
		} catch (SocketTimeoutException e) {
			out.writeUTF("ERROR:timeout leyendo nombre de algoritmo");
			out.flush();
		} catch (EOFException e) {
			out.writeUTF("ERROR:EOF leyendo nombre de algoritmo");
			out.flush();
		}

	}

	private boolean hashMethod(String recibo) throws IOException, SocketTimeoutException {
		for (String s : new String[] { "SHA-256", "MD5", "SHA3-512" })
			if (recibo.matches(s)) {
				return true;
			}
		return false;
	}

	private void tester() throws IOException, EOFException {

	}

}
