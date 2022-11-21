package stage6.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server started!");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ConnectionWorker connectionWorker = new ConnectionWorker();
        executor.submit(connectionWorker);
        executor.shutdown();
    }
}
