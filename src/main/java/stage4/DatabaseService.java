package stage4;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

public class DatabaseService {

    Database database = new Database();

    public String show(String key) {
        if (database.show(key) == null) {
            return toJson(Map.of("response", "ERROR", "reason", "No such key"));
        } else {
            return toJson(Map.of("response", "OK", "value", database.show(key)));
        }
    }

    public String save(String key, String message) {
        if (message != null) {
            database.update(key, message);
            return toJson(Map.of("response", "OK"));
        } else {
            database.delete(key);
            return toJson(Map.of("response", "OK"));

        }
    }

    public String delete(String key) {
        if (database.show(key) == null) {
            return toJson(Map.of("response", "ERROR", "reason", "No such key"));
        } else {
            database.delete(key);
            return toJson(Map.of("response", "OK"));
        }
    }

    public String databaseSearch(String method, String key, String message) throws IOException {
        String response = "";
        switch (Method.valueOf(method)) {
            case get -> response = show(key);
            case set -> response = save(key, message);
            case delete -> response = delete(key);
            case exit -> response = toJson(Map.of("response", "OK", "reason", "exit"));
        }
        return response;
    }

    private String toJson(Object object) {
        return new Gson().toJson(object);
    }

}
