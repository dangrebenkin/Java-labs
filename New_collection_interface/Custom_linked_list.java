package New_collection_interface;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

class Custom_linked_list<E> implements Collection<E> {

    private Node<E> last_of_the_list;
    private Node<E> first_of_the_list;

    private static class Node<E> {
        Node<E> next;
        Node<E> prev;
        E data;

        private Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        first_of_the_list = null;
        last_of_the_list = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        for (E e : this) {
            if (e.equals(o)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean add(E t) {

        if (size == 0) {
            first_of_the_list = new Node<>(t, null, null);
            last_of_the_list = new Node<>(t, null, null);
            size++;
        } else if (size == 1) {
            last_of_the_list = new Node<>(t, first_of_the_list, null);
            first_of_the_list.next = last_of_the_list;
            size++;
        } else {
            last_of_the_list = new Node<>(t, last_of_the_list, null);
            last_of_the_list.prev.next = last_of_the_list;
            size++;
        }

        return true;
    }

    private boolean remove_from_current_node(Object o, Node<E> current_node) {
        boolean result = false;
        while (current_node.next != null){
            if (current_node.data.equals(o)) {
                if (current_node.prev == null) {
                    first_of_the_list = first_of_the_list.next;
                    first_of_the_list.prev = null;
                } else {
                    current_node.prev.next = current_node.next;
                }
                result = true;
                size--;
                break;
            } else {
                current_node = current_node.next;
            }
        }
        if (current_node.next == null && current_node.data.equals(o)) {
            last_of_the_list = last_of_the_list.prev;
            last_of_the_list.next = null;
            result = true;
            size--;
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
        if (size == 1) {
            if (first_of_the_list.data.equals(o)) {
                clear();
                result = true;
            }
        } else if (size > 1) {
          result = remove_from_current_node(o,first_of_the_list);
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        Object[] array_of_objects = new Object[size];
        Iterator<?> iter = iterator();
        int counter = 0;
        while (iter.hasNext()){
            array_of_objects[counter] = iter.next();
            counter++;
        }
        return array_of_objects;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        Object[] arr = c.toArray();
        if (!isEmpty() && arr.length != 0) {
            for (Object o : arr)
                if (!contains(o)) {
                    result = false;
                    break;
                }
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        Object[] arr = c.toArray();
        if (!isEmpty() && arr.length != 0) {
            for (Object o : arr)
                if (remove(o)) {
                    result = true;
                }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        Object[] arr = c.toArray();
        if (!isEmpty() && arr.length != 0) {
            Node<E> current_node = first_of_the_list;
            for (Object o : arr){
                while (current_node.next != null){
                    if (!current_node.data.equals(o)) {
                        remove_from_current_node(o, current_node);
                        result = true;
                    }
                    current_node = current_node.next;
                }
                if (!current_node.data.equals(o)) {
                    remove_from_current_node(o, current_node);
                }
            }
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        Object[] arr = c.toArray();
        if (arr.length != 0) {
            for (Object o : arr) {
                add((E) o);
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private Node<E> current_node = first_of_the_list;
            boolean first_marker = true;

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (current_node != null) {
                    result = current_node.next != null;}
                return result;
            }

            @Override
            public E next() {
                if (first_marker) {
                    first_marker = false;
                    return current_node.data;
                } else {
                    if (hasNext()) {
                        current_node = current_node.next;
                        return current_node.data;
                    }
                }
                return null;
            }
        };
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] result;
        Iterator<?> iter = iterator();
        int counter = 0;
        if (a.length >= size) {
            while (iter.hasNext()) {
                a[counter] = (T1) iter.next();
                counter ++;
            }
            result = a;
        } else {
            T1[] new_array = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
            while (iter.hasNext()) {
                new_array[counter] = (T1) iter.next();
                counter++;
            }
            result = new_array;
        }
        return result;
    }
}
