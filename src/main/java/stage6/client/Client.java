package stage6.client;

import com.beust.jcommander.JCommander;
import com.google.gson.*;
import stage5.client.Arguments;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
                JsonElement root = new JsonParser().parse(new FileReader("src/main/java/stage6/client/data/" + arguments.getFileName()));
                message = gson.toJson(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Sent: " + message);
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
            System.out.println("Sent: " + gson.toJson(request));
        }
        return message;
    }
}
