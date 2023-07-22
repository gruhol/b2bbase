package pl.thinkdata.b2bbase.common.error;

public class AuthorizationException extends RuntimeException {
    private String message;

    public AuthorizationException(String error_message) {
        super(error_message);
        this.message = error_message;    }
}
