package org.owasp.esapi.codec.encode;

import org.owasp.esapi.codec.Encoder;

public class ContextEncoder<T> implements Encoder<T> {
	private final Encoder<T> delegate;
	private final String returnFormat;
	
	public ContextEncoder(String prefix, String suffix, Encoder<T> delegate) {
		this.delegate = delegate;
		this.returnFormat = prefix +"%s" + suffix;
				
	}
	
	@Override
	public String encode(T input) {
		CharSequence delegateProcess = delegate.encode(input);		
		return delegateProcess == null ? null : String.format(returnFormat, delegateProcess);
	}

}
