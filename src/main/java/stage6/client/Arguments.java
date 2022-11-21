package stage6.client;

import com.beust.jcommander.Parameter;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Arguments {

    @Parameter(names = {"--type", "-t"})
    @Expose
    private String type;

    @Parameter(names = {"--key", "-k"})
    @Expose
    private String key;

    @Parameter(names = {"--value", "-v"})
    @Expose
    private String value;

    @Parameter(names = {"--fileName", "-in"})
    @Expose
    private String fileName;

}
