package stage6.server.requests;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Request {
    @Expose
    private String type;
    @Expose
    private JsonElement key;
    @Expose
    private JsonElement value;
}
