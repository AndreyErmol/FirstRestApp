package ermolaev.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FailedToSaveResource extends RuntimeException {
    public FailedToSaveResource(String message) {
        super(message);
    }
}
