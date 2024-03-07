package ermolaev.exceptions;

public class NoWorkerFound extends RuntimeException {
    public NoWorkerFound(String message) {
        super(message);
    }
}
