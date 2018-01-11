package org.owasp.esapi.codecs.canonicalization.composed;

import java.util.ArrayList;
import java.util.List;

import org.owasp.esapi.codec.canonicalization.Canonicalizer;

/**
 * FIXME: Document intent of class. General Function, purpose of creation, intended feature, etc.
 * Why do people care this exists?
 * 
 * @author Jeremiah
 * @since Jan 3, 2018
 */
public class ComposedCanonicalizer implements Canonicalizer {

    private final List<EncodingTester> testers;

    /**
     * 
     */
    public ComposedCanonicalizer(List<EncodingTester> encodingTesters) {
        this.testers = new ArrayList<>(encodingTesters);
    }

    /** {@inheritDoc} */
    @Override
    public String canonicalize(String input) {
        if (input == null) {
            return null;
        }
        String rval = input;
        for (EncodingTester tester : testers) {
            String decoded = tester.check(input);
            if (decoded != null && rval.equals(input) && !rval.equals(decoded)) {
                rval = decoded;
            }
        }

        return rval;
    }

}
