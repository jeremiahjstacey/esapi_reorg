package org.owasp.esapi.codec;

public interface Encoder<T> {
   String encode(T input);
}
