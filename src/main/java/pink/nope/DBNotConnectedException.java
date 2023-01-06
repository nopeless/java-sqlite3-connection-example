package pink.nope;

public class DBNotConnectedException extends RuntimeException {
    public DBNotConnectedException() {
        super("Not connected to database!");
    }
}
