package com.ltsllc.password;

public class AnyCharacter extends Candidate{
    @Override
    public char generate() {
        int index = Candidate.random.nextInt(ANY_CHARACTERS.length());
        return ANY_CHARACTERS.charAt(index);
    }
}
