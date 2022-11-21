package stage2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            try (Socket socket = server.accept(); DataInputStream input = new DataInputStream(socket.getInputStream()); DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
                String clientMessage = input.readUTF();
                System.out.println("Received: " + clientMessage);
                String sent = "A record # 23 was sent!";
                output.writeUTF(sent);
                System.out.println("Sent: " + sent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
