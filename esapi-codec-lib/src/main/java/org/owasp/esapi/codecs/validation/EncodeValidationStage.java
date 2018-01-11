package org.owasp.esapi.codecs.validation;

import org.owasp.esapi.codec.Encoder;
import org.owasp.esapi.validation.ValidationDataStage;

public class EncodeValidationStage implements ValidationDataStage<String, String> {
	private final Encoder<String> encoder;
	
	public EncodeValidationStage(Encoder<String> encoder) {
		this.encoder = encoder;
	}
	@Override
	public String prepareData(String input) {
		return encoder.encode(input);
	}
}
