package stage1;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<Integer, String> database = new HashMap<>();

    public Map<Integer, String> getDatabase() {
        return database;
    }

    public void setDatabase(Map<Integer, String> database) {
        this.database = database;
    }

    public Map<Integer, String> index() {
        return new HashMap<>(this.database);
    }

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
