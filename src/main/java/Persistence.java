import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persistence {

    private static final String USERNAME = "adam";
    private static final String PASSWORD = "MysqlaA1!";
    private static final String JDBC_URL = "jdbc:mysql://pirateofdw.gotdns.com:8089/catamarans?useSSL=false";

    public static Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
