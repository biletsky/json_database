package stage3;


import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;


    public static void main(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        JCommander.newBuilder()
                .addObject(databaseService)
                .build()
                .parse(args);
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            String sent = "";
            if (databaseService.getValue() != null) {
                System.out.println("Sent: " + databaseService.getMethod() + " " + databaseService.getKey() + " " + databaseService.getValue());
                sent = databaseService.getMethod() + " " + databaseService.getKey() + " " + databaseService.getValue();
            } else {
                System.out.println("Sent: " + databaseService.getMethod() + " " + databaseService.getKey());
                sent = databaseService.getMethod() + " " + databaseService.getKey();
            }
            output.writeUTF(sent);
            String serverMessage = input.readUTF();
            System.out.println("Received: " + serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
