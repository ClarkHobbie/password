package com.ltsllc.password;

public class AnyCharacterAllLowerCase extends Candidate {
    public static final String NAME = "AnyCharacterAllLowerCase";

    @Override
    public char generate() {
        int index = random.nextInt(ANY_CHARACTERS_ALL_LOWER_CASE.length());
        return ANY_CHARACTERS_ALL_LOWER_CASE.charAt(index);
    }
}
