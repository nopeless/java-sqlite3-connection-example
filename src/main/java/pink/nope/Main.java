package pink.nope;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void run() throws URISyntaxException, SQLException {
        DBManager dbManager = new DBManager("userdb.sqlite");

        dbManager.connect();

        try (var res = dbManager.execute("SELECT * FROM users")) {
            if (!res.next()) {
                System.out.println("No users found!");
            } else {
                do {
                    System.out.println(res.getString("name"));
                } while (res.next());
            }
        }
    }
}