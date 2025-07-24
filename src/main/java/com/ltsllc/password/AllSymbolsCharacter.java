package com.ltsllc.password;

/**
 * A {@link Candidate} who takes it's characters from {@link #ALL_SYMBOLS_CHARACTERS
 * }
 */
public class AllSymbolsCharacter extends Candidate {
    public char generate() {
        int index = Candidate.random.nextInt(Candidate.ALL_SYMBOLS_CHARACTERS.length());
        return Candidate.ALL_SYMBOLS_CHARACTERS.charAt(index);
    }
}
