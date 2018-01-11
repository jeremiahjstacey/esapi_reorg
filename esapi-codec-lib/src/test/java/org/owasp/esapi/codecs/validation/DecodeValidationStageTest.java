package org.owasp.esapi.codecs.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.owasp.esapi.codec.Decoder;

public class DecodeValidationStageTest {
	private DecodeValidationStage uit;
	
	private Decoder mockDecoder;
	
	@Before
	public void setup() {
		mockDecoder = Mockito.mock(Decoder.class);
		uit = new DecodeValidationStage(mockDecoder);
	}
	
	@Test
	public void thing() {
		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		
		String rval = uit.prepareData("test");
		Assert.assertEquals("good",rval);
		
		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
	}
}
