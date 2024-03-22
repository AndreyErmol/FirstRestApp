package ermolaev.exceptions;

public class InvalidRequestBody extends RuntimeException {
    public InvalidRequestBody(String message) {
        super(message);
    }
}
