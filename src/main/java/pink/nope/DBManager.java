package pink.nope;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;

public class DBManager {
    private final String dbUrl;
    private DBManagerState state = DBManagerState.DISCONNECTED;

    private Connection connection;

    public DBManager(String file) throws URISyntaxException {
        URL res = Main.class.getClassLoader().getResource(file);
        if (res == null) {
            throw new RuntimeException("File not found!");
        }
        String absolutePath = Paths.get(res.toURI()).normalize().toString();
        this.dbUrl = "jdbc:sqlite:/" + absolutePath;

    }

    public void connect() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(this.dbUrl);
            this.state = DBManagerState.CONNECTED;
        } catch (SQLException e) {
            this.state = DBManagerState.ERROR;
            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet execute(String query) throws SQLException {
        if (this.state != DBManagerState.CONNECTED || this.connection == null) {
            throw new DBNotConnectedException();
        }
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public DBManagerState getState() {
        return state;
    }

    public String getDbUrl() {
        return dbUrl;
    }

}
