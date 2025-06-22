package com.ltsllc.password;

import org.junit.jupiter.api.Test;

class BagTest {

    @Test
    void add() {
        Bag<Character> bag = new Bag<Character>();
        bag.add('C');

        assert (bag.contains('C'));
    }

    @Test
    void next() {
    }

    @Test
    void notEmpty() {
    }

    @Test
    void iterator() {
    }
}