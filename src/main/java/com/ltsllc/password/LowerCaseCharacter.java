package com.ltsllc.password;

/**
 * A {@link Candidate} that takes it's characters from {@link #LOWERCASE_CHARACTERS}.
 */
public class LowerCaseCharacter extends Candidate{
    public static final String NAME = Candidate.LOWERCASE_NAME.toString();

    @Override
    public char generate() {
        int index = Candidate.random.nextInt(LOWERCASE_CHARACTERS.length());
        return Candidate.LOWERCASE_CHARACTERS.charAt(index);
    }
}
