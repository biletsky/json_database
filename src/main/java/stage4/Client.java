package stage4;


import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        Database database = new Database();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JCommander.newBuilder().addObject(database).build().parse(args);
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); DataInputStream input = new DataInputStream(socket.getInputStream()); DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String jsonRequest = gson.toJson(database);
            System.out.println("Client started!");
            String sent = "";
            if (database.getValue() != null) {
                System.out.println("Sent: " + jsonRequest);
                sent = database.getType() + " " + database.getKey() + " " + database.getValue();
            } else {
                System.out.println("Sent: " + jsonRequest);
                sent = database.getType() + " " + database.getKey();
            }
            output.writeUTF(sent);
            String serverMessage = input.readUTF();
            System.out.println("Received: " + serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
