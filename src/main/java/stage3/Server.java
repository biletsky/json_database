package stage3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (!server.isClosed()) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    String[] clientMessage = input.readUTF().split(" ");
                    List<String> list = new ArrayList<>(Arrays.asList(clientMessage).subList(2, clientMessage.length));
                    String message;
                    if (list.size() > 0) {
                        message = databaseService.databaseSearch(clientMessage[0], Integer.parseInt(clientMessage[1]), String.join(" ", list));
                    } else {
                        message = databaseService.databaseSearch(clientMessage[0], Integer.parseInt(clientMessage[1]), "");
                    }
                    output.writeUTF(message);
                    if (message.equalsIgnoreCase("exit")) {
                        server.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
