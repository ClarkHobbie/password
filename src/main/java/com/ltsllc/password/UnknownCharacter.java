package com.ltsllc.password;

/**
 * A {@link Candidate} that is unknown.
 */
public class UnknownCharacter extends Candidate{
    @Override
    public char generate() {
        throw new RuntimeException("Not implemented");
    }
}
