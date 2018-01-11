package org.owasp.esapi.codec;

import java.util.function.Function;
import java.util.function.Predicate;

import nu.xom.jaxen.expr.PredicateSet;

public class Fiddle {
public static void main(String[] args) {
	
	
}

private static class HexEncoder implements Function<String, String> {

	@Override
	public String apply(String t) {
		IsPrintableCharacter printable = new IsPrintableCharacter();
		
		String cpy = t;
		for ( char c = 0; c < 0xFF; c++ ) {
			if (!printable.test(c)) {
				t = t.replace(c+"", "&#x" + Integer.toHexString(c) + ";");
			}
		}
		
		
		return cpy;
	}
	
}

private static class IsPrintableCharacter implements Predicate<Character> {

	@Override
	public boolean test(Character t) {
		IsHexNumber isNum = new IsHexNumber();
		IsCapitalLetter isCap = new IsCapitalLetter();
		IsLowerCaseLetter isLower = new IsLowerCaseLetter();
		
		return isNum.test(t) || isCap.test(t) || isLower.test(t);
	}
	
}


private static class IsHexNumber implements Predicate<Character> {

	@Override
	public boolean test(Character t) {
		// is 0 to 9 in hex.
		return t >= 0x30 && t <= 0x39;
	}
	
}

private static class IsCapitalLetter implements Predicate<Character> {

	@Override
	public boolean test(Character t) {
		// is A to Z in hex.
		return t >= 0x41 && t <= 0x5A;
	}
	
}

private static class IsLowerCaseLetter implements Predicate<Character> {

	@Override
	public boolean test(Character t) {
		// is a to z in hex.
		return t >= 0x61 && t <= 0x7A;
	}
	
}

}
