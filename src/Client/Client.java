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

    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the ip address");
            String ipAddr = scanner.nextLine();
            System.out.println(ipAddr);
            System.out.println("Enter the port");
            int port = scanner.nextInt();
            System.out.println();
            client = new Socket(InetAddress.getByName(ipAddr), port);
            BufferedWriter outTemp = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println("Enter the nickname");
            String nick = scanner.nextLine();
            outTemp.write(nick);
            outTemp.flush();
            outTemp.close();
            BufferedReader inTemp = new BufferedReader(new InputStreamReader(client.getInputStream()));
            if ((client.isConnected()) && (inTemp.readLine().equals("Ok"))) {
                System.out.println("all is fine");
                InMessages in = new InMessages(client);
                in.start();
                OutMessages out = new OutMessages(client);
                out.start();
            }
            else {
                System.out.println("Ooops, check your nick or ip and port");
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
                System.out.println(message);
                if (!(null == message)) {
                    out.write(message + '\n');
                    out.flush();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}