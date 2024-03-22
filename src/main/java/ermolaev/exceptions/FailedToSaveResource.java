package ermolaev.exceptions;

public class FailedToSaveResource extends RuntimeException {
    public FailedToSaveResource(String message) {
        super(message);
    }
}
