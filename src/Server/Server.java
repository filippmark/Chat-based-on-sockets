package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    static ServerSocket serverSocket;
    static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    ArrayList<Socket> sockets = new ArrayList<>();
    static int port = 0;

    public static void main(String[] args){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the port");
            port = scanner.nextInt();
            serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
            clientSocket = serverSocket.accept();
            System.out.println("oke");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String word = in.readLine();
            System.out.println(word);
            in.close();
            out.close();
            serverSocket.close();

        }catch (Exception e){
            e.getStackTrace();
        }
    }
}