package stage5.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server started!");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ConnectionWorker connectionWorker = new ConnectionWorker();
        executor.submit(connectionWorker);
        executor.shutdown();
    }
}
