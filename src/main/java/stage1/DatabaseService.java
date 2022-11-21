package stage1;

import java.util.*;

public class DatabaseService {

    Database database = new Database();

    public void show(int key) {
        if (database.show(key) == null) {
            System.out.println("Error");
        } else System.out.println(database.show(key));
    }

    public void save(int key, String value) {
        if (key < 0 || key > 100) {
            System.out.println("ERROR");
        } else {
            database.delete(key);
            System.out.println("OK");
        }
    }

    public void delete(int key) {
        if (database.show(key) == null) {
            System.out.println("ERROR");
        } else {
            database.delete(key);
            System.out.println("OK");
        }
    }

    public void databaseSearch() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("exit")) {
            String[] arr = input.split(" ");
            String method = arr[0].toUpperCase(Locale.ROOT);
            List<String> list = new ArrayList<>(Arrays.asList(arr).subList(2, arr.length));
            switch (Method.valueOf(method)) {
                case GET -> {
                    show(Integer.parseInt(arr[1]));
                }
                case SET -> {
                    save(Integer.parseInt(arr[1]), String.join(" ", list));
                }
                case DELETE -> {
                    delete(Integer.parseInt(arr[1]));
                }
                default -> throw new DatabaseException("Invalid command");
            }
            input = scanner.nextLine();
        }
    }
}
