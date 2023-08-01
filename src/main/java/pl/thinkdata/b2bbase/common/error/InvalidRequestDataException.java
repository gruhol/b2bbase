package pl.thinkdata.b2bbase.common.error;

public class InvalidRequestDataException extends RuntimeException {
    private String message;

    public InvalidRequestDataException(String message) {
        super(message);
        this.message = message;
    }
}
