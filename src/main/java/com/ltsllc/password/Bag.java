package com.ltsllc.password;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/******************************************************************************
 * A class in which the items are removed in a random order.
 *
 * Items can be added in any order, but they will be returned in a random
 * order.  Calling {@link Bag#next} results in the item being removed from the
 * bag, unlike {@link java.util.List#get(int)}.
 *
 * @param <T> the type of the items
 */
public class Bag<T> implements Iterable<T> {
    protected ArrayList list = new ArrayList();
    protected Random random  = new Random();

    /**************************************************************************
     * Add an item to the bag.
     *
     * @param t The item to add.
     */
    public void add(T t) {
        list.add(t);
    }

    /**************************************************************************
     * Get a random item from the Bag.
     *
     * Note that the order of the items cannot be determined ahead of time.
     * Note also that this will remove the item from the bag.
     *
     * @return The next item.
     */
    public T next () {
        int index = random.nextInt(list.size());
        return (T) list.remove(index);
     }

     public boolean notEmpty() {
        return !list.isEmpty();
     }

     public int size() {
        return list.size();
     }


    @Override
    public Iterator<T> iterator() {
        return new BagIterator<T>(this);
    }

    public boolean contains(T t) {
        return list.contains(t);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
