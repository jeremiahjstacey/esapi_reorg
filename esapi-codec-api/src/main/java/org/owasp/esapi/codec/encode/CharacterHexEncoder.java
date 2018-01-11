package org.owasp.esapi.codec.encode;

import org.owasp.esapi.codec.Encoder;

public class CharacterHexEncoder implements Encoder<Character> {
	public static final byte LETTERS = 90;
	public static final byte DIGITS = 92;
	
	

	@Override
	public String encode(Character input) {
		return Integer.toHexString(input.charValue());
	}

	public static void main(String[] args) {
		
		
			System.out.println("HEX:");
			for (int x = 0; x < 257 ; x++) {
				if (Character.isLetter(x) || Character.isDigit(x)) {
					System.out.println(String.format("\t%s = %s  (%s)", (char)x, Integer.toHexString(x), x ));
				}
			}
		
		System.out.println("OCTAL:");
		for (int x = 0; x < 257 ; x++) {
			if (Character.isLetter(x) || Character.isDigit(x)) {
				System.out.println(String.format("\t%s = %s  (%s)", (char)x, Integer.toOctalString(x), x ));
			}
		}
	}
}
