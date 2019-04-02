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
    //static ServerSocket serverSocket;
    static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    static int port = 0;

    public static void main(String[] args){
        ArrayList<Socket> sockets = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the port");
        port = scanner.nextInt();
        try(ServerSocket serverSocket = new ServerSocket(0)){
            System.out.println(serverSocket.getLocalPort());
            clientSocket = serverSocket.accept();
            System.out.println("lololol");
            InMessages in = new InMessages(clientSocket);
            System.out.println("lololol");
            in.start();
            System.out.println("lololol");
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}

class InMessages extends Thread{
    Socket socket;
    public InMessages (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message = null;
            System.out.println("in");
            while ((message == null) || (!message.equals("stop"))){
                System.out.println("in");
                message = in.readLine();
                System.out.println("in");
                if (!(message == null)) {
                    System.out.println(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}