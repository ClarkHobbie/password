package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

class BagTest {

    @Test
    void add() {
        Bag<Character> bag = new Bag<Character>();
        bag.add('C');

        assert (bag.contains('C'));
    }

    @Test
    void next() {
        Bag<Candidate> bag = new Bag<Candidate>();
        bag.add(new SymbolCharacter());
        bag.add(new AnyCharacter());

        assert(bag.size() >= 2);
        Candidate c = bag.next();
        assert (c != null);
        assert(bag.notEmpty());
        c = bag.next();
        assert (c != null);
        assert (bag.isEmpty());

    }

    @Test
    void notEmpty() {

        Bag<Candidate> bag = new Bag<Candidate>();
        assert (!bag.notEmpty());
        bag.add(new AnyCharacter());
        assert (bag.notEmpty());
    }

    @Test
    void iterator() {
        Bag<Candidate> bag = new Bag<Candidate>();
        Iterator<Candidate> i = bag.iterator();
        assert(i != null);
    }
}