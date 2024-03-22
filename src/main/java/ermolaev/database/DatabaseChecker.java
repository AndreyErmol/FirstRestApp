package ermolaev.database;

import ermolaev.exceptions.DatabaseConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseChecker {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseChecker.class);

    private DatabaseChecker() {}

    public static void checkConnection(JdbcTemplate jdbcTemplate) {
        try {
            logger.info("Trying to send a test request to data base.");
            Integer result = jdbcTemplate.queryForObject("select 1", Integer.class);

            if (result == null || result != 1) {
                logger.warn("Data could not be retrieved from the database.");
                throw new DatabaseConnectException("Data could not be retrieved from the database");
            } else {
                logger.info("The database has been successfully connected.");
            }
        } catch (Exception e) {
            logger.warn("Failed to connect to the database.");
            throw new DatabaseConnectException("Failed to connect to the database");
        }
    }
}
