package br.ifc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class PaymentClient {
	
	public static void main(String[] args) {
		new PaymentClient().startClient();
	}

	public void startClient() {
		System.out.println("Trying to connect to the server...");
		try (Socket socketClient = new Socket("localhost", 3333)) {
			System.out.println("Connected on the server.");

			BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			PrintStream out = new PrintStream(socketClient.getOutputStream());

			System.out.println("Requesting (REQ) payment service to the server...");
			out.println("payment");
			out.println("2");
			out.println("JoseDaSilva;5376168351501945;589;07/07/2021;3;545");
			out.println("MarcioDaSilva;5376168351501945;589;07/07/2021;3;545");

			System.out.println("Server is processing your payments...");

			Integer paymentNumber = 0;
			String paymentStatus = "";

			while (paymentNumber <= 2) {
				paymentStatus = in.readLine();
				paymentNumber = Integer.parseInt(in.readLine());
				if (paymentStatus.equals("complete")) {
					System.out.println("Your payment of number " + (paymentNumber+1) + " has been successfully processed.");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
