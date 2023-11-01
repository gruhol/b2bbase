package pl.thinkdata.b2bbase.common.error;

public class SystemException extends RuntimeException {

    private String message;

    public SystemException(String message) {
        super(message);
        this.message = message;
    }
}
