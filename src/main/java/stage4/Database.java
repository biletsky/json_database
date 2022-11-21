package stage4;

import com.beust.jcommander.Parameter;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, String> database = new HashMap<>();

    public String show(String value) {
        return database.get(value);
    }

    public void update(String key, String value) {
        database.put(key, value);
    }

    public void delete(String key) {
        database.remove(key);
    }

    @Parameter(names = {"--type", "-t"})
    @Expose
    private Method type;

    @Parameter(names = {"--key", "-i"})
    @Expose
    private String key;

    @Parameter(names = {"--value", "-m"})
    @Expose
    private String value;

    public Method getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
