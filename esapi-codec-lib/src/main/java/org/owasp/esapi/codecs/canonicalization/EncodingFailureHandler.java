package org.owasp.esapi.codecs.canonicalization;

/**
 * Contract for allowing isolated forms of responses when Encoding issues are detected.
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public interface EncodingFailureHandler {
	/**
	 * Performs handling for the identified failure event.
	 * @param message String providing information on the failure.
	 */
    void onFailure(String message);
}
