package com.ltsllc.password;

/**
 * A {@link Candidate} that takes it's characters from {@link #ANY_CHARACTERS}.
 */
public class AnyCharacter extends Candidate{
    @Override
    public char generate() {
        int index = Candidate.random.nextInt(ANY_CHARACTERS.length());
        return ANY_CHARACTERS.charAt(index);
    }
}
