package org.owasp.esapi.codecs.canonicalization.composed;

/**
 * Implementation which provides some 
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
//TODO Make this a Validator<String>
public interface EncodingTester {
    String check(String input);
}
