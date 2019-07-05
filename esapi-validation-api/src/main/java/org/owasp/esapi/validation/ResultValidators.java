package org.owasp.esapi.validation;

import java.util.List;

import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

/**
 * Utility class which contains helpful methods for working with the Valdiation
 * api.
 *
 */
public final class ResultValidators {
	/** Private Utility Constructor. */
	private ResultValidators() {
		/* NO OP Util Ctr. */ }

	/**
	 * Creates a single ResultValidator reference which iterates the specified List
	 * and validates that the data element is valid to each. <br/>
	 * In the case of a failure, the ValidationResponseWithResult of the faulted delegate
	 * will be returned to the caller.
	 * 
	 * @param ResultValidators
	 *            List of ResultValidator references to check.
	 * @return ResultValidator reference.
	 */
	public static <R> ResultValidator<R> and(final List<ResultValidator<R>> ResultValidators) {
		return new ResultValidator<R>() {

			public ValidationResponseWithResult<R> validate(R data) {
			    ValidationResponseWithResult<R> response = ValidationResponseWithResult.OK;
				for (ResultValidator<R> ResultValidator : ResultValidators) {
					response = ResultValidator.validate(data);
					if (!response.isValid()) {
						break;
					}
				}
				return response;
			}
		};
	}

	public static <R> ResultValidator<R> or(final List<ResultValidator<R>> ResultValidators) {
		return new ResultValidator<R>() {
			public ValidationResponseWithResult<R> validate(R data) {
				StringBuilder msgBuffer = new StringBuilder();
				ValidationResponseWithResult<R> response = ValidationResponseWithResult.OK;
				for (ResultValidator<R> ResultValidator : ResultValidators) {
					response = ResultValidator.validate(data);
					if (response.isValid()) {
						break;
					}
					msgBuffer.append(response.getResponseStatus());
					msgBuffer.append(" : ");
					msgBuffer.append(response.getResponseDetail());
					msgBuffer.append(System.lineSeparator());
				}

				if (!response.isValid()) {
					response = new ValidationResponseWithResult(ValidationStatus.FAIL, msgBuffer.toString());
				}

				return response;
			}
		};
	}
}
