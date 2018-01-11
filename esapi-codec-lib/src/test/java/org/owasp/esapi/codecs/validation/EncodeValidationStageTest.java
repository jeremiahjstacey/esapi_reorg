package org.owasp.esapi.codecs.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.owasp.esapi.codec.Encoder;

public class EncodeValidationStageTest {
	private EncodeValidationStage uit;
	
	private Encoder mockEncoder;
	
	@Before
	public void setup() {
		mockEncoder = Mockito.mock(Encoder.class);
		uit = new EncodeValidationStage(mockEncoder);
	}
	
	@Test
	public void thing() {
		Mockito.when(mockEncoder.encode("test")).thenReturn("good");
		
		String rval = uit.prepareData("test");
		Assert.assertEquals("good",rval);
		
		Mockito.verify(mockEncoder, Mockito.times(1)).encode("test");
	}
}
