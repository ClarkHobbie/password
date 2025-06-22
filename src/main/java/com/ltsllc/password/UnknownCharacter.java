package com.ltsllc.password;

public class UnknownCharacter extends Candidate{
    @Override
    public char generate() {
        throw new RuntimeException("Not implemented");
    }
}
