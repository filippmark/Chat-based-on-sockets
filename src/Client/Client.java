package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    static Socket client;
    private static BufferedWriter out;
    static String nick;

    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the ip address");
            String ipAddr = scanner.nextLine();
            System.out.println("Enter the port");
            int port = scanner.nextInt();
            System.out.println("Enter the nickname");
            scanner.nextLine();
            nick = scanner.nextLine();
            client = new Socket(InetAddress.getByName(ipAddr), port);
            BufferedWriter outTemp = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            outTemp.write(nick + '\n');
            outTemp.flush();
            BufferedReader inTemp = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String servAns = inTemp.readLine();
            if ((client.isConnected()) && (servAns.equals("Ok"))) {
                System.out.println("all is fine");
                InMessages in = new InMessages(client);
                in.start();
                OutMessages out = new OutMessages(client);
                out.start();
            }
            else {
                if (servAns.equals("No"))
                    System.out.println("Ooops, change your nick and try again");
                else
                    System.out.println("Ooops, check ip and port");
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
                if (!(message.equals("stop"))) {
                    System.out.println(message);
                }
            }
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

class OutMessages extends Thread{
    Socket socket;
    public OutMessages(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in)) {
            String message = null;
            while ((null == message) || (!message.equals("stop"))){
                message = scanner.nextLine();
                System.out.println("You: " + message);
                if ((!message.equals("stop"))) {
                    out.write(Client.nick + ": " + message + '\n');
                    out.flush();
                }
                else{
                    out.write(message + '\n');
                    out.flush();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}