package org.owasp.esapi.codecs.canonicalization.composed;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.validation.ResultValidator;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.ValidationResult;

/**
 * EncodingTester implementation which determines whether a given String can be
 * decoded by a single codec multiple times.
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MultipleEncodingTester  implements ResultValidator<String> {
    /** The codec reference being tested.*/
    private final Codec codec;
    
    /**
     * Constructs a new reference.
     * @param codec Codec reference to be used in Multiple Encoding checks.
     */
    public MultipleEncodingTester(Codec codec) {
    	if (codec == null) {
    		throw new IllegalArgumentException("Codec must be defined.");
    	}
       this.codec = codec;
    }
   

    @Override
    public ValidationResult<String> validate(String data) {
        String rval = codec.decode(data);
        ValidationResult<String> response = new ValidationResult<>(ValidationStatus.PASS, data, rval);

        String decode1 = rval;
        String decode2 = codec.decode(decode1);
        if (!decode2.equals(decode1)) {
            String message = String.format("Multiple Encoding Detected: '%s' -> %s decodes to '%s' decodes to '%s'", data, codec.getClass().getSimpleName(),decode1, decode2);
            response = new ValidationResult<>(ValidationStatus.FAIL, message, data);
        }
        return response;
    }


}
