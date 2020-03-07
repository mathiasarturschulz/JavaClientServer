package br.ifc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PaymentServer {

	private final Integer PORT = 3333;

	public void startServer() {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("-> Waiting for requests...");
			Socket socketClient = null;
			while (true) {
				socketClient = serverSocket.accept();

				BufferedReader inUser = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				PrintStream outUser = new PrintStream(socketClient.getOutputStream());

				String op = inUser.readLine();

				if (op.equalsIgnoreCase("payment")) {
					executePayment(inUser, outUser);
				}
			}

		} catch (Exception e) {
			System.err.println("Some error has occured. Error message -> " + e.getMessage());
		}
	}

	public void executePayment(BufferedReader inUser, PrintStream outUser) {
		Integer numberOfPayments = 0;
		try {
			numberOfPayments = Integer.parseInt(inUser.readLine());
			System.out.println("-> The number of payments is " + numberOfPayments + ".");
		} catch (Exception e) {
			System.err.println("Some error has occured while trying to read the number of payments. Error message -> "
					+ e.getMessage());
			return;
		}

		for (int i = 0; i < numberOfPayments; i++) {

			try {

				String clientData[] = inUser.readLine().split(";");
				String clientName = clientData[0];

				String clientCardNumber = clientData[1];
				String clientCardValidDate = clientData[2];
				String clientCardSecureCode = clientData[3];
				String clientNumberInstallments = clientData[4];
				String clientPurchaseValue = clientData[5];

				System.out.println("\n");
				System.out.println(".: Payment Number (" + (i + 1) + "):");
				System.out.println(".: Client Name: " + clientName);
				System.out.println(".: Card Number: " + clientCardNumber);
				System.out.println(".: Card Secure Code: " + clientCardSecureCode);
				System.out.println(".: Card Valid Date: " + clientCardValidDate);
				System.out.println(".: Installments: " + clientNumberInstallments);
				System.out.println(".: Purchase value: " + clientPurchaseValue);
				System.out.println("\n");

				System.out.println("-> Processing payment...");

				Thread.sleep(5000);

				System.out.println("-> Payment processed.");
				Thread.sleep(500);
				System.out.println("-> Sending confirmation (ACK) to the client...");
				Thread.sleep(2000);
				outUser.println("complete");
				outUser.println(String.valueOf(i));
				System.out.println("-> Confirmation (ACK) sent.");
				Thread.sleep(500);

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		new PaymentServer().startServer();
	}

}
