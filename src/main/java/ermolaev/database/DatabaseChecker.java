package ermolaev.database;

import ermolaev.exceptions.DatabaseConnectException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseChecker {
    public static void checkConnection(JdbcTemplate jdbcTemplate) {
        try {
            Integer result = jdbcTemplate.queryForObject("select 1", Integer.class);

            if (result == null || result != 1) {
                System.out.println("Data could not be retrieved from the database");
                throw new DatabaseConnectException("Data could not be retrieved from the database");
            } else {
                System.out.println("The database has been successfully connected");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database");
            throw new DatabaseConnectException("Failed to connect to the database");
        }
    }
}
