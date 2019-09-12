package ru.byprogminer.Lab2_Web.utility;

import java.util.Deque;
import java.util.Iterator;

public class ReversedDequeIterable<T> implements Iterable<T> {

    private final Deque<T> deque;

    public ReversedDequeIterable(Deque<T> deque) {
        this.deque = deque;
    }

    @Override
    public Iterator<T> iterator() {
        return deque.descendingIterator();
    }
}
