import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("Tryng to connect...");

        try (Socket client = new Socket("192.168.100.157", 3333)){
            System.out.println("Connected.");
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
            buffer.write("TESTE ");
            buffer.write("TESTE 2 ");
            buffer.write("TESTE 3 ");
            buffer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}