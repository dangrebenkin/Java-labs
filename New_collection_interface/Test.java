package New_collection_interface;

import java.util.Arrays;
import java.util.Iterator;

public class Test {

    // создаем массив элементов нашей коллекции и выводим на экран (поскольку в
    // нашем случае это строки, то можно использваоть класс StringBuilder)
    public static void pretty_string(Custom_linked_list<String> obj) {
        Object[] arr = obj.toArray();
        StringBuilder sb = new StringBuilder();
        for (Object o : arr) {
            sb.append(o);
            sb.append("; ");
        }
        System.out.println("Элементы коллекции: " + sb);
    }

    public static void main(String[] args) {

        // создаем объект класса Custom_linked_list, допустим мы хотим создать коллекцию из объектов класса String
        Custom_linked_list<String> simple_box = new Custom_linked_list<>();

        // создаём и добавляем элементы в Custom_linked_list
        String one = "Новый элемент";
        String two = "Второй элемент";
        String three = "Третий элемент";
        String four = "Четвертый элемент";
        String five = "Пятый элемент";
        String six = "Шестой элемент";
        String seven = "Седьмой элемент";

        simple_box.add(one);
        simple_box.add(two);
        simple_box.add(three);
        pretty_string(simple_box);

        System.out.printf("Размер коллекции: %d \n", simple_box.size());
        System.out.printf("Объект '%s' находится в коллекции? %b \n",
                one, simple_box.contains(one));
        System.out.printf("Объект '%s' удалось удалить? %b \n",
                three, simple_box.remove(three)); // true
        System.out.printf("Коллекция пустая? %b \n", simple_box.isEmpty());
        pretty_string(simple_box);
        simple_box.clear();
        System.out.printf("Объект '%s' удалось удалить из пустой коллекции? %b \n",
                three, simple_box.remove(three)); // false
        System.out.println();

        Custom_linked_list<String> simple_box2 = new Custom_linked_list<>();
        Custom_linked_list<String> simple_box3 = new Custom_linked_list<>();
        Custom_linked_list<String> simple_box4 = new Custom_linked_list<>();

        simple_box2.add(five);
        simple_box2.add(three);
        simple_box2.add(seven);
        simple_box2.add(one);

        simple_box3.add(four);
        simple_box3.add(five);
        simple_box3.add(six);

        simple_box4.add(four);
        simple_box4.add(five);
        simple_box4.add(six);

        pretty_string(simple_box2);
        pretty_string(simple_box3);
        System.out.printf("Коллекция simple_box2 содержит все элементы simple_box3? %b \n",
                simple_box2.containsAll(simple_box3));
        System.out.printf("Удалось ли удалить все элементы simple_box2 из simple_box3? %b \n",
                simple_box3.removeAll(simple_box2));
        pretty_string(simple_box2);
        pretty_string(simple_box3);
        System.out.println();

        pretty_string(simple_box3);
        pretty_string(simple_box4);
        System.out.printf("Изменилась ли коллекция после удаления несовпадающих элементов? %b \n",
                simple_box4.retainAll(simple_box3));
        pretty_string(simple_box3);
        System.out.println();

        pretty_string(simple_box3);
        pretty_string(simple_box2);
        System.out.printf("Удалось ли добавить все элементы simple_box3 в simple_box2? %b \n",
                simple_box2.addAll(simple_box3));
        pretty_string(simple_box2);
        System.out.println();

        Iterator <?> iter = simple_box2.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        String[] array_of_strings = new String[simple_box2.size()];
        String[] arr = simple_box2.toArray(array_of_strings);
        System.out.println(Arrays.toString(arr));
    }
}


