package stage3;

import com.beust.jcommander.Parameter;

import java.io.IOException;

public class DatabaseService {

    Database database = new Database();

    @Parameter(names = {"--method", "-t"})
    private Method method;

    @Parameter(names = {"--cell", "-i"})
    private int key;

    @Parameter(names = {"--value", "-m"})
    private String value;

    public String show(int key) {
        if (database.show(key) == null) {
            return "ERROR";
        } else {
            return database.show(key);
        }
    }

    public String save(int key, String message) {
        if (key < 0 || key > 100) {
            return "ERROR";
        } else if (message != null) {
            database.update(key, message);
            return "OK";
        } else {
            database.delete(key);
            return "OK";
        }
    }

    public String update(int key, String message) {
        if (database.show(key) == null) {
            return "ERROR";
        } else {
            database.update(key, message);
            return "OK";
        }
    }

    public String delete(int key) {
        if (key < 0 || key > 1000) {
            return "ERROR";
        } else {
            database.delete(key);
            return "OK";
        }
    }

    public String databaseSearch(String method, int key, String meassage) throws IOException {
        switch (Method.valueOf(method)) {
            case GET -> {
                return show(key);
            }
            case SET -> {
                return save(key, meassage);
            }
            case DELETE -> {
                return delete(key);
            }
            case EXIT -> {
                return "exit";
            }
            default -> {
                return "Invalid command";
            }
        }
    }

    public Method getMethod() {
        return method;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
