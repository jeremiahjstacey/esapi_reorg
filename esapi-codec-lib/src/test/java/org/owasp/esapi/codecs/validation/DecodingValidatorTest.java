package org.owasp.esapi.codecs.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.owasp.esapi.codec.Decoder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class DecodingValidatorTest {
	private DecodingValidator uit;
	
	private Decoder mockDecoder;
	
	@Before
	public void setup() {
		mockDecoder = Mockito.mock(Decoder.class);
		uit = new DecodingValidator(mockDecoder, "unit test");
	}
	
	@Test
	public void testRiskReturnOnMismatch() {
		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		
		ValidationResponse response = uit.validate("test");
		
		Assert.assertEquals(ValidationStatus.RISK, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("unit test", response.getResponseDetail());
		Assert.assertNull(response.getException());
		
		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
	}
	
	
	@Test
	public void testPassOnNoMutation() {
		Mockito.when(mockDecoder.decode("test")).thenReturn("test");
		
		ValidationResponse response = uit.validate("test");
		
		Assert.assertEquals(ValidationResponse.OK, response);
		
		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
	}
}
