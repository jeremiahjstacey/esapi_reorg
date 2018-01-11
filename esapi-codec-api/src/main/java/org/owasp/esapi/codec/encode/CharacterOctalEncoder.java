package org.owasp.esapi.codec.encode;

import org.owasp.esapi.codec.Encoder;

public class CharacterOctalEncoder implements Encoder<Character> {

	@Override
	public String encode(Character input) {
		return Integer.toOctalString(input.charValue());
	}

}
