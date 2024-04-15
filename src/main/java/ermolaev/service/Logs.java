package ermolaev.service;

public class Logs {
    private Logs() {}

    static final String WORKER_FOUND = "The worker has been found: {}.";
    static final String METHOD_STARTED_WORKING = "Method '{}' started working with the parameter {}: {}.";
    static final String METHOD_CALLING_METHOD = "Method '{}' is calling method '{}' of {}.";
    static final String NO_WORKER_FOUND_WITH = "No worker found with {} {}";
}
