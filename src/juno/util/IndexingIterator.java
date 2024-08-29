package juno.util;

import java.util.Iterator;

public class IndexingIterator<T> implements Iterator<IndexedValue<T>> {
    
    private final Iterator<T> iterator;
    private int index = 0;

    public IndexingIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public IndexedValue<T> next() {
        return new IndexedValue<T>(index++, iterator.next());
    }
}
