package org.owasp.esapi.codecs.validation;

import org.owasp.esapi.codec.Decoder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class DecodingValidator implements Validator<String> {

	private Decoder<String> decoder;
	private String context;
	
	public DecodingValidator(Decoder<String> decoder, String validationContext) {
		this.decoder = decoder;
		this.context = validationContext;
	}
	
	@Override
	public ValidationResponse validate(String data) {
		ValidationResponse rval = ValidationResponse.OK;		
		//Expected that the incoming data has already been decoded by some other source.
		String toCheck = decoder.decode(data);
		if (!data.equals(toCheck)){
			rval = new ValidationResponse(ValidationStatus.RISK, context);
		}
		return rval;
	}

}
