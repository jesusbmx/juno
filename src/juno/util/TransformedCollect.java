package juno.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TransformedCollect<Input, Result> extends AbstractCollection<Result> {
  final Collection<Input> fromCollection;
  final Fun<? super Input, ? extends Result> function;

  public TransformedCollect(Collection<Input> fromCollection, Fun<? super Input, ? extends Result> function) {
    this.fromCollection = fromCollection;
    this.function = function;
  }

  @Override public void clear() {
    fromCollection.clear();
  }

  @Override public boolean isEmpty() {
    return fromCollection.isEmpty();
  }

  @Override public Iterator<Result> iterator() {
    return new TransformedIterator<Input, Result>(fromCollection.iterator()) {
      @Override Result transform(Input from) {
        return function.apply(from);
      }
    };
  }

  @Override public int size() {
    return fromCollection.size();
  }

  public List<Result> toList() {
    return new ArrayList<Result>(this);
  }
  
  public Set<Result> toSet() {
    return new HashSet<Result>(this);
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
