package org.owasp.esapi.codecs.canonicalization;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.Logger.EventType;

/**
 * Failure handler which logs specified events.
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class LoggingEncodingFailureHandler implements EncodingFailureHandler {
    private final Logger logger = ESAPI.getLogger("Encoder");
    private final EventType eventType = Logger.SECURITY_FAILURE;
    
    /** {@inheritDoc}*/
    @Override
    public void onFailure(String message) {
        logger.warning( eventType, message);
    }

}
