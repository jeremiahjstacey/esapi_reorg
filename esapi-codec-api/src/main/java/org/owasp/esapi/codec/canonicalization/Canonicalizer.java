package org.owasp.esapi.codec.canonicalization;

/**
 * Contract for converting Strings, which have more than one possible representation, into a "standard", "normal", or canonical form.
 * @author Jeremiah
 * @since Jan 1, 2018
 *
 */
public interface Canonicalizer {
    
	/**
	 * Performs normalization on the input value and returns the safe form of the content.
	 * @param input String to normalize.
	 * @return Normalized String.
	 */
    String canonicalize (String input);

}
