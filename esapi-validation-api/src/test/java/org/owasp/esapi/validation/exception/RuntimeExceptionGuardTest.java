package org.owasp.esapi.validation.exception;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;


public class RuntimeExceptionGuardTest {
	private RuntimeExceptionGuard<Object> uit;

	@Test
	public void testPassthrough() {
		Validator<Object> delegate = Mockito.mock(Validator.class);
		Mockito.when(delegate.validate(Matchers.any())).thenReturn(ValidationResponse.OK);
		
		uit = new RuntimeExceptionGuard<>(delegate);
		
		ValidationResponse response = uit.validate(new Object());
		Assert.assertEquals(response, ValidationResponse.OK);
		
		Mockito.verify(delegate, Mockito.times(1)).validate(Matchers.any());
	}
	

	@Test
	public void testRuntimeException() {
		RuntimeException ex = new RuntimeException("");
		Validator<Object> delegate = Mockito.mock(Validator.class);
		Mockito.when(delegate.validate(Matchers.any())).thenThrow(ex);
		
		uit = new RuntimeExceptionGuard<>(delegate);
		
		ValidationResponse response = uit.validate(new Object());
		
		Assert.assertEquals(ValidationStatus.ERROR, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("", response.getResponseDetail());
		Assert.assertEquals(ex, response.getException());
		
		Mockito.verify(delegate, Mockito.times(1)).validate(Matchers.any());
	}
}
