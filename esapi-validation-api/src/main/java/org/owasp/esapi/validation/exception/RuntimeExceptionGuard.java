package org.owasp.esapi.validation.exception;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;

/**
 * Decorator for a delegate Validator that will manage RuntimeException occurences and supply well-formatted ValidationResponse captures of the failure.
 * 
 * @param <T> Data type being validatet
 */
public class RuntimeExceptionGuard<T> implements Validator<T> {
	/**  Delegate to decorate.*/
	private final Validator<T> delegate;

	/**
	 * Constructor.
	 * @param delegate Protected instance.
	 */
	public RuntimeExceptionGuard(Validator<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public ValidationResponse validate(T data) {
		ValidationResponse response;
		try {
			response = delegate.validate(data);
		} catch (RuntimeException re) {
			response = new ValidationResponse(re);
		}
		return response;
	}

}
