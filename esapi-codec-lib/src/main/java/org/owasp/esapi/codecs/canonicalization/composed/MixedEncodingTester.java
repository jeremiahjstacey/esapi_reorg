package org.owasp.esapi.codecs.canonicalization.composed;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.validation.ResultValidator;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.ValidationResult;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MixedEncodingTester implements ResultValidator<String> {

    private final Codec codec1;
    private final Codec codec2;
    
    /**
     * 
     */
    public MixedEncodingTester(Codec codec1, Codec codec2) {
        this.codec1 = codec1;
        this.codec2 = codec2;
    }
    
    @Override
    public ValidationResult<String> validate(String data) {
        String rval = codec1.decode(data);
        ValidationResult<String> response = new ValidationResult<>(ValidationStatus.PASS, data, rval);
        
        if (!rval.equals(data)) {
            String decode1 = rval;
            String decode2 = codec2.decode(decode1);
            if (!decode2.equals(decode1)) {
                String message = String.format("Mixed Encoding Detected: %s -> %s decodes to %s :: %s -> %s decodes to  %s", data, codec1.getClass().getSimpleName(),decode1,codec2.getClass().getSimpleName(),decode1, decode2);
                response = new ValidationResult<>(ValidationStatus.FAIL, message, data);
            }
        }
        return response;
    }

   
}
