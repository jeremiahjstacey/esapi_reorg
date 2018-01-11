package org.owasp.esapi.codec;

public interface Decoder<T> {
	String decode(T input);
}
