import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("Tryng to connect...");

        // ALTERAR O IP PARA O IP ATUAL DO COMPUTADOR - wlp2s0
        try (Socket client = new Socket("172.16.0.213", 3333)){
            System.out.println("Connected.");
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
            Scanner s = new Scanner(System.in);
            System.out.println("Informe uma frase para o servidor: ");
            String frase = s.nextLine();

            System.out.println("Sua frase:  " + frase);
            buffer.write(frase);
            buffer.write("Teste de envio de mensagem ");
            buffer.flush();
            System.out.println("Encerrando... ");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // /**
    //  * Pega o IP atual utilizado no computador
    //  */
    // private static String getIP()
    // {
    //     InetAddress inetAddress = InetAddress.getLocalHost();
    //     // System.out.println("IP Address:- " + inetAddress.getHostAddress());
    //     // System.out.println("Host Name:- " + inetAddress.getHostName());
    //     return inetAddress.getHostAddress();
    // }
}