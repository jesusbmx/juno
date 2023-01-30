package juno.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TransformedCollect<P, R> extends AbstractCollection<R> {
  final Collection<P> fromCollection;
  final Fun<? super P, ? extends R> function;

  public TransformedCollect(Collection<P> fromCollection, Fun<? super P, ? extends R> function) {
    this.fromCollection = fromCollection;
    this.function = function;
  }

  @Override public void clear() {
    fromCollection.clear();
  }

  @Override public boolean isEmpty() {
    return fromCollection.isEmpty();
  }

  @Override public Iterator<R> iterator() {
    return new TransformedIterator<P, R>(fromCollection.iterator()) {
      @Override R transform(P from) {
        return function.apply(from);
      }
    };
  }

  @Override public int size() {
    return fromCollection.size();
  }

  public List<R> toList() {
    return new ArrayList<R>(this);
  }
  
  public Set<R> toSet() {
    return new HashSet<R>(this);
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
