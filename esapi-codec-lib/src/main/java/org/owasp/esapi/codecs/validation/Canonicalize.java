package org.owasp.esapi.codecs.validation;

import java.util.ArrayList;
import java.util.List;

import org.owasp.esapi.codec.Decoder;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.Validators;

public class Canonicalize {
	
	public static Validator<String> multiEncodingValidator(Decoder<String> decoder) {
		return buildCheck(decoder, decoder, "Multiple Encoding Detected");
	}
	
	public static Validator<String> mixEncodingValidator(Decoder<String> first, Decoder<String> second) {
		return buildCheck(first, second, "Mixed Encoding Detected");	
	}
	
	public static Validator<String> mixEncodingValidator(Decoder<String> primary, List<Decoder<String>> mixedChecks) {
		DecodeValidationStage primaryDecode = new DecodeValidationStage(primary);
		
		List<Validator<String>> mixedValidtors = new ArrayList<>();
		for (Decoder<String> decoder: mixedChecks) {
			mixedValidtors.add(new DecodingValidator(decoder, "Mixed Encoding Detected")); 
		}
		
		Validator<String> allMixedChecks = Validators.and(mixedValidtors);
		
		return Validators.chain(allMixedChecks, primaryDecode);
		
	}
	
	public static Validator<String> buildCheck(Decoder<String> first, Decoder<String> second, String context) {
		DecodeValidationStage primaryDecode = new DecodeValidationStage(first);
		DecodingValidator decodingValidator = new DecodingValidator(second, context);
		
		return Validators.chain(decodingValidator, primaryDecode);	
	}
	
	
	/** Private Utility Constructor. */
	private Canonicalize() {
		throw new UnsupportedOperationException("Cannot construct instance of Canonicalize Utility");
	}
}
