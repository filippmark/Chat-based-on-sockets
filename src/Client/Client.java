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
            client = new Socket(InetAddress.getByName(ipAddr), port);
            client.getLocalPort();
            if(client.isConnected()) {
                System.out.println("all is fine");
                InMessages in = new InMessages(client);
                in.run();
                OutMessages out = new OutMessages(client);
                out.run();
            }
            else {
                System.out.println("Ooops");
            }
            client.close();
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
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            String message = null;
            while ((null == message) || (!message.equals("stop"))){
                message = input.readLine();
                if (!(null == message)) {
                    out.write(message);
                    out.flush();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}