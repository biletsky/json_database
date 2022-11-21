package stage5.client;

import com.beust.jcommander.Parameter;
import com.google.gson.annotations.Expose;

public class Arguments {
    @Parameter(names = {"--type", "-t"})
    @Expose
    private Method type;

    @Parameter(names = {"--key", "-k"})
    @Expose
    private String key;

    @Parameter(names = {"--value", "-v"})
    @Expose
    private String value;

    @Parameter(names = {"--fileName", "-in"})
    @Expose
    private String fileName;

    public String getType() {
        return String.valueOf(type);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFileName() {
        return fileName;
    }
}
