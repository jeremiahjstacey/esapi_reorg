package org.owasp.esapi.validation;

@FunctionalInterface
public interface ResultValidator <T> {
	/**
	 * Performs a context sensitive validation on the specified piece of data.
	 * @param data Reference to validate.
	 * @return {@link ValidationResponse} defining the result of the process.
	 */
    ValidationResponseWithResult<T> validate(T data);

}
