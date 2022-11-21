package stage3;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<Integer, String> database = new HashMap<>();

    public String show(int value) {
        return database.get(value);
    }

    public void update(int key, String value) {
        database.put(key, value);
    }

    public void delete(int key) {
        database.remove(key);
    }

}
