package com.ltsllc.password;

public class SymbolCharacter extends Candidate{
    public static final String NAME = Candidate.SYMBOL_NAME.toString();

    @Override
    public char generate() {
        int index = Candidate.random.nextInt(Candidate.SYMBOL_CHARACTERS.length());
        return Candidate.SYMBOL_CHARACTERS.charAt(index);
    }
}
