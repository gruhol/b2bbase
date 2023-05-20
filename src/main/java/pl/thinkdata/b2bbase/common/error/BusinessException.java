package pl.thinkdata.b2bbase.common.error;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}