package org.owasp.esapi.codecs.canonicalization.composed;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.owasp.esapi.codecs.canonicalization.composed.EncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.NullInputGuard;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class NullInputGuardTest {

    @Test
    public void testReturnNullOnNullInput() {
        EncodingTester delegate = Mockito.mock(EncodingTester.class);
        NullInputGuard uit = new NullInputGuard(delegate);
        
        Assert.assertNull(uit.check(null));
        
        Mockito.verify(delegate, Mockito.times(0)).check(Matchers.anyString());
        
    }
    
    @Test
    public void testDelegateInvokeOnValidInput() {
        EncodingTester delegate = Mockito.mock(EncodingTester.class);
        Mockito.when(delegate.check("check")).thenReturn("answer");
        
        NullInputGuard uit = new NullInputGuard(delegate);
        
        Assert.assertEquals("answer", uit.check("check"));
        
        Mockito.verify(delegate, Mockito.times(1)).check("check");
        
    }
}
