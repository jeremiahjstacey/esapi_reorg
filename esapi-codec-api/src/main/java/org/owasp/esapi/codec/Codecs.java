package org.owasp.esapi.codec;

import org.owasp.esapi.codec.encode.CharacterHexEncoder;
import org.owasp.esapi.codec.encode.ContextEncoder;

public enum Codecs {
	CSS("//", " ", new CharacterHexEncoder());
	;
	
	Encoder<?> encoder;
	private Codecs(String prefix, String suffix, Encoder<?> raw) {
		encoder = new ContextEncoder<>(prefix, suffix, raw);
	}
}
