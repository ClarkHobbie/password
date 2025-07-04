package com.ltsllc.password;

import java.util.Iterator;

/******************************************************************************
 * An {@link Iterator} for a bag.
 *
 * The items in the bag are returned by {@link #next()} are in a random order.
 *
 * @param <T> The type of the items.
 */
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
        return !bag.list.isEmpty();
    }
}
