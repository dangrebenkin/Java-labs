package New_collection_interface;

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
        Node<E> x = first_of_the_list;
        for (int j = 0; j < size; j++) {
            if (x.data.equals(o)) {
                result = true;
                break;
            } else {
                x = x.next;
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
            last_of_the_list.next = new Node<>(t, last_of_the_list, null);
            size++;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
        if (size == 1) {
            if (first_of_the_list.data.equals(o)) {
                clear();
            }
        } else if (size > 1) {
            Node<E> current_node = first_of_the_list;
            for (int j = 0; j < size; j++) {
                if (current_node.data.equals(o)) {
                    if (j == 0) {
                        first_of_the_list = current_node.next;
                        first_of_the_list.prev = null;
                        result = true;
                        size--;
                    } else if (j == size - 1) {
                        last_of_the_list = current_node.prev;
                        last_of_the_list.next = null;
                        result = true;
                        size--;
                    } else {
                        current_node.prev.next = current_node.next;
                        result = true;
                        size--;
                    }
                    break;
                } else {
                    current_node = current_node.next;
                }
            }
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[0];
        if (size > 0) {
            Object[] array_of_objects = new Object[size];
            Node<E> current_node = first_of_the_list;
            for (int j = 0; j < size; j++) {
                if (j == size - 1) {
                    array_of_objects[j] = current_node.data;
                    result = array_of_objects;
                    break;
                } else {
                    array_of_objects[j] = current_node.data;
                    current_node = current_node.next;
                }
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        Object[] arr = c.toArray();
        if (arr.length != 0) {
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
        boolean result = true;
        Object[] arr = c.toArray();
        if (arr.length != 0) {
            for (Object o : arr)
                if (!remove(o))
                    result = false;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
//        Object[] arr = c.toArray();
//        Node<E> current_node = first_of_the_list;
//        if (arr.length != 0) {
//            for (Object o : arr) {
//                for (int j = 0; j < size; j++) {
//                    if (!current_node.data.equals(o)) {
//                        if (j == size-1) {
//                            remove(o);
//                        }else {
//                            remove(o);
//                            current_node = current_node.next;
//                        }
//
//                    }
//                }
//            }
//
//        } else {
//            clear();
//        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = true;
//        Object[] arr = c.toArray();
//        if (arr.length != 0) {
//            for (Object o : arr)
//                if (!add((E) o)) {
//                    result = false;
//                }
//        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

}
