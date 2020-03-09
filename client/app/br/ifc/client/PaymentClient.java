package br.ifc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class PaymentClient {

	HashMap<Integer, String> payments = new HashMap<Integer, String>();
	Socket socketClient;

	public static void main(String[] args) throws IOException {
		new PaymentClient().startClient();
	}

	public void startClient() throws IOException {

		try {

			System.out.println("Tentando conectar ao servidor...");
			socketClient = new Socket("localhost", 9000);
			socketClient.setSoTimeout(7000);
			payments.put(0, "Teste;5376168351501945;589;07/07/2021;3;545");
			payments.put(1, "Teste;5376168351501945;589;07/07/2021;3;545");

			System.out.println("Conectado ao servidor.");

			BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			PrintStream out = new PrintStream(socketClient.getOutputStream());

			System.out.println("REQ: Requisitando servi�o de pagamento ao servidor...");

			out.println("pagamento");
			out.println(String.valueOf(payments.size()));

			payments.keySet().forEach(i -> {
				try {
					processPayment(i, out, in);
				} catch (SocketTimeoutException e2) {
					
					System.out.println("TA: Servidor parece ocupado. Tente novamente mais tarde.");
					System.exit(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			socketClient.close();
			System.out.println("-> Finalizando socket do cliente.");
		}

	}

	public void processPayment(Integer i, PrintStream out, BufferedReader in) throws IOException {
		
		
		
		System.out.println("AYA: Enviando requisi��o AYA ao servidor.");
		out.println("AYA");
		String status = in.readLine();
		if (status.equals("IAA")) {
			System.out.println("IAA: Servidor est� vivo. Enviando dados do cliente.");
			out.println(payments.get(i));
		}
	

		String paymentStatus = null;
		paymentStatus = in.readLine();


		if (paymentStatus.equals("OK")) {
			System.out.println("REP: Seu pagamento foi processado com sucesso.");
			System.out.println("ACK: Enviando confirma��o ao servidor.");
			out.println("ACK");
			System.out.println("ACK: Confirma��o enviada.");
		} else {
			System.out.println(paymentStatus);
		}

	}

}
