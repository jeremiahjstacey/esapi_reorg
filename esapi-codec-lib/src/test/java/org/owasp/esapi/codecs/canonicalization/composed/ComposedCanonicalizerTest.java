package org.owasp.esapi.codecs.canonicalization.composed;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.owasp.esapi.codecs.canonicalization.composed.EncodingTester;


/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
public class ComposedCanonicalizerTest {

    private EncodingTester mockEncTest1;
    private EncodingTester mockEncTest2;
    private List<EncodingTester> ctrArg;
    
    @Before
    public void setup() {
        mockEncTest1 = Mockito.mock(EncodingTester.class);
        mockEncTest2 = Mockito.mock(EncodingTester.class);
        ctrArg = Arrays.asList(mockEncTest1, mockEncTest2);
    }
    
    @Test
    public void testShallowListCopy() {        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        List<EncodingTester> field = (List<EncodingTester>) Whitebox.getInternalState(uit, "testers");
        Assert.assertTrue(ctrArg != field);
        Assert.assertTrue(field.size() == 2);
        Assert.assertTrue(field.contains(mockEncTest1));
        Assert.assertTrue(field.contains(mockEncTest2));
        
    }
    
    @Test
    public void testNullReturnsNull() {
        Mockito.when(mockEncTest1.check(null)).thenReturn(null);
        Mockito.when(mockEncTest2.check(null)).thenReturn(null);
        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        
        String value = uit.canonicalize(null);
        Assert.assertNull(value);
        
        Mockito.verify(mockEncTest1, Mockito.times(0)).check(null);
        Mockito.verify(mockEncTest2, Mockito.times(0)).check(null);
    }
    
    @Test
    public void testFirstViableEncodeReturns() {
        Mockito.when(mockEncTest1.check("input")).thenReturn("first");
        Mockito.when(mockEncTest2.check("input")).thenReturn("second");
        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        
        String value = uit.canonicalize("input");
        Assert.assertEquals("first", value);
        
        Mockito.verify(mockEncTest1, Mockito.times(1)).check("input");
        Mockito.verify(mockEncTest2, Mockito.times(1)).check("input");
    }
    
    @Test
    public void testFirstViableEncodeReturnsWithNulls() {
        Mockito.when(mockEncTest1.check("input")).thenReturn(null);
        Mockito.when(mockEncTest2.check("input")).thenReturn("second");
        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        
        String value = uit.canonicalize("input");
        Assert.assertEquals("second", value);
        
        Mockito.verify(mockEncTest1, Mockito.times(1)).check("input");
        Mockito.verify(mockEncTest2, Mockito.times(1)).check("input");
    }
    
    @Test
    public void testFirstViableEncodeReturns2() {
        Mockito.when(mockEncTest1.check("input")).thenReturn("encoded");
        Mockito.when(mockEncTest2.check("input")).thenReturn("encoded");
        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        
        String value = uit.canonicalize("input");
        Assert.assertEquals("encoded", value);
        
        Mockito.verify(mockEncTest1, Mockito.times(1)).check("input");
        Mockito.verify(mockEncTest2, Mockito.times(1)).check("input");
    }
    
    @Test
    public void testFirstViableEncodeReturns3() {
        Mockito.when(mockEncTest1.check("input")).thenReturn("input");
        Mockito.when(mockEncTest2.check("input")).thenReturn("encoded");
        
        ComposedCanonicalizer uit = new ComposedCanonicalizer(ctrArg);
        
        String value = uit.canonicalize("input");
        Assert.assertEquals("encoded", value);
        
        Mockito.verify(mockEncTest1, Mockito.times(1)).check("input");
        Mockito.verify(mockEncTest2, Mockito.times(1)).check("input");
    }
  }
