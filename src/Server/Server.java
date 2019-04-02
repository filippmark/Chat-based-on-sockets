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
    static ArrayList<Connection> connections = new ArrayList<Connection>();
    static ArrayList<String> nicknames = new ArrayList<String>();
    static Socket clientSocket;
    private static BufferedReader in;
    static int port = 0;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        System.out.println("Enter the port");
        port = scanner.nextInt();
        try(ServerSocket serverSocket = new ServerSocket(0)){
            System.out.println(serverSocket.getLocalPort());
            while (flag || (connections.size() != 0)) {
                clientSocket = serverSocket.accept();
                BufferedReader inTemp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String nick = inTemp.readLine();
                BufferedWriter outTemp = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                if (nicknames.indexOf(nick) == -1) {
                    outTemp.write("Ok");
                    nicknames.add(nick);
                    connections.add(new Connection(clientSocket, nick));
                    inTemp.close();
                    flag = false;
                } else {
                    outTemp.write("No");
                    inTemp.close();
                    clientSocket.close();
                }
                InMessages in = new InMessages(clientSocket);
                in.start();
            }
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
            while ((message == null) || (!message.equals("stop"))){
                message = in.readLine();
                if (!(message == null)) {
                    System.out.println(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

class Connection{
    Socket socket;
    String nick;

    public Connection(Socket socket, String nick){
        this.socket = socket;
        this.nick = nick;
    }

}