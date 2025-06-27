package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BagIteratorTest {

    @Test
    void next() {
        Bag<Candidate> bag = new Bag<Candidate>();
        bag.add(new AnyCharacter());
        assert (bag.notEmpty());
        Iterator<Candidate> iter = bag.iterator();
        assert (iter.hasNext());
        Candidate candidate = iter.next();
        assert (candidate != null);
        assert (!bag.notEmpty());
    }

    @Test
    void hasNext() {
        Bag<Candidate> bag = new Bag<Candidate>();
        Iterator<Candidate> iterator = bag.iterator();
        assert (!iterator.hasNext());
        bag.add (new AnyCharacter());
        assert (iterator.hasNext());
    }
}