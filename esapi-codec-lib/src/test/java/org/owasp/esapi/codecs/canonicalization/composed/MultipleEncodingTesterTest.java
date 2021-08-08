package org.owasp.esapi.codecs.canonicalization.composed;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.ValidationResult;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class MultipleEncodingTesterTest {

    private Codec mockCodec;
    
    @Before
    public void setup() {
        mockCodec = Mockito.mock(Codec.class);
    }
    
    @Test
    public void testNoMultipleEncoding() {
        Mockito.when(mockCodec.decode("input")).thenReturn("reply");
        Mockito.when(mockCodec.decode("reply")).thenReturn("reply");
        
        MultipleEncodingTester uit = new MultipleEncodingTester(mockCodec);
        ValidationResult<String> result = uit.validate("input");
        Assert.assertEquals("reply", result.getResult());
        Mockito.verify(mockCodec, Mockito.times(1)).decode("input");
        Mockito.verify(mockCodec, Mockito.times(1)).decode("reply");
    }
    
    @Test
    public void testMultipleEncodingDetected() {
        Mockito.when(mockCodec.decode("input")).thenReturn("reply");
        Mockito.when(mockCodec.decode("reply")).thenReturn("multiViolation");
        
        MultipleEncodingTester uit = new MultipleEncodingTester(mockCodec);
        ValidationResult<String> result = uit.validate("input");
        Assert.assertEquals("input", result.getResult());
        Assert.assertFalse(result.isValid());
        Assert.assertEquals(ValidationStatus.FAIL, result.getResponseStatus());
        Assert.assertTrue(result.getResponseDetail().toLowerCase().contains("multiple encoding detected"));
        
        Mockito.verify(mockCodec, Mockito.times(1)).decode("input");
        Mockito.verify(mockCodec, Mockito.times(1)).decode("reply");
    }
    
}
