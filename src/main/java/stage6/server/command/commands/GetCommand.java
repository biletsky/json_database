package stage6.server.command.commands;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.Getter;
import stage6.server.Database;

import java.io.IOException;

@Data
@Getter
public class GetCommand implements Command {

    private JsonElement key;
    private JsonElement value;
    Database database = new Database();

    public GetCommand(JsonElement key) throws IOException {
        this.key = key;
    }

    @Override
    public void execute() {
        value = database.get(key);
    }
}
