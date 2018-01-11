package org.owasp.esapi.codec.encode;

import org.owasp.esapi.codec.Encoder;

public class ASCIIEncoder implements Encoder<Character> {

	@Override
	public String encode(Character input) {
		return String.valueOf((int) input);
	}

}
