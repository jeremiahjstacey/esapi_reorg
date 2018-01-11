package org.owasp.esapi.codecs.canonicalization.composed;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.EncodingFailureHandler;

/**
 * EncodingTester implementation which determines whether a given String can be
 * decoded by a single codec multiple times.
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MultipleEncodingTester implements EncodingTester {
	/** Handler for Failures when Multiple Encoding is detected.*/
    private final EncodingFailureHandler failureHandler;
    /** The codec reference being tested.*/
    private final Codec codec;
    
    /**
     * Constructs a new reference.
     * @param codec Codec reference to be used in Multiple Encoding checks.
     * @param failHandler Handler to be notified if Multiple Encoding is detected.
     */
    public MultipleEncodingTester(Codec codec, EncodingFailureHandler failHandler) {
    	if (codec == null) {
    		throw new IllegalArgumentException("Codec must be defined.");
    	}
    	if (failHandler == null) {
    		throw new IllegalArgumentException("EncodingFailureHandler must be provided");
    	}
    	
       this.codec = codec;
       this.failureHandler = failHandler;
    }
    
    /** {@inheritDoc}*/
    @Override
    public String check(String input) {
        String rval = codec.decode(input);
        String decode1 = rval;
        String decode2 = codec.decode(decode1);
        if (!decode2.equals(decode1)) {
            String message = String.format("Multiple Encoding Detected: '%s' -> %s decodes to '%s' decodes to '%s'", input, codec.getClass().getSimpleName(),decode1, decode2);
            failureHandler.onFailure(message);
            rval = input;
        }
        return rval;
    }
    

}
