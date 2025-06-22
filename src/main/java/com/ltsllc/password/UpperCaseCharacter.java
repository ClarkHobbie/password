package com.ltsllc.password;

public class UpperCaseCharacter extends Candidate{
    public static final String NAME = Candidate.UPPERCASE_NAME.toString();

    @Override
    public char generate() {
        int index = Candidate.random.nextInt(UPPERCASE_CHARACTERS.length());
        return Candidate.UPPERCASE_CHARACTERS.charAt(index);
    }
}
