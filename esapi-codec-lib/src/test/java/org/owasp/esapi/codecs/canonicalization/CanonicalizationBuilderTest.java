package org.owasp.esapi.codecs.canonicalization;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.LogFactory;
import org.owasp.esapi.Logger;
import org.owasp.esapi.codec.canonicalization.Canonicalizer;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.composed.ComposedCanonicalizer;
import org.owasp.esapi.codecs.canonicalization.composed.EncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.MixedEncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.MultipleEncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.NullInputGuard;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 2, 2018
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest (ESAPI.class)
public class CanonicalizationBuilderTest {

	@Before
    public void setup() throws Exception {
    	Logger logger = Mockito.mock(Logger.class);
    	LogFactory factory = Mockito.mock(LogFactory.class);
    	Mockito.when(factory.getLogger(Matchers.anyString())).thenReturn(logger);
    	Mockito.when(factory.getLogger(Matchers.any(Class.class))).thenReturn(logger);
    	PowerMockito.mockStatic(ESAPI.class);
    	PowerMockito.when(ESAPI.class, "logFactory").thenReturn(factory);
    	PowerMockito.when(ESAPI.class, "getLogger", Matchers.anyString()).thenReturn(logger);
    	PowerMockito.when(ESAPI.class, "getLogger", Matchers.any(Class.class)).thenReturn(logger);
    }
     
    @Test
    public void testCodecAllocationInBuildComposed() {
        Codec codec = Mockito.mock(Codec.class);
        Codec codec2 = Mockito.mock(Codec.class);
        CanonicalizationBuilder builder = new CanonicalizationBuilder();
        builder.setDelegateCodecs(Arrays.asList(codec, codec2));
        Canonicalizer target = builder.build();
        Assert.assertTrue(target instanceof ComposedCanonicalizer);
        
        ComposedCanonicalizer ucan = (ComposedCanonicalizer) target;
        List<EncodingTester> testers = (List<EncodingTester>) Whitebox.getInternalState(ucan, "testers");
        
        Assert.assertEquals(4, testers.size());
        
        MultipleEncodingTester codecSelfCheck = unwrapNullGuard(testers.get(0));
        MixedEncodingTester oneToTwo = unwrapNullGuard(testers.get(1));
        MixedEncodingTester twoToOne = unwrapNullGuard(testers.get(2));
        MultipleEncodingTester codec2SelfCheck = unwrapNullGuard(testers.get(3));
      
        Codec codecSelfRef = (Codec) Whitebox.getInternalState(codecSelfCheck, "codec");
        Codec codec2SelfRef = (Codec)Whitebox.getInternalState(codec2SelfCheck, "codec");
        
        Codec oneToTwoFirst = (Codec)Whitebox.getInternalState(oneToTwo, "codec1");
        Codec oneToTwoSecond = (Codec)Whitebox.getInternalState(oneToTwo, "codec2");
        
        Codec twoToOneFirst = (Codec) Whitebox.getInternalState(twoToOne, "codec1");
        Codec twoToOneSecond = (Codec)Whitebox.getInternalState(twoToOne, "codec2");
        
        Assert.assertEquals(codec, codecSelfRef);
        Assert.assertEquals(codec, oneToTwoFirst);
        Assert.assertEquals(codec, twoToOneSecond);
        
        Assert.assertEquals(codec2, oneToTwoSecond);
        Assert.assertEquals(codec2, twoToOneFirst);
        Assert.assertEquals(codec2, codec2SelfRef);
    }

    private <T> T unwrapNullGuard(EncodingTester tester) {
        NullInputGuard guard = (NullInputGuard) tester;
        return (T) Whitebox.getInternalState(guard, "delegate");
    }
    
    @Test
    public void testDefaultFailureHandlersInBuildComposed() {
        Codec codec = Mockito.mock(Codec.class);
        Codec codec2 = Mockito.mock(Codec.class);
        CanonicalizationBuilder builder = new CanonicalizationBuilder();
        builder.setDelegateCodecs(Arrays.asList(codec, codec2));
        Canonicalizer target = builder.build();
        Assert.assertTrue(target instanceof ComposedCanonicalizer);
        
        ComposedCanonicalizer ucan = (ComposedCanonicalizer) target;
        List<EncodingTester> testers = (List<EncodingTester>) Whitebox.getInternalState(ucan, "testers");
        
        MultipleEncodingTester codecSelfCheck = unwrapNullGuard(testers.get(0));
        MixedEncodingTester oneToTwo = unwrapNullGuard(testers.get(1));
        MixedEncodingTester twoToOne = unwrapNullGuard(testers.get(2));
        MultipleEncodingTester codec2SelfCheck = unwrapNullGuard(testers.get(3));
      
        EncodingFailureHandler codecSelfHandler = (EncodingFailureHandler) Whitebox.getInternalState(codecSelfCheck, "failureHandler");
        EncodingFailureHandler codec2SelfHandler = (EncodingFailureHandler)Whitebox.getInternalState(codec2SelfCheck, "failureHandler");
        EncodingFailureHandler oneToTwoHandler = (EncodingFailureHandler)Whitebox.getInternalState(oneToTwo, "failureHandler");
        EncodingFailureHandler twoToOneHandler = (EncodingFailureHandler) Whitebox.getInternalState(twoToOne, "failureHandler");
    
        Assert.assertTrue(codecSelfHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(codec2SelfHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(oneToTwoHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(twoToOneHandler instanceof ExceptionEncodingFailureHandler);
    }
    

    
    @Test
    public void testMultipleEncodingRestrictionComposed() {
        Codec codec = Mockito.mock(Codec.class);
        Codec codec2 = Mockito.mock(Codec.class);
        CanonicalizationBuilder builder = new CanonicalizationBuilder();
        builder.setDelegateCodecs(Arrays.asList(codec, codec2));
        builder.setRestrictMultipleEncoding(false);
        Canonicalizer target = builder.build();
        Assert.assertTrue(target instanceof ComposedCanonicalizer);
        
        ComposedCanonicalizer ucan = (ComposedCanonicalizer) target;
        List<EncodingTester> testers = (List<EncodingTester>) Whitebox.getInternalState(ucan, "testers");
        
        MultipleEncodingTester codecSelfCheck = unwrapNullGuard(testers.get(0));
        MixedEncodingTester oneToTwo = unwrapNullGuard(testers.get(1));
        MixedEncodingTester twoToOne = unwrapNullGuard(testers.get(2));
        MultipleEncodingTester codec2SelfCheck = unwrapNullGuard(testers.get(3));
      
        EncodingFailureHandler codecSelfHandler = (EncodingFailureHandler) Whitebox.getInternalState(codecSelfCheck, "failureHandler");
        EncodingFailureHandler codec2SelfHandler = (EncodingFailureHandler)Whitebox.getInternalState(codec2SelfCheck, "failureHandler");
        EncodingFailureHandler oneToTwoHandler = (EncodingFailureHandler)Whitebox.getInternalState(oneToTwo, "failureHandler");
        EncodingFailureHandler twoToOneHandler = (EncodingFailureHandler) Whitebox.getInternalState(twoToOne, "failureHandler");
    
        Assert.assertTrue(codecSelfHandler instanceof LoggingEncodingFailureHandler);
        Assert.assertTrue(codec2SelfHandler instanceof LoggingEncodingFailureHandler);
        Assert.assertTrue(oneToTwoHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(twoToOneHandler instanceof ExceptionEncodingFailureHandler);
    }
    
    @Test
    public void testMixedEncodingRestrictionComposed() {
        Codec codec = Mockito.mock(Codec.class);
        Codec codec2 = Mockito.mock(Codec.class);
        CanonicalizationBuilder builder = new CanonicalizationBuilder();
        builder.setDelegateCodecs(Arrays.asList(codec, codec2));
        builder.setRestrictMixedEncoding(false);
        Canonicalizer target = builder.build();
        Assert.assertTrue(target instanceof ComposedCanonicalizer);
        
        ComposedCanonicalizer ucan = (ComposedCanonicalizer) target;
        List<EncodingTester> testers = (List<EncodingTester>) Whitebox.getInternalState(ucan, "testers");
        
        MultipleEncodingTester codecSelfCheck = unwrapNullGuard(testers.get(0));
        MixedEncodingTester oneToTwo = unwrapNullGuard(testers.get(1));
        MixedEncodingTester twoToOne = unwrapNullGuard(testers.get(2));
        MultipleEncodingTester codec2SelfCheck = unwrapNullGuard(testers.get(3));
      
        EncodingFailureHandler codecSelfHandler = (EncodingFailureHandler) Whitebox.getInternalState(codecSelfCheck, "failureHandler");
        EncodingFailureHandler codec2SelfHandler = (EncodingFailureHandler)Whitebox.getInternalState(codec2SelfCheck, "failureHandler");
        EncodingFailureHandler oneToTwoHandler = (EncodingFailureHandler)Whitebox.getInternalState(oneToTwo, "failureHandler");
        EncodingFailureHandler twoToOneHandler = (EncodingFailureHandler) Whitebox.getInternalState(twoToOne, "failureHandler");
    
        Assert.assertTrue(codecSelfHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(codec2SelfHandler instanceof ExceptionEncodingFailureHandler);
        Assert.assertTrue(oneToTwoHandler instanceof LoggingEncodingFailureHandler);
        Assert.assertTrue(twoToOneHandler instanceof LoggingEncodingFailureHandler);
    }

}
