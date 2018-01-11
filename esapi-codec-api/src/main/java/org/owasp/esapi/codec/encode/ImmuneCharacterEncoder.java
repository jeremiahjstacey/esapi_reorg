package org.owasp.esapi.codec.encode;

import java.util.Set;

import org.owasp.esapi.codec.Encoder;

public class ImmuneCharacterEncoder implements Encoder<Character> {

	private final Encoder<Character> delegate;
	private final Set<Character> immunitySet;
	
	public ImmuneCharacterEncoder(Encoder<Character> delegate, Set<Character> immuneCharacters) {
		this.delegate = delegate;
		this.immunitySet = immuneCharacters;
	}
	
	@Override
	public String encode(Character input) {
		String encoding;
		if (immunitySet.contains(input)) {
			encoding = input.toString();
		} else {
			encoding = delegate.encode(input);
		}
		return encoding;
	}

}
