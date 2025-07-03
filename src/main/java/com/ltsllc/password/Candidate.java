package com.ltsllc.password;

import java.util.Random;

/******************************************************************************
 * A candidate character for a password.
 *
 * This abstract class defines the set of characters from which a password
 * character is chosen.  So the {@link com.ltsllc.password.NumberCharacter}
 * class represents the set [1234567890].
 */
public abstract class Candidate {
    public static final Object UPPERCASE_NAME = "Uppercase";
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final Object LOWERCASE_NAME = "LowerCase";
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

    public static final Object ANY_LOWER_CASE_NAME = "AnyLowerCase";
    public static final String ANY_LOWER_CASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890!#$%^&*()";

    public static Candidate ourInstance;

    public static Random random = new Random();

    static {
        Candidate.ourInstance = new UnknownCharacter();
    }

    /**************************************************************************
     * Convert a String to the {@link Candidate} it represents.
     *
     * The method assumes that the input String is one of NAMEs defined by
     * {@link Candidate}.  If the method does not recognize the input string
     * it returns {@link UnknownCharacter}.
     *
     * @param s The input string.
     * @return The corresponding {@link Candidate}
     */
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
        else if (s.equalsIgnoreCase(ANY_LOWER_CASE_NAME.toString()))
            return new AnyLowerCaseCharacter();


        return new UnknownCharacter();
    }

    /**************************************************************************
     * Convert a string to a sequence of {@link Candidate} instances.
     *
     * The method assumes that the string takes the form of a sequence of
     * {@link Candidate} NAMEs separated by {@link Candidate#NAME_SEPARATOR}
     * characters.
     *
     * @param string The string to convert.
     * @return The sequence of {@link Candidate}s the input string represents.
     */
    public static Candidate[] parse(String string) {
        String[] strings = string.split(NAME_SEPARATOR.toString());
        Candidate[] candidates = new Candidate[strings.length];

        for (int i =  0; i < strings.length; i++) {
            candidates[i] = parseCandidate(strings[i]);
        }

        return candidates;
    }

    /**************************************************************************
     * Create a character taken from the set of characters the {@link Candidate}
     * defines.
     *
     * @return The resulting character.
     */
    public abstract char generate();
}
