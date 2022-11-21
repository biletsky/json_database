package stage6.server.requests;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Response {

    public final static String OK = "OK";
    public final static String ERROR = "ERROR";
    public final static String EXIT = "EXIT";
    public final static String NO_KEY = "No such key";

    @Expose
    private String response;
    @Expose
    private String reason;
    @Expose
    private JsonElement value;

}
