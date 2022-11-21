package stage5.server;

import com.google.gson.Gson;
import stage5.client.Method;

import java.util.Map;

public class DatabaseService {

    Database database = new Database("src/main/java/stage5/server/data/db.json");

    public String get(String key) {
        if (database.get(key) == null) {
            return toJson(Map.of("response", "ERROR", "reason", "No such key"));
        } else {
            return toJson(Map.of("response", "OK", "value", database.get(key)));
        }
    }

    public String set(String key, String message) {
        if (message != null) {
            database.set(key, message);
        } else {
            database.delete(key);
        }
        return toJson(Map.of("response", "OK"));
    }

    public String delete(String key) {
        if (database.get(key) == null) {
            return toJson(Map.of("response", "ERROR", "reason", "No such key"));
        } else {
            database.delete(key);
            return toJson(Map.of("response", "OK"));
        }
    }

    public String request(String method, String key, String message) {
        String response = "";
        switch (Method.valueOf(method)) {
            case get -> response = get(key);
            case set -> response = set(key, message);
            case delete -> response = delete(key);
            case exit -> response = toJson(Map.of("response", "OK", "reason", "exit"));
        }
        return response;
    }

    private String toJson(Object object) {
        return new Gson().toJson(object);
    }

}
