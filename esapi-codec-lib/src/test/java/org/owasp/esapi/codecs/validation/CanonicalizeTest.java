package org.owasp.esapi.codecs.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.owasp.esapi.codec.Decoder;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;


public class CanonicalizeTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testNoConstruct() throws Exception {
		expectedException.expect(InvocationTargetException.class);
		expectedException.expectCause(Is.isA(UnsupportedOperationException.class));

		Constructor<Canonicalize> constructor = Canonicalize.class.getDeclaredConstructor(new Class[0]);
		constructor.setAccessible(true);
		constructor.newInstance(new Object[0]);
	}


	@Test
	public void testMultipleEncodingValidatorPasses() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder.decode("good")).thenReturn("good");

		Validator<String> multiEncodeCheck = Canonicalize.multiEncodingValidator(mockDecoder);

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationResponse.OK, response);

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder, Mockito.times(1)).decode("good");
	}

	@Test
	public void testMultipleEncodingValidatorFails() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder.decode("good")).thenReturn("bad");

		Validator<String> multiEncodeCheck = Canonicalize.multiEncodingValidator(mockDecoder);

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationStatus.RISK, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("Multiple Encoding Detected", response.getResponseDetail());
		Assert.assertNull(response.getException());

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder, Mockito.times(1)).decode("good");
	}

	@Test
	public void testMixedEncodingValidatorPasses() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);
		Decoder mockDecoder2 = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder2.decode("good")).thenReturn("good");

		Validator<String> multiEncodeCheck = Canonicalize.mixEncodingValidator(mockDecoder, mockDecoder2);

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationResponse.OK, response);

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder2, Mockito.times(1)).decode("good");
	}

	@Test
	public void testMixedEncodingValidatorFails() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);
		Decoder mockDecoder2 = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder2.decode("good")).thenReturn("bad");

		Validator<String> multiEncodeCheck = Canonicalize.mixEncodingValidator(mockDecoder, mockDecoder2);

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationStatus.RISK, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("Mixed Encoding Detected", response.getResponseDetail());
		Assert.assertNull(response.getException());

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder2, Mockito.times(1)).decode("good");
	}


	@Test
	public void testMixedEncodingChainValidatorPasses() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);
		Decoder mockDecoder2 = Mockito.mock(Decoder.class);
		Decoder mockDecoder3 = Mockito.mock(Decoder.class);
		Decoder mockDecoder4 = Mockito.mock(Decoder.class);
		Decoder mockDecoder5 = Mockito.mock(Decoder.class);
		Decoder mockDecoder6 = Mockito.mock(Decoder.class);
		Decoder mockDecoder7 = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder2.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder3.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder4.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder5.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder6.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder7.decode("good")).thenReturn("good");

		Validator<String> multiEncodeCheck = Canonicalize.mixEncodingValidator(mockDecoder, Arrays.asList(mockDecoder2,mockDecoder3,mockDecoder4,mockDecoder5,mockDecoder6,mockDecoder7));

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationResponse.OK, response);

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder2, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder3, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder4, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder5, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder6, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder7, Mockito.times(1)).decode("good");
	}

	@Test
	public void testMixedEncodingChainValidatorFails() {
		Decoder mockDecoder = Mockito.mock(Decoder.class);
		Decoder mockDecoder2 = Mockito.mock(Decoder.class);
		Decoder mockDecoder3 = Mockito.mock(Decoder.class);
		Decoder mockDecoder4 = Mockito.mock(Decoder.class);
		Decoder mockDecoder5 = Mockito.mock(Decoder.class);
		Decoder mockDecoder6 = Mockito.mock(Decoder.class);
		Decoder mockDecoder7 = Mockito.mock(Decoder.class);

		Mockito.when(mockDecoder.decode("test")).thenReturn("good");
		Mockito.when(mockDecoder2.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder3.decode("good")).thenReturn("good");
		Mockito.when(mockDecoder4.decode("good")).thenReturn("good");
		//This stops the chain
		Mockito.when(mockDecoder5.decode("good")).thenReturn("bad");

		Validator<String> multiEncodeCheck = Canonicalize.mixEncodingValidator(mockDecoder, Arrays.asList(mockDecoder2,mockDecoder3,mockDecoder4,mockDecoder5,mockDecoder6,mockDecoder7));

		ValidationResponse response = multiEncodeCheck.validate("test");

		Assert.assertEquals(ValidationStatus.RISK, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("Mixed Encoding Detected", response.getResponseDetail());
		Assert.assertNull(response.getException());

		Mockito.verify(mockDecoder, Mockito.times(1)).decode("test");
		Mockito.verify(mockDecoder2, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder3, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder4, Mockito.times(1)).decode("good");
		Mockito.verify(mockDecoder5, Mockito.times(1)).decode("good");
		//NOT CALLED
		Mockito.verify(mockDecoder6, Mockito.times(0)).decode("good");
		Mockito.verify(mockDecoder7, Mockito.times(0)).decode("good");
	}
}
