package stage2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            String sent = "Give me a record # 23";
            output.writeUTF(sent);
            System.out.println("Sent: " + sent);
            String serverMessage = input.readUTF();
            System.out.println("Received: " + serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
            SERVER_ADDRESS.length();
        }
    }
}
