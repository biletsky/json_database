package stage6.server;

import com.google.gson.*;
import stage6.server.command.CommandExecutor;
import stage6.server.command.commands.DeleteCommand;
import stage6.server.command.commands.GetCommand;
import stage6.server.command.commands.SetCommand;
import stage6.server.exceptions.BadRequestException;
import stage6.server.exceptions.NoSuchKeyException;
import stage6.server.requests.Request;
import stage6.server.requests.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionWorker implements Runnable {
    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";

    private final CommandExecutor executor = new CommandExecutor();

    @Override
    public void run() throws BadRequestException {
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
                    Request request = new Gson().fromJson(input.readUTF(), Request.class);
                    Response response = new Response();
                    try {
                        switch (request.getType()) {
                            case "get" -> {
                                GetCommand getCommand = new GetCommand(request.getKey());
                                executor.executeCommand(getCommand);
                                response.setValue(getCommand.getValue());
                            }
                            case "set" -> {
                                SetCommand setCommand = new SetCommand(request.getKey(), request.getValue());
                                executor.executeCommand(setCommand);
                            }
                            case "delete" -> {
                                DeleteCommand deleteCommand = new DeleteCommand(request.getKey());
                                executor.executeCommand(deleteCommand);
                            }
                            case "exit" -> {
                                server.close();
                                response.setReason(Response.EXIT);
                            }
                            default -> throw new BadRequestException();
                        }
                        response.setResponse(Response.OK);
                    } catch (BadRequestException | NoSuchKeyException e) {
                        response.setResponse(Response.ERROR);
                        response.setReason(Response.NO_KEY);
                    } catch (Exception e) {
                        response.setResponse(Response.ERROR);
                    } finally {
                        output.writeUTF(gson.toJson(response));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
