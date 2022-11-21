package stage5.client;


import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;
    static Arguments arguments = new Arguments();

    public static void main(String[] args) {
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            output.writeUTF(createRequest());
            String serverMessage = input.readUTF();
            System.out.println("Received: " + serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createRequest() {
        Map<String, String> request = new HashMap<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        String message = "";
        Gson gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        if (arguments.getFileName() != null) {
            try {
                File file = new File("src/client/data/" + arguments.getFileName());
                Scanner scanner = new Scanner(file);
                message = scanner.nextLine();
                gson.toJson(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (arguments.getValue() != null) {
                request.put("type", arguments.getType());
                request.put("key", arguments.getKey());
                request.put("value", arguments.getValue());
            } else {
                request.put("type", String.valueOf(arguments.getType()));
                if (arguments.getKey() != null) {
                    request.put("key", arguments.getKey());
                }
            }
            message = gson.toJson(request);
        }
        System.out.println("Sent: " + gson.toJson(request));
        return message;
    }
}
