package com.ltsllc.password;

import java.util.Random;

public abstract class Candidate {
    public static final Object UPPERCASE_NAME = "Uppercase";
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final Object LOWERCASE_NAME = "Lowercase";
    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static final Object NUMBER_NAME = "Number";
    public static final String NUMBER_CHARACTERS = "0123456789";

    public static final Object SYMBOL_NAME = "Symbol";
    public static final String SYMBOL_CHARACTERS = "!@#$%^&*()";

    public static final Object ALL_SYMBOLS_NAME = "AllSymbols";
    public static final String ALL_SYMBOLS_CHARACTERS = "!@#$%^&*()-_=+[{]}?,<.>";

    public static final Object ANY_NAME = "Any";
    public static final String ANY_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    public static final Object NAME_SEPARATOR = ",";

    public static final String ANY_LOWER_CASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890!#$%^&*()";

    public static Candidate ourInstance;

    public static Random random = new Random();
    public static boolean initialized = false;

    static {
        Candidate.ourInstance = new UnknownCharacter();
    }


    public static Candidate parseCandidate(String s) {
        if (s.equalsIgnoreCase(UPPERCASE_NAME.toString()))
            return new UpperCaseCharacter();
        else if (s.equalsIgnoreCase(LOWERCASE_NAME.toString()))
            return new LowerCaseCharacter();
        else if (s.equalsIgnoreCase(NUMBER_NAME.toString()))
            return new NumberCharacter();
        else if (s.equalsIgnoreCase(SYMBOL_NAME.toString()))
            return new SymbolCharacter();
        else if (s.equalsIgnoreCase(ALL_SYMBOLS_NAME.toString()))
            return new AllSymbolsCharacter();
        else if (s.equalsIgnoreCase(ANY_NAME.toString()))
            return new AnyCharacter();

        return new UnknownCharacter();
    }

    public static Candidate[] parse(String string) {
        String[] strings = string.split(NAME_SEPARATOR.toString());
        Candidate[] candidates = new Candidate[strings.length];

        for (int i =  0; i < strings.length; i++) {
            candidates[i] = parseCandidate(strings[i]);
        }

        return candidates;
    }

    public abstract char generate();
}
