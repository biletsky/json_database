import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stage6.server.Database;
import stage6.server.exceptions.NoSuchKeyException;

import static org.junit.jupiter.api.Assertions.*;
public class DataBaseTests {

    Database database;

    @BeforeEach
    public void init(){
        database = new Database();
        database.set(new Gson().fromJson("999", JsonElement.class), new Gson().fromJson("test_value", JsonElement.class));
    }

    @Test
    public void getDataTest() {
        assertEquals(database.get(new Gson().fromJson("999", JsonElement.class)).getAsString(), "test_value");
    }

    @Test
    public void setDataToDatabase() {
        database.set(new Gson().fromJson("998", JsonElement.class), new Gson().fromJson("test_value2", JsonElement.class));
        assertEquals(database.get(new Gson().fromJson("998", JsonElement.class)).getAsString(), "test_value2");
    }

    @Test
    public void updateDatabaseData() {
        database.set(new Gson().fromJson("999", JsonElement.class), new Gson().fromJson("new_value", JsonElement.class));
        assertEquals(database.get(new Gson().fromJson("999", JsonElement.class)).getAsString(), "new_value");
    }

    @Test
    public void deleteKeyFromDatabase() {
        database.delete(new Gson().fromJson("999", JsonElement.class));
        Exception exception = assertThrows(NoSuchKeyException.class, () -> database.get(new Gson().fromJson("999", JsonElement.class)).getAsString());
        assertEquals("No such key", exception.getMessage());
    }
}