package org.owasp.esapi.codecs.canonicalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.owasp.esapi.codec.canonicalization.Canonicalizer;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.composed.ComposedCanonicalizer;
import org.owasp.esapi.codecs.canonicalization.composed.EncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.MixedEncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.MultipleEncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.NullInputGuard;

/**
 * Builder implementation for isolating  
 * @author Jeremiah
 * @since Jan 1, 2018
 *
 */
public class CanonicalizationBuilder {
    
    private List<Codec> codecs = Collections.emptyList();
    private boolean restrictMultipleEncoding = true;
    private boolean restrictMixedEncoding = true;
    
    /*
     * TODO:
     *   1) Add new fields for the Fail handlers of mixed & multi events.
     *   2) Use the new handlers in the composed.
     *   3) Delete the boolean setting options when Original/Updated are OBE'd.
     */

    public CanonicalizationBuilder setRestrictMultipleEncoding (boolean multipleEncodingRestricted) {
        this.restrictMultipleEncoding = multipleEncodingRestricted;
        return this;
    }
    
    public CanonicalizationBuilder setRestrictMixedEncoding (boolean mixedEncodingRestricted) {
        this.restrictMixedEncoding = mixedEncodingRestricted;
        return this;
    }
    
    public CanonicalizationBuilder setDelegateCodecs(List<Codec> delegates) {
        //shallow copy to prevent external list mutation.
        this.codecs = new ArrayList<>(delegates);
        return this;
    }

    public Canonicalizer build() {
        EncodingFailureHandler multipleEncodingFailHandler = restrictMultipleEncoding ? new ExceptionEncodingFailureHandler() : new LoggingEncodingFailureHandler();
        EncodingFailureHandler mixedEncodingFailHandler = restrictMixedEncoding ? new ExceptionEncodingFailureHandler() : new LoggingEncodingFailureHandler();
        
        List<EncodingTester> testers = new ArrayList<>();
        
        int codecIndex = 0;
        while (codecIndex < codecs.size()) {
            Codec codec1 = codecs.get(codecIndex);
            EncodingTester multiEncodeCheck = new MultipleEncodingTester(codec1, multipleEncodingFailHandler); 
            testers.add(NullInputGuard.guard(multiEncodeCheck));
            for (int mixedIndex = codecIndex+1 ; mixedIndex < codecs.size(); mixedIndex ++) {
                Codec codec2 = codecs.get(mixedIndex);
                
                EncodingTester firstThenSecondMixedTest = new MixedEncodingTester(codec1, codec2, mixedEncodingFailHandler);
                EncodingTester secondThenFirstMixedTest = new MixedEncodingTester(codec2, codec1, mixedEncodingFailHandler);
                
                testers.add(NullInputGuard.guard(firstThenSecondMixedTest));
                testers.add(NullInputGuard.guard(secondThenFirstMixedTest));
            }
            codecIndex ++;
        }
        
        
        return new ComposedCanonicalizer(testers);
    }
}
