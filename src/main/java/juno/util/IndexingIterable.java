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
    
    public static class IndexingIterator<T> implements Iterator<IndexedValue<T>> {
    
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

}
