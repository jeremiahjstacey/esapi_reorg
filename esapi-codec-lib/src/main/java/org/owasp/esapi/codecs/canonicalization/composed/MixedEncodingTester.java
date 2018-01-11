package org.owasp.esapi.codecs.canonicalization.composed;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.EncodingFailureHandler;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MixedEncodingTester implements EncodingTester {

    private final EncodingFailureHandler failureHandler;
    private final Codec codec1;
    private final Codec codec2;
    
    /**
     * 
     */
    public MixedEncodingTester(Codec codec1, Codec codec2, EncodingFailureHandler failHandler) {
        this.codec1 = codec1;
        this.codec2 = codec2;
       this.failureHandler = failHandler;
    }
    
    /** {@inheritDoc}*/
    @Override
    public String check(String input) {
        String rval = codec1.decode(input);
        
        if (!rval.equals(input)) {
            String decode1 = rval;
            String decode2 = codec2.decode(decode1);
            if (!decode2.equals(decode1)) {
                String message = String.format("Mixed Encoding Detected: %s -> %s decodes to %s :: %s -> %s decodes to  %s", input, codec1.getClass().getSimpleName(),decode1,codec2.getClass().getSimpleName(),decode1, decode2);
                failureHandler.onFailure(message);
                rval = input;
            }
        }
        return rval;
    }
}
