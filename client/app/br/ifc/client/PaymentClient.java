package br.ifc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

public class PaymentClient {

	HashMap<Integer, String> payments = new HashMap<Integer, String>();
	Socket socketClient;

	public static void main(String[] args) throws IOException {
		new PaymentClient().startClient();
	}

	public void startClient() throws IOException {
		

		System.out.println("Tentando conectar ao servidor...");
		socketClient = new Socket("localhost", 9000);
		payments.put(0, "Teste;5376168351501945;589;07/07/2021;3;545");
		payments.put(1, "Teste;5376168351501945;589;07/07/2021;3;545");
		payments.put(2, "Teste;5376168351501945;589;07/07/2021;3;545");
		payments.put(4, "Teste;5376168351501945;589;07/07/2021;3;545");

		System.out.println("Conectado ao servidor.");

		BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		PrintStream out = new PrintStream(socketClient.getOutputStream());

		System.out.println("REQ: Requisitando serviço de pagamento ao servidor...");

		out.println("pagamento");
		out.println(String.valueOf(payments.size()));

		payments.keySet().forEach(i -> {
			processPayment(i, out, in);
		});

	}

	public void processPayment(Integer i, PrintStream out, BufferedReader in)  {

		out.println(payments.get(i));

		String paymentStatus = null;
		
		try {
			paymentStatus = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (paymentStatus.equals("OK")) {
			System.out.println("REQ: Seu pagamento foi processado com sucesso.");
			System.out.println("ACK: Enviando confirmação ao servidor.");
			out.println("ACK");
			System.out.println("ACK: Confirmação enviada.");
		} else {
			System.out.println(paymentStatus);
		}

	}

}
