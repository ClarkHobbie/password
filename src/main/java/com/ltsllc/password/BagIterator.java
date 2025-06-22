package com.ltsllc.password;

import java.util.Iterator;

public class BagIterator<T> implements Iterator<T> {
    Bag<T> bag = null;

    public BagIterator (Bag<T> bag) {
        this.bag = bag;
    }

    @Override
    public T next() {
        return bag.next();
    }

    @Override
    public boolean hasNext () {
        return bag.list.isEmpty();
    }
}
