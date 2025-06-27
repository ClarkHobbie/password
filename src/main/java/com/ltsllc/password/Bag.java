package com.ltsllc.password;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Bag<T> implements Iterable<T> {
    protected ArrayList list = new ArrayList();
    protected Random random  = new Random();

    public void add(T t) {
        list.add(t);
    }

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
