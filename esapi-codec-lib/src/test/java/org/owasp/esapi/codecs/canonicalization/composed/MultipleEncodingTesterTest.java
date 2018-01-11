package org.owasp.esapi.codecs.canonicalization.composed;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.EncodingFailureHandler;
import org.owasp.esapi.codecs.canonicalization.composed.MultipleEncodingTester;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MultipleEncodingTesterTest {

    private EncodingFailureHandler mockFailHandler;
    private Codec mockCodec;
    
    @Before
    public void setup() {
        mockFailHandler = Mockito.mock(EncodingFailureHandler.class);
        mockCodec = Mockito.mock(Codec.class);
    }
    
    @Test
    public void testNoMultipleEncoding() {
        Mockito.when(mockCodec.decode("input")).thenReturn("reply");
        Mockito.when(mockCodec.decode("reply")).thenReturn("reply");
        
        MultipleEncodingTester uit = new MultipleEncodingTester(mockCodec, mockFailHandler);
        String result = uit.check("input");
        Assert.assertEquals("reply", result);
        
        Mockito.verify(mockCodec, Mockito.times(1)).decode("input");
        Mockito.verify(mockCodec, Mockito.times(1)).decode("reply");
        Mockito.verify(mockFailHandler, Mockito.times(0)).onFailure(Matchers.anyString());
    }
    
    @Test
    public void testMultipleEncodingDetected() {
        Mockito.when(mockCodec.decode("input")).thenReturn("reply");
        Mockito.when(mockCodec.decode("reply")).thenReturn("multiViolation");
        
        MultipleEncodingTester uit = new MultipleEncodingTester(mockCodec, mockFailHandler);
        String result = uit.check("input");
        Assert.assertEquals("input", result);
        
        Mockito.verify(mockCodec, Mockito.times(1)).decode("input");
        Mockito.verify(mockCodec, Mockito.times(1)).decode("reply");
        Mockito.verify(mockFailHandler, Mockito.times(1)).onFailure(Matchers.anyString());
    }
    
}
