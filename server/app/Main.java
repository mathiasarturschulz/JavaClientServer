import java.net.ServerSocket;
import java.net.Socket;	
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Olá ;)");
        System.out.println("By: Mathias Artur Schulz");

        final Integer PORT = 3333;

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port -> " + PORT);
            while(true) {
                Socket client = server.accept();
                System.out.println("Client connected -> " + client.getInetAddress().toString());

                Scanner entrada = new Scanner(client.getInputStream());
                while(entrada.hasNextLine()){
                    System.out.println(entrada.nextLine());
                }
                entrada.close();
            }
        }catch(Exception e) {
            System.err.println("Server exception -> " + e.getMessage());
        }
    }
}
