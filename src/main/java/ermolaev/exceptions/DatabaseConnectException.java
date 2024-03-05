package ermolaev.exceptions;

public class DatabaseConnectException extends RuntimeException {
    public DatabaseConnectException(String message) {
        super(message);
    }
}
