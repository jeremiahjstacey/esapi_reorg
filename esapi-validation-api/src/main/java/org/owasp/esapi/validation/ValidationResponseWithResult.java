package org.owasp.esapi.validation;

public class ValidationResponseWithResult<T> extends ValidationResponse {

    /** Reusable PASS reference with no Message.*/
    public static final ValidationResponseWithResult OK = new ValidationResponseWithResult(ValidationStatus.PASS, null);

    private final T result;
    
    public ValidationResponseWithResult(Exception exception) {
        super(exception);
        result = null;
    }

    public ValidationResponseWithResult(ValidationStatus status, String detail, Exception exception) {
        super(status, detail, exception);
        result = null;
    }

    public ValidationResponseWithResult(ValidationStatus status, String detail, T result) {
        super(status, detail);
        this.result = result;
    }

    public ValidationResponseWithResult(ValidationStatus status, T result) {
        super(status);
        this.result = result;
    }
    
    public T getResult() {
        return result;
    }

}
