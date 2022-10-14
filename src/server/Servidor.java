package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import control.Criptografar;

public class Servidor {
	
	private ServerSocket sckServidor;

	public Servidor() throws IOException {
		this.sckServidor = new ServerSocket(4000);
		
		Criptografar criptografar = new Criptografar();

		for (;;) {
			Socket sckEcho;
			InputStream canalEntrada;
			OutputStream canalSaida;
			BufferedReader entrada;
			PrintWriter saida;

			sckEcho = this.sckServidor.accept();
			canalEntrada = sckEcho.getInputStream();
			canalSaida = sckEcho.getOutputStream();
			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			while (true) {
				String linhaPedido = entrada.readLine();

				if (linhaPedido == null || linhaPedido.length() == 0)
					break;

				String msgCriptografada = linhaPedido;
				
				String[] texto = msgCriptografada.split(":");
				
				// Exibe o texto que chegou Criptografado
				System.out.println(msgCriptografada);
				
				msgCriptografada = texto[0];
				String codigo = texto[1];
				
				String msgDesencriptografada = criptografar.desencriptografar(codigo, msgCriptografada);

				saida.println("Servidor: " + msgDesencriptografada);
			}
			sckEcho.close();
		}
	}
}
