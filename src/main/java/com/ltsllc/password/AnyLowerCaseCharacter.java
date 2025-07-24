package com.ltsllc.password;

/**
 * A {@link Candidate} that takes it's characters from {@link #ANY_LOWER_CASE_CHARACTERS}.
 */
public class AnyLowerCaseCharacter extends Candidate {
    public static final String NAME = ANY_LOWER_CASE_NAME.toString();

    @Override
    public char generate() {
        int index = random.nextInt(ANY_LOWER_CASE_CHARACTERS.length());
        return ANY_LOWER_CASE_CHARACTERS.charAt(index);
    }
}
