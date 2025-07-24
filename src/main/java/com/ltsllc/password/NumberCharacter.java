package com.ltsllc.password;

/**
 * A {@link Candidate} that takes it's characters from {@link #NUMBER_CHARACTERS}.
 */
public class NumberCharacter extends Candidate{
    public static final String NAME = Candidate.NUMBER_NAME.toString();

    @Override
    public char generate() {
        int index = Candidate.random.nextInt(NUMBER_CHARACTERS.length());
        return Candidate.NUMBER_CHARACTERS.charAt(index);
    }

    public boolean in(String string) {
        for (char c : Candidate.NUMBER_CHARACTERS.toCharArray()) {
            for (char d : string.toCharArray()) {
                if (c == d) {
                    return true;
                }
            }
        }

        return false;
    }
}
