package org.owasp.esapi.validation;

public class ValidationResult<T> extends ValidationResponse {

    /** Reusable PASS reference with no Message.*/
    public static final ValidationResult OK = new ValidationResult(ValidationStatus.PASS, null);

    private final T result;
    
    public ValidationResult(Exception exception) {
        super(exception);
        result = null;
    }

    public ValidationResult(ValidationStatus status, String detail, Exception exception) {
        super(status, detail, exception);
        result = null;
    }

    public ValidationResult(ValidationStatus status, String detail, T result) {
        super(status, detail);
        this.result = result;
    }

    public ValidationResult(ValidationStatus status, T result) {
        super(status);
        this.result = result;
    }
    
    public T getResult() {
        return result;
    }

}
