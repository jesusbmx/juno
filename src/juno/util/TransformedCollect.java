package juno.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TransformedCollect<F, T> extends AbstractCollection<T> {
  final Collection<F> fromCollection;
  final Fun<? super F, ? extends T> function;

  public TransformedCollect(Collection<F> fromCollection, Fun<? super F, ? extends T> function) {
    this.fromCollection = fromCollection;
    this.function = function;
  }

  @Override public void clear() {
    fromCollection.clear();
  }

  @Override public boolean isEmpty() {
    return fromCollection.isEmpty();
  }

  @Override public Iterator<T> iterator() {
    return new TransformedIterator<F, T>(fromCollection.iterator()) {
      @Override T transform(F from) {
        return function.apply(from);
      }
    };
  }

  @Override public int size() {
    return fromCollection.size();
  }

  public List<T> toList() {
    return new ArrayList<T>(this);
  }
  
  public Set<T> toSet() {
    return new HashSet<T>(this);
  }
  
  
  abstract class TransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> backingIterator;

    TransformedIterator(Iterator<? extends F> backingIterator) {
      this.backingIterator = backingIterator;
    }

    abstract T transform(F from);

    @Override public final boolean hasNext() {
      return backingIterator.hasNext();
    }

    @Override public final T next() {
      return transform(backingIterator.next());
    }
    
    @Override public void remove() {  
      backingIterator.remove();
    }
  }
}
