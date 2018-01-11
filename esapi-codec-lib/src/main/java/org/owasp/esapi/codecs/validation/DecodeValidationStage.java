package org.owasp.esapi.codecs.validation;

import org.owasp.esapi.codec.Decoder;
import org.owasp.esapi.validation.ValidationDataStage;

/**
 * A stage of a validation stream when input is passed through a Decoder for downstream use.
 */
public class DecodeValidationStage implements ValidationDataStage<String, String> {
	
	/** Decoder reference.*/
	private final Decoder<String> decoder;
	
	/**
	 * Constructor.
	 * @param decoder used to transform data.
	 */
	public DecodeValidationStage(Decoder<String> decoder) {
		this.decoder = decoder;
	}
	
	@Override
	public String prepareData(String input) {
		return decoder.decode(input);
	}	
}
