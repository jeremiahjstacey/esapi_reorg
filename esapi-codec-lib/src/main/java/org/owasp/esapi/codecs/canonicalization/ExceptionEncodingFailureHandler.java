package org.owasp.esapi.codecs.canonicalization;

import org.owasp.esapi.errors.IntrusionException;

/**
 * Failure handler which will throw an {@link IntrusionException}.
 *  
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class ExceptionEncodingFailureHandler implements EncodingFailureHandler {
    /** {@inheritDoc}*/
    @Override
    public void onFailure(String message) {
        throw new IntrusionException( "Input validation failure", message );
       // throw new IntrusionException( message, message );
    }

}
