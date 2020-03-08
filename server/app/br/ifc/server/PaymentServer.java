package br.ifc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PaymentServer {

	private final Integer PORT = 9000;

	public void startServer() {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			System.out.println("-> Aguardando requisições...");
			Socket socketClient = null;
			while (true) {
				socketClient = serverSocket.accept();

				BufferedReader inUser = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				PrintStream outUser = new PrintStream(socketClient.getOutputStream());

				String op = inUser.readLine();

				if (op.equalsIgnoreCase("pagamento")) {
					executePayment(inUser, outUser);
				}
			}

		} 
		
		catch (Exception e) {
			System.err.println("Some error has occured. Error message -> " + e.getMessage());
		}
	}

	public void executePayment(BufferedReader inUser, PrintStream outUser) {
		
		Integer numberOfPayments = 0;
		try {
			numberOfPayments = Integer.parseInt(inUser.readLine());
			
			if(numberOfPayments > 10) {
				throw new Exception("Número de pagamentos é maior do que 10.");
			}
			
			System.out.println("-> O número de pagamentos é " + numberOfPayments + ".");
		} catch (Exception e) {
			outUser.println("TA: Tente novamente. O número de pagamentos é maior do que 10.");
			System.exit(1);
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
				System.out.println(".: Número do pagamento (" + (i + 1) + "):");
				System.out.println(".: Nome do cliente: " + clientName);
				System.out.println(".: Número do cartão: " + clientCardNumber);
				System.out.println(".: Código de segurança: " + clientCardSecureCode);
				System.out.println(".: Validade do cartão: " + clientCardValidDate);
				System.out.println(".: Parcelas: " + clientNumberInstallments);
				System.out.println(".: Valor da compra: " + clientPurchaseValue);
				System.out.println("\n");

				System.out.println("-> Processando pagamento...");
				Thread.sleep(2000);
				System.out.println("-> Pagamento processado.");
				Thread.sleep(500);
				System.out.println("-> REP: Enviando resposta ao cliente...");
				Thread.sleep(500);
				
				
				outUser.println("OK");

				System.out.println("-> REP: Resposta enviada.");
				System.out.println("-> ACK: Aguardando confirmação...");
				String confirmationStatus = inUser.readLine();
				if(confirmationStatus.equals("ACK")) {
					System.out.println("-> ACK: Confirmação do cliente recebida.");
				}
				
				Thread.sleep(500);

			} catch (Exception e) {
				outUser.println("O pagamento falhou. Razão: " + e.getMessage());
			}

		}

	}

	public static void main(String[] args) {
		new PaymentServer().startServer();
	}

}
