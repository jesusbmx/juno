package juno.util;

import java.util.Iterator;

public class IndexingIterable<T> implements Iterable<IndexedValue<T>> {
    
    private final Iterator<T> iterator;

    public IndexingIterable(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<IndexedValue<T>> iterator() {
        return new IndexingIterator<T>(iterator);
    }
}
