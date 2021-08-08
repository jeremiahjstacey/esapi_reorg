package org.owasp.esapi.codec.encode;

import java.nio.charset.Charset;
import java.util.Map.Entry;

import org.owasp.esapi.codec.Encoder;

public class ASCIIEncoder implements Encoder<Character> {

	@Override
	public String encode(Character input) {
		return String.valueOf((int) input);
	}
	
	

}
