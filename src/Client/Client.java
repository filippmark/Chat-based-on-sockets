package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Socket client;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the ip address");
            String ipAddr = scanner.nextLine();
            System.out.println(ipAddr);
            System.out.println("Enter the port");
            int port = scanner.nextInt();
            client = new Socket(InetAddress.getByName(ipAddr), port);
            client.getLocalPort();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            if(client.isConnected()) {
                System.out.println("all is fine");
                out.write("all is fine" + "\n");
                out.flush();
            }
            System.out.println(in.readLine());
            in.close();
            out.close();
            client.close();
        }catch (Exception e){
            e.getStackTrace();
        }

    }

}