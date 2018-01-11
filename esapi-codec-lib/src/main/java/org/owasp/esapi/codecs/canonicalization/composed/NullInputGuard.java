package org.owasp.esapi.codecs.canonicalization.composed;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class NullInputGuard implements EncodingTester {

    private final EncodingTester delegate;
    
    /**
     * 
     */
    public NullInputGuard(EncodingTester delegate) {
        this.delegate = delegate;
    }
    
    /** {@inheritDoc}*/
    @Override
    public String check(String input) {
        return input == null ? null : delegate.check(input);
    }
    
    public static NullInputGuard guard(EncodingTester delegate) {
    	return new NullInputGuard(delegate);
    }

}
