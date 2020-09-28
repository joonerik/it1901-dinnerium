package dinnerium.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class Container<T> implements Iterable<T> {

    private Collection<T> container;

    /** Initializes the container as a ArrayList. */
    public Container() {
        this.container = new ArrayList<>();
    }

    /** sets container.  */
    public Container(Collection<T> container) {
        this.setCollection(container);
    }

    /**
     * sets the collection and thus the container.
     *
     * @param container
     *        of collection
     * @throws IllegalArgumentException
     *        if container is invalid/empty
     */
    public void setCollection(Collection<T> container) {
        if (container.isEmpty()) {
            throw new IllegalArgumentException(
                    container + "invalid");
        }
        this.container = container;
    }

    /**
     * adds item to the container
     *
     * @param item
     *        of type
     * @throws IllegalArgumentException
     *        if name is invalid
     */
    public void addItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add to container");
        }
        this.container.add(item);
    }

    /**
     * @return container
     */
    public Collection<T> getContainer() {
        return this.container;
    }

    /**
     * @return number of elements in container
     */
    public int getContainerSize() {
        return this.container.size();
    }

    /**
     * @return a container iterator
     */
    @Override
    public Iterator<T> iterator() {
        return this.container.iterator();
    }
}