package pl.dreilt.iteventsapi.appuser.exception;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String message) {
        super(message);
    }
}
