package com.ltsllc.password;

public class AllSymbolsCharacter extends Candidate {
    public char generate() {
        int index = Candidate.random.nextInt(Candidate.ALL_SYMBOLS_CHARACTERS.length());
        return Candidate.ALL_SYMBOLS_CHARACTERS.charAt(index);
    }
}
