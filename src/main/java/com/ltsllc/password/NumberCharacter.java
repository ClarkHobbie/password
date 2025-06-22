package com.ltsllc.password;

public class NumberCharacter extends Candidate{
    public static final String NAME = Candidate.NUMBER_NAME.toString();

    @Override
    public char generate() {
        int index = Candidate.random.nextInt(NUMBER_CHARACTERS.length());
        return Candidate.NUMBER_CHARACTERS.charAt(index);
    }

    public boolean contains(String string) {
        StringBuilder builder = new StringBuilder();
        builder.append(string);
        return Candidate.NUMBER_CHARACTERS.contains(builder.toString());
    }
}
