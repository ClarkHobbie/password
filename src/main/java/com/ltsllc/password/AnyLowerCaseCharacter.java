package com.ltsllc.password;

public class AnyLowerCaseCharacter extends Candidate {
    public static final String NAME = ANY_LOWER_CASE_NAME.toString();

    @Override
    public char generate() {
        int index = random.nextInt(ANY_LOWER_CASE_CHARACTERS.length());
        return ANY_LOWER_CASE_CHARACTERS.charAt(index);
    }
}
