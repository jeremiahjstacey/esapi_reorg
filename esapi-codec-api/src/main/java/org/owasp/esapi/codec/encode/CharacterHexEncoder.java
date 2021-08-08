package org.owasp.esapi.codec.encode;

import org.owasp.esapi.codec.Encoder;

public class CharacterHexEncoder implements Encoder<Character> {
	@Override
	public String encode(Character input) {
		return Integer.toHexString(input.charValue());
	}

}
