package org.owasp.esapi.codecs.canonicalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.canonicalization.composed.MixedEncodingTester;
import org.owasp.esapi.codecs.canonicalization.composed.MultipleEncodingTester;
import org.owasp.esapi.validation.ResultValidator;
import org.owasp.esapi.validation.ResultValidators;


public class CanonicalizationBuilder {
    
    private List<Codec> codecs = Collections.emptyList();
    private boolean restrictMultipleEncoding = true;
    private boolean restrictMixedEncoding = true;
    
    
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

    public ResultValidator<String> build() {
        List<ResultValidator<String>> testers = new ArrayList<>();
        
        int codecIndex = 0;
        while (codecIndex < codecs.size()) {
            Codec codec1 = codecs.get(codecIndex);
            ResultValidator<String> multiEncodeCheck = new MultipleEncodingTester(codec1); 
        
            testers.add(multiEncodeCheck);
            for (int mixedIndex = codecIndex+1 ; mixedIndex < codecs.size(); mixedIndex ++) {
                Codec codec2 = codecs.get(mixedIndex);
                
                ResultValidator<String> firstThenSecondMixedTest = new MixedEncodingTester(codec1, codec2);
                ResultValidator<String> secondThenFirstMixedTest = new MixedEncodingTester(codec2, codec1);
               
                testers.add(firstThenSecondMixedTest);
                testers.add(secondThenFirstMixedTest);
            }
            codecIndex ++;
        }
        
        return ResultValidators.and(testers);
    }
}
