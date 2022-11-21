package stage5.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ConnectionWorker implements Runnable {
    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";

    @Override
    public void run() {
        DatabaseService databaseService = new DatabaseService();
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (!server.isClosed()) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder
                            .excludeFieldsWithoutExposeAnnotation()
                            .create();
                    Map<String, String> clientJson = gson.fromJson(input.readUTF(), Map.class);
                    output.writeUTF(databaseService.request(clientJson.get("type"), clientJson.get("key"), clientJson.get("value")));
                    if (clientJson.get("type").equals("exit")) {
                        server.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
